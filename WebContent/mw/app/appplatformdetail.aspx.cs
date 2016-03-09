using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using MW;
using MW.BLL;
using MW.Common;

namespace PortalWeb.mw.app
{
    public partial class appplatformdetail : System.Web.UI.Page
    {
        protected int Id = 0;
        private MW.BLL.B_appplatform bll = new MW.BLL.B_appplatform();
        private MW.Model.M_Mw_appplatform model = new MW.Model.M_Mw_appplatform();
        private MW.BLL.B_ecan_domainvalue bl=new B_ecan_domainvalue();

        protected void Page_Load(object sender, EventArgs e)
        {
            Id = MW.Common.LYRequest.GetInt("id", 0);
            if (!IsPostBack)
            {
                InitData();
            }
        }
        private void InitData()
        {
            DataTable dt = new MW.BLL.B_ecan_domainvalue().GetDrpList("DOMAIN_ID='PROTYPE'");
            JBind.BindList(txtproType, dt, "DOMAINLABEL", "VALUE");
            txtproType.Items.Insert(0, "");
            DataTable db = new MW.BLL.B_ecan_domainvalue().GetDrpList("DOMAIN_ID='BRAND'");
            JBind.BindList(txtbrand, db, "DOMAINLABEL", "VALUE");
            txtbrand.Items.Insert(0, "");
            DataTable dc = new MW.BLL.B_ecan_domainvalue().GetDrpList("DOMAIN_ID='STATUS'");
            JBind.BindList(txtstatus, dc, "DOMAINLABEL", "VALUE");
            
            if (Id > 0)
            {//edit
                model = bll.GetModel(Id);
                if (model != null)
                {
                    txtremark.Text = model.remark;
                    txticonURL.Text = model.iconURL;
                    txtname.Text = model.name;
                    txtversion.Text = model.version;
                    txtapkurl.Text = model.apkurl;
                    txtiosurl.Text = model.iosurl;
                    txtbrand.SelectedValue = model.brand;
                    txtproType.SelectedValue = model.proType;
                    txtstatus.SelectedValue = model.status;
                    txtpkgid.Text = model.pkgid;
                }
            }
        }
        protected void btnSave_Click(object sender, EventArgs p1)
        {
            if (Id > 0)
            {
                #region __________Edit__________
                model = bll.GetModel(Id);
                model.remark = txtremark.Text;
                model.iconURL = txticonURL.Text;
                model.name = txtname.Text;
                model.version = txtversion.Text;
                model.apkurl = txtapkurl.Text;
                model.iosurl = txtiosurl.Text;
                model.brand = txtbrand.SelectedValue;
                model.proType = txtproType.SelectedValue;
                model.status = txtstatus.SelectedValue;
                model.pkgid = txtpkgid.Text;
                model.versionCode = model.versionCode + 1;
                if (bll.Update(model))
                {
                    //B_operationLog.CreateOperationLog(B_operationLog.operationLogType.Edit, "", "");
                    MessageBox.AlertToUrl(this, "保存成功!", "appplatform.aspx");
                }
                #endregion
            }
            else
            {
                #region __________Add__________
                model.remark = txtremark.Text;
                model.iconURL = txticonURL.Text;
                model.name = txtname.Text;
                model.version = txtversion.Text;
                model.apkurl = txtapkurl.Text;
                model.iosurl = txtiosurl.Text;
                model.brand = txtbrand.SelectedValue;
                model.proType = txtproType.SelectedValue;
                model.status = txtstatus.SelectedValue;
                model.pkgid =txtpkgid.Text;
                model.versionCode = 0;
                if (bll.Add(model))
                {
                    //B_operationLog.CreateOperationLog(B_operationLog.operationLogType.Add, "", "");
                    MessageBox.AlertToUrl(this, "新增保存成功!", "appplatform.aspx");
                }
                #endregion
            }
        }
    }
}