<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@include file="/common/head.jsp" %>
	<script type="text/javascript">
	$(document).ready(function(){
		initJsonForm("#userForm",function(d){
			alert("${i18n.commit.success}");
			location.href="index";
		},function(d){
			alert(d.data)
		});
	});
	</script>
</head>
<body>
<%@include file="/common/header.jsp" %>

<div class="centerbody centerbox">
	<div id="contentwrapper">
		<div id="content_right">
			<div class="tt">
				<c:choose>
					<c:when test="${empty dto}">${i18n.user.add}</c:when>
					<c:otherwise>${i18n.user.edit}</c:otherwise>
				</c:choose>
			</div>
			
			<form id="userForm" action="save" method="post" class="form" enctype="multipart/form-data">
			<input type="hidden" name="id" value="${dto.id}" />
			<input type="hidden" name="headImg" value="${dto.headImg}" />
			<input type="hidden" name="information" value="${dto.information}" />
			<input type="hidden" name="status" value="${(empty dto.status)?1:dto.status}" />
			
			<table width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td width="200" align="center">
						<img src="${ctxPath}${dto.headImg}" width="138" height="160" alt="headImage" />
						<br />
						(137px * 160px) 
						<br />
						<input type="file" name="file" style="width: 160px;float: none;" />
					</td>
					<td width="450">
						<ul>
							<li><label>${i18n.user.filed.name}</label><input type="text" name="name" value="${dto.name}" v="notEmpty" maxlength="10" />
								<span class="msg">${i18n.user.filed.name}${i18n.valid.notEmpty}</span><em>*</em></li>
							<li>
							<c:choose>
								<c:when test="${empty dto.id}">
									<label>${i18n.user.filed.loginName}</label><input type="text" name="loginName" value="${dto.loginName}" v="notEmpty" maxlength="25" />
									<span class="msg">${i18n.user.filed.loginName}${i18n.valid.notEmpty}</span><em>*</em>
								</c:when>
								<c:otherwise>
									<label>${i18n.user.filed.loginName}</label>
									<input type="text" name="loginName" value="${dto.loginName}" class="gray" readonly="readonly" />
								</c:otherwise>
							</c:choose>
							</li>
							<li><label>${i18n.user.filed.password}</label><input type="text" name="loginPasswd" value="${dto.loginPasswd}" v="notEmpty" maxlength="25" />
								<span class="msg">${i18n.user.filed.password}${i18n.valid.notEmpty}</span><em>*</em></li>
							<li><label>${i18n.user.filed.nickname}</label><input type="text" name="nickName" value="${dto.nickName}" v="notEmpty" maxlength="25" />
								<span class="msg">${i18n.user.filed.nickname}${i18n.valid.notEmpty}</span><em>*</em></li>
							<li><label>${i18n.user.filed.birthday}</label><input type="text" name="birthday" onclick="WdatePicker()" readonly="readonly" value="${dto.birthday}"  maxlength="15" />
								<span class="msg">${i18n.user.filed.birthday}${i18n.valid.notEmpty}</span></li>	
							<li><label>${i18n.user.filed.cardId}</label><input type="text" name="cardId" value="${dto.cardId}" maxlength="18" />
								<span class="msg">${i18n.user.filed.cardId}${i18n.valid.notEmpty}</span></li>	
							<li><label>${i18n.user.filed.email}</label><input type="text" name="email" value="${dto.email}"  v="notEmpty" maxlength="50"/>
								<span class="msg">${i18n.user.filed.email}${i18n.valid.notEmpty}</span>
								<em>*</em>
							</li>
							<li>
								<label>${i18n.domains.call}</label>
								<d:selectDomain domain="CALLER" name="caller" value="${dto.caller}"/>
								<span class="msg"/>${i18n.domains.call}${i18n.valid.notEmpty}</span>
							</li>
							<li>
								<label>${i18n.domains.position}</label>
								<d:selectDomain domain="POSITION" name="position" value="${dto.position}"/>
								<span class="msg"/>${i18n.domains.position}${i18n.valid.notEmpty}</span>
							</li>
							<li><label>${i18n.user.filed.company}</label><d:selectDomain domain="COMPANY" name="company" value="${dto.company}" /></li>
							<%-- DELETE PROTYPE
							<li><label>${i18n.user.filed.proType}</label>
								<d:selectDomain domain="PROTYPE" name="proType" value="${dto.proType}" />
							</li>
							--%>
							<li><label>${i18n.user.filed.roleName}</label><d:selectDomain domain="ROLE" name="roleId" value="${dto.roleId}" /><em>*</em></li>
							<li>
								<label>${i18n.user.filed.sex}</label>
								<d:selectDomain domain="SEX" name="sex" value="${dto.sex}"/>
								<span class="msg"/>${i18n.user.filed.sex}${i18n.valid.notEmpty}</span>
							</li>
							<li><label>${i18n.user.filed.tel}</label><input type="text" name="tel" value="${dto.tel}"  maxlength="15" />
								<span class="msg">${i18n.user.filed.tel}${i18n.valid.notEmpty}</span></li>
							<li><label>${i18n.user.filed.mobile}</label><input type="text" name="mobile" value="${dto.mobile}"  maxlength="11"/>
								<span class="msg">${i18n.user.filed.mobile}${i18n.valid.notEmpty}</span></li>
							<li>
								<label>${i18n.domains.information}</label>
								<input type="file" name="informationFile" value="${curUser.information}"/>
								<span class="msg">${i18n.domains.information}${i18n.valid.notEmpty}</span>
							</li>
							
						</ul>						
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<div class="btns" style="padding-left: 60px;">
							<span class="btn">
								<a href="javascript:void(0)" onclick="$('#userForm').submit()">${i18n.button.submit}</a>
							</span>
							<c:if test="${!(empty dto.information)}">
								<span class="btn">
									<a href="${ctxPath}${dto.information}" target="_blank">${i18n.usercenter.button.downloadResume}</a>
								</span>
							</c:if>
							<span class="btn">
								<a href="javascript:history.back()">${i18n.button.back}</a>
							</span>
						</div>			
					</td>
				</tr>
			</table>
			</form>
		</div>
	</div>
	
	<%@include file="/common/leftmenu.jsp" %>
	<div class="clearfix_b"></div>
</div>
<%@include file="/common/footer.jsp" %>
</body> 
</html>