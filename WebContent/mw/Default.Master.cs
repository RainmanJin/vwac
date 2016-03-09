using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using MW.Common;
using MW.Model;

namespace PortalWeb.mw
{
    public partial class Default : System.Web.UI.MasterPage
    {

        protected void Page_Load(object sender, EventArgs e)
        {
            //if (BaseUi.token == "")
            //{
            //    base.Response.Redirect("/p/login");
            //    return;
            //}
            ////MW.BLL.B_ecan_user.inance.LoadUser("59977a531adc4369830d4c4a3b47ae4f");
            //MW.BLL.B_ecan_user.inance.LoadUser(BaseUi.token); //BaseUi.token
            //if (!IsPostBack)
            //{
            //    loadSkin();
            //    loadData();
            //    //homeUrl = BaseUi.homeurl;
                
            //}

            //为了能够显示页面，注释上述代码
            loadSkin();
            loadData();
        }
        private void loadSkin()
        {
            lnksh1.Href = BaseUi.CssPath +"/site.css";
            lnksh2.Href = BaseUi.CoreCssPath + "/form.css";
            lnksh3.Href = BaseUi.CoreCssPath + "/extremecomponents.css";
            lnksh5.Href=BaseUi.JqueryPath +"/jquery.autocomplete/jquery.autocomplete.css";
            lnksh6.Href = BaseUi.CoreCssPath + "/style.css";
        }
        private void loadData()
        {
            litTopMenu.Text = "";
            litLeftMenu.Text = "";
            loadTopMenu();
            loadLang();
            //loadLeftMenu();
        }

        private string levelCode
        {
            get
            {
                var _lc = MW.Common.JCookie.GetCookie("levelCode");
                return _lc;
            }
        }
        private string sublevelCode
        {
            get
            {
                var _lc = MW.Common.JCookie.GetCookie("sublevelCode");
                return _lc;
            }
        }
        protected string GetDmpurl()
        {
            string url = Configs.GetWebConfigSetting("dmpURL");
            if (url != "")
            {
                return "<li><a href=\""+url+"?token="+BaseUi.token+"\">"+BaseUi.GetLang("i18n.appname.dmp")+"</a></li>";
            }
            return "";
        }
        private string loadTopMenu()
        {
            //Log.SetLog("BaseUi:" + BaseUi.roled + "=" + JSession.Get("roled") + " -uid:" + BaseUi.uid + "=" + JSession.Get("uid"));
            //Log.SetLog("loadLeft roled:" + BaseUi.roled);
            StringBuilder sb = new StringBuilder();
            var list = MW.BLL.B_ecan_app.inance.GetMenus(BaseUi.roled.Split(','),BaseUi.homeurl);
            string litmp = "<li {0}><a href=\"{1}\">{2}</a></li>";
            var pagename = MW.BLL.B_ecan_app.inance.GetlevelCode(LYRequest.GetPageName().ToLower());
            //Log.SetLog(pagename+"=="+LYRequest.GetPageName().ToLower());
            foreach (EcanMenus app in list)
            {
                //if(app.submenu==null||(app.submenu.Count==0&&app.menu.APP_CODE!="home")) continue;
                M_ecan_app mapp = null;
                if (app.menu.FUN_CODE != "")
                {
                    mapp = app.menu;
                }
                else
                {
                    foreach (var sub in app.submenu)
                    {
                        if (sub.submenus != null && sub.submenus.Count > 0)
                        {
                            mapp = sub;
                            break;
                        }
                        if(string.IsNullOrEmpty(sub.FUN_CODE)) {continue;}
                        mapp = sub;
                        break;
                    }
                }

                string url = GetAppUrl(mapp);
                if (pagename == "")
                {
                    pagename = levelCode;
                }
                else
                {
                    JCookie.WriteCookie("levelCode",pagename,0.5);
                }
                
                string active = app.menu.LEVEL_CODE.Equals(pagename) ? "class=\"active\"" : "";
                if(url!="") sb.AppendFormat(litmp, active, url, BaseUi.GetLang(app.menu.APP_NAME));
            }
            litTopMenu.Text = sb.ToString();
            //调用左边
            litLeftMenu.Text=loadLeftMenu(pagename);
            return "";
        }
        
        private string GetAppUrl(M_ecan_app app)
        {
            if (app != null)
            {
                if (string.IsNullOrEmpty(app.FUN_CODE) && app.submenus!=null&&app.submenus.Count > 0)
                {
                    return GetAppUrl(app.submenus[0]);
                }
                else
                {
                    string url = BaseUi.CtxPath + "/techc/" + app.APP_CODE + "/" + app.FUN_CODE;
                    if (app.APP_NAME.Contains("mw.")) url = BaseUi.CtxPath + "/mw/" + app.APP_CODE + "/" + app.FUN_CODE;
                    return url;
                }
            }
            return "";
        }
        private string loadLeftMenu(string levescode)
        {
            StringBuilder sb = new StringBuilder();
            var list = MW.BLL.B_ecan_app.inance.GetMenus(BaseUi.roled.Split(','), BaseUi.homeurl).FirstOrDefault(e => e.menu.LEVEL_CODE == levescode);
            //Log.SetLog("list:" + (list == null) + " =" + levescode);
            if (list == null) list = MW.BLL.B_ecan_app.inance.GetMenus(BaseUi.roled.Split(','), BaseUi.homeurl)[0];
            if (list != null)
            {
                var sublist = list.submenu;
                //Log.SetSucLog("list.submenu:"+sublist.Count);
                var subCode = MW.BLL.B_ecan_app.inance.GetlevelCode(LYRequest.GetPageName(), 2);
                if (subCode == "")
                {
                    subCode = sublevelCode;
                }
                else
                {
                    JCookie.WriteCookie("sublevelCode", subCode, 0.5);
                }
                string litmp = "<li {0}><a href=\"{1}\">{2}</a></li>";
                foreach (M_ecan_app app in sublist)
                {
                    string active = app.LEVEL_CODE.Equals(subCode) ? "class=\"active\"" : "";
                    string url = GetAppUrl(app);
                    //Log.SetSucLog("app.submenus:" + (app.submenus==null));
                    if (app.submenus != null && app.submenus.Count == 0)
                    {
                        //一级
                        sb.AppendFormat(litmp, active, url, BaseUi.GetLang(app.APP_NAME));
                    }
                    else
                    {
                        string txt = "<li class=\"subMenu\"><div>" + BaseUi.GetLang(app.APP_NAME) + "</div><ul class=\"listMenu\">";
                        StringBuilder sbsub = new StringBuilder();
                        foreach (M_ecan_app sub in app.submenus)
                        {
                            active = sub.LEVEL_CODE.Equals(subCode) ? "class=\"active\"" : "";
                            url = BaseUi.CtxPath + "/techc/" + sub.APP_CODE + "/" + sub.FUN_CODE;
                            if (app.APP_NAME.Contains("mw.")) url = BaseUi.CtxPath + "/mw/" + sub.APP_CODE + "/" + sub.FUN_CODE;
                            sbsub.AppendFormat(litmp, active, url, BaseUi.GetLang(sub.APP_NAME));
                        }
                        sb.Append(txt).Append(sbsub.ToString()).Append("</ul></li>");
                    }
                }
               // Log.SetSucLog("left:"+sb.ToString());
                
            }
            return sb.ToString();
        }
        private void loadLang()
        {
            drplang.Attributes.Add("onchange", "changeLang(this.value)");
            var data = new MW.BLL.B_ecan_i18n().GetList("");
            drplang.DataTextField = "NAME";
            drplang.DataValueField = "ID";
            drplang.DataSource = data.Tables[0];
            drplang.DataBind();
            drplang.SelectedValue = BaseUi.lang;
        }
    }
}