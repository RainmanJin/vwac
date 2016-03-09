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
				<ec:table action="votesubject?sysId=${sysId}" method="get" items="dblist" var="var" view="ecan" width="99%" imagePath="${coreImgPath}/table/*.gif" showExports="false">
					<ec:row>
						<ec:column title="题目名称" property="CSubTitle" width="50%">
							<ecan:SubString content="${var.CSubTitle}" end="40" fix="..." />
						</ec:column>
						<%-- <ec:column title="所属" property="parentid" width="15%">
						</ec:column> --%>
						<ec:column title="类型" property="NType" width="20%">
							<c:if test="${var.NType==1}">1-满意度调查</c:if>
							<c:if test="${var.NType==2}">2-意见框</c:if>
						</ec:column>
						<ec:column title="排序" property="NOrderId" width="7%">
							<ecan:SubString content="${var.NOrderId}" />
						</ec:column>
						<ec:column title="必答" property="NNeed" width="7%">
							<c:if test="${var.NNeed==0}">0-否</c:if>
							<c:if test="${var.NNeed==1}">1-是</c:if>
						</ec:column>
						<ec:column title="${i18n.oper.text}" property="oper" width="45%" filterCell="oper" sortable="false">
							<a href="votesubjectview?id=${var.id}" class="oper">编辑</a>
						</ec:column>
					</ec:row>
				</ec:table>
				<div class="btns">
					<span class="btn"> <a href="votesubjectview?sysId=${sysId}">${i18n.button.add}</a>
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