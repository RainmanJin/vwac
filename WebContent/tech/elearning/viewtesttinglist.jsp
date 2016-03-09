<%@include file="/common/context.jsp"%>
<script>
$(document).ready(function(){
	initHtmlForm("#ec_ajax", $("#jmodal-container-content"),function(d){});	
});

</script>
<ec:table tableId="ec_ajax" action="viewTesttingInstances?courseInstanceId=${param.courseInstanceId}&courseId=${param.courseId}" method="post"
	items="list" var="var" view="ecan" width="99%"
	imagePath="${coreImgPath}/table/*.gif" showExports="false"
	onInvokeAction="$('#ec_ajax').submit();" autoIncludeParameters="false">
	<ec:row>
		<ec:column title="${i18n.techdb.filed.testtingtime}" cell="date"  format="yyyy-MM-dd HH:mm:ss"   property="testingTime"
			width="30%"></ec:column>
		<ec:column title="${i18n.techdb.filed.testtingPoint}" property="testingPoint"
		width="15%"></ec:column>
		<ec:column title="${i18n.oper.text}" property="oper" width="13%"
			filterCell="oper" sortable="false">
			<a  class="oper" href="viewTestting?testtingInstanceId=${var.id}">${i18n.oper.viewanswer}</a>
		</ec:column>
	</ec:row>
</ec:table>