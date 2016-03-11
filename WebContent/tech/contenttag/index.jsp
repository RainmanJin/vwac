<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@include file="/common/head.jsp"%>
<script type="text/javascript">
	function delmainTag(id) {
		if (confirm("${i18n.delete.comfirm}")) {
			ajaxGet("delmainTag", {
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
				<div class="tt">${i18n.appname.tag.title}</div>

				<ec:table action="" method="get" items="list" var="var" view="ecan" width="99%" imagePath="${coreImgPath}/table/*.gif" showExports="false">
					<ec:row>
						<ec:column title="${i18n.tag.mainname}" property="tagName" width="25%">
							<a href="view?id=${var.id}" style="width: 320px; display: block; height: 25px; line-height: 25px; overflow: hidden;"> ${var.tagName} </a>
						</ec:column>
						<ec:column title="${i18n.tag.childname}" property="ctagName" width="44%" filterable="false" sortable="false">
							<c:forEach items="${var.tct}" var="tct" begin="0" end="1" varStatus="status">
								<a href="view?id=${var.id}" style="overflow: hidden;">${tct.ctagName},</a>
							</c:forEach>...
						</ec:column>
						<ec:column title="${i18n.oper.text}" property="oper" width="20%" filterCell="oper" sortable="false">
							<a href="view?id=${var.id}" class="oper">${i18n.oper.edit}</a>&nbsp;
						    <c:if test="${empty var.tct}">
								<a href="javascript:void(0)" onclick="delmainTag('${var.id}')" class="oper">${i18n.oper.del}</a>
							</c:if>
						</ec:column>
					</ec:row>
				</ec:table>

				<div class="btns">
					<span class="btn"> <a href="addmain">${i18n.button.addmaintag}</a>
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