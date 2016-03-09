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
	initJsonForm("#importExamPage",function(d){
		location.href="index";
	},function(d){
		alert(d.data);
	});
});
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

					<form id="importExamPage" action="importExamPage" method="post" class="form" enctype="multipart/form-data">
						<ul style="width: 700px">
						<li style="height: 100px;">
							<label>
								模板文件：
							</label>
							<a href="/tech/upload/exam/examImport.xls">下载模板</a>
						</li>
						<li style="height: 100px;">
							<label>
								导入试题:
							</label>
							<input type="file" name="excelExam" id="excelExam" v="notEmpty"/>
						</li>
						</ul>
						<div class="btns" style="padding-left: 100px;">
							<span class="btn"> 
										<a href="javascript:void(0);" onclick="$('#importExamPage').submit();">${i18n.button.submit}</a>
							
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