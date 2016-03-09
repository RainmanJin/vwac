using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using MW.BLL;
using MW.Common;
using MW.Model;

namespace Plugin.VWSurvey.Admini.VWSurvey
{
    public partial class voteexport : System.Web.UI.Page
    {
        System.Text.StringBuilder sb = new System.Text.StringBuilder();
        private B_Votesubject bllsub = new B_Votesubject();
        private M_Mw_votesubject mv = new M_Mw_votesubject();
        private B_Voteresult bll = new B_Voteresult();
        private B_Votecourse bs = new B_Votecourse();
        private MW.Model.M_Mw_votecourse mb = new MW.Model.M_Mw_votecourse();
        private B_Votecourse bv = new B_Votecourse();
        private int sysid = 0, voteid = 0;
        protected void Page_Load(object sender, EventArgs e)
        {
            sysid = LYRequest.GetInt("VoteId", 0);
            voteid = LYRequest.GetInt("id", 0);
            if (!new B_Votecourse().GetStartDate(voteid))
            {
                Response.Redirect("voteexporttmp.aspx?VoteId=" + sysid + "&id=" + voteid);
                Response.End();
            }

            if (!IsPostBack)
            {
                string code = "";
                string time = "";
                string sta = tableOne(voteid, sysid, ref code, ref time);
                ExportExcelFile(sta, "vwac_survey_" + voteid + "_" + time + "_" + code);

            }

        }
        /*
        private DataTable CreateTB(int voteid, int sysid)
        {
            DataTable table = new DataTable();
            table.Columns.Add("sub", typeof(string));
            table.Columns.Add("type", typeof(int));
            table.Columns.Add("pj", typeof(string));
            var arr = bllsub.GetItem(sysid);
            if (arr != null)
                for (int j = 0; j < arr.Length; j++)
                {
                    table.Columns.Add("x" + j, typeof(string));
                }
            else
            {
                table.Columns.Add("x0", typeof(string));
            }
            int yl = bll.GetSum(voteid, sysid);
            int xh = 1;
            //获取父级
            DataTable dtpro = bllsub.GetList(0, "N_SubId,C_SubTitle", "Parentid=0 and N_SysId=" + sysid, "").Tables[0];
            if (dtpro != null && dtpro.Rows.Count > 0)
            {//如果分级，按分级数据显示
                foreach (DataRow dataRow in dtpro.Rows)
                {
                    DataRow row = table.NewRow();
                    row["sub"] = xh.ToString() + ". " + dataRow[1].ToString();
                    row["type"] = 0;
                    string ids = bllsub.GetIDbyParent(Utils.StrToInt(dataRow[0], 0));
                    int soucre = bll.GetSumSource(" s.N_SubId in(" + ids + ")");
                    var p = (soucre * 1.0 / yl * 1.0);
                    int len = ids.Replace("0,", "").Trim(',').Split(',').Length;
                    if (len > 0) p = (p * 1.0 / len * 1.0);
                    row["pj"] = p.ToString("0.00");
                    var js = bllsub.GetValue(voteid, sysid, Utils.StrToInt(dataRow["N_SubId"], 0));
                    if (js != null)
                    {
                        if (arr != null)
                            for (int j = 0; j < arr.Length; j++)
                            {
                                row["x" + j] = js.Compute("sum(x" + j + ")", "true");
                            }
                        else
                        {
                            row["x0"] = js.Compute("sum(x0)", "true");
                        }

                    }
                    table.Rows.Add(row);
                    int jj = 0;
                    foreach (DataRow dataRow2 in bllsub.GetSubToKey(sysid, Utils.StrToInt(dataRow["N_SubId"], 0)).Rows)
                    {
                        jj++;
                        GetTable(xh, jj, yl, voteid, dataRow2, ref table, arr);

                    }

                    xh++;
                }

            }
            else
            {
                foreach (
                    DataRow row7 in bllsub.GetSubToKey(sysid).Rows)
                {
                    GetTable(xh, 0, yl, voteid, row7, ref table, arr);
                    xh++;
                }
            }
            DataRow row2 = table.NewRow();
            row2["sub"] = "有效样本数";
            row2["type"] = 0;
            row2["pj"] = yl.ToString();
            table.Rows.Add(row2);
            return table;
        }
        private void GetTable(int xh, int hh, int yb, int voteid, DataRow row, ref DataTable table, string[] fieldname)
        {
            DataRow row2 = table.NewRow();
            row2["sub"] = xh.ToString() + ((hh > 0) ? "-" + hh : "") + ". " + row["C_SubTitle"].ToString();
            int soucre = bll.GetSumSource(" s.N_SubId =" + row["SubId"]);
            var p = (soucre * 1.0 / yb * 1.0);
            row2["pj"] = p.ToString("0.00");

            DataTable DT = new B_Votekey().GetList(0, "N_SubId=" + row["N_SubId"] + "", " N_OrderId,N_KeyId").Tables[0];
            int jj = 0;
            foreach (DataRow current in DT.Rows)
            {
                string str2 = "";
                int num3 = 0;
                row2["type"] = int.Parse(current["N_Type"].ToString());
                switch (int.Parse(current["N_Type"].ToString()))
                {
                    case 1:
                    case 5:
                        DataTable item = bll.GetItem(voteid, int.Parse(row["SubId"].ToString()),
                                                             int.Parse(current["N_KeyId"].ToString()));
                        foreach (DataRow row6 in item.Rows)
                        {
                            str2 = str2 + row6["C_Reuslt"].ToString() + "\n";
                            num3++;
                        }
                        row2["x0"] = str2;
                        break;
                    case 2:
                    case 3:
                    case 4:
                        if (table.Columns.Contains("x" + jj))
                        {
                            num3 = bll.GetSum(voteid, int.Parse(row["SubId"].ToString()),
                                                    int.Parse(current["N_KeyId"].ToString()));
                            row2["x" + jj] = num3;
                        }
                        jj++;
                        break;
                }
            }
            table.Rows.Add(row2);
        }
        private JGridView jgv = new JGridView();
        private GridView cgw = new GridView();
        private void CreateGridView(int voteid, int sysid)
        {
            cgw.RowDataBound += new GridViewRowEventHandler(cgw_RowDataBound);
            cgw.DataBound += new EventHandler(cgw_DataBound);
            cgw.DataSource = CreateTB(voteid, sysid);
            cgw.DataBind();
            //BaseVote.voteid = voteid;
            //BaseVote.sysid = sysid;
            jgv.ToExcel(cgw, "Vote_" + voteid + "_" + DateTime.Now.ToString());
        }

        void cgw_RowDataBound(object sender, GridViewRowEventArgs e)
        {
            if (e.Row.RowType == DataControlRowType.Header)
            {
                string header = "调查项目#平均分#内容";

                var arr = bllsub.GetItem(sysid);
                if (arr != null)
                {
                    header += "";
                    for (int j = 0; j < arr.Length; j++)
                    {
                        header += arr[j].Replace("\r", "").Replace(" ", "") + ",";
                    }
                    header = header.Trim(',');
                }
                e.Row.CssClass = "fixedHeaderTr";

                e.Row.BackColor = System.Drawing.Color.Beige;
                jgv.SplitTableHeader(e.Row, header);
            }
        }

        private void cgw_DataBound(object sender, EventArgs e)
        {
            int index = 0;
            foreach (GridViewRow row in cgw.Rows)
            {
                if (cgw.Rows[index] == null) continue;
                if (row.Cells[1].Text == "1" || row.Cells[1].Text == "5")
                {
                    var arr = bllsub.GetItem(sysid);
                    if (arr != null)
                    {
                        JGridView.GridViewGroup.GroupRow(cgw, index, 3, 3 + arr.Length);
                    }
                }
                TableCell tc = cgw.Rows[index].Cells[1]; //Cells[0]就是你要合并的列  
                tc.Width = 0;
                tc.Visible = false;
                index++;

            }

        }
         */

        private string tableOne(int voteid, int sysid, ref string code, ref string time)
        {
            var arr = bllsub.GetItem(sysid);
            if (arr==null)
            {
                return "";
            }
            int columns =3+ arr.Length; //总列数
            columns = columns < 8 ? 8 : columns;//总列数小于8 默认8列
            int xh = 1;
            int yl = bll.GetSum(voteid, sysid);
            mb = bs.GetModel(voteid);
            code = mb.C_Code;
            time = Convert.ToString(mb.DT_StartDate.ToString("yyyyMMdd"));
            sb.Append("<table style=\"border:thin solid #333333;\"><tr style=\"height:60px;\"><td style=\"border:thin solid #333333;background-color:#003C65;text-align:center;font-size:16px;color:#FFF\" colspan=\""+columns+"\";>" + mb.C_Title + "</td></tr>" +
                      "<tr style=\"height:40px;\"><td style=\"border:thin solid #333333;background-color:#8DB6CD\" colspan=\"2\">培训名称:" + mb.C_course + "</td><td style=\"border:thin solid #333333;background-color:#8DB6CD\" colspan=\"2\">培训师:" + mb.C_Tearcher + "</td><td style=\"border:thin solid #333333;background-color:#8DB6CD\" colspan=\"2\">培训时间:" + DataFormat(mb.CourseStart) + "-" + DataFormat(mb.CourseEnd) + "</td><td style=\"border:thin solid #333333;background-color:#8DB6CD\">培训人数:" + mb.pxnum + "</td><td style=\"border:thin solid #333333;background-color:#8DB6CD\" colspan=\"" + (columns - 7) + "\">参加人数:" + yl + "</td></tr>" +
                      "<tr><td style=\"border:thin solid #333333;background-color:#C9C9C9;text-align:center;\" rowspan=\"2\">序列</td><td style=\"border:thin solid #333333;background-color:#C9C9C9;text-align:center\" rowspan=\"2\">调查项目</td><td style=\"border:thin solid #333333;text-align:center;background-color:#C9C9C9\" rowspan=\"2\">平均分</td><td style=\"border:thin solid #333333;text-align:center;background-color:#C9C9C9;height:30px\";colspan=\"" + (columns - 3) + "\">选项票数/内容</td></tr>");

            string header = "";//, itemtmp = "<tr><td style=\"border:thin solid #333333;\">{0}</td style=\"border:thin solid #333333;\"><td style=\"border:thin solid #333333;\">{1}</td style=\"border:thin solid #333333;\"><td style=\"border:thin solid #333333;\">{2}</td>";
            header = "<tr style=\"height:30px\">";
            for (int j = 0; j < arr.Length; j++)
            {
                header += "<td style=\"border:thin solid #333333;background-color:#C9C9C9;text-align:center;\">" + arr[j].Replace("\r", "") + "</td>";
                //itemtmp += "<td style=\"border:thin solid #333333;\">{"+(j+3)+"}</td>";
            }
            header = header + "</tr>";
            //itemtmp += "</tr>";
            sb.Append(header);
            DataTable dtpro = bllsub.GetList(0, "N_SubId,C_SubTitle", "Parentid=0 and N_SysId=" + sysid, "").Tables[0];
            if (dtpro != null && dtpro.Rows.Count > 0)
            {//如果分级，按分级数据显示
                Dictionary<string,string> zdb=new Dictionary<string, string>();
                string itemtmp = "<tr><td style=\"border:thin solid #333333;\" align=\"center\">{0}</td><td style=\"border:thin solid #333333;\">{1}</td><td style=\"border:thin solid #333333;\">{2}</td><td colspan=" + arr.Length + " style=\"border:thin solid #333333;\"></td></tr>";
                foreach (DataRow dataRow in dtpro.Rows)
                {
                    string b = xh.ToString() + " ";
                    string c = dataRow[1].ToString();
                    string d = "";
                    string ids = bllsub.GetIDbyParent(Utils.StrToInt(dataRow[0], 0));
                    int soucre = bll.GetSumSource("r.N_VoteId='"+voteid+"' and s.N_SubId in(" + ids + ")");
                    var p = yl == 0 ? 0 : (soucre * 1.0 / yl * 1.0);
                    int len = bllsub.GetSubNum(Utils.StrToInt(dataRow[0], 0));
                    if (len > 0) p = (p * 1.0 / len * 1.0);
                    d = p.ToString("0.00");
                    zdb.Add(c,d);
                    //num +
                    //var obj = new object[columns];
                    //obj[0] = b;
                    //obj[1] = c;
                    //obj[1] = d;
                    //sb.AppendFormat(itemtmp, obj);
                    sb.AppendFormat(itemtmp, b,c,d);
                    //var js = bllsub.GetValue(voteid, sysid, Utils.StrToInt(dataRow["N_SubId"], 0));
                    int jj = 0;
                    foreach (DataRow dataRow2 in bllsub.GetSubToKey(sysid, Utils.StrToInt(dataRow["N_SubId"], 0)).Rows)
                    {
                        jj++;
                        dataOne(xh, jj, yl, voteid, dataRow2, arr);

                    }
                    xh++;
                }
                sb.Append(" <tr><td colspan=\""+columns+"\"></td></tr><tr><td rowspan=" + (zdb.Count+1) + " style=\"border:thin solid #333333;\">单项汇总</td><td style=\"border:thin solid #333333;\" colspan=\"2\">项目</td><td style=\"border:thin solid #333333;\" colspan=" + (columns - 3) + ">分值</td></tr>");
                foreach (KeyValuePair<string, string> pair in zdb)
                {
                    if (pair.Value != "0.00" && pair.Value != "0")
                    {
                        sb.Append("<tr ><td style=\"border:thin solid #333333;\" colspan=\"2\">" + pair.Key +
                                  "</td><td align=\"left\" style=\"border:thin solid #333333;\" colspan=" + (columns - 3) +
                                  ">" + pair.Value + "</td></tr>");
                    }
                }
            }
            sb.Append("</table>");
            return sb.ToString();
        }
        private void ExportExcelFile(string excel, string fileName)
        {
            HttpContext.Current.Response.Clear();
            HttpContext.Current.Response.Buffer = true;
            HttpContext.Current.Response.Charset = "utf-8";
            HttpContext.Current.Response.AppendHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");
            //如果设置为 GetEncoding("GB2312");导出的文件将会出现乱码！！！
            HttpContext.Current.Response.ContentEncoding = System.Text.Encoding.UTF8;
            //设置输出文件类型为excel文件。
            HttpContext.Current.Response.ContentType = "application/ms-excel";
            //HttpContext.Current.Response.Output.Write("<meta http-equiv=\"content-type\" content=\"application/ms-excel; charset=uft-8\"/>");  
            HttpContext.Current.Response.Output.Write(excel.ToString());
            HttpContext.Current.Response.Flush();
            HttpContext.Current.Response.End();
        }

        private void dataOne(int xh, int hh, int yb, int voteid, DataRow row,  string[] fieldname)
        {
            int i = 0;
            sb.Append("<tr><td {rowspan} align=\"center\" style=\"border:thin solid #333333;\">" + xh.ToString() + ((hh > 0) ? "." + hh : "") + " </td>");
            sb.Append("<td {rowspan} style=\"border:thin solid #333333;\">" + row["C_SubTitle"] + "</td>");

            StringBuilder sbx =new StringBuilder();

            DataTable DT = new B_Votekey().GetList(0, "N_SubId=" + row["N_SubId"] + "", " N_OrderId,N_KeyId").Tables[0];
            int jj = 0;
            foreach (DataRow current in DT.Rows)
            {
                //string str2 = "";
                int num3 = 0;
               // sb.Append("<td>"+int.Parse(current["N_Type"].ToString())+"</td>");
                switch (int.Parse(current["N_Type"].ToString()))
                {
                    case 1:
                    case 5:
                        DataTable item = bll.GetItem(voteid, int.Parse(row["SubId"].ToString()),
                                                     int.Parse(current["N_KeyId"].ToString()));
                        if (item != null && item.Rows.Count > 0)
                        {
                            foreach (DataRow row6 in item.Rows)
                            {
                                if (i == 0)
                                {
                                    sb.Append("<td {rowspan} style=\"border:thin solid #333333;\"></td>");
                                    sb.Append("<td colspan=" + fieldname.Length +
                                              " style=\"border:thin solid #333333;\">" + row6["C_Reuslt"].ToString() +
                                              "</td>");
                                    sb.Append("</tr>");
                                }
                                else
                                {
                                    sbx.Append("<tr><td colspan=" + fieldname.Length +
                                               " style=\"border:thin solid #333333;\" align=left>" + row6["C_Reuslt"].ToString() +
                                               "</td></tr>");
                                }
                                num3++;
                                i++;
                            }
                        }
                        else
                        {
                            sb.Append("<td {rowspan} style=\"border:thin solid #333333;\"></td>");
                            sb.Append("<td colspan=" + fieldname.Length + " style=\"border:thin solid #333333;\"></td>");
                        }

                        break;
                    case 2:
                    case 4:
                        if (jj == 0)
                        {
                            int soucre = bll.GetSumSource(" r.N_VoteId='" + voteid + "' and s.N_SubId =" + row["SubId"]);
                            var p = yb == 0 ? 0 : (soucre*1.0/yb*1.0);
                            sb.Append("<td style=\"border:thin solid #333333;\">" + p.ToString("0.00") + "</td>");

                        }
                        num3 = bll.GetSum(voteid, int.Parse(row["SubId"].ToString()),
                                          int.Parse(current["N_KeyId"].ToString()));

                        sb.Append("<td style=\"border:thin solid #333333;\">" + num3 + "</td>");
                        jj++;
                        break;
                    case 3:
                        
                        int colspan = fieldname.Length > 1 ? fieldname.Length - 1 : 0;
                        if (jj == 0)
                        {
                           // int soucre = bll.GetSum("C_Reuslt=3 and N_VoteId=" + voteid + " and N_SubId=" + row["SubId"].ToString());
                            sb.Append("<td {rowspan} style=\"border:thin solid #333333;\">0</td>");

                            sb.Append("<td align=left style=\"border:thin solid #333333;\">" + current["C_KeyTitle"].ToString() + "</td>");
                            sb.Append("<td colspan=\"" + colspan + "\" style=\"border:thin solid #333333;\">" + num3 + "</td>");
                            sb.Append("</tr>");
                        }
                        else
                        {
                            num3 = bll.GetSum(voteid, int.Parse(row["SubId"].ToString()),
                                          int.Parse(current["N_KeyId"].ToString()));
                            sb.Append(" <tr><td align=left style=\"border:thin solid #333333;\">" + current["C_KeyTitle"].ToString() +
                                      "</td><td colspan=\"" + colspan + "\" style=\"border:thin solid #333333;\">" + num3 +
                                      "</td> </tr>");
                        }
                        jj++;
                        i++;
                        break;
                }

            }
            if (i > 0)
            {
                sb.Append(sbx.ToString());
                sb.Replace("{rowspan}", "rowspan=\"" + i + "\"");
            }
            else
            {
                sb.Append("</tr>");
                sb.Replace("{rowspan}", "");
            }
        }

        private string DataFormat(object date)
        {
            if (date != null&&date.ToString()!="")
            {
                return Utils.StrToDateTime(date.ToString(), DateTime.Now).ToString("yyyy-MM-dd");
            }
            return "";
        }
    }
}