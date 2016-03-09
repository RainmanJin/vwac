<%@page import="java.util.Iterator"%><%@page import="java.util.Map"%>
<%@page contentType="text/html; charset=UTF-8" %>
<%@include file="/common/context.jsp" %>

<%@page import="com.ecannetwork.tech.controller.bean.testactive.Points"%>
<%@page import="java.text.DecimalFormat"%>
<%
DecimalFormat df = new DecimalFormat("#####.##");
%>

<%@page import="com.ecannetwork.dto.tech.TechTestUser"%><c:if test="${!(empty testUser.pointCn)}">
<div class="eXtremeTable" >
	<table border="0"  cellspacing="0"  cellpadding="0"  class="tableRegion"  width="100%" >
		<thead>
			<tr>
				<td class="tableHeader" width="30%">&nbsp;</td>
				<td class="tableHeader" width="35%">${i18n.testactive.testfiled.pointCN}</td>
				<td class="tableHeader" width="35%">${i18n.testactive.testfiled.pointDE}</td>
			</tr>
		</thead>
		<tbody class="tableBody" >
			<tr>
				<td>${i18n.testactive.titleTestOnline}</td>
				<td>${testUser.pointCn}
					<c:if test="${!(empty testUser.pointCn)}">
					(<c:choose>
						<c:when test="${levelOne>=testUser.pointCn}">
							${i18n.testactive.basic}
						</c:when>
						<c:when test="${levelTwo>testUser.pointCn}">
							${i18n.testactive.experienced}
						</c:when>
						<c:otherwise>
							${i18n.testactive.expert}
						</c:otherwise>
					</c:choose>)
					</c:if>
				</td>
				<td>${testUser.pointDe}&nbsp;
					<c:if test="${!(empty testUser.pointDe)}">
					(<c:choose>
						<c:when test="${levelOne>=testUser.pointDe}">
							${i18n.testactive.basic}
						</c:when>
						<c:when test="${levelTwo>testUser.pointDe}">
							${i18n.testactive.experienced}
						</c:when>
						<c:otherwise>
							${i18n.testactive.expert}
						</c:otherwise>
					</c:choose>)
					</c:if>
				</td>
			</tr>
			<tr>
				<td>${i18n.domainlabel.teststep.Expertise}</td>
				<td>
					${zyk}
				</td>
				<td>
					${zyk}
				</td>
			</tr>
			<tr>
				<td>${i18n.testactive.result.final}</td>
				<%
					TechTestUser testUser = (TechTestUser)request.getAttribute("testUser");
					Integer zyk = (Integer)request.getAttribute("zyk");
					if(testUser != null && zyk != null)
					{
				%>
				<td><%=df.format((zyk + testUser.getPointCn())/2) %></td>
				<td><%=df.format((zyk + testUser.getPointDe())/2) %></td>
				<%
					}else
					{
				%>
				<td></td>
				<td></td>
				<%
					}
				%>
			</tr>
			
		</tbody>
	</table>
	
	<table border="0"  cellspacing="0"  cellpadding="0"  class="tableRegion"  width="100%" >
		<thead>
			<tr>
				<td class="tableHeader">${i18n.techdb.filed.courseName}</td>
				<td class="tableHeader">${i18n.techdb.filed.moduleName}</td>
				<td class="tableHeader">${i18n.testactive.testfiled.pointCN}</td>
				<td class="tableHeader">${i18n.testactive.testfiled.pointDE}</td>
			</tr>
		</thead>
		<tbody class="tableBody" >
		<%
			Map results = (Map) request.getAttribute("result");
		
			for(Iterator it = results.keySet().iterator(); it.hasNext();)
			{
				String course = (String)it.next();
				Map modules = (Map)results.get(course);
				
				int i=0;
				for(Iterator mIt = modules.keySet().iterator(); mIt.hasNext();)
				{
					i++;
					String module = (String)mIt.next();
					Points p = (Points) modules.get(module);
		%>
				<tr class="odd"  onmouseover="this.className='highlight'"  onmouseout="this.className='odd'" >
					<%
						if(i==1)
						{
					%>
					<td rowspan="<%=modules.size()%>">
						<ecan:viewDto property="name" dtoName="TechTrainCourse" id="<%=course%>"/>
					</td>
					<%
						}
					%>
					<td>
					<%
						if(!"common".equals(module))
						{
					%>
						<ecan:viewDto property="name" dtoName="TechTrainCourseItem" id="<%=module %>"/>					
					<%
						}else
						{
					%>
						&nbsp;					
					<%
						}
					%>
					</td>
					<td><%=df.format(p.getPointCn100()) %></td>
					<td><%=df.format(p.getPointDe100()) %></td>
				</tr>		
		<%
				}
			}
		%>
		</tbody>
	</table>
</div>
</c:if>