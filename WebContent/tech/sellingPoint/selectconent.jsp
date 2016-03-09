
<%@include file="/common/context.jsp" %>
<script>
$(document).ready(function(){
	initHtmlForm("#ec_ajax", $("#jmodal-container-content"),function(d){});	
});
</script>
			<ec:table tableId="ec_ajax" action="listconents?id=${param.id}" method="post" items="list" var="var" view="ecan" width="99%"
					imagePath="${coreImgPath}/table/*.gif" showExports="false" onInvokeAction="$('#ec_ajax').submit();" autoIncludeParameters="false">
					<ec:row>
							<ec:column headerCell="selectAll" property="oper" alias="selIds" width="2%" filterable="false">
								<input type="checkbox" name="selIds" value="${var.id}" />
							</ec:column>
						<ec:column title="${i18n.selling.pointtitle}" property="title" width="10%">
							<a href="#nogo" onclick="chgHoster('${var.id}')">${var.title}</a>
						</ec:column>
					</ec:row>
			</ec:table>