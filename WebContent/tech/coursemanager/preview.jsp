<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@include file="/common/head.jsp" %>
	<style>
		.fullS{
			position: absolute;
			top: 0px;
			left: 0px;
		}
		.fullS .btns{
			background: #f0f0f0;
			margin: 0px;
			padding: 10px;
		}
	</style>
	<script type="text/javascript">
		$(document).ready(function(){
			$("#lmsFrame").load(function(){
				var mainheight = $(this).contents().find("body").height()+30;
				$(this).height(mainheight);
			}); 
		});
		
		function GenericAPIAdaptor(){
	        this.LMSInitialize = LMSInitializeMethod;
	        this.LMSGetValue = LMSGetValueMethod;
	        this.LMSSetValue = LMSSetValueMethod;
	        this.LMSCommit = LMSCommitMethod;
	        this.LMSFinish = LMSFinishMethod;
	        this.LMSGetLastError = LMSGetLastErrorMethod;
	        this.LMSGetErrorString = LMSGetErrorStringMethod;
	        this.LMSGetDiagnostic = LMSGetDiagnosticMethod;

	        this.Initialize = LMSInitializeMethod;
	        this.GetValue = LMSGetValueMethod;
	        this.SetValue = LMSSetValueMethod;
	        this.Commit = LMSCommitMethod;
	        this.Finish = LMSFinishMethod;
	        this.GetLastError = LMSGetLastErrorMethod;
	        this.GetErrorString = LMSGetErrorStringMethod;
	        this.GetDiagnostic = LMSGetDiagnosticMethod;
		}

		function LMSInitializeMethod(parameter){
			return "true";
		}
		function LMSFinishMethod(parameter){
			return "true";
		}
		function LMSCommitMethod(parameter){
			return "true";
		}
		function LMSGetValueMethod(element){
			return "0";
		}
		function LMSSetValueMethod(element, value){
			return "true";
		}
		function LMSGetErrorStringMethod(errorCode){
			return "No error";
		}
		function LMSGetLastErrorMethod(){
			return "0";
		}
		function LMSGetDiagnosticMethod(errorCode){
			return "No error. No errors were encountered. Successful API call.";
		}
		 
		var API = new GenericAPIAdaptor;

		var itemId = false;
		var _fullScreened = false;
		function fullScreen(obj)
		{
			if(_fullScreened)
			{
				_fullScreened = false;
				$("#studyCenterView").removeClass("fullS");
				$("#studyCenterView").css("width", "710px");
				
				$(obj).html("${i18n.button.fullScreen}");
			}else
			{
				_fullScreened = true;
				$("#studyCenterView").addClass("fullS");
				$("#studyCenterView").css("width", $(document).width() + "px");
				
				$(obj).html("${i18n.button.restore}");
			}
		}

		function clickItem(obj,id, url)
		{
			itemId = id;
			$("#studyCenterView").css("visibility", "visible");
			
			$(obj).css("color","#3675AC");
			$("#lmsFrame").attr("src",url);
		}
	</script>
</head>
<body>
<%@include file="/common/header.jsp" %>

<div class="centerbody centerbox">
	<div id="contentwrapper">
		<div id="content_right">
			<div id="studyCenterView" style="width: 710px; background: #fff; visibility: hidden;">
					<div class="btns">
						<span class="btn">
							<a href="javascript:;" onclick="fullScreen(this)">${i18n.button.fullScreen}</a>
						</span>
					</div>
					<iframe name="lmsFrame" id="lmsFrame" width="100%" height="500"></iframe>
			</div>
		</div>
	</div>
	
	<div id="content_left">
		<c:forEach items="${items}" var="var">
			<a href="javascript:;" onclick="clickItem(this,'${var.id}','${ctxPath}/tech/upload/scorm/${dto.id}/${var.lanunch}')" style="line-height:20px; border-bottom:1px solid #dfdfdf;<c:if test="${var.exts.hasStudy}">color:#3675AC;</c:if>">${var.indexName} ${var.title}</a><br />
		</c:forEach>
	</div>
	<div class="clearfix_b"></div>
</div>
<%@include file="/common/footer.jsp" %>
</body> 
</html>