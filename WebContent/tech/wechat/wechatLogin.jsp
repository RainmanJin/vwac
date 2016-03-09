<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@include file="/common/head.jsp" %>
	<style>
		.loginRight{
			position: relative; top: 22px;
			min-height: 400px;width:580px; margin: 0 auto;
		}
		.loginRight li{
			height: 50px;margin-bottom: 40px;
		}
		.loginRight li label{
			width: 200px; display: inline-block; font-size: 36px; color: #8994A0; text-align: right; padding-right: 10px;
		}
		.loginRight li input{
			height: 50px;font-size: 22px;width: 240px;border: 1px solid #bbb;border-radius: 8px 8px 8px 8px;
		}
		.pic{  
		min-width:461px;
		min-height:442px;
		margin:0 auto;
			background:url(${imgPath}/welogin_banner.jpg);  
			filter:"progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale')";  
			-moz-background-size:100% 100%;  
			    background-size:100% 100%;  
		} 
		.butn{
			margin-left:200px;
			height: 60px;width: 260px;
			background-color:#0080ff;
			border-radius: 8px 8px 8px 8px;
			text-align:center;
		}
		.butn a{
			font-size: 40px;
			color:white;
			line-height:60px;
		}
	</style>
	<script  type="text/javascript">
	function checkb(){
		var is = $("#autoLogin");
		if(is.attr("checked")){
			is.attr("checked","")
		}else{
			is.attr("checked","checked")
		}
	}
	</script>
</head>
<body>

<div id="top" style="margin: 0 auto;">
		<div>
			<div style="margin-top:26px;float: left; margin-left: 10px; font-size: 48px; font-weight: bold;">
				<span style="font-size: 48px; font-weight: bold; font-family:'黑体'">大众汽车学院</span>
			</div>
			<div style="width:101px; float: right; margin-right: 10px;"><img src="${imgPath}/welogo.png" alt="logo" /></div>
		</div>
</div>
<div class="pic"></div>
<div style="height: 321px; margin: 0 auto; margin-top: 20px;">
	
	<form id="loginForm" action="/techc/client/wechatlogin" method="post" class="form" onsubmit="return validFrom('#loginForm')">
	<div class="loginRight">
		<ul> 
			<c:if test="${not empty msg}">
			<li class="error"><span class="msg">${msg}</span></li>
			</c:if>
			<li><label>${i18n.filed.login.username}:  </label><input id="u" type="text" name="username" value="${param.username}" v="notEmpty" /><span class="msg">${i18n.filed.login.username}${i18n.valid.notEmpty}</span></li>
			<li><label>${i18n.filed.login.password}:  </label><input id="p" type="password" name="password" value="${param.password}" v="notEmpty" /><span class="msg">${i18n.filed.login.password2}${i18n.valid.notEmpty}</span></li>
			<!-- <li>
			<div style="width: 320px;height:50px;margin: 0 0 0 150px;"><input type="checkbox" checked="checked" id="autoLogin" name="autoLogin" value=""style="width:50px;font-size: 36px;"/>
			<a id="checkb" onclick="checkb()" style="width: 260px; height:50px;font-size: 36px; color: #8994A0; text-align: left; padding-left: 10px;">七天内自动登录</a>
			</div></li> -->
			<li>
				<span class="butn">
					<a href="#nogo" onclick="$('#loginForm').submit();">${i18n.button.login}</a>
				</span>
				<!-- fix enter submit -->
				<input type="submit" style="width: 0px; height: 0px; border:0px; position: absolute; overflow: hidden;" value="" />
			</li>
		</ul>
	</div>
	</form>
</div>
</body> 
</html>