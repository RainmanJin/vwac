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
#AnsDIV{
	display: inline-block;
}
</style>
		<script type="text/javascript">
	$(document).ready(function(){
		initJsonForm("#dbForm",function(d){
		alert(d.data);
			location.href="editques?id=${dto.mainId}";
		},function(d){
			alert(d.data);
		});

	});

		var idx = 5;
		function addAnswer()
		{
			idx++;
			var row = " <div class=\"rowAns\"><input type=\"text\" name=\"t_"+idx+"\" /><input style=\"width:40px;\" name=\"c_"+idx+"\" type=\"checkbox\"/>${i18n.techdb.filed.isright}</div>";
			$('#AnsDIV').append(row);
			//document.getElementById("AnsDIV").innerHTML = document.getElementById("AnsDIV").innerHTML + row;
		}
		function submitQuesForm(){
			var type =  $("select[name=type]").val();
			//var count = $("input[type=checkbox][checked]").length;
			var count = $("input[type='checkbox']:checked").length;
			if(type == "S" && count>1 ){
				alert("单选题只能有一个答案");
			}else{
				$('#dbForm').submit();
			}
			
		}
	</script>
	</head>
	<body>
		<%@include file="/common/header.jsp"%>
		<div class="centerbody centerbox">
			<div id="contentwrapper">
				<div id="content_right">
					<div class="tt">
						<%-- <c:choose>
							<c:when test="${empty dto}">${i18n.techdb.titleadd}</c:when>
							<c:otherwise>${i18n.techdb.titleedit}</c:otherwise>
						</c:choose> --%>
						${i18n.button.editquestion}
					</div>

					<form id="dbForm" action="saveques" method="post" class="form">
						<input type="hidden" name="id" value="${dto.id}" />
						<input type="hidden" name="mainId" value="${dto.mainId}" />
						<ul style="width: 700px">
							<li style="height: 100px;">
								<label>
									${i18n.exam.questiontitle}
								</label>
								<textarea style="width: 350px; height: 100px; resize: none" name="title" v="notEmpty">${dto.title}</textarea>
								<span class="msg">${i18n.techdb.filed.title}${i18n.valid.notEmpty}</span>
							</li>
							<%--<li>
								<label>${i18n.exam.enableflag}</label>
								<d:selectDomain  domain="EXAM_ENABLE_FLAG" name="flag" value="${dto.flag}"/>
								<span class="msg">${i18n.valid.notEmpty}</span>
						    </li>
						    --%><li>
						    <tags></tags>
								<label>${i18n.exam.questiontype}</label>
								<d:selectDomain domain="EXAM_QUESTION_TYPE" name="type" value="${dto.type}"/>
								<span class="msg">${i18n.valid.notEmpty}</span>
						    </li>
							<li style="height: auto;">
								<%-- <input name="tesingid" id="tesingid" type="hidden" value="${techanswer.testingid}" />
								<input name="techanswerid" id="techanswerid" type="hidden" value="${techanswer.id}" /> --%>
									<label>
										${i18n.techdb.filed.answer}
									</label>
								<div id="AnsDIV">
									<c:choose>
										<c:when test="${not empty dto.tea}">
											<c:forEach items="${dto.tea}" var="tea" varStatus="ids">
											<div class="rowAns">
												<input type="text" name="t_${ids.index}" value="${tea.title}" />
												<input style="width: 40px;" type="checkbox" name="c_${ids.index}" <c:if test="${tea.isRight eq 'Y'}">checked="checked"</c:if>/>
												${i18n.techdb.filed.isright}
											</div>
											</c:forEach>
										</c:when>
									</c:choose>
								</div>
								<span style="float: right;" class="btn"> 
									<a href="javascript:addAnswer();">${i18n.oper.add}</a>
								</span>
								
							</li>
						</ul>
						<div class="btns" style="padding-left: 100px;">
						<span class="btn"> 
									<a href="javascript:void(0);" onclick="submitQuesForm();">${i18n.button.submit}</a>
						</span>
						<span class="btn">
							<a href="javascript:history.back();">${i18n.button.back}</a>
						</span>
								<%-- <span class="gray">${i18n.commit.edit.desc}</span> --%>
					</div>
					</form>
				</div>
			</div>
			<br />
			<br />
			<%@include file="/common/leftmenu.jsp"%>
			<div class="clearfix_b"></div>
		</div>
		<%@include file="/common/footer.jsp"%>
	</body>
</html>