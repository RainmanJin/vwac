<%@page import="com.ecannetwork.dto.core.EcanUser"%>
<%@page import="com.ecannetwork.tech.facade.HolidayFacade"%>
<%@page import="com.ecannetwork.dto.tech.TechTrainCourse"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="com.ecannetwork.dto.tech.TechTrainPlan"%>
<%@page import="java.util.Iterator"%><html>
<head>
	<%@include file="/common/head.jsp" %>
	<style type="text/css">
		b.cBox{
			width: 10px; height: 10px;display: inline-block; margin-right: 4px;
		}
		
		.holiday{
			background: #c82d20;
		}
		
		.fill{
			background: #999;
		}
		
		.plan{
			background: #003c65;/*green*/
		}
		
		.confirm{
			background: #51ae30;/*blue*/
		}
		
		.cancel{
			background: #faaa00;/*yellow*/
		}
		
		.tips{
			display: none;
			position: absolute;
			text-align: left;
			background: #f0f0f0;
			border: 1px dotted #000;
			width: 300px;
		}
		
		.tips b{
			font-weight: bold;
			color: blue;
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
		
		td.ccc{
			color: #fff;
		}
	</style>
	<script type="text/javascript">
		$(document).ready(function(){
				$(".fill, .plan, .confirm, .cancel").hover(function(){
						$(this).find(".tips").show();
					},function(){
						$(this).find(".tips").hide();
					});
				$(".ccc").hover(function(){
					$(this).removeClass("ccc");
				},function(){
					$(this).addClass("ccc");
				})
			});
		function intelligence()
		{
			$.fn.jmodal({
	            title: '${i18n.trainplan.button.intelligence}',
	            width:600,
	            content: function(body) {
	                body.html('loading...');
	                body.load('intelligenceView?year=${param.year}&brand=${param.brand}');
	            },
	            buttonText: { ok: '${i18n.button.ok}', cancel: '${i18n.button.cancel}' },
	            okEvent: function(data, args) {
	            	//args.hide();
	            	$("#myForm").submit();
	            }
	        });
		}

		function plan(week, proType, courseId)
		{/*
			$.fn.jmodal({
	            title: '${param.year}${i18n.year.text}<d:viewDomain value="${param.brand}" domain="BRAND"/>${i18n.trainplan.title}',
	            width:600,
	            content: function(body) {
	                body.html('loading...');
	                body.load('viewPlan?year=${param.year}&brand=${param.brand}&week=' + week + "&proType=" + proType + "&courseId=" + courseId);
	            },
	            buttonText: { ok: '${i18n.button.ok}', ok2: '${i18n.button.del}', cancel: '${i18n.button.cancel}' },
	            okEvent: function(data, args) {
	            	//args.hide();
	            	$("#myForm").submit();
	            },
	            ok2Event: function(data, args){
	            	ajaxGet("del",{"year":"${param.year}","brand":"${param.brand}","week":week, "courseId":courseId},function(){
							location.reload();
		            	},'json');
		        }
	        });	*/
	        location.href='viewPlan?year=${param.year}&brand=${param.brand}&week=' + week + "&proType=" + proType + "&courseId=" + courseId;	
		}

		function changeMonth()
		{
			if($("#beginMonth").val() - $("#endMonth").val() >0)
			{
				alert("${i18n.trainplan.msg.beginLtEndMonth}");
				return;
			}else{
				location.href="view?year=${param.year}&brand=${param.brand}&beginMonth=" + $("#beginMonth").val() + "&endMonth=" + $("#endMonth").val();
			}
		}
	</script>
</head>
<body>
<%@include file="/common/header.jsp" %>
			<div class="tt btns">
				<span style="float: left; font-size: 14px; font-weight: bold; height: 36px; line-height: 36px;">
					${param.year}&nbsp;<d:viewDomain value="${param.brand}" domain="BRAND"/>${i18n.trainplan.title}&nbsp;&nbsp;&nbsp;
				</span>
				<%--
				<span class="btn" style="float: right;">
					<a href="stats?year=${param.year}&brand=${param.brand}" target="_blank">${i18n.trainplan.btn.stats}</a>
				</span>
				--%>
				<span class="btn" style="float: right;">
					<a href="index">${i18n.button.back}</a>
				</span>
				<span class="btn" style="float: right;">
					<a href="csResourceUsedReport?year=${param.year}&brand=${param.brand}">${i18n.trainplan.btn.resReport}</a>
				</span>
				
				<span class="btn" style="float: right;">
					<a target="_blank" href="download?year=${param.year}&brand=${param.brand}">${i18n.button.download}</a>
				</span>
				<!-- 
				<c:if test="${('planadm' eq current_user.role.id) or ('admin' eq current_user.role.id)}">
				<span class="btn" style="float: right;">
					<a href="javascript:intelligence()">${i18n.trainplan.button.intelligence}</a>
				</span>
				</c:if>
				 -->
			</div>
			<div>
				<b class="cBox holiday"></b>${i18n.trainplan.status.holiday}&nbsp;&nbsp;&nbsp;
				<b class="cBox fill"></b><d:viewDomain value="fill" domain="TRAINPLANSTATUS"/>&nbsp;&nbsp;&nbsp;
				<b class="cBox plan"></b><d:viewDomain value="plan" domain="TRAINPLANSTATUS"/>&nbsp;&nbsp;&nbsp;
				<b class="cBox confirm"></b><d:viewDomain value="confirm" domain="TRAINPLANSTATUS"/>&nbsp;&nbsp;&nbsp;
				<b class="cBox cancel"></b><d:viewDomain value="cancel" domain="TRAINPLANSTATUS"/>&nbsp;&nbsp;&nbsp;
				<%
					Integer beginMonth = (Integer) request.getAttribute("beginMonth");
					Integer endMonth = (Integer) request.getAttribute("endMonth");
				%>
				
				<span style="margin-left: 100px;">
					${i18n.trainplan.txt.fromMonth}:&nbsp;
					<d:selectDomain domain="MONTH" uid="beginMonth" onchange="changeMonth()" value="${beginMonth}"/>
					${i18n.trainplan.txt.toMonth}
					<d:selectDomain domain="MONTH" uid="endMonth" onchange="changeMonth()" value="${endMonth}"/>
				</span>
			</div>
			<%
								Integer year = (Integer)request.getAttribute("year");
								Map monthWeeks = (Map) request.getAttribute("monthWeeks");
			%>
			<div class="eXtremeTable">
			<table border="0" width="100%" bgcolor="#dfdfdf" cellpadding="0" cellspacing="0" align="center">
				<tr>
				<td style="height: auto;" valign="top">
					<table border="0" width="400" bgcolor="#dfdfdf" cellpadding="0" cellspacing="1" align="center">
						<tr>
							<th height="50" width="40" bgcolor="#f0f0f0">${i18n.sequnce}</th>
							<th height="50" width="150" bgcolor="#f0f0f0">
								${i18n.trainplan.filed.proType}
								<!-- 
								<span style="float: left;">${i18n.trainplan.filed.proType}</span>
								<a target="_blank" href="stats?year=${param.year}" style="float: right;"><img style="width: 20px; height: 20px; border: 0px;" src="${iconPath22}/download.png" /></a>
								 -->
							</th>
							<th height="50" width="340" bgcolor="#f0f0f0">${i18n.trainplan.filed.trainCourse}</th>
						</tr>
						<%
							Map map = (Map)request.getAttribute("map");
							int row = 0;
							for(Object key: map.keySet())
							{//循环每个领域
								String proType = (String)key;
								pageContext.setAttribute("_proType", proType);
								List courses = (List)map.get(proType);
								for(int idx = 0; idx<courses.size(); idx++)
								{//循环领域下的每个课程
									row++;
									TechTrainCourse course = (TechTrainCourse)courses.get(idx);
						%>
								<tr align="center" class="<%if(row%2==0){%>even<%}else{%>odd<%}%>">
									<td height="25" width="40"><%=row%></td>
									<%
										if(idx == 0)
										{
									%>
									<td height="25" width="120" rowspan="<%=courses.size()%>">
										<d:viewDomain value="${_proType}" domain="PROTYPE" />
										<!-- 
										<span style="float: left;">
											<d:viewDomain value="${_proType}" domain="PROTYPE" />
										</span>
										<a target="_blank" href="stats?year=${param.year}&brand=${param.brand}" style="float: right;"><img style="width: 20px; height: 20px; border: 0px;" src="${iconPath22}/download.png" /></a>
										-->
									</td>
									<%
										}
									%>
									<td height="25" width="240">
										<span style="float: left;"><%=course.getName()%></span>
										<a target="_blank" href="stats?year=${param.year}&brand=${param.brand}&course=<%=course.getId()%>" style="float: right;"><img style="width: 20px; height: 20px; border: 0px;" src="${iconPath22}/download.png" /></a>
									</td>
								</tr>
						<%
								}
							}
						%>
					</table>
				</td>
				<%
				int weekCounts =0;
				for(Object key: monthWeeks.keySet())
				{
					Integer mon = (Integer) key;
					if(mon<beginMonth || mon>endMonth)
					{//跳出非选择月份
						continue;
					}
					
					List weeks = (List) monthWeeks.get(mon);
					weekCounts += weeks.size();
				}
				%>
				<td width="560" valign="top"><!-- data部分 -->
					<div style="width: 560px; overflow: scroll; height: auto; display: block;">
					<table border="0" width="<%=weekCounts*25 %>" bgcolor="#dfdfdf" cellpadding="0" cellspacing="1" align="center">
						<tr>
							<%
								int mIdx = 0;
								for(Object key: monthWeeks.keySet())
								{
									Integer mon = (Integer) key;
									if(mon<beginMonth || mon>endMonth)
									{//跳出非选择月份
										continue;
									}
									List weeks = (List) monthWeeks.get(mon);
							%>
							<th height="25" width="340" bgcolor="#f0f0f0" colspan="<%=weeks.size()%>">
								<d:viewDomain value="<%=String.valueOf(mon)%>" domain="MONTH"/>
							</th>
							<%
								}
							%>
						</tr>
						<tr>
							<%
								for(Object key: monthWeeks.keySet())
								{
									Integer mon = (Integer) key;
									if(mon<beginMonth || mon>endMonth)
									{//跳出非选择月份
										continue;
									}
									List weeks = (List) monthWeeks.get(mon);
									for(int z =0; z<weeks.size(); z++)
									{
							%>
							<th height="25" width="340" bgcolor="#f0f0f0"><%=weeks.get(z)%></th>
							<%
									}
								}
							%>
						</tr>
						<%
							EcanUser currentUser = (EcanUser) session.getAttribute("current_user");

							row = 0;
							for(Object key: map.keySet())
							{//循环每个领域
								String proType = (String)key;
								pageContext.setAttribute("_proType", proType);
								List courses = (List)map.get(proType);
								for(int idx = 0; idx<courses.size(); idx++)
								{//循环领域下的每个课程
									row++;
									TechTrainCourse course = (TechTrainCourse)courses.get(idx);
						%>
								<tr align="center" class="<%if(row%2==0){%>even<%}else{%>odd<%}%>">
									<%
									for(Object kkk: monthWeeks.keySet())
									{
										Integer mon = (Integer) kkk;
										if(mon<beginMonth || mon>endMonth)
										{//跳出非选择月份
											continue;
										}
										List weeks = (List) monthWeeks.get(mon);
										for(int z =0; z<weeks.size(); z++)
										{
											Integer weekIdx = (Integer)weeks.get(z);
											
											String backGround = "ccc";
											String onclick = null;
											TechTrainPlan plan = course.getPlan(weekIdx);
											if(plan != null)
											{
												backGround = plan.getStatus();
												onclick = "plan('" + weekIdx + "','" + proType + "','" + course.getId() + "')";
											}else
											{
												if(HolidayFacade.isHolidayWeek(year,weekIdx))
												{
													backGround = "holiday";
													onclick = "void(0)";
												}else
												{
													if(currentUser.getRoleId().equals("planadm") || currentUser.getRoleId().equals("admin"))
													{
														onclick = "plan('" + weekIdx + "','" + proType + "','" + course.getId() + "')";
													}else{
														onclick = "void(0)";
													}
												}
											}
									%>
									
									<td height="25" width="25"  onclick="<%=onclick%>" class="<%=backGround%>" style="cursor: pointer;">
										<%=weekIdx%>
										<%
											String planRemark = null;
											if(plan != null)
											{
												planRemark = course.getPlanRemarks(weekIdx);
												if(planRemark != null && planRemark.length()>0)
												{
										%>
											<span class="form tips"><%=planRemark%></span>
										<%	
												}
											}
										%>
									</td>
									<%
										}
									}
									%>
								</tr>
						<%
								}
							}
						%>
					</table>
					</div>
				</td>
				</tr>
			</table>
			</div>
			<br />
			<br />
			<br />
			<br />
	<%@include file="/common/leftmenu.jsp" %>
	<div class="clearfix_b"></div>
<%@include file="/common/footer.jsp" %>
</body> 
</html>