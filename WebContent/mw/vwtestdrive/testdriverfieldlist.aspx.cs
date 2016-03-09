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
    public partial class testdriverfieldlist : System.Web.UI.Page
    {
        private MW.BLL.B_Testdriverfield bll = new MW.BLL.B_Testdriverfield();

        private string strWhere, SearchName;

        protected void Page_Load(object sender, EventArgs e)
        {
            SearchName = MW.Common.LYRequest.GetString("SearchName", true);

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
                //模糊查询语句
                strWhere += " and (C_Name like '%" + SearchName + "%')";
            }
            #endregion

            rpt_List.PageLink = "testdriverfieldlist.aspx?Search=Yes&SearchName=" + Utils.UrlEncode(SearchName);
            rpt_List.PageSize = 15;
            rpt_List.CurrentPage = LYRequest.GetInt("page", 1);

            DataSet ds = MW.BLL.mw_getlistbypage.GetDataSet_NoCache("mw_testdriverfield", "*", strWhere, "", "ID DESC", "ID", rpt_List.CurrentPage, rpt_List.PageSize, 1);

            rpt_List.RecordCount = Convert.ToInt32(ds.Tables[0].Rows[0][0]);
            JBind.BindList(rpt_List, ds.Tables[1]);
        }
        protected void btnSearch_Click(object sender, EventArgs p1)
        {
            StringBuilder builder = new StringBuilder("testdriverfieldlist.aspx?Search=Yes");
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
        protected void rpt_List_ItemDataBound(object sender, RepeaterItemEventArgs e)
        {
            if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
            {
                DataRowView drv = (DataRowView)e.Item.DataItem;
                int parentid = Utils.StrToInt(drv["parentid"], 0);
                if (parentid == 0)
                {
                    ((Literal)e.Item.FindControl("lit")).Text = "<a href=\"testdriverfielddetail.aspx?parentid=" + drv["id"] + "\" >添加子项</a>";
                }

            }
        }
        protected string getname(object id)
        {
            if (id != null && id.ToString() != "0")
            {
                var model = bll.GetModel(Utils.StrToInt(id, 0));
                if (model != null)
                {
                    return model.C_Name;
                }
            }
            return "";
        }
    }
}