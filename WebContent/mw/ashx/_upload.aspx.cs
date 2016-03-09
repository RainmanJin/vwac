using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using MW;
using MW.Common;

namespace PortalWeb.mw.ashx
{
    public partial class _upload : System.Web.UI.Page
    {
        private int filesize = 2000;//文件大小
        private string filetype = "image";//文件类型
        private int iswatermark = 0;//是否上传水印
        private string tofilepath = "/UpLoads/";
        private string recievetxtname = ""; //接收上传成功后的文件名
        private int ismultifile = 0;//是否返回多文件
        private string callback = ""; //回调

        protected void Page_Load(object sender, EventArgs e)
        {
            filesize = LYRequest.GetInt("filesize", 1024);
            filetype = LYRequest.GetString("filetype", "image");
            tofilepath = LYRequest.GetString("filepath", "/UpLoads/");
            recievetxtname = LYRequest.GetString("recievetxtname", "");
            ismultifile = LYRequest.GetInt("ismultifile", 0);
            callback = LYRequest.GetString("callback", "");

            int type = LYRequest.GetInt("type", 0);
            if (!IsPostBack)
            {
                if (!chkLogin(type)) return;

                if (recievetxtname == "")
                {
                    Lbl_Tip.Text = "缺少参数recievetxtname";
                    File_Upload.Visible = false;
                    Btn_Submit.Visible = false;
                }
            }

        }

        protected void Btn_Submit_Click(object sender, EventArgs e)
        {
            string strcallback = "";
            if (callback != "")
            {
                strcallback = "parent." + callback + "();";
            }
            if (File_Upload.PostedFile.ContentLength == 0)
            {
                Lbl_Tip.Text = "请选择文件";
                return;
            }

            string errors = string.Empty;
            string Pic = string.Empty;
            // 上传文件

            MW.Common.JUpload upload = new MW.Common.JUpload();
            upload.IsUsedFtp = false;
            upload.FileSize = filesize;
            upload.FileType = filetype;
            upload.IsWaterMark = Utils.StrToBool(iswatermark.ToString(), false);

            if (File_Upload.PostedFile.ContentLength > 0)
            {
                string result = upload.SaveFile(File_Upload, tofilepath);

                if (result == "0")//文件格式不正确
                {
                    errors = "文件格式不正确 " + filetype;
                }
                else if (result == "1") //文件大小超过filesizeK
                {
                    errors = "文件大小超过 " + filesize + "K";
                }
                else if (result == "3") //文件大小超过filesizeK
                {
                    errors = "非法的文件 ";
                }
                else
                {
                    result = "/" + result.TrimStart('/');
                    Pic = BasePage.GetWebPath + result;
                }

            }
            else
            {
                errors = "无数据提交";
            }
            if (errors != "")
            {
                Lbl_Tip.Text = errors;
            }
            else
            {
                Lbl_Tip.Text = "上传成功";
                if (ismultifile == 0)
                {
                    ClientScript.RegisterClientScriptBlock(Page.GetType(), "", "<script>parent.document.all." + recievetxtname + ".value='" + Pic + "';" + strcallback + "</script>");
                }
                else
                {

                    //string result_html = "<a  target=\"_blank\" href=\"" + result + "\" style=\"font-size:14px;color:#3c3c3c\">" + result.Substring(result.LastIndexOf("/") + 1) + "</a><br /> ";
                    ClientScript.RegisterClientScriptBlock(Page.GetType(), "", "<script>parent.document.all." + recievetxtname + ".value+='" + Pic + "\\n';" + strcallback + "</script>");

                }
            }
        }

        private bool chkLogin(int type)
        {
            //if (type == 1)
            //{
            //    return ART.BLL.B_TBAdmin.AId != 0;
            //}
            return BaseUi.uid!= "";
        }
    }
}