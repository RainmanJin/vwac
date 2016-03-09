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
<style>
.form li input{
	width: 150px;
}

#teacherDiv{
	display: inline-block;
}

.urow{
	display: block;
	height: 25px; line-height: 25px;
	border-bottom: 1px dotted #ddd;
}

a.operText{
	color: blue;
	text-decoration: underline;
	font-size: 14px;	
}
.w800{
	width: 500px;
	float: left;
}
.form td.label label{
	width: 160px;
	float: none;
	text-align: right;
	display: block;
}
td .eXtremeTable{
	float: left;
}
		.fill{
			background: #999;
		}
		
		.plan{
			background: #003c65;/*green*/
		}
		
		.confirm{
			background: #51ae30;/*blue*/
		}
		
		.cancel{
			background: #faaa00;/*yellow*/
		}
</style>
</head>
<body>

<ec:table tableId="myForm" action="exportUsers?id=${param.id}" method="post" items="list" var="var" 
			view="ecan" width="98%" rowsDisplayed="10000"
			imagePath="${coreImgPath}/table/*.gif"
			showExports="true" showPagination="false" sortable="false" filterable="false" autoIncludeParameters="false">
									<ec:exportXls fileName="users.xls"></ec:exportXls>
									<ec:row>
										<ec:column title="${i18n.user.filed.name}" property="name" width="15%">
										</ec:column>
										
										<ec:column title="${i18n.user.filed.loginName}" property="loginName" width="15%">
										</ec:column>
										
										<ec:column title="${i18n.user.filed.roleName}" property="roleId" width="10%">
										</ec:column>

										<ec:column title="${i18n.user.filed.cardId}" property="cardId" width="60%">
										</ec:column>
									</ec:row>
</ec:table>			 

<br />
<br />

</body> 
</html>