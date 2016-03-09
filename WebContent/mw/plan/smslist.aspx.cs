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
using MW.BLL;
using MW.Common;

namespace PortalWeb.mw.plan
{
    public partial class smslist : System.Web.UI.Page
    {
        private MW.BLL.B_Sendsms bll = new MW.BLL.B_Sendsms();

        private string strWhere, SearchName;
        protected string Mobile, Begin_Time, End_Time, Operator, KeyWord, rurl, type;
        protected int id, re;

        protected void Page_Load(object sender, EventArgs e)
        {
            SearchName = MW.Common.LYRequest.GetString("SearchName", true);
            Operator = LYRequest.GetString("Operator", "");
            Begin_Time = LYRequest.GetString("Begin_Time", "");
            End_Time = LYRequest.GetString("End_Time", "");
            KeyWord = LYRequest.GetString("KeyWord", "");
            type = LYRequest.GetString("type", "");
            id = LYRequest.GetInt("id", 0);
            re = LYRequest.GetInt("re", 0);
            rurl = LYRequest.GetString("rurl", Server.UrlDecode(Request.Url.ToString()));

            if (type == "reset")
            {
                ResetSMS();
            }
            if (!IsPostBack)
            {
                InitData();
                BindList();
            }
        }
        private void ResetSMS()
        {
#warning BasePage.IsPermissions(0x6c)
            if (!BasePage.IsPermissions(294))
            {
                //MessageBox.JSLoad(sender, "alert('没有权限！!');reloadDlg();");
                Page.ClientScript.RegisterStartupScript(this.GetType(), "", "<script>alert('您没有权限进行重发操作！')</script>");
            }
            else
            {
                var sms = bll.GetModel(id);
                if (sms != null)
                {
                    //BusLogic.SendSMSLogic.SendSMS(sms.SendType, sms.Mobile, sms.SendContent, "0", id);
                    MW.BLL.B_Sendsms.Type = sms.SendType;
                    MW.BLL.B_Sendsms.StrReceiveMobile = sms.Mobile;
                    MW.BLL.B_Sendsms.StrSmsContent = sms.SendContent;
                    MW.BLL.B_Sendsms.SendName = BasePage.UId.ToString();
                    MW.BLL.B_Sendsms.SendSMS();
                    MessageBox.JSLoad(this, "alert('短信重发成功！!');reloadDlg();");

                }
                else
                {
                    MessageBox.JSLoad(this, "alert('此短信信息有误！!');reloadDlg();");
                }
            }
        }
        private void InitData()
        {
            //JBind.BindList(Drp_Province, bll.GetProvince(), "Name", "ID");//省份
            // Drp_Province.Items.Insert(0, new ListItem("请选择", "-1"));

           
            //TextBox_BeginTime.Text = Begin_Time;
            //TextBox_EndTime.Text = End_Time;
            //TextBox_User.Text = Operator;
            //TextBox_KeyWord.Text = KeyWord;
        }
        private void BindList()
        {

            #region __________Where语句__________

            strWhere = "  1=1";
            if (SearchName != "")
            {
                strWhere += " and (Mobile like '%" + SearchName + "%')";
            }
            if (Begin_Time != "")
            {
                strWhere += " and SendTime>='" + Begin_Time + "'";
            }
            if (End_Time != "")
            {
                strWhere += " and SendTime>='" + End_Time + "'";
            }
            if (Operator != "")
            {
                strWhere += " and SendName in (select id from tbusers where account like '%" + Operator + "%')";
            }
            if (KeyWord != "")
            {
                strWhere += " and SendContent like '%" + KeyWord + "%'";
            }
            #endregion

            rpt_List.PageLink = "smslist.aspx?Search=Yesre=1&Operator=" + Server.UrlEncode(Operator) + "&Begin_Time=" + Begin_Time + "&End_Time=" + End_Time + "&KeyWord=" + Server.UrlEncode(KeyWord) + "&SearchName=" + Utils.UrlEncode(SearchName);
            rpt_List.PageSize = 15;
            rpt_List.CurrentPage = LYRequest.GetInt("page", 1);

            DataSet ds = MW.BLL.mw_getlistbypage.GetDataSet_NoCache("mw_sendsms", "*", strWhere, "", "ID DESC", "ID", rpt_List.CurrentPage, rpt_List.PageSize, 1);

            rpt_List.RecordCount = Convert.ToInt32(ds.Tables[0].Rows[0][0]);
            JBind.BindList(rpt_List, ds.Tables[1]);
        }
        protected void btnSearch_Click(object sender, EventArgs p1)
        {
            StringBuilder builder = new StringBuilder("smslist.aspx?Search=Yes");
            //builder.Append("&TypeId="+JBind.GetSelectedList(drpTypeId));
            //builder.Append("&Operator=" + Server.UrlEncode(TextBox_User.Text.Trim()) + "&Begin_Time=" +
            //               TextBox_BeginTime.Text.Trim() + "&End_Time=" + TextBox_EndTime.Text.Trim() + "&KeyWord=" +
            //               Server.UrlEncode(TextBox_KeyWord.Text.Trim()));
         
            base.Response.Redirect(builder.ToString());
        }
        protected void lbtnDeletes_Click(object sender, EventArgs p1)
        {
#warning BasePage.IsPermissions(0x6c)
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
            else
            {
                MessageBox.Alert(this, "没有删除权限!");
            }
        }
        protected void rpt_List_ItemCommand(object source, RepeaterCommandEventArgs p1)
        {
            int num = int.Parse(p1.CommandArgument.ToString());
            if (p1.CommandName == "lbtnDelete")
            {
#warning delte IsPermissions(0x6c)
                if (!BasePage.IsPermissions(0x6c))
                {
                    MessageBox.Alert(this, "没有删除权限!");
                }
                else
                {
                    bll.Delete(num);
                    base.Response.Redirect(HttpContext.Current.Request.Url.PathAndQuery);
                }
            }
        }


        public string GetContext(string str)
        {
            if (str.Length > 60)
            {
                return str.Substring(0, 60) + "...";
            }
            else
            {
                return str;
            }
        }

        protected string GetMsgContent(string strMsgCode)
        {
            string msg = strMsgCode;
            if (msg == "1")
            {
                msg += "|发送成功";
            }
            else
            {
                switch (strMsgCode)
                {
                    case "0":
                        msg += "|帐户格式不正确";
                        break;
                    case "-1":
                        msg += "|服务器拒绝";
                        break;
                    case "-2":
                        msg += "|密钥不正确";
                        break;
                    case "-3":
                        msg += "|密钥已锁定";
                        break;
                    case "-4":
                        msg += "|参数不正确";
                        break;
                    case "-5":
                        msg += "|无此帐户";
                        break;
                }
            }

            return msg;
        }
    }
}