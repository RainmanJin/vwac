using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using MW.Common;

namespace PortalWeb.mw.vwtestdrive
{
    public partial class testDrivertimedetail : System.Web.UI.Page
    {
        protected int Id = 0;
        private MW.BLL.B_Testdrivertime bll = new MW.BLL.B_Testdrivertime();
        private MW.Model.M_Mw_testdrivertime model = new MW.Model.M_Mw_testdrivertime();

        protected void Page_Load(object sender, EventArgs e)
        {

            Id = MW.Common.LYRequest.GetInt("Id", 0);
            if (!IsPostBack)
            {
                InitData();
            }
        }

        private void InitData()
        {
            if (Id > 0)
            {
//edit
                model = bll.GetModel(Id);
                if (model != null)
                {

                    txtC_Course.Text = model.C_Course;
                    txtStudent.Text = model.Student;
                    txtC_Teacher.Text = model.C_Teacher;
                    txtStartTime.Text = model.StartTime.ToString();
                    txtEndTime.Text = model.EndTime.ToString();
                    txtChexi.Text = model.Chexi;
                    txtChangdi.Text = model.Changdi;
                }
            }
        }

        //protected void btnSave_Click(object sender, EventArgs p1)
        //{
        //    if (Id > 0)
        //    {
        //        #region __________Edit__________

        //        model = bll.GetModel(Id);
        //        model.Cid = Convert.ToInt32(txtC_Course.Text);
        //        model.Student = txtStudent.Text;
        //        model.C_Teacher = txtC_Teacher.Text;
        //        model.StartTime = DateTime.Parse(txtStartTime.Text);
        //        model.EndTime = DateTime.Parse(txtEndTime.Text);
        //        model.Chexi = txtChexi.Text;
        //        model.Changdi = txtChangdi.Text;
        //        if (bll.Update(model))
        //        {
        //            //B_operationLog.CreateOperationLog(B_operationLog.operationLogType.Edit, "", "");
        //            MessageBox.JSLoad(this, "alert('保存成功!');window.location.href='testdrivertimeList.aspx'");
        //        }

        //        #endregion
        //    }
        //    else
        //    {
        //        #region __________Add__________

        //        model.Cid = Convert.ToInt32(txtC_Course.Text);
        //        model.Student = txtStudent.Text;
        //        model.C_Teacher = txtC_Teacher.Text;
        //        model.StartTime = DateTime.Parse(txtStartTime.Text);
        //        model.EndTime = DateTime.Parse(txtEndTime.Text);
        //        model.Chexi = txtChexi.Text;
        //        model.Changdi = txtChangdi.Text;
        //        if (bll.Add(model))
        //        {
        //            //B_operationLog.CreateOperationLog(B_operationLog.operationLogType.Add, "", "");
        //            //MessageBox.JSLoad(this, "alert('新增成功!');window.location.href='testdrivertimeList.aspx'");
        //            Response.Redirect("testdrivertimeList.aspx");
        //        }

        //        #endregion
        //    }
        //}

       
    }
}