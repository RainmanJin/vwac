<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@include file="/common/head.jsp" %>
	<style>
		.table_border {border-top:1px #DDD solid;border-right:1px #DDD solid;border-left: 1px #DDD solid;border-bottom: 1px #DDD solid;}
		.user_cls label{line-height: 30px;height: 30px;}
		.pleft20{
			padding-left: 20px;
			font-size: 16px;
			font-weight: bold;
		}
		.pleft40{
			padding-left: 40px;
		}
	</style>
</head>
<body>
<%@include file="/common/header.jsp" %>

<div class="centerbody centerbox">
	<div id="contentwrapper">
		 <div  style="float: left; width: 760px;">
				 <table style="float: left;margin-left: 20px;height: 140px;" width="350" class="table_border">
				 	<tr>
				 		<td class="pleft20">${i18n.operman.course}</td>
				 	</tr>
				 	<tr>
				 		<td class="pleft40">${i18n.operman.coursecount}:&nbsp;${courseCount}</td>
				 	</tr>
				 	<tr>
				 		<td class="pleft40">${i18n.operman.testdbcount}:&nbsp;${testListSize}</td>
				 	</tr>
				 	<tr>
				 		<td class="pleft40">${i18n.operman.testtingdbcount}:&nbsp;${testtingListSize}</td>
				 	</tr>
				 </table>
				 
				 <table style="margin-left: 390px;height: 140px;" width="350" class="table_border">
					 <tr>
				 		<td class="pleft20">${i18n.operman.user}</td>
				 	</tr>
				 	<tr>
				 		<td class="pleft40">${i18n.operman.usercount }:&nbsp;${userlistSize}</td>
				 	</tr>
				 	<tr>
				 		<td class="pleft40">${i18n.operman.activecount }:&nbsp;${activelistSize}</td>
				 	</tr>
				 	<tr>
				 		<td class="pleft40">${i18n.operman.diactivecount}:&nbsp;${userlistSize-activelistSize}</td>
				 	</tr>
				 </table>
			 </div>
			 
			  <div style="margin-left: 790px;" class="user_cls">
						<img width="120px;" height="140px;" src="${ctxPath}${user.headImg}"/><br/>
						<label>${i18n.student.filed.name}:&nbsp;&nbsp;${user.name }</label><br/>
						<label>${i18n.student.filed.company }:&nbsp;&nbsp;<d:viewDomain domain="COMPANY" value="${user.company}"/></label><br/>
						<label>${i18n.user.filed.roleName}：   <d:viewDomain domain="ROLE" value="${user.roleId}"/></label><br/>
			  </div>
			  <br />
			  <br />
			  <br />
			  <br />
			  <br />
			  <br />
			  <br />
			  <br />
	</div>
	
	<%@include file="/common/leftmenu.jsp" %>
	<div class="clearfix_b"></div>
</div>
<%@include file="/common/footer.jsp" %>
<%@include file="/common/sync.jsp" %>
</body> 
</html>