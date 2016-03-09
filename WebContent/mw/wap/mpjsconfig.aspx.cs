using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Senparc.Weixin.MP.CommonAPIs;

namespace PortalWeb.mw.wap
{
    public partial class mpjsconfig : System.Web.UI.Page
    {
        protected string appId = "", timestamp = "", nonceStr = "", signature = "", jsapiTicket = "", url = "";
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                appId = WxBase.AppId;
                timestamp = GetTimeSpan().ToString();
                nonceStr = CreatenNonce_str();
                jsapiTicket = JsApiTicketContainer.TryGetTicket(WxBase.AppId, WxBase.Secret);
                if (HttpContext.Current.Request.UrlReferrer != null)
                    url = HttpContext.Current.Request.UrlReferrer.ToString();
                else
                {
                    url = HttpContext.Current.Request.Url.ToString();
                }
                // 这里参数的顺序要按照 key 值 ASCII 码升序排序  
                string rawstring = "jsapi_ticket=" + jsapiTicket + "&noncestr=" + nonceStr + "&timestamp=" + timestamp + "&url=" + url + "";

                signature = GetSignature(jsapiTicket,nonceStr,timestamp,url);
            }
        }
        private string[] strs = new string[]
                                 {
                                  "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
                                  "A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"
                                 };

        private string CreatenNonce_str()
        {
            Random r = new Random();
            var sb = new StringBuilder();
            var length = strs.Length;
            for (int i = 0; i < 15; i++)
            {
                sb.Append(strs[r.Next(length - 1)]);
            }
            return sb.ToString();
        }
        
        private long GetTimeSpan()
        {
            DateTime time = new DateTime(0x7b2, 1, 1, 0, 0, 0, 0);
            TimeSpan span = (TimeSpan)(DateTime.UtcNow - time);
            return (long)Math.Floor(span.TotalSeconds);
        }
        private string GetSignature(string jsapi_ticket, string noncestr, string timestamp, string url)
        {
            var string1Builder = new StringBuilder();
            string1Builder.Append("jsapi_ticket=").Append(jsapi_ticket).Append("&")
                          .Append("noncestr=").Append(noncestr).Append("&")
                          .Append("timestamp=").Append(timestamp).Append("&")
                          .Append("url=").Append(url.IndexOf("#") >= 0 ? url.Substring(0, url.IndexOf("#")) : url);
            string orgStr = string1Builder.ToString();
            var sha1 = new SHA1Managed();
            var sha1bytes = System.Text.Encoding.GetEncoding("UTF-8").GetBytes(orgStr); 
             byte[] resultHash = sha1.ComputeHash(sha1bytes); 
            string sha1String = BitConverter.ToString(resultHash).ToLower(); 
             sha1String = sha1String.Replace("-", ""); 
             return sha1String; 
        }
        private string getSignature(string rawstring)
        {
            //计算签名
            string Signature = System.Web.Security.FormsAuthentication.HashPasswordForStoringInConfigFile(
                rawstring, System.Web.Configuration.FormsAuthPasswordFormat.SHA1.ToString());

            if (Signature != null)
            {
                return Signature.ToLower();
            }
            return "";
        }
       
    }
}