<%@include file="/common/context.jsp"%>
<script>
$(document).ready(function(){
	initHtmlForm("#ec_ajax", $("#jmodal-container-content"),function(d){});	
});

</script>
<ec:table tableId="ec_ajax" action="viewstudentstatus?id=${param.id}" method="post"
	items="list" var="var" view="ecan" width="99%"
	imagePath="${coreImgPath}/table/*.gif" showExports="false"
	onInvokeAction="$('#ec_ajax').submit();" filterable="false" showStatusBar="false" sortable="false" autoIncludeParameters="false">
	<ec:row>
		<ec:column title="${i18n.operman.studentname}" property="studentId" width="20%">
			<ecan:viewDto property="name" dtoName="EcanUser" id="${var.studentId}" />
		</ec:column>
		<ec:column title="${i18n.operman.company}" property="courseInstanceId" width="20%">
			<d:viewDomain domain="COMPANY" value="${ef:viewDto('EcanUser',var.studentId,'company')}"/>
		</ec:column>
		<ec:column title="${i18n.appname.testreport}" property="testingPoint" width="20%">
		</ec:column>
		<ec:column title="${i18n.operman.endtime}" property="testingTime" width="20%">
			<ecan:dateFormart value="${var.testingTime}" formart="yyyy-MM-dd HH:mm:ss"/>
		</ec:column>
		<ec:column title="${i18n.operman.isfinish}" property="isFinish" width="10%">
			<d:viewDomain domain="ISFINISH" value="${var.isFinish}"/>
		</ec:column>
	</ec:row>
</ec:table>

