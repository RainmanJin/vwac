<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@include file="/common/head.jsp"%>
<script type="text/javascript">
	function del(id) {
		if (confirm("${i18n.delete.comfirm}")) {
			ajaxGet("${ctxPath}/techc/testdb/del", {
				"id" : id
			}, function(d) {
				if (d.success)
					location.reload();
				else
					alert(d.data);
			}, "json");
		}
	}

	function status(type, id) {
		ajaxGet("${ctxPath}/techc/testdb/" + type, {
			"id" : id
		}, function(d) {
			if (d.success)
				location.reload();
			else
				alert(d.data);
		}, "json");
	}
</script>
</head>
<body>
	<%@include file="/common/header.jsp"%>

	<div class="centerbody centerbox">
		<div id="contentwrapper">
			<div id="content_right">
				<div class="tt">${i18n.techdb.title}</div>
				<ec:table action="index" method="get" items="dblist" var="var" view="ecan" width="99%" imagePath="${coreImgPath}/table/*.gif" showExports="false">
					<ec:row>
						<%--
						<ec:column headerCell="selectAll" filterable="false" sortable="false" width="3%" alias="ids">
							<input type="checkbox" name="ids" value="${var.id}" />
						</ec:column>
						 --%>
						<ec:column title="${i18n.techdb.filed.title}" property="title" width="180">
							<a href="view?id=${var.id}"> <ecan:SubString content="${var.title}" end="14" fix="..." />
							</a>
						</ec:column>
						<ec:column title="${i18n.techdb.filed.courseName}" property="trainCourseId" width="23%" filterCell="droplist" filterOptions="courseList">
							<ecan:viewDto id="${var.trainCourseId}" property="name" dtoName="TechTrainCourse" />
						</ec:column>
						<ec:column title="${i18n.techdb.filed.status}" property="status" width="8%" filterCell="droplist" filterOptions="DOMAIN_STATUS">
							<d:viewDomain value="${var.status}" domain="STATUS" />
						</ec:column>

						<ec:column title="${i18n.techdb.filed.protype}" property="proType" width="8%" filterCell="droplist" filterOptions="DOMAIN_PROTYPE">
							<d:viewDomain value="${var.proType}" domain="PROTYPE" />
						</ec:column>
						<ec:column title="${i18n.techdb.filed.brand}" property="brand" width="6%" filterCell="droplist" filterOptions="DOMAIN_BRAND">
							<d:viewDomain value="${var.brand}" domain="BRAND" />
						</ec:column>
						<ec:column title="${i18n.oper.text}" property="oper" width="120" filterCell="oper" sortable="false">
							<a href="view?id=${var.id}" class="oper">${i18n.oper.edit}</a>&nbsp;
							<c:choose>
								<c:when test="${var.status eq '1'}">
									<a href="javascript:void(0);" onclick="status('disable','${var.id}')" class="oper">${i18n.oper.disable}</a>
								</c:when>
								<c:when test="${var.status eq '0'}">
									<a href="javascript:void(0);" onclick="status('active','${var.id}')" class="oper">${i18n.oper.active}</a>
								</c:when>
								<a href="javascript:void(0);" onclick="del('${var.id}')" class="oper">${i18n.oper.del}</a>
							</c:choose>
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