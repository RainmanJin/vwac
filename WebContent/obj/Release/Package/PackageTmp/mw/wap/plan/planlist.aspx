<%@ Page Title="" Language="C#" MasterPageFile="~/mw/wap/Wap.Master" AutoEventWireup="true" CodeBehind="planlist.aspx.cs" Inherits="PortalWeb.mw.wap.plan.planlist" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
      
    <div class="header"><div class="menu_control menu_control_h"><a href="javascript:;"></a></div><h2>培训课程排期查询</h2><img src="<%=MW.BasePage.GetWebPath %>/mw/wap/images/header.jpg"/></div>
    <div class="page">
    <div class="main">
         <form id="Form1" runat="server">
    
    <div class="search" style=" display:none">
        <div class="searchbox">
    <div>领&nbsp;&nbsp;&nbsp;&nbsp;域：
         <asp:DropDownList ID="dropTypeId" runat="Server"></asp:DropDownList>
    </div>
   <div>     <button class="button btnsubmit" type="button">
					<img src="<%=MW.BasePage.GetWebPath %>/mw/wap/images/icon_search.png"/>&nbsp;检索
</button>
<button class="close">关闭</button>
        
        </div>
     </div>   
 </div>
             </form>
  </div>
  
  <!--日历开始-->
<script src="<%=MW.BasePage.GetWebPath %>/mw/wap/js/jquery.cookie.js"></script>
<script src="<%=MW.BasePage.GetWebPath %>/mw/wap/js/jquery.json.js" type="text/javascript"></script>
<script src="<%=MW.BasePage.GetWebPath %>/mw/wap/js/plan.js"></script>
<style type="text/css">
.date input {
	border: 0px;
	background-color: #FFF; font-weight:bold; color:#6d6d6d;
}
#year{ width:32px; font-size:14px; font-weight:normal; color:#666;}
#month{ width:16px;font-size:14px; font-weight:normal; color:#666;}
.date { margin-top:5px;width:100%; z-index:1;}
.date strong{ font-size:14px;}
.Input {text-decoration: none;background-color: #FFFFFF;height: 28px;}
input:disabled{ color:#666 !important;}
input[disabled]{ color:#666 !important;}
input.disabled{ color:#666 !important;}
.Calendar { text-decoration: none;width: 100%; z-index:1; background-color:#fff}
.CalendarTD {color: #000000;background-color:#fff;height: 28px;width:12%;text-align: center;}

.Title {font-weight: normal;height: 24px;text-align: center;color: #6d6d6d;text-decoration: none;background-color: #FFF;}

.Day {color:#243d5b;background-color: #fff;height: 28px;width:11%;text-align: center;}
.DaySat {color:#243d5b;text-decoration: none;background-color:#fff;text-align: center;height: 28px;width: 14.28%;}
.DaySun { font-size: 16px;color: #243d5b;text-decoration: none;background-color:#fff;text-align: center;height: 28px;width: 14.28%}
.DayNow { font-size: 16px;font-weight: bold;color: #FFF;height: 28px;text-align: center; background-color:#0075C2}
.DayTask{ position:relative; top:50px}

.DayTitle { font-size: 12px;color: #666;background-color: #FFF;height: 28px;width:14.28%;text-align: center; font-weight:bold}
.DaySatTitle { font-size: 12px;color:#666;text-decoration: none;background-color:#FFF;text-align: center;height: 28px;width: 14.28%;font-weight:bold}
.DaySunTitle { font-size: 12px;color: #666;text-decoration: none;background-color: #FFF;text-align: center;height: 28px;width: 14.28%;font-weight:bold}

.DayButton { padding:1px 5px; width:30px; }
#calendar tr{ height: 31px;border-bottom:#dedede 1px solid;}
#calendar tr:last-child{ border-bottom: 0px;}

.showbox{ box-sizing:border-box;height: 31px;display: block; cursor:pointer; text-align:center; width:100%;border:1px solid #999; line-height:28px;background-color:#999;color:#fff}
.showbox:hover{border:2px solid #0075C2;line-height:26px;}
.currday{background-color:#0075C2;}
#calendar .showtask{ color:#F00;z-index: 10;}
#calendar .showtask ul{
	position: relative; text-align:left;max-width:80px; min-width:60px;
	background-color:#FFF;
	padding: 8px;
	border:#CCC 1px solid;top:5px; left:-70px}
#calendar .showtask li :active{ background:none;}
.DayNow .showtask{font-weight: normal;}

</style>
        <div class="date"> 
          
          <table border="0" cellpadding="1" cellspacing="1" class="Calendar" id="caltable">
            <thead>
              <tr align="center" valign="middle">
                <td colspan="7" class="Title"><a href="javaScript:subMonth();" title="上一月" Class="DayButton"><img src="<%=MW.BasePage.GetWebPath %>/wap/images/dateL.png" style=" margin-right:40px;" /></a>
                  <input class="Input" id="year" name="year"  readonly="readonly" type="text" onKeyDown="" onKeyUp="this.value=this.value.replace(/[^0-9]/g,'')"  onpaste="this.value=this.value.replace(/[^0-9]/g,'')" />
                  <strong>年</strong>
                  <input class="Input" id="month" name="month" readonly="readonly" type="text" nKeyDown="" onKeyUp="this.value=this.value.replace(/[^0-9]/g,'')"  onpaste="this.value=this.value.replace(/[^0-9]/g,'')"/>
                  <strong>月</strong> <a href="JavaScript:addMonth();" title="下一月" Class="DayButton nextmonth"><img src="<%=MW.BasePage.GetWebPath %>/wap/images/dateR.png"  style=" margin-left:40px;"/></a></td>
              </tr>
              <tr align="center" valign="middle"> 
                <script LANGUAGE="JavaScript">
                    loadTrTitle();
                </script> 
              </tr>
            </thead>
            <tbody border=0 cellspacing="1" cellpadding="1" ID="calendar" ALIGN=CENTER >
              <script LANGUAGE="JavaScript">
                  loadWeek();
            </script>
            </tbody>
          </table>
          </div>
        <!--日历结束-->
  <ul class="course">
  </ul>
 <div class="clear"></div>
<script  LANGUAGE="JavaScript">
    Calendar();
</script>   
  </div>
    <script>
        $(document).ready(function () {
            $(".menu_control").click(function () {
                $(".search").show();
            });
            $(".close").click(function () {
                $(".search").hide();
            });
            $(".btnsubmit").click(function () {
                var url = "<%=MW.BasePage.GetWebPath %>/mw/wap/plan/searchplanlist.aspx?typeid=" + $("#<%=dropTypeId.ClientID%>").val();
                //alert(url);
                window.location.href = url;
                return false;
            });
        });
    </script>
</asp:Content>
