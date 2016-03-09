<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/context.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- 调查结果分析 -->
<%@include file="/common/head.jsp"%>
</head>
<body>
	<%@include file="/common/header.jsp"%>
	<div class="centerbody centerbox">
		<div id="contentwrapper">
			<div id="content_right">
				<div class="tt">${i18n.appname.mw.vwsurveystatvote}</div>
				<ec:table action="index" method="get" items="dblist" var="var" view="ecan" width="99%" imagePath="${coreImgPath}/table/*.gif" showExports="false">
					<ec:row>
						<ec:column title="${i18n.survey.name}" property="CTitle" width="20%">
							<ecan:SubString content="${var.CTitle}" end="14" fix="..." />
						</ec:column>
						<ec:column title="${i18n.app.trainer}" property="CTearcher" width="15%"></ec:column>
						<ec:column title="${i18n.operman.starttime}" property="dtStartDate" cell="date" format="yyyy-MM-dd" width="10%"></ec:column>
						<ec:column title="${i18n.operman.endtime}" property="dtOverDate" cell="date" format="yyyy-MM-dd" width="10%"></ec:column>
						<ec:column title="${i18n.oper.text}" property="oper" width="45%" filterCell="oper" sortable="false">
							<a href="statresult?id=${var.id}&NSysId=${var.NSysId}" class="oper">${i18n.app.analysisresult}</a>
							<a href="statreport?id=${var.id}&NSysId=${var.NSysId}" class="oper">${i18n.app.graphreport}</a>
							<a href="excelallreport?id=${var.id}&NSysId=${var.NSysId}" class="oper">${i18n.app.exporttotal}</a>
							<a href="excelsinglereport?id=${var.id}&NSysId=${var.NSysId}" class="oper">${i18n.app.exportperson}</a>
						</ec:column>
					</ec:row>
				</ec:table>
			</div>
		</div>
		<%@include file="/common/leftmenu.jsp"%>
		<div class="clearfix_b"></div>
	</div>
	<%@include file="/common/footer.jsp"%>
</body>
</html>