using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Web;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using MW;
using MW.BLL;
using MW.Common;

namespace PortalWeb.mw.plan
{
    public partial class applyplanlist : System.Web.UI.Page
    {
        private MW.BLL.B_mw_Applyplans bll = new MW.BLL.B_mw_Applyplans();

        private string strWhere, SearchName;
        private int id;

        protected void Page_Load(object sender, EventArgs e)
        {
           // SearchName = MW.Common.LYRequest.GetString("SearchName", true);
            id = LYRequest.GetInt("id", 0);
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

            strWhere = "  1=1";
            //if (SearchName != "")
            //{
            //    strWhere += " and ApplyPlanID in(select id from mw_trainingplans where (PlanName like '%" + SearchName + "%'))";
            //}
            //if (userid > 0)
            //{
            //    strWhere += " and ApplierID=" + userid + "";
            //}
            #endregion

            rpt_List.PageLink = "applyplanslist.aspx?Search=Yes&userid=" + id + "&SearchName=" + Utils.UrlEncode(SearchName);
            rpt_List.PageSize = 15;
            rpt_List.CurrentPage = LYRequest.GetInt("page", 1);

            DataSet ds = MW.BLL.mw_getlistbypage.GetDataSet_NoCache("mw_applyplans", "*", strWhere, "", "ID DESC", "ID", rpt_List.CurrentPage, rpt_List.PageSize, 1);

            rpt_List.RecordCount = Convert.ToInt32(ds.Tables[0].Rows[0][0]);
            JBind.BindList(rpt_List, ds.Tables[1]);
        }
        protected void btnSearch_Click(object sender, EventArgs p1)
        {
            StringBuilder builder = new StringBuilder("applyplanslist.aspx?Search=Yes");
            //builder.Append("&TypeId="+JBind.GetSelectedList(drpTypeId));
         
            base.Response.Redirect(builder.ToString());
        }
        protected void lbtnDeletes_Click(object sender, EventArgs p1)
        {
#warning basepage.IsPermissions(0x6c)
            if (BasePage.IsPermissions(0x6c))
            {
                //int num = 0;
                foreach (RepeaterItem item in this.rpt_List.Items)
                {
                    HtmlInputCheckBox box = (HtmlInputCheckBox)item.FindControl("cheId");
                    if (box.Checked)
                    {
                        bll.Delete(int.Parse(box.Value));
                        // num++;
                    }
                }
                base.Response.Redirect(HttpContext.Current.Request.Url.PathAndQuery);
            }
            else
            {
                MessageBox.Alert(this, "没有删除权限!");
            }
        }
        protected void rpt_List_ItemCommand(object source, RepeaterCommandEventArgs p1)
        {
            int num = int.Parse(p1.CommandArgument.ToString());
            if (p1.CommandName == "lbtnDelete")
            {
#warning delte IsPermissions(0x6c)
                if (!BasePage.IsPermissions(0x6c))
                {
                    MessageBox.Alert(this, "没有删除权限!");
                }
                else
                {
                    bll.Delete(num);
                    base.Response.Redirect(HttpContext.Current.Request.Url.PathAndQuery);
                }
            }
        }
    }
}