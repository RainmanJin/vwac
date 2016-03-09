using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using MW.Common;

namespace PortalWeb.mw.plan
{
    public partial class tmpcodedialog : System.Web.UI.Page
    {
        protected string id = "";
        protected void Page_Load(object sender, EventArgs e)
        {

            if (base.Request["action"] != null && base.Request["action"] == "set")
            {
                SetTmpCode();
            }
            else
            {
                id = LYRequest.GetString("id");
                hideid.Value = MW.Common.LYRequest.GetString("hidCtl");
                hideradio.Value = MW.Common.LYRequest.GetString("txtCtl");
                if (!IsPostBack)
                {
                    InitData();
                    BindList();
                }
            }

          
        }
          private void SetTmpCode()
        {
            var tmpcode = MW.Common.LYRequest.GetString("tmpcode");
            var id = MW.Common.LYRequest.GetString("id");
            bool flag = MW.BLL.B_tech_train_course.inance.UpdateCourseTmpCode(id, tmpcode);
            if (flag)
            {
                Response.Write("0");
            }
            else
            {
                Response.Write("1");
            }
            Response.End();
        }
        private void InitData()
        {
           

        }
        private void BindList()
        {

            StringBuilder builder = new StringBuilder(" ID not in(SELECT tc.ID FROM TECH_TRAIN_COURSE AS tp INNER JOIN mw_tempcode AS tc ON tp.tmpcode = tc.CardNumber WHERE tp.tmpcode IS NOT NULL) ");
            if (!string.IsNullOrEmpty(this.UserName))
            {
                builder.Append(" and (CardNumber like '%" + UserName + "%')");
            }
            if (GroupId > 0)
            {
                builder.Append(string.Format(" and type={0}", this.GroupId));
            }
            rptCustomer.PageLink = string.Format("tmpcodedialog.aspx?hidCtl={0}&txtCtl={1}&UserName={2}&groupid={3}&parentDlg={4}", base.Request.QueryString["hidCtl"], base.Request.QueryString["txtCtl"], UserName, GroupId, parentDlg);
            rptCustomer.PageSize = 7;
            rptCustomer.CurrentPage = LYRequest.GetInt("page", 1);

            DataSet ds = MW.BLL.mw_getlistbypage.GetDataSet_NoCache("mw_tempcode", "ID,CardNumber", builder.ToString(), "", "ID DESC", "ID", rptCustomer.CurrentPage, rptCustomer.PageSize, 1);

            rptCustomer.RecordCount = Convert.ToInt32(ds.Tables[0].Rows[0][0]);
            JBind.BindList(rptCustomer, ds.Tables[1]);
        }
        private int GroupId
        {
            get
            {
                int gid = LYRequest.GetInt("groupid", 0);
               // if (gid > 0) drpGroup.SelectedValue = gid.ToString();
                return gid;
            }
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
                    string text1 = base.Request.QueryString["UserName"];
                    //this.txtSearch.Text = base.Request.QueryString["UserName"];
                }
                return base.Request.QueryString["UserName"];
            }
        }

        protected void btnSearch_Click(object sender, EventArgs p1)
        {
            base.Response.Redirect(string.Format("tmpcodedialog.aspx?hidCtl={0}&txtCtl={1}", base.Request.QueryString["hidCtl"], base.Request.QueryString["txtCtl"]));
        }
    }
}