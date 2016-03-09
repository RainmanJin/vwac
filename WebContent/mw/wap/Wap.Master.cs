using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace MW.wap
{
    public partial class Wap : System.Web.UI.MasterPage
    {
        private string abspath = BasePage.GetWebPath;
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                lnk.Href = abspath + "/mw/wap/css/wap.css";
            }
        }
    }
}