using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace PortalWeb.mw
{
    public partial class test : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            string token = MW.Common.LYRequest.GetString("token");
            Response.Write(token);
            if (!IsPostBack)
            {
                // MW.BLL.B_ecan_user.inance.LoadUser(token);
                Response.Write("cooke:" + MW.Common.JCookie.GetCookie("testcooke"));
                MW.Common.JCookie.WriteCookie("testcooke2", "2222", 0.5);
            }
            
        }
    }
}