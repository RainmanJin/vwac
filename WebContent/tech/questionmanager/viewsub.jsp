<%@include file="/common/context.jsp" %>
<script type="text/javascript">
	initJsonForm("#subform11",function(d)
			{
				location.reload();
			},function(d){
				alert(d.data)
			});
</script>
<form id="subform11" action="addsub" method="post" class="form">
				<input type="hidden" name="id" value="${subitems.id}" />
				<input type="hidden" name="questionId" value="${questionid}" />
					<c:choose>
						<c:when test="${empty subitems}">
							<ul>
								<li>
									<label>${i18n.question.topictitle}</label>
									<input type="text" v="notEmpty" name="subitmesName" value="${subitems.subitmesName}" maxlength="300"/>
									<span class="msg">${i18n.question.topictitle}${i18n.valid.notEmpty}</span>
								</li>
								<li>
									<label>${i18n.question.topic.type}</label>
									<d:selectDomain domain="QUESTIONASSTYPE" name="subitmeType" value="${subitems.subitmeType}" />
								</li>
								<li>
									<label>${i18n.domain.filed.idx}</label>
									<input type="text" name="subitmesIdx" value="${subitems.subitmesIdx}" v="notEmpty num" maxlength="100" />
									<span class="msg">${i18n.domain.filed.idx}${i18n.valid.notEmpty}</span>
								</li>
							</ul>
						</c:when>
						<c:otherwise>
							<ul>
								<li>
									<label>${i18n.question.topictitle}</label>
									<input type="text" v="notEmpty" name="subitmesName" value="${subitems.subitmesName}" maxlength="300"/>
									<span class="msg">${i18n.question.topictitle}${i18n.valid.notEmpty}</span>
								</li>
								<li>
									<label>${i18n.question.topic.type}</label>
									<d:selectDomain domain="QUESTIONASSTYPE" name="subitmeType" value="${subitems.subitmeType}" />
								</li>
								<li>
									<label>${i18n.domain.filed.idx}</label>
									<input type="text" name="subitmesIdx" value="${subitems.subitmesIdx}"  v="notEmpty num"/>
									<span class="msg">${i18n.domain.filed.idx}${i18n.valid.notEmpty}</span>
								</li>
							</ul>
						</c:otherwise>
					</c:choose>
			</form>
