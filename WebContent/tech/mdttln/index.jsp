<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@include file="/common/head.jsp"%>
<script type="text/javascript">
	function push(id) {

		ajaxGet("push", {
			"id" : id
		}, function(d) {
			if (d.success)
				location.reload();
			else
				alert(d.data);
		}, "json");

	}
	function del(id) {
		if (confirm("${i18n.delete.comfirm}")) {
			ajaxGet("del", {
				"id" : id
			}, function(d) {
				if (d.success)
					location.reload();
				else
					alert(d.data);
			}, "json");
		}
	}
</script>
</head>
<body>
	<%@include file="/common/header.jsp"%>

	<div class="centerbody centerbox">
		<div id="contentwrapper">
			<div id="content_right">
				<div class="tt">${i18n.mdttln.title}</div>

				<ec:table action="" method="get" items="list" var="var" view="ecan" width="99%" imagePath="${coreImgPath}/table/*.gif" showExports="false">
					<ec:row>
						<ec:column title="${i18n.mdttln.tasktitle}" property="title" width="20%">
							<a href="view?id=${var.id}" style="width: 320px; display: block; height: 25px; line-height: 25px; overflow: hidden;"> ${var.title} </a>
						</ec:column>
						<ec:column title="${i18n.mdttln.taskstyle}" property="style" width="18%" filterCell="droplist" filterOptions="DOMAIN_MDTT_LN_STATUS">
							<d:viewDomain value="${var.style}" domain="MDTT_LN_STATUS" />
						</ec:column>
						<ec:column title="${i18n.mdttln.status}" property="status" width="16%" filterCell="droplist" filterOptions="DOMAIN_MDTTLN_PUSH_FLAG">
							<d:viewDomain value="${var.status}" domain="MDTTLN_PUSH_FLAG" />
						</ec:column>
						<%-- <ec:column title="${i18n.mdttln.taskstart}" property="starttime" width="15%" filterable="false" cell="date" format="yyyy-MM-dd"></ec:column>
						<ec:column title="${i18n.mdttln.taskend}" property="endtime" width="15%" filterable="false" cell="date" format="yyyy-MM-dd"></ec:column> --%>
						<ec:column title="${i18n.mdttln.taskstart}" property="starttime" width="15%">${var.starttime}</ec:column>
						<ec:column title="${i18n.mdttln.taskend}" property="endtime" width="15%">${var.endtime}</ec:column>
						<%--<ec:column title="${i18n.mdttln.systype}" property="type" width="17%" filterable="false" sortable="false">
						   <c:forEach var="mt" items="${metypeList}" varStatus="s">
				                 <c:if test="${var.type==mt.typeCode}">${mt.typeName}</c:if>
			               </c:forEach>
						</ec:column>
						--%>
						<ec:column title="${i18n.oper.text}" property="oper" width="16%" filterCell="oper" sortable="false">
							<c:if test="${var.status ne '4'}">
								<a href="view?id=${var.id}" class="oper">${i18n.oper.edit}</a>&nbsp;
								<c:if test="${var.status eq '3'}">
									<a href="javascript:void(0)" onclick="push('${var.id}')" class="oper">${i18n.mdttln.button.bdpush}</a>
								</c:if>
							</c:if>
							<%--<a href="javascript:void(0)" onclick="del('${var.id}')" class="oper">${i18n.oper.del}</a>
						--%>
						</ec:column>
					</ec:row>
				</ec:table>
				<div class="btns">
					<span class="btn"> <a href="add">${i18n.button.add}</a>
					</span>
				</div>

				<br />
				<br />
			</div>
		</div>

		<%@include file="/common/leftmenu.jsp"%>
		<div class="clearfix_b"></div>
	</div>
	<%@include file="/common/footer.jsp"%>
</body>
</html>