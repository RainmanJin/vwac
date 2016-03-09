<%@include file="/common/context.jsp" %>
<script type="text/javascript">
	initJsonForm("#myForm1",function(d){
		location.reload();
	},function(d){
		alert(d.data)
	});
</script>
<form id="myForm1" action="modifynum" method="post" class="form">
				<input type="hidden" name="id" value="${resourseManager.id}" />
					<ul>
						<li>
							<label>${i18n.resourse.name}</label>
							<input type="text" name="name" value="${resourseManager.resName}" readonly="readonly" class="gray" v="notEmpty" maxlength="100" />
							<span class="msg">${i18n.resourse.name}${i18n.valid.notEmpty}</span>
						</li>
						<li>
							<label>${i18n.resourse.totlesum}</label>
							<input type="text" name="totlesum" class="gray" value="${resourseManager.intResSum}" readonly="readonly" v="notEmpty num" maxlength="4" />
							<span class="msg">${i18n.resourse.num}${i18n.valid.notEmpty}</span>
						</li>
						<li>
							<label>${i18n.resourse.modify}</label>
							<input type="text" name="num" value="" v="notEmpty num" maxlength="4" />
							<span class="msg">${i18n.resourse.modify}${i18n.valid.notEmpty}</span>
						</li>
						<li>
							<label>${i18n.resourse.modifyremark}</label>
							<textarea rows="4" cols="4" name="conremark" v="notEmpty"></textarea>
							<span class="msg">${i18n.resourse.modifyremark}${i18n.valid.notEmpty}</span>
						</li>
					</ul>
			</form>
