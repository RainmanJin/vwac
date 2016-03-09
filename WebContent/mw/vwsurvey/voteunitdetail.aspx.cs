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
    public partial class VoteUnitDetail : System.Web.UI.Page
    {
        protected int Id = 0, sysid = 0;
    private MW.BLL.B_Voteunit bll = new MW.BLL.B_Voteunit();
    private MW.Model.M_Mw_voteunit model = new MW.Model.M_Mw_voteunit();
        protected string parentdialog = "Add";

        protected void Page_Load(object sender, EventArgs e)
        {
            Id = MW.Common.LYRequest.GetInt("id", 0);
            sysid = MW.Common.LYRequest.GetInt("sysid", 0);
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
            hidSysvoteid.Value = sysid.ToString();
             if(Id>0){//edit
              model= bll.GetModel(Id);
 
                if(model!=null)
                {
                  txtC_Title.Text = model.C_SubTitle;
                    drpType.SelectedValue = model.N_Type.ToString();
                    if (Plugin.VWSurvey.Admini.VWSurvey.BaseVote.ShowSysEdit(sysid))
                    {
                        drpType.Enabled = false;
                    }
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
                        model.C_SubTitle = txtC_Title.Text;
                model.N_Type = Utils.StrToInt(drpType.SelectedValue,0);
                model.N_SysId = Utils.StrToInt(hidSysvoteid.Value,0);
              if (bll.Update(model))
                {
                    MessageBox.JSLoad(this, "alert('保存成功!');window.location.href='voteunit.aspx?sysid=" + sysid + "'");
                }
                #endregion
            }
            else
            {
            #region __________Add__________

                model.C_SubTitle = txtC_Title.Text;
                model.N_Type = Utils.StrToInt(drpType.SelectedValue,0);
                model.N_SysId = Utils.StrToInt(hidSysvoteid.Value,0);
               if (bll.Add(model))
                {
                    if (model.N_Type == 1 || model.N_Type == 5)
                    {
                        var mk = new MW.Model.M_Mw_votekey();
                        mk.N_SubId = bll.GetMaxId();
                        mk.C_KeyTitle = "";
                        mk.N_Type = model.N_Type;
                        mk.N_OrderId = 0;
                        mk.C_Rule = "不限制`";
                        mk.N_Score = 0;
                        mk.C_LogicSub = "";
                        new MW.BLL.B_Votekey().Add(mk);
                    }
                    MessageBox.JSLoad(this, "alert('保存成功!');window.location.href='voteunit.aspx?sysid=" + sysid + "'");
                }
                #endregion
            }
        }
    }
}