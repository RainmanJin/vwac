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
			<div class="tt">${i18n.appname.tag.title}
				<span class="btn" style="float: right;">
					<a href="add">${i18n.oper.add}</a>
				</span>
			</div>
			
			<ec:table action="" method="get" items="list" var="var" view="ecan" width="99%"
					imagePath="${coreImgPath}/table/*.gif" showExports="false">
					<ec:row>
					<ec:column title="ID" property="id" >
							 <a href="view?id=${var.id}" style="display: block; height: 25px; line-height: 25px; overflow: hidden;">
							    ${var.id}
							</a> 
						</ec:column>
					
						<ec:column title="${i18n.i18n.filed.name}" property="title" >
							 <a href="view?id=${var.id}" style="display: block; height: 25px; line-height: 25px; overflow: hidden;">
							    ${var.title}
							</a> 
						</ec:column>
						
						<ec:column title="${i18n.classic}" property="typeId" >
							 <a href="view?id=${var.id}" style="display: block; height: 25px; line-height: 25px; overflow: hidden;">
							    首页幻灯
							</a> 
						</ec:column>
						<ec:column title="${i18n.resourselog.date}"  property="createTimeDesc" >
							 <a href="view?id=${var.id}" style="display: block; height: 25px; line-height: 25px; overflow: hidden;">
							    ${var.createTimeDesc}
							</a> 
							
							
						</ec:column>
						
						
						
						<ec:column title="${i18n.oper.text}" property="oper" width="20%" filterCell="oper" sortable="false">
						    <a href="view?id=${var.id}" class="oper">${i18n.oper.edit}</a>&nbsp;
						    <a href="javascript:void(0)" onclick="del('${var.id}')" class="oper">${i18n.oper.del}</a>
						  
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