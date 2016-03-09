<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<script type="text/javascript">
initJsonForm("#domainForm",function(d)
{
	location.reload();
},function(d){
	alert(d.data)
});
</script>
<form id="domainForm" action="${ctxPath}/techc/domain/save" method="post" class="form">
	<input type="hidden" name="domain" value="${param.domain}" />
	<input type="hidden" name="id" value="${dv.id}" />
		<ul>
			<li><label>${i18n.domain.filed.label}</label><input type="text" name="label" value="${dv.domainlabel}" v="notEmpty" /><span class="msg">${i18n.domain.filed.label}${i18n.valid.notEmpty}</span></li>
			<li><label>${i18n.domain.filed.value}</label><input type="text" name="value" value="${dv.domainvalue}" v="notEmpty" /><span class="msg">${i18n.domain.filed.value}${i18n.valid.notEmpty}</span></li>
			<li><label>${i18n.domain.filed.idx}</label><input type="text" name="idx" value="${dv.indexsequnce}" v="notEmpty" /><span class="msg">${i18n.domain.filed.idx}${i18n.valid.notEmpty}</span></li>
		</ul>
</form>