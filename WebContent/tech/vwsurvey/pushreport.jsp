<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/context.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
</head>
<body>
	<div class="form" style="height: 450px;">
		<!-- escapeXml ﻿：是否转换特殊字符，如：< 转换成 &lt;  -->
		<ul>
			<li><label>${i18n.app.postaddress}<br />${i18n.app.copyuse}
			</label> <input style="width: 500px;" type="text" name="webpath" value="${webpath}" maxlength="255" /></li>
			<li style=""><label>${i18n.app.dimensional}<br />${i18n.app.saveimage}
			</label><img src="${qrPath}" width="100" height="100" /></li>
			<li><label>${i18n.app.sitepasscode}<br />${i18n.app.copyuse}
			</label> 
			
			
			<div style="margin-top:80px;margin-left:170px">
			<textarea style="width: 500px; height: 300px; resize: none" name="content">
			
			<c:if test="${fn:length(content)>0}">  
			<c:out value="${content}" escapeXml="true" />
			</c:if>   
			</textarea>
			</div> 
			</li>
		</ul>
	</div>
</body>
</html>