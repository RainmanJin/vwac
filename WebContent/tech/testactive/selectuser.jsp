<%@include file="/common/context.jsp" %>
<script>
$(document).ready(function(){
	initHtmlForm("#ec_ajax", $("#jmodal-container-content"),function(d){});	
});
</script>
			<ec:table tableId="ec_ajax" action="listUsers?id=${param.id}&type=${param.type}&courseinstanceid=${param.courseinstanceid}" method="post" items="list" var="var" view="ecan" width="99%"
					imagePath="${coreImgPath}/table/*.gif" showExports="false" onInvokeAction="$('#ec_ajax').submit();" autoIncludeParameters="false">
					<ec:row>
						<c:if test="${!(param.type eq 'hoster')}">
							<ec:column headerCell="selectAll" property="oper" alias="selIds" width="2%" filterable="false">
								<input type="checkbox" name="selIds" value="${var.id}" />
							</ec:column>
						</c:if>
						<ec:column title="${i18n.user.filed.name}" property="name" width="10%">
						<c:if test="${param.type eq 'hoster'}">
							<a href="#nogo" onclick="chgHoster('${var.id}')">${var.name}</a>
						</c:if>
						</ec:column>
						<ec:column title="${i18n.user.filed.company}" property="company" width="20%" filterCell="droplist" filterOptions="DOMAIN_COMPANY">
							<d:viewDomain value="${var.company}" domain="COMPANY" />
						</ec:column>
						
						<%--  DELETE PROTYPE
						<ec:column title="${i18n.user.filed.proType}" property="proType" width="10%" filterCell="droplist" filterOptions="DOMAIN_PROTYPE">
							<d:viewDomain value="${var.proType}" domain="PROTYPE" />
						</ec:column>
						 --%>
						<ec:column title="${i18n.oper.text}" property="oper" width="10%">
							<c:if test="${param.type eq 'hoster'}">
									<a href="#nogo" onclick="chgHoster('${var.id}')">${i18n.oper.select}</a>
							</c:if>
							<a target="_blank" href="${ctxPath}/techc/usermanager/profile?id=${var.id}">${i18n.oper.profile}</a>
						</ec:column>
					</ec:row>
			</ec:table>