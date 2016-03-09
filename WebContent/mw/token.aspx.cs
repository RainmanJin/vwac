using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace PortalWeb.mw
{
    public partial class token : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            Session.Clear();
            string token = MW.Common.LYRequest.GetString("token");
            MW.Common.JCookie.WriteCookie("token", token, 0.5);
            if (token != "")
            {
                MW.BLL.B_ecan_user.inance.LoadUser(token);
            }
        }
    }
}