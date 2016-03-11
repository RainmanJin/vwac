<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/context.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- 课堂卡片互动 -->
<%@include file="/common/head.jsp"%>
<script type="text/javascript">
	function del(id) {
		if (confirm("${i18n.delete.comfirm}")) {
			ajaxGet("${ctxPath}/techc/retailknowhow/del", {
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
				<div class="tt">${i18n.appname.mw.sktitemlist}</div>
				<ec:table action="index" method="get" items="dblist" var="var" view="ecan" width="99%" imagePath="${coreImgPath}/table/*.gif" showExports="false">
					<ec:row>
						<ec:column title="知识名称" property="CName">
							<a href="edit?id=${var.id}"> <ecan:SubString content="${var.CName}" end="14" fix="..." />
							</a>
						</ec:column>
						<ec:column title="状态" property="islocked" width="8%">${var.islocked == 0 ? "正常" : "异常"}</ec:column>
						<ec:column title="加入时间" property="dtCreateTime" cell="date" width="20%" format="yyyy-MM-dd HH:mm" filterable="false">${var.dtCreateTime}</ec:column>
						<ec:column title="${i18n.oper.text}" property="oper" width="18%" filterCell="oper" sortable="false">
							<a href="sktchapterlist?id=${var.id}" class="oper">配置题目</a>
							<a href="edit?id=${var.id}" class="oper">${i18n.oper.edit}</a>
							<a href="javascript:void(0);" onclick="del('${var.id}')" class="oper">${i18n.oper.del}</a>
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