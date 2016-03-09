<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<script>
</script>
<div>
				<form id="msForm" action="save" method="post">
				<ul class="form">
						<li>
							<label>${i18n.tag.childname}</label>
							<input type="hidden" name="id" id="id" value="${dto.id }"/>
							<input type="text" id="ctagName" name="ctagName" value="${dto.ctagName}" v="notEmpty" maxlength="400" style="width: 400px" />
							<span class="msg">${i18n.valid.notEmpty}</span>
						</li>
				</ul>
				</form>
</div>