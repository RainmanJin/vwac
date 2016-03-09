<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@include file="/common/head.jsp"%>
		
		<link rel="stylesheet" href="${ctxPath}/js/kindeditor-4.1.10/themes/default/default.css" />
		<link rel="stylesheet" href="${ctxPath}/js/kindeditor-4.1.10/plugins/code/prettify.css" />
		
		<script type="text/javascript" charset="utf-8" src="${ctxPath}/js/kindeditor-4.1.10/kindeditor.js"></script>
	    <script type="text/javascript" charset="utf-8" src="${ctxPath}/js/kindeditor-4.1.10/lang/zh_CN.js"></script>
	    <script type="text/javascript" charset="utf-8" src="${ctxPath}/js/kindeditor-4.1.10/plugins/code/prettify.js"> </script>
		
		
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
.lineSeperater{
			border-top: 1px dotted #dfdfdf;
			padding: 20px 0px;
		}
		.form li label{
			width: 140px;
		}
		
		.rowTitle{
			border: 1px solid #dfdfdf;background: #f0f0f0;color: #2274AC; font-size: 14px; font-weight: bold;
			height: 25px; line-height: 25px;
		}
		.eXtremeTable .tableRegion{
			margin: 0px;
		}
</style>
<script type="text/javascript">
$(document).ready(function(){
	initJsonForm("#saveContentForm",function(d){
		location.href="index";
	},function(d){
		alert(d.data);
	});
});
var editor;

KindEditor.ready(function(K) {
	editor = K.create('textarea[name="content"]', {
		cssPath : '../../js/kindeditor-4.1.10/plugins/code/prettify.css',
		//uploadJson : '../../js/kindeditor-4.1.10/jsp/upload_json.jsp',
		items : ['source', '|', 'undo', 'redo', '|',
		         'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
		         'justifyfull', 'indent', 'outdent',
		         'clearhtml', 'quickformat', '|', 'fullscreen',
		         'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
		         'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image',
		         'media',  'table', 'hr', 'pagebreak','link', 'unlink','menu','template','body','preview'],
		//fileManagerJson : '../../js/kindeditor-4.1.10/jsp/file_manager_json.jsp',
		filterMode : false,
		newlineTag : "br",
		allowFileManager : true
	});
});

		var idx = 0;
		function addAnswer()
		{
			idx++;
			var row = " <div class=\"rowAns\"><input type=\"text\" name=\"t_"+idx+"\" /><input style=\"width:40px;\" name=\"c_"+idx+"\" type=\"checkbox\"/>${i18n.techdb.filed.isright}</div>";
			$('#AnsDIV').append(row);
		} 

		function showTextarea(){
			editor.sync();
			var html = $("#editor").val();
			//alert(html);
			$('#saveContentForm').submit();

		}
		
	</script>
	</head>
	<body>
		<%@include file="/common/header.jsp"%>
		<div class="centerbody centerbox">
			<div id="contentwrapper">
				<div id="content_right">
					<div class="tt">
						${i18n.sellingpoint.add}
					</div>

					<form id="saveContentForm" action="savemain" method="post" class="form">
						 <input type="hidden" name="id" value="${dto.id}" /> 
						 <%--<textarea id="editor" name="content" cols="100" rows="8" style="width:700px;height:400px;"></textarea>
							--%><ul class="form">
								<li>
									<label>${i18n.selling.pointtitle}</label>
									<input type="text" id="title" name="title" value="" v="notEmpty" maxlength="450" style="width: 450px" />
									<span class="msg">${i18n.valid.notEmpty}</span>
								</li>
						
							</ul>
					 </form>
						<div class="btns" style="padding-left: 160px;">
							<span class="btn"> 
							     <a href="javascript:void(0);" onclick="$('#saveContentForm').submit();">${i18n.button.submit}</a>
							</span>
							<span class="btn">
								<a href="javascript:history.back();">${i18n.button.back}</a>
							</span>
							<%--<span class="btn"> 
										<a href="javascript:void(0);" onclick="showTextarea();">展示</a>
							
							</span>
					    --%></div>
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