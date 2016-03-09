<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<script type="text/javascript">
initJsonForm("#holidayForm",function(d){
	location.reload();
},function(d){
		alert("${i18n.appname.userschedulingEndDate}");
});
</script>
<form id="holidayForm" action="save" method="post" class="form">
	<input type="hidden" name="userId" value="${param.userid}" />
	<input type="hidden" name="id" value="${dto.id}" />
	
		<ul>
			<li>
				<label>${i18n.holiday.filed.remark}</label>
				<input type="text" name="remark" value="${dto.remark}" v="notEmpty" />
				<span class="msg">${i18n.holiday.filed.remark}${i18n.valid.notEmpty}</span>
			</li>
			<li>
				<label>${i18n.holiday.filed.beginDate}</label>
				<ecan:dateInupt name="beginDay" value="${dto.beginDay}" params="v=notEmpty"/>
				<span class="msg">${i18n.holiday.filed.beginDate}${i18n.valid.notEmpty}</span>
			</li>
			<li>
				<label>${i18n.holiday.filed.endDate}</label>
				<ecan:dateInupt name="endDay" value="${dto.endDay}"  params="v=notEmpty"/>
				<span class="msg">${i18n.holiday.filed.endDate}${i18n.valid.notEmpty}</span>
			</li>
		</ul>
</form>