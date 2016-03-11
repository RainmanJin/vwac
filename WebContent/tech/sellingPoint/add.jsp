<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@include file="/common/head.jsp"%>
<script type="text/javascript">
	$(document).ready(function() {
		initJsonForm("#saveContentForm", function(d) {
			location.href = "index";
		}, function(d) {
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
				<div class="tt">${i18n.sellingpoint.add}</div>
				<form id="saveContentForm" action="savemain" method="post" class="form">
					<input type="hidden" name="id" value="${dto.id}" />
					<ul class="form">
						<li><label>${i18n.selling.pointtitle}</label> <input type="text" id="title" name="title" value="" v="notEmpty" maxlength="450" style="width: 450px" /> <span class="msg">${i18n.valid.notEmpty}</span>
						</li>
					</ul>
				</form>
				<div class="btns" style="padding-left: 160px;">
					<span class="btn"> <a href="javascript:void(0);" onclick="$('#saveContentForm').submit();">${i18n.button.submit}</a>
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