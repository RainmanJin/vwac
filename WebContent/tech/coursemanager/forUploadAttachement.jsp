<script>
	$(document).ready(function(){
			initJsonForm("#myForm",function(d){
				location.reload();
			},function(d){
				alert(d.data)
			});
		});
</script>

<form id="myForm" action="uploadAttachement" enctype="multipart/form-data" method="post">
<input type="hidden" name="id" value="${param.id}" />
<ul class="form">
<li>
	<label style="width: 225px;">${i18n.course.text.attachementName}</label>
	<input type="text" name="name" v="notEmpty" maxlength="50" />
	<span class="msg">${i18n.valid.notEmpty}</span>
</li>
<li>
	<label style="width: 225px;">${i18n.course.text.selAttachement}(10M)</label>
	<input type="file" name="file" v="notEmpty" />
</li>
</ul>
</form>