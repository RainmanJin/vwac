<%@page import="com.ecannetwork.dto.tech.TechHoliday"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="com.ecannetwork.tech.facade.HolidayFacade"%>
<%@page import="java.util.Calendar"%>
<%@page import="com.ecannetwork.dto.core.EcanUser"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.ecannetwork.dto.tech.TechTrainPlan"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@include file="/common/context.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="org.apache.commons.lang.StringUtils"%><html>
<head>
	<%@include file="/common/head.jsp" %>
	
	<style>
		.calHeader{
			font-size: 13px; font-weight: bold;background: #f4f4f4; height: 25px; line-height: 25px;
			border: 1px solid #f0f0f0;
		}
		.calDay{
			height: 40px; border: 1px solid #f0f0f0; background: #fefefe; cursor: pointer;
		}
		.calEmpty{
			border: none;
		}
		.calHoliday{
			height: 40px; border: 1px solid #f0f0f0; background: #F8F8FF;
		}
		.calDay, .calHoliday{
			font-size: 24px; color: #8B8B7A; font-weight: bold; font-style: italic;
		}
		.planed{
			background: #CAE1FF;
		}
		.tips{
			display: none;
			position: absolute;
			text-align: left;
			background: #f0f0f0;
			border: 1px dotted #000;
			width: auto;
			height: auto;
		}
		
		.tips ul li label{
			width: 60px;
		}
		.tips ul li{
			margin: 0px;
			border-bottom: 1px dotted #fff;
		}
		
		.tips li span.info{
			line-height: 25px;
		}
	</style>
	
	<script type="text/javascript">
		$(document).ready(function(){
				$(".tips").hide();
				$(".calDay").hover(function(){
						$(this).find(".tips").show();
					},function(){
						$(this).find(".tips").hide();
					});
			});
	function change() 
	{ 
		location.href = "index?month="+ $("#month").val() +"&year=" + $("#year").val() + "&user=" + $("#user").val();
	}

	function clickDay(id,y,m,d)
	{
		var ymd = y + "";
		if(m<10)
		{
			ymd += "0";
		}
		ymd += m;
		if(d<10)
		{
			ymd += "0";
		}
		ymd += d;
		
		if(id != '')
			buttonText = { ok: '${i18n.button.ok}',ok2:'${i18n.button.del}', cancel: '${i18n.button.cancel}' };
		else
			buttonText = { ok: '${i18n.button.ok}',cancel: '${i18n.button.cancel}' };
		
		$.fn.jmodal({
            title: '${i18n.holiday.title}',
            width:600,
            content: function(body) {
                body.html('loading...');
                body.load('view?userid=${userid}&id=' + id + "&ymd=" + ymd);
            },
            buttonText: buttonText,
            okEvent: function(data, args) {
            	$("#holidayForm").submit();
            },
            ok2Event: function(data, args) {
            	ajaxGet("del",{"id":id},function(d){
    				if(d.success)
    					location.reload();
    				else
    					alert(d.data);
    			},"json");
            }
        });
	}
	</script>
</head>
<body>
<%@include file="/common/header.jsp" %>
<%
	Calendar cl = Calendar.getInstance();
	cl.set(Calendar.HOUR_OF_DAY,0);
	cl.set(Calendar.MINUTE,0);
	cl.set(Calendar.SECOND,0);
	Integer year = (Integer) request.getAttribute("year");
	Integer month = (Integer) request.getAttribute("month");
	String userid = (String) request.getAttribute("userid");
	Map weeks=(HashMap)request.getAttribute("week");
	EcanUser user = (EcanUser)request.getAttribute("user");
	
	String days[];
	days=new String[42]; 
	for(int i=0;i<42;i++) 
	{ 
		days[i]=""; 
	} 
	Calendar thisMonth=Calendar.getInstance();
	thisMonth.set(Calendar.MONTH, month); 
	thisMonth.set(Calendar.YEAR, year); 
	thisMonth.setFirstDayOfWeek(Calendar.SUNDAY); 
	thisMonth.set(Calendar.DAY_OF_MONTH,1); 
	
	int firstIndex=thisMonth.get(Calendar.DAY_OF_WEEK)-1; 
	int maxIndex=thisMonth.getActualMaximum(Calendar.DAY_OF_MONTH); 
	
	for(int i=0;i<maxIndex;i++) 
	{ 
		days[firstIndex+i]=String.valueOf(i+1); 
	}
%> 
<div class="centerbody centerbox">
	<div id="contentwrapper">
		<div id="content_right1">
			<div class="tt">
				请选择日历:
				<select id="year" onchange="change()" > 
					<%
						for(int yyyy=2011; yyyy<2031; yyyy++)
						{
					%>
					<option <%if(year == yyyy){%>selected="selected" <%}%>value="<%=yyyy%>"><%=yyyy%></option>
					<%
						}
					%>
				</select>
				
				<select id="month" onchange="change()" > 
					<option <%if(month == 0){%>selected="selected" <%}%>value="0">${i18n.month.January}</option> 
					<option <%if(month == 1){%>selected="selected" <%}%>value="1">${i18n.month.February}</option> 
					<option <%if(month == 2){%>selected="selected" <%}%>value="2">${i18n.month.March}</option> 
					<option <%if(month == 3){%>selected="selected" <%}%>value="3">${i18n.month.April}</option> 
					<option <%if(month == 4){%>selected="selected" <%}%>value="4">${i18n.month.May}</option> 
					<option <%if(month == 5){%>selected="selected" <%}%>value="5">${i18n.month.June}</option> 
					<option <%if(month == 6){%>selected="selected" <%}%>value="6">${i18n.month.July}</option> 
					<option <%if(month == 7){%>selected="selected" <%}%>value="7">${i18n.month.August}</option> 
					<option <%if(month == 8){%>selected="selected" <%}%>value="8">${i18n.month.September}</option> 
					<option <%if(month == 9){%>selected="selected" <%}%>value="9">${i18n.month.October}</option> 
					<option <%if(month == 10){%>selected="selected" <%}%>value="10">${i18n.month.November}</option> 
					<option <%if(month == 11){%>selected="selected" <%}%>value="11">${i18n.month.December}</option> 
				</select>
			
				<span style="font-size: 12px;"></span>
			</div>
			
			<table border="0" width="700" cellpadding="0" cellspacing="0"> 
				<tr>
					<td colspan="7">
						<div style="height: 30px; line-height: 30px; float: left;">
							<c:choose>
								<c:when test="${empty userid}">${i18n.holiday.public}</c:when>
								<c:otherwise><b class="bold">${user.name}</b>${i18n.holiday.personal}</c:otherwise>
							</c:choose>
						</div>
						
						<div style="float: right; height: 30px; line-height: 30px;">
							<b style="border:1px dotted #ddd; height: 10px; width: 10px; display: inline-block; background: #F8F8FF"></b>Weekend&nbsp;&nbsp;&nbsp;&nbsp;
							<b style="border:1px dotted #ddd; height: 10px; width: 10px; display: inline-block; background: #7FAADB"></b>Vacation&nbsp;&nbsp;&nbsp;&nbsp;
							<b style="border:1px dotted #ddd; height: 10px; width: 10px; display: inline-block; background: #CAE1FF"></b>Train Plan&nbsp;&nbsp;&nbsp;&nbsp;
						</div>
					</td>
				</tr>
				 <tr> 
				   <th width="100" class="calHeader">${i18n.week.Sunday}</th> 
				   <th width="100" class="calHeader">${i18n.week.Monday}</th> 
				   <th width="100" class="calHeader">${i18n.week.Tuesday}</th> 
				   <th width="100" class="calHeader">${i18n.week.Wednesday}</th> 
				   <th width="100" class="calHeader">${i18n.week.Thursday}</th> 
				   <th width="100" class="calHeader">${i18n.week.Friday}</th> 
				   <th width="100" class="calHeader">${i18n.week.Saturday}</th> 
				 </tr>
				<%
				for(int j=0;j<6;j++) 
				{
				%> 
				<tr> 
				<%
					for(int i=j*7;i<(j+1)*7;i++)
					{
						if(StringUtils.isBlank(days[i]))
						{//空白
				%>
					<td class="calEmpty" valign="middle" align="center">&nbsp;</td> 
				<%
						}else
						{
							if(i%7==0 || i%7==6)
							{//周六日
				%>
					<td class="calHoliday" valign="middle" align="center" style="color: #ddd;"> 
					<%=days[i]%>
				    </td> 
				<%
							}else
							{//工作日
								String ddd = year + "";
								if(month<9)
								{
									ddd += "0";
								}
								ddd += (month+1);
								Integer ddday = Integer.valueOf(days[i]);
								if(ddday<10)
								{
									ddd += "0";
								}
								ddd += days[i];
								TechHoliday holiday = HolidayFacade.getHoliday(ddd,userid);
				%>
				<%
				thisMonth.set(Calendar.DAY_OF_MONTH, Integer.valueOf(days[i]));
				Integer week = thisMonth.get(Calendar.WEEK_OF_YEAR);
				String styleClass = "calDay ";
				if(weeks.containsKey(week))
				{
					styleClass += " planed";
					//System.out.println("是否在此期间"+week +"\t 当前样式"+styleClass);
				}
					if("teacher".equals(user.getRoleId())){
						//System.out.println("============"+(styleClass.indexOf("planed")!=-1));
						//如果当前有排期，并且是老师，不能点击添加假期
						if(styleClass.indexOf("planed")!=-1){	
					%>
						<td class="<%=styleClass %>" <%if(holiday != null){%> style="background:#7faadb; color:#fff;"<%} %> valign="middle" align="center"">
					
					<%
						}//如果是老师当前没有排期，允许添加假期
						else{
							
							if(thisMonth.getTimeInMillis() <= cl.getTimeInMillis())
							{
						%>
						<td class="<%=styleClass %>" <%if(holiday != null){%> style="background:#7faadb; color:#fff;"<%}else{%>  style="color: #ddd;" <%} %> valign="middle" align="center">
						<%}else{%>
						<td class="<%=styleClass %>" <%if(holiday != null){%> style="background:#7faadb; color:#fff;"<%} %> valign="middle" align="center" onclick="clickDay('<%=holiday==null?"":holiday.getId()%>',<%=year%>,<%=month+1%>,<%=days[i]%>)">	
						<%}} %>
						<%=days[i]%>
						<%
						TechTrainPlan plan = (TechTrainPlan)weeks.get(week);
							if(plan != null && plan.getRemark() != null && !"".equals(plan.getRemark()))
							{
						%>
							<span class="form tips"><ecan:SubString end="30" fix="..." content="<%=plan.getRemark()%>"  /></span>
						<%	
							}
							if(holiday!=null && holiday.getRemark()!=null)
							{
						%>
							<span class="form tips"><ecan:SubString end="30" fix="..." content="<%=holiday.getRemark()%>"  /></span>
						<%
						}
						%>
					</td> 
				<%}else{%>
					
					<td class="<%=styleClass %>" <%if(holiday != null){%> style="background:#7faadb; color:#fff;"<%} %> valign="middle" align="center">
						<%=days[i]%>
						<%
							TechTrainPlan plan = (TechTrainPlan)weeks.get(week);
							if(plan != null && plan.getRemark() != null && !"".equals(plan.getRemark()))
							{
						%>
							<span class="form tips"><ecan:viewDto dtoName="TechTrainCourse" id="<%=plan.getTrainId() %>" property="name"/></span>
						<%	
							}
						%>
					</td>
				<%}%>
				<%
							}
						}
					}
				%> 
				 </tr>
				<%
				}
				%> 
			</table>
			<div class="btns">
				<span class="btn">
					<a href="javascript:;" onclick="clickDay('','<%=year%>','<%=month+1%>','1')">${i18n.button.add}</a>
				</span>
			</div>
		</div>
	</div>
	
	<%@include file="/common/leftmenu.jsp" %>
	<div class="clearfix_b"></div>
</div>
<%@include file="/common/footer.jsp" %>
</body> 
</html>