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
		
		function modify(id)
		{
			$.fn.jmodal({
		            title: '${i18n.appname.questionmanager}',
		            width:800,
		            content: function(body) {
		                body.html('loading...');
		                body.load('view?id='+id);
		            },
		            buttonText: { ok: '${i18n.button.ok}', cancel: '${i18n.button.cancel}' },
			            okEvent: function(data, args) {
				        	$('#myForm1').submit();
				        }
		        });
		}
	
		function add(id)
		{
			$.fn.jmodal({
	            title: '${i18n.question.add.title}',
	            width:800,
	            content: function(body) {
	                body.html('loading...');
	                body.load('view?id='+id);
	            },
	            buttonText: { cancel: '${i18n.button.cancel}' }
	        });
		}
		
	</script>
</head>
<body>
<%@include file="/common/header.jsp" %>

<div class="centerbody centerbox">
	<div id="contentwrapper">
		<div id="content_right">
	<div class="tt">${i18n.appname.questionmanager}</div>
			<ec:table action="index" method="get" items="list" var="var" view="ecan" width="99%"
					imagePath="${coreImgPath}/table/*.gif" showExports="false">
					<ec:row>
						<%--
						<ec:column headerCell="selectAll" filterable="false" sortable="false" width="3%" alias="ids">
							<input type="checkbox" name="ids" value="${var.id}" />
						</ec:column>
						 --%>
						<ec:column title="${i18n.question.name}" property="questionName" width="120">
							<ecan:SubString content="${var.questionName}" end="14" fix="..."/>
						</ec:column>
						<ec:column title="${i18n.question.status}" property="questionStatus" width="9%" filterCell="droplist" filterOptions="DOMAIN_STATUS">
							<d:viewDomain value="${var.questionStatus}" domain="STATUS" />
						</ec:column>
						<ec:column title="${i18n.techdb.filed.createname}" property="questionUser" width="100">
							<ecan:viewDto dtoName="EcanUser" id="${var.questionUser}" property="name"/>
						</ec:column>
						<ec:column title="${i18n.techdb.filed.createtime}" property="questionDate" width="100">
							<ecan:dateFormart value="${var.questionDate}" formart="yyyy-MM-dd HH:ss:mm"/>
						</ec:column>
						
						<ec:column title="${i18n.oper.text}" property="oper" width="150" filterCell="oper" sortable="false">
								<a href="sublist?id=${var.id}" class="oper">${i18n.question.add.title}</a>
								<c:if test="${!var.is_Answer}">
									<a href="javascript:void(0);" onclick="del('${var.id}');" class="oper">${i18n.oper.del}</a>
									<a href="javascript:void(0)" onclick="modify('${var.id}');" class="oper">${i18n.oper.edit}</a>
								</c:if>
								<c:choose>
									<c:when test="${var.questionStatus eq '1'}">
										<a href="javascript:void(0);" onclick="status('disable','${var.id}');" class="oper">${i18n.oper.disable}</a>
									</c:when>
									<c:when test="${var.questionStatus eq '0'}">
										<a href="javascript:void(0);" onclick="status('canable','${var.id}');" class="oper">${i18n.oper.active}</a>
									</c:when>
								</c:choose>
							<a href="preview?id=${var.id}" target="_blank" class="oper">${i18n.question.preview}</a>
						</ec:column>
					</ec:row>
			</ec:table>
				
				<div class="btns">
				<span class="btn">
					<a href="javascript:void(0);" onclick="modify('');">${i18n.button.add}</a>
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