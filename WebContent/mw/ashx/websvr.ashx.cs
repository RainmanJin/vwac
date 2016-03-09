using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Web;
using MW.Common;

namespace PortalWeb.mw.ashx
{
    /// <summary>
    /// websvr 的摘要说明
    /// </summary>
    public class websvr : IHttpHandler
    {

        public void ProcessRequest(HttpContext context)
        {
            //context.Response.ContentType = "text/plain";
            context.Response.ContentType = "application/json";
            string cmd = LYRequest.GetString("cmd");
            string result = "";
            switch (cmd.ToLower())
            {
                case "getlearningtask":
                    int year = LYRequest.GetInt("year", DateTime.Now.Year);
                    int month = LYRequest.GetInt("month", DateTime.Now.Month);
                    result = GetLearningTask(year, month);
                    break;
                default:
                    result = "";
                    break;

            }
            context.Response.Write(result);
            context.Response.Flush();
            context.Response.End();
        }
     
        private string GetLearningTask(int year, int month = 0)
        {
            DateTime dt = DateTime.Parse(DateTime.Now.Year + "-" + (month == 0 ? 1 : month) + "-01");
            string BeginTime = dt.ToString();
            string EndTime = month == 0 ? dt.AddYears(1).ToString() : dt.AddMonths(1).ToString();

            return GetLearningTaskDate(BeginTime, EndTime);
        }


        private string GetLearningTaskDate(string BeginTime, string EndTime)
        {
            try
            {
                DataTable dt = new MW.BLL.B_tech_train_plan().GetLearningTask(Utils.StrToDateTime(BeginTime, DateTime.Now), Utils.StrToDateTime(EndTime, DateTime.Now));
                return Utils.DataTableToJSON(dt).ToString();
            }
            catch //(Exception)
            {
                return "";
            }

        }
        public bool IsReusable
        {
            get
            {
                return false;
            }
        }
    }
}