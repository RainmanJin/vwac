using System;
using System.Collections;
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
using MW.Model;

namespace Plugin.VWSurvey.Admini.VWSurvey
{
           
    public partial class MakeItem : System.Web.UI.Page
    {
        protected int subid = 0, sysid = 0, ntype = 0;

        private MW.BLL.B_Votekey bll = new MW.BLL.B_Votekey();

        private string strWhere, SearchName;

        protected void Page_Load(object sender, EventArgs e)
        {
            SearchName = MW.Common.LYRequest.GetString("SearchName", true);
            subid = LYRequest.GetInt("subid", 0);
            ntype = LYRequest.GetInt("type", 0);
            sysid = MW.Common.LYRequest.GetInt("sysid", 0);
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

            txtSearchName.Text = SearchName;
        }
      
        private void BindList()
        {

            #region __________Where语句__________

            strWhere = "  N_SubId=" + subid;
            if (SearchName != "")
            {
                strWhere += " and (C_KeyTitle like '%" + SearchName + "%')";
            }
            #endregion

            rpt_List.PageLink = "makeitem.aspx?Search=Yes&ntype=" + ntype + "&subid=" + subid + "&SearchName=" + Utils.UrlEncode(SearchName);
            rpt_List.PageSize = 15;
            rpt_List.CurrentPage = LYRequest.GetInt("page", 1);

            DataSet ds =mw_getlistbypage.GetDataSet_NoCache("mw_votekey", "*", strWhere, "", "N_KeyId DESC", "N_KeyId", rpt_List.CurrentPage, rpt_List.PageSize, 1);

            rpt_List.RecordCount = Convert.ToInt32(ds.Tables[0].Rows[0][0]);
            JBind.BindList(rpt_List, ds.Tables[1]);
        }
        protected void btnSearch_Click(object sender, EventArgs p1)
        {
            StringBuilder builder = new StringBuilder("makeitem.aspx?Search=Yes&ntype=" + ntype + "&subid=" + subid + "");
            //builder.Append("&TypeId="+JBind.GetSelectedList(drpTypeId));

            if (!string.IsNullOrEmpty(this.txtSearchName.Text.Trim()))
            {
                builder.Append("&SearchName=" + Utils.UrlEncode(this.txtSearchName.Text.Trim()));
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
                //object sysid = drv["N_SubId"];
                if (BaseVote.ShowSysEdit(sysid))
                {
                    ((LinkButton)e.Item.FindControl("lbtnDelete")).Visible = false;
                }
            }
        }

        protected string GetRuleDB(object C_Rule)
        {
            if (Utils.IsNullOrEmpty(C_Rule))
            {
                return "不限制";
            }
            return C_Rule.ToString().Split(new char[] { '`' })[0];
        }
        
    }
}