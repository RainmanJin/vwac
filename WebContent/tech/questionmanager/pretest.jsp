<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@include file="/common/head.jsp" %>
	<style type="text/css">
		.eXtremeTable .titleRow td{
			text-align: left; padding-left: 10px;
		}
	</style>
	<script type="text/javascript">
		function beginTest(testUserId)
		{
			location.href="test?testUserId=" + testUserId;
		}
	</script>
</head>
<body>

<div class="centerbody centerbox">
	<div id="contentwrapperquestion">
		<div id="content_right_question">
			<div class="tt">${i18n.question.titleTestOnline}</div>
			
			<ec:table action="" title="${i18n.question.titleTestOnline}" method="get" items="userList" var="var" view="ecan" width="99%"
					imagePath="${coreImgPath}/table/*.gif" showExports="false">
					<ec:row>
						<ec:column title="${i18n.user.filed.name}" property="userId" width="60">
							<ecan:viewDto dtoName="EcanUser" id="${var.userId}" property="name"/>
						</ec:column>
						 
						<ec:column title="${i18n.user.filed.status}" property="finish" width="10%" filterCell="droplist" filterOptions="DOMAIN_QUESTIONSTATUS">
							<d:viewDomain value="${var.finish}" domain="QUESTIONSTATUS" />
						</ec:column>
						
						<ec:column title="${i18n.oper.text}" property="oper" filterCell="oper" sortable="false" width="150">
							<c:choose>
								<c:when test="${var.finish eq '0'}">
									<a href="beginTest?id=${questionId}&userid=${var.userId}&planid=${planid}" class="oper">${i18n.question.titleTestOnline}</a>
								</c:when>
								<c:otherwise>
									<a href="viewTest?questionid=${questionId}&userid=${var.userId}&planid=${planid}" class="oper">${i18n.oper.view}</a>
								</c:otherwise>
							</c:choose>
						</ec:column>
					</ec:row>
			</ec:table>
			
			<div class="btns">
				<span class="btn">
					<a href="sitestat?planid=${planid}&questionId=${questionId}">${i18n.question.sitestat}</a>
				</span>
				<span class="btn">
					<a href="javascript:window.close();">${i18n.button.close}</a>
				</span>
			</div>
		</div>
	</div>
	
	<div class="clearfix_b"></div>
</div>
<%@include file="/common/footer.jsp" %>
</body> 
</html>