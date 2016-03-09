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
    public partial class myplan : WapBase
    {
        private string typeid;
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
            //PROTYPE
            JBind.BindList(dropTypeId, new MW.BLL.B_ecan_domainvalue().GetDrpList("DOMAIN_ID='PROTYPE'"), "DOMAINLABEL", "VALUE");
            dropTypeId.Items.Insert(0, new ListItem("请选择", ""));

        }
        private void BindData()
        {
            #region __________Where语句__________

            string strWhere = "  1=1";
            //strWhere += " and EndTime>='" + DateTime.Now + "' and BeginTime<='" + DateTime.Now + "'";
            if (typeid !="")
            {
                strWhere += " and PRO_TYPE='" + typeid + "'";
            }
            strWhere += " and ApplierID='" + (CurrentUser!=null?CurrentUser.ID:"") + "' ";
            #endregion
            rpt_List.ShowPagenation = false;
            rpt_List.PageLink = "";
            rpt_List.PageSize = 100;
            rpt_List.CurrentPage = LYRequest.GetInt("page", 1);
            /*SELECT p.`NAME`,p.DAYS,p.YEAR_VALUE,p.PLAN_WEEK,p.USER_TYPE,p.USER_ID,a.ApplyTime
              FROM mw_applyplans AS a INNER JOIN mw_trainingplans AS p ON a.ApplyPlanID = p.ID
              WHERE p.USER_TYPE = 'teacher'*/
            string table = "mw_applyplans AS a INNER JOIN mw_TrainingPlans AS p ON a.ApplyPlanID = p.ID";

            DataSet ds = MW.BLL.mw_getlistbypage.GetDataSet_NoCache(table, "p.ID,p.`NAME`,p.DAYS,p.YEAR_VALUE,p.PLAN_WEEK,p.USER_TYPE,p.UNAME,a.ApplyTime", strWhere, "", "ApplyTime DESC", "ID", rpt_List.CurrentPage, rpt_List.PageSize, 0);
            if (ds != null && ds.Tables[0] != null && ds.Tables[0].Rows.Count > 0)
            {
                DataView dv = ds.Tables[0].DefaultView;
                dv.Sort = "ApplyTime desc";
                DataTable tmptb = dv.ToTable(true, new string[] { "ID", "NAME", "DAYS", "YEAR_VALUE", "PLAN_WEEK" });
                DataTable DT = new DataTable();
                DT.Columns.Add("ID");
                DT.Columns.Add("PlanName");
                DT.Columns.Add("Teacher");
                DT.Columns.Add("BeginTime");
                DT.Columns.Add("days");
                foreach (DataRow row in tmptb.Rows)
                {
                    var dr = DT.NewRow();
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
                            sb.Append(item["UNAME"] + " ，");
                        }
                        dr["Teacher"] = sb.ToString().Trim('，');
                    }
                    DT.Rows.Add(dr);
                }
                JBind.BindList(rpt_List, DT);
            }
            //rpt_List.RecordCount = Convert.ToInt32(ds.Tables[0].Rows[0][0]);
            
        }
    }
}