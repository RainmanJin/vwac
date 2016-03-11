<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@include file="/common/head.jsp"%>
<script type="text/javascript">
	function del(id) {
		if (confirm("${i18n.delete.comfirm}")) {
			ajaxGet("${ctxPath}/techc/app/del", {
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

				<div class="tt">${i18n.appname.mw.appplatform}</div>

				<ec:table action="index" method="get" items="appplat" var="var" view="ecan" width="99%" imagePath="${coreImgPath}/table/*.gif" showExports="false">
					<ec:row>
						<ec:column title="${i18n.consumables.name}" property="name" width="120">
							<a href="view?id=${var.id}"> <ecan:SubString content="${var.name}" end="14" fix="..." />
							</a>
						</ec:column>
						<ec:column title="${i18n.techdb.filed.protype}" property="proType" width="16%" filterCell="droplist" filterOptions="DOMAIN_PROTYPE">
							<d:viewDomain value="${var.proType}" domain="PROTYPE" />
						</ec:column>
						<ec:column title="${i18n.mdttpkg.filed.version}" property="version" width="6%">
						</ec:column>
						<ec:column title="${i18n.techdb.filed.status}" property="status" width="8%" filterCell="droplist" filterOptions="DOMAIN_STATUS">
							<d:viewDomain value="${var.status}" domain="STATUS" />
						</ec:column>
						<ec:column title="${i18n.mdttpkg.filed.update}" property="lastUpdateTime" width="20%">
						</ec:column>
						<ec:column title="${i18n.oper.text}" property="oper" width="50" filterCell="oper" sortable="false">
							<a href="view?id=${var.id}" class="oper">${i18n.oper.edit}</a>&nbsp;
                            <a href="javascript:void(0);" onclick="del('${var.id}')" class="oper">${i18n.oper.del}</a>
						</ec:column>
					</ec:row>
				</ec:table>

				<div class="btns">
					<span class="btn"> <a href="view">${i18n.button.add}</a>
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