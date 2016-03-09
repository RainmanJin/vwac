using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using MW;
using MW.BLL;
using MW.Common;
using System.Data;
using System.Text;
using System.Web.UI.HtmlControls;
using MW.Model;

namespace Plugin.VWSurvey.Admini.VWSurvey
{
    public partial class BiceItem : System.Web.UI.Page
    {
        private MW.BLL.B_Votesubject bll = new MW.BLL.B_Votesubject();
        protected int sysid = 0, Parentid=0;
        private string strWhere, SearchName;

        protected void Page_Load(object sender, EventArgs e)
        {
            SearchName = MW.Common.LYRequest.GetString("SearchName", true);
            sysid = LYRequest.GetInt("SysId", 0);
            Parentid = LYRequest.GetInt("Parentid", 0);
            if (!IsPostBack)
            {
                InitData();
                BindList();
            }
        }
        private void InitData()
        {
            JBind.BindList(drpType, bll.GetList(0, "N_SubId,C_SubTitle", "Parentid=0 and N_SysId=" + sysid, "").Tables[0], "C_SubTitle", "N_SubId");//省份
            drpType.Items.Insert(0, new ListItem("----", "0"));

            txtSearchName.Text = SearchName;
            drpType.SelectedValue = Parentid.ToString();
        }
        private void BindList()
        {

            #region __________Where语句__________

            strWhere = "  N_sysid="+sysid;
            if (SearchName != "")
            {
                strWhere += " and (C_SubTitle like '%" + SearchName + "%')";
            }
            if (Parentid > 0)
            {
                strWhere += " and Parentid=" + Parentid;
            }
            #endregion

            rpt_List.PageLink = "biceitem.aspx?Search=Yes&Parentid=" + Parentid + "&SysId=" + sysid + "&SearchName=" + Utils.UrlEncode(SearchName);
            rpt_List.PageSize = 15;
            rpt_List.CurrentPage = LYRequest.GetInt("page", 1);

            DataSet ds = MW.BLL.mw_getlistbypage.GetDataSet_NoCache("mw_votesubject", "*", strWhere, "", "N_SubId DESC", "N_SubId", rpt_List.CurrentPage, rpt_List.PageSize, 1);

            rpt_List.RecordCount = Convert.ToInt32(ds.Tables[0].Rows[0][0]);
            JBind.BindList(rpt_List, ds.Tables[1]);
        }
        protected void btnSearch_Click(object sender, EventArgs p1)
        {
            StringBuilder builder = new StringBuilder("biceitem.aspx?Search=Yes&SysId=" + sysid + "");
            builder.Append("&Parentid=" + JBind.GetSelectedList(drpType));

            if (!string.IsNullOrEmpty(this.txtSearchName.Text.Trim()))
            {
                builder.Append("&SearchName=" + this.txtSearchName.Text.Trim());
            }
            base.Response.Redirect(builder.ToString());
        }
        protected void lbtnDeletes_Click(object sender, EventArgs p1)
        {
#warning BasePage.IsPermissions(1)
            if (BasePage.IsPermissions(1))
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
                if (!BasePage.IsPermissions(1))
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
        protected void rpt_List_ItemDataBound(object sender, RepeaterItemEventArgs e)
        {
            if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
            {
                DataRowView drv = (DataRowView)e.Item.DataItem;
                int parentid = Utils.StrToInt(drv["parentid"], 0);
                if (!BaseVote.ShowSysEdit(sysid)&&parentid == 0)
                {
                    ((Literal)e.Item.FindControl("lit")).Text = "<a href=\"biceitemdetail.aspx?sysid=" + sysid + "&parentid=" + drv["n_subid"] + "\">添加子项</a>";
                }
                if (BaseVote.ShowSysEdit(sysid))
                {

                    ((LinkButton)e.Item.FindControl("lbtnDelete")).Visible = false;
                    
                }


            }
        }

        protected string GetType(object n_type)
        {
            if (n_type != null && n_type.ToString() != "0")
            {
                var model = new MW.BLL.B_Voteunit().GetModel(MW.Common.Utils.StrToInt(n_type, 0));
                if (model != null) return model.C_SubTitle;
            }
            return "";
        }
    }
}