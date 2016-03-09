using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace PortalWeb.mw.wap.Info
{
    public partial class wkview : System.Web.UI.Page
    {
        protected string title, img, time, conent, url;
        protected int Id = 0;
        private MW.BLL.B_Coursechapter bll = new MW.BLL.B_Coursechapter();
        private MW.Model.M_Mw_coursechapter model = new MW.Model.M_Mw_coursechapter();

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
                        string zip = model.A3;
                        if (zip != "" && zip.EndsWith(".zip"))
                        {
                            bool flag = MW.Common.Utils.DecompressEx(MW.Common.Utils.GetMapPath(zip));
                            if (flag)
                            {
                                url = zip.Replace(".zip", "");
                                Response.Redirect(url);
                                Response.End();
                                return;
                            }
                        }
                        title = model.Title;
                        time = model.CreateTime.ToString();
                        img = WxBase.GetResAbsUrl(model.A4);
                        conent = WxBase.ReplacePic(MW.Common.Utils.HtmlDecode(model.Content));
                        base.Title = title;
                    }
                }
            }

        }
    }
}