<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@include file="/common/head.jsp"%>
		<style>
			.table_border td{border: 1px #DDD solid;}
			.table_border td div{
				line-height: 24px;
			}
			
			.table_border td p{
				font-weight: bold; padding: 5px 0px;
			}
			.user_cls label{line-height: 30px;height: 30px;}
		</style>
		<script>
			function addTestting(id,courseInstanceId)
			{
				var url = "";
				location.href = "${ctxPath}/techc/elearning/testting?id="+id+"&courseInstanceId="+courseInstanceId;
			}
			function addStudy(courseId, courseInstanceId)
			{
				location.href="${ctxPath}/techc/elearning/study?id="+courseId + "&courseInstanceId=" + courseInstanceId;
			}
		</script>
	</head>
	<body>
		<%@include file="/common/header.jsp"%>

		<div class="centerbody centerbox">
			<div id="contentwrapper">
			 	 <table width="650" style="float: left;" class="table_border">
			 	 	<c:forEach items="${courseTmp}" var="dto">
						<tr>
							<td style="margin-left: 20px; margin-top: 20px; width: 200px; height: 160px;">
								<img width="200" height="160" src="${ctxPath}${dto.preview}"/>
							</td>
							<td>
								<div style="width:550px; padding-left: 20px;">
									<p>${dto.name}</p>
									<div style="height: 72px; overflow: hidden;">
											<ecan:SubString content="${dto.remark}" end="240" fix="..."/>
									</div>
									<div class="btns" style="text-align: right; margin: 3px 3px 10px;">
										<span class="btn">
											<a href="javascript:addStudy('${dto.courseId}','${dto.id}');">${i18n.oper.startlearn}</a>
										</span>
										<c:if test="${dto.hasDbs}">
										<span class="btn">
											<a href="javascript:addTestting('${dto.courseId}','${dto.id}');">${i18n.oper.starttestting}</a>
										</span>
										</c:if>
									</div>
								</div>
							</td>
						</tr>
					</c:forEach>
					</table>
					<div style="margin-left: 800px;" class="user_cls">
							<img width="120px;" height="140px;" src="${ctxPath}${user.headImg}"/><br/>
							<label>${i18n.student.filed.name}:&nbsp;&nbsp;${user.name }</label><br/>
							<label>${i18n.student.filed.company }:&nbsp;&nbsp;<d:viewDomain domain="COMPANY" value="${user.company}"/></label><br/>
							<label>${i18n.user.filed.roleName}：   <d:viewDomain domain="ROLE" value="${user.roleId}"/></label><br/>
					</div>
					<div class="clearfix_b"></div>
			</div>

			<%@include file="/common/leftmenu.jsp"%>
			<div class="clearfix_b"></div>
		</div>
		<%@include file="/common/footer.jsp"%>
<%@include file="/common/sync.jsp" %>
	</body>
</html>