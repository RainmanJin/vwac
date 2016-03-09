<%@include file="/common/context.jsp" %>
<script>
$(document).ready(function(){
	initHtmlForm("#ec_ajax", $("#jmodal-container-content"),function(d){});	
});

function publish(id)
{
	ajaxGet("pub",{"scromid":id},function(r){
			if(r.success)
				location.href="view?id=" + r.data.id;
			else
				alert(r.data);
		},"json");
}
</script>
			<ec:table tableId="ec_ajax" action="listScorm" method="post" items="list" var="var" view="ecan" width="99%"
					imagePath="${coreImgPath}/table/*.gif" showExports="false" onInvokeAction="$('#ec_ajax').submit();">
					<ec:row>
						<ec:column title="${i18n.scormpkg.filed.name}" property="name" width="30%"></ec:column>
						<ec:column title="${i18n.scormpkg.filed.contentType}" property="contentType" width="10%" filterCell="droplist" filterOptions="DOMAIN_SCORMTYPE">
							<d:viewDomain domain="SCORMTYPE" value="${var.contentType}" />
						</ec:column>
						<ec:column title="${i18n.scormpkg.filed.brand}" property="brand" width="8%" filterCell="droplist" filterOptions="DOMAIN_BRAND">
							<d:viewDomain value="${var.brand}" domain="BRAND" />
						</ec:column>
						<ec:column title="${i18n.scormpkg.filed.proType}" property="proType" width="10%" filterCell="droplist" filterOptions="DOMAIN_PROTYPE">
							<d:viewDomain value="${var.proType}" domain="PROTYPE" />
						</ec:column>
						<ec:column title="${i18n.oper.text}" property="oper" width="10%" filterCell="oper" sortable="false">
							<a href="#nogo" onclick="publish('${var.id}')">${i18n.oper.publish}</a>
						</ec:column>
					</ec:row>
			</ec:table>