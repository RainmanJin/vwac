using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using MW.BLL;
using MW.Common;
using MW;
using MW.Model;

namespace PortalWeb.mw.vwtestdrive
{
    public partial class testdriverdetail : System.Web.UI.Page
    {
        int Id = 0;
        private MW.BLL.B_Testdriver bll = new MW.BLL.B_Testdriver();
        private MW.Model.M_Mw_testdriver model = new MW.Model.M_Mw_testdriver();
        private MW.BLL.B_ecan_domainvalue bl=new B_ecan_domainvalue();
        private  MW.Model.M_ecan_domainvalue modelAoe=new M_ecan_domainvalue();
        private MW.Model.M_ecan_user mdi=new M_ecan_user();
        protected string parentdialog = "Add";

        protected void Page_Load(object sender, EventArgs e)
        {

            Id = MW.Common.LYRequest.GetInt("Id", 0);

            txtStudents.Text = txtStudents.Text;
            txtChexi.Text = txtChexi.Text;
            txtC_Teacher.Text = txtC_Teacher.Text;
            txtC_Course.Text = txtC_Course.Text;
            if (!IsPostBack)
            {

                InitData();
                if (Id > 0)
                {
                    parentdialog = "Edit";
                }
                //txtC_Teacher.Attributes.Add("onclick","javascript:modify1(\"查找老师\",\"" + "_userdialog.aspx?hidCtl=" + hideC_Teacher.ClientID + "&txtCtl=" + txtC_Teacher.ClientID + "\",530,\"" + "\");");
                txtChexi.Attributes.Add("onclick","javascript:modify(\"查找车型\",\"" + "chexidetail.aspx?hidCtl=" + hideChexi.ClientID + "&txtCtl=" + txtChexi.ClientID + "\",730,\"" + "\");");

                //txtC_Course.Attributes.Add("onclick","javascript:modify1(\"查找课程\",\"" + "coursedetail.aspx?hidCtl=" + hideC_Course.ClientID + "&txtCtl=" + txtC_Course.ClientID + "\",530,\"" + "\");");
                txtStudents.Attributes.Add("onclick", "javascript:modify(\"查找学员\",\"" + "_usersdialog.aspx?hidCtl=" + hideStudents.ClientID + "&txtCtl=" + txtStudents.ClientID + "\",730,\"" + "\");");


            }
        }
        private void InitData()
        {
            txtC_Code.Text = bll.GetMaxCode();
            if (Id > 0)
            {
                //edit
                model = bll.GetModel(Id);
                if (model != null)
                {
                    txtC_Name.Text = model.C_Name;
                    txtC_Code.Text = model.C_Code;  
                    if (model.C_Code != "") txtC_Code.Enabled = false;
                    txtC_Course.Text = model.C_Course;
                    hideC_Course.Value = model.C_Courseid;
                    txtC_Teacher.Text = model.C_Teacher;
                    hideC_Teacher.Value = model.C_Teacherid;
                    txtStudents.Text = new MW.BLL.B_ecan_user().GetUserNameAoe(model.Students.Trim(','));
                    hideStudents.Value = model.Students;
                    txtChexi.Text = new MW.BLL.B_ecan_domainvalue().GetString(model.Chexi.Trim(','));
                    hideChexi.Value = model.Chexi;
                    string[] arr = Utils.SplitString(model.Changdi, ",", 3);
                    chkgl.Checked = arr[0] == "0";
                    chkyd.Checked = arr[1] == "1";
                    chkyy.Checked = arr[2] == "2";
                    //JBind.SetSelectedList(txtChangdi, model.Changdi.Split(','));
                }
            }
        }
        private string getcd()
        {
            string tmp = "";
            tmp = chkgl.Checked ? "0" : "";
            tmp += "," + (chkyd.Checked ? "1" : "");
            tmp += "," + (chkyy.Checked ? "2" : "");
            return tmp;
        }
        protected void btnSave_Click(object sender, EventArgs p1)
        {
            if (Id > 0)
            {
                #region __________Edit__________
                model = bll.GetModel(Id);
                model.C_Name = txtC_Name.Text;
                model.C_Code = txtC_Code.Text;
                model.C_Course = txtC_Course.Text;
                model.C_Courseid =hideC_Course.Value;
                model.C_Teacher = txtC_Teacher.Text;
                model.C_Teacherid = hideC_Teacher.Value;
                model.Students = hideStudents.Value;
                model.Chexi = hideChexi.Value;
                model.Changdi = getcd();
                if (bll.Update(model))
                {
                    bll.ClearCache("_info");
                    MessageBox.JSLoad(this, "alert('保存成功!');window.location.href='testdriverlist.aspx'");
                }

                #endregion
            }
            else
            {
                #region __________Add__________

                model.C_Name = txtC_Name.Text;
                model.C_Code = txtC_Code.Text;
                model.C_Course = txtC_Course.Text;
                model.C_Courseid = hideC_Course.Value;
                model.C_Teacher = txtC_Teacher.Text;
                model.C_Teacherid = hideC_Teacher.Value;
                model.Students = hideStudents.Value;
                model.Chexi = hideChexi.Value;
                model.Changdi = getcd();

                if (bll.Add(model))
                {
                    MessageBox.JSLoad(this, "alert('新增成功!');window.location.href='testdriverlist.aspx'");
                }

                #endregion
            }
        }
    }
}