<%@include file="/common/context.jsp" %>
<script>
$(document).ready(function(){
	initHtmlForm("#ec_ajax", $("#jmodal-container-content"),function(d){});	
});
</script>
			<ec:table tableId="ec_ajax" action="listXHPResources" method="post" items="list" var="var" view="ecan" width="99%"
					imagePath="${coreImgPath}/table/*.gif" showExports="false" onInvokeAction="$('#ec_ajax').submit();" autoIncludeParameters="false">
				  <ec:row>
						<ec:column title="${i18n.consumables.name}" property="conName" width="280">
						</ec:column>
						<ec:column title="${i18n.consumables.num}" property="intConSum" width="80"></ec:column>
						<ec:column title="${i18n.resourse.allocation}" property="assgin" filterable="false" sortable="false">
							<c:choose>
								<c:when test="${var.intConSum>0}">
									<input name="xhpResource" id="${var.id}" type="text" style="width: 50px;" maxlength="4" />								
								</c:when>
								<c:otherwise>
									<input type="text" class="gray" disabled="disabled" style="width: 50px;" />
								</c:otherwise>
							</c:choose>
						</ec:column>
					</ec:row>
			</ec:table>