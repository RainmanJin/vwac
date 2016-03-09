<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<script>
$(document).ready(function(){
	initHtmlForm("#ec_ajax", $("#jmodal-container-content"),function(d){});	
});
</script>
			<ec:table tableId="ec_ajax" action="cfgPlan" method="post" items="list" var="var" view="ecan" width="99%"
					imagePath="${coreImgPath}/table/*.gif" showExports="false" onInvokeAction="$('#ec_ajax').submit();" autoIncludeParameters="false">
					<ec:row>
						<ec:column title="课程" property="trainCourseName" width="30%"></ec:column>
						<ec:column title="排期时间(Y/W)" property="yearWeek" width="20%"></ec:column>
						<ec:column title="备注" property="remark" width="40%">
							<div style="width: 200px; overflow: hidden; line-height: 25px; height: 25px;">${var.remark}</div>
						</ec:column>
						<ec:column title="${i18n.oper.text}" property="assgin" width="10%" sortable="false" filterable="false">
							<a href="javascript:void(0)" onclick="saveCfgPlan('${var.id}')">${i18n.oper.select}</a>
						</ec:column>
					</ec:row>
			</ec:table>