﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace PortalWeb.mw.wap
{
    public partial class debug : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            base.Response.Write(Session["OAuthAccessTokenOpenId"]);
        }
    }
}