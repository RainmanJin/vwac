<%@page pageEncoding="UTF-8"%>
<div id="content_left">
		<div class="sidebar">
			<ul class="listMenu">
				<c:forEach items="${current_menu.root.submenu}" var="_mu">
					<c:choose>
						<c:when test="${_mu.subCount==0}">
							<li <c:if test="${current_menu.levelCode eq _mu.levelCode}">class="active"</c:if>>
								<a href="javascript:;" onclick="gourl('${_mu.outurl}','${ctxPath}/techc/${_mu.uri}')">${_mu.appName}</a>
							</li>
						</c:when>
						<c:otherwise>
							<li class="subMenu">
								<div>${_mu.appName}</div>
								<ul class="listMenu">
								<c:forEach items="${_mu.submenu}" var="_muu">
									<li <c:if test="${current_menu.levelCode eq _muu.levelCode}">class="active"</c:if>>
										<a href="javascript:;" onclick="gourl('${_mu.outurl}','${ctxPath}/techc/${_muu.uri}')">${_muu.appName}</a>
									</li>
								</c:forEach>
								</ul>
							</li>
						</c:otherwise>
					</c:choose>
					
				</c:forEach>
			</ul>
		</div>
</div>