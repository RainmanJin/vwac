using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using MW.BLL;
using MW.Common;
using MW;

namespace PortalWeb.mw.vwtestdrive
{
    public partial class _usersdialog : System.Web.UI.Page
    {
        private MW.BLL.B_ecan_user bll_user = new MW.BLL.B_ecan_user();
        protected void btnAll_Click(object sender, EventArgs p1)
        {
           
            this.InitData();
        }

      

        protected void btnSearch_Click(object sender, EventArgs p1)
        {
            this.InitData();
        }
        protected void lbxUsersBind(int TypeId)
        {
            //this.lbxUsers.DataSource = bll_user.GetList(string.Format("C_Code=1 and Id={0}", TypeId));
            //this.lbxUsers.DataTextField = "NAME";
            //this.lbxUsers.DataValueField = "ID";
            //this.lbxUsers.DataBind();
        }

        private void InitData()
        {
            string where = "ROLE_ID='student'";
            this.lbxUsers.DataSource = bll_user.GetList(where);
            this.lbxUsers.DataValueField = "ID";
            this.lbxUsers.DataTextField = "NAME";
            this.lbxUsers.DataBind();
        }

        protected void Page_Load(object sender, EventArgs p1)
        {
            if (!base.IsPostBack)
            {
                //JBind.BindList((Repeater)repQType, new MW.BLL().GetList("").Tables[0]);//用户组取值
                this.InitData();
                LoadUser();
                hidetxt1.Value = MW.Common.LYRequest.GetString("txtCtl");
                hidevalue1.Value = MW.Common.LYRequest.GetString("hidCtl");
            }
        }

        protected void repQType_ItemCommand(object source, RepeaterCommandEventArgs p1)
        {
            if (string.Compare(p1.CommandName, "lbtnQType") == 0)
            {
                this.lbxUsersBind(int.Parse(p1.CommandArgument.ToString()));
            }
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