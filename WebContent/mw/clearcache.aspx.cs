using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace PortalWeb.mw
{
    public partial class clearcache : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            int type = MW.Common.LYRequest.GetInt("type", 0);
            string xmlpath = MW.Common.LYRequest.GetString("path");
            if (type == 0)
            {
                MW.Common.JCache.Clear();
            }
            else
            {
                if (xmlpath == "")
                {
                    MW.Common.JCache.Remove_Contains("BLL_");
                }
                else
                {
                    MW.Common.JCache.Remove(xmlpath);
                }
            }
            base.Response.Write("清除缓存成功！");
        }
    }
}