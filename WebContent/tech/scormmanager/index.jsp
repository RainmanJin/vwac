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
				${i18n.scormpkg.title}
			</div>
			<ec:table action="" method="get" items="list" var="var" view="ecan" width="99%"
					imagePath="${coreImgPath}/table/*.gif" showExports="false">
					<ec:row>
						<%--
						<ec:column headerCell="selectAll" filterable="false" sortable="false" width="3%" alias="ids">
							<input type="checkbox" name="ids" value="${var.id}" />
						</ec:column>
						 --%>
						<ec:column title="${i18n.scormpkg.filed.name}" property="name" width="30%"></ec:column>
						<ec:column title="${i18n.scormpkg.filed.contentType}" property="contentType" width="10%" filterCell="droplist" filterOptions="DOMAIN_SCORMTYPE">
							<d:viewDomain domain="SCORMTYPE" value="${var.contentType}" />
						</ec:column>
						<ec:column title="${i18n.scormpkg.filed.brand}" property="brand" width="8%" filterCell="droplist" filterOptions="DOMAIN_BRAND">
							<d:viewDomain value="${var.brand}" domain="BRAND" />
						</ec:column>
						<ec:column title="${i18n.scormpkg.filed.proType}" property="proType" width="10%" filterCell="droplist" filterOptions="DOMAIN_PROTYPE">
							<d:viewDomain value="${var.proType}" domain="PROTYPE" />
						</ec:column>
						<ec:column title="${i18n.scormpkg.filed.status}" property="status" width="8%" filterCell="droplist" filterOptions="DOMAIN_STATUS">
							<d:viewDomain value="${var.status}" domain="STATUS" />
						</ec:column>
						<ec:column title="${i18n.oper.text}" property="oper" width="15%" filterCell="oper" sortable="false">
							<a href="view?id=${var.id}" class="oper">${i18n.oper.edit}</a>
							<c:choose>
								<c:when test="${var.status eq '1'}">
									<a href="javascript:void(0);" onclick="status('disable','${var.id}')" class="oper">${i18n.oper.disable}</a>
								</c:when>
								<c:when test="${var.status eq '0'}">
									<a href="javascript:void(0);" onclick="status('active','${var.id}')" class="oper">${i18n.oper.active}</a>
								</c:when>
							</c:choose>
							<a href="javascript:void(0);" onclick="del('${var.id}')" class="oper">${i18n.oper.del}</a>
						</ec:column>
					</ec:row>
			</ec:table>
			<div class="btns">
				<!-- <span class="graybtn">
					<a href="#nogo" onclick="del()">${i18n.button.del}</a>
				</span>
				 -->
				<span class="btn">
					<a href="javascript:upload();">${i18n.button.add}</a>
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