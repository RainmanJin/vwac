<%@ page contentType="text/html;charset=utf-8"%>

<%@include file="/common/context.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@include file="/common/head.jsp" %>
	
				<link rel="stylesheet" href="${ctxPath}/js/kindeditor-4.1.10/themes/default/default.css" />
		<link rel="stylesheet" href="${ctxPath}/js/kindeditor-4.1.10/plugins/code/prettify.css" />
		
		<script type="text/javascript" charset="utf-8" src="${ctxPath}/js/kindeditor-4.1.10/kindeditor.js"></script>
	    <script type="text/javascript" charset="utf-8" src="${ctxPath}/js/kindeditor-4.1.10/lang/zh_CN.js"></script>
	    <script type="text/javascript" charset="utf-8" src="${ctxPath}/js/kindeditor-4.1.10/plugins/code/prettify.js"> </script>
		
	
	
	<style>
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

	function submitTextarea(){
		editor.sync();
		var html = $("#editor").val();
		//alert(html);
		$('#spForm').submit();

	}
	
	$(document).ready(function(){
		initJsonForm("#spForm",function(d){
			location.href="conentlist";
		},function(d){
			alert(d.data);
		});
		$("#contentTag").focus(function(){
			$.fn.jmodal({
		        title: '${i18n.oper.select}',
		        width:460,
		        height:300,
		        content: function(body) {
		            body.html('loading...');
		            body.load("listtag?id=${dto.id}");
		        },
		        buttonText: { ok: '${i18n.button.add}', cancel: '${i18n.button.cancel}' },
		        okEvent: function(data, args) {
		        	var content = $("#content").val();
		        	var selVal = [];
		        	var rightSel = $("#selectR");
		    		rightSel.find("option").each(function(){
		    			selVal.push(this.value);
		    		});
		    		selVals = selVal.join(",");
		    		if(selVals==""){
		    			alert("没有选择任何项！");
		    		}else{
		    			ajaxPost("chgtags",
		            			{"id":"${dto.id}","tagid": selVals,"mainId":"${mainId}","content":content},
		            			function(r){
					        			if(r.success){
					        				location.reload();
					        			}
					        			else{
					        				alert(r.data);
					        			}
		        		        },"json");
		            }
		    		}
		        	
		    });
		});
		});
</script>
</head>
<body>
<%@include file="/common/header.jsp" %>

<div class="centerbody centerbox">
	<div id="contentwrapper">
		<div id="content_right">
				<div class="tt">
				${i18n.sellingpoint.edit}
				</div>
				<form id="spForm" action="saveconent" method="post">
				<ul class="form">
						<li>
							<label>${i18n.selling.conent.pointtitle}</label>
							<input type="hidden" name="id" id="id" value="${dto.id }"/>
							<input type="hidden" name="mainId" id="mainId" value="${mainId}"/>
							<input type="text" id="title" name="title" value="${dto.title}" v="notEmpty" maxlength="400" style="width: 400px" />
							<span class="msg">${i18n.valid.notEmpty}</span>
						</li>
						<li>
							<label>${i18n.sellingpoint.content}</label>
							<%--<input type="text" id="content" name="content" value="${dto.content}" v="notEmpty" maxlength="400" style="width: 400px" />
							--%><span class="msg">${i18n.valid.notEmpty}</span>
						</li><%--
						 <li><label>${i18n.sellingpoint.contentTag}</label>
							<textarea  id="contentTag" name="contentTag" v="notEmpty" style="width: 300px;">${dto.contentTag}</textarea>
							<span class="msg">${i18n.valid.notEmpty}</span>
					     </li>
					     
				--%>
				<input type="hidden"  id="contentTag" name="contentTag" value="vw" />
				</ul>
				<br/><br/><br/>
				<textarea id="editor" name="content" cols="100" rows="8" style="width:700px;height:400px;">${dto.content}</textarea>
				</form>
			<div class="btns" style="padding-left: 160px;">
				<span class="btn">
					<a href="javascript:void(0)" onclick="submitTextarea()">${i18n.button.save}</a>
				</span>
				<span class="btn">
					<a href="conentlist">${i18n.button.back}</a>
				</span>
			</div>			
		</div>
	</div>
	
	<%@include file="/common/leftmenu.jsp" %>
	<div class="clearfix_b"></div>
</div>
<%@include file="/common/footer.jsp" %>
</body> 
</html>