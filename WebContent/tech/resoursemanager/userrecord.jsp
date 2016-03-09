<%@include file="/common/context.jsp" %>
<script type="text/javascript">
$(document).ready(function(){
	initHtmlForm("#ec_ajax", $("#jmodal-container-content"),function(d){});	
});
</script>
<ec:table tableId="ec_ajax" action="userrecord?id=${resourseid}" method="post" items="planList" var="var" view="ecan" width="99%"
					imagePath="${coreImgPath}/table/*.gif" showExports="false" onInvokeAction="$('#ec_ajax').submit();" autoIncludeParameters="false">
					<ec:row>
						<ec:column title="${i18n.trainplan.filed.trainCourse}" property="trainId" width="5%">
							<ecan:viewDto dtoName="TechTrainCourse" id="${var.trainId}" property="name"/>
						</ec:column>
						<ec:column title="${i18n.trainplan.filed.yearValue}" property="yearValue" width="6%"></ec:column>
						<ec:column title="${i18n.trainplan.filed.noweek}" property="planWeek" width="5%"></ec:column>
						<ec:column title="${i18n.resourse.name}" property="resName" width="8%"></ec:column>
						<ec:column title="${i18n.resourse.num}" property="resSum" width="15%">
						</ec:column>
					</ec:row>
			</ec:table>