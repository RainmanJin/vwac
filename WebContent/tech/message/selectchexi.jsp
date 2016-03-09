
<%@include file="/common/context.jsp"%>
<script>
	$(document).ready(function() {
		initHtmlForm("#ec_ajax", $("#jmodal-container-content"), function(d) {
		});
	});
</script>
<ec:table tableId="ec_ajax" action="listchexi" method="post" items="list" var="var" view="ecan" width="99%" imagePath="${coreImgPath}/table/*.gif" showExports="false"
	onInvokeAction="$('#ec_ajax').submit();" autoIncludeParameters="false">
	<ec:row>
		<c:if test="${!(param.type eq 'hoster')}">
			<ec:column headerCell="selectAll" property="oper" alias="selIds" width="2%" filterable="false">
				<input type="checkbox" name="selIds" value="${var.id}@${var.domainlabel}" />
			</ec:column>
		</c:if>
		<ec:column title="${i18n.domain.filed.label}" property="domainlabel" width="10%">
			<c:if test="${param.type eq 'hoster'}">
				<a href="#nogo" onclick="chgHoster('${var.id}')">${var.domainlabel}</a>
			</c:if>
		</ec:column>
	</ec:row>
</ec:table>