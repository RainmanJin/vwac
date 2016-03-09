<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@include file="/common/head.jsp" %>
	<script type="text/javascript">
		function del(year,brand)
		{
			if(confirm("${i18n.delete.comfirm}"))
			{
				ajaxGet("del",{"year":year, "brand":brand},function(d){
					if(d.success)
						location.reload();
					else
						alert(d.data);
				},"json");
			}
		}
		
		function add(id)
		{
			$.fn.jmodal({
	            title: '${i18n.traincourse.title}',
	            width:600,
	            content: function(body) {
	                body.html('loading...');
	                body.load('createPre' + id);
	            },
	            buttonText: { ok: '${i18n.button.ok}', cancel: '${i18n.button.cancel}' },
	            okEvent: function(data, args) {
	            	//args.hide();
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
				${i18n.trainplan.title}
			</div>
			<ec:table action="" method="get" items="list" var="var" view="ecan" width="100%"
					imagePath="${coreImgPath}/table/*.gif" showExports="false">
					<ec:row>
						<%--
						<ec:column headerCell="selectAll" filterable="false" sortable="false" width="3%" alias="ids">
							<input type="checkbox" name="ids" value="${var.id}" />
						</ec:column>
						 --%>
						<ec:column title="${i18n.trainplan.filed.yearValue}" property="yearValue" width="30%">
							<a href="view?year=${var.yearValue}&brand=${var.brand}" class="oper">${var.yearValue}</a>
						</ec:column>
						<ec:column title="${i18n.trainplan.filed.brand}" property="brand" width="10%" filterCell="droplist" filterOptions="DOMAIN_BRAND">
							<d:viewDomain value="${var.brand}" domain="BRAND" />
						</ec:column>
						
						<ec:column title="${i18n.oper.text}" property="oper" width="10%" filterCell="oper" sortable="false">
							<a href="view?year=${var.yearValue}&brand=${var.brand}" class="oper">${i18n.oper.edit}</a>&nbsp;
							<!-- <a href="javascript:void(0)" onclick="del('${var.yearValue}','${var.brand}')" class="oper">${i18n.oper.del}</a> -->
						</ec:column>
					</ec:row>
			</ec:table>
			
			<div class="btns">
				<c:if test="${('planadm' eq current_user.role.id) or ('admin' eq current_user.role.id)}">
					<span class="btn">
						<a href="javascript:void(0)" onclick="add('${dto.id}')">${i18n.button.add}</a>
					</span>
				</c:if>
			</div>	
		</div>
	</div>
	
	<%@include file="/common/leftmenu.jsp" %>
	<div class="clearfix_b"></div>
</div>
<%@include file="/common/footer.jsp" %>
</body> 
</html>