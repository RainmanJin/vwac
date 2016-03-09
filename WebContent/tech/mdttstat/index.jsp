<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@include file="/common/head.jsp" %>
	<script type="text/javascript">
	</script>
</head>
<body>
<%@include file="/common/header.jsp" %>

<div class="centerbody centerbox">
	<div id="contentwrapper">
		<div id="content_right">
			<div class="tt">${i18n.mdttstat.title}</div>
			<ec:table action="" method="get" items="list" var="var" view="ecan" width="99%"
					imagePath="${coreImgPath}/table/*.gif" showExports="false"
					filterable="false" sortable="false">
					<ec:row>
						<ec:column title="${i18n.mdttstat.trainName}" property="courseName">
							<ecan:viewDto property="name" dtoName="TechTrainCourse" id="${var.courseName}" />
						</ec:column>
						<ec:column title="${i18n.mdttstat.trainWeek}" property="planWeek"></ec:column>
						<ec:column title="${i18n.mdttstat.pkgName}" property="pkgName"></ec:column>
						
						<ec:column title="${i18n.oper.text}" property="oper" width="10%" filterCell="oper" sortable="false">
							<a href="view?id=${var.pkgID}" class="oper">${i18n.oper.view}</a>
						</ec:column>
					</ec:row>
			</ec:table>
			
		</div>
	</div>
	
	<%@include file="/common/leftmenu.jsp"%>
	<div class="clearfix_b"></div>
</div>
<%@include file="/common/footer.jsp" %>
</body> 
</html>