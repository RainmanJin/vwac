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
							<label>${i18n.consumables.name}</label>
							<input type="text" name="name" value="${resourseManager.conName}" readonly="readonly"  class="gray" v="notEmpty" maxlength="100" />
							<span class="msg">${i18n.consumables.name}${i18n.valid.notEmpty}</span>
						</li>
						<li>
							<label>${i18n.consumables.num}</label>
							<input type="text" name="num" class="gray" value="${resourseManager.intConSum}" v="notEmpty" readonly="readonly" maxlength="4" />
							<span class="msg">${i18n.consumables.num}${i18n.valid.notEmpty}</span>
						</li>
						
						<li>
							<label>${i18n.resourse.deleteremark}</label>
							<textarea rows="4" cols="4" name="remark" v="notEmpty"></textarea>
							<span class="msg">${i18n.resourse.deleteremark}${i18n.valid.notEmpty}</span>
						</li>
					</ul>
			</form>
