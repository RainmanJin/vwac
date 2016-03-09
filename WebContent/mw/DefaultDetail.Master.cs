using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using MW.Common;

namespace PortalWeb.mw
{
    public partial class DefaultDetail : System.Web.UI.MasterPage
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                if (BaseUi.token == "" || JSession.GetStr("login") == "")
                {
                    base.Response.Redirect("/p/login");
                    return;
                }
                lnksh1.Href = BaseUi.CoreCssPath + "/style.css";
            }
        }
    }
}