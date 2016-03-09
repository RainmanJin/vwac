<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>

<div id="loading">
	<div class="text">
		正在加载,请稍候... ...
	</div>
</div>
<div id="top">
	<div id="logo"></div>
	<div id="userinfo">
		<b>小胖</b>你好<br />
		<label>签名: 我好胖哦,我好胖哦,我好胖哦... ...</label>
	</div>
	<div id="appbar"><!-- do nonthing --></div>
</div>

<div id="menu">
	<ul>
		<c:forEach items="${MENU_LIST}" var="_mu">
			<li <c:if test="${current_menu.appCode eq _mu.appCode && current_menu.funcCode eq _mu.funcCode}">class="selected"</c:if>>
				<a href="${ctxPath}/techc/${_mu.appCode}/${_mu.funcCode}">${_mu.appName}</a>
			</li>
		</c:forEach>
	</ul>
</div>

<c:if test="${not empty subMenu}">
<!-- tech项目暂不使用二级菜单 -->
<div id="sub_menu">
	<ul>
		<li class="selected">
			<a href="#nogo">个人首页</a>
		</li>
		<li>
			<a href="#nogo">项目任务</a>
		</li>
	</ul>
</div>
</c:if>
<div id="menu_shadow"></div>