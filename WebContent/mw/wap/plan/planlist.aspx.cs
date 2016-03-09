using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using MW.Common;

namespace PortalWeb.mw.wap.plan
{
    public partial class planlist : System.Web.UI.Page
    {
        private string typeid;
        protected void Page_Load(object sender, EventArgs e)
        {
            typeid = LYRequest.GetString("typeid");
            if (!IsPostBack)
            {
                LoadInit();
            }
            //BindData();
        }
        private void LoadInit()
        {
            JBind.BindList(dropTypeId, new MW.BLL.B_ecan_domainvalue().GetDrpList("DOMAIN_ID='PROTYPE'"), "DOMAINLABEL", "VALUE");
            //dropTypeId.Items.Insert(0, new ListItem("请选择", ""));

        }
    }
}