using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using MW.Common;

namespace PortalWeb.mw.wap.plan
{
    public partial class searchplanlist : System.Web.UI.Page
    {
        private string typeid;
        protected string typename = "培训课程";
        protected void Page_Load(object sender, EventArgs e)
        {
            typeid = LYRequest.GetString("typeid");
            if (!IsPostBack)
            {
                LoadInit();
            }
            BindData();
        }
        private void LoadInit()
        {
            JBind.BindList(dropTypeId, new MW.BLL.B_ecan_domainvalue().GetDrpList("DOMAIN_ID='PROTYPE'"), "DOMAINLABEL", "VALUE");
            //dropTypeId.Items.Insert(0, new ListItem("请选择", ""));

            if (typeid !="")
            {
                dropTypeId.SelectedValue = typeid.ToString();
                //typename = new MW.BLL.B_ecan_domain().GetString(typeid, false);
            }

        }
        private void BindData()
        {
            #region __________Where语句__________

            string strWhere = "  1=1";
            //strWhere += " and EndTime>='" + DateTime.Now + "' and BeginTime<='" + DateTime.Now + "'";
            if (typeid != "")
            {
                strWhere += " and PRO_TYPE='" + typeid + "'";
            }
            #endregion
            rpt_List.ShowPagenation = false;
            rpt_List.PageLink = "";
            rpt_List.PageSize = 100;
            rpt_List.CurrentPage = LYRequest.GetInt("page", 1);

            DataSet ds = MW.BLL.mw_getlistbypage.GetDataSet_NoCache("mw_TrainingPlans", "ID,NAME,DAYS,YEAR_VALUE,PLAN_WEEK,USER_TYPE,UNAME", strWhere, "", "YEAR_VALUE DESC,PLAN_WEEK DESC", "ID", rpt_List.CurrentPage, rpt_List.PageSize, 0);
            if (ds != null && ds.Tables[0] != null && ds.Tables[0].Rows.Count > 0)
            {
                DataView dv = ds.Tables[0].DefaultView;
                DataTable tmptb = dv.ToTable(true, new string[] { "ID" });
                DataTable DT = new DataTable();
                DT.Columns.Add("ID");
                DT.Columns.Add("PlanName");
                DT.Columns.Add("Teacher");
                DT.Columns.Add("BeginTime");
                DT.Columns.Add("days");
                foreach (DataRow rowt in tmptb.Rows)
                {
                    var dr = DT.NewRow();
                    DataRow row = ds.Tables[0].Select("ID='" + rowt["ID"] + "'")[0];
                    dr["ID"] = row["ID"];
                    dr["PlanName"] = BaseUi.GetLang(row["NAME"].ToString());
                    dr["days"] = row["DAYS"];
                    dr["BeginTime"] = row["YEAR_VALUE"] + " 第" + row["PLAN_WEEK"] + "周";
                    dv.RowFilter = "(ID='" + row["ID"] + "' and USER_TYPE='teacher')";
                    var ts = dv.ToTable();
                    {
                        var sb = new System.Text.StringBuilder();
                        foreach (DataRow item in ts.Rows) //因为所查询的名字可能有多行
                        {
                            sb.Append(item["UNAME"] + " ");
                        }
                        dr["Teacher"] = sb.ToString();
                    }
                    DT.Rows.Add(dr);
                }
                JBind.BindList(rpt_List, DT);
            }
        }
    }
}