<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<script type="text/javascript">
initJsonForm("#myForm",function(d){
	location.href="view?id=" + d.data;
},function(d){
	alert(d.data)
});
</script>
<form id="myForm" action="add" method="post" class="form">
		<ul>
			<li>
				<div style="color: red; text-align: center;">${i18n.testactive.text.addNotice}</div>
			</li>
			<li>
				<label>${i18n.testactive.filed.date}</label>
				<ecan:dateInupt name="creatTime" value="${dto.creatTime}" params="v=notEmpty"/>
			</li>
			<li>
				<label>${i18n.user.filed.proType}</label>
				<d:selectDomain domain="PROTYPE" name="proType" value="${dto.proType}" />	
			</li>
			<li>
				<label>${i18n.testactive.filed.group}</label>
				<input type="text" name="group" value="" />
			</li>
		</ul>
</form>