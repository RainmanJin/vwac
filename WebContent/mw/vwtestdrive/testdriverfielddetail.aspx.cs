using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using MW.Common;

namespace PortalWeb.mw.vwtestdrive
{
    public partial class testdriverfielddetail : System.Web.UI.Page
    {
        protected int Id = 0, parentid = 0;
        private MW.BLL.B_Testdriverfield bll = new MW.BLL.B_Testdriverfield();
        private MW.Model.M_Mw_testdriverfield model = new MW.Model.M_Mw_testdriverfield();

        protected void Page_Load(object sender, EventArgs e)
        {
            Id = MW.Common.LYRequest.GetInt("id", 0);
            parentid = LYRequest.GetInt("parentid", 0);
            if (!IsPostBack)
            {
                InitData();
            }
        }

        private void InitData()
        {
            DataSet ds = bll.GetList("");
            JBind.BindList(DropDownList1, JTree.GetClassTree(2, ds.Tables[0], "ParentId", "0", "C_Name"));
            DropDownList1.Items.Insert(0, new ListItem("根目录", "0"));
            
            if (Id > 0)
            {//edit
                model = bll.GetModel(Id);
                if (model != null)
                {
                    txtC_Name.Text = model.C_Name;
                    DropDownList1.SelectedValue = model.ParentId.ToString();
                    txtOrderby.Text = model.Orderby.ToString();
                    txtChangdi.SelectedValue = model.isUse.ToString();
                    //txtRemark.Text = model.Remark;
                }
            }
            else
            {
                DropDownList1.SelectedValue = parentid.ToString();
            }
        }
        protected void btnSave_Click(object sender, EventArgs p1)
        {
            if (Id > 0)
            {
                #region __________Edit__________
                model = bll.GetModel(Id);
                model.C_Name = txtC_Name.Text;
                //dropdownlist.selectItems.text
                model.ParentId = int.Parse(DropDownList1.SelectedValue);
                model.Orderby = int.Parse(txtOrderby.Text);
                model.isUse = Convert.ToInt32(txtChangdi.SelectedValue);
               // model.Remark = txtRemark.Text;
                if (bll.Update(model))
                {
                    //B_operationLog.CreateOperationLog(B_operationLog.operationLogType.Edit, "", "");
                    MessageBox.JSLoad(this, "alert('保存成功!');window.location.href='testdriverfieldlist.aspx'");
                }
                #endregion
            }
            else
            {
                #region __________Add__________
                model.C_Name = txtC_Name.Text;
                model.ParentId = int.Parse(DropDownList1.SelectedValue);
                model.Orderby = int.Parse(txtOrderby.Text);
                model.isUse = Convert.ToInt32(txtChangdi.SelectedValue);
                //model.Remark = txtRemark.Text;
                if (bll.Add(model))
                {
                    //B_operationLog.CreateOperationLog(B_operationLog.operationLogType.Add, "", "");
                    MessageBox.JSLoad(this, "alert('保存成功!');window.location.href='testdriverfieldlist.aspx'");
                }
                #endregion
            }
        }
    }
}