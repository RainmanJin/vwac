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
				<ec:table action="votekey?subId=${subId}" method="get" items="dblist" var="var" view="ecan" width="99%" imagePath="${coreImgPath}/table/*.gif" showExports="false">
					<ec:row>
						<ec:column title="选项内容" property="CKeyTitle" width="50%">
							<ecan:SubString content="${var.CKeyTitle}" end="50" fix="..." />
						</ec:column>
						<ec:column title="优先顺序" property="NOrderId" width="10%" >
							<ecan:SubString content="${var.NOrderId}" />
						</ec:column>
						<ec:column title="分值" property="NScore" width="10%" >
							<ecan:SubString content="${var.NScore}" />
						</ec:column>
						<%-- <ec:column title="格式限制" property="CRule" width="10%" >
							${var.CRule}
						</ec:column> --%>
						<ec:column title="${i18n.oper.text}" property="oper" width="45%" filterCell="oper" sortable="false">
							<a href="votekeyview?id=${var.id}" class="oper">编辑</a>
						</ec:column>
					</ec:row>
				</ec:table>
				<div class="btns">
					<span class="btn"> <a href="votekeyview?subId=${subId}">${i18n.button.add}</a>
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