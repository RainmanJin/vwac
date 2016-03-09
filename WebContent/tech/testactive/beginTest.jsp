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
			if(confirm('${i18n.testactive.msg.confirmLogoutAndTest}'))
			{//进入安全模式：：：注销用户所有的授权
				ajaxGet("${ctxPath}/p/ajaxLogout",{},function(d){
						location.href="test?testUserId=" + testUserId;
					},'json');
			}else
			{
				location.href="test?testUserId=" + testUserId;
			}
		}
		function confirmTest(id)
		{
			$.fn.jmodal({
	            title: '${i18n.testactive.testconfirm.title}',
	            width:600,
	            content: function(body) {
	                body.html('loading...');
	                body.load('confirmTest?testUserId=' + id);
	            },
	            buttonText: {cancel: '${i18n.button.cancel}' }
	        });
		}

		// 确认得分
		function confirmTestSubmit(id, type)
		{
			/*if(confirm("${i18n.testactive.testconfirm.text}"))
			{*/
				ajaxPost("confirmTest",{"testUserId":id,"type":type},function(d){
						location.reload();
					},'json');
			/*}*/
		}
	</script>
</head>
<body>
<%@include file="/common/header.jsp" %>

<div class="centerbody centerbox">
	<div id="contentwrapper">
		<div id="content_right">
			<div class="tt">${i18n.testactive.titleTestOnline}</div>
			
			<ec:table action="" title="${i18n.testactive.testfiled.title}" method="get" items="list" var="var" view="ecan" width="99%"
					imagePath="${coreImgPath}/table/*.gif" showExports="false">
					<ec:row>
						<ec:column title="${i18n.testactive.testfiled.name}" property="name"></ec:column>
						<ec:column title="${i18n.testactive.testfiled.pointCN}" property="pointCn" width="60"></ec:column>
						<ec:column title="${i18n.testactive.testfiled.pointDE}" property="pointDe" width="60"></ec:column>
						<ec:column title="${i18n.testactive.testfiled.point}" property="points" width="60"></ec:column>
						<ec:column title="${i18n.testactive.testfiled.status}" property="status" width="60" filterCell="droplist" filterOptions="DOMAIN_ACTIVEUSERSTATUS">
							<d:viewDomain value="${var.status}" domain="ACTIVEUSERSTATUS"/>
						</ec:column>
						<ec:column title="${i18n.oper.text}" property="oper" filterCell="oper" sortable="false" width="150">
							<c:if  test="${var.status eq 'totest'}">
								<a href="javascript:;" onclick="return beginTest('${var.id}')" class="oper">${i18n.testactive.testoper.beginTest}</a>
							</c:if>
							
							<c:if  test="${!(var.status eq 'totest')}">
							<a href="javascript:;" onclick="return confirmTest('${var.id}');" class="oper">${i18n.testactive.testoper.confirmPoint}</a>
							</c:if>
							
							<c:if test="${current_user.roleId eq 'admin'}">
							<a href="confirm?testUserId=${var.id}" class="oper">${i18n.testactive.testoper.modify}</a>
							</c:if>
							
						</ec:column>
					</ec:row>
			</ec:table>
			
			<div class="btns">
				<span class="btn">
					<a href="index">${i18n.button.back}</a>
				</span>
			</div>
		</div>
	</div>
	
	<%@include file="/common/leftmenu.jsp" %>
	<div class="clearfix_b"></div>
</div>
<%@include file="/common/footer.jsp" %>
</body> 
</html>