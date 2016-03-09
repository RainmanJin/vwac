<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
		<%@include file="/common/head.jsp"%>
		<style>
			.table_border td{border-top:1px #DDD solid;border-right:1px #DDD solid;}
			.user_cls label{line-height: 30px;height: 30px;}
		</style>
	</head>
	<body>
		<%@include file="/common/header.jsp"%>

		<div class="centerbody centerbox">
			<div id="contentwrapper">
			
			 <div  style="float: left; width: 760px;">
			 	<div class="tt" style="margin-bottom: 0px;">
					${i18n.course.order}
				</div>
				
				<ec:table action="" items="inglist" var="var" view="ecan" width="100%"
					imagePath="${coreImgPath}/table/*.gif" showExports="false" filterable="false" showStatusBar="false" sortable="false" rowsDisplayed="1000">
					<ec:row highlightRow="true">
						<ec:column title="${i18n.domain.filed.label}" property="name" width="40%"></ec:column>
						<ec:column title="${i18n.traincourse.filed.expiretime}" property="expireTime" width="20%" cell="date" format="yyyy-MM-dd"></ec:column>
						<ec:column title="${i18n.course.studentcount}" property="studentCount" width="20%" ></ec:column>
						<ec:column title="${i18n.domains.status}" property="courseStatus" width="20%" >
							<d:viewDomain domain="COURSESTATUS" value="${var.courseStatus}"/>
						</ec:column>
					</ec:row>
				</ec:table>
			 </div>
			   
			 <div style="margin-left: 800px;" class="user_cls">
							<img width="120" height="140" src="${ctxPath}${user.headImg}"/><br/>
							<label>${i18n.student.filed.name}:&nbsp;&nbsp;${user.name }</label><br/>
							<label>${i18n.student.filed.company }:&nbsp;&nbsp;<d:viewDomain domain="COMPANY" value="${user.company}"/></label><br/>
							<label>${i18n.user.filed.roleName}：   <d:viewDomain domain="ROLE" value="${user.roleId}"/></label><br/>
			 </div>
		</div>
	<%@include file="/common/leftmenu.jsp" %>
	<div class="clearfix_b"></div>
</div>
<%@include file="/common/footer.jsp" %>
<%@include file="/common/sync.jsp" %>
</body> 
</html>