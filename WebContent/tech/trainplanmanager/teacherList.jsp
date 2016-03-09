<%@include file="/common/context.jsp" %>
<script>
$(document).ready(function(){
	initHtmlForm("#ec_ajax", $("#jmodal-container-content"),function(d){});	
});
</script>

			<ec:table tableId="ec_ajax" action="listOnWorkTeacher?year=${param.year}&brand=${param.brand}&week=${param.week}&courseId=${param.courseId}" method="post" items="list" var="var" view="ecan" width="99%"
					imagePath="${coreImgPath}/table/*.gif" showExports="false" onInvokeAction="$('#ec_ajax').submit();" autoIncludeParameters="false">
					<ec:row>
						<ec:column headerCell="selectAll" property="oper" alias="selIds" width="2%" filterable="false">
							<input type="checkbox" name="selIds" value="${var.id}" />
						</ec:column>
						<ec:column title="${i18n.user.filed.name}" property="name" width="10%"></ec:column>
						<ec:column title="${i18n.user.filed.company}" property="company" width="20%" filterCell="droplist" filterOptions="DOMAIN_COMPANY">
							<d:viewDomain value="${var.company}" domain="COMPANY" />
						</ec:column>
						<ec:column title="${i18n.oper.text}" property="oper" width="10%" filterable="false" sortable="false">
							<a target="_blank" href="${ctxPath}/techc/usermanager/profile?id=${var.id}">${i18n.oper.profile}</a>
						</ec:column>
					</ec:row>
			</ec:table>