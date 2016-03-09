using MW;
using System;
using System.Data;
using System.Web.UI;
using MW.BLL;
using MW.Common;

namespace Plugin.VWSurvey
{
    
    public class Build : Page
    {
        private MW.BLL.B_Votesubject bllsub=new B_Votesubject();
        private B_Votekey bllkey= new B_Votekey();

        /// <summary>
        /// 生成调查html
        /// </summary>
        /// <param name="id">调查问卷ID</param>
        /// <param name="sysid">调查模版ID</param>
        /// <returns></returns>
        public string PubBuildHtml(int id,int sysid)
        {
            string str = "<form name=\"VWVoteForm\" id=\"VWVoteForm\" method=\"post\" action=\"" + BasePage.GetWebPath + "/mw/onlinesurvey/getresult.aspx\" > <!--onSubmit='return CheckForm(this)'-->";
            var modelvote = new B_Votecourse().GetModel(id);

            object obj2 = str;
            str = string.Concat(new object[] { obj2, "<input name=\"VoteId\" type=\"hidden\" value=\"", id, "\"><input name=\"SubjectId\" type=\"hidden\" value=\"", sysid, "\">" });
            str += "<div class=\"survey\">";

            int xh = 1;
            //获取父级
            DataTable list = bllsub.GetList(0, "N_SubId,C_SubTitle", "Parentid=0 and N_SysId=" + sysid, "").Tables[0];
            if (list != null && list.Rows.Count > 0)
            {//如果分级，按分级数据显示
                foreach (DataRow dataRow in list.Rows)
                {
                    int jj = 0;
                    var subdata = bllsub.GetSubToKey(sysid, Utils.StrToInt(dataRow["N_SubId"], 0));
                    if (subdata.Rows.Count > 0)
                    {
                        foreach (DataRow dataRow2 in subdata.Rows)
                        {
                            jj++;
                            str = str + "<div class=\"qa" + ((xh * jj > 1) ? " nodisplay" : "") + " \">" + "<h2>" + dataRow["C_SubTitle"].ToString().Replace("\n", "<br/>") + "</h2>" + "<p>" + xh.ToString() + ((jj > 0) ? "-" + jj : "") + ". " + dataRow2["C_SubTitle"].ToString().Replace("\n", "<br/>") + "</p>";
                            GetTable(dataRow2, ref str);
                            str = str + "</div>";
                        }
                        xh++;
                    }
                    //else
                    //{
                    //    //无二级项目
                    //    jj++;
                    //    str = str + "<div class=\"qa" + ((xh * jj > 1) ? " nodisplay" : "") + " \">" + "<h2>" + dataRow["C_SubTitle"].ToString().Replace("\n", "<br/>") + "</h2>" + "<p>" + xh.ToString() + ((jj > 0) ? "-" + jj : "") + ". " + dataRow2["C_SubTitle"].ToString().Replace("\n", "<br/>") + "</p>";
                    //    GetTable(dataRow, ref str);
                    //    str = str + "</div>";
                    //}
                }

            }
            //else
            //{
            //    foreach (
            //        DataRow dataRow2 in bllsub.GetSubToKey(sysid, 0, " and s.Parentid<>0 ").Rows)
            //    {
            //        int parentid = Utils.StrToInt(dataRow2["parentid"], 0);
            //        string pt = parentid > 0 ? "<h2>" + bllsub.GetName(parentid) + "</h2>" : "";
            //        str = str + "<div class=\"qa" + ((xh > 1) ? " nodisplay" : "") + " \">" + "<h2>" + pt.Replace("\n", "<br/>") + "</h2>" + "<p>" + xh.ToString() + " " + ". " + dataRow2["C_SubTitle"].ToString().Replace("\n", "<br/>") + "</p>";
            //        GetTable(dataRow2, ref str);
            //        str = str + "</div>";
            //        xh++;
            //    }
            //}


            if (modelvote.N_CodeSurvey == 1)
            {
                str = str + "<div class=\"qa nodisplay\"><p>" + (xh++).ToString() + ". 请输入图片中文字</p>";
                str = str + "<input type=\"text\" name=\"Check\" style=\"width:100px\" /> <img src=\"" + BasePage.GetWebPath + "/onlinesurvey/Code.aspx\"><br/>";
                str = str + "</div>";
            }
            str = str +
                  "</div><div class=\"btn\"> <a id=\"btnpre\">上一页<br />Previous</a> <span></span> <a id=\"btnnext\">下一页<br />Next</a> </div>";
            //str = str + "<br><input type=\"submit\" name=\"Submit\" value=\"提 交\" style=\"width:100px\">" + "</form>";
            str=str+"</form>";
            
            return str;
        }

        private void GetTable(DataRow row2, ref string str)
        {
            DataTable table3 = bllkey.GetList(0, "N_SubId=" + row2["N_SubId"] + "", " N_OrderId,N_KeyId").Tables[0];
            int num2 = 0;
            foreach (DataRow row in table3.Rows)
            {
                num2++;
                int ntype = int.Parse(row["N_Type"].ToString());
                switch (ntype)
                {
                    case 1:
                        #region
                        if (row2["N_Need"].ToString() == "1")
                        {
                            str = str + row["C_KeyTitle"].ToString() + "  <input type=\"text\" name=\"" + row2["subid"].ToString() + "\" check=\"^\\S+$\" warning=\"" + row["C_KeyTitle"].ToString() + "不能为空\" >";
                        }
                        else
                        {
                            if (!Utils.IsNullOrEmpty(row["C_Rule"]) && !row["C_Rule"].ToString().StartsWith("不限制"))
                            {
                                string[] strArray17 = row["C_Rule"].ToString().Split(new char[] { '`' });
                                if (strArray17[1] != "*")
                                {
                                    str = str + row["C_KeyTitle"].ToString() + "" + "<input type=\"text\" name=\"" + row2["subid"].ToString() + "\" check=\"" + strArray17[1] + "\" warning=\"" + row["C_KeyTitle"].ToString() + "格式不正确,应填 " + strArray17[0] + "\" >";
                                }
                                else
                                {
                                    str = str + row["C_KeyTitle"].ToString() + "" + "<input type=\"text\" name=\"" + row2["subid"].ToString() + "\">";
                                }
                            }
                            else
                            {
                                str = str + row["C_KeyTitle"].ToString() + " <input type=\"text\" name=\" " + row2["subid"].ToString() + "\">";
                            }

                        }
                        #endregion
                        break;
                    case 2:
                        #region
                        if (num2 == 1)
                        {
                            str = str + "<ul class=\"option\">";
                        }
                        if (row2["N_Need"].ToString() == "1")
                        {
                            if (num2 != 1)
                            {
                                str = str + "<li><label><input type=\"radio\" name=\"" + row2["subid"].ToString() + "\" value=\"" + row["N_KeyId"].ToString() + "\">" + row["C_KeyTitle"].ToString() + "</label></li>";
                            }
                            else
                            {
                                str = str + "<li><label><input type=\"radio\" name=\"" + row2["subid"].ToString() + "\" value=\"" + row["N_KeyId"].ToString() + "\"   check=\"^0$\" warning=\"" + row2["C_SubTitle"].ToString() + "最少选一项\" >" + row["C_KeyTitle"].ToString() + "</label></li>";
                            }
                        }
                        else
                        {
                            str = str + "<li><label><input type=\"radio\" name=\"" + row2["subid"].ToString() + "\" value=\"" + row["N_KeyId"].ToString() + "\">" + row["C_KeyTitle"].ToString() + "</label></li>";

                        }
                        if (num2 == table3.Rows.Count)
                        {
                            str = str + "</ul>";
                        }
                        #endregion
                        break;

                    case 3:
                        #region
                        if (num2 == 1)
                        {
                            str = str + "<ul class=\"option\">";
                        }
                        if (row2["N_Need"].ToString() == "1")
                        {
                            if (num2 == 1)
                            {
                                str = str + "<li><label><input type=\"checkbox\" name=\"" + row2["subid"].ToString() + "\" value=\"" + row["N_KeyId"].ToString() + "\"  check=\"^0{1,}$\" warning=\"" + row2["C_SubTitle"].ToString() + "最少选一项或以上\">" + row["C_KeyTitle"].ToString() + "</label></li>";
                            }
                            else
                            {
                                str = str + "<li><label><input type=\"checkbox\" name=\"" + row2["subid"].ToString() + "\" value=\"" + row["N_KeyId"].ToString() + "\">" + row["C_KeyTitle"].ToString() + "</label></li>";
                            }
                        }
                        else
                        {
                            str = str + "<li><label><input type=\"checkbox\" name=\"" + row2["subid"].ToString() + "\" value=\"" + row["N_KeyId"].ToString() + "\">" + row["C_KeyTitle"].ToString() + "</label></li>";
                        }
                        if (num2 == table3.Rows.Count)
                        {
                            str = str + "</ul>";
                        }
                        #endregion

                        break;
                    case 4:
                        #region

                        if (num2 == 1)
                        {
                            str = str + "<select name=\"" + row2["subid"].ToString() + "\"><option value=\"" + row["N_KeyId"].ToString() + "\"  selected=\"selected\">" + row["C_KeyTitle"].ToString() + "</option>";
                        }
                        else if (num2 == table3.Rows.Count)
                        {
                            str = str + "<option value=\"" + row["subid"].ToString() + "\" >" + row["C_KeyTitle"].ToString() + "</option></select>";
                        }
                        else
                        {
                            str = str + "<option value=\"" + row["subid"].ToString() + "\" >" + row["C_KeyTitle"].ToString() + "</option>";
                        }

                        #endregion
                        break;
                    case 5:
                        #region
                        if (row2["N_Need"].ToString() == "1")
                        {
                            str = str + "<p>" + row["C_KeyTitle"].ToString() + "<textarea name=\"" + row2["subid"].ToString() + "\" rows=\"5\"  check=\"^[\\s|\\S]{2,}$\" warning=\"" + row["C_KeyTitle"].ToString() + "不能为空,且不能少于3个字\"></textarea></p>";
                            break;
                        }
                        else
                        {
                            if (!Utils.IsNullOrEmpty(row["C_Rule"]) && !row["C_Rule"].ToString().StartsWith("不限制"))
                            {
                                string[] strArray3 = row["C_Rule"].ToString().Split(new char[] { '`' });
                                str = str + "<p>" + row["C_KeyTitle"].ToString() + "<textarea type=\"text\" name=\"" + row2["subid"].ToString() + "\" check=\"" + strArray3[1] + "\" warning=\"" + row["C_KeyTitle"].ToString() + "格式不正确,应为 " + strArray3[0] + "\" ></textarea></p>";
                            }
                            else
                            {
                                str = str + "<p>" + row["C_KeyTitle"].ToString() + "<textarea name=\"" + row2["subid"].ToString() + "\" rows=\"5\" ></textarea></p>";
                            }
                        }

                        #endregion
                        break;
                }
            }
           
        }
    }
}

