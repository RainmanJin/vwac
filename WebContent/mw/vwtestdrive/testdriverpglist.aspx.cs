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
using MW.Common;

namespace PortalWeb.mw.vwtestdrive
{
    public partial class testDriverpglist : System.Web.UI.Page
    {
        private MW.BLL.B_Testdriverpg bll = new MW.BLL.B_Testdriverpg();

        private string strWhere, SearchName, Student, Changdi, CreateTime, EndTime;

        protected void Page_Load(object sender, EventArgs e)
        {
            //SearchName = MW.Common.LYRequest.GetString("SearchName", true);
            //Student = MW.Common.LYRequest.GetString("Student", true);
            //Changdi = MW.Common.LYRequest.GetString("Changdi", true);
            //CreateTime = MW.Common.LYRequest.GetString("StartTime");
            //EndTime = MW.Common.LYRequest.GetString("EndTime");

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

            strWhere = "1=1";
            if (SearchName != "")
            {
                strWhere += " and (Chexi like '%" + SearchName + "%')";
            }
            if (Student != "")
            {
                strWhere += " and (Student like '%" + Student + "%')";
            }
            if (Changdi != "")
            {
                strWhere += " and (Changdi like '%" + Changdi + "%')";
            }
            if (CreateTime != "")
            {
                strWhere += " and (CreateTime >= '" + CreateTime + "')";
            }
            if (EndTime != "")
            {
                strWhere += " and (CreateTime <= '" + EndTime + "')";
            }
            #endregion
            //StringBuilder builder = new StringBuilder(MW.BasePage.GetAdminPath + "testdriverpglist.aspx?Search=Yes");
            //builder.Append("&SearchName=" + Utils.UrlEncode(SearchName)).ToString();
            //builder.Append("&Student=" + Utils.UrlEncode(Student)).ToString();
            //builder.Append("&Changdi=" + Utils.UrlEncode(Changdi)).ToString();
            //builder.Append("&CreateTime=" + Utils.UrlEncode(CreateTime)).ToString();
            rpt_List.PageLink = "testdriverpglist.aspx?Search=Yes&SearchName=" + Utils.UrlEncode(SearchName);
            //rpt_List.PageLink = builder.Append("&EndTime=" + Utils.UrlEncode(EndTime)).ToString();
            rpt_List.PageSize = 10;
            rpt_List.CurrentPage = LYRequest.GetInt("page", 1);

            DataSet ds = MW.BLL.mw_getlistbypage.GetDataSet_NoCache("mw_testdriverpg", "*", strWhere, "", "ID DESC", "ID", rpt_List.CurrentPage, rpt_List.PageSize, 1);

            rpt_List.RecordCount = Convert.ToInt32(ds.Tables[0].Rows[0][0]);
            JBind.BindList(rpt_List, ds.Tables[1]);
        }
        protected void btnSearch_Click(object sender, EventArgs p1)
        {
            StringBuilder builder = new StringBuilder("testdriverpglist.aspx?Search=Yes");
            //builder.Append("&TypeId="+JBind.GetSelectedList(drpTypeId));

         
            base.Response.Redirect(builder.ToString());
        }
        protected void lbtnDeletes_Click(object sender, EventArgs p1)
        {
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
        }
        protected void rpt_List_ItemCommand(object source, RepeaterCommandEventArgs p1)
        {
            int num = int.Parse(p1.CommandArgument.ToString());
            if (p1.CommandName == "lbtnDelete")
            {
                bll.Delete(num);
                base.Response.Redirect(HttpContext.Current.Request.Url.PathAndQuery);
            }
        }
    }
}