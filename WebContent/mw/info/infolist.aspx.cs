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

namespace PortalWeb.mw.Info
{
    public partial class InfoList : System.Web.UI.Page
    {
        private MW.BLL.B_Article bll = new MW.BLL.B_Article();

        private string strWhere, SearchName;
        private string typeid = "";

        protected void Page_Load(object sender, EventArgs e)
        {
            SearchName = LYRequest.GetString("SearchName", true);
            typeid = LYRequest.GetString("typeid");

           
            if (!IsPostBack)
            {
                InitData();
                BindList();
            }
        }
        private void InitData()
        {
            //DataTable dt = new MW.BLL.B_ecan_domain().GetListByParent(PageID);
            //JBind.BindList(dropTypeId, JTree.GetClassTree(dt, "ParentId", PageID.ToString(), "DictionaryName"));
            //dropTypeId.Items.Insert(0, new ListItem("全部分类", "0"));
            //dropTypeId.SelectedValue = typeid.ToString();
            //txtSearchName.Text = SearchName;
        }
        private void BindList()
        {
            strWhere = "1=1";
            #region __________Where语句__________
            if (typeid !="")
            {
                strWhere = "  TypeId ='" + typeid+"'";
            }
            else
            {

                strWhere = "  TypeId ='" + PageID+"'";
            }

            if (SearchName != "")
            {
                //strWhere += " and (title like '%" + SearchName + "%')";
            }
            #endregion

            rpt_List.PageLink = "infolist.aspx?Search=Yes&pageid=" + PageID + "&TypeId=" + typeid + "&SearchName=" + Utils.UrlEncode(SearchName);
            rpt_List.PageSize = 15;
            rpt_List.CurrentPage = LYRequest.GetInt("page", 1);

            DataSet ds = MW.BLL.mw_getlistbypage.GetDataSet_NoCache("mw_article", "*", strWhere, "", "ID DESC", "ID", rpt_List.CurrentPage, rpt_List.PageSize, 1);

            rpt_List.RecordCount = Convert.ToInt32(ds.Tables[0].Rows[0][0]);
            JBind.BindList(rpt_List, ds.Tables[1]);
        }
        protected void btnSearch_Click(object sender, EventArgs p1)
        {
            StringBuilder builder = new StringBuilder("infolist.aspx?Search=Yes&pageid=" + PageID + "");
            //builder.Append("&TypeId=" + JBind.GetSelectedList(dropTypeId));

            //if (!string.IsNullOrEmpty(this.txtSearchName.Text.Trim()))
            //{
            //    builder.Append("&SearchName=" + this.txtSearchName.Text.Trim());
            //}
            base.Response.Redirect(builder.ToString());
        }
        protected void lbtnDeletes_Click(object sender, EventArgs p1)
        {
            if (BasePage.IsPermissions(59))
            {
                int num = 0;
                foreach (RepeaterItem item in this.rpt_List.Items)
                {
                    HtmlInputCheckBox box = (HtmlInputCheckBox)item.FindControl("cheId");
                    if (box.Checked)
                    {
                        bll.Delete(int.Parse(box.Value));
                        num++;
                    }
                }
                //if (num > 0)
                //{
                //    B_Operationlog.CreateOperationLog(BasePage.UId,string.Format("共批量删除{0}条信息", num));
                //}
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
                if ((!BasePage.IsPermissions(59)))
                {
                    MessageBox.Alert(this, "没有删除权限!");
                }
                else
                {
                    bll.Delete(num);
                    //Literal literal = (Literal)p1.Item.FindControl("litName");
                    //B_Operationlog.CreateOperationLog(BasePage.UId, B_Operationlog.operationLogType.Del, "信息", literal.Text);
                    base.Response.Redirect(HttpContext.Current.Request.Url.PathAndQuery);
                }
            }
        }
        //protected void rpt_List_ItemDataBound(object source, RepeaterItemEventArgs e)
        //{
        //    if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
        //    {
        //        DataRowView drv = e.Item.DataItem as DataRowView;
        //        Literal lblPID = e.Item.FindControl("litName") as Literal;
        //        lblPID.Text = drv["title"].ToString();
        //    }
        //}

        protected string PageID
        {
            get
            {
                return "wxxw";// LYRequest.GetString("pageid");
            }
        }
    }
}