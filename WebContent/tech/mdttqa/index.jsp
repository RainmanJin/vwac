<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@include file="/common/head.jsp"%>
<script type="text/javascript">
	function chgStatus(s, id) {
		ajaxGet("status", {
			"id" : id,
			"status" : s
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
				<div class="tt">${i18n.mdttqa.title}</div>

				<ec:table action="" method="get" items="list" var="var" view="ecan" width="99%" imagePath="${coreImgPath}/table/*.gif" showExports="false">
					<ec:row>
						<ec:column title="${i18n.mdttqa.qustion}" property="question" width="45%">
							<a href="view?id=${var.id}" style="width: 320px; display: block; height: 25px; line-height: 25px; overflow: hidden;"> ${var.question} </a>
						</ec:column>
						<%-- 
						<ec:column title="Brand" property="brand" width="15%"  filterCell="droplist" filterOptions="DOMAIN_BRAND">
							<d:viewDomain value="${var.brand}" domain="BRAND" />
						</ec:column>
						--%>
						<ec:column title="${i18n.mdttqa.spec}" property="proType" width="15%" filterCell="droplist" filterOptions="DOMAIN_PROTYPE">
							<d:viewDomain value="${var.proType}" domain="PROTYPE" />
						</ec:column>
						<ec:column title="${i18n.i18n.filed.status}" property="status" width="8%" filterCell="droplist" filterOptions="DOMAIN_MDTT_PKG_STATUS">
							<d:viewDomain value="${var.status}" domain="MDTT_PKG_STATUS" />
						</ec:column>
						<ec:column title="${i18n.oper.text}" property="oper" width="16%" filterCell="oper" sortable="false">
							<a href="view?id=${var.id}" class="oper">${i18n.oper.edit}</a>&nbsp;
							<c:choose>
								<c:when test="${var.status eq '1'}">
									<a href="javascript:void(0)" onclick="chgStatus('0','${var.id}')" class="oper">${i18n.oper.disable}</a>
								</c:when>
								<c:otherwise>
									<a href="javascript:void(0)" onclick="chgStatus('1','${var.id}')" class="oper">${i18n.oper.active}</a>
								</c:otherwise>
							</c:choose>&nbsp;
							<a href="javascript:void(0)" onclick="del('${var.id}')" class="oper">${i18n.oper.del}</a>
						</ec:column>
					</ec:row>
				</ec:table>

				<div class="btns">
					<span class="btn"> <a href="view">${i18n.button.add}</a>
					</span>
				</div>
				<br /> <br />
			</div>
		</div>

		<%@include file="/common/leftmenu.jsp"%>
		<div class="clearfix_b"></div>
	</div>
	<%@include file="/common/footer.jsp"%>
</body>
</html>