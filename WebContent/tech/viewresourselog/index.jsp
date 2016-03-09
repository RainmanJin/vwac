<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@include file="/common/head.jsp" %>
</head>
<body>
<%@include file="/common/header.jsp" %>

<div class="centerbody centerbox">
	<div id="contentwrapper">
		<div id="content_right">
	<div class="tt">${i18n.goods.title}</div>
			<ec:table action="index" method="get" items="resourseList" var="var" view="ecan" width="99%"
					imagePath="${coreImgPath}/table/*.gif" showExports="false">
					<ec:row>
						<%--
						<ec:column headerCell="selectAll" filterable="false" sortable="false" width="3%" alias="ids">
							<input type="checkbox" name="ids" value="${var.id}" />
						</ec:column>
						 --%>
						<ec:column title="${i18n.goods.type}" property="resType" width="10%" filterCell="droplist" filterOptions="DOMAIN_RESOURSETYPE">
						<d:viewDomain domain="RESOURSETYPE" value="${var.resType}" />
						</ec:column>
						<ec:column title="${i18n.goods.name}" property="resId" width="130">
							<c:choose>
								<c:when test="${var.resType eq '1'}">
									<ecan:viewDto dtoName="TechConsumablesManager" property="conName" id="${var.resId}"/>
								</c:when>
								<c:when test="${var.resType eq '0'}">
									<ecan:viewDto dtoName="TechResourseManager" property="resName" id="${var.resId}"/>
								</c:when>
							</c:choose>
						</ec:column>
						<ec:column title="${i18n.goods.sum}" property="intResSum" width="50">
						</ec:column>
						<ec:column title="${i18n.domains.resoursetype.opertype}" property="operType" width="100" filterCell="droplist" filterOptions="DOMAIN_RESOURSEOPERTYPE">
						<d:viewDomain domain="RESOURSEOPERTYPE" value="${var.operType}" />
						</ec:column>
						<ec:column title="${i18n.user.filed.name}" property="operName" width="60">
						</ec:column>
						
						<ec:column title="${i18n.resourselog.date}" property="insertDate" width="180">
						</ec:column>
						<ec:column title="${i18n.oper.text}" property="oper" width="120" filterCell="oper" sortable="false">
								<a href="view?id=${var.id}" class="oper">${i18n.oper.view}</a>
						</ec:column>
					</ec:row>
			</ec:table>
				
				<div class="btns">
				</div>			
			
		</div>
	</div>
	
	<%@include file="/common/leftmenu.jsp" %>
	<div class="clearfix_b"></div>
</div>
<%@include file="/common/footer.jsp" %>
</body> 
</html>