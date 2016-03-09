<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/context.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style>
body {
	margin: 10px 10px;
	padding: 0;
	font-family: "Lucida Grande", Helvetica, Arial, Verdana, sans-serif;
	font-size: 14px;
}

#calendar {
	max-width: 1000px;
	margin: 0 auto;
}

.table {
	max-width: 1000px;
	margin: 0 auto;
	font-family: "微软雅黑", Helvetica, Arial, Verdana, sans-serif,;
	font-size: 14px;
	/* font-color:#fcfcfc" */
	 border:0px ;cellpadding:0px; cellspacing:0px;
	 border-collapse:collapse;
}

.table td {
	height: 72px;
	font-size: 44px;
	font-color:#fcfcfc"
	/* border:0px; */ 
	padding-left: 10px;
	
	 border: 1px solid #bbb; 
	/* border-radius: 8px 8px 8px 8px; */
}

</style>
</head>
<body>
	<div
		style="max-width: 940px; height: 60px; margin: 0px auto; margin-bottom: 30px">
		<span
			style="width: 180px; float: right; background-color: #ddd; border-radius: 5px 5px 5px 5px; font-size: 36px; line-height: 60px; text-align: center;">
			<a href="/techc/client/logout">${i18n.logout}</a>
		</span>
	</div>
	<div id='calendar'>
			   <table class="table"  >
			<c:forEach items="${list}" var="l" varStatus="ls">
		
				    <tr style="background-color:#bebebe;">
						<td style="text-align: left;width:740px;" colspan=2>名称：
						${l.title}</td>
					</tr><tr style="background-color:#bebebe;" >
						<td style="text-align: left;width:740px;" colspan=2>时间：
						${l.starttime} — ${l.endtime}</td>
					</tr>
					<tr style="background-color:#bebebe;">
						<td style="text-align: left;width:740px;" colspan=2>类型：<c:if test="${l.type == 'P'}">个人任务</c:if> <c:if
								test="${l.type == 'S'}">分配任务</c:if></td>
					</tr>
					
					
				<c:forEach items="${l.lnpkg}" var="p" varStatus="s">
				
					
					
					<c:if test="${s.index%2==0 }">
                        <tr style="text-align:left;vertical-align:middle;">
                     </c:if>
							<c:choose> 
								<c:when test="${p.pkgname==null || p.pkgname=='' }">
									<td>无资源</td>
								</c:when>
								<c:otherwise>
									<td ><div style="position:relative;padding-top:0px;align:top;"><div style="float:left;width:370px;border-right-style:none;"><img  src="../../images/empty_photo1.png" border=0 align="middle" width="365px"/></div>
									<div style="clear:both;width:370px;text-overflow:ellipsis;white-space:nowrap;overflow:hidden;">${p.pkgname}</div>
									<c:forEach items="${pkglist}" var="pl" varStatus="sl">
									<c:if test="${p.pkgid==pl.id}">
									<div style="float:left;">版本：${pl.version}</div>
									
									<c:choose>
											<c:when test="${pl.proType==null || pl.proType==''}">
											<div style="clear:both;border-radius: 8px 8px 8px 8px;float:left;background-color:#ed0090;  width:auto" >
											            未知分类
											</div>
											</c:when>
											<c:otherwise>
											<div style="clear:both;border-radius: 8px 8px 8px 8px;float:left;background-color:#027cc5;  width:auto" >
											     <d:viewDomain value="${pl.proType}" domain="PROTYPE" />
											     </div>
											</c:otherwise>
									</c:choose>
									</c:if>
									</c:forEach>
									</div>
									</td>
								</c:otherwise>
							</c:choose>
							<c:if test="${s.index%2==1 }">
							 </tr>
						    </c:if>
					
					
				</c:forEach>
				
			</c:forEach>
			</table>
	</div>
</body>
</html>
