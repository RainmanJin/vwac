<%@page import="com.ecannetwork.tech.facade.HolidayFacade"%>
<%@page import="com.ecannetwork.dto.tech.TechTrainCourse"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="com.ecannetwork.dto.tech.TechTrainPlan"%>
<%@page import="java.util.Iterator"%>
<html>
<head>
	<%@include file="/common/head.jsp" %>
</head>
<body>

<ec:table tableId="myForm" action="resReport?year=${param.year}&brand=${param.brand}" method="post" items="list" var="var" 
			view="ecan" width="98%" rowsDisplayed="10000"
			imagePath="${coreImgPath}/table/*.gif"
			showExports="true" showPagination="false" sortable="false" filterable="false" autoIncludeParameters="false">
									<ec:exportXls fileName="${param.year}_${param.brand}_res.xls"></ec:exportXls>
									<ec:row>
										<ec:column title="${i18n.consumables.name}" property="conName" width="15%">
										</ec:column>
										
										<ec:column title="${i18n.consumables.num}" property="intConSum" width="60%" sortable="true">
										</ec:column>
									</ec:row>
</ec:table>			 

<br />
<br />

</body> 
</html>