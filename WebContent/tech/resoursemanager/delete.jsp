<%@include file="/common/context.jsp" %>
<script type="text/javascript">
	initJsonForm("#myForm1",function(d){
		location.reload();
	},function(d){
		alert(d.data)
	});
</script>
<form id="myForm1" action="delete" method="post" class="form">
				<input type="hidden" name="id" value="${resourseManager.id}" />
					<ul>
						<li>
							<label>${i18n.resourse.name}</label>
							<input type="text" name="name" value="${resourseManager.resName}" readonly="readonly" class="gray" v="notEmpty" maxlength="100" />
							<span class="msg">${i18n.resourse.name}${i18n.valid.notEmpty}</span>
						</li>
						<li>
							<label>${i18n.resourse.num}</label>
							<input type="text" class="gray" name="num" value="${resourseManager.resSum}" v="notEmpty" maxlength="4" readonly="readonly" />
							<span class="msg">${i18n.resourse.num}${i18n.valid.notEmpty}</span>
						</li>
						
						<li>
							<label>${i18n.resourse.remark}</label>
							<textarea rows="4" cols="4" name="remark" v="notEmpty"></textarea>
							<span class="msg">${i18n.resourse.deleteremark}${i18n.valid.notEmpty}</span>
						</li>
					</ul>
			</form>
