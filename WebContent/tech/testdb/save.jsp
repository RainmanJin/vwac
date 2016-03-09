<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@include file="/common/head.jsp"%>
<style>
.rowAns {
	height: 32px;
	display: block;
}

.rowAns span {
	display: inline-block;
	border: 1px solid #dfdfdf;
	float: left;
	height: 30px;
	line-height: 30px;
	text-align: center;
}

.rowAns .ans1 {
	width: 300px;
}

.rowAns .ans2 {
	width: 100px;
}

#AnsDIV {
	display: inline-block;
}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		initJsonForm("#dbForm", function(d) {
			alert(d.data);
			location.href = "index";
		}, function(d) {
			alert(d.data)
		});
	});

	function cleanCourse() {
		$("#_trainCourseId").val("");
		$("#_moduleId").val("");
		$("#courseNameId").val("");
	}

	function courseAjax() {
		var brand = $('#brand').val();
		var proType = $('#proType').val();

		$.fn
				.jmodal({
					title : '${i18n.techdb.filed.courseName}',
					width : 800,
					content : function(body) {
						body.html('loading...');
						body
								.load('${ctxPath}/techc/commonresource/listTrainCourseModelByProTypeAndBrand?brand='
										+ brand + "&proType=" + proType);
					},
					buttonText : {
						cancel : '${i18n.button.cancel}'
					}
				});
	}

	function selectCourseModule(moduleid, moduleName, courseid, coursename) {
		$("#_trainCourseId").val(courseid);
		$("#_moduleId").val(moduleid);
		$("#courseNameId").val(coursename + "/" + moduleName);
		$.fn.jmodalClose();
	}
	var idx = 2;
	function addAnswer() {
		idx++;
		var row = " <div class=\"rowAns\"><input type=\"text\" name=\"t_"+idx+"\" /><input style=\"width:40px;\" name=\"c_"+idx+"\" type=\"checkbox\"/>${i18n.techdb.filed.isright}</div>";

		$('#AnsDIV').append(row);
	}
</script>
</head>
<body>
	<%@include file="/common/header.jsp"%>
	<div class="centerbody centerbox">
		<div id="contentwrapper">
			<div id="content_right">
				<div class="tt">
					<c:choose>
						<c:when test="${empty dto}">${i18n.techdb.titleadd}</c:when>
						<c:otherwise>${i18n.techdb.titleedit}</c:otherwise>
					</c:choose>
				</div>

				<form id="dbForm" action="save" method="post" class="form">
					<input type="hidden" name="id" value="${dto.id}" />
					<ul style="width: 700px">
						<li style="height: 100px;"><label> ${i18n.techdb.filed.title} </label> <textarea style="width: 350px; height: 100px; resize: none" name="title" v="notEmpty">${dto.title}</textarea> <span
							class="msg">${i18n.techdb.filed.title}${i18n.valid.notEmpty}</span></li>
						<li><label> ${i18n.techdb.filed.brand} </label> <d:selectDomain domain="BRAND" uid="brand" name="brand" value="${dto.proType}" onchange="cleanCourse();" /></li>
						<li><label> ${i18n.techdb.filed.protype} </label> <d:selectDomain domain="PROTYPE" uid="proType" name="proType" value="${dto.proType}" onchange="cleanCourse();" /></li>
						<li><label> ${i18n.techdb.filed.courseName} </label> <input type="hidden" id="_trainCourseId" name="trainCourseId" value="${dto.trainCourseId}" /> <input type="hidden" id="_moduleId"
							name="moduleId" value="${dto.moduleId}" /> <input id="courseNameId" onclick="courseAjax()" type="text" readonly="readonly" v="notEmpty" class="search"
							value="<c:if test="${!(empty dto.trainCourseId)}"><ecan:viewDto property="name" dtoName="TechTrainCourse" id="${dto.trainCourseId}"/><c:if test="${!(empty dto.moduleId)}">/<ecan:viewDto property="name" dtoName="TechTrainCourseItem" id="${dto.moduleId}"/></c:if></c:if>" />
						</li>
						<li><label> ${i18n.techdb.filed.status} </label> <d:selectDomain domain="STATUS" name="status" value="${dto.status}" /></li>
						<li style="height: auto;"><input name="tesingid" id="tesingid" type="hidden" value="${techanswer.testingid}" /> <input name="techanswerid" id="techanswerid" type="hidden"
							value="${techanswer.id}" /> <label> ${i18n.techdb.filed.answer} </label>
							<div id="AnsDIV">
								<div class="rowAns">
									<input type="text" name="t_0" /> <input style="width: 40px;" type="checkbox" name="c_0" /> ${i18n.techdb.filed.isright}
								</div>
								<div class="rowAns">
									<input type="text" name="t_1" /> <input style="width: 40px;" type="checkbox" name="c_1" /> ${i18n.techdb.filed.isright}
								</div>
								<div class="rowAns">
									<input type="text" name="t_2" /> <input style="width: 40px;" type="checkbox" name="c_2" /> ${i18n.techdb.filed.isright}
								</div>
							</div> <span style="float: right;" class="btn"> <a href="javascript:addAnswer();">${i18n.oper.add}</a>
						</span></li>
					</ul>
				</form>

				<div class="btns" style="padding-left: 100px;">
					<span class="btn"> <a href="javascript:void(0);" onclick="$('#dbForm').submit();">${i18n.button.submit}</a>
					</span> <span class="btn"> <a href="javascript:history.back();">${i18n.button.back}</a>
					</span>
				</div>
			</div>
		</div>
		<br /> <br />
		<%@include file="/common/leftmenu.jsp"%>
		<div class="clearfix_b"></div>
	</div>
	<%@include file="/common/footer.jsp"%>
</body>
</html>