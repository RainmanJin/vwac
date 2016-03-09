<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/context.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/common/head.jsp"%>
<script type="text/javascript">
</script>
</head>
<body>
	<%@include file="/common/header.jsp"%>
	<div class="centerbody centerbox">
		<div id="contentwrapper">
			<div id="content_right">
				<div class="tt">${i18n.appname.mw.vwsurveymakevote}</div>
				<ec:table action="index" method="get" items="dblist" var="var" view="ecan" width="99%" imagePath="${coreImgPath}/table/*.gif" showExports="false">
					<ec:row>
						<ec:column title="调查名称" property="CTitle" width="70%">
							<ecan:SubString content="${var.CTitle}" end="80" fix="..." />
						</ec:column>
						<ec:column title="${i18n.oper.text}" property="oper" width="45%" filterCell="oper" sortable="false">
							<a href="voteunit?voteId=${var.id}" class="oper">调查单元</a>
							<a href="votesubject?sysId=${var.id}" class="oper">调查选项</a>
							<a href="view?id=${var.id}" class="oper">编辑</a>
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