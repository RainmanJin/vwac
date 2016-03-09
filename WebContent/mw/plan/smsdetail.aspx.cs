using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using MW;
using MW.Common;


namespace PortalWeb.mw.plan
{
    public partial class smsDetail : System.Web.UI.Page
    {
        protected int Id = 0;
        private MW.BLL.B_Sendsms bll = new MW.BLL.B_Sendsms();
        private MW.Model.M_Mw_sendsms model = new MW.Model.M_Mw_sendsms();
        protected int strNum = 0;

        protected void Page_Load(object sender, EventArgs e)
        {
            Id = MW.Common.LYRequest.GetInt("id", 0);
            //#warning BasePage.IsPermissions(0x6c)
            //            if (!BasePage.IsPermissions(42))
            //            {
            //                MessageBox.JSLoad(sender, "alert('没有权限！!');reloadDlg();");
            //                return;
            //            }
            if (!IsPostBack)
            {
                InitData();
            }
        }
        private void InitData()
        {
            if (Id > 0)
            {//edit
                //model = bll.GetModel(Id);
                //if (model != null)
                //{
                //    txtMobile.Text = model.Mobile;
                //    txtSendContent.Text = model.SendContent;
                //    txtSendType.Text = model.SendType.ToString();
                //    txtSendTime.Text = model.SendTime.ToString();
                //    txtSendName.Text = model.SendName;
                //    txtStatus.Text = model.Status;
                //    txtTimes.Text = model.Times.ToString();
                //}
            }
        }
        protected void btnSave_Click(object sender, EventArgs p1)
        {
            if (Id > 0)
            {
                #region __________Edit__________

                //model = bll.GetModel(Id);
                //model.Mobile = txtMobile.Text;
                //model.SendContent = txtSendContent.Text;
                //model.SendType = 0;
                //model.SendTime = DateTime.Now;
                //model.SendName = BasePage.UId.ToString();
                //if (bll.Update(model))
                //{
                //    //B_operationLog.CreateOperationLog(B_operationLog.operationLogType.Edit, "", "");
                //    MessageBox.JSLoad(sender, "alert('保存成功!');reloadDlg(true);");
                //}

                #endregion
            }
            else
            {
                MW.BLL.B_Sendsms.Type = Convert.ToInt32(ddlTypeList.SelectedValue);
                MW.BLL.B_Sendsms.StrReceiveMobile = txtMobile.Text.Trim();
                string sendStr = txtSendContent.Text.Trim();
                //if (sendStr.IndexOf("{from}") < 0)
                //{
                //    sendStr += " {from}";
                //}
                MW.BLL.B_Sendsms.StrSmsContent = sendStr.Replace("{from}", Configs.GetConfigValue("/configuration/sitename"));
                MW.BLL.B_Sendsms.SendName = BasePage.UId.ToString();
                MW.BLL.B_Sendsms.EventComplete += new EventHandler(ResultMessage);
                //Thread t = new Thread(new ThreadStart(MW.BLL.B_Sendsms.SendSMS));
                //t.Priority = ThreadPriority.Lowest;
                //t.Name = "LY_SendSMS";
                //t.IsBackground = true;
                //t.Start();
                MW.BLL.B_Sendsms.SendSMS();
            }
        }

        protected void ResultMessage(object sender, EventArgs e)
        {
            //Response.Redirect("")
            string msg = "发送成功！";
            if (sender.ToString() != "" && sender.ToString().Substring(0, 3) != "100")
            {
                msg = sender as string;
            }
            MessageBox.JSLoad(this, "alert('发送状态：" + msg + "!');reloadDlg(true);");
        }
    }

  
}