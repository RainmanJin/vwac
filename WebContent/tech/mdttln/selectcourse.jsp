<%@include file="/common/context.jsp" %>
<script>
$(document).ready(function(){
	initHtmlForm("#ec_ajax", $("#jmodal-container-content"),function(d){});	
});
</script>
			<%-- <ec:table tableId="ec_ajax" action="listUsers?id=${param.id}&type=${param.type}&courseinstanceid=${param.courseinstanceid}" method="post" items="list" var="var" view="ecan" width="99%"
					imagePath="${coreImgPath}/table/*.gif" showExports="false" onInvokeAction="$('#ec_ajax').submit();" autoIncludeParameters="false"> --%>
					<ec:table tableId="ec_ajax" action="listcourse?id=${param.id}" method="post" items="list" var="var" view="ecan" width="99%"
					imagePath="${coreImgPath}/table/*.gif" showExports="false" onInvokeAction="$('#ec_ajax').submit();" autoIncludeParameters="false">
					<ec:row>
						<ec:column headerCell="selectAll" property="oper" alias="selIds" width="2%" filterable="false">
								<input type="checkbox" name="selIds" value="${var.id}" />
						</ec:column>
						<ec:column title="${i18n.mdttln.coursename}" property="fixedName" width="10%">
							${var.fixedName}
						</ec:column>
					</ec:row>
			</ec:table>