using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Senparc.Weixin;
using Senparc.Weixin.MP.AdvancedAPIs;

namespace PortalWeb.mw.wap
{
    /// <summary>
    /// OAuth2 的摘要说明
    /// </summary>
    public class OAuth2 : IHttpHandler, System.Web.SessionState.IRequiresSessionState
    {

        public void ProcessRequest(HttpContext context)
        {
            context.Response.ContentType = "text/plain";
            string code = MW.Common.LYRequest.GetString("code");
            string state =MW.Common.LYRequest.GetString("state");
            if (code != "")
            {
                var result = OAuth.GetAccessToken(WxBase.AppId, WxBase.Secret, code);
                if (result.errcode != ReturnCode.请求成功)
                {
                    context.Response.Write("错误：" + result.errmsg);
                    return;
                }

                //下面2个数据也可以自己封装成一个类，储存在数据库中（建议结合缓存）
                //如果可以确保安全，可以将access_token存入用户的cookie中，每一个人的access_token是不一样的
                context.Session["OAuthAccessTokenStartTime"] = DateTime.Now;
                MW.Common.JCookie.WriteCookie("OAuthAccessTokenOpenId", result.openid, 1);
                //context.Session["OAuthAccessToken"] = result;
                //context.Session["OAuthAccessTokenOpenId"] = result.openid;
                //因为这里还不确定用户是否关注本微信，所以只能试探性地获取一下
                //OAuthUserInfo userInfo = null;
                //try
                //{
                //    //已关注，可以得到详细信息
                //    userInfo = OAuth.GetUserInfo(result.access_token, result.openid);
                //    ViewData["ByBase"] = true;
                //    return View("UserInfoCallback", userInfo);
                //}
                //catch (ErrorJsonResultException ex)
                //{
                //    //未关注，只能授权，无法得到详细信息
                //    //这里的 ex.JsonResult 可能为："{\"errcode\":40003,\"errmsg\":\"invalid openid\"}"
                //    return Content("用户已授权，授权Token：" + result);
                //}
                string url = geturl(state);
                context.Response.Redirect(url);
            }
            else
            {
                context.Response.Write("您拒绝了授权！无法进行身份验证，请允许授权应用！");
            }

        }
        private string geturl(string type)
        {
            string url = "debug.aspx";
            string[] arr = type.Split('_');
            switch (arr[0])
            {
                case "plan"://培训计划
                    if (arr.Length > 1)
                    {
                        url = "plan/planview.aspx?type=0&id=" + arr[1];
                    }
                    else
                    {
                        url = "plan/planlist.aspx";
                    }
                    break;
                case "myplan"://我的培训计划
                    url = "plan/myplan.aspx";
                    break;
                case "oauth"://身份认证
                    url = "plan/oauth.aspx";
                    break;
                case "applyplan"://报名
                    url = "plan/applyplan.aspx";
                    break;
                case "news"://新闻
                case "info":
                    if (arr.Length > 1)
                    {
                        url = "info/infoview.aspx?typeid=wxxw&id=" + arr[1];
                    }
                    else
                    {
                        url = "info/infolist.aspx?typeid=wxxw";
                    }
                    break;

                case "wk"://微课程
                    if (arr.Length > 1)
                    {
                        url = "info/wkview.aspx?id=" + arr[1];
                    }
                    else
                    {
                        url = "info/wklist.aspx";
                    }
                    break;
            }
            return url;
        }
        public bool IsReusable
        {
            get
            {
                return false;
            }
        }
    }
}