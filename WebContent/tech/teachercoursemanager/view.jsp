<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@include file="/common/head.jsp" %>
	<script>
		function add(teacherId, trainCourseId)
		{
			ajaxGet("add",{"id":teacherId, "trainCourseId":trainCourseId},function(d){
					if(d.success)
					{
						location.reload();
					}else
					{
						alert(d.data);
					}
				},"json");
		}

		function del(teacherId, trainCourseId)
		{
			ajaxGet("del",{"id":teacherId, "trainCourseId":trainCourseId},function(d){
				if(d.success)
				{
					location.reload();
				}else
				{
					alert(d.data);
				}
			},"json");
		}
	</script>
</head>
<body>
<%@include file="/common/header.jsp" %>

<div class="centerbody centerbox">
	<div id="contentwrapper">
		<div id="content_right">
			<div class="tt">
				${i18n.teachercourse.titleViewTeacher}: &nbsp;
				<ecan:viewDto property="name" dtoName="EcanUser" id="${param.id}"/>
			</div>
			<ec:table tableId="t1" action="" method="get" items="list" var="var" view="ecan" width="99%"
					imagePath="${coreImgPath}/table/*.gif" showExports="false">
					<ec:row>
						<ec:column title="${i18n.traincourse.filed.name}" property="name" width="30%"></ec:column>
						<ec:column title="${i18n.traincourse.filed.proType}" property="proType" width="10%" filterCell="droplist" filterOptions="DOMAIN_PROTYPE">
							<d:viewDomain value="${var.proType}" domain="PROTYPE" />
						</ec:column>
						<ec:column title="${i18n.traincourse.filed.brand}" property="brand" width="10%" filterCell="droplist" filterOptions="DOMAIN_BRAND">
							<d:viewDomain value="${var.brand}" domain="BRAND" />
						</ec:column>
						<ec:column title="${i18n.oper.text}" property="oper" width="10%" filterCell="oper" sortable="false">
							<a href="javascript:void(0)" onclick="del('${param.id}','${var.id}');" class="oper">${i18n.oper.del}</a>
						</ec:column>
					</ec:row>
			</ec:table>
			
			<div class="tt" style="margin-top: 30px; margin-bottom: 5px;">
				${i18n.teachercourse.titleViewAll}
			</div>
			<ec:table tableId="t2" action="" method="get" items="all" var="var" view="ecan" width="99%"
					imagePath="${coreImgPath}/table/*.gif" showExports="false">
					<ec:row>
						<ec:column title="${i18n.traincourse.filed.name}" property="name" width="30%"></ec:column>
						<ec:column title="${i18n.traincourse.filed.proType}" property="proType" width="10%" filterCell="droplist" filterOptions="DOMAIN_PROTYPE">
							<d:viewDomain value="${var.proType}" domain="PROTYPE" />
						</ec:column>
						<ec:column title="${i18n.traincourse.filed.brand}" property="brand" width="10%" filterCell="droplist" filterOptions="DOMAIN_BRAND">
							<d:viewDomain value="${var.brand}" domain="BRAND" />
						</ec:column>
						<ec:column title="${i18n.oper.text}" property="oper" width="10%" filterCell="oper" sortable="false">
							<a href="javascript:void(0)" onclick="add('${param.id}','${var.id}');" class="oper">${i18n.oper.add}</a>
						</ec:column>
					</ec:row>
			</ec:table>
			
			<div class="btns">
				<span class="btn">
						<a href="javascript:history.back();">${i18n.button.back}</a>
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