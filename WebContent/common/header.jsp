<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<script>
	function gourl(type, url) {
		var token = '${token}';

		//原来使用aspx的页面都是这次需要改造的
		//注销掉，也使用spring mvc
		//2016-1-23，yufei
/* 		//aspx的页面都是放在mw中，type对应数据库中的outurl
		if (type == "mw") {
			url = url.replace('techc', "mw");
		}

		//调查管理模块使用的也是aspx
		if (url.indexOf("vwsurvey") != -1) {
			url = url.replace('techc', "mw");
		} */

		if (url.indexOf("?") == -1) {
			location.href = url + "?token=" + token;
		} else {
			location.href = url + "&token=" + token;
		}
	}
</script>
<div id="loading">
	<div class="text">Loadding ... ...</div>
</div>
<div id="bd">
	<!-- #bd will be closed at footer -->
	<!-- 顶部logo等 -->
	<div id="top">
		<div>
			<div
				style="margin-top: 46px; float: left; margin-left: 47px; font-size: 28px; font-weight: bold;">
				<img src="${imgPath}/title_${_LANG}.png" alt="${i18n.system.title}" />
			</div>
			<div
				style="width: 47px; float: right; margin-top: 35px; margin-right: 47px;">
				<img src="${ctxPath}/images/logo.jpg" alt="logo" />
			</div>
		</div>
	</div>
	<c:choose>
		<c:when test="${empty isLoginView}">
			<div id="banner"
				style="background: url('${imgPath}/banner_${current_user.role.homeUrl}.jpg');"></div>
			<div id="menu">
				<ul>
					<c:forEach items="${MENU_LIST}" var="_mu">
						<li
							<c:if test="${current_menu.root.levelCode eq _mu.levelCode}">class="active"</c:if>>
							<a href="javascript:;"
							onclick="gourl('${_mu.outurl}','${ctxPath}/techc/${_mu.uri}')">${_mu.appName}</a>
						</li>
					</c:forEach>
					<c:if test="${!(empty dmpEnable)}">
						<li><a href="${dmpURL}">${i18n.appname.dmp}</a></li>
					</c:if>
					<li class="userinfo"><span>&nbsp;</span> <span> <select
							onchange="changeLang(this.value)">
								<c:forEach items="${supportLangs}" var="var">
									<option
										<c:if test="${_LANG eq var.id}">selected="selected" </c:if>
										value="${var.id}">${var.name}</option>
								</c:forEach>
						</select>&nbsp;
					</span> <span><a href="${ctxPath}/p/logout">${i18n.logout}</a></span></li>
				</ul>
			</div>
		</c:when>
		<c:otherwise>
			<div id="banner"
				style="background: url('${imgPath}/login_banner.jpg') no-repeat center top; height:221px;">
				<div id="menu"
					style="top: 187px; max-width: 1250px; margin: 0 auto;"></div>
			</div>
		</c:otherwise>
	</c:choose>