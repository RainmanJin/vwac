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
    public partial class BiceOption : System.Web.UI.Page
    {   protected int Id = 0;
    private MW.BLL.B_Votecourse bll = new MW.BLL.B_Votecourse();
    private MW.Model.M_Mw_votecourse model = new MW.Model.M_Mw_votecourse();
        protected string parentdialog = "Add";

        protected void Page_Load(object sender, EventArgs e)
        {
            //txtC_course.Text = txtC_course.Text;
            //txtC_Tearcher.Text = txtC_Tearcher.Text;
            Id = MW.Common.LYRequest.GetInt("id", 0);
            #warning BasePage.IsPermissions(1)
            //if (!BasePage.IsPermissions(42))
            //{
            //    //MessageBox.JSLoad(this, "alert('没有权限！!');reloadDlg();");
            //    return;
            //}

            if (!IsPostBack)
            {
                //txtC_course.Attributes.Add("onclick",
                //              "javascript:modify1(\"查找课程\",\"" + "coursedetail.aspx?hidCtl=" + hideC_Course.ClientID + "&txtCtl=" + txtC_course.ClientID + "\",530,\"" + "\");");
                //txtC_Tearcher.Attributes.Add("onclick",
                                 //"javascript:modify1(\"查找老师\",\"" + "_userdialog.aspx?hidCtl=" + hideC_Teacher.ClientID + "&txtCtl=" + txtC_Tearcher.ClientID + "\",730,\"" + "\");");
                //if (Id > 0)
                //{
                //    parentdialog = "Edit";
                //}
                InitData();
               
            }
        }

        private void InitData()
        {

            DataTable dt = new MW.BLL.B_ecan_domainvalue().GetDrpList("DOMAIN_ID='DCLX'");
            JBind.BindList(dropTypeId, dt,"DOMAINLABEL", "VALUE");
             DataSet ds = MW.BLL.mw_getlistbypage.GetDataSet_NoCache("mw_votesystem", "N_SysId,C_Title", "1=1", "", "N_SysId DESC", "N_SysId", 1, 100, 0);

            JBind.BindList(drpVoteTmp, ds.Tables[0], "C_Title", "N_SysId");
            txtC_Code.Text = bll.GetMaxCode();
             if(Id>0){//edit
              model= bll.GetModel(Id);
 
                if(model!=null)
                {
                      StartTime.Text = Utils.StrToDateTime(model.DT_StartDate.ToString(), DateTime.Now).ToShortDateString();
                      EndTime.Text = Utils.StrToDateTime(model.DT_OverDate.ToString(), DateTime.Now).ToShortDateString();
                      txtCourseStart.Text = Utils.StrToDateTime(model.CourseStart.ToString(), DateTime.Now).ToShortDateString();
                      txtCourseEnd.Text = Utils.StrToDateTime(model.CourseEnd.ToString(), DateTime.Now).ToShortDateString();
                      txtC_Title.Text = model.C_Title; 
                      txtC_Tearcher.Text=model.C_Tearcher; 
                      txtC_Adrees.Text=model.C_Adrees;
                      txtN_CodeSurvey.Checked = model.N_CodeSurvey == 1;
                      txtC_ReturnUrl.Text=model.C_ReturnUrl;
                      txtC_course.Text = model.C_course;
                      dropTypeId.SelectedValue = model.N_Type.ToString();
                      txtpxnum.Text = model.pxnum;
                    drpVoteTmp.SelectedValue = model.N_SysId.ToString();
                      txtC_Code.Text = model.C_Code;
                    if (model.C_Code != "") txtC_Code.Enabled = false;

                }
             }
        }
        protected void btnSave_Click(object sender, EventArgs p1)
        {
            if(Id>0)
            {
            #region __________Edit__________
                        model= bll.GetModel(Id);
                        model.pxnum=txtpxnum.Text;
                        model.N_CodeSurvey = txtN_CodeSurvey.Checked ? 1 : 0;
                        model.C_Title = txtC_Title.Text;
                        model.DT_StartDate = DateTime.Parse(StartTime.Text);
                        model.C_Tearcher = txtC_Tearcher.Text.Trim(',');
                        model.C_Adrees = txtC_Adrees.Text;
                        model.C_Code = txtC_Code.Text;
                        model.DT_OverDate = DateTime.Parse(EndTime.Text);
                        model.C_ReturnUrl = txtC_ReturnUrl.Text;
                        model.C_course = txtC_course.Text;
                        model.N_Type = Utils.StrToInt(dropTypeId.SelectedValue,0);
                        model.N_SysId = Utils.StrToInt(drpVoteTmp.SelectedValue,0);
                        model.CourseStart = DateTime.Parse(txtCourseStart.Text);
                        model.CourseEnd = DateTime.Parse(txtCourseEnd.Text);
              if (bll.Update(model))
                {
                    MessageBox.AlertToUrl(this, "修改保存成功!", "votelist.aspx");
                }
                #endregion
            }
            else
            {
            #region __________Add__________
                model.pxnum = txtpxnum.Text;
                model.N_SysId = Utils.StrToInt(drpVoteTmp.SelectedValue, 0);
                        model.N_CodeSurvey = txtN_CodeSurvey.Checked ? 1 : 0;
                        model.C_Title = txtC_Title.Text;
                        model.DT_StartDate = DateTime.Parse(StartTime.Text);
                        model.C_Tearcher = txtC_Tearcher.Text.Trim(','); 
                        model.C_Adrees = txtC_Adrees.Text; 
                        model.C_Code = txtC_Code.Text;
                        model.DT_OverDate = DateTime.Parse(EndTime.Text);
                        model.C_ReturnUrl = txtC_ReturnUrl.Text;
                        model.C_course = txtC_course.Text;
                        model.N_Type = Utils.StrToInt(dropTypeId.SelectedValue, 0);
                        model.CourseStart = DateTime.Parse(txtCourseStart.Text);
                        model.CourseEnd = DateTime.Parse(txtCourseEnd.Text);
               if (bll.Add(model))
                {
                    MessageBox.AlertToUrl(this, "保存成功!", "votelist.aspx");
                }
                #endregion
            }
        }
    }
}