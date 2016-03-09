using System;
using System.Collections.Generic;
using System.Drawing.Imaging;
using System.IO;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using MW.Common;
using ThoughtWorks.QRCode.Codec;

namespace Plugin.VWSurvey.Admini.VWSurvey
{
    public partial class SurveyPub : System.Web.UI.Page
    {
        private int sysid = 0,id=0;
        private MW.BLL.B_Votesystem bll = new MW.BLL.B_Votesystem();
        private MW.Model.M_Mw_votesystem model = new MW.Model.M_Mw_votesystem();

        protected void Page_Load(object sender, EventArgs e)
        {
            sysid = LYRequest.GetInt("sysid", 0);
            id = LYRequest.GetInt("id", 0);
            if (!IsPostBack)
            {
                InitData();
            }
        }
        private void InitData()
        {
            if (sysid > 0&&id>0)
            {//edit
                model = bll.GetModel(sysid);
                if (model != null)
                {
                    txtContent.Text = new Build().PubBuildHtml(id,sysid);
                    txtUrl.Text = string.Concat(new object[] { MW.Common.Configs.GetConfigValue("/configuration/url"), MW.BasePage.GetWebPath, "/onlinesurvey/", id, ".html" });
                    string path = string.Concat(new object[] { MW.BasePage.GetWebPath, "/mw/onlinesurvey/qr/", id, ".gif" });
                    if (!FileHelper.FileExists(Utils.GetMapPath(path)))
                    {
                        QRCodeEncoder encoder = new QRCodeEncoder
                        {
                            QRCodeEncodeMode = QRCodeEncoder.ENCODE_MODE.BYTE,
                            QRCodeScale = 4,
                            QRCodeVersion = 5,
                            QRCodeErrorCorrect = QRCodeEncoder.ERROR_CORRECTION.M
                        };
                        string content = txtUrl.Text;
                        Utils.CreateDir(Utils.GetMapPath(path));
                        encoder.Encode(content).Save(Utils.GetMapPath(path), ImageFormat.Gif);
                        // this.Image1.ImageUrl = string.Concat(new object[] { BasePage.AbsPath, "Vote/qr/", sysid, ".gif" });
                    }
                    this.Image1.ImageUrl = path;
                }
            }
        }
    }
}