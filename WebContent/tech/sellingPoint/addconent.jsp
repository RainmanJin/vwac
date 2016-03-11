<%@ page contentType="text/html;charset=utf-8"%>

<%@include file="/common/context.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@include file="/common/head.jsp"%>
<link rel="stylesheet" href="${ctxPath}/js/kindeditor-4.1.10/themes/default/default.css" />
<!--  <link rel="stylesheet" href="${ctxPath}/js/kindeditor-4.1.10/plugins/code/prettify.css" />-->
<!-- 这个CSS会影响语言选择下拉列表 -->
<script type="text/javascript" charset="utf-8" src="${ctxPath}/js/kindeditor-4.1.10/kindeditor.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctxPath}/js/kindeditor-4.1.10/lang/zh_CN.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctxPath}/js/kindeditor-4.1.10/plugins/code/prettify.js">
	
</script>
<script type="text/javascript">
	$(document).ready(function() {
		initJsonForm("#spForm", function(d) {
			location.href = "conentlist";
		}, function(d) {
			alert(d.data);
		});
	});

	var editor;

	KindEditor.ready(function(K) {
		editor = K.create('textarea[name="content"]', {
			cssPath : '../../js/kindeditor-4.1.10/plugins/code/prettify.css',
			//uploadJson : '../../js/kindeditor-4.1.10/jsp/upload_json.jsp',
			items : [ 'source', '|', 'undo', 'redo', '|', 'plainpaste',
					'wordpaste', '|', 'justifyleft', 'justifycenter',
					'justifyright', 'justifyfull', 'indent', 'outdent',
					'clearhtml', 'quickformat', '|', 'fullscreen',
					'formatblock', 'fontname', 'fontsize', '|', 'forecolor',
					'hilitecolor', 'bold', 'italic', 'underline',
					'strikethrough', 'lineheight', 'removeformat', '|',
					'image', 'media', 'table', 'hr', 'pagebreak', 'link',
					'unlink', 'menu', 'template', 'body', 'preview' ],
			//fileManagerJson : '../../js/kindeditor-4.1.10/jsp/file_manager_json.jsp',
			filterMode : false,
			newlineTag : "br",
			allowFileManager : true
		});
	});

	function submitTextarea() {
		editor.sync();
		var html = $("#content").val();
		//alert(html);
		$('#spForm').submit();
	}
</script>
</head>
<body>
	<%@include file="/common/header.jsp"%>
	<div class="centerbody centerbox">
		<div id="contentwrapper">
			<div id="content_right">
				<div class="tt">${i18n.sellingpoint.edit}</div>
				<form id="spForm" action="saveconent" method="post">
					<ul class="form">
						<li><label>${i18n.selling.conent.pointtitle}</label> <input type="hidden" name="mainId" id="mainId" value="${mainId}" /> <input type="hidden" name="id" id="id" value="${dto.id }" /> <input
							type="text" id="title" name="title" value="${dto.title}" v="notEmpty" maxlength="400" style="width: 400px" /> <span class="msg">${i18n.valid.notEmpty}</span></li>
					</ul>
					<textarea id="content" name="content" cols="100" rows="8" style="width: 700px; height: 400px;">${dto.content}</textarea>
				</form>
				<div class="btns" style="padding-left: 160px;">
					<span class="btn"> <a href="javascript:void(0)" onclick="submitTextarea()">${i18n.button.save}</a>
					</span> <span class="btn"> <a href="index">${i18n.button.back}</a>
					</span>
				</div>
			</div>
		</div>
		<%@include file="/common/leftmenu.jsp"%>
		<div class="clearfix_b"></div>
	</div>
	<%@include file="/common/footer.jsp"%>
</body>
</html>