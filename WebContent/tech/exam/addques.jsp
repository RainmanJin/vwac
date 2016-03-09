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
	initJsonForm("#quForm",function(d){
		alert(d.data);
		location.href="editques?id=${dto.id}";
	},function(d){
		alert(d.data);
	});
});
		var idx = 0;
		function addAnswer()
		{
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
						<%-- <c:choose>
							<c:when test="${empty dto}">${i18n.techdb.titleadd}</c:when>
							<c:otherwise>${i18n.techdb.titleedit}</c:otherwise>
						</c:choose> --%>
						${i18n.exam.addquestiont}
					</div>

					<form id="quForm" action="saveques" method="post" class="form">
						 <input type="hidden" name="mainId" value="${dto.id}" /> 
						<ul style="width: 700px">
						    <li>
								<label>
									${i18n.exam.maintitle}：
								</label>
								 ${dto.title}
							</li>
							<li style="height: 100px;">
								<label>
									${i18n.exam.questiontitle}
								</label>
								<textarea style="width: 350px; height: 100px; resize: none" name="title" v="notEmpty"></textarea>
								<span class="msg">${i18n.techdb.filed.title}${i18n.valid.notEmpty}</span>
							</li>
							<%--<li>
								<label>${i18n.exam.enableflag}</label>
								<d:selectDomain  domain="EXAM_ENABLE_FLAG" name="flag" value=""/>
								<span class="msg">${i18n.valid.notEmpty}</span>
						    </li>
						    --%><li>
								<label>${i18n.exam.questiontype}</label>
								<d:selectDomain  domain="EXAM_QUESTION_TYPE" name="type" value=""/>
								<span class="msg">${i18n.valid.notEmpty}</span>
						    </li>
						    <li style="height: auto;">
								<input name="id" id="id" type="hidden" value="" />
									<label>
										${i18n.techdb.filed.answer}
									</label>
								<div id="AnsDIV">
									<div class="rowAns">
										<input type="text" name="t_0" />
										<input style="width: 40px;" type="checkbox" name="c_0" />
											${i18n.techdb.filed.isright}
									</div>
								</div>
								<span style="float: right;" class="btn"> 
										<a href="javascript:addAnswer();">${i18n.oper.add}</a> 
								</span>
							</li>
						</ul>
						<div class="btns" style="padding-left: 100px;">
							<span class="btn"> 
										<a href="javascript:void(0);" onclick="$('#quForm').submit();">${i18n.button.submit}</a>
							
							</span>
							<span class="btn">
								<a href="javascript:history.back();">${i18n.button.back}</a>
							</span>
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