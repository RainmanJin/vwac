using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using MW;
using MW.BLL;
using MW.Common;

namespace Plugin.VWSurvey.Admini.VWSurvey
{
    public partial class StyleDetail : System.Web.UI.Page
    {
        private int sysid = 0, id = 0;
    private MW.BLL.B_Votesystem bll = new MW.BLL.B_Votesystem();

        protected void Page_Load(object sender, EventArgs e)
        {


            sysid = LYRequest.GetInt("sysid", 0);
            id = LYRequest.GetInt("id", 0);
            if (!IsPostBack)
            {
                string path = Utils.GetMapPath(MW.BasePage.GetWebPath + "/mw/onlinesurvey/" + id + ".html");
                this.Label3.Text = "引用相对路径： /uploads/文件名 ,若图像|视频等不显示，请参照修改路径";
                InitData(path);
                hidid.Value = id.ToString();
            }
        }

        private void InitData(string path)
        {
            if (!MW.Common.FileHelper.FileExists(path))
            {
                string votetemp = "";
                if (FileHelper.FileExists(Utils.GetMapPath(BasePage.GetWebPath + "/mw/onlinesurvey/votetemplate_" + id + ".htm")))
                {
                    votetemp =
                        FileHelper.ReadFile(Utils.GetMapPath(BasePage.GetWebPath + "/mw/onlinesurvey/votetemplate_" + id + ".htm")) + "\n"; ;
                }
                else
                {
                    votetemp = FileHelper.ReadFile(Utils.GetMapPath(BasePage.GetWebPath + "/mw/onlinesurvey/votetemplate.htm")) + "\n";
                }
                Build build = new Build();
                var model = new B_Votecourse().GetModel(id);
                votetemp =
                     votetemp.Replace("{id}", model.Id.ToString())
                            .Replace("{path}", BasePage.GetWebPath)
                            .Replace("{title}", model.C_Title)
                            .Replace("{teacher}", model.C_Tearcher)
                            .Replace("{address}", model.C_Adrees)
                            .Replace("{starttime}", model.DT_StartDate.ToShortDateString())
                            .Replace("{endtime}", model.DT_OverDate.ToShortDateString());

                votetemp = votetemp.Replace("{Content}", build.PubBuildHtml(id,sysid)).Replace("{Images}", BasePage.GetWebPath + "/onlinesurvey/SysImages");
                if (votetemp.IndexOf("{QR}", StringComparison.OrdinalIgnoreCase) > 0)
                {
                   // this.CreateQR(model.N_SysId);
                    votetemp = votetemp.Replace("{QR}", string.Concat(new object[] { "<img src=\"", BasePage.GetWebPath, "/mw/onlinesurvey/qr/", model.Id, ".gif\" width=\"100\" height=\"100\" />" }));
                }
                FileHelper.WriteFile(Utils.GetMapPath(BasePage.GetWebPath + "/mw/onlinesurvey/" + id + ".html"), votetemp);
                //MessageBox.Alert(this, "生成投票问卷成功！");
            }
            this.TextBox1.Text = FileHelper.ReadFile(path);
        }
        protected void btnSave_Click(object sender, EventArgs e)
        {
            if (hidid.Value!="0")
            {
                string path = MW.BasePage.GetWebPath + "/mw/onlinesurvey/" + hidid.Value + ".html";
                MW.Common.FileHelper.WriteFile(Utils.GetMapPath(path),this.TextBox1.Text);
                MessageBox.AlertToUrl(this, "编辑成功！", "votelist.aspx");
              
            }
        }
    }
}