using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Web;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using MW;
using MW.BLL;
using MW.Common;

namespace PortalWeb.mw.app
{
    public partial class appplatform : System.Web.UI.Page
    {

        private MW.BLL.B_appplatform bll = new MW.BLL.B_appplatform();

        private string strWhere, SearchName,brand,protype;

        protected void Page_Load(object sender, EventArgs e)
        {
            SearchName = MW.Common.LYRequest.GetString("SearchName", true);
            brand = MW.Common.LYRequest.GetString("txtbrand");
            protype = MW.Common.LYRequest.GetString("txtproType");
            
            if (!IsPostBack)
            {
                InitData();
                BindList();
            }
        }
        private void InitData()
        {
            DataTable dt = new MW.BLL.B_ecan_domainvalue().GetDrpList("DOMAIN_ID='PROTYPE'");
            JBind.BindList(txtproType, dt, "DOMAINLABEL", "VALUE");
            txtproType.Items.Insert(0,"");
            DataTable db = new MW.BLL.B_ecan_domainvalue().GetDrpList("DOMAIN_ID='BRAND'");
            JBind.BindList(txtbrand, db, "DOMAINLABEL", "VALUE");
            txtbrand.Items.Insert(0, "");

            txtSearchName.Text = SearchName;
            txtbrand.SelectedValue = brand;
            txtproType.SelectedValue = protype;

        }
        private void BindList()
        {

            #region __________Where语句__________

            strWhere = "  1=1";
            if (SearchName != "")
            {
                strWhere += " and (name like '%" + SearchName + "%')";
            }
            if (brand != "")
            {
                strWhere += " and (brand='" + brand + "')";
            }
            if (protype != "")
            {
                strWhere += " and (protype='" + protype + "')";
            }
            #endregion

            rpt_List.PageLink = "appplatform.aspx?Search=Yes&SearchName=" + Utils.UrlEncode(SearchName) + "&brand=" + Utils.UrlEncode(brand) + "&protype=" + Utils.UrlEncode(protype);
            rpt_List.PageSize = 20;
            rpt_List.CurrentPage = LYRequest.GetInt("page", 1);

            DataSet ds = MW.BLL.mw_getlistbypage.GetDataSet_NoCache("mw_appplatform", "*", strWhere, "", "ID DESC", "ID", rpt_List.CurrentPage, rpt_List.PageSize, 1);

            rpt_List.RecordCount = Convert.ToInt32(ds.Tables[0].Rows[0][0]);
            JBind.BindList(rpt_List, ds.Tables[1]);
        }
        protected void btnSearch_Click(object sender, EventArgs p1)
        {
            StringBuilder builder = new StringBuilder("appplatform.aspx?Search=Yes");

            builder.Append("&brand=" + JBind.GetSelectedList(txtbrand));
            builder.Append("&protype=" + JBind.GetSelectedList(txtproType));
            if (txtSearchName.Text != "")
            {
                builder.Append("&SearchName=" + Utils.UrlEncode(txtSearchName.Text));
            }
         
            base.Response.Redirect(builder.ToString());
        }
        protected void lbtnDeletes_Click(object sender, EventArgs p1)
        {
            if (BasePage.IsPermissions(0x6c))
            {
                //int num = 0;
                foreach (RepeaterItem item in this.rpt_List.Items)
                {
                    HtmlInputCheckBox box = (HtmlInputCheckBox)item.FindControl("cheId");
                    if (box.Checked)
                    {
                        bll.Delete(int.Parse(box.Value));
                        // num++;
                    }
                }
                base.Response.Redirect(HttpContext.Current.Request.Url.PathAndQuery);
            }
        
        }
        protected void rpt_List_ItemCommand(object source, RepeaterCommandEventArgs p1)
        {
            int num = int.Parse(p1.CommandArgument.ToString());
            if (p1.CommandName == "lbtnDelete")
            {
        
                    bll.Delete(num);
                    base.Response.Redirect(HttpContext.Current.Request.Url.PathAndQuery);
            }
        }
        protected string GetType(object n_type)
        {
            if (n_type != null && n_type.ToString() != "0")
            {
                var model = new MW.BLL.B_ecan_domainvalue().GetModelAoe(n_type.ToString());
                if (model != null)
                {
                    var text = model.DOMAINLABEL.Replace("_T_:", "");
                    //if (text.StartsWith("i18n.")) 
                    text = B_ecan_i18n_properties.inance.GetStrByLang(BaseUi.lang, text);
                    return text;
                }
                
            }
        
            return "";
        }
    }
}