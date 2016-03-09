<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<c:if test="${empty ajaxRequest}">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="java.util.List"%>
<%@page import="com.ecannetwork.tech.controller.bean.testactive.Row"%>
<%@page import="com.ecannetwork.dto.tech.TechTestActive"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.ecannetwork.dto.tech.TechTestActivePoint"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="com.ecannetwork.tech.controller.bean.testactive.StatRow"%>
<%@page import="java.util.Map"%>
<%@page import="com.ecannetwork.tech.util.TechConsts"%><html>
<head>
	<%@include file="/common/head.jsp" %>
	
	<style type="text/css">
		<%@include file="/platform/css/extremecomponents.css" %>
		
		.eXtremeTable .tableRegion{
			margin: 0;
		}
		
		.eXtremeTable td{
			border-bottom: 2px solid #AAAAAA; border-right: 2px solid #AAAAAA;
		}
		
		.blueRow{
			background: #2274AC; color: #fff; font-size: 14px;
		}
 		
		.pipt{
			text-align: center; font-size: 14px; color: blue;
		}
		
		.eXtremeTable .tableHeader{
			font-weight: normal;
			border-bottom: 2px solid #AAAAAA; border-right: 2px solid #AAAAAA;
		}

		.eXtremeTable td.avg,.eXtremeTable td.stp{
			font-size: 14px;
		}
	</style>
</head>
<body>
</c:if>
<script>
		$().ready(function(){
				$("#userLevels").load("${ctxPath}/techc/testactive/viewPointLevels?id=${param.id}&userid=${param.userid}");
			});
</script>
<%
						DecimalFormat dft = new DecimalFormat("####.##");
					
						TechTestActive active = (TechTestActive) request.getAttribute("dto");
						List rows = (List)request.getAttribute("dimension");//大维度
						List srows = (List)request.getAttribute("stats");
						int watchMensCount = active.getWatchMenIds().size();
%>
				
<div class="eXtremeTable" style="width: <%=1300+active.getWatchMenIds().size()*55 * 4 %>px; margin:10px;">
	<div id="userLevels">
	
	</div>
	<table cellspacing="0"  cellpadding="0"  class="tableRegion"  width="100%" >
		<tbody class="tableBody" >
			<tr>
				<td style="font-size: 20px; height: 40px; border-right: none;" colspan="<%= watchMensCount*4 + 7%>">
					${i18n.testactive.viewStat.reportBlTo}:
					<ecan:viewDto property="name" dtoName="EcanUser" id="${param.userid}"/>
					
					<span class="btn" style="">
							<a href="${ctxPath}/techc/testactive/downloadPointStat?id=${dto.id}&userid=${param.userid}" target="_blank">${i18n.testactive.button.downloadPointStat}</a>
					</span>
				</td>
			</tr>
			<tr align="center">
				<td class="blueRow" width="200">${i18n.testactive.viewStat.jobReq}:
					<d:viewDomain value="${dto.proType}" domain="PROTYPE"/>
				</td>
<%
						for(int i=0; i<rows.size(); i++)
						{
							Row row = (Row)rows.get(i);
%>
				<td class="blueRow" colspan="<%=row.getDimensionId().equals(TechConsts.PTP)?2:watchMensCount%>">
					<d:viewDomain value="<%=row.getDimensionId()%>" domain="TESTSTEP" />
				</td>
<%
						}
%>
				<td class="blueRow" width="55">${i18n.testactive.viewStat.student}</td>
				<td class="blueRow" width="55">${i18n.testactive.viewStat.index}</td>
				<td class="blueRow" width="55">${i18n.testactive.viewStat.absSpaceing}</td>
				<td class="blueRow" width="55">${i18n.testactive.viewStat.watchMenSpaceing2}</td>
			</tr>
			
			<tr align="center">
				<td style="text-align: right;" class="tableHeader">${i18n.testactive.viewStat.watchmen}：</td>
<%
						for(int i=0; i<rows.size(); i++)
						{
							Row row = (Row) rows.get(i);
							if(row.getDimensionId().equals(TechConsts.PTP))
							{
%>
				<td width="60" class="tableHeader">${i18n.testactive.viewStat.ptp100}</td>
				<td width="55" class="tableHeader">${i18n.testactive.viewStat.ptp6}</td>
<%
							}else
							{
%>
				<c:forEach items="${dto.watchMenIds}" var="var" varStatus="status">
					<td width="55" class="tableHeader"><ecan:viewDto property="name" dtoName="EcanUser" id="${var}"/></td>
				</c:forEach>
<%
							}
						}
%>
				<td width="55" class="tableHeader">&nbsp;</td>
				<td width="55" class="tableHeader">&nbsp;</td>
				<td width="55" class="tableHeader">&nbsp;</td>
				<td width="55" class="tableHeader">&nbsp;</td>
			</tr>
<%
						for(int i=0; i<srows.size(); i++)
						{
							StatRow srow = (StatRow)srows.get(i);
%>
			<tr align="left" class="<%=i%2==0?"even":"odd" %>">
				<td class="tableHeader"><%=srow.getDimName()%></td>
<%
								for(int k=0; k<rows.size(); k++)
								{//循环每个一级维度
									Row r = (Row)rows.get(k);
									if(r.getDimensionId().equals(TechConsts.PTP))
									{//PTP
										if(srow.getPtpPoint() != null)
										{
%>
				<td class="pipt"><%=dft.format(srow.getPtpPoint())%></td>
				<td class="pipt"><%=dft.format(srow.getPtpPoint()/20*+1)%></td>
<%
										}else
										{
%>
				<td colspan="2">&nbsp;</td>
<%											
										}
									}else
									{//观察员
										Map pp = (Map)srow.getPoints().get(r.getDimensionId());
										
										if(pp != null)
										{
											for(Iterator it = active.getWatchMenIds().iterator(); it.hasNext(); )
											{//循环每个观察员
												String watchMen = (String)it.next();
												Double p = (Double)pp.get(watchMen);
%>
				<%if(p!=null){%>
				<td class="pipt"><%=dft.format(p) %></td>
				<%}else{%>
				<td>&nbsp;</td>
				<%}%>
<%
											}
										}else
										{
%>
				<td colspan="<%=active.getWatchMenIds().size()%>">&nbsp;</td>
<%
										}
									}
								}
%>
				<td width="55" class="pipt">
				<%
					Double _avg = srow.getAvg28();
					if(_avg==null)
					{
				%>
					&nbsp;
				<%
					}else{
				%>
					<%=dft.format(_avg)%>
				<%
					}

					Double pointIndex = srow.getPointIndex();
					Double absToIndex = srow.getAbsToIndex();
				%>
				
				</td>
				<td width="55" class="pipt"><%=pointIndex == null?"&nbsp;":dft.format(srow.getPointIndex())%></td>
				<td width="55" class="pipt"><%=absToIndex == null?"&nbsp;":dft.format(srow.getAbsToIndex())%></td>
				<td width="55" class="pipt">
				<%
					if(srow.isWatchMenStep2())
					{
				%>
					<em style="color: red;">**</em>				
				<%
					}else
					{
				%>
					&nbsp;
				<%
					}
				%>
				</td>
			</tr>
<%
			}
%>
		</tbody>
	</table>
	
	<div>
		<img src="${ctxPath}/techc/testactive/resultImage?id=${param.id}&userid=${param.userid}" alt="pointimage" />
	</div>
	<div>
		<img src="${ctxPath}/techc/testactive/ptpImage.jpeg?id=${param.id}&userid=${param.userid}" alt="pointimage" />
	</div>
</div>

<c:if test="${empty ajaxRequest}">
</body> 
</html>
</c:if>