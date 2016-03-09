<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp"%>
<script>
	$(document).ready(function() {
		initHtmlForm("#ec_ajax", $("#jmodal-container-content"), function(d) {
		});
	});
</script>

<ec:table tableId="ec_ajax" action="${ctxPath}/techc/commonresource/listTrainCourseModelByProTypeAndBrand?brand=${param.brand}&proType=${param.proType}" method="get" items="list" var="var" view="ecan"
	width="99%" imagePath="${coreImgPath}/table/*.gif" showExports="false" onInvokeAction="$('#ec_ajax').submit();" autoIncludeParameters="false">
	<ec:row>
		<ec:column title="${i18n.techdb.filed.courseName}" property="courseName" width="30%"></ec:column>
		<ec:column title="${i18n.techdb.filed.moduleName}" property="moduleName" width="30%"></ec:column>
		<ec:column title="${i18n.traincourse.filed.proType}" property="proType" width="10%" filterCell="droplist" filterOptions="DOMAIN_PROTYPE">
			<d:viewDomain value="${var.proType}" domain="PROTYPE" />
		</ec:column>
		<ec:column title="${i18n.traincourse.filed.brand}" property="brand" width="10%" filterCell="droplist" filterOptions="DOMAIN_BRAND">
			<d:viewDomain value="${var.brand}" domain="BRAND" />
		</ec:column>
		<ec:column title="${i18n.oper.text}" property="oper" width="15%" filterCell="oper" sortable="false">
			<a href="javascript:;" class="oper" onclick="selectCourseModule('${var.moduleid}','${var.moduleName}','${var.courseid}','${var.courseName}','${var.days}')">${i18n.oper.select}</a>&nbsp;
						</ec:column>
	</ec:row>
</ec:table>
