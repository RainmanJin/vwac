<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<%
		HttpSession namesession = request.getSession();
		String fileName = (String) namesession.getAttribute("fileName");//获取文件名
		
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "inline; filename=" + fileName);
	%>
	<div id="excelData">${tableExcelString}</div>
</body>
</html>