<%@include file="/common/context.jsp" %>
<script type="text/javascript">
	initJsonForm("#myForm1",function(d){
		location.reload();
	},function(d){
		alert(d.data)
	});
</script>
<form id="myForm1" action="delete" method="post" class="form">
				<input type="hidden" name="id" value="${manager.id}" />
					<ul>
						<li>
							<label>${i18n.resourse.name}</label>
							<input type="text" name="title" value="${manager.title}" readonly="readonly" class="gray" v="notEmpty" maxlength="100" />
							<span class="msg">${i18n.resourse.name}${i18n.valid.notEmpty}</span>
						</li>
					</ul>
			</form>
