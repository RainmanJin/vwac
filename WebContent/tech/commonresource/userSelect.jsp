<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/context.jsp" %>

<select id="${selectid}" name="${selectname}">
	<c:forEach items="${list }" var="var">
		<option <c:if test="${param.value eq var.id}">selected="selected"</c:if> value="${var.id}"> ${var.name} </option>
	</c:forEach>
</select>