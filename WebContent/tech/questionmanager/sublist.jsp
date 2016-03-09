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
				ajaxGet("delsub",{"id":id},function(d){
					if(d.success)
						location.reload();
					else
						alert(d.data);
				},"json");
			}
		}
		
		
		function modify(id,questionid)
		{
			$.fn.jmodal({
		            title: '${i18n.question.add.title}',
		            width:800,
		            content: function(body) {
		                body.html('loading...');
		                body.load('viewsub?id='+id+"&questionid="+questionid);
		            },
		            buttonText: { ok: '${i18n.button.ok}', cancel: '${i18n.button.cancel}'},
			            okEvent: function(data, args) {
				        	$('#subform11').submit();
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
	<div class="tt">${i18n.question.add.title}</div>
			<ec:table action="sublist" method="get" items="subList" var="var" view="ecan" width="99%"
					imagePath="${coreImgPath}/table/*.gif" showExports="false">
					<ec:row>
						<%--
						<ec:column headerCell="selectAll" filterable="false" sortable="false" width="3%" alias="ids">
							<input type="checkbox" name="ids" value="${var.id}" />
						</ec:column>
						 --%>
						<ec:column title="${i18n.techdb.filed.title}" property="subitmesName" width="270">
							<ecan:SubString content="${var.subitmesName}" end="35" fix="..."/>
						</ec:column>
						<ec:column title="${i18n.domain.filed.idx}" property="subitmesIdx" width="8%">
						</ec:column>
						<ec:column title="${i18n.question.topic.type}" property="subitmeType" width="20%" filterCell="droplist" filterOptions="DOMAIN_QUESTIONASSTYPE">
							<d:viewDomain value="${var.subitmeType}" domain="QUESTIONASSTYPE"/>
						</ec:column>
						
						<ec:column title="${i18n.oper.text}" property="oper" width="50" filterCell="oper" sortable="false">
								<a href="javascript:void(0);" onclick="del('${var.id}');" class="oper">${i18n.oper.del}</a>
								<a href="javascript:void(0)" onclick="modify('${var.id}','${questionid}');" class="oper">${i18n.oper.edit}</a>
						</ec:column>
					</ec:row>
			</ec:table>
				
				<div class="btns">
				<span class="btn">
					<a href="javascript:void(0);" onclick="modify('','${questionid}');">${i18n.button.add}</a>
				</span>
				<span class="btn">
					<a href="index">${i18n.button.back}</a>
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