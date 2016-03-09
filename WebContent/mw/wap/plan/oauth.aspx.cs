using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using MW.Common;

namespace PortalWeb.mw.wap.plan
{
    public partial class oauth : WapBase
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (CurrentUser != null)
            {
                base.Response.Redirect("planlist.aspx");
                return;
            }
            if (base.Request["cmd"] != null && base.Request["cmd"] == "reg")
            {
                Reg();
            }
            if (!IsPostBack)
            {
                LoadInit();
            }

        }
        private void LoadInit()
        {
            JBind.BindList(drpDW, new MW.BLL.B_ecan_domainvalue().GetDrpList("DOMAIN_ID='COMPANY'"), "DOMAINLABEL", "VALUE");
            JBind.BindList(dropAreaId, new MW.BLL.B_ecan_domainvalue().GetDrpList("DOMAIN_ID='POSITION'"), "DOMAINLABEL", "VALUE");
            drpDW.Items.Insert(0, new ListItem("请选择", "0"));
            dropAreaId.Items.Insert(0, new ListItem("请选择", "0"));
        }
        private void Reg()
        {
            var name = MW.Common.LYRequest.GetString("name");
            var mobile = MW.Common.LYRequest.GetString("mobile");
            var email = MW.Common.LYRequest.GetString("email");
            var area = MW.Common.LYRequest.GetString("area");
            var code = MW.Common.LYRequest.GetString("code");
            var dw = MW.Common.LYRequest.GetString("dw");
            //认证码检测
            bool flag = MW.BLL.B_Tempcode.VaildCardNumber(code);
            if (!flag)
            {
                base.Output(WapBase.MessageStatus.failure, "verification", "");
            }
            else
            {
                var model = MW.BLL.B_ecan_user.inance.GetModelByEmailMobile(email, mobile);
                if (model != null)
                {
                    flag=MW.BLL.B_ecan_user.inance.UpdateUserWX(model.ID, "", base.Openid);
                }
                else
                {
                    model = new MW.Model.M_ecan_user();
                    model.ID = System.Guid.NewGuid().ToString().ToLower().Replace("-", "");
                    model.NAME = name;
                    model.MOBILE = mobile;
                    model.COMPANY=dw;
                    model.POSITION = area;
                    model.EMAIL = email;
                    model.STATUS = "0";
                    model.Openid = Openid;
                    model.ROLE_ID = "student";
                    model.CREATE_TIME = DateTime.Now;
                    model.LAST_TIME = DateTime.Now;
                    flag = new MW.BLL.B_ecan_user().Add(model);
                }
                
                if (flag)
                {
                    base.Output(WapBase.MessageStatus.success, "", base.Request["ReturnUrl"] != null ? base.Request["ReturnUrl"] : "");
                }
                else
                {
                    base.Output(WapBase.MessageStatus.failure, "", "");
                }
            }
        }
    }
}