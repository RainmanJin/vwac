using System;
using System.Collections.Generic;
using System.Data;
using System.Drawing.Imaging;
using System.IO;
using System.Linq;
using System.Text;
using System.Web;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using MW;
using MW.BLL;
using MW.Common;
using ThoughtWorks.QRCode.Codec;

namespace Plugin.VWSurvey.Admini.VWSurvey
{
    public partial class VoteList : System.Web.UI.Page
    {
        private MW.BLL.B_Votecourse bll = new MW.BLL.B_Votecourse();

        private string strWhere, SearchName,teachername,starttime,endtime;
        private int typeid = 0,pagesize=20;
        protected void Page_Load(object sender, EventArgs e)
        {
            SearchName = MW.Common.LYRequest.GetString("SearchName", true);
            teachername = MW.Common.LYRequest.GetString("teachername", true);
            starttime = MW.Common.LYRequest.GetString("starttime");
            endtime = MW.Common.LYRequest.GetString("endtime");
            typeid = LYRequest.GetInt("typeid", 0);
            pagesize = LYRequest.GetInt("pagesize", 20);

            if (!IsPostBack)
            {
                InitData();
                BindList();
            }
        }

        private void InitData()
        {
            //DataTable dt = new MW.BLL.B_ecan_domainvalue().GetList("DOMAIN_ID='SURVEYTYPE'").Tables[0];
            //JBind.BindList(drpType, dt, "DOMAINLABEL", "ID");
            //drpType.Items.Insert(0, new ListItem("全部分类", "0"));
            //drpType.SelectedValue = typeid.ToString();

            txtSearchName.Text = SearchName;
            txtTeacherName.Text = teachername;
            txtBeginTime.Text = starttime;
            txtEndTime.Text = endtime;
            drpSize.SelectedValue = pagesize.ToString();

        }
        private void BindList()
        {

            #region __________Where语句__________

            strWhere = "  1=1";
            if (typeid > 0)
            {
                strWhere += " and N_Type=" + typeid;
            }
            if (SearchName != "")
            {
                //Log.SetSucLog(SearchName);
                strWhere += " and (C_Title like '%" + SearchName + "%')";
            }
            if (teachername != "")
            {
                strWhere += " and (C_Tearcher like '%" + teachername + "%')";
            }
            if (starttime != "")
            {
                strWhere += " and (DT_StartDate>='" + starttime + "')";
            }
            if (endtime != "")
            {
                strWhere += " and (DT_StartDate<='" + endtime + "')";
            }
            #endregion

            rpt_List.PageLink = "votelist.aspx?Search=Yes&pagesize="+pagesize+"&TypeId=" + typeid + "&starttime=" + starttime + "&endtime=" + endtime + "&SearchName=" + Utils.UrlEncode(SearchName) + "&teachername=" + Utils.UrlEncode(teachername);
            rpt_List.PageSize = pagesize;
            rpt_List.CurrentPage = LYRequest.GetInt("page", 1);

            DataSet ds = MW.BLL.mw_getlistbypage.GetDataSet_NoCache("mw_votecourse", "*", strWhere, "", "Id DESC", "Id", rpt_List.CurrentPage, rpt_List.PageSize, 1);

            rpt_List.RecordCount = Convert.ToInt32(ds.Tables[0].Rows[0][0]);
            JBind.BindList(rpt_List, ds.Tables[1]);
        }
        protected void btnSearch_Click(object sender, EventArgs p1)
        {
            StringBuilder builder = new StringBuilder("votelist.aspx?Search=Yes");
            //builder.Append("&TypeId="+JBind.GetSelectedList(drpType));
            builder.Append("&pagesize=" + drpSize.SelectedValue);
            if (!string.IsNullOrEmpty(this.txtSearchName.Text.Trim()))
            {
                builder.Append("&SearchName=" + Utils.UrlEncode(this.txtSearchName.Text.Trim()));
            }
            if (!string.IsNullOrEmpty(this.txtBeginTime.Text.Trim()))
            {
                builder.Append("&starttime=" + Utils.UrlEncode(this.txtBeginTime.Text.Trim()));
            }
            if (!string.IsNullOrEmpty(this.txtEndTime.Text.Trim()))
            {
                builder.Append("&endtime=" + Utils.UrlEncode(this.txtEndTime.Text.Trim()));
            }
            if (!string.IsNullOrEmpty(this.txtTeacherName.Text.Trim()))
            {
                builder.Append("&teachername=" + Utils.UrlEncode(this.txtTeacherName.Text.Trim()));
            }
            base.Response.Redirect(builder.ToString());
        }
        protected void lbtnDeletes_Click(object sender, EventArgs p1)
        {
#warning BasePage.IsPermissions(1)
            if (BasePage.IsPermissions(1))
            {
                //int num = 0;
                foreach (RepeaterItem item in this.rpt_List.Items)
                {
                    HtmlInputCheckBox box = (HtmlInputCheckBox)item.FindControl("cheId");
                    if (box.Checked)
                    {
                        bll.Delete(int.Parse(box.Value));
                        // num++;
                    }
                }
                base.Response.Redirect(HttpContext.Current.Request.Url.PathAndQuery);
            }
            else
            {
                MessageBox.Alert(this, "没有删除权限!");
            }
        }
        protected void rpt_List_ItemCommand(object source, RepeaterCommandEventArgs p1)
        {
            object num=p1.CommandArgument.ToString();
            if (p1.CommandName == "lbtnDelete")
            {
#warning delte IsPermissions(0x6c)
                if (!BasePage.IsPermissions(1))
                {
                    MessageBox.Alert(this, "没有删除权限!");
                }
                else
                {
                    bll.Delete(Utils.StrToInt(num,0));
                    base.Response.Redirect(HttpContext.Current.Request.Url.PathAndQuery);
                }
            }
            else if (p1.CommandName == "lbtnBuild")
            {
                var arr = num.ToString().Split('|');

                string votetemp = "";
                if (FileHelper.FileExists(Utils.GetMapPath(BasePage.GetWebPath + "/mw/onlinesurvey/votetemplate_" + arr[0] + ".htm")))
                {
                    votetemp =
                        FileHelper.ReadFile(Utils.GetMapPath(BasePage.GetWebPath + "/mw/onlinesurvey/votetemplate_" + arr[0] + ".htm")) + "\n"; ;
                }
                else
                {
                    votetemp = FileHelper.ReadFile(Utils.GetMapPath(BasePage.GetWebPath + "/mw/onlinesurvey/votetemplate.htm")) + "\n"; 
                }
                Build build = new Build();
                
                var model = bll.GetModel(Utils.StrToInt(arr[0], 0));
                votetemp =
                    votetemp.Replace("{id}", model.Id.ToString())
                            .Replace("{path}", BasePage.GetWebPath)
                            .Replace("{title}", model.C_Title)
                            .Replace("{course}",model.C_course)
                            .Replace("{teacher}", model.C_Tearcher)
                            .Replace("{address}", model.C_Adrees)
                            .Replace("{starttime}", model.DT_StartDate.ToShortDateString())
                            .Replace("{endtime}", model.DT_OverDate.ToShortDateString())
                            .Replace("{coursestarttime}", model.CourseStart.ToShortDateString())
                            .Replace("{courseendtime}", model.CourseEnd.ToShortDateString());

                votetemp = votetemp.Replace("{Content}", build.PubBuildHtml(model.Id, Utils.StrToInt(arr[1], 0))).Replace("{Images}", BasePage.GetWebPath + "/mw/onlinesurvey/SysImages");
                if (votetemp.IndexOf("{QR}", StringComparison.OrdinalIgnoreCase) > 0)
                {
                    this.CreateQR(model.N_SysId);
                    votetemp = votetemp.Replace("{QR}", string.Concat(new object[] { "<img src=\"", BasePage.GetWebPath, "/mw/onlinesurvey/qr/", model.Id, ".gif\" width=\"100\" height=\"100\" />" }));
                }
                if (FileHelper.FileExists(Utils.GetMapPath(BasePage.GetWebPath + "/mw/onlinesurvey/" + arr[0] + ".html")))
                {
                    System.IO.File.Delete(Utils.GetMapPath(BasePage.GetWebPath + "/mw/onlinesurvey/" + arr[0] + ".html"));
                }
                FileHelper.WriteFile(Utils.GetMapPath(BasePage.GetWebPath + "/mw/onlinesurvey/" + arr[0] + ".html"), votetemp);
                MessageBox.Alert(this, "生成投票问卷成功！");

            }
            else if (p1.CommandName == "lbtnCopy")
            {
                bll.CopyVote(Utils.StrToInt(num, 0));
                MessageBox.AlertToUrl(this, "调查复制成功", "votelist.aspx");
            }
        }
        private void CreateQR(int voteid)
        {
            try
            {
                QRCodeEncoder encoder2 = new QRCodeEncoder
                {
                    QRCodeEncodeMode = QRCodeEncoder.ENCODE_MODE.BYTE,
                    QRCodeScale = 4,
                    QRCodeVersion = 5,
                    QRCodeErrorCorrect = QRCodeEncoder.ERROR_CORRECTION.M
                };
                string str3 = string.Concat(new object[] { "http://", HttpContext.Current.Request.ServerVariables["HTTP_HOST"], BasePage.GetWebPath, "/mw/onlinesurvey/", voteid, ".html" });
                encoder2.Encode(str3).Save(base.Server.MapPath(string.Concat(new object[] { BasePage.GetWebPath, "/mw/onlinesurvey/qr/", voteid, ".gif" })), ImageFormat.Gif);

            }
            catch
            {
                MessageBox.Alert(this, "生成二维码图片失败！");
            }
        }

        protected void txtEndTime_TextChanged(object sender, EventArgs e)
        {

        }
    }
}