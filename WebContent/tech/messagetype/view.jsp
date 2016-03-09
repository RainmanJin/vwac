<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@include file="/common/head.jsp" %>
	
	<style>
		.form li label{
			width: 100px;
		}
	</style>
	<script type="text/javascript">
	$(document).ready(function(){
		initJsonForm("#mtForm",function(d){
			alert("${i18n.commit.success}");
			location.href="index";
		},function(d){
			alert(d.data);
		});
	});
	
	</script>
</head>
<body>
<%@include file="/common/header.jsp" %>

<div class="centerbody centerbox">
	<div id="contentwrapper">
		<div id="content_right">
				<div class="tt">${i18n.mdttqa.title}</div>
				<form id="mtForm" action="save" method="post">
				<input type="hidden" name="id" value="${dto.id}" />
				<ul class="form">
					   <li>
							<label>${i18n.appname.typename}</label>
							<input type="text" id="typeName" name="typeName" value="${dto.typeName}" v="notEmpty" maxlength="450" style="width: 450px" />
							<span class="msg">${i18n.valid.notEmpty}</span>
						</li>
						<li>
							<label>${i18n.appname.typecode}</label>
							<input type="text" id="typeCode" name="typeCode" value="${dto.typeCode}" v="notEmpty" maxlength="450" style="width: 450px" />
							<span class="msg">${i18n.valid.notEmpty}</span>
						</li>
						<li>
							<label>${i18n.appname.typedflag}</label>
							<d:selectDomain  domain="TYPE_DELETE_FLAG" name="status"  value="${dto.status}"/>
							<span class="msg">${i18n.valid.notEmpty}</span>
						</li>
				</ul>
				</form>
			
			<div class="btns" style="padding-left: 160px;">
				<span class="btn">
					<a href="javascript:void(0)" onclick="$('#mtForm').submit()">${i18n.button.save}</a>
				</span>
				
				<span class="btn">
					<a href="index">${i18n.button.back}</a>
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