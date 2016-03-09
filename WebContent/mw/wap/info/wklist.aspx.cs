using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using MW.Common;
using MW.Model;

namespace PortalWeb.mw.wap.Info
{
    public partial class wklist : WapBase
    {
        private MW.BLL.B_Coursechapter bll = new MW.BLL.B_Coursechapter();

        protected void Page_Load(object sender, EventArgs e)
        {
            int page = LYRequest.GetInt("page", 1);
            string typeid = LYRequest.GetString("typeid");
            if (typeid == "") typeid = "wkc";
            if (base.Request["cmd"] != null && base.Request["cmd"] == "list")
            {
                BindData(page, typeid);
            }

        }
        private void BindData(int page, string typeid)
        {
            #region __________Where语句__________

            string strWhere = " 1=1";
            if (typeid != "")
            {
                strWhere += " and TypeId='" + typeid + "'";
            }
            #endregion

            int pagesize = 10;
            DataSet ds = bll.GetListByPage("Id,Title,FilePath,ShareUsers,CreateTime,fj,A4,hits", strWhere, "OderBy desc,id desc", (page - 1) * pagesize, pagesize);
            List<MW.Model.M_Mw_article> list = new List<M_Mw_article>();
            if (ds != null && ds.Tables[0] != null && ds.Tables[0].Rows.Count > 0)
            {
                list = DbTranslate.Translate<M_Mw_article>(ds.Tables[0]);
            }
            int total = bll.GetRecordCount(strWhere);
            base.OutputListData(Serialize.SerializeJson(list), total);
        }
    }
}