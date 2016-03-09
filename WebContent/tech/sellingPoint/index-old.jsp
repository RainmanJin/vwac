<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/context.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/common/head.jsp"%>
<script type="text/javascript">
	function delmain(id) {
		if (confirm("${i18n.delete.comfirm}")) {
			ajaxGet("delmain", {
				"id" : id
			}, function(d) {
				if (d.success)
					location.reload();
				else
					alert(d.data);
			}, "json");
		}
	}
	function push(id) {
		if (confirm("${i18n.pushpkg.comfirm}")) {
			ajaxGet("push", {
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
				<div class="tt">${i18n.selling.pointlist}</div>
				<ec:table action="" method="get" items="list" var="var" view="ecan" width="99%" imagePath="${coreImgPath}/table/*.gif" showExports="false">
					<ec:row>
						<ec:column title="${i18n.selling.pointtitle}" property="title" width="32%">
							<a href="view?id=${var.id}" style="width: 320px; display: block; height: 25px; line-height: 25px; overflow: hidden;"> ${var.title} </a>
						</ec:column>
						<ec:column title="${i18n.sellingpoint.status}" property="status" width="15%" filterCell="droplist" filterOptions="DOMAIN_POINT_STATUS">
							<d:viewDomain value="${var.status}" domain="POINT_STATUS" />
						</ec:column>
						<ec:column title="${i18n.sellingpoint.createTime}" property="createTime" width="20%">
							    ${var.createTime}
						</ec:column>
						<ec:column title="${i18n.oper.text}" property="oper" width="24%" filterCell="oper" sortable="false">
							<a href="view?id=${var.id}" class="oper">${i18n.oper.edit}</a>&nbsp;
					        <c:if test="${var.status ne 1}">
								<a href="javascript:void(0)" onclick="push('${var.id}')" class="oper">${i18n.button.sellingpoint.push}</a>
							</c:if>
							<a href="javascript:void(0)" onclick="delmain('${var.id}')" class="oper">${i18n.oper.del}</a>
						</ec:column>
					</ec:row>
				</ec:table>
				<div class="btns">
					<span class="btn"> <a href="add">${i18n.button.add}</a>
					</span>
				</div>
			</div>
		</div>
		<%@include file="/common/leftmenu.jsp"%>
		<div class="clearfix_b"></div>
	</div>
	<%@include file="/common/footer.jsp"%>
</body>
</html>