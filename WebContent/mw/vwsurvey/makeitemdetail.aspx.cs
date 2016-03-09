using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using MW;
using MW.BLL;
using MW.Common;

namespace Plugin.VWSurvey.Admini.VWSurvey
{
    public partial class MakeItemDetail : System.Web.UI.Page
    {
        protected int Id = 0;
        protected int subid = 0, sysid = 0, ntype = 0;
        private MW.BLL.B_Votekey bll = new MW.BLL.B_Votekey();
        private MW.Model.M_Mw_votekey model = new MW.Model.M_Mw_votekey();

        protected void Page_Load(object sender, EventArgs e)
        {
            Id = LYRequest.GetInt("id", 0);
            subid = LYRequest.GetInt("subid", 0);
            ntype = LYRequest.GetInt("type", 0);
            sysid = MW.Common.LYRequest.GetInt("sysid", 0);
//#warning BasePage.IsPermissions(1)
//            if (!BasePage.IsPermissions(42))
//            {
//                MessageBox.JSLoad(this, "alert('没有权限！!');reloadDlg();");
//                return;
//            }
            if (!IsPostBack)
            {
                EnableDropList(subid);
                BindRule();
                InitData();
            }
        }
        private void EnableDropList(int subid)
        {
            DataTable table3 = new B_Votekey().GetList(0,"N_Type", "N_SubId=" + subid + "", " N_OrderId,N_KeyId").Tables[0];
            if (table3 != null)
            {
                foreach (DataRow dataRow in table3.Rows)
                {
                    //if (dataRow[0].ToString() == "2")
                    //{
                    //    drpType.Items.FindByValue("5").Enabled=false;
                    //    break;
                    //}
                    if (dataRow[0].ToString() == "5")
                    //{
                    //    drpType.Items.FindByValue("2").Enabled = false;
                        if (Id == 0) btnSave.Enabled = false;
                    //    break;
                    //}
                }
            }

        }
        private void InitData()
        {
            hidsubid.Value = subid.ToString();
            hidtype.Value = ntype.ToString();
            if (Id > 0)
            {//edit
                model = bll.GetModel(Id);
                if (model != null)
                {
                  
                    txtC_KeyTitle.Text = model.C_KeyTitle;
                    drpType.SelectedValue = model.N_Type.ToString();
                    txtN_OrderId.Text = model.N_OrderId.ToString();
                    DDLRule.SelectedItem.Text = model.C_Rule.Split('`')[0];
                    txtN_Score.Text = model.N_Score.ToString();

                    if (BaseVote.ShowSysEdit(sysid))
                    {
                        txtC_KeyTitle.Enabled = false;
                        drpType.Enabled = false;
                        txtN_Score.Enabled = false;
                    }
                }
            }
        }
        private void BindRule()
        {
            DataSet set = new DataSet();
            set.ReadXml(Utils.GetMapPath(BasePage.GetWebPath+"/mw/onlinesurvey/rule.xml"));
            foreach (DataRow row in set.Tables[0].Rows)
            {
                ListItem item3 = new ListItem
                {
                    Value = row["Expression"].ToString(),
                    Text = row["Prompt"].ToString()
                };
                DDLRule.Items.Add(item3);
            }
            DDLRule.Items.Insert(0, new ListItem("不限制", ""));
           
        }
        protected void btnSave_Click(object sender, EventArgs p1)
        {
            string C_Rule = DDLRule.SelectedItem.Text + "`" + DDLRule.SelectedItem.Value.Replace("&amp;", "&").Replace("&lt;", "<").Replace("&gt;", ">");

            if (Id > 0)
            {
                #region __________Edit__________
                model = bll.GetModel(Id);
                model.N_SubId = int.Parse(hidsubid.Value);
                model.C_KeyTitle = txtC_KeyTitle.Text;
                model.N_Type = int.Parse(hidtype.Value);
                model.N_OrderId = int.Parse(txtN_OrderId.Text);
                model.C_Rule = C_Rule;
                model.N_Score = int.Parse(txtN_Score.Text);
                model.C_LogicSub = "";
                if (bll.Update(model))
                {
                    //B_operationLog.CreateOperationLog(B_operationLog.operationLogType.Edit, "", "");
                    MessageBox.JSLoad(this, "alert('保存成功!');window.location.href='makeitem.aspx?sysid=" + sysid + "&type=" + hidtype.Value + "&subid="+hidsubid.Value+"'");
                }
                #endregion
            }
            else
            {
                #region __________Add__________
                model.N_SubId = int.Parse(hidsubid.Value);
                model.C_KeyTitle = txtC_KeyTitle.Text;
                model.N_Type = int.Parse(hidtype.Value);
                model.N_OrderId = int.Parse(txtN_OrderId.Text);
                model.C_Rule = C_Rule;
                model.N_Score = int.Parse(txtN_Score.Text);
                model.C_LogicSub = "";
                if (bll.Add(model))
                {
                    //B_operationLog.CreateOperationLog(B_operationLog.operationLogType.Add, "", "");
                    MessageBox.JSLoad(this, "alert('保存成功!');window.location.href='makeitem.aspx?sysid=" + sysid + "&type=" + hidtype.Value + "&subid=" + hidsubid.Value + "'");
                }
                #endregion
            }
        }
    }
}