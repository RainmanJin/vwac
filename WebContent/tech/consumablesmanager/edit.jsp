<%@include file="/common/context.jsp" %>
<script type="text/javascript">
	initJsonForm("#myForm1",function(d){
		location.href="index";
	},function(d){
		alert(d.data)
	});
</script>
<form id="myForm1" action="modifynum" method="post" class="form">
				<input type="hidden" name="id" value="${resourseManager.id}" />
					<ul>
						<li>
							<label>${i18n.consumables.name}</label>
							<input type="text" name="name" value="${resourseManager.conName}" readonly="readonly" v="notEmpty" maxlength="100" />
							<span class="msg">${i18n.consumables.name}${i18n.valid.notEmpty}</span>
						</li>
						<li>
							<label>${i18n.consumables.totlesum}</label>
							<input type="text" class="gray" name="totlesum" value="${resourseManager.intConSum}" readonly="readonly" v="notEmpty num" maxlength="4" />
							<span class="msg">${i18n.consumables.num}${i18n.valid.notEmpty}</span>
						</li>
						<li>
							<label>${i18n.consumables.modifytype}</label>
							<select name="modifytype">
								<option value="0">${i18n.consumables.add}</option>
								<option value="1">${i18n.consumables.reduction}</option>
							</select>
						</li>
						<li>
							<label>${i18n.consumables.modify}</label>
							<input type="text" name="num" value="" v="notEmpty num"  maxlength="4" />
							<span class="msg">${i18n.consumables.modify}${i18n.valid.notEmpty}</span>
						</li>
						<li>
							<label>${i18n.consumables.remark}</label>
							<textarea rows="4" cols="4" name="conremark" v="notEmpty"></textarea>
							<span class="msg">${i18n.consumables.modifyremark}${i18n.valid.notEmpty}</span>
						</li>
					</ul>
			</form>
