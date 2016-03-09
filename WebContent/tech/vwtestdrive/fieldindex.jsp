<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/context.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- 试乘试驾管理-字段管理 -->
<%@include file="/common/head.jsp"%>
<script type="text/javascript">
	function del(id) {
		if (confirm("${i18n.delete.comfirm}")) {
			ajaxGet("${ctxPath}/techc/vwtestdrive/delfield", {
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
				<div class="tt">字段管理</div>
				<ec:table action="filed" method="get" items="dblist" var="var" view="ecan" width="99%" imagePath="${coreImgPath}/table/*.gif" showExports="false">
					<ec:row>
						<ec:column title="名称" property="CName" width="40%">
							<a href="editfield?id=${var.id}"> <ecan:SubString content="${var.CName}" end="14" fix="..." />
							</a>
						</ec:column>
						<ec:column title="父级" property="parentname" width="30%">
							<ecan:SubString content="${var.parentName}" end="14" fix="..." />
						</ec:column>
						<ec:column title="排序" property="orderby" width="5%"></ec:column>
						<ec:column title="是否使用" property="isUse" width="10%">
							<c:choose>
								<c:when test="${var.isUse eq 1}">是</c:when>
								<c:otherwise>否</c:otherwise>
							</c:choose>
						</ec:column>
						<ec:column title="${i18n.oper.text}" property="oper" width="25%" filterCell="oper" sortable="false">
							<a href="editfield?id=${var.id}" class="oper">${i18n.oper.edit}</a>
							<a href="javascript:void(0);" onclick="del('${var.id}')" class="oper">删除</a>
						</ec:column>
					</ec:row>
				</ec:table>
				<div class="btns">
					<span class="btn"> <a href="addfield">${i18n.button.add}</a>
					</span> <span class="btn"> <a href="javascript:history.back();">${i18n.button.back}</a>
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