<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@include file="/common/head.jsp" %>
	<script type="text/javascript">
	
	function delmain(id)
	{
		if(confirm("${i18n.delete.comfirm}"))
		{
			ajaxGet("delmain",{"id":id},function(d){
				if(d.success)
					location.reload();
				else
					alert(d.data);
			},"json");
		}
	}
	function preview(id){
		var url = "${pageContext.request.contextPath}/tech/upload/sellingPoint/"+id+"/index.html";
		//alert(url);
		window.open(url);
	}
	</script>
</head>
<body>
<%@include file="/common/header.jsp" %>

<div class="centerbody centerbox">
	<div id="contentwrapper">
		<div id="content_right">
			<div class="tt">${i18n.sellingpoint.conentlist}
				<span class="btn" style="float: right;">
					<a href="add_conent">${i18n.button.add}</a>
				</span>
			</div>
			
			<ec:table action="" method="get" items="list" var="var" view="ecan" width="99%"
					imagePath="${coreImgPath}/table/*.gif" showExports="false">
					<ec:row>
						<ec:column title="${i18n.selling.pointtitle}" property="title" width="32%">
							 <a href="editconent?id=${var.id}" style="width: 320px; display: block; height: 25px; line-height: 25px; overflow: hidden;">
							    ${var.title}
							</a>
						</ec:column>
						 <%-- 
						<ec:column title="${i18n.sellingpoint.content}" property="content" width="28%">
							 ${var.content} 
						</ec:column>--%>
						<ec:column title="${i18n.sellingpoint.createTime}" property="createTime" width="24%">
							    ${var.createTime}
						</ec:column>
						<ec:column title="${i18n.oper.text}" property="oper" width="24%" filterCell="oper" sortable="false">
							<a href="editconent?id=${var.id}" class="oper">${i18n.oper.edit}</a> 
							<%-- <a href="javascript:void(0)" onclick="delmain('${var.id}')" class="oper">${i18n.oper.del}</a> --%>
							<a href="javascript:void(0)" onclick="preview('${var.id}')" class="oper">${i18n.button.preview}</a>
							<%-- <a href="${pageContext.request.contextPath}/tech/sellingPoint/${var.id}.jsp" class="oper">${i18n.button.preview}</a> --%>
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