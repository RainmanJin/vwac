using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using MW.BLL;

namespace Plugin.VWSurvey.Admini.VWSurvey
{
    public class BaseVote
    {
        public static  string VoteType(object type)
        {
            switch (MW.Common.Utils.StrToInt(type.ToString(),0))
            {
                case 1:
                    return "文本行";

                case 2:
                    return "单选";

                case 3:
                    return "多选";

                case 4:
                    return "下拉列表";

                case 5:
                    return "多行文本";
            }
            return "类型无";
        }
        public static bool ShowEdit(object subid)
        {
            int count = new B_Voteresult().GetRecordCount("N_SubId=" + subid);
            return count > 0;
        }
        public static bool ShowSysEdit(object sysid)
        {
            int count = new B_Voteresult().GetRecordCount("N_SysId=" + sysid);
            return count > 0;
        }
    }
}