<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@include file="/common/head.jsp" %>
	<style>
		.form input.num{
			float: none;
		}
		div.pls h2{
			font-weight: bold;
		}
		div.pls div{
			border-bottom: 1px dotted #000;
			margin-bottom: 10px;
			display: inline-block;
		}
	</style>
	<script type="text/javascript">
		$(document).ready(function(){
				initJsonForm("#jxfForm",function(d){
					alert("${i18n.commit.success}");
				},function(d){
					alert(d.data)
				});

				initJsonForm("#zykForm",function(d){
					alert("${i18n.commit.success}");
					location.reload();
				},function(d){
					alert(d.data)
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
				${i18n.testactivestandard.title1}
			</div>
			
			<div class="btns">
				<form id="jxfForm" action="save" method="post" class="form">
					<input type="hidden" name="id" value="jiaoxuefa"/>
					0------${i18n.testactive.expert}-----<input name="levelOne" type="text" value="${jxf.levelOne}" class="num" maxlength="4" v="notEmpty"/>-----${i18n.testactive.experienced}------<input name="levelTwo" type="text" value="${jxf.levelTwo}" class="num" maxlength="4" v="notEmpty"/>----${i18n.testactive.basic}
				</form>
			</div>
			<div class="btns">
				<span class="btn">
					<a href="javascript:void(0)" onclick="$('#jxfForm').submit();">${i18n.button.submit}</a>
				</span>
			</div>
			
			
			
			<div class="tt">
				${i18n.testactivestandard.title2}
			</div>
			<div class="btns">
				<form id="zykForm" action="save" method="post" class="form">
					<c:forEach items="${DOMAIN_PROTYPE}" var="var">
						<input type="hidden" name="zyk" value="${var.value}"/>
						<div class="pls">
							<h2>${var.i18nLabel}</h2>
							<div>
								0------${i18n.testactive.basic}-----
								<input name="levelOne_${var.value}" type="text" value="${zyk[var.value].intOne}" class="num" maxlength="2"/>
								-----${i18n.testactive.experienced}------
								<input name="levelTwo_${var.value}" type="text" value="${zyk[var.value].intTwo}" class="num" maxlength="2"/>
								----${i18n.testactive.expert}----100
							</div>
						</div>
					</c:forEach>
				</form>
			</div>
			<div class="btns">
				<span class="btn">
					<a href="javascript:void(0)" onclick="$('#zykForm').submit();">${i18n.button.submit}</a>
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