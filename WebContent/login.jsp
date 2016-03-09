<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@include file="/common/head.jsp" %>
	<style>
		.loginRight{
			padding-left: 20px;float: left; position: relative; top: 22px;
		}
		.loginRight li{
			height: 36px;
		}
		.loginRight li label{
			width: 60px; display: inline-block; font-size: 14px; color: #8994A0; text-align: right; padding-right: 10px;
		}
		
	</style>
	<script>
		<c:if test="${empty isLoginView}">
		$(document).ready(function(){
				location.href="${ctxPath}/p/login";
			});
		</c:if>
	</script>
</head>
<body>

<!-- 错误的设计，直接写行内样式解决 -->
<%@include file="/common/header.jsp" %>
<div style="width: 915px; height: 269px; margin: 0 auto; margin-top: 20px;">
	<div style="float: left; width: 86px; height: 128px; background: url('${imgPath}/login_left.jpg') no-repeat right top; ">
		<span style="font-size: 14px; font-weight: bold; position: relative; top:22px;">${i18n.login.title}</span>
	</div>
	<!-- 原来的action有问题，原来是/p/login，找不到，使用login
	yufei-20160118
	 -->
	<form id="loginForm" action="login" method="post" class="form" onsubmit="return validFrom('#loginForm')">
	<div class="loginRight">
		<ul>
			<c:if test="${not empty msg}">
			<li class="error"><span class="msg">${msg}</span></li>
			</c:if>
			<li><label>${i18n.filed.login.username}</label><input id="u" type="text" name="username" value="${param.username}" v="notEmpty" /><span class="msg">${i18n.filed.login.username}${i18n.valid.notEmpty}</span></li>
			<li><label>${i18n.filed.login.password}</label><input id="p" type="password" name="password" value="${param.password}" v="notEmpty" /><span class="msg">${i18n.filed.login.password2}${i18n.valid.notEmpty}</span></li>
			<li>
				<label>${i18n.filed.login.lang}</label>
				<select style="font-size: 14px;" name="_LANG" onchange="changeLang(this.value)">
					<c:forEach items="${supportLangs}" var="var">
						<option value="${var.id}" <c:if test="${_LANG eq var.id}">selected="selected" </c:if>>${var.name}</option>					
					</c:forEach>
				</select>
				<span class="btn" style="margin-left: 20px;">
					<a href="#nogo" onclick="$('#loginForm').submit();">${i18n.button.login}</a>
				</span>
				<!-- fix enter submit -->
				<input type="submit" style="width: 0px; height: 0px; border:0px; position: absolute; overflow: hidden;" value="" />
			</li>
		</ul>
	</div>
	</form>
</div>
<%@include file="/common/footer.jsp" %>
</body> 
</html>