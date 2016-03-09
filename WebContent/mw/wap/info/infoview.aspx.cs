using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace PortalWeb.mw.wap.Info
{
    public partial class infoview : System.Web.UI.Page
    {
        protected string title, img, time, conent, fj;
        protected int Id = 0;
        private MW.BLL.B_Article bll = new MW.BLL.B_Article();
        private MW.Model.M_Mw_article model = new MW.Model.M_Mw_article();

        protected void Page_Load(object sender, EventArgs e)
        {
            Id = MW.Common.LYRequest.GetInt("id", 0);
            if (!IsPostBack)
            {
                if (Id > 0)
                {
                    model = bll.GetModel(Id);
                    if (model != null)
                    {
                        title = model.Title;
                        time = model.UpdateTime.ToString();
                        img = WxBase.GetResAbsUrl(model.FilePath);
                        conent = WxBase.ReplacePic(MW.Common.Utils.HtmlDecode(model.Contents));
                        fj = model.fj;
                        base.Title = title;
                    }
                }
            }

        }
    }
}