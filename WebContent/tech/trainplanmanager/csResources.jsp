<%@include file="/common/context.jsp" %>
<script>
$(document).ready(function(){
	initHtmlForm("#ec_ajax", $("#jmodal-container-content"),function(d){});	
});
</script>
			<ec:table tableId="ec_ajax" action="listCSResources?id=${param.id}" method="post" items="list" var="var" view="ecan" width="99%"
					imagePath="${coreImgPath}/table/*.gif" showExports="false" onInvokeAction="$('#ec_ajax').submit();" autoIncludeParameters="false">
									
					<ec:row>
						<ec:column title="${i18n.resourse.name}" property="resName" width="280">
						</ec:column>
						<ec:column title="${i18n.resourse.type}" property="resType" width="80" filterCell="droplist" filterOptions="DOMAIN_COMMONLYUSED">
							<d:viewDomain domain="COMMONLYUSED"  value="${var.resType}"/>
						</ec:column>
						<ec:column title="${i18n.resourse.num}" property="intResSum" width="80">
						</ec:column>
						<ec:column title="${i18n.consumables.inventory}" property="idleSum" width="80">
												${var.idleSum<0?0:var.idleSum}
						</ec:column>
						<ec:column title="${i18n.resourse.allocation}" property="assgin" filterable="false" sortable="false">
							<c:choose>
								<c:when test="${var.idleSum>0}">
									<input name="csResource" id="${var.id}" type="text" style="width: 50px;" maxlength="4" />								
								</c:when>
								<c:otherwise>
									<input type="text" class="gray" disabled="disabled" style="width: 50px;" />
								</c:otherwise>
							</c:choose>
						</ec:column>
					</ec:row>
			</ec:table>