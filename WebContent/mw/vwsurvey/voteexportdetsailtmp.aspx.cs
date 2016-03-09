using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using MW.BLL;
using MW.Common;
using MW.Model;

namespace Plugin.VWSurvey.Admini.VWSurvey
{
    public partial class voteexportdetsailtmp : System.Web.UI.Page
    {

        System.Text.StringBuilder sb = new System.Text.StringBuilder();
        private B_Votesubject bllsub = new B_Votesubject();
        private B_VoteresultTmp bll = new B_VoteresultTmp();
        private B_Votecourse bs = new B_Votecourse();
        private MW.Model.M_Mw_votecourse mb = new MW.Model.M_Mw_votecourse();
        private int sysid = 0, voteid = 0;
        protected void Page_Load(object sender, EventArgs e)
        {
            sysid = LYRequest.GetInt("VoteId", 0);
            voteid = LYRequest.GetInt("id", 0);
            if (!IsPostBack)
            {
                string code = "";
                string time = "";
                string sta = tableOne(voteid, sysid, ref code, ref time);
                ExportExcelFile(sta, "vwac_survey_detail_" + voteid + "_" + time + "_" + code);

            }

        }
        private string tableOne(int voteid, int sysid, ref string code, ref string time)
        {
            var arr = bllsub.GetItem(sysid);
            int columns = 3 + arr.Length; //总列数
            columns = columns < 8 ? 8 : columns;//总列数小于8 默认8列
            int yl = bll.GetSum(voteid, sysid);
            mb = bs.GetModel(voteid);
            code = mb.C_Code;
            time = Convert.ToString(mb.DT_StartDate.ToString("yyyyMMdd"));
            sb.Append("<table style=\"border:1px solid #F0F0F0;\"><tr style=\"height:60px;\"><td style=\"border:1px solid #F0F0F0;background-color:#003C65;text-align:center;font-size:16px;color:#FFF\" colspan=\"" + columns + "\";>" + mb.C_Title + "</td></tr>" +
                       "<tr style=\"height:40px;\"><td style=\"border:1px solid #F0F0F0;background-color:#8DB6CD\" colspan=\"2\">培训名称:" + mb.C_course + "</td><td style=\"border:1px solid #F0F0F0;background-color:#8DB6CD\" colspan=\"2\">培训师:" + mb.C_Tearcher + "</td><td style=\"border:1px solid #F0F0F0;background-color:#8DB6CD\" colspan=\"2\">培训时间:" + DataFormat(mb.CourseStart) + "-" + DataFormat(mb.CourseEnd) + "</td><td style=\"border:1px solid #F0F0F0;background-color:#8DB6CD\">培训人数:" + mb.pxnum + "</td><td style=\"border:1px solid #F0F0F0;background-color:#8DB6CD\" colspan=\"" + (columns - 7) + "\">参加人数:" + yl + "</td></tr>" +
                       "<tr><td style=\"border:1px solid #F0F0F0;background-color:#C9C9C9;text-align:center;\" rowspan=\"2\">序列</td><td style=\"border:1px solid #F0F0F0;background-color:#C9C9C9;text-align:center\" rowspan=\"2\" colspan=\"2\">调查项目</td><td style=\"border:1px solid #F0F0F0;text-align:center;background-color:#C9C9C9;height:30px\";colspan=\"" + (columns - 3) + "\">选项票数/内容</td></tr>");

            string header = "";//, itemtmp = "<tr><td style=\"border:1px solid #F0F0F0;\">{0}</td style=\"border:1px solid #F0F0F0;\"><td style=\"border:1px solid #F0F0F0;\" colspan=\"2\">{1}</td style=\"border:1px solid #F0F0F0;\">";
            header = "<tr style=\"height:30px\">";
            for (int j = 0; j < arr.Length; j++)
            {
                header += "<td style=\"border:1px solid #F0F0F0;background-color:#C9C9C9;\">" + arr[j].Replace("\r", "").Replace(" ", "") + "</td>";
                //itemtmp += "<td style=\"border:1px solid #F0F0F0;\">{" + (j + 2) + "}</td>";
            }
            header = header + "</tr>";
            //itemtmp += "</tr>";
            sb.Append(header);

            //找到所有参与投票的人，但有可能项目不全
            DataTable dtresult = bll.GetList(0, "N_ResId,N_KeyId,N_SubId,C_Reuslt,N_TestId,C_Ip", "N_VoteId=" + voteid + " and N_SysId=" + sysid, "").Tables[0];
            //获取单个人节点集合
            var resultrows = dtresult.Select("N_SubId=0");

            //有多少人调查，就循环几轮
            if (yl > 0)
            {
                for (int i = 0; i < yl; i++)
                {
                    string dt = resultrows[i]["DT_Date"]!=null?resultrows[i]["DT_Date"].ToString():"";
                    string ip = resultrows[i]["C_Ip"] != null ? resultrows[i]["C_Ip"].ToString() : "";
                    sb.Append("<tr><td colspan=\"" + columns + "\" style=\"background-color:#003C65;height:30px;text-align:center;color:#ffffff\">问卷：" + (i + 1) + " 时间："+dt+" ip:"+ip+"</td></tr>");
                    //获取该人下所有投票项目
                    var result = dtresult.Select("N_TestId=" + resultrows[i]["N_ResId"]);
                    string itemtmp = "<tr><td style=\"border:1px solid #F0F0F0;height:40px;\">{0}</td style=\"border:1px solid #F0F0F0;\"><td style=\"border:1px solid #F0F0F0;\" colspan=2>{1}</td><td colspan=" + arr.Length + " style=\"border:1px solid #F0F0F0;\"></td></tr>";
                
                    DataTable dtpro = bllsub.GetList(0, "N_SubId,C_SubTitle", "Parentid=0 and N_SysId=" + sysid, "").Tables[0];
                    if (dtpro != null && dtpro.Rows.Count > 0)
                    {//如果分级，按分级数据显示
                        int xh = 1;
                        foreach (DataRow dataRow in dtpro.Rows)
                        {
                            string b = (i + 1) + "." + xh.ToString();//+ ".";
                            string c = dataRow[1].ToString();
                            var obj = new object[columns - 1];
                            obj[0] = b + " ";
                            obj[1] = c;
                            sb.AppendFormat(itemtmp, obj);
                            int jj = 0;
                            foreach (DataRow dataRow2 in bllsub.GetSubToKey(sysid, Utils.StrToInt(dataRow["N_SubId"], 0)).Rows)
                            {
                                jj++;
                                dataOne(b, jj, voteid, dataRow2, arr, result);

                            }
                            xh++;
                        }

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
            //定义文件流，和控件输出流，建立关联。
            //System.IO.StringWriter oStringWriter = new System.IO.StringWriter();
            ////System.Web.UI.Html32TextWriter oHtmlTextWriter = new Html32TextWriter(oStringWriter);
            ////将控件的信息写到输出流。
            ////RenderControl(oHtmlTextWriter);
            //HttpContext.Current.Response.Output.Write("<meta http-equiv=\"content-type\" content=\"application/ms-excel; charset=uft-8\"/>");  
            HttpContext.Current.Response.Output.Write(excel.ToString());
            HttpContext.Current.Response.Flush();
            HttpContext.Current.Response.End();
        }
        private string DataFormat(object date)
        {
            if (date != null && date.ToString() != "")
            {
                return Utils.StrToDateTime(date.ToString(), DateTime.Now).ToString("yyyy-MM-dd");
            }
            return "";
        }
        private void dataOne(string xh, int rr, int voteid, DataRow row, string[] fieldname,DataRow[] rowresult)
        {
            sb.Append("<tr><td style=\"border:1px solid #F0F0F0;height:30px;\">" + xh.ToString() + ((rr > 0) ? "." + rr : "") + " </td>");
            sb.Append("<td style=\"border:1px solid #F0F0F0;\" colspan=\"2\">" + row["C_SubTitle"] + "</td>");

            DataTable DT = new B_Votekey().GetList(0, "N_SubId=" + row["N_SubId"] + "", " N_OrderId,N_KeyId").Tables[0];
            int xmnum = 0;
            foreach (DataRow current in DT.Rows)
            {
                string str2 = "";
                int num3 = 0;
                switch (int.Parse(current["N_Type"].ToString()))
                {
                    case 1:
                    case 5:
                        //N_KeyId,N_SubId,C_Reuslt
                        var rows =
                            rowresult.FirstOrDefault(x => (x.Field<int>("N_KeyId") == int.Parse(current["N_KeyId"].ToString()) && x.Field<int>("N_SubId") == int.Parse(row["SubId"].ToString())));
                        if (rows != null) str2 = rows["C_Reuslt"].ToString();

                        sb.Append("<td colspan=" + fieldname.Length + " style=\"border:1px solid #F0F0F0;\">" + str2 + "</td>");
                        break;
                    case 2:
                    case 4:
                        var rows2 =
                            rowresult.FirstOrDefault(x => (x.Field<int>("N_KeyId") == int.Parse(current["N_KeyId"].ToString()) && x.Field<int>("N_SubId") == int.Parse(row["SubId"].ToString())));
                        if (rows2 != null) num3=1;

                        sb.Append("<td style=\"border:1px solid #F0F0F0;\">" + num3 + "</td>");
                        break;
                    case 3:
                        var rows3 =
                            rowresult.FirstOrDefault(x => (x.Field<int>("N_KeyId") == int.Parse(current["N_KeyId"].ToString()) && x.Field<int>("N_SubId") == int.Parse(row["SubId"].ToString())));
                        if (rows3 != null) str2 = current["C_KeyTitle"].ToString();;
                        if (xmnum == 0)
                        {
                            sb.Append("<td colspan=" + fieldname.Length + " style=\"border:1px solid #F0F0F0;\">" + str2 + "");
                        }
                        else if(xmnum==DT.Rows.Count-1)
                        {
                            sb.Append(" " + str2 + "</td>");
                        }
                        else
                        {
                            sb.Append(" " + str2 + "");
                        }
                        xmnum++;
                        break;
                }

            }
            sb.Append("</tr>");
        }
    }
}