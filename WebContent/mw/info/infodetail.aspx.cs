using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using MW;
using MW.Common;

namespace PortalWeb.mw
{
    public partial class InfoDetail : System.Web.UI.Page
    {
        protected int Id = 0;
        private MW.BLL.B_Article bll = new MW.BLL.B_Article();
        private MW.Model.M_Mw_article model = new MW.Model.M_Mw_article();
        protected string plg = "InfoAdd";

        protected void Page_Load(object sender, EventArgs e)
        {
            Id = MW.Common.LYRequest.GetInt("id", 0);

            if (Id > 0) plg = "InfoEdit";
            if (!IsPostBack)
            {
                //txtAidUsers.Attributes.Add("onclick","javascript:openDlg(\"UserDialog\",\"查找用户\",\"Controls/_UserDialog.aspx?hidCtl="+hidAidUsers.ClientID + "&txtCtl="+txtAidUsers.ClientID + "\",\"500px\",\"auto\",\""+plg+" \");");
                InitData();
            }
        }
        private void InitData()
        {
            hidurl.Value = base.Request.UrlReferrer.PathAndQuery;
            //DataTable dt = new MW.BLL.B_ecan_domain().GetListByParent(PageID);
            //JBind.BindList(dropTypeId, JTree.GetClassTree(dt, "ParentId", PageID.ToString(), "DictionaryName"));
            DataTable dt = new MW.BLL.B_ecan_domainvalue().GetDrpList("DOMAIN_ID='xwlb'");
            JBind.BindList(dropTypeId, dt, "DOMAINLABEL", "VALUE");
            //dropTypeId.Items.Insert(0, new ListItem("一级分类", "0"));
            if (Id > 0)
            {//edit
                model = bll.GetModel(Id);
                if (model != null)
                {
                    txtTitle.Text = model.Title;
                    dropTypeId.SelectedValue = model.TypeId;
                    dropTypeId.Enabled = false;
                    txtContents.Text = Utils.HtmlDecode(model.Contents);
                    txtShareUsers.Text = model.ShareUsers;
                    txtSort.Text = model.Sort.ToString();
                    //this.fileFilePath.Attributes.Add("value", model.FilePath);
                    imgPhoto.ImageUrl = model.FilePath;
                    txtFj.Text = model.fj;
                    txtBeginTime.Text = model.CreateTime.ToString("yyyy-MM-dd");
                }
            }
            else
            {
                dropTypeId.SelectedValue = PageID;
                if (PageID != "") dropTypeId.Enabled = false;
                txtBeginTime.Text = DateTime.Now.ToString("yyyy-MM-dd");
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
                up.IsChangeName = true;
                string rev = up.SaveFile(fileFilePath, "/upLoads/info/");

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
                model.CreateTime = Utils.StrToDateTime(txtBeginTime.Text,DateTime.Now);
                model.UpdateTime = DateTime.Now;
                model.Title = txtTitle.Text;
                model.TypeId = dropTypeId.SelectedValue;
                model.ShareUsers = txtShareUsers.Text;
                model.Contents = Utils.HtmlEncode(txtContents.Text);
                model.Sort = int.Parse(txtSort.Text);
                model.fj = txtFj.Text;
                if (filepath != "")
                {
                    model.FilePath = filepath;
                }
               // addID = Id;
                if (bll.Update(model))
                {
                    MessageBox.AlertToUrl(this, "保存成功!", hidurl.Value);
                    //B_Operationlog.CreateOperationLog(BasePage.UId, B_Operationlog.operationLogType.Edit, "信息", model.Title);
                   // MessageBox.JSLoad(this, "alert('保存成功!');location.href='infolist.aspx?typeId =" + Id + "'");
                }
            }
            else
            {
                model.Audit = 1;
                model.RecoveryState = 0;
                //if (hidAidUsers.Value == "0")
                //{
                model.AId = BasePage.UId.ToString();
                //}
                //else
                //{
                //    model.AId = int.Parse(hidAidUsers.Value);
                //}
                model.ShareUsers = txtShareUsers.Text;
                model.UpdateTime = DateTime.Now;
                model.Title = txtTitle.Text;
                model.TypeId = dropTypeId.SelectedValue;
                model.Contents = Utils.HtmlEncode(txtContents.Text);
                model.FilePath = filepath;
                model.Sort = int.Parse(txtSort.Text);
                model.fj = txtFj.Text;
                model.CreateTime = Utils.StrToDateTime(txtBeginTime.Text, DateTime.Now);
                bool falg = bll.Add(model);
                if (falg)
                {
                    MessageBox.AlertToUrl(this, "保存成功!", hidurl.Value);
                    //B_Operationlog.CreateOperationLog(BasePage.UId, B_Operationlog.operationLogType.Add, "信息", model.Title);
                    //MessageBox.JSLoad(this, "alert('保存成功!');location.href='infolist.aspx?typeId =" + Id + "'");
                }
            }

        }

        protected string PageID
        {
            get
            {
                return LYRequest.GetString("pageid");
            }
        }
    }
}