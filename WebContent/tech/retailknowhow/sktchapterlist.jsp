<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/context.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- 配置题目 -->
<%@include file="/common/head.jsp"%>
<script type="text/javascript">
	function delques(id) {
		if (confirm("${i18n.delete.comfirm}")) {
			ajaxGet("${ctxPath}/techc/retailknowhow/delques", {
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
				<div class="tt">配置题目</div>
				<ec:table action="sktchapterlist" method="get" items="dblist" var="var" view="ecan" width="99%" imagePath="${coreImgPath}/table/*.gif" showExports="false">
					<ec:row>
						<ec:column title="題目名" property="CName" width="30%">
							<a href="editques?id=${var.id}"> <ecan:SubString content="${var.CName}" end="14" fix="..." />
							</a>
						</ec:column>
						<ec:column title="排序" property="sort" width="30%">
              ${var.sort}
            </ec:column>
						<ec:column title="状态" property="islock" width="10%">${var.islock == 0 ? "正常" : "锁定"}</ec:column>
						<ec:column title="类型" property="type" width="10%">
							<c:choose>
								<c:when test="${var.type eq '1'}">结构图 </c:when>
								<c:when test="${var.type eq '2'}">排序图 </c:when>
								<c:otherwise>树状图 </c:otherwise>
							</c:choose>
						</ec:column>
						<ec:column title="${i18n.oper.text}" property="oper" width="30%" filterCell="oper" sortable="false">
							<a href="sktitemlist?id=${var.id}&cid=${param.id}&type=${var.type}" class="oper">配置项目</a>
							<a href="editques?id=${var.id}" class="oper">${i18n.oper.edit}</a>
							<a href="javascript:void(0);" onclick="delques('${var.id}')" class="oper">${i18n.oper.del}</a>
						</ec:column>
					</ec:row>
				</ec:table>
				<div class="btns">
					<span class="btn"> <a href="addques?cid=${param.id}">${i18n.button.add}</a>
					</span> <span class="btn"><a href="index">${i18n.button.back}</a> </span>
				</div>
			</div>
		</div>
		<%@include file="/common/leftmenu.jsp"%>
		<div class="clearfix_b"></div>
	</div>
	<%@include file="/common/footer.jsp"%>
</body>
</html>