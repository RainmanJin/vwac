using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text.RegularExpressions;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using MW.BLL;
using MW.Common;

namespace Plugin.VWSurvey.Vote
{
    public partial class GetResult : System.Web.UI.Page
    {
        private MW.BLL.B_Votesubject bllsub = new B_Votesubject();
        private B_Votekey bllkey = new B_Votekey();

        private void method_3(int voteid, int sysid)
        {
            string strA = new B_Votecourse().GetReturnUrl(voteid);
            //if (num == 0)
            //{
            if (strA == "")
            {
                string html = "";
                if (FileHelper.FileExists(Utils.GetMapPath(MW.BasePage.GetWebPath + "/mw/onlinesurvey/result_" + voteid + ".htm")))
                {
                    html =
                        FileHelper.ReadFile(Utils.GetMapPath(MW.BasePage.GetWebPath + "/mw/onlinesurvey/result_" + voteid + ".htm"));
                }
                else
                {
                    html = FileHelper.ReadFile(Utils.GetMapPath(MW.BasePage.GetWebPath + "/mw/onlinesurvey/result.htm"));
                }
                html = html.Replace("{voteid}", voteid.ToString());
                base.Response.Write(html);
            }
            else
            {
                base.Response.Redirect(strA);
            }
            //}
        }
        private void WERR(int voteid, string msg)
        {
            string html = "";
            if (FileHelper.FileExists(Utils.GetMapPath(MW.BasePage.GetWebPath + "/mw/onlinesurvey/err_" + voteid + ".htm")))
            {
                html =
                    FileHelper.ReadFile(Utils.GetMapPath(MW.BasePage.GetWebPath + "/mw/onlinesurvey/err_" + voteid + ".htm"));
            }
            else
            {
                html = FileHelper.ReadFile(Utils.GetMapPath(MW.BasePage.GetWebPath + "/mw/onlinesurvey/err.htm"));
            }
            html = html.Replace("{voteid}", voteid.ToString());
            html = html.Replace("{msg}", msg);
            base.Response.Write(html);
             base.Response.End();
        }
        protected void Page_Load(object sender, EventArgs e)
        {
            if (base.Request["SubjectId"] == null && base.Request["VoteId"] == null)
            {
                WERR(0,"参数错误！");
                return;
            }
            int sysId = int.Parse(base.Request["SubjectId"]);
            int Id = int.Parse(base.Request["VoteId"]);
            int num2;
            if (this.Page.IsPostBack)
            {
                WERR(Id,"提交已经成功，请关闭浏览器！");
                return;
            }

            var modelvote = new B_Votecourse().GetModel(Id);

            if (modelvote.N_CodeSurvey == 1)
            {
                if (base.Request.Cookies["CheckCode"] == null)
                {
                    WERR(Id, "请不要重复提交，并请设置浏览器允许 Cookies!");
                    return;
                }
                if (string.Compare(base.Request.Cookies["CheckCode"].Value, base.Request["Check"].ToString(), true) != 0)
                {
                    WERR(Id, "验证码错误，请重新输入!");
                    return;
                }
                base.Response.Cookies["CheckCode"].Expires = DateTime.Now;
            }
            if (!new B_Votecourse().GetOverDate(Id))
            {
                WERR(Id, "调查已经过期!");
                return;
            }
            //是否是正式开始
            bool isdovote = modelvote.DT_StartDate < DateTime.Now;
            DataTable list = bllsub.GetSubToKey(sysId, 0, " and s.Parentid<>0 ");

            int num4 = 0;
            //if (Convert.IsDBNull(this.Session["TestID"]))
            //{//(Convert.ToInt32(row3["N_LogicSurvey"]) != 1) ||

            //    #region N_LogicSurvey!=1
            //    switch (int.Parse(row3["N_Option"].ToString()))
            //    {
            //        case 1:
            //            if (T_IP.CheckIP(base.Request.ServerVariables["REMOTE_ADDR"], sysId, Convert.ToInt32(row3["N_IPHour"])))
            //            {
            //                break;
            //            }
            //            MessageBox.Alert(this, "您不能重复投票!");
            //            return;

            //        case 2:
            //            if (T_IP.CheckIP(base.Request.ServerVariables["REMOTE_ADDR"], sysId, 0))
            //            {
            //                break;
            //            }
            //            MessageBox.Alert(this, "您不能重复投票!");
            //            return;

            //        case 3:
            //            {
            //                Regex regex = new Regex(T_System.GetIpRule(sysId), RegexOptions.Compiled | RegexOptions.IgnoreCase);
            //                if (regex.IsMatch(base.Request.ServerVariables["REMOTE_ADDR"]))
            //                {
            //                    break;
            //                }
            //                MessageBox.Alert(this, "您不能重复投票!");
            //                return;
            //            }
            //        case 4:
            //            if (base.Request.Cookies["HCheck"] == null)
            //            {
            //                HttpCookie cookie = new HttpCookie("HCheck");
            //                cookie["HCheck"] = sysId.ToString();
            //                TimeSpan span = new TimeSpan(Convert.ToInt32(row3["N_IPHour"]), 0, 0);
            //                cookie.Expires = DateTime.Now + span;
            //                base.Response.Cookies.Add(cookie);
            //                break;
            //            }
            //            if (!(base.Request.Cookies["HCheck"].Value == sysId.ToString()))
            //            {
            //                break;
            //            }
            //            MessageBox.Alert(this, "您不能重复投票!");
            //            return;
            //    }
            //    #endregion
            //}
            //else
            //{
            //    num2 = Convert.ToInt32(this.Session["TestID"]);
            //    if (num2 == 0)
            //    {
            //        num2 = T_Result.AddRes(0, 0, sysId.ToString(), 0, sysId, num4);
            //        if (num2 != Convert.ToInt32(this.Session["TestID"]))
            //        {
            //            #region
            //            switch (int.Parse(T_System.GetNameById(int.Parse(base.Request["SubjectId"])).Rows[0]["N_Option"].ToString()))
            //            {
            //                case 1:
            //                    if (T_IP.CheckIP(base.Request.ServerVariables["REMOTE_ADDR"], sysId, Convert.ToInt32(row3["N_IPHour"])))
            //                    {
            //                        break;
            //                    }
            //                    MessageBox.Alert(this,"您不能重复投票!");
            //                    return;

            //                case 2:
            //                    if (T_IP.CheckIP(base.Request.ServerVariables["REMOTE_ADDR"], sysId, 0))
            //                    {
            //                        break;
            //                    }
            //                    MessageBox.Alert(this,"您不能重复投票!");
            //                    return;

            //                case 3:
            //                    {
            //                        Regex regex2 = new Regex(T_System.GetIpRule(sysId), RegexOptions.Compiled | RegexOptions.IgnoreCase);
            //                        if (regex2.IsMatch(base.Request.ServerVariables["REMOTE_ADDR"]))
            //                        {
            //                            break;
            //                        }
            //                        MessageBox.Alert(this,"您不在调查范围,不能进行投票!");
            //                        return;
            //                    }
            //                case 4:
            //                    {
            //                        if (base.Request.Cookies["HCheck"] != null)
            //                        {
            //                            if (base.Request.Cookies["HCheck"].Value == sysId.ToString())
            //                            {
            //                                MessageBox.Alert(this,"您不能重复投票!");
            //                                return;
            //                            }
            //                            break;
            //                        }
            //                        HttpCookie cookie2 = new HttpCookie("HCheck");
            //                        cookie2["HCheck"] = sysId.ToString();
            //                        TimeSpan span2 = new TimeSpan(Convert.ToInt32(row3["N_IPHour"]), 0, 0);
            //                        cookie2.Expires = DateTime.Now + span2;
            //                        base.Response.Cookies.Add(cookie2);
            //                        break;
            //                    }
            //            }
            //            #endregion
            //        }
            //        this.Session["TestID"] = num2;
            //    }
            //    num4 = Convert.ToInt32(base.Request["LogicPageId"]);
            //    list = new T_Logic().T_LogicGetSubject(num4);

            //}
            num2 = AddRes(isdovote, 0, 0, sysId.ToString(), 0, sysId, num4, Id);

            foreach (DataRow row2 in list.Rows)
            {
                int num8 = 0;
                int num5 = 0;
                int num7 = 0;
                foreach (DataRow row in bllkey.GetList(0,"N_SubId=" + row2["N_SubId"] + ""," N_OrderId,N_KeyId").Tables[0].Rows)
                {
                    string[] strArray2;
                    switch (int.Parse(row["N_Type"].ToString()))
                    {
                        case 1:
                            if (base.Request[row2["subid"].ToString()].ToString() != "")
                            {
                                AddRes(isdovote, int.Parse(row["N_KeyId"].ToString()), int.Parse(row2["subid"].ToString()), base.Request[row2["subid"].ToString()].ToString(), num2, sysId, num4, Id);
                            }
                            break;
                        case 2:
                            if ((base.Request[row2["subid"].ToString()] != null) && (num8 == 0))
                            {
                                AddRes(isdovote, int.Parse(base.Request[row2["subid"].ToString()].ToString()), int.Parse(row2["subid"].ToString()), "2", num2, sysId, num4, Id);
                                num8 = 1;
                            }
                            break;
                        case 3:
                            if ((base.Request[row2["subid"].ToString()] == null) || (num7 != 0))
                            {
                                break;
                            }
                            //row["N_SubId"].ToString();
                            strArray2 = base.Request[row2["subid"].ToString()].Split(new char[] { ',' });
                            if (strArray2.Length != 0)
                            {
                                foreach (string str in strArray2)
                                {
                                    AddRes(isdovote, int.Parse(str), int.Parse(row2["subid"].ToString()), "3", num2, sysId, num4, Id);
                                }
                            }
                            num7 = 1;
                            break;
                        case 4:
                            if ((base.Request[row2["subid"].ToString()].ToString() != "") && (num5 == 0))
                                {
                                    AddRes(isdovote, int.Parse(base.Request[row2["subid"].ToString()].ToString()), int.Parse(row2["subid"].ToString()), "4", num2, sysId, num4, Id);
                                    num5 = 1;
                                }
                                break;
                        case 5:
                                if (base.Request[row2["subid"].ToString()].ToString() != "")
                                {
                                    AddRes(isdovote, int.Parse(row["N_KeyId"].ToString()), int.Parse(row2["subid"].ToString()), base.Request[row2["subid"].ToString()].ToString(), num2, sysId, num4, Id);
                                }
                                break;

                    }


                }
            }
            //if (Convert.ToInt32(row3["N_LogicSurvey"]) != 1)
            //{
            this.method_3(Id,sysId);
            //}
            //else
            //{
            //    //new T_Logic();
            //    int num3 = this.method_0(num4, sysId, num2);
            //    if (num3 != 0)
            //    {
            //        base.Response.Redirect("Vote/BiceLoc" + num3.ToString() + ".htm");
            //    }
            //    else
            //    {
            //        this.Session["TestID"] = 0;
            //        this.method_3(sysId, num2);
            //    }
            //}
        }
        private int AddRes(bool istrue, int KeyId, int SubId, string Reuslt, int TestID, int SysID, int N_LogicPageId, int VoteID)
        {
            int i = 0;
             if (istrue)
            {
                i = B_Voteresult.AddRes(KeyId, SubId, Reuslt, TestID, SysID, N_LogicPageId, VoteID);
            }
            else
            {
                i = B_VoteresultTmp.AddRes(KeyId, SubId, Reuslt, TestID, SysID, N_LogicPageId, VoteID);
            }
            return i;
        }
    }
}
