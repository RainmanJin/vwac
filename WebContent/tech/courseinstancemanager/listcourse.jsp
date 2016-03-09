<%@include file="/common/context.jsp" %>
<script>
$(document).ready(function(){
	initHtmlForm("#ec_ajax", $("#jmodal-container-content"),function(d){});	
});

</script>
			<ec:table tableId="ec_ajax" action="canPublishCourse" method="post" items="list" var="var" view="ecan" width="99%"
					imagePath="${coreImgPath}/table/*.gif" showExports="false" onInvokeAction="$('#ec_ajax').submit();" autoIncludeParameters="false">
					<ec:row>
						<ec:column title="${i18n.course.filed.name}" property="name" width="30%"></ec:column>
						<ec:column title="${i18n.course.filed.createtime}" property="createTime" cell="date" format="yyyy-MM-dd HH:mm:ss" width="30%"></ec:column>
						<ec:column title="${i18n.oper.text}" property="oper" width="13%" filterCell="oper" sortable="false">
							<a href="view?cousreid=${var.id}" class="oper">${i18n.traincourse.filed.publish}</a>&nbsp;
						</ec:column>
					</ec:row>
			</ec:table>