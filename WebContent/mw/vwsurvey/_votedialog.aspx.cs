using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using MW.Common;

namespace Plugin.VWSurvey.Admini.VWSurvey
{
    public partial class _VoteDialog : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
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

            StringBuilder builder = new StringBuilder(" 1=1  ");
            if (!string.IsNullOrEmpty(this.UserName))
            {
                builder.Append(string.Format(" and C_Title like '%{0}%'", this.UserName));
            }
            rptCustomer.PageLink = string.Format(MW.BasePage.GetAdminPath + "vwsurvey/_VoteDialog.aspx?hidCtl={0}&txtCtl={1}&UserName={2}&parentDlg={3}", base.Request.QueryString["hidCtl"], base.Request.QueryString["txtCtl"], UserName, parentDlg);
            rptCustomer.PageSize = 10;
            rptCustomer.CurrentPage = LYRequest.GetInt("page", 1);

            DataSet ds = MW.BLL.mw_getlistbypage.GetDataSet_NoCache("mw_votesystem", "N_SysId,C_Title", builder.ToString(), "", "N_SysId DESC", "N_SysId", rptCustomer.CurrentPage, rptCustomer.PageSize, 1);

            rptCustomer.RecordCount = Convert.ToInt32(ds.Tables[0].Rows[0][0]);
            JBind.BindList(rptCustomer, ds.Tables[1]);
        }

        private string parentDlg
        {
            get
            {
                return base.Request.QueryString["parentDlg"];
                ;
            }
        }
        private string UserName
        {
            get
            {
                if (!string.IsNullOrEmpty(base.Request.QueryString["UserName"]))
                {
                   // string text1 = base.Request.QueryString["UserName"];
                    this.txtSearch.Text = base.Request.QueryString["UserName"];
                }
                return base.Request.QueryString["UserName"];
            }
        }

        protected void btnSearch_Click(object sender, EventArgs p1)
        {
            base.Response.Redirect(string.Format("_VoteDialog.aspx?hidCtl={0}&txtCtl={1}&UserName={2}&parentDlg={3}", base.Request.QueryString["hidCtl"], base.Request.QueryString["txtCtl"], this.txtSearch.Text, parentDlg));
        }
    }
}