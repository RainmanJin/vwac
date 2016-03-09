<%@page contentType="text/html; charset=UTF-8" %>
<%@include file="/common/context.jsp" %>

<%@page import="java.text.DecimalFormat"%>
<%@page import="com.ecannetwork.dto.tech.TechTestUser"%><style>
	table.ppppp td{
		border-left: 1px solid #ddd; border-right: 1px solid #ddd; border-bottom: 1px solid #ddd; text-align: center; padding: 2px;
	}
	.tr_{
		font-size: 14px; line-height: 30px; height: 30px;
	}
	.tp_{
		font-size: 30px; line-height: 40px; height: 40px; color: blue; font-weight: bold;
		padding-left: 20px;	
	}
	
</style>

<table class="ppppp" width="90%" align="center" border="0" cellpadding="0" cellspacing="0" style="padding-left: 20px;">
	<tr>
		<td colspan="3" style="font-size: 14px; font-weight: bold; border-bottom: 1px dotted #aaa; text-align: left;">
			${i18n.testactive.testfiled.name}:&nbsp;&nbsp;<ecan:viewDto property="name" dtoName="EcanUser" id="${dto.userId}"/>
		</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td width="30%" class="tr_">${i18n.testactive.testfiled.pointCN}</td>
		<td width="30%" class="tr_">${i18n.testactive.testfiled.pointDE}</td>
	</tr>
	<tr>
		<td class="tr_">${i18n.testactive.titleTestOnline}</td>
		<td>${dto.pointCn}</td>
		<td>${dto.pointDe}</td>
	</tr>
	<tr>
		<td class="tr_">${i18n.domainlabel.teststep.Expertise}</td>
		<td>${zyk}</td>
		<td>${zyk}</td>
	</tr>
	<tr>
		<td class="tr_">${i18n.testactive.testfiled.point}</td>
		<%
			Integer tpzyk = (Integer) request.getAttribute("zyk");
			com.ecannetwork.dto.tech.TechTestUser tuser = (com.ecannetwork.dto.tech.TechTestUser) request.getAttribute("dto");
			
			java.text.DecimalFormat df = new java.text.DecimalFormat("#####.##");

		%>
		
		<td class="tp_"><%=df.format((tpzyk + tuser.getPointCn())/2) %></td>
		<td class="tp_"><%=df.format((tpzyk + tuser.getPointDe())/2) %></td>
	</tr>
	
	<tr>
		<td>&nbsp;</td>
		<td>
			<span class="btn">
				<a href="javascript:void(0)" onclick="confirmTestSubmit('${dto.id}','cn')">${i18n.testactive.testoper.submit}</a>
			</span>
		</td>
		<td>
			<span class="btn">
				<a href="javascript:void(0)" onclick="confirmTestSubmit('${dto.id}','de')">${i18n.testactive.testoper.submit}</a>
			</span>
		</td>
	</tr>
</table>
