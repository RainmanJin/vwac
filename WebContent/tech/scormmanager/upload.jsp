<%@page contentType="text/html; charset=UTF-8" %>
<%@include file="/common/context.jsp" %>
<script>
	$(document).ready(function(){
			initJsonForm("#myForm",function(d){
				location.href = "view?id=" + d.data;
			},function(d){
				alert(d.data)
			});
		});
</script>

<form id="myForm" action="upload" enctype="multipart/form-data" method="post">
<ul class="form">
<li>
	<label style="width: 200px;">${i18n.scormpkg.filed.selectFile}</label>
	<input type="file" name="file" v="notEmpty" />
</li>
</ul>
</form>