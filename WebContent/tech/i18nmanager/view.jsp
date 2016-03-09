<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<script type="text/javascript">
initJsonForm("#i18nForm",function(d)
{
	location.reload();
},function(d){
	alert(d.data)
});
</script>
			
			<form id="i18nForm" action="save" method="post" class="form">
					<input type="hidden" name="id" value="${id}" />
					<ul>
						<li style="height: 60px;">
							<label>English:</label>
							<textarea rows="4" cols="60" name="en">${dto.en}</textarea>
						</li>
						<li style="height: 50px;">
							<label>简体中文:</label>
							<textarea rows="4" cols="60" name="zh_CN">${dto.zh_CN}</textarea>
						</li>
					</ul>
			</form>
