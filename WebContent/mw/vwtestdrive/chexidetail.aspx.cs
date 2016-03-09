using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using MW.BLL;
using MW.Common;

namespace PortalWeb.mw.vwtestdrive
{
    public partial class chexidetail : System.Web.UI.Page
    {
        
        private B_ecan_domainvalue bll = new B_ecan_domainvalue();
        protected void btnAll_Click(object sender, EventArgs p1)
        {
            this.InitData();
        }
        protected void btnSearch_Click(object sender, EventArgs p1)
        {
            this.InitData();
        }
        protected void lbxUsersBind(string TypeId)
        {
            this.lbxUsers.DataSource = bll.GetList(string.Format("ChexiId={0}", TypeId));
            this.lbxUsers.DataTextField = "DOMAINLABEL";
            this.lbxUsers.DataValueField = "Id";
            this.lbxUsers.DataBind();
        }

        private void InitData()
        {
            string where = "DOMAIN_ID='CX'";
         
            this.lbxUsers.DataSource = bll.GetDrpList(where);
            this.lbxUsers.DataValueField = "Id";
            this.lbxUsers.DataTextField = "DOMAINLABEL";
            this.lbxUsers.DataBind();
        }

        protected void Page_Load(object sender, EventArgs p1)
        {

            
            if (!base.IsPostBack)
            {
                this.InitData();
                LoadUser();
                
                ;
                hidetxt.Value = MW.Common.LYRequest.GetString("txtCtl");
                hidevalue.Value = MW.Common.LYRequest.GetString("hidCtl");
            }
        }

        protected void repQType_ItemCommand(object source, RepeaterCommandEventArgs p1)
        {
            if (string.Compare(p1.CommandName, "lbtnQType") == 0)
            {
                this.lbxUsersBind(p1.CommandArgument.ToString());
            }
        }
        private void LoadUser()
        {
            if (CIds != "" && CIds.Trim(',') != "")
            {
                string[] Uids = CIds.Trim(',').Split(',');
                //int i = 0;
                foreach (string uid in Uids)
                {
                    ListItem item = new ListItem();
                    //item.Text = bll.GetString(Utils.StrToInt(uid, 0), false);
                    item.Text = "";
                    item.Value = uid;
                    if (lbxUsersSure.Items.FindByValue(uid) == null) lbxUsersSure.Items.Add(item);
                    if (lbxUsers.Items.FindByValue(uid) != null) lbxUsers.Items.Remove(item);
                    // i++;
                }
            }
        }
        private string CIds
        {
            get { return LYRequest.GetString("cids"); }
        }
        private int PageId
        {
            get { return LYRequest.GetInt("pageid", 1); }
        }
    }
}