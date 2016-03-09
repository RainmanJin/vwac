<%@include file="/common/context.jsp" %>
<script>
$(document).ready(function(){
	initHtmlForm("#ec_ajax", $("#jmodal-container-content"),function(d){});	
});
</script>
			<ec:table tableId="ec_ajax" action="listTitle?courseid=${courseid}&protype=${protype}" method="post" items="list" var="var" view="ecan" width="99%"
					imagePath="${coreImgPath}/table/*.gif" showExports="false" onInvokeAction="$('#ec_ajax').submit();" autoIncludeParameters="false">
					<ec:row>
						<ec:column headerCell="selectAll" property="oper" alias="selIdss" width="1%" filterable="false">
							<input type="checkbox" name="selIdss" value="${var.id}" />
						</ec:column>
						<ec:column title="${i18n.testting.filed.title}" property="title" width="17%"></ec:column>
						
						<ec:column title="${i18n.user.filed.proType}" property="proType" width="5%" filterCell="droplist" filterOptions="DOMAIN_PROTYPE">
							<d:viewDomain value="${var.proType}" domain="PROTYPE" />
						</ec:column>
						 
					</ec:row>
			</ec:table>