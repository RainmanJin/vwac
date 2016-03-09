﻿using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using MW.Common;

namespace PortalWeb.mw.vwtestdrive
{
    public partial class _userdialog : System.Web.UI.Page
    {

        private MW.BLL.B_ecan_user bll_user = new MW.BLL.B_ecan_user();
        protected void Page_Load(object sender, EventArgs e)
        {
            StringBuilder builder = new StringBuilder(" ROLE_ID='teacher'");
            hideid.Value = MW.Common.LYRequest.GetString("hidCtl");
            hideradio.Value = MW.Common.LYRequest.GetString("txtCtl");
            if (!IsPostBack)
            {
                InitData();
                BindList();
                LoadUser();
            }
        }
        private void InitData()
        {

            string where = "ROLE_ID='teacher'";
            this.lbxUsers.DataSource = bll_user.GetList(where);
            this.lbxUsers.DataValueField = "ID";
            this.lbxUsers.DataTextField = "NAME";
            this.lbxUsers.DataBind();
        }
        private void BindList()
        {

            StringBuilder builder = new StringBuilder(" ROLE_ID='teacher'");
            if (!string.IsNullOrEmpty(this.C_Teacher))
            {
                builder.Append(string.Format(" and NAME like '%{0}%'", this.C_Teacher));
            }
            if (GroupId > 0)
            {
                builder.Append(string.Format(" and ID={0}", this.GroupId));
            }

            //rptCustomer.PageLink = string.Format("_userdialog.aspx?hidCtl={0}&txtCtl={1}&UserName={2}&groupid={3}&parentDlg={4}", base.Request.QueryString["hidCtl"], base.Request.QueryString["txtCtl"], C_Teacher, GroupId, parentDlg);
            //rptCustomer.PageSize = 5;
            //rptCustomer.CurrentPage = LYRequest.GetInt("page", 1);

            //DataSet ds = MW.BLL.mw_getlistbypage.GetDataSet_NoCache("ECAN_USER", "ID,NAME", builder.ToString(), "", "ID DESC", "ID", rptCustomer.CurrentPage, rptCustomer.PageSize, 1);

            //rptCustomer.RecordCount = Convert.ToInt32(ds.Tables[0].Rows[0][0]);
            //JBind.BindList((Repeater)rptCustomer, ds.Tables[1]);
        }
        private int GroupId
        {
            get
            {
                int gid = LYRequest.GetInt("id", 0);

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
        private string C_Teacher
        {
            get
            {
                if (!string.IsNullOrEmpty(base.Request.QueryString["C_Teacher"]))
                {
                    string text1 = base.Request.QueryString["C_Teacher"];

                }
                return base.Request.QueryString["C_Teacher"];
            }
        }

        protected void btnSearch_Click(object sender, EventArgs p1)
        {
            base.Response.Redirect(string.Format("_userdialog.aspx?hidCtl={0}&txtCtl={1}&UserName={2}&groupid={3}&parentDlg={4}", base.Request.QueryString["hidCtl"], base.Request.QueryString["txtCtl"], parentDlg));
        }
        private void LoadUser()
        {
            if (UserIds != "" && UserIds.Trim(',') != "")
            {
                string[] Uids = UserIds.Trim(',').Split(',');
                //string[] Unames = Utils.SplitString(BLL.B_TBUsers.GetUserName(UserIds),",",Uids.Length);
                //int i = 0;
                foreach (string uid in Uids)
                {
                    ListItem item = new ListItem();
                    item.Text = bll_user.GetUserName(uid);
                    item.Value = uid;
                    if (lbxUsersSure.Items.FindByValue(uid) == null) lbxUsersSure.Items.Add(item);
                    if (lbxUsers.Items.FindByValue(uid) != null) lbxUsers.Items.Remove(item);
                    // i++;
                }
            }
        }
        private string UserIds
        {
            get { return LYRequest.GetString("uids"); }
        }
    }
}