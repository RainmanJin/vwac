using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using MW;
using MW.Common;

namespace Plugin.VWSurvey.Admini.VWSurvey
{
    public partial class BiceItemDetail : System.Web.UI.Page
    {
        protected int Id = 0, sysid = 0,parentid=0;
        private MW.BLL.B_Votesubject bll = new MW.BLL.B_Votesubject();
        private MW.Model.M_Mw_votesubject model = new MW.Model.M_Mw_votesubject();

        protected void Page_Load(object sender, EventArgs e)
        {
            Id = MW.Common.LYRequest.GetInt("id", 0);
            sysid = LYRequest.GetInt("sysid", 0);
            parentid = LYRequest.GetInt("parentid", 0);
//#warning BasePage.IsPermissions(1)
//            if (!BasePage.IsPermissions(42))
//            {
//                MessageBox.JSLoad(this, "alert('没有权限！!');reloadDlg();");
//                return;
//            }
            if (!IsPostBack)
            {
                InitData();
            }
        }
        private void InitData()
        {
            JBind.BindList(drpType, bll.GetList(0, "N_SubId,C_SubTitle", "Parentid=0 and N_SysId=" + sysid, "").Tables[0], "C_SubTitle", "N_SubId");//省份
            drpType.Items.Insert(0, new ListItem("一级目录", "0"));
            JBind.BindList(drpUnit, new MW.BLL.B_Voteunit().GetList(0, "N_SubId,C_SubTitle", "N_SysId=" + sysid, "").Tables[0], "C_SubTitle", "N_SubId");//省份
            //drpUnit.Items.Insert(0, new ListItem("请选择", "0"));
            hidsysid.Value = sysid.ToString();
            if (Id > 0)
            {//edit
                model = bll.GetModel(Id);
                if (model != null)
                {
                    txtC_SubTitle.Text = model.C_SubTitle;
                    txtN_OrderId.Text = model.N_OrderId.ToString();
                    drpNeed.SelectedValue = model.N_Need.ToString();
                    drpType.SelectedValue = model.Parentid.ToString();
                    drpUnit.SelectedValue = model.N_Type.ToString();
                    if (BaseVote.ShowSysEdit(sysid))
                    {
                        drpUnit.Enabled = false;
                    }
                    
                }
            }
            else
            {
                drpType.SelectedValue = parentid.ToString();
            }
        }
        protected void btnSave_Click(object sender, EventArgs p1)
        {
            if (Id > 0)
            {
                #region __________Edit__________
                model = bll.GetModel(Id);
                model.C_SubTitle = txtC_SubTitle.Text;
                model.N_OrderId = int.Parse(txtN_OrderId.Text);
                model.N_Need = int.Parse(drpNeed.SelectedValue);
                model.Parentid = int.Parse(drpType.SelectedValue);
                model.N_Type = int.Parse(drpUnit.SelectedValue);
                if (bll.Update(model))
                {
                    //B_operationLog.CreateOperationLog(B_operationLog.operationLogType.Edit, "", "");
                    MessageBox.JSLoad(this, "alert('保存成功!');window.location.href='biceitem.aspx?Sysid=" + sysid + "'");
                }
                #endregion
            }
            else
            {
                #region __________Add__________
                model.N_SysId = int.Parse(hidsysid.Value);
                model.C_SubTitle = txtC_SubTitle.Text;
                model.N_OrderId = int.Parse(txtN_OrderId.Text);
                model.N_Need = int.Parse(drpNeed.SelectedValue);
                model.Parentid = int.Parse(drpType.SelectedValue);
                model.N_Type = int.Parse(drpUnit.SelectedValue);
                if (bll.Add(model))
                {
                    //B_operationLog.CreateOperationLog(B_operationLog.operationLogType.Add, "", "");
                    MessageBox.JSLoad(this, "alert('保存成功!');window.location.href='biceitem.aspx?Sysid=" + sysid + "'");
                }
                #endregion
            }
        }
    }
}