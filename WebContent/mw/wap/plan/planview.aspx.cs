using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using MW.BLL;
using MW.Common;

namespace PortalWeb.mw.wap.plan
{
    public partial class planview : WapBase
    {
        protected int type = 0;
        protected string Id = "";
        protected string courseid = "0";

        protected void Page_Load(object sender, EventArgs e)
        {
            Id = MW.Common.LYRequest.GetString("id");
            type = LYRequest.GetInt("type", 0);
            if (base.Request["cmd"] != null && base.Request["cmd"] == "bm")
            {
                Baoming();
            }
            else
            {
                if (!IsPostBack)
                {
                    LoadInit();
                }
            }

        }

        private void LoadInit()
        {
            if (Id != "")
            {
                DataSet ds = new MW.BLL.B_tech_train_plan().GetList("ID='" + Id + "'");
                if (ds != null && ds.Tables[0] != null && ds.Tables[0].Rows.Count > 0)
                {
                    DataView dv = ds.Tables[0].DefaultView;
                    //DataTable tmptb = dv.ToTable(true, new string[] { "ID" });
                    //DataRow rowdis = tmptb.Rows[0];
                    DataRow row = ds.Tables[0].Rows[0];
                    //dr["ID"] = row["ID"];
                    txtPlanName.Text = BaseUi.GetLang(row["NAME"].ToString());
                    txtAddress.Text = row["DAYS"].ToString();
                    txtBeginTime.Text = row["YEAR_VALUE"] + " 第" + row["PLAN_WEEK"] + "周";
                    rbltStatus.Text = getstatus(row["STATUS"].ToString());
                    dv.RowFilter = " USER_TYPE='teacher' ";
                    courseid = row["TRAIN_ID"].ToString();
                    var ts = dv.ToTable();
                    {
                        var sb = new System.Text.StringBuilder();
                        foreach (DataRow item in ts.Rows) //因为所查询的名字可能有多行
                        {
                            sb.Append(item["UNAME"] + " ，");
                        }
                        txtTearcher.Text = sb.ToString().Trim('，');
                    }

                }
            }
            
        }
        private string getstatus(string status)
        {
            switch (status)
            {
                case "plan":
                    return "计划中";
                case "confirm":
                    return "已确认";
                case "fill":
                    return "已取消";
            }
            return "待确认";
        }
        protected bool canapply
        {
            get
            {
                bool flag = true;
                //判断学员是否已报名
                bool iflag = MW.BLL.B_mw_Applyplans.ExitPlan(base.CurrentUser.ID, Id);
                if (iflag || type == 1) flag = false;
                return flag;
            }
        }

        private void Baoming()
        {
            var name = MW.Common.LYRequest.GetString("name");
            var mobile = MW.Common.LYRequest.GetString("mobile");
            var email = MW.Common.LYRequest.GetString("email");
            var area = MW.Common.LYRequest.GetString("area");
            var code = MW.Common.LYRequest.GetString("code");
            var planid = MW.Common.LYRequest.GetString("planid");
            var courseid = MW.Common.LYRequest.GetString("courseid");
            //认证码检测
            bool flag = MW.BLL.B_tech_train_plan.VaildCardNumber(courseid, code);
            if (!flag)
            {
                base.Output(WapBase.MessageStatus.failure, "verification", "");
            }
            else
            {
                if (MW.BLL.B_mw_Applyplans.ExitPlan(base.CurrentUser.ID, planid))
                {
                    base.Output(WapBase.MessageStatus.failure, "exists", "");
                    return;
                }
                var model = new MW.Model.M_Mw_applyplans();
                model.ApplierID = base.CurrentUser.ID;
                model.ApplyPlanID = planid;
                model.ApplyCourseAetiology = code;
                model.ApplyTime = DateTime.Now;
                model.Deleted = 0;
                model.LastWriteTime = DateTime.Now;
                flag = new MW.BLL.B_mw_Applyplans().Add(model);
                if (flag)
                {
                    //报名成功提醒
                    if (mobile != "" && MW.Common.JValidate.IsMobileNum(mobile))
                    {
                        string smscontent = MW.BLL.B_Sendsms.GetTemplate("applyplan");
                        var planmodel = new B_tech_train_plan().GetModel(planid);
                        if (planmodel != null)
                        {
                            var courlmodel = new B_tech_train_course().GetModel(planmodel.TRAIN_ID);
                            smscontent = smscontent.Replace("{planname}", BaseUi.GetLang(courlmodel.NAME));
                            smscontent = smscontent.Replace("{begintime}", planmodel.YEAR_VALUE + " 第" + planmodel.PLAN_WEEK + "周");
                            smscontent = smscontent.Replace("{days}", courlmodel.DAYS);
                        }
                        else
                        {
                            smscontent = smscontent.Replace("{planname}","");
                            smscontent = smscontent.Replace("{begintime}", "");
                            smscontent = smscontent.Replace("{days}", "");
                        }
                        
                        smscontent = smscontent.Replace("{from}", MW.Common.Configs.GetConfigValue("sitename"));

                        MW.BLL.B_Sendsms.SendSMS(12, mobile, smscontent);
                    }
                    base.Output(WapBase.MessageStatus.success, "", "");
                }
                else
                {
                    base.Output(WapBase.MessageStatus.failure, "", "");
                }
            }
        }
    }
}