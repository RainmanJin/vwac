using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using MW.Common;

namespace PortalWeb.mw.retailknowhow
{
    public partial class sktcoursedetail : System.Web.UI.Page
    {
        protected int Id = 0;
        protected int Chapterid = 0;
        private MW.BLL.B_Sktcourse bll = new MW.BLL.B_Sktcourse();
        private MW.Model.M_Mw_sktcourse model = new MW.Model.M_Mw_sktcourse();

        protected void Page_Load(object sender, EventArgs e)
        {
            Id = MW.Common.LYRequest.GetInt("id", 0);
            Chapterid = MW.Common.LYRequest.GetInt("chapterid", 0);

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
                    txtC_Name.Text = model.C_Name;
                    txtC_Code.Text = model.C_Code;
                    txtC_Content.Text = model.C_Content;
                    if (txtC_Code.Text != "") txtC_Code.Enabled = false;
                    chkislock.Checked = model.Islock == 1;
                }
            }
            else
            {
                txtC_Code.Text = bll.GetMaxCode();
            }
        }
        protected void btnSave_Click(object sender, EventArgs p1)
        {
            if (Id > 0)
            {
                #region __________Edit__________
                model = bll.GetModel(Id);
                model.C_Name = txtC_Name.Text;
                model.C_Code = txtC_Code.Text;
                model.C_Content = txtC_Content.Text;
                model.Islock = chkislock.Checked ? 1 : 0;
                if (bll.Update(model))
                {
                    //B_operationLog.CreateOperationLog(B_operationLog.operationLogType.Edit, "", "");
                    MessageBox.JSLoad(this, "alert('保存成功!');window.location.href='sktcourselist.aspx'");
                }
                #endregion
            }
            else
            {
                #region __________Add__________
                model.C_Name = txtC_Name.Text;
                model.C_Code = txtC_Code.Text;
                model.C_Content = txtC_Content.Text;
                model.Islock = chkislock.Checked ? 1 : 0;

                if (bll.Add(model))
                {
                    //B_operationLog.CreateOperationLog(B_operationLog.operationLogType.Add, "", "");
                    MessageBox.JSLoad(this, "alert('保存成功!');window.location.href='sktcourselist.aspx'");
                }
                #endregion
            }
        }
    }
}