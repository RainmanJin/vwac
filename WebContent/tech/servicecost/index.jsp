<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/context.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- 服务费用计算 -->
<%@include file="/common/head.jsp"%>
<script type="text/javascript">
	function del(id) {
		if (confirm("${i18n.delete.comfirm}")) {
			ajaxGet("${ctxPath}/techc/servicecost/del", {
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
				<div class="tt">${i18n.appname.mw.servicecost}</div>
				<ec:table action="index" method="get" items="dblist" var="var" view="ecan" width="99%" imagePath="${coreImgPath}/table/*.gif" showExports="false">
					<ec:row>
						<ec:column title="ID" property="id" width="20%"></ec:column>
						<ec:column title="项目名称" property="a3" width="40%">
							<ecan:SubString content="${var.a3}" end="14" fix="..." />
						</ec:column>
						<ec:column title="${i18n.course.filed.createtime}" property="createTime" width="20%">
							<ecan:dateFormart value="${var.createTime}" formart="yyyy-MM-dd HH:ss:mm" />
						</ec:column>
						<ec:column title="${i18n.oper.text}" property="oper" width="20%" filterCell="oper" sortable="false">
							<a href="javascript:void(0);" onclick="del('${var.id}')" class="oper">${i18n.oper.del}</a>
						</ec:column>
					</ec:row>
				</ec:table>
			</div>
		</div>
		<%@include file="/common/leftmenu.jsp"%>
		<div class="clearfix_b"></div>
	</div>
	<%@include file="/common/footer.jsp"%>
</body>
</html>