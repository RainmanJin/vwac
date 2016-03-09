<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<%@page import="java.util.List"%>
<%@page import="com.ecannetwork.tech.controller.bean.testactive.Row"%>
<%@page import="com.ecannetwork.dto.tech.TechTestActive"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.ecannetwork.dto.tech.TechTestActivePoint"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="com.ecannetwork.tech.controller.bean.testactive.StatRow"%>
<%@page import="java.util.Map"%>
<%@page import="com.ecannetwork.tech.util.TechConsts"%><html>
<%
						DecimalFormat dft = new DecimalFormat("####.##");
						List srows = (List)request.getAttribute("stats");
						Double levelOne = (Double)request.getAttribute("levelOne");
						Double levelTwo = (Double)request.getAttribute("levelTwo");
%>
				
	<table cellspacing="0"  cellpadding="0"  class="tableRegion"  width="500" >
		<tbody class="tableBody" >
		<tr>
			<td class="blueRow">${i18n.testactive.viewStat.dim}</td>
			<td class="blueRow">${i18n.testactive.viewStat.student}</td>
			<td class="blueRow">${i18n.testactive.viewStat.index}</td>
			<td class="blueRow">${i18n.testactive.viewStat.rank}</td>
		</tr>
<%
	Double l2 = new Double(0);
	Double l4 = new Double(0);
	Double l6 = new Double(0);

	for(int i=0; i<srows.size(); i++)
	{
		StatRow srow = (StatRow)srows.get(i);
		Double point = srow.getAvg28();
		if(point == null)
			point = new Double(0);
		
		if(i<2)
		{
			l2 += (srow.getPointIndex() - point);
		}else
		{
			if(i<6)
			{
				l4 += (srow.getPointIndex() - point);	
			}else
			{
				l6 += (srow.getPointIndex() - point);
			}
		}
	}
	l2 = Math.abs(l2/2);
	l4 = Math.abs(l4/4);
	l6 = Math.abs(l6/6);
	
	
	for(int i=0; i<srows.size(); i++)
	{
		StatRow srow = (StatRow)srows.get(i);
		Double point = srow.getAvg28();
		if(point == null)
			point = new Double(0);
%>
		<tr align="left" class="<%=i%2==0?"even":"odd" %>">
			<td class="tableHeader"><%=srow.getDimName()%></td>
			<td><%=dft.format(point)%></td>
			<td><%=dft.format(srow.getPointIndex())%></td>
<%
		if(i==0)
		{
%>
			<td rowspan="2">
<%
				if(levelOne>l2)
				{
%>
				${i18n.testactive.expert}
<%
				}else
				{
					if(levelTwo>l2)
					{
%>
				${i18n.testactive.experienced}
<%
					}else
					{
%>
				${i18n.testactive.basic}
<%
					}
				}
%>
			</td>
<%
		}else
		{
			if(i==2)
			{
%>
			<td rowspan="4">
<%
				if(levelOne>l2)
				{
%>
				${i18n.testactive.expert}
<%
				}else
				{
					if(levelTwo>l2)
					{
%>
				${i18n.testactive.experienced}
<%
					}else
					{
%>
				${i18n.testactive.basic}
<%
					}
				}
%>
			</td>
<%				
			}else
			{
				if(i==6)
				{
%>
			<td rowspan="6">
<%
				if(levelOne>l2)
				{
%>
				${i18n.testactive.expert}
<%
				}else
				{
					if(levelTwo>l2)
					{
%>
				${i18n.testactive.experienced}
<%
					}else
					{
%>
				${i18n.testactive.basic}
<%
					}
				}
%>
			</td>
<%
				}
			}
		}
%>
		</tr>
<%
	}
%>
		</tbody>
	</table>