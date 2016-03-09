<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<script>
initJsonForm("#uploadForm",function(d){
	alert(d.data);
	location.href="index";
},function(d){
	alert(d.data)
});
</script>
			<form id="uploadForm" action="batchTemplateSave" method="post" enctype="multipart/form-data">
				<input id="myfilename" type="file" name="filename"/>
				<input id="aa" type="hidden" value="0"/>
			</form>
			