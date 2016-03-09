<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@include file="/common/head.jsp" %>
	<script type="text/javascript">
		function del(id)
		{
			if(confirm("${i18n.delete.comfirm}"))
			{
				ajaxGet("del",{"id":id},function(d){
					if(d.success)
						location.reload();
					else
						alert(d.data);
				},"json");
			}
		}
		
		function status(type,id)
		{
				ajaxGet(type,{"id":id},function(d){
					if(d.success)
						location.reload();
					else
						alert(d.data);
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
				${i18n.traincourse.title}
			</div>
			<ec:table action="" method="get" items="list" var="var" view="ecan" width="99%"
					imagePath="${coreImgPath}/table/*.gif" showExports="false">
					<ec:row>
						<%--
						<ec:column headerCell="selectAll" filterable="false" sortable="false" width="3%" alias="ids">
							<input type="checkbox" name="ids" value="${var.id}" />
						</ec:column>
						 --%>
						<ec:column title="${i18n.traincourse.filed.name}" property="name" width="30%"></ec:column>
						<ec:column title="${i18n.traincourse.filed.type}" property="type" width="10%" filterCell="droplist" filterOptions="DOMAIN_TRAINCOURSE_TYPE">
							<d:viewDomain value="${var.type}" domain="TRAINCOURSE_TYPE" />
						</ec:column>
						<ec:column title="${i18n.traincourse.filed.proType}" property="proType" width="10%" filterCell="droplist" filterOptions="DOMAIN_PROTYPE">
							<d:viewDomain value="${var.proType}" domain="PROTYPE" />
						</ec:column>

						<ec:column title="${i18n.traincourse.filed.brand}" property="brand" width="10%" filterCell="droplist" filterOptions="DOMAIN_BRAND">
							<d:viewDomain value="${var.brand}" domain="BRAND" />
						</ec:column>
						<ec:column title="${i18n.user.filed.status}" property="status" width="10%" filterCell="droplist" filterOptions="DOMAIN_STATUS">
							<d:viewDomain value="${var.status}" domain="STATUS" />
						</ec:column>
						<ec:column title="${i18n.oper.text}" property="oper" width="15%" filterCell="oper" sortable="false">
							<a href="view?id=${var.id}" class="oper">${i18n.oper.edit}</a>
							<c:choose>
								<c:when test="${var.status eq '1'}">
									<a href="#nogo" onclick="status('disable','${var.id}')" class="oper">${i18n.oper.disable}</a>
								</c:when>
								<c:when test="${var.status eq '0'}">
									<a href="#nogo" onclick="status('active','${var.id}')" class="oper">${i18n.oper.active}</a>
								</c:when>
							</c:choose>
							<a href="#nogo" onclick="del('${var.id}')" class="oper">${i18n.oper.del}</a>
						</ec:column>
					</ec:row>
			</ec:table>
			<div class="btns">
				<!-- <span class="graybtn">
					<a href="#nogo" onclick="del()">${i18n.button.del}</a>
				</span>
				 -->
				<span class="btn">
					<a href="view">${i18n.button.add}</a>
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