<%@include file="/common/context.jsp"%>
<script>
$(document).ready(function(){
	initHtmlForm("#ec_ajax", $("#jmodal-container-content"),function(d){});	
});

</script>
<ec:table tableId="ec_ajax" action="viewstudystatus?id=${param.id}&courseId=${param.courseId}" method="post"
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
		<ec:column title="${i18n.operman.doprocess}" property="studentCoursePro" width="15%">
			${var.studentCoursePro==null?"0":var.studentCoursePro}%   &nbsp;(${var.studentScoitem}/${var.scoitemCount})
		</ec:column>
	</ec:row>
</ec:table>

