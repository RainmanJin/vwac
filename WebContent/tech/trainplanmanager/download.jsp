<%@page import="com.ecannetwork.tech.facade.HolidayFacade"%>
<%@page import="com.ecannetwork.dto.tech.TechTrainCourse"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="com.ecannetwork.dto.tech.TechTrainPlan"%><html>
<head>
	<style type="text/css">
		<%@include file="/platform/css/extremecomponents.css" %>
		*{
			margin:0px;
			padding:0px;
			font:normal 12px "宋体","Arial Narrow";
			color: #333;
		}
	
		b.cBox{
			width: 10px; height: 10px;display: inline-block;
		}
		
		.holiday{
			background: #DEDEDE;
		}
		
		.fill{
			background: red;
		}
		
		.plan{
			background: green;
		}
		
		.confirm{
			background: blue;
		}
		
		.cancel{
			background: yellow;
		}
		
	</style>
</head>
<body>
			<div>
				<b class="cBox holiday"></b>${i18n.trainplan.status.holiday}&nbsp;&nbsp;&nbsp;
				<b class="cBox fill"></b><d:viewDomain value="fill" domain="TRAINPLANSTATUS"/>&nbsp;&nbsp;&nbsp;
				<b class="cBox plan"></b><d:viewDomain value="plan" domain="TRAINPLANSTATUS"/>&nbsp;&nbsp;&nbsp;
				<b class="cBox confirm"></b><d:viewDomain value="confirm" domain="TRAINPLANSTATUS"/>&nbsp;&nbsp;&nbsp;
				<b class="cBox cancel"></b><d:viewDomain value="cancel" domain="TRAINPLANSTATUS"/>&nbsp;&nbsp;&nbsp;
			</div>
			<div style="position: absolute;" class="eXtremeTable">
			<table border="0" width="1700" bgcolor="#dfdfdf" cellpadding="0" cellspacing="1" style="min-height: 300px;" align="center">
				<tr>
					<th height="25" width="40" bgcolor="#f0f0f0" rowspan="2">${i18n.sequnce}</th>
					<th height="25" width="120" bgcolor="#f0f0f0" rowspan="2">${i18n.trainplan.filed.proType}</th>
					<th height="25" width="340" bgcolor="#f0f0f0" rowspan="2">${i18n.trainplan.filed.trainCourse}</th>
					<%
						Integer year = (Integer)request.getAttribute("year");
						Map monthWeeks = (Map) request.getAttribute("monthWeeks");
						int mIdx = 0;
						for(Object key: monthWeeks.keySet())
						{
							Integer k = (Integer) key;
							List weeks = (List) monthWeeks.get(k);
					%>
					<th height="25" width="340" bgcolor="#f0f0f0" colspan="<%=weeks.size()%>">
						<d:viewDomain value="<%=String.valueOf(k)%>" domain="MONTH"/>
					</th>
					<%
						}
					%>
				</tr>
				<tr>
					<c:forEach var="var" begin="1" end="52" step="1">
					<th height="25" width="25" bgcolor="#f0f0f0">${var}</th>
					</c:forEach>
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
							</td>
							<%
								}
							%>
							<td height="25" width="240"><%=course.getName()%></td>
							<%
								for(int weekIdx = 1; weekIdx<=52; weekIdx++)
								{
									String backGround = null;
									TechTrainPlan plan = course.getPlan(weekIdx);
									if(plan != null)
									{
										backGround = plan.getStatus();
									}else
									{
										if(HolidayFacade.isHolidayWeek(year,weekIdx))
										{
											backGround = "holiday";
										}
									}
							%>
							<td height="25" width="25" class="<%if(backGround != null){ out.print(backGround);}%>" style="cursor: pointer;">&nbsp;</td>
							<%
								}
							%>
						</tr>
				<%
						}
					}
				%>
			</table>
			</div>
	<%@include file="/common/leftmenu.jsp" %>
	<div class="clearfix_b"></div>
<%@include file="/common/footer.jsp" %>
</body> 
</html>