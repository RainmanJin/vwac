using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using MW.Common;

namespace PortalWeb.mw.retailknowhow
{
    public partial class sktitemdetali : System.Web.UI.Page
    {
        protected int Id = 0;
        protected int CId = 0, type = 0, chapterid = 0, dis = 0;
        private MW.BLL.B_Sktitem bll = new MW.BLL.B_Sktitem();
        private MW.Model.M_Mw_sktitem model = new MW.Model.M_Mw_sktitem();

        protected void Page_Load(object sender, EventArgs e)
        {
            Id = MW.Common.LYRequest.GetInt("Id", 0);
            CId = MW.Common.LYRequest.GetInt("CId", 0);
            chapterid = MW.Common.LYRequest.GetInt("chapterid", 0);
            type = MW.Common.LYRequest.GetInt("type", 0);
            dis = MW.Common.LYRequest.GetInt("dis", 0);

            if (!IsPostBack)
            {
                InitData();
            }
        }
        private void InitData()
        {
            if (type == 1 & dis == 0)
            {
                lit.Text = "所属二维结构：";
                DrpParentid2.Visible = true;
                JBind.BindList(DrpParentid, JTree.GetClassTree(bll.GetListByParent2(CId, chapterid, 0, 1), "Parentid2", "C_Name"));
                JBind.BindList(DrpParentid2, JTree.GetClassTree(bll.GetListByParent2(CId, chapterid, 0, 2), "Parentid2", "C_Name"));

            }
            else
            {
                JBind.BindList(DrpParentid, JTree.GetClassTree(0, bll.GetListByParent(CId, chapterid, 0), "Parentid", "0", "C_Name"));
                DrpParentid.Items.Insert(0, new ListItem("根目录", "0"));
            }

            if (Id > 0)
            {//edit
                model = bll.GetModel(Id);
                if (model != null)
                {
                    txtA1.Text = model.A1.ToString();
                    txtA2.Text = model.A2.ToString();
                    txtA3.Text = model.A3;
                    txtA4.Text = model.A4;
                    txtN_OrderId.Text = model.Sort.ToString();
                    txtName.Text = model.C_Name;
                    drpType.Text = model.C_Type.ToString();
                    drpColor.Text = model.C_Color.ToString();
                    DrpParentid.SelectedValue = model.Parentid.ToString();
                    DrpParentid2.SelectedValue = model.Parentid2.ToString();

                }
            }
            else
            {
                DrpParentid.SelectedValue = LYRequest.GetInt("parentid", 0).ToString();
            }
        }
        protected void btnSave_Click(object sender, EventArgs p1)
        {
            if (Id > 0)
            {
                #region __________Edit__________

                model = bll.GetModel(Id);
                model.CId = CId;
                model.chapterid = chapterid;
                model.Sort = int.Parse(txtN_OrderId.Text);
                model.C_Name = txtName.Text;
                model.C_Color = int.Parse(drpColor.SelectedValue);
                model.C_Type = int.Parse(drpType.SelectedValue);
                model.Parentid = int.Parse(DrpParentid.SelectedValue);
                model.Parentid2 = Utils.StrToInt(DrpParentid2.SelectedValue, 0);
                model.A1 = int.Parse(txtA1.Text);
                model.A2 = int.Parse(txtA2.Text);
                model.A3 = txtA3.Text;
                model.A4 = txtA4.Text;
                if (bll.Update(model))
                {
                    //B_operationLog.CreateOperationLog(B_operationLog.operationLogType.Edit, "", "");
                    MessageBox.JSLoad(this, "alert('保存成功!');window.location.href='sktitemlist.aspx?&Cid=" + CId + "&Id=" + chapterid + "'");
                }
                #endregion
            }
            else
            {
                #region __________Add__________
                model.CId = CId;
                model.chapterid = chapterid;
                model.Sort = int.Parse(txtN_OrderId.Text);
                model.C_Name = txtName.Text;
                model.C_dimension = dis;
                model.C_Color = int.Parse(drpColor.SelectedValue);
                model.C_Type = int.Parse(drpType.SelectedValue);
                model.Parentid = int.Parse(DrpParentid.SelectedValue);
                model.Parentid2 = Utils.StrToInt(DrpParentid2.SelectedValue, 0);
                model.A1 = int.Parse(txtA1.Text);
                model.A2 = int.Parse(txtA2.Text);
                model.A3 = txtA3.Text;
                model.A4 = txtA4.Text;
                if (bll.Add(model))
                {
                    //B_operationLog.CreateOperationLog(B_operationLog.operationLogType.Add, "", "");
                    MessageBox.JSLoad(this, "alert('新增成功!');window.location.href='sktitemlist.aspx?&Cid=" + CId + "&Id=" + chapterid + "'");
                }
                #endregion
            }
        }
    }
}