using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

//namespace PortalWeb
//{
    public class BaseUi:System.Web.UI.Page
    {
        /// <summary>
        /// 语言
        /// </summary>
        public static string lang 
        {
            get { 
                var _lang = MW.Common.JCookie.GetCookie("lang");
                if (string.IsNullOrEmpty(_lang)) _lang = "zh_CN";
                return _lang;
            }
        }
        public static string token
        {
            get
            {
                var _token = MW.Common.LYRequest.GetString("token");
                if (string.IsNullOrEmpty(_token))
                {
                    _token = MW.Common.JSession.GetStr("token");
                }
                return _token;
            }
        }
        public static string uid
        {
            get
            {
                var _uid = MW.Common.JSession.GetStr("uid");
                return _uid;
            }
        }
        /// <summary>
        /// 用户权限IDS
        /// </summary>
        public static string roled
        {
            get
            {
                var _uid = MW.Common.JSession.GetStr("roled");
                return _uid;
            }
        }
        
        public static string homeurl
        {
            get
            {
                var _homeurl = MW.Common.JSession.GetStr("homeurl");
                if (string.IsNullOrEmpty(_homeurl)) _homeurl = "student";
                return _homeurl;
            }
        }
      
        public static string GetLang(string key)
        {
            key = key.Replace("_T_:", "");
            return MW.BLL.B_ecan_i18n_properties.inance.GetStrByLang(lang, key);
        }
        /// <summary>
        /// 页面绝对地址
        /// </summary>
        public static string CtxPath
        {
            get { return MW.BasePage.GetWebPath; }
        }
        public static string JqueryPath
        {
            get { return CtxPath + "/platform/jquery"; }
        }
        public static string CoreImgPath
        {
            get { return CtxPath + "/platform/images"; }
        }
        public static string CoreCssPath
        {
            get { return CtxPath + "/platform/css"; }
        }
        public static string CoreJsPath
        {
            get { return CtxPath + "/platform/js"; }
        }
        public static string ImgPath
        {
            get { return CtxPath + "/images"; }
        }
        public static string CssPath
        {
            get { return CtxPath + "/css"; }
        }
        public static string JsPath
        {
            get { return CtxPath + "/js"; }
        }
    }
//}