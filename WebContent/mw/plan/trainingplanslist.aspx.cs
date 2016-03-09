using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Web;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using MW.Common;
using MW.BLL;
namespace PortalWeb.mw
{
    public partial class TrainingPlansList : System.Web.UI.Page
    {

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                InitData();
                BindList();
            }
        }
        private void InitData()
        {
            

        }
      
        private void BindList()
        {

            #region __________Where语句__________

            string strWhere = "  1=1";

            //if (SearchName != "")
            //{
            //    strWhere += " and (PlanName like '%" + SearchName + "%')";
            //}
            //if (starttime != "")
            //{
            //    strWhere += " and (BeginTime>='" + starttime + "')";
            //}
            //if (endtime != "")
            //{
            //    strWhere += " and (BeginTime<='" + endtime + "')";
            //}
            //if (userid > 0)
            //{
            //    strWhere += " and dbo.inArray(" + userid + ",BelongTo)>0";
            //}
            #endregion

            rpt_List.PageLink = "trainingplanslist.aspx?Search=Yes";
            rpt_List.PageSize = 15;
            rpt_List.CurrentPage = LYRequest.GetInt("page", 1);

            DataSet ds = MW.BLL.mw_getlistbypage.GetDataSet_NoCache("TECH_TRAIN_COURSE", "*", strWhere, "", "ID DESC", "ID", rpt_List.CurrentPage, rpt_List.PageSize, 1);

            rpt_List.RecordCount = Convert.ToInt32(ds.Tables[0].Rows[0][0]);
            JBind.BindList(rpt_List, ds.Tables[1]);
        }
       
       
    }
}