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
			<div class="tt">
				${i18n.teachercourse.title}
			</div>
			<ec:table action="" method="get" items="list" var="var" view="ecan" width="99%"
					imagePath="${coreImgPath}/table/*.gif" showExports="false">
					<ec:row>
						<ec:column title="${i18n.user.filed.name}" property="name" width="10%"></ec:column>
						<ec:column title="${i18n.user.filed.loginName}" property="loginName" width="10%"></ec:column>
						<ec:column title="${i18n.user.filed.company}" property="company" width="20%" filterCell="droplist" filterOptions="DOMAIN_COMPANY">
							<d:viewDomain value="${var.company}" domain="COMPANY" />
						</ec:column>
						<%-- DELETE PROTYPE
						<ec:column title="${i18n.user.filed.proType}" property="proType" width="10%" filterCell="droplist" filterOptions="DOMAIN_PROTYPE">
							<d:viewDomain value="${var.proType}" domain="PROTYPE" />
						</ec:column>
						 --%>
						<ec:column title="${i18n.oper.text}" property="oper" width="10%" filterCell="oper" sortable="false">
							<a href="view?id=${var.id}" class="oper">${i18n.teachercourse.oper.courseList}</a>&nbsp;
						</ec:column>
					</ec:row>
			</ec:table>
		</div>
	</div>
	
	<%@include file="/common/leftmenu.jsp" %>
	<div class="clearfix_b"></div>
</div>
<%@include file="/common/footer.jsp" %>
</body> 
</html>