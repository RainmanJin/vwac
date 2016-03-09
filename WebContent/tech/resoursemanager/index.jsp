<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@include file="/common/head.jsp" %>
	<script type="text/javascript">
	
	function del(id)
		{
			$.fn.jmodal({
	            title: '${i18n.resourse.delete}',
	            width:800,
	            content: function(body) {
	                body.html('loading...');
	                body.load('beforedelete?id='+id);
	            },
	            buttonText: { ok: '${i18n.button.ok}', cancel: '${i18n.button.cancel}' },
		            okEvent: function(data, args) {
			        	$('#myForm1').submit();
			        }
	        });
		}
		
	function modify(id)
	{
		$.fn.jmodal({
	            title: '${i18n.resourse.modify}',
	            width:800,
	            content: function(body) {
	                body.html('loading...');
	                body.load('edit?id='+id);
	            },
	            buttonText: { ok: '${i18n.button.ok}', cancel: '${i18n.button.cancel}' },
	             okEvent: function(data, args) {
			        	$('#myForm1').submit();
			        }
	        });
	}
	
	function userRecord(id)
	{
		$.fn.jmodal({
	            title: '${i18n.resourse.record}',
	            width:800,
	            content: function(body) {
	                body.html('loading...');
	                body.load('userrecord?id='+id);
	            },
	            buttonText: {cancel: '${i18n.button.cancel}'}
	        });
	}
	
	function status(type,id)
		{
				ajaxGet("${ctxPath}/techc/resoursemanager/" + type,{"id":id},function(d){
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
	<div class="tt">${i18n.resourse.title}</div>
			<ec:table action="index" method="get" items="resourseList" var="var" view="ecan" width="99%"
					imagePath="${coreImgPath}/table/*.gif" showExports="false">
					<ec:row>
						<%--
						<ec:column headerCell="selectAll" filterable="false" sortable="false" width="3%" alias="ids">
							<input type="checkbox" name="ids" value="${var.id}" />
						</ec:column>
						 --%>
						<ec:column title="${i18n.resourse.name}" property="resName" width="250">
							<a href="view?id=${var.id}">
							<ecan:SubString content="${var.resName}" end="14" fix="..."/>
							</a>
						</ec:column>
						<ec:column title="${i18n.resourse.type}" property="resType" width="50" filterCell="droplist" filterOptions="DOMAIN_COMMONLYUSED">
							<d:viewDomain domain="COMMONLYUSED"  value="${var.resType}"/>
						</ec:column>
						<ec:column title="${i18n.resourse.num}" property="intResSum" width="40">
						</ec:column>
						<ec:column title="${i18n.user.filed.status}" property="status"
								width="40" filterCell="droplist"
								filterOptions="DOMAIN_STATUS">
								<d:viewDomain value="${var.status}" domain="STATUS" />
						</ec:column>
						<%--
						<ec:column title="${i18n.resourse.remark}" property="resRemark" width="180">
							<ecan:SubString content="${var.resRemark}" end="14" fix="..."/>
						</ec:column>
						 --%>
						<ec:column title="${i18n.oper.text}" property="oper" width="250" filterCell="oper" sortable="false">
							<c:choose>
								<c:when test="${var.status eq '1'}">
									<a href="javascript:void(0);" onclick="status('disable','${var.id}')" class="oper">${i18n.oper.disable}</a>&nbsp;
									&nbsp;&nbsp;&nbsp;
									<a href="javascript:void(0);" onclick="modify('${var.id}')" class="oper">${i18n.consumables.modify}</a> &nbsp;
									&nbsp;&nbsp;&nbsp;
								</c:when>
								<c:when test="${var.status eq '0'}">
									<a href="javascript:void(0);" onclick="status('active','${var.id}')" class="oper">${i18n.oper.active}</a>&nbsp;
									&nbsp;&nbsp;&nbsp;
								</c:when>
							</c:choose>
								<a href="javascript:void(0);" onclick="del('${var.id}')" class="oper">${i18n.oper.del}</a>
								&nbsp;&nbsp;&nbsp;
								<a href="view?id=${var.id}" class="oper">${i18n.oper.edit}</a>
								<c:if test="${var.hasUser}">
									<a href="javascript:void(0);" onclick="userRecord('${var.id}');" class="oper">${i18n.resourse.record}</a>
								</c:if>
						</ec:column>
					</ec:row>
			</ec:table>
				
				<div class="btns">
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