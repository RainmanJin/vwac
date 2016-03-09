<%@include file="/common/context.jsp"%>
<script>
$(document).ready(function(){
	initHtmlForm("#ec_ajax", $("#jmodal-container-content"),function(d){});	
});

</script>
<ec:table tableId="ec_ajax" action="listAttchment" method="post"
	items="attchmentList" var="var" view="ecan" width="99%"
	imagePath="${coreImgPath}/table/*.gif" showExports="false"
	onInvokeAction="$('#ec_ajax').submit();" autoIncludeParameters="false">
	<ec:row>
		<ec:column title="${i18n.course.text.attachementName}" property="name"
			width="30%"></ec:column>
		<ec:column title="${i18n.course.filed.createtime}"
			property="createTime" width="30%"></ec:column>
		<ec:column title="${i18n.oper.text}" property="oper" width="13%"
			filterCell="oper" sortable="false">
			<a target="_blank" class="oper" href="${ctxPath}${var.url}">${i18n.course.tab.downloadAttachement}</a>
		</ec:column>
	</ec:row>
</ec:table>