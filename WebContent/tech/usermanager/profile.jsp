<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@include file="/common/head.jsp" %>
</head>
<body>
<c:set var="isLoginView" value="1"></c:set>
<%@include file="/common/header.jsp" %>

<div class="centerbody centerbox">
	<div id="contentwrapper">
			<div class="tt">
				&nbsp;&nbsp;&nbsp;&nbsp;${dto.name}
			</div>
			<table width="100%" cellpadding="0" cellspacing="0" class="form">
				<tr>
					<td width="200" align="center">
						<img src="${ctxPath}${dto.headImg}" width="138" height="160" alt="headImage" />
						<br />
					</td>
					<td width="450">
						<ul>
							<li>
								<label>${i18n.user.filed.name}</label>
								<input type="text" name="name" value="${dto.name}" v="notEmpty" maxlength="10" class="gray" />
							</li>
							<li>
								<label>${i18n.user.filed.loginName}</label>
								<input type="text" name="loginName" value="${dto.loginName}" v="notEmpty" maxlength="10" class="gray" />
							</li>
							<li>
								<label>${i18n.user.filed.birthday}</label>
								<input type="text" name="birthday" value="${dto.birthday}"  maxlength="15" class="gray" />
							</li>
							<li>
								<label>${i18n.domains.call}</label>
								<input type="text" value="<d:viewDomain domain="CALLER" value="${dto.caller}" />" class="gray" />
							</li>
							<li>
								<label>${i18n.domains.position}</label>
								<input type="text" value="<d:viewDomain domain="POSITION" value="${dto.position}" />" class="gray" />
							</li>
							<li>
								<label>${i18n.user.filed.company}</label>
								<input type="text" value="<d:viewDomain domain="POSITION" value="${dto.position}" />" class="gray" />
							</li>
							<li>
								<label>${i18n.user.filed.roleName}</label>
								<input type="text" value="<d:viewDomain domain="ROLE" value="${dto.roleId}" />" class="gray" />
							</li>
							<li>
								<label>${i18n.user.filed.status}</label>
								<input type="text" value="<d:viewDomain domain="USERSTATUS" value="${dto.status}" />" class="gray" />
							</li>
	
							<li>
								<label>${i18n.user.filed.sex}</label>
								<input type="text" value="<d:viewDomain domain="SEX" value="${dto.sex}" />" class="gray" />
							</li>
							<li>
								<label>${i18n.user.filed.tel}</label>
								<input type="text" name="tel" value="${dto.tel}"  maxlength="15" class="gray" />
							</li>
							<li>
								<label>${i18n.user.filed.mobile}</label>
								<input type="text" name="mobile" value="${dto.mobile}"  maxlength="11" class="gray" />
							</li>
							<li>
								<label>${i18n.user.filed.email}</label>
								<input type="text" name="email" value="${dto.email}"  maxlength="30" class="gray" />
							</li>
						</ul>						
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<div class="btns" style="padding-left: 60px;">
							<c:if test="${!(empty dto.information)}">
								<span class="btn">
									<a href="${ctxPath}${dto.information}" target="_blank">${i18n.usercenter.button.downloadResume}</a>
								</span>
							</c:if>
						</div>			
					</td>
				</tr>
			</table>
</div>
<div class="clearfix_b"></div>
</div>
<%@include file="/common/footer.jsp" %>
</body> 
</html>