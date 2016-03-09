using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using MW.BLL;
using MW.Common;

namespace Plugin.VWSurvey.Admini.VWSurvey
{
    public partial class statresulttmp : System.Web.UI.Page
    {
        private B_Votesubject bllsub = new B_Votesubject();
        private B_VoteresultTmp bll = new B_VoteresultTmp();
        private int sysid = 0, voteid = 0;
        protected void Page_Load(object sender, EventArgs e)
        {
            sysid = LYRequest.GetInt("VoteId", 0);
            voteid = LYRequest.GetInt("id", 0);
            if (!IsPostBack)
            {
                litNum.Text = bll.GetSum(voteid, sysid).ToString();
                rpt_List.ShowPagenation = false;
                JBind.BindList(rpt_List, CreateTB(voteid, sysid));
            }

        }

        private DataTable CreateTB(int voteid, int sysid)
        {
            DataTable table = new DataTable();
            table.Columns.Add("sub", typeof (string));
            table.Columns.Add("type", typeof (int));
            table.Columns.Add("tp", typeof (string));
            table.Columns.Add("pj", typeof(string));
            int xh = 1;
            int yl = bll.GetSum(voteid, sysid);
            //获取父级
            DataTable dtpro = bllsub.GetList(0, "N_SubId,C_SubTitle", "Parentid=0 and N_SysId=" + sysid, "").Tables[0];
            if (dtpro != null && dtpro.Rows.Count > 0)
            {//如果分级，按分级数据显示
                foreach (DataRow dataRow in dtpro.Rows)
                {
                    DataRow row = table.NewRow();
                    row["sub"] = xh.ToString() + ". " + dataRow[1].ToString();
                    row["type"] = 0;
                    //int soucre = bll.GetSumCout(" s.N_SubId in(" + bllsub.GetIDbyParent(Utils.StrToInt(dataRow[0], 0)) + ")");
                    row["tp"] = DBNull.Value;
                    table.Rows.Add(row);
                    int jj = 0;
                    foreach (DataRow dataRow2 in bllsub.GetSubToKey(sysid,Utils.StrToInt(dataRow["N_SubId"],0)).Rows)
                    {
                        jj++;
                        GetTable(xh, jj,yl, voteid, dataRow2, ref table);
                        
                    }
                    xh++;
                }

            }
            else
            {
                foreach (
                    DataRow row7 in bllsub.GetSubToKey(sysid).Rows)
                {
                    GetTable(xh,0, yl, voteid, row7, ref table);
                    xh++;
                }
            }

            return table;
        }

        private void GetTable(int xh, int hh,int yb, int voteid, DataRow row, ref DataTable table)
        {
            DataRow row2 = table.NewRow();
            row2["sub"] = "<span class=\"splace\">&nbsp;</span>" + xh.ToString() + ((hh > 0) ? "-" + hh : "") + ". " + row["C_SubTitle"].ToString();
            row2["tp"] = DBNull.Value;
            row2["type"] = DBNull.Value;
            table.Rows.Add(row2);

            DataTable DT = new B_Votekey().GetList(0, "N_SubId=" + row["N_SubId"] + "", " N_OrderId,N_KeyId").Tables[0];
            int jj = 0;
            foreach (DataRow current in DT.Rows)
            {
                string str2 = "";
                int num3 = 0;
                row2 = table.NewRow();
                row2["type"] = int.Parse(current["N_Type"].ToString());
                switch (int.Parse(current["N_Type"].ToString()))
                {
                    case 1:
                    case 5:
                        str2 = "<span class=\"splace2\">&nbsp;&nbsp;</span>" + current["C_KeyTitle"].ToString() + "<br/>";
                        DataTable item = bll.GetItem(voteid, int.Parse(row["SubId"].ToString()),
                                                             int.Parse(current["N_KeyId"].ToString()));
                        foreach (DataRow row6 in item.Rows)
                        {
                            str2 = str2 + row6["C_Reuslt"].ToString() + "<br/>";
                            num3++;
                        }

                        row2["sub"] = str2;
                        row2["tp"] = num3;//DBNull.Value;
                        table.Rows.Add(row2);
                        break;
                    case 2:
                    case 3:
                    case 4:
                        num3 = bll.GetSum(voteid, int.Parse(row["SubId"].ToString()),
                                                    int.Parse(current["N_KeyId"].ToString()));
                        row2["sub"] = "<span class=\"splace2\">&nbsp;&nbsp;</span>" + current["C_KeyTitle"];
                        row2["tp"] = num3;//DBNull.Value;
                        table.Rows.Add(row2);
                        break;
                }
            }
        }
    }
}