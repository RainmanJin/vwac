using System;
using System.Collections.Generic;
using System.Linq;
using System.Text.RegularExpressions;
using System.Web;
using MW;

namespace PortalWeb.mw.wap
{
    public class WxBase : WapBase
    {
        public static string AppId
        {
            get { return MW.Common.Configs.GetConfigValue("/configuration/appid"); }
        }
        public static string Secret
        {
            get { return MW.Common.Configs.GetConfigValue("/configuration/secret"); }
        }
        public static string IsTest
        {
            get { return MW.Common.Configs.GetConfigValue("/configuration/istest"); }
        }
        /// <summary>
        /// 域名不带HTTP
        /// </summary>
        public static string SiteDomain
        {
            get { return MW.Common.Configs.GetConfigValue("/configuration/domain"); }
        }
        public static string SiteUrl
        {
            get { return MW.Common.Configs.GetConfigValue("/configuration/url"); }
        }
        public static string GetResAbsUrl(string url)
        {
            if (string.IsNullOrEmpty(url) || url == "") return "";
            if (url.StartsWith("http://", StringComparison.OrdinalIgnoreCase)) return url;
            if (!url.Contains(BasePage.GetWebPath)) url = BasePage.GetWebPath + url;
            return SiteUrl + url;
        }
        /// <summary>
        /// 查找img对象并实现替换
        /// </summary>
        /// <param name="content"></param>
        /// <returns></returns>
        public static string ReplacePic(string content)
        {
            if (content == "") return "";
            if (content.Contains("img"))
            {
                //<img.+src=\"?(.+\.(jpg|gif|bmp|bnp|png))\"?.+>
                Regex regex = new Regex("<img [^>]*src=['\"](?<img>([^'\"]+))[^>]*>", RegexOptions.Compiled | RegexOptions.IgnoreCase);
                if (regex.IsMatch(content))
                {
                    string tmp = content;
                    MatchCollection mc = regex.Matches(content);
                    foreach (Match match in mc)
                    {
                        string img = match.Groups["img"].Value;
                        if (!img.Contains("http://") && !img.Contains("https://"))
                        {
                            if (img.Contains(BasePage.GetWebPath))
                            {
                                tmp = tmp.Replace(img, SiteUrl + img);
                            }
                            else
                            {
                                tmp = tmp.Replace(img, SiteUrl + BasePage.GetWebPath + img);
                            }
                        }

                    }
                    return tmp;
                }
            }

            return content;
        }
    }
}