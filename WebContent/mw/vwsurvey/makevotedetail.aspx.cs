using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using MW;
using MW.Common;

namespace Plugin.VWSurvey.Admini.VWSurvey
{
    public partial class MakeVoteDetail : System.Web.UI.Page
    {   protected int Id = 0;
    private MW.BLL.B_Votesystem bll = new MW.BLL.B_Votesystem();
    private MW.Model.M_Mw_votesystem model = new MW.Model.M_Mw_votesystem();
        protected string parentdialog = "Add";

        protected void Page_Load(object sender, EventArgs e)
        {
            Id = MW.Common.LYRequest.GetInt("id", 0);
            #warning BasePage.IsPermissions(1)
            //if (!BasePage.IsPermissions(42))
            //{
            //    //MessageBox.JSLoad(this, "alert('没有权限！!');reloadDlg();");
            //    return;
            //}

            if (!IsPostBack)
            {
                if (Id > 0)
                {
                    parentdialog = "Edit";
                }
                InitData();
               
            }
        }
        protected int PageID
        {
            get
            {
                return LYRequest.GetInt("pageid", 0);
            }
        }
        private void InitData()
        {

            //DataTable dt = new MW.BLL.B_Dictionary().GetListByParent(PageID);
            //JBind.BindList(dropTypeId, JTree.GetClassTree(dt, "ParentId", PageID.ToString(), "DictionaryName"));
            //txtC_Code.Text = bll.GetMaxCode();
             if(Id>0){//edit
              model= bll.GetModel(Id);
 
                if(model!=null)
                {
                      //StartTime.Text = Utils.StrToDateTime(model.DT_StartDate.ToString(), DateTime.Now).ToShortDateString();
                      //EndTime.Text = Utils.StrToDateTime(model.DT_OverDate.ToString(), DateTime.Now).ToShortDateString();
                      txtC_Title.Text = model.C_Title; 
                    //  txtC_Tearcher.Text=model.C_Tearcher; 
                    //  txtC_Adrees.Text=model.C_Adrees;
                    //  txtN_CodeSurvey.Checked = model.N_CodeSurvey == 1;
                    //  txtC_ReturnUrl.Text=model.C_ReturnUrl;
                    //  dropTypeId.SelectedValue = model.N_Type.ToString();
                    //txtC_Code.Text = model.C_Code;
                    //if (model.C_Code != "") txtC_Code.Enabled = false;
                    //hidSysvoteid.Value = model.N_SysId.ToString();
                    //txtSysvote.Text = new MW.BLL.B_Votesystem().GetValue("C_TITLE", model.N_SysId).ToString();
                }
             }
        }
        protected void btnSave_Click(object sender, EventArgs p1)
        {
            //alert(10)
            if(Id>0)
            {
            #region __________Edit__________
                        model= bll.GetModel(Id);
                        //model.N_CodeSurvey = txtN_CodeSurvey.Checked ? 1 : 0;
                        model.C_Title = txtC_Title.Text;
                //        model.DT_StartDate = DateTime.Parse(StartTime.Text);
                //        model.C_Tearcher = txtC_Tearcher.Text;
                //        model.C_Adrees = txtC_Adrees.Text;
                //        model.C_Code = txtC_Code.Text;
                //        model.DT_OverDate = DateTime.Parse(EndTime.Text);
                //        model.C_ReturnUrl = txtC_ReturnUrl.Text;
                //model.N_Type = Utils.StrToInt(dropTypeId.SelectedValue,0);
                //model.N_SysId = Utils.StrToInt(hidSysvoteid.Value,0);
              if (bll.Update(model))
                {
                    MessageBox.JSLoad(this, "alert('保存成功!');window.location.href='makevote.aspx'");
                }
                #endregion
            }
            else
            {
            #region __________Add__________

                //model.N_SysId = Utils.StrToInt(hidSysvoteid.Value,0);
                //        model.N_CodeSurvey = txtN_CodeSurvey.Checked ? 1 : 0;
                        model.C_Title = txtC_Title.Text;
                        //model.DT_StartDate = DateTime.Parse(StartTime.Text);
                        //model.C_Tearcher = txtC_Tearcher.Text; 
                        //model.C_Adrees = txtC_Adrees.Text; 
                        //model.C_Code = txtC_Code.Text;
                        //model.DT_OverDate = DateTime.Parse(EndTime.Text);
                        //model.C_ReturnUrl = txtC_ReturnUrl.Text;
                        //model.N_Type = Utils.StrToInt(dropTypeId.SelectedValue, 0);
               if (bll.Add(model))
                {
                    MessageBox.JSLoad(this, "alert('新增成功!');window.location.href='makevote.aspx'");
                }
                #endregion
            }
        }
    }
}