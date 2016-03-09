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
	</script>
</head>
<body>
<%@include file="/common/header.jsp" %>

<div class="centerbody centerbox">
	<div id="contentwrapper">
		<div id="content_right">
			<div class="tt">${i18n.appname.type.title}
				<span class="btn" style="float: right;">
					<a href="view">${i18n.button.add}</a>
				</span>
			</div>
			
			<ec:table action="" method="get" items="list" var="var" view="ecan" width="99%"
					imagePath="${coreImgPath}/table/*.gif" showExports="false">
					<ec:row>
						<ec:column title="${i18n.appname.typename}" property="typeName" width="45%">
							 <a href="view?id=${var.id}" style="width: 320px; display: block; height: 25px; line-height: 25px; overflow: hidden;">
							    ${var.typeName}
							</a> 
						</ec:column>
						<ec:column title="${i18n.appname.typecode}" property="typeCode" width="24%">
							    ${var.typeCode}
						</ec:column>
						<ec:column title="${i18n.oper.text}" property="oper" width="20%" filterCell="oper" sortable="false">
						    <c:if test="${var.typeCode ne 'S' and var.typeCode ne 'D' }">
						    <a href="view?id=${var.id}" class="oper">${i18n.oper.edit}</a>&nbsp;
							</c:if>
							<c:if test="${var.status eq '1'}">
							<a href="javascript:void(0)" onclick="del('${var.id}')" class="oper">${i18n.oper.del}</a>
							</c:if>
						</ec:column>
					</ec:row>
			</ec:table>
			<br /><br />
		</div>
	</div>
	
	<%@include file="/common/leftmenu.jsp"%>
	<div class="clearfix_b"></div>
</div>
<%@include file="/common/footer.jsp" %>
</body> 
</html>