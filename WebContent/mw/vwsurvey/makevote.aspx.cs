using System;
using System.Collections.Generic;
using System.Data;
using System.Drawing.Imaging;
using System.IO;
using System.Linq;
using System.Text;
using System.Web;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using MW;
using MW.BLL;
using MW.Common;
using ThoughtWorks.QRCode.Codec;

namespace Plugin.VWSurvey.Admini.VWSurvey
{
    public partial class MakeVote : System.Web.UI.Page
    {
        private MW.BLL.B_Votesystem bll = new MW.BLL.B_Votesystem();

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
            //JBind.BindList(Drp_Province, bll.GetProvince(), "Name", "ID");//省份
           // Drp_Province.Items.Insert(0, new ListItem("请选择", "-1"));

            txtSearchName.Text = SearchName;
        }
        private void BindList()
        {
            #region __________Where语句__________

            strWhere = "  1=1";
            if (SearchName != "")
            {
                strWhere += " and (c_title like '%" + SearchName + "%')";
            }
            #endregion

            rpt_List.PageLink = "makevote.aspx?Search=Yes&SearchName=" + Utils.UrlEncode(SearchName);
            rpt_List.PageSize = 15;
            rpt_List.CurrentPage = LYRequest.GetInt("page", 1);

            DataSet ds = MW.BLL.mw_getlistbypage.GetDataSet_NoCache("mw_votesystem", "*", strWhere, "", "N_SysId DESC", "N_SysId", rpt_List.CurrentPage, rpt_List.PageSize, 1);

            rpt_List.RecordCount = Convert.ToInt32(ds.Tables[0].Rows[0][0]);
            JBind.BindList(rpt_List, ds.Tables[1]);
        }
        protected void btnSearch_Click(object sender, EventArgs p1)
        {
            StringBuilder builder = new StringBuilder("makevote.aspx?Search=Yes");
            //builder.Append("&TypeId="+JBind.GetSelectedList(drpTypeId));

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
            else if (p1.CommandName == "lbtnCopy")
            {
                bll.CopyVote(num);
                MessageBox.AlertToUrl(this, "问卷复制成功", "MakeVote.aspx");
            }
        }

        protected void rpt_List_ItemDataBound(object sender, RepeaterItemEventArgs e)
        {
            if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
            {
                DataRowView drv = (DataRowView)e.Item.DataItem;
                object sysid = drv["n_sysid"];
                if (ShowEdit(sysid))
                {
                    ((LinkButton)e.Item.FindControl("lbtnDelete")).Visible  =false ;
                }
            }
        }

        protected bool ShowEdit(object sysid)
        {
            int count = new B_Voteresult().GetRecordCount("n_sysid=" + sysid);
            return count > 0;
        }
    }
}