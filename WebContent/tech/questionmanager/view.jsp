<%@include file="/common/context.jsp" %>
<script type="text/javascript">
	initJsonForm("#myForm1",function(d){
		location.href="index";
	},function(d){
		alert(d.data)
	});
</script>
<form id="myForm1" action="add" method="post" class="form">
				<input type="hidden" name="id" value="${manager.id}" />
					<c:choose>
						<c:when test="${empty manager}">
							<ul>
								<li>
									<label>${i18n.question.name}</label>
									<input type="text" name="questionname" value="${manager.questionName}" v="notEmpty" maxlength="100" />
									<span class="msg">${i18n.question.name}${i18n.valid.notEmpty}</span>
								</li>
							</ul>
						</c:when>
						<c:otherwise>
							<ul>
								<li>
									<label>${i18n.question.name}</label>
									<input type="text" name="questionname" value="${manager.questionName}" v="notEmpty" maxlength="100" />
									<span class="msg">${i18n.question.name}${i18n.valid.notEmpty}</span>
								</li>
								<li>
									<label>${i18n.techdb.filed.createname}</label>
									<input type="text" name="questionuser" value="${manager.questionUser}" v="notEmpty" readonly="readonly" />
									<span class="msg">${i18n.techdb.filed.createname}${i18n.valid.notEmpty}</span>
								</li>
								<li>
									<label>${i18n.techdb.filed.createtime}</label>
									
									<input type="text" name="questiondate" value="<ecan:dateFormart value="${manager.questionDate}" formart="yyyy-MM-dd HH:ss:mm"/>" v="notEmpty" readonly="readonly" />
									<span class="msg">${i18n.techdb.filed.createtime}${i18n.valid.notEmpty}</span>
								</li>
							</ul>
						</c:otherwise>
					</c:choose>
			</form>
