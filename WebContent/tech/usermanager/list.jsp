<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
			<ec:table action="${ctxPath}/techc/usermanager/listByPrefix?prefix=${param.prefix}&first=${param.first}" method="get" items="list" var="var" view="ecan" width="99%"
					imagePath="${coreImgPath}/table/*.gif" showExports="true" showPagination="false" rowsDisplayed="10000" sortable="false" filterable="false">
					<ec:exportXls fileName="${param.prefix}batch_users.xls"></ec:exportXls>
					<ec:row>
						<ec:column title="${i18n.user.filed.loginName}" property="loginName" width="50%"></ec:column>
						<ec:column title="${i18n.user.filed.password}" property="loginPasswd" width="50%"></ec:column>
					</ec:row>
			</ec:table>