<%@page contentType="text/html; charset=UTF-8" %>
<%@include file="/common/context.jsp" %>
<style>
.form li input{
	width: 150px;
}
</style>
<script type="text/javascript">
$(document).ready(function(){

	initJsonForm("#myForm",function(d){
		location.reload();
	},function(d){
		alert(d.data)
	});

	loadCourse($("#proType").val());
});

function loadCourse(proType)
{
	$("#courseDiv").load("${ctxPath}/techc/commonresource/listTrainCourseByProTypeAndBrand?brand=${param.brand}&name=traincourseid&proType=" + proType);
}

</script>
<form id="myForm" action="intelligencePlan" method="post" class="form">
	<input type="hidden" name="year" value="${param.year}" />
	<input type="hidden" name="brand" value="${param.brand}" />
		<ul>
			<li>
				<label>${i18n.trainplan.filed.proType}</label>
				<d:selectDomain domain="PROTYPE" name="proType" uid="proType" onchange="loadCourse(this.value)"/>
			</li>
			<li>
				<label>${i18n.trainplan.filed.trainCourse}</label>
				<div id="courseDiv"><select></select></div>
			</li>			
			<li>
				<label>${i18n.trainplan.filed.count}</label>
				<input type="text" name="count" v="notEmpty num" maxlength="2" />
				<span class="msg">${i18n.trainplan.filed.count}${i18n.valid.notEmpty}</span>
			</li>
			<li>
				<label>${i18n.trainplan.filed.fromWeek}</label>
				<input type="text" name="beginweek" v="notEmpty num" maxlength="2" />
				<span class="msg">${i18n.trainplan.filed.fromWeek}${i18n.valid.notEmpty}</span>
			</li>
			<li>
				<label>${i18n.trainplan.filed.endWeek}</label>
				<input type="text" name="endweek" v="notEmpty num" maxlength="2" />
				<span class="msg">${i18n.trainplan.filed.endWeek}${i18n.valid.notEmpty}</span>
			</li>
		</ul>
</form>