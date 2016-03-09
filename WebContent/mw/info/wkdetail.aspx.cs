using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using MW.Common;

namespace PortalWeb.mw.Info
{
    public partial class WKDetail : System.Web.UI.Page
    {
        protected int Id = 0;
        private int typeid = 0;
        private MW.BLL.B_Coursechapter bll = new MW.BLL.B_Coursechapter();
        private MW.Model.M_Mw_coursechapter model = new MW.Model.M_Mw_coursechapter();
       // protected string plg = "wkAdd";

        protected void Page_Load(object sender, EventArgs e)
        {
            Id = MW.Common.LYRequest.GetInt("id", 0);
            typeid = MW.Common.LYRequest.GetInt("typeid", 0);
            //if (PageID == 0)
            //{
            //    MessageBox.Alert(this, "非法的参数！");
            //    return;
            //}
           // if (Id > 0) plg = "wkEdit";
            if (!IsPostBack)
            {
                InitData();
            }
        }
        private void InitData()
        {

            DataTable dt = new MW.BLL.B_ecan_domainvalue().GetDrpList("DOMAIN_ID='XWLB'");
            JBind.BindList(dropTypeId, dt, "DOMAINLABEL", "VALUE");
            //dropTypeId.Items.Insert(0, new ListItem("一级分类", "0"));

            if (Id > 0)
            {//edit
                model = bll.GetModel(Id);
                if (model != null)
                {
                    txtTitle.Text = model.Title;
                    dropTypeId.SelectedValue = model.Cid.ToString();
                    txtContents.Text = Utils.HtmlDecode(model.Content);
                    //txtUrl.Text = model.ShareUsers;
                    txtSort.Text = model.OderBy.ToString();
                    //this.fileFilePath.Attributes.Add("value", model.FilePath);
                    imgPhoto.ImageUrl = model.A4;
                    txtFj.Text = model.A3;
                }
            }
        }
        protected void btnSave_Click(object sender, EventArgs p1)
        {
            string filepath = "";
            //文件上传
            if (this.fileFilePath.HasFile)
            {
                JUpload up = new JUpload();
                up.FileSize = 10000;
                up.FileType = "image";
                up.IsChangeName = false;
                string rev = up.SaveFile(fileFilePath, "/UpLoads/wk/");

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
                filepath = rev;
            }
            int addID = 0;
            if (Id > 0)
            {
                model = bll.GetModel(Id);
                //if (hidAidUsers.Value == "0")
                //{
                //    model.AId = MW.BLL.B_TBAdmin.AId;
                //}
                //else
                //{
                //    model.AId = int.Parse(hidAidUsers.Value);
                //}
                model.CreateTime = DateTime.Now;
                model.Title = txtTitle.Text;
                model.Cid = int.Parse(dropTypeId.SelectedValue);
                model.Content = Utils.HtmlEncode(txtContents.Text);
                model.OderBy = int.Parse(txtSort.Text);
                model.A3 = txtFj.Text;
                if (filepath != "")
                {
                    model.A4 = filepath;
                }
                if (bll.Update(model))
                {
                    //B_Operationlog.CreateOperationLog(BasePage.UId, B_Operationlog.operationLogType.Edit, "信息", model.Title);
                    MessageBox.JSLoad(this, "alert('保存成功!');location.href='wklist.aspx?cid =" + typeid+"'");
                }
            }
            else
            {

                model.CreateTime = DateTime.Now;
                model.Title = txtTitle.Text;
                model.Cid = Utils.StrToInt(dropTypeId.SelectedValue, 0);
                model.Content = Utils.HtmlEncode(txtContents.Text);
                model.A4 = filepath;
                model.OderBy = int.Parse(txtSort.Text);
                model.A3 = txtFj.Text;
                model.CreateTime = DateTime.Now;
                bool falg = bll.Add(model);
                if (falg)
                {

                    //B_Operationlog.CreateOperationLog(BasePage.UId, B_Operationlog.operationLogType.Add, "信息", model.Title);
                    MessageBox.JSLoad(this, "alert('新增成功!');window.location.href='wklist.aspx?cid =" + typeid + "'");
                }
            }

        }

        //protected int PageID
        //{
        //    get
        //    {
        //        return LYRequest.GetInt("pageid",1);
        //    }
        //}
    }
}