<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/context.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- 调查结果分析列表 -->
<%@include file="/common/head.jsp"%>
</head>
<body>
	<%@include file="/common/header.jsp"%>
	<div class="centerbody centerbox">
		<div id="contentwrapper">
			<div id="content_right">
				<div class="tt">${i18n.appname.mw.vwsurveystatvote}</div>
				<div>${i18n.app.effectivesize} ${userfullCount}</div>
				<ec:table action="statresult" method="get" items="SubDatalist" var="var" view="ecan" width="99%" imagePath="${coreImgPath}/table/*.gif" showExports="false" filterable="false"
					showPagination="false">
					<ec:row>
						<ec:column title="${i18n.survey.name}" property="sub" width="90%">
						</ec:column>
						<ec:column title="${i18n.app.votes}" property="tp" width="10%">
							<c:choose>
								<c:when test="${empty var.tp}"></c:when>
								<c:otherwise>${var.tp}</c:otherwise>
							</c:choose>
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