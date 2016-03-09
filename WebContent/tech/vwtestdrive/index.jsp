<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/context.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- 试乘试驾管理 -->
<%@include file="/common/head.jsp"%>
<script type="text/javascript">
	function del(id) {
		if (confirm("${i18n.delete.comfirm}")) {
			ajaxGet("${ctxPath}/techc/vwtestdrive/del", {
				"id" : id
			}, function(d) {
				if (d.success) {
					location.reload();
				} else {
					alert(d.data);
				}
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
				<div class="tt">${i18n.appname.mw.vwtestdrive}</div>
				<ec:table action="index" method="get" items="dblist" var="var" view="ecan" width="99%" imagePath="${coreImgPath}/table/*.gif" showExports="false">
					<ec:row>
						<ec:column title="名称" property="CName" width="60%">
							<a href="edit?id=${var.id}"> <ecan:SubString content="${var.CName}" end="14" fix="..." />
							</a>
						</ec:column>
						<ec:column title="字段配置" property="changdi" width="20%">
							<c:choose>
								<c:when test="${var.changdi eq '0,1,2'}">
									<a href="changdidetail?id=${var.id}&type=0">公路</a>
									<a href="changdidetail?id=${var.id}&type=1">运动</a>
									<a href="changdidetail?id=${var.id}&type=2">越野</a>
								</c:when>
								<c:when test="${var.changdi eq ',1,2'}">
									<a href="changdidetail?id=${var.id}&type=1">运动</a>
									<a href="changdidetail?id=${var.id}&type=2">越野</a>
								</c:when>
								<c:when test="${var.changdi eq '0,,2'}">
									<a href="changdidetail?id=${var.id}&type=0">公路</a>
									<a href="changdidetail?id=${var.id}&type=2">越野</a>
								</c:when>
								<c:when test="${var.changdi eq '0,1,'}">
									<a href="changdidetail?id=${var.id}&type=0">公路</a>
									<a href="changdidetail?id=${var.id}&type=1">运动</a>
								</c:when>
								<c:when test="${var.changdi eq '0,,'}">
									<a href="changdidetail?id=${var.id}&type=0">公路</a>
								</c:when>
								<c:when test="${var.changdi eq ',1,'}">
									<a href="changdidetail?id=${var.id}&type=1">运动</a>
								</c:when>
									<c:when test="${var.changdi eq ',,2'}">
									<a href="changdidetail?id=${var.id}&type=2">越野</a>
								</c:when>
								<c:otherwise>无</c:otherwise>
							</c:choose>
						</ec:column>
						<ec:column title="${i18n.oper.text}" property="oper" width="20%" filterCell="oper" sortable="false">
							<a href="edit?id=${var.id}" class="oper">${i18n.oper.edit}</a>
							<a href="javascript:void(0);" onclick="del('${var.id}')" class="oper">删除</a>
						</ec:column>
					</ec:row>
				</ec:table>
				<div class="btns">
					<span class="btn"> <a href="add">${i18n.button.add}</a>
					</span> <span class="btn"> <a href="filed">字段管理</a>
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