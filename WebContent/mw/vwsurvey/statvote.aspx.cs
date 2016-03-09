using System;
using System.Collections.Generic;
using System.Data;
using System.IO;
using System.Linq;
using System.Text;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using MW;
using MW.BLL;
using MW.Common;

namespace Plugin.VWSurvey.Admini.VWSurvey
{
    public partial class StatVote : System.Web.UI.Page
    {
        private string strWhere, SearchName, teachername, starttime, endtime;
        private int typeid = 0, pagesize = 20;

        private B_Voteresult bll= new B_Voteresult();
        

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
            //DataTable dt = new MW.BLL.B_Dictionary().GetListByParent(PageID);
            //JBind.BindList(drpType, JTree.GetClassTree(dt, "ParentId", PageID.ToString(), "DictionaryName"));
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
            strWhere = " 1=1 ";
            #region __________Where语句__________
            if (typeid > 0)
            {
                strWhere += " and N_Type=" + typeid;
            }
            if (SearchName != "")
            {
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

            rpt_List.PageLink = "statvote.aspx?Search=Yes&pagesize=" + pagesize + "&TypeId=" + typeid + "&starttime=" + starttime + "&endtime=" + endtime + "&SearchName=" + Utils.UrlEncode(SearchName) + "&teachername=" + Utils.UrlEncode(teachername);
            rpt_List.PageSize = pagesize;
            rpt_List.CurrentPage = LYRequest.GetInt("page", 1);

            DataSet ds = mw_getlistbypage.GetDataSet_NoCache("mw_votecourse", "*", strWhere, "", "Id DESC", "Id", rpt_List.CurrentPage, rpt_List.PageSize, 1);

            rpt_List.RecordCount = Convert.ToInt32(ds.Tables[0].Rows[0][0]);
            JBind.BindList(rpt_List, ds.Tables[1]);
        }

        protected void rpt_List_ItemCommand(object source, RepeaterCommandEventArgs p1)
        {
            object ids = p1.CommandArgument;
            if (p1.CommandName == "lbtnToExcel")
            {
                var arr = ids.ToString().Split('|');
                //CreateGridView(Utils.StrToInt(arr[0], 0), Utils.StrToInt(arr[1], 0));
                //this.method_0(this.method_1(Utils.StrToInt(arr[0], 0),Utils.StrToInt(arr[1], 0)), Utils.StrToInt(arr[0], 0));
            }

        }
        protected void btnSearch_Click(object sender, EventArgs p1)
        {
            StringBuilder builder = new StringBuilder("statvote.aspx?Search=Yes");
            //builder.Append("&TypeId=" + JBind.GetSelectedList(drpType));
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


        private void method_0(DataTable dataTable_0, int voteid)
        {
            StringWriter writer = new StringWriter();

            HttpContext.Current.Response.Clear();
            HttpContext.Current.Response.Buffer = true;
            HttpContext.Current.Response.Charset = "utf-8";
            HttpContext.Current.Response.AppendHeader("Content-Disposition", "attachment;filename=Vote_" + voteid.ToString() + ".xls");
            HttpContext.Current.Response.ContentEncoding = System.Text.Encoding.UTF8;
            //设置输出文件类型为excel文件。
            HttpContext.Current.Response.ContentType = "application/ms-excel";
            HtmlTextWriter htmlWrite = new HtmlTextWriter(writer);
            DataGrid dg = new DataGrid();
            dg.HeaderStyle.CssClass = "dgHead";
            dg.DataSource = dataTable_0;
            dg.DataBind();
            dg.RenderControl(htmlWrite);

            HttpContext.Current.Response.Output.Write(writer);
            HttpContext.Current.Response.Flush();
            HttpContext.Current.Response.End();
            //writer.Close();
        }

        private DataTable method_1(int voteid, int sysid)
        {
            int num = 1;
            DataTable table = new DataTable();
            table.Columns.Add("编号", typeof(int));
            table.Columns.Add("名称", typeof(string));
            table.Columns.Add("票数", typeof(string));
           // table.Columns.Add("类型", typeof(int));
            foreach (DataRow row7 in new B_Votesubject().GetList(0, "parentid!=0 and N_SysId=" + sysid + "", "N_OrderId,N_SubId").Tables[0].Rows)
            {
                DataRow row2 = table.NewRow();
                row2["编号"] = num;
                row2["名称"] = row7["C_SubTitle"];
                row2["票数"] = DBNull.Value;
                //row2["类型"] = DBNull.Value;
                table.Rows.Add(row2);

                DataTable DT = new B_Votekey().GetList(0, "N_SubId=" + row7["N_SubId"] + "", " N_OrderId,N_KeyId").Tables[0];
                int totalsubs = bll.GetVoteNumBySubId(voteid, int.Parse(row7["N_SubId"].ToString()), sysid);
                foreach (DataRow current in DT.Rows)
                {
                    string str2;
                    int num3 = 0;
                    switch (int.Parse(current["N_Type"].ToString()))
                    {
                        case 1:
                            DataTable item = bll.GetItem(voteid, int.Parse(current["N_SubId"].ToString()),
                                                                 int.Parse(current["N_KeyId"].ToString()));
                            str2 = current["C_KeyTitle"].ToString() + "<br/>";
                            foreach (DataRow row6 in item.Rows)
                            {
                                str2 = str2 + row6["C_Reuslt"].ToString() + "<br/>";
                                num3++;
                            }
                            row2 = table.NewRow();
                            row2["编号"] = DBNull.Value;
                            row2["名称"] = str2;
                            //var pp = (num3 * 1.0 / totalsubs * 1.0) * 100;
                            row2["票数"] = num3;//DBNull.Value;
                            //row2["类型"] = 1;
                            table.Rows.Add(row2);
                            break;
                        case 2:
                        case 3:
                        case 4:
                            row2 = table.NewRow();
                            row2["编号"] = DBNull.Value;
                            row2["名称"] = current["C_KeyTitle"];
                            num3 = bll.GetSum(voteid, int.Parse(current["N_SubId"].ToString()),
                                                         int.Parse(current["N_KeyId"].ToString()));
                            var p = (num3 * 1.0 / totalsubs * 1.0) * 100;
                            row2["票数"] = num3;//p.ToString("0.00");
                           // row2["类型"] = int.Parse(current["N_Type"].ToString());
                            table.Rows.Add(row2);
                            break;

                        case 5:
                            string str;
                            DataTable table3 = bll.GetItem(voteid, int.Parse(current["N_SubId"].ToString()),
                                                                int.Parse(current["N_KeyId"].ToString()));
                            str = current["C_KeyTitle"].ToString() + "<br/>";
                            foreach (DataRow row3 in table3.Rows)
                            {
                                str = str + row3["C_Reuslt"].ToString() + "<br/>";
                                num3++;
                            }
                            row2 = table.NewRow();
                            row2["编号"] = DBNull.Value;
                            row2["名称"] = str;
                            // var p_5 = (num3 * 1.0 / totalsubs * 1.0) * 100;
                            row2["票数"] = num3;// p_5.ToString("0.00");
                            //row2["类型"] = 5;
                            table.Rows.Add(row2);
                            break;
                    }


                }

                num++;
            }
            return table;
        }

      
    }
}