<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@include file="/common/head.jsp" %>
</head>
<body>
<%@include file="/common/header.jsp" %>

<div class="centerbody centerbox">
	<div id="contentwrapper">
		<div id="content_right">
			
			<span class="btn">
				<a href="#nogo">你好阿</a>
			</span>
			
			<span class="btn">
				<a href="#nogo">你好</a>
			</span>
			<br />
			<ec:table action="" items="list" var="var" view="ecan" width="100%"
					imagePath="${coreImgPath}/table/*.gif" showExports="true">
					<ec:exportXls fileName="download.xls"></ec:exportXls>
					<ec:row highlightRow="true">
						<ec:column title="名称" property="name" width="15%"></ec:column>
						<ec:column title="登录名" property="loginName" width="15%"></ec:column>
						<ec:column title="Email" property="email" width="30%" filterCell="droplist"></ec:column>
					</ec:row>
			</ec:table>	 
		</div>
	</div>
	
	<%@include file="/common/leftmenu.jsp" %>
	<div class="clearfix_b"></div>
</div>
<%@include file="/common/footer.jsp" %>
</body> 
</html>