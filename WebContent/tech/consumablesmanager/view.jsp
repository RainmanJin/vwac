<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@include file="/common/head.jsp" %>
	
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
		
		.rowAns a{
			text-decoration:underline;
			color: blue;
			height: 25px; line-height: 25px;
		}
		
		#AnsDIV{
			display: inline-block;
		}

	</style>
	
	<script type="text/javascript">
	initJsonForm("#myForm",function(d){
		location.href="index";
	},function(d){
		alert(d.data)
	});
	</script>
</head>
<body>
<%@include file="/common/header.jsp" %>

<div class="centerbody centerbox">
	<div id="contentwrapper">
		<div id="content_right">
			<div class="tt">
				${i18n.consumables.title}
			</div>
			<form id="myForm" action="add" method="post" class="form">
				<input type="hidden" name="id" value="${resourseManager.id}" />
					<ul>
						<li>
							<label>${i18n.consumables.name}</label>
							<input type="text" name="name" value="${resourseManager.conName}" v="notEmpty" maxlength="100" />
							<span class="msg">${i18n.consumables.name}${i18n.valid.notEmpty}</span>
						</li>
						
						<li>
							<label>${i18n.consumables.remark}</label>
							<textarea rows="4" cols="4" name="remark">${resourseManager.conRemark}</textarea>
							<span class="msg">${i18n.consumables.remark}${i18n.valid.notEmpty}</span>
						</li>
					</ul>
			</form>
				<div class="btns" style="margin-left: 160px;">
							<span class="btn">
								<a href="javascript:void(0)" onclick="$('#myForm').submit()">${i18n.button.submit}</a>
							</span>
							<span class="btn">
								<a href="javascript:history.back()">${i18n.button.back}</a>
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