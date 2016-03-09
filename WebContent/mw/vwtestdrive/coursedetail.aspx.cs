using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using MW.Common;

namespace PortalWeb.mw.vwtestdrive
{
    public partial class coursedetail : System.Web.UI.Page
    {
        private MW.BLL.B_Testdriver bll = new MW.BLL.B_Testdriver();

        private string strWhere, SearchName;

        protected void Page_Load(object sender, EventArgs e)
        {
            hideid1.Value = MW.Common.LYRequest.GetString("hidCtl");
            hideradio1.Value = MW.Common.LYRequest.GetString("txtCtl");
            SearchName = MW.Common.LYRequest.GetString("SearchName", true);

            if (!IsPostBack)
            {
                InitData();
                BindList();
            }
        }
        private void InitData()
        {
            //JBind.BindList(Drp_Province, bll.GetProvince(), "Name", "ID");//省份
            // Drp_Province.Items.Insert(0, new ListItem("请选择", "-1"));


        }
        private void BindList()
        {

            #region __________Where语句__________

            strWhere = "  1=1";
            //if (SearchName != "")
            //{
            //    strWhere += " and (PlanName like '%" + SearchName + "%')";
            //}
            #endregion
            rpt_List.PageLink = string.Format("coursedetail.aspx?hidCtl={0}&txtCtl={1}&UserName={2}", base.Request.QueryString["hidCtl"], base.Request.QueryString["txtCtl"], C_course);
            rpt_List.PageSize = 7;
            rpt_List.CurrentPage = LYRequest.GetInt("page", 1);

            DataSet ds = MW.BLL.mw_getlistbypage.GetDataSet_NoCache("TECH_TRAIN_COURSE", "ID,NAME", strWhere, "", "ID DESC", "ID", rpt_List.CurrentPage, rpt_List.PageSize, 1);

            rpt_List.RecordCount = Convert.ToInt32(ds.Tables[0].Rows[0][0]);
            JBind.BindList(rpt_List, ds.Tables[1]);
        }
        protected void btnSearch_Click(object sender, EventArgs p1)
        {
            StringBuilder builder = new StringBuilder("coursedetail.aspx?Search=Yes");

          
            base.Response.Redirect(builder.ToString());
        }
        private string UserName
        {
            get
            {
                if (!string.IsNullOrEmpty(base.Request.QueryString["SearchName"]))
                {
                    string text1 = base.Request.QueryString["SearchName"];
                    
                }
                return base.Request.QueryString["SearchName"];
            }
        }
        private string C_course
        {
            get
            {
                if (!string.IsNullOrEmpty(base.Request.QueryString["C_course"]))
                {
                    string text1 = base.Request.QueryString["C_course"];

                }
                return base.Request.QueryString["C_course"];
            }
        }
        protected void rpt_List_ItemCommand(object source, RepeaterCommandEventArgs p1)
        {

        }
    }
}