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

namespace PortalWeb.mw.retailknowhow
{
    public partial class SktchapterList : System.Web.UI.Page
    {
        private MW.BLL.B_Sktchapter bll = new MW.BLL.B_Sktchapter();
        private MW.Model.M_Mw_sktchapter model = new MW.Model.M_Mw_sktchapter();
        public int Id = 0;
        private string strWhere, SearchName;

        protected void Page_Load(object sender, EventArgs e)
        {
            Id = MW.Common.LYRequest.GetInt("Id", 0);
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

            strWhere = " CId=" + Id;

            if (SearchName != "")
            {
                strWhere += " and (C_Name like '%" + SearchName + "%')";
            }
            #endregion



            rpt_List.PageLink = "sktchapterlist.aspx?Search=Yes&Id=" + Id + "&SearchName=" + Utils.UrlEncode(SearchName);
            rpt_List.PageSize = 15;
            rpt_List.CurrentPage = LYRequest.GetInt("page", 1);

            DataSet ds = MW.BLL.mw_getlistbypage.GetDataSet_NoCache("mw_sktchapter", "*", strWhere, "", "ID DESC", "ID", rpt_List.CurrentPage, rpt_List.PageSize, 1);

            rpt_List.RecordCount = Convert.ToInt32(ds.Tables[0].Rows[0][0]);
            JBind.BindList(rpt_List, ds.Tables[1]);
        }
        //protected void btnSearch_Click(object sender, EventArgs p1)
        //{
        //    StringBuilder builder = new StringBuilder("sktchapterList.aspx?Search=Yes&Id=" + Id);
        //    //builder.Append("&TypeId="+JBind.GetSelectedList(drpTypeId));

        //    if (!string.IsNullOrEmpty(this.txtSearchName.Text.Trim()))
        //    {
        //        builder.Append("&SearchName=" + this.txtSearchName.Text.Trim());
        //    }
        //    base.Response.Redirect(builder.ToString());
        //}
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

        protected string _Type(int id)
        {
            if (id == 1)
            {
                return "结构图";
            }
            else if (id == 2)
            {
                return "排序图";
            }
            else if (id == 3)
            {
                return "树状图";
            }
            return "无此类型";
        }
    }
}