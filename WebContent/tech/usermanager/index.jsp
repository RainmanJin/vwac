<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@include file="/common/head.jsp"%>
		<script type="text/javascript">
		function del(id)
		{
			if(confirm("${i18n.delete.comfirm}"))
			{
				ajaxGet("${ctxPath}/techc/usermanager/del",{"id":id},function(d){
					if(d.success)
						location.reload();
					else
						alert(d.data);
				},"json");
			}
		}
		
		function status(type,id)
		{
				ajaxGet("${ctxPath}/techc/usermanager/" + type,{"id":id},function(d){
					if(d.success)
						location.reload();
					else
						alert(d.data);
				},"json");
		}
		
		function batchActive(type)
		{
			var str="";    
			$("input[type='checkbox']:checked").each(function(){   
			str+=$(this).val()+",";    
			});
			
			if(str =="")
			{
				alert("${i18n.user.msg.plsSelectUsers}");
				return;
			}
			
			ajaxGet("${ctxPath}/techc/usermanager/" + type,{"id":str},function(d){
					if(d.success)
						location.href="index";
					else
						alert(d.data);
				},"json");
		//}
		}
		
	</script>
	</head>
	<body>
		<%@include file="/common/header.jsp"%>

		<div class="centerbody centerbox">
			<div id="contentwrapper">
				<div id="content_right">
					<div class="tt">
						${i18n.user.title}
					</div>
					<ec:table action="" method="get" items="list" var="var" view="ecan"
						width="99%" imagePath="${coreImgPath}/table/*.gif"
						showExports="true">
						<ec:exportXls fileName ="employee.xls" tooltip="导出 Excel" />  
						<ec:row>
							<ec:column headerCell="selectAll" filterable="false"
								sortable="false" width="3%" alias="ids">
								<input type="checkbox" name="ids" value="${var.id}" />
							</ec:column>
							<ec:column title="${i18n.user.filed.name}" property="name"
								width="10%"></ec:column>
							<ec:column title="${i18n.user.filed.loginName}"
								property="loginName" width="10%"></ec:column>
							<ec:column title="${i18n.user.filed.company}" property="company"
								width="20%" filterCell="droplist" filterOptions="DOMAIN_COMPANY">
								<d:viewDomain value="${var.company}" domain="COMPANY" />
							</ec:column>
							<%-- DELETE PROTYPE
						<ec:column title="${i18n.user.filed.proType}" property="proType" width="10%" filterCell="droplist" filterOptions="DOMAIN_PROTYPE">
							<d:viewDomain value="${var.proType}" domain="PROTYPE" />
						</ec:column>
						--%>
							<ec:column title="${i18n.user.filed.roleName}" property="roleId"
								width="10%" filterCell="droplist" filterOptions="DOMAIN_ROLE">
								<d:viewDomain value="${var.roleId}" domain="ROLE" />
							</ec:column>
							<ec:column title="${i18n.user.filed.status}" property="status"
								width="10%" filterCell="droplist"
								filterOptions="DOMAIN_USERSTATUS">
								<d:viewDomain value="${var.status}" domain="USERSTATUS" />
							</ec:column>
							<ec:column title="${i18n.oper.text}" property="oper" width="10%"
								filterCell="oper" sortable="false">
								<a href="view?id=${var.id}" class="oper">${i18n.oper.edit}</a>&nbsp;
							
							<c:choose>
									<c:when test="${var.status eq '1'}">
										<a href="javascript:void(0);"
											onclick="status('disable','${var.id}')" class="oper">${i18n.oper.disable}</a>
									</c:when>
									<c:when test="${var.status eq '2'}">
										<a href="javascript:void(0);"
											onclick="status('active','${var.id}')" class="oper">${i18n.oper.active}</a>
									</c:when>
									<c:otherwise>
										<a href="javascript:void(0);" onclick="del('${var.id}')"
											class="oper">${i18n.oper.del}</a>
									</c:otherwise>
								</c:choose>
							</ec:column>
						</ec:row>
					</ec:table>
					<div class="btns">
						<!-- <span class="graybtn">
					<a href="#nogo" onclick="del()">${i18n.button.del}</a>
				</span>
				 -->
						<span class="btn"> <a href="view">${i18n.button.add}</a> </span>
						<span class="btn"> <a href="#nogo"
							onClick="batchActive('batchActive');">${i18n.user.filed.batchActive}</a> </span>
						<span class="btn"> <a href="#nogo"
							onClick="batchActive('batchDisable');">${i18n.user.filed.batchDisable}</a> </span>
					</div>
				</div>
			</div>

			<%@include file="/common/leftmenu.jsp"%>
			<div class="clearfix_b"></div>
		</div>
		<%@include file="/common/footer.jsp"%>
	</body>
</html>