using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using MW.Common;

namespace PortalWeb.mw.plan
{
    public partial class tmpcodeDetail : System.Web.UI.Page
    {
        protected int Id = 0;
        private MW.BLL.B_Tempcode bll = new MW.BLL.B_Tempcode();
        private MW.Model.M_Mw_tempcode model = new MW.Model.M_Mw_tempcode();

        protected void Page_Load(object sender, EventArgs e)
        {
            Id =MW.Common.LYRequest.GetInt("id", 0);
            //#warning BasePage.IsPermissions(0x6c)
            //            if (!BasePage.IsPermissions(42))
            //            {
            //                MessageBox.JSLoad(sender, "alert('没有权限！!');reloadDlg();");
            //                return;
            //            }
            if (!IsPostBack)
            {
                InitData();
            }
        }
        private void InitData()
        {
            if (Id > 0)
            {//edit
                //model = bll.GetModel(Id);
                //if (model != null)
                //{
                //    txtCardNumber.Text = model.CardNumber;
                //    txtType.Text = model.Type.ToString();
                //    txtState.Text = model.State.ToString();
                //    txtOrderID.Text = model.OrderID;
                //    txtCanUse.Text = model.CanUse.ToString();
                //    txtUselog.Text = model.Uselog;
                //    txtIndate.Text = model.Indate.ToString();
                //    txtCreateTime.Text = model.CreateTime.ToString();
                //}
            }
        }
        protected void btnSave_Click(object sender, EventArgs p1)
        {
            if (Id > 0)
            {
                //#region __________Edit__________
                //model = bll.GetModel(Id);
                //model.CardNumber = txtCardNumber.Text;
                //model.Type = int.Parse(txtType.Text);
                //model.State = int.Parse(txtState.Text);
                //model.OrderID = txtOrderID.Text;
                //model.CanUse = int.Parse(txtCanUse.Text);
                //model.Uselog = txtUselog.Text;
                //model.Indate = DateTime.Parse(txtIndate.Text);
                //model.CreateTime = DateTime.Parse(txtCreateTime.Text);
                //if (bll.Update(model))
                //{
                //    //B_operationLog.CreateOperationLog(B_operationLog.operationLogType.Edit, "", "");
                //    MessageBox.JSLoad(sender, "alert('保存成功!');reloadDlg(true);");
                //}
                //#endregion
            }
            else
            {
                #region __________Add__________
                model.CardNumber = "";
                model.Type = int.Parse(rbltType.Text);
                model.State = 0;
                model.OrderID = "";
                model.CanUse = int.Parse(txtCanUse.Text);
                model.Uselog = "";
                model.Indate = DateTime.Parse(txtIndate.Text);
                model.CreateTime = DateTime.Now;
                int num = int.Parse(txtNum.Text);
                int len = int.Parse(txtLen.Text);
                if (bll.AddMore(model, num, len))
                {
                    //B_operationLog.CreateOperationLog(B_operationLog.operationLogType.Add, "", "");
                    MessageBox.JSLoad(this, "alert('保存成功!');window.location.href='tmpcodelist.aspx'");
                }
                #endregion
            }
        }
    }
}