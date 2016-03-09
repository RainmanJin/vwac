<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@include file="/common/head.jsp" %>
	<script type="text/javascript">
	function push(id)
	{
	
			ajaxGet("push",{"id":id},function(d){
				if(d.success)
					location.reload();
				else
					alert(d.data);
			},"json");
		
	}
	function del(id)
	{
		if(confirm("${i18n.delete.comfirm}"))
		{
			ajaxGet("deleteMess",{"id":id},function(d){
				if(d.success)
					location.reload();
				else
					alert(d.data);
			},"json");
		}
	}
	function batchpush()
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
		
		ajaxGet("batchpush" ,{"id":str},function(d){
				if(d.success)
					location.href="index";
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
			<div class="tt">${i18n.message.list}
				
			</div>
			
			<ec:table action="" method="get" items="list" var="var" view="ecan" width="99%"
					imagePath="${coreImgPath}/table/*.gif" showExports="false">
					<ec:row>
					<ec:column headerCell="selectAll" filterable="false"
								sortable="false" width="5%" alias="ids">
								<input type="checkbox" name="ids" value="${var.id}" />
							</ec:column>
						<ec:column title="${i18n.message.title}" property="title" width="23%">
							 <a href="view?id=${var.id}" style="width: 320px; display: block; height: 25px; line-height: 25px; overflow: hidden;">
							    ${var.title}
							</a>
						</ec:column>
						<ec:column title="${i18n.message.pushflag}" property="flag" width="12%"  filterCell="droplist" filterOptions="DOMAIN_MESSAGE_PUSH_FLAG">
							 <d:viewDomain value="${var.flag}" domain="MESSAGE_PUSH_FLAG" />
						</ec:column>
						
						<%-- <ec:column title="${i18n.message.sendtime}" property="mesTime" width="23%" filterable="false" cell="date" format="yyyy-MM-dd HH:mm">${var.mesTime}</ec:column> --%>
						<ec:column title="${i18n.message.sendtime}" property="mesTime" width="23%">${var.mesTime}</ec:column>
						<%-- <ec:column title="${i18n.message.type}" property="type" width="12%"  filterable="false" sortable="false" filterOptions="DOMAIN_MESSAGE_TYPE">
							 <d:viewDomain value="${var.type}" domain="MESSAGE_TYPE" />
						</ec:column> --%>
						<ec:column title="${i18n.message.type}" property="type" width="14%" filterable="false" sortable="false">
						   <c:forEach var="mt" items="${metypeList}" varStatus="s">
				                 <c:if test="${var.type==mt.typeCode}">${mt.typeName}</c:if>
			               </c:forEach>
						</ec:column>
						<ec:column title="${i18n.oper.text}" property="oper" width="20%" filterCell="oper" sortable="false">
							<a href="view?id=${var.id}" class="oper">${i18n.oper.edit}</a>&nbsp;
							<c:if test="${var.flag eq '0'}">
							<a href="javascript:void(0)" onclick="push('${var.id}')" class="oper">${i18n.mdttln.button.savepush}</a>
							</c:if>
							<a href="javascript:void(0)" onclick="del('${var.id}')" class="oper">${i18n.oper.del}</a>
						</ec:column>
					</ec:row>
			</ec:table>
			<div class="btns">
						<span class="btn"> <a href="add">${i18n.button.add}</a> </span>
						<span class="btn"> <a href="#nogo"
							onClick="batchpush();">${i18n.message.button.batchpush}</a> </span>
					</div>
			<br /><br />
		</div>
	</div>
	
	<%@include file="/common/leftmenu.jsp"%>
	<div class="clearfix_b"></div>
</div>
<%@include file="/common/footer.jsp" %>
</body> 
</html>