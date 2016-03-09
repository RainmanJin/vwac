using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using MW.Common;

namespace PortalWeb.mw.retailknowhow
{
    public partial class SktchapterDetail : System.Web.UI.Page
    {
        protected int Id = 0;
        protected int CId = 0;
        private MW.BLL.B_Sktchapter bll = new MW.BLL.B_Sktchapter();
        private MW.Model.M_Mw_sktchapter model = new MW.Model.M_Mw_sktchapter();

        protected void Page_Load(object sender, EventArgs e)
        {
            Id = MW.Common.LYRequest.GetInt("Id", 0);
            CId = MW.Common.LYRequest.GetInt("CId", 0);

            if (!IsPostBack)
            {
                InitData();
            }
        }
        private void InitData()
        {
            if (Id > 0)
            {//edit
                model = bll.GetModel(Id);
                if (model != null)
                {
                    C_Type.SelectedValue = model.C_Type.ToString();
                    txtName.Text = model.C_Name;
                    txtN_OrderId.Text = model.Sort.ToString();
                    txtC_Content.Text = model.C_Content;
                    chkislock.Checked = model.Islock == 1;

                }
            }
        }
        protected void btnSave_Click(object sender, EventArgs p1)
        {
            if (Id > 0)
            {
                #region __________Edit__________
                model = bll.GetModel(Id);
                model.C_Name = txtName.Text;
                model.C_Type = int.Parse(C_Type.Text);
                model.C_Content = txtC_Content.Text;
                model.Islock = chkislock.Checked ? 1 : 0;
                model.Sort = Utils.StrToInt(txtN_OrderId.Text, 0);
                if (bll.Update(model))
                {
                    //B_operationLog.CreateOperationLog(B_operationLog.operationLogType.Edit, "", "");
                    MessageBox.JSLoad(this, "alert('保存成功!');window.location.href='sktchapterlist.aspx?id=" + CId + "'");
                }
                #endregion
            }
            else
            {
                #region __________Add__________
                model.CId = CId;
                model.C_Type = int.Parse(C_Type.SelectedValue);
                model.C_Name = txtName.Text;
                model.C_Content = txtC_Content.Text;
                model.Islock = chkislock.Checked ? 1 : 0;
                model.Sort = Utils.StrToInt(txtN_OrderId.Text, 0);

                if (bll.Add(model))
                {
                    if (model.C_Type == 1)
                    {
                        int id = bll.GetMaxId();
                        new MW.BLL.B_Sktitem().AddDefaultJieguo(CId, id);
                    }
                    //B_operationLog.CreateOperationLog(B_operationLog.operationLogType.Add, "", "");
                    MessageBox.JSLoad(this, "alert('保存成功!');window.location.href='sktchapterlist.aspx?id=" + CId + "'");
                }
                #endregion
            }
        }
    }
}