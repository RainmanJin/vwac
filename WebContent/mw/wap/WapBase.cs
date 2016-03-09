using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using MW;
using MW.BLL;
using MW.Model;

namespace PortalWeb.mw.wap
{
    public class WapBase : System.Web.UI.Page
    {
        public enum MessageStatus
        {
            success,
            failure
        }

        protected override void OnPreLoad(EventArgs e)
        {
            //bool iswx = Senparc.Weixin.MP.HttpUtility.RequestUtility.IsWeixinClientRequest(base.Context);
            //if (!iswx&&WxBase.IsTest=="0")
            //{
            //    Output("请在微信中打开！");
            //}
            if (WxBase.IsTest == "1")
            {
                MW.Common.JCookie.WriteCookie("OAuthAccessTokenOpenId", "1111111111", 1);
                //Session["OAuthAccessTokenOpenId"] = "1111111111";
            }
        }
        protected override void OnLoadComplete(EventArgs e)
        {
            //用户登陆检测
            var openid =MW.Common.JCookie.GetCookie("OAuthAccessTokenOpenId");
            if (WxBase.IsTest == "1") openid = "1111111111";
            if (openid == null || string.IsNullOrEmpty(openid))
            {
                Output("您是否很久未使用了，请重新在微信中打开！");
                return;
            }
            if (this.Page.Request.Url.LocalPath.ToLower().IndexOf("/info") == -1)
            {
                var model = new MW.BLL.B_ecan_user().GetModelByOpenid(openid.ToString());
                if (model != null)
                {
                    Session["_account"] = model;
                }
                else
                {
                    //提示用户去验证
                    if (this.Page.Request.Url.LocalPath.ToLower().IndexOf("/oauth") == -1)
                    {
                        this.Page.Response.Redirect(string.Format(BasePage.GetWebPath + "/mw/wap/plan/oauth.aspx?ReturnUrl={0}",
                                                                  this.Page.Request.RawUrl.ToString()));
                    }
                }
            }
        }
        /// <summary>
        /// 当前用户
        /// </summary>
        protected MW.Model.M_ecan_user CurrentUser
        {
            get
            {
                var obj = Session["_account"];
                if (obj == null)
                {
                    var openid = MW.Common.JCookie.GetCookie("OAuthAccessTokenOpenId");
                    if (openid == null || string.IsNullOrEmpty(openid))
                    {
                        return new M_ecan_user();
                    }
                    var model = new MW.BLL.B_ecan_user().GetModelByOpenid(openid.ToString());
                    if (model != null)
                    {
                        Session["_account"] = model;
                    }
                }
                var um = new M_ecan_user();
                try
                {
                    um = (MW.Model.M_ecan_user) Session["_account"];
                }
                catch// (Exception)
                {
                    
                    //throw;
                }
                return um;
            }
        }
        /// <summary>
        /// 用户Openid
        /// </summary>
        protected string Openid { get { return MW.Common.JCookie.GetCookie("OAuthAccessTokenOpenId").ToString(); } }
        protected void Output(object data)
        {
            this.Controls.Clear();
            base.Response.Write(data);
            base.Response.End();
        }
        protected void Output(MessageStatus status, string error, string msg = "")
        {
            this.Controls.Clear();
            base.Response.Write("{" + string.Format("\"status\":\"{0}\",\"error\":\"{1}\",\"message\":\"{2}\"", status.ToString(), error, msg) + "}");
            base.Response.End();
        }

        protected void OutputMsg(string error, string msg)
        {
            this.Controls.Clear();
            base.Response.Write("{" + string.Format("\"error\":\"{0}\",\"message\":\"{1}\"", error, msg) + "}");
            base.Response.End();
        }
        protected void OutputListData(string contect, int total, string page = "")
        {
            base.Controls.Clear();
            base.Response.Write(string.Format("{{\"data\":{0},\"pagination\":\"{1}\",\"total\":\"{2}\"}}", contect, page, total));
            base.Response.End();
        }
    }
}