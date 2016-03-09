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
    public partial class _upfile : System.Web.UI.Page
    {
        protected int filesize = 2000;//文件大小
        protected string filetype = "image";//文件类型
        protected bool iswatermark = false;//是否上传水印
        protected string tofilepath = "/upLoads/";

        protected void Page_Load(object sender, EventArgs e)
        {
            filesize = LYRequest.GetInt("filesize", 1024);
            filetype = LYRequest.GetString("filetype", "image");
            tofilepath = LYRequest.GetString("filepath", "/upLoads/");
            int type = LYRequest.GetInt("type", 0);
            if (!IsPostBack)
            {
                string errors = string.Empty;
                string Pic = string.Empty;
                string file = "";

                if (!chkLogin(type))
                {
                    errors = "未登录，请登陆后上传资源！";
                }
                else
                {// 上传文件
                    HttpFileCollection filecollection = HttpContext.Current.Request.Files;
                    HttpPostedFile hpf = filecollection.Get("filedata");
                    if (hpf == null) hpf = filecollection.Get(0);
                    MW.Common.JUpload upload = new MW.Common.JUpload();
                    upload.IsUsedFtp = false;
                    upload.FileSize = filesize;
                    upload.FileType = filetype;
                    upload.IsWaterMark = iswatermark;
                    try
                    {
                        if (hpf.ContentLength > 0)
                        {
                            string result = upload.SaveFile(hpf, tofilepath);

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
                                file = hpf.FileName;
                            }

                        }
                        else
                        {
                            errors = "无数据提交";
                        }
                    }
                    catch (Exception)
                    {

                        errors = "没有上传文件";
                    }

                }

                HttpContext.Current.Response.Write("{'err':'" + jsonString(errors) + "','msg':'" + Pic + "','file':'" + jsonString(file) + "'}");
                HttpContext.Current.Response.End();
            }

        }
        string jsonString(string str)
        {
            str = str.Replace("\\", "\\\\");
            str = str.Replace("/", "\\/");
            str = str.Replace("'", "\\'");
            return str;
        }

        private bool chkLogin(int type)
        {
            bool flag = BaseUi.uid != "";
            if (!flag)
            {
                if (base.Request.UserAgent == "Shockwave Flash")
                {
                    //usr=Id%3db2TLzpLGjVo%253d%26UserName%3dojEAWbiblW8%253d%26
                    return true;
                }
            }
            return flag;
        }
    }
}