<%@include file="/common/context.jsp" %>
<script>
$(document).ready(function(){
	initHtmlForm("#ec_ajax", $("#jmodal-container-content"),function(d){});	
});

</script>
			<ec:table tableId="ec_ajax" action="listTestting?courseId=${param.courseId}" method="post" items="techcoursetestingdb" var="var" view="ecan" width="99%"
					imagePath="${coreImgPath}/table/*.gif" showExports="false" onInvokeAction="$('#ec_ajax').submit();" autoIncludeParameters="false">
					<ec:row>
						<ec:column  filterable="false"  headerCell="selectAll"  alias="chktestingid"   width="2%">
							<input name="chktestingid" id="chktestingid" value="${var.id}"  type="checkbox"/>
						</ec:column>
						<ec:column title="${i18n.techdb.filed.title}" property="title" width="30%"></ec:column>
						<ec:column title="${i18n.techdb.filed.courseName}" property="trainCourseId" width="20%" filterOptions="courseLists" filterCell="droplist">
							<c:if test="${!(empty var.trainCourseId)}">
								<ecan:viewDto property="name" dtoName="TechTrainCourse" id="${var.trainCourseId}"/>
								<c:if test="${!(empty var.moduleId)}">/<ecan:viewDto property="name" dtoName="TechTrainCourseItem" id="${var.moduleId}"/></c:if>
							</c:if>
						</ec:column>
					</ec:row>
			</ec:table>