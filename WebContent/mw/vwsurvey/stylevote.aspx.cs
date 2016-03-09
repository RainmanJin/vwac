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

namespace Plugin.VWSurvey.Admini.VWSurvey
{
    public partial class StyleVote : System.Web.UI.Page
    {

        private MW.BLL.B_Votesystem bll = new MW.BLL.B_Votesystem();

        private string strWhere, SearchName;

        protected void Page_Load(object sender, EventArgs e)
        {
            SearchName = MW.Common.LYRequest.GetString("SearchName", true);

            if (!IsPostBack)
            {

                BindList();
            }
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

            rpt_List.PageLink = "StyleVote.aspx?Search=Yes&SearchName=" + Utils.UrlEncode(SearchName);
            rpt_List.PageSize = 20;
            rpt_List.CurrentPage = LYRequest.GetInt("page", 1);

            DataSet ds = MW.BLL.mw_getlistbypage.GetDataSet_NoCache("mw_votesystem", "*", strWhere, "", "N_SysId DESC", "N_SysId", rpt_List.CurrentPage, rpt_List.PageSize, 1);

            rpt_List.RecordCount = Convert.ToInt32(ds.Tables[0].Rows[0][0]);
            JBind.BindList(rpt_List, ds.Tables[1]);
        }
    }
}