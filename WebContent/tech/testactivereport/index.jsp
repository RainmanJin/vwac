<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@include file="/common/head.jsp" %>
	<style type="text/css">
		div.tt{
			margin-bottom: 0px; 
		}
	</style>
	<script type="text/javascript">
		function del(id,status)
		{
			var msg = "${i18n.delete.comfirm}";
			if(status != "ready")
			{
				msg = "${i18n.testactive.msg.deleteStarted}";
			}
			if(confirm(msg))
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
		
		function upload()
		{
			$.fn.jmodal({
	            title: '${i18n.scormpkg.title}',
	            width:600,
	            content: function(body) {
	                body.html('loading...');
	                body.load('forUpload');
	            },
	            buttonText: { ok: '${i18n.button.upload}', cancel: '${i18n.button.cancel}' },
	            okEvent: function(data, args) {
	            	$("#myForm").submit();
	            }
	        });
		}
	</script>
</head>
<body>
<%@include file="/common/header.jsp" %>

<div class="centerbody centerbox">
	<div id="contentwrapper">
		<div id="content_right">
			<div class="tt">
				${i18n.activereport.title}
			</div>
			<ec:table action="" tableId="ec_1" method="get" items="list" var="var" view="ecan" width="99%"
					imagePath="${coreImgPath}/table/*.gif" showExports="false">
					<ec:row>
						<ec:column title="${i18n.testactive.filed.name}" property="name" width="20%"></ec:column>
						<ec:column title="${i18n.testactive.filed.brand}" property="brand" width="8%" filterCell="droplist" filterOptions="DOMAIN_BRAND">
							<d:viewDomain value="${var.brand}" domain="BRAND" />
						</ec:column>
						<ec:column title="${i18n.testactive.filed.hoster}" property="hosterId" width="10%" filterable="false">
							<ecan:viewDto property="name" dtoName="EcanUser" id="${var.hosterId}"/>
						</ec:column>
						<ec:column title="${i18n.testactive.filed.status}" property="status" width="8%" filterCell="droplist" filterOptions="DOMAIN_TESTSTATUS">
							<d:viewDomain value="${var.status}" domain="TESTSTATUS" />
						</ec:column>
						<ec:column title="${i18n.oper.text}" property="oper" width="10%" filterCell="oper" sortable="false">
							<a href="view?id=${var.id}" class="oper">${i18n.activereport.oper.viewReport}</a>&nbsp;
						</ec:column>
					</ec:row>
			</ec:table>
		</div>
	</div>
	
	<%@include file="/common/leftmenu.jsp" %>
	<div class="clearfix_b"></div>
</div>
<%@include file="/common/footer.jsp" %>
</body> 
</html>