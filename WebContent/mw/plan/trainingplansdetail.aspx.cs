using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using MW.BLL;
using MW.Common;

namespace PortalWeb.mw.plan
{
    public partial class TrainingPlansDetail : System.Web.UI.Page
    {
        protected int Id = 0;
        private MW.BLL.B_tech_train_plan bll = new MW.BLL.B_tech_train_plan();
        private MW.Model.M_tech_train_plan model = new MW.Model.M_tech_train_plan();
        protected int eanum = 0;
        protected string parentdialog = "Add";
        private MW.BLL.B_ecan_domain bll_dic = new MW.BLL.B_ecan_domain();

        protected void Page_Load(object sender, EventArgs e)
        {
            Id = MW.Common.LYRequest.GetInt("id", 0);
            //if (!BasePage.IsPermissions(96))
            //{
            //    MessageBox.JSLoad(sender, "alert('没有权限！!');reloadDlg();");
            //    return;
            //}

            if (!IsPostBack)
            {
                if (Id > 0)
                {
                    parentdialog = "Edit";
                }
                InitData();
                txtUsers.Attributes.Add("onclick",
                                        "javascript:openDlg(\"UsersDialog\",\"查找用户\",\"Controls/_UsersDialog.aspx?hidCtl=" + hidUsersId.ClientID + "&txtCtl=" + txtUsers.ClientID + "&uids=" + model.BelongTo + "\",730,480,\"" +
                                        parentdialog + "\");");
                txtTearcher.Attributes.Add("onclick",
                                        "javascript:openDlg(\"UsersDialogT\",\"查找培训老师\",\"Controls/_UsersDialog.aspx?hidCtl=" + hidtearcherid.ClientID + "&txtCtl=" + txtTearcher.ClientID + "&uids=" + model.TeacherIds + "\",730,480,\"" +
                                        parentdialog + "\");");
                txtCode.Attributes.Add("onclick",
                                        "javascript:openDlg(\"TempCodeDialog\",\"查找课程码\",\"Controls/_TempCodeDialog.aspx?hidCtl=" + hidetxtCode.ClientID + "&txtCtl=" + txtCode.ClientID + "&groupid=1\",530,480,\"" +
                                        parentdialog + "\");");

            }
        }

        private void InitData()
        {
            JBind.BindList(dropTypeId, JTree.GetClassTree(bll_dic.GetListByParent(1), "ParentId", "1", "DictionaryName"));

            if (Id > 0)
            {
                //edit
                model = bll.GetModel(Id.ToString());
                if (model != null)
                {
                    txtAddress.Text = model.EvalAble;
                    imgPhoto.ImageUrl = model.ImagePath;
                    chkIsTop.Checked = model.IsTop > 0;
                    chkIsRecommend.Checked = model.IsRecommend > 0;
                    dropTypeId.Text = model.TypeID.ToString();
                    txtPlanName.Text = model.PlanName;
                    txtDescrip.Text = model.Descrip;
                    txtBeginTime.Text = model.BEGIN_TIME.ToString("yyyy-MM-dd");
                    txtEndTime.Text = model.END_TIME.ToString("yyyy-MM-dd");
                    rbltStatus.SelectedValue = model.State.ToString();
                    hidUsersId.Value = model.TRAIN_ID;
                    //txtUsers.Text = B_ecan_user.GetUserName(model.BelongTo);
                    hidtearcherid.Value = model.TeacherIds;
                    // txtTearcher.Text = B_ecan_user.GetUserName(model.TeacherIds);
                    txtApplyNum.Text = model.ApplyNum.ToString();
                    txtCode.Text = model.KeyWords;
                    if (model.KeyWords != "")
                    {
                        txtCode.Enabled = false;
                    }

                }
            }
        }

        protected void btnSave_Click(object sender, EventArgs p1)
        {
            //int typeid = 0;
            if (Id > 0)
            {
                model = bll.GetModel(Id);
                if (this.FileUpload1.HasFile && model.ImagePath != "")
                {
                    Utils.DelFile(model.ImagePath);
                }
                //typeid = model.TypeID;
            }
            model.TeacherIds = hidtearcherid.Value;
            model.EvalAble = txtAddress.Text;
            model.TRAIN_ID = hidUsersId.Value;
            model.State = int.Parse(rbltStatus.SelectedValue);
            model.TypeID = int.Parse(dropTypeId.Text);
            model.IsTop = chkIsTop.Checked ? 1 : 0;
            model.IsRecommend = chkIsRecommend.Checked ? 1 : 0;
            model.PlanName = txtPlanName.Text;
            model.Descrip = txtDescrip.Text;
            model.BEGIN_TIME = DateTime.Parse(txtBeginTime.Text);
            model.END_TIME = DateTime.Parse(txtEndTime.Text);
            model.ApplyNum = Utils.StrToInt(txtApplyNum.Text, 0);
            model.KeyWords = txtCode.Text;
            if (this.FileUpload1.HasFile)
            {
                JUpload up = new JUpload();
                up.FileSize = 1000;
                up.FileType = "image";
                string rev = up.SaveFile(FileUpload1, "/UpLoads/TrainingPlans/");

                string strMsg = "";
                switch (rev)
                {
                    case "0":
                        strMsg = "上传文件格式不正确";
                        break;
                    case "1":
                        strMsg = "上传文件过大";
                        break;
                    case "3":
                        strMsg = "非法的图片";
                        break;

                }
                if (strMsg != "")
                {
                    MessageBox.Alert(this, strMsg);
                    return;
                }
                model.ImagePath = rev;
            }
            if (Id > 0)
            {
                #region __________Edit__________

                if (bll.Update(model))
                {

                    //B_operationLog.CreateOperationLog(B_operationLog.operationLogType.Edit, "", "");
                    MessageBox.JSLoad(this, "alert('保存成功!');reloadDlg(true);");
                }
                #endregion
            }
            else
            {
                model.CREATE_TIME = DateTime.Now;
                bool rv = bll.Add(model);
                if (rv)
                {

                    //B_operationLog.CreateOperationLog(B_operationLog.operationLogType.Add, "", "");
                    MessageBox.JSLoad(this, "alert('保存成功!');reloadDlg(true);");
                }
            }
        }
    }
}