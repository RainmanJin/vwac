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
				<ec:table action="voteunit?voteId=${voteId}" method="get" items="dblist" var="var" view="ecan" width="99%" imagePath="${coreImgPath}/table/*.gif" showExports="false">
					<ec:row>
						<ec:column title="调查名称" property="CSubTitle" width="60%">
							<ecan:SubString content="${var.CSubTitle}" end="50" fix="..." />
						</ec:column>
						<ec:column title="类型" property="NType" width="20%">
							<c:if test="${var.NType==2}">2-单选</c:if>
							<c:if test="${var.NType==3}">3-多选</c:if>
							<c:if test="${var.NType==5}">5-多行文本</c:if>
						</ec:column>
						<ec:column title="${i18n.oper.text}" property="oper" width="45%" filterCell="oper" sortable="false">
							<a href="votekey?subId=${var.id}" class="oper">字段</a>
							<a href="voteunitview?id=${var.id}" class="oper">编辑</a>
						</ec:column>
					</ec:row>
				</ec:table>
				<div class="btns">
					<span class="btn"> <a href="voteunitview?voteId=${voteId}">${i18n.button.add}</a>
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