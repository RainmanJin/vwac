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
    public partial class testdriverlist : System.Web.UI.Page
    {
        private MW.BLL.B_Testdriver bll = new MW.BLL.B_Testdriver();
        private MW.Model.M_Mw_testdriver model = new MW.Model.M_Mw_testdriver();
        private string strWhere, SearchName;
        private string parentdialog = "Add";
        protected void Page_Load(object sender, EventArgs e)
        {
           // SearchName = MW.Common.LYRequest.GetString("SearchName", true);
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
            if (SearchName != "")
            {
                strWhere += " and (C_Name like '%" + SearchName + "%')";
            }
            #endregion

            rpt_List.PageLink = "testdriverlist.aspx?Search=Yes%SearchName=" + Utils.UrlEncode(SearchName);
            rpt_List.PageSize = 15;
            rpt_List.CurrentPage = LYRequest.GetInt("page", 1);

            DataSet ds = MW.BLL.mw_getlistbypage.GetDataSet_NoCache("mw_testdriver", "*", strWhere, "", "ID DESC", "ID", rpt_List.CurrentPage, rpt_List.PageSize, 1);

            rpt_List.RecordCount = Convert.ToInt32(ds.Tables[0].Rows[0][0]);
            JBind.BindList(rpt_List, ds.Tables[1]);
        }
        protected void btnSearch_Click(object sender, EventArgs p1)
        {
            StringBuilder builder = new StringBuilder("testdriverlist.aspx?Search=Yes");
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
        protected string getname(object id, object type)
        {
            string num = "";
            if (type == null)
            {
                return "";
            }
            if (type.ToString().IndexOf("0") > -1)
            {
                num += "<a href=\"javascript:void(0)\" onclick=\"javascript:goUrl('changdidetail.aspx?Id=" + id + "&type=0');\">公路</a>";
            }
            if (type.ToString().IndexOf("1") > -1)
            {
                num += " " + "<a href=\"javascript:void(0)\" onclick=\"javascript:goUrl('changdidetail.aspx?Id=" + id + "&type=1',550,400,'');\">运动</a>";
            }
            if (type.ToString().IndexOf("2") > -1)
            {
                num += " " + "<a href=\"javascript:void(0)\" onclick=\"javascript:goUrl('changdidetail.aspx?Id=" + id + "&type=2',550,400,'');\">越野</a>";
            }
            return num;
        }
    }
}