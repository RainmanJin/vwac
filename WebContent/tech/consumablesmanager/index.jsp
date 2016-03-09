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
	            title: '${i18n.consumables.delete}',
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
	            title: '${i18n.consumables.addbatch}',
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
	function viewbatch(id,name)
	{
		$.fn.jmodal({
	            title: name,
	            width:800,
	            content: function(body) {
	                body.html('loading...');
	                body.load('viewbatch?id='+id);
	            },
	            buttonText: {cancel: '${i18n.button.cancel}' }
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
	</script>
</head>
<body>
<%@include file="/common/header.jsp" %>

<div class="centerbody centerbox">
	<div id="contentwrapper">
		<div id="content_right">
	<div class="tt">${i18n.consumables.title}</div>
			<ec:table action="index" method="get" items="resourseList" var="var" view="ecan" width="99%"
					imagePath="${coreImgPath}/table/*.gif" showExports="false">
					<ec:row>
						<%--
						<ec:column headerCell="selectAll" filterable="false" sortable="false" width="3%" alias="ids">
							<input type="checkbox" name="ids" value="${var.id}" />
						</ec:column>
						 --%>
						<ec:column title="${i18n.consumables.name}" property="conName"  >
							<a href="view?id=${var.id}">
							<ecan:SubString content="${var.conName}" end="50" fix="..."/>
							</a>
						</ec:column>
						<ec:column title="${i18n.consumables.num}" property="intConSum" width="50">
						</ec:column>
						
						<ec:column title="${i18n.oper.text}" property="oper" width="250" filterCell="oper" sortable="false">
							<!-- <a href="javascript:void(0);" onclick="modify('${var.id}')" class="oper">${i18n.consumables.modify}</a>&nbsp; -->
								<a href="javascript:void(0);" onclick="modify('${var.id}')" class="oper">${i18n.consumables.addbatch}</a>
								<a href="javascript:void(0);" onclick="del('${var.id}')" class="oper">${i18n.oper.del}</a>
								<a href="view?id=${var.id}" class="oper">${i18n.oper.edit}</a>
								<a href="javascript:void(0);" onclick="viewbatch('${var.id}','${var.conName}')" class="oper">${i18n.oper.viewbatch}</a>
								<c:if test="${var.hasUsed}">
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