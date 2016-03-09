<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@include file="/common/head.jsp" %>
	<script type="text/javascript">
	$(document).ready(function(){
		initJsonForm("#userForm",function(d){
			alert(d.data)
			<c:choose>
				<c:when test="${empty isLoginView}">
					location.href="index";
				</c:when>
				<c:otherwise>
					$("#li_passwd").addClass("hover");
					$("#li_basic").removeClass("hover");
					$("#div_basic").hide();
					$("#div_passwd").show();
				</c:otherwise>
			</c:choose>
		},function(d){
			alert(d.data)
		});
		
		initJsonForm("#passForm",function(d){
			<c:choose>
				<c:when test="${empty isLoginView}">
					location.href="index";
				</c:when>
				<c:otherwise>
					location.href="${ctxPath}/p/actived";
				</c:otherwise>
			</c:choose>
		},function(d){
			alert(d.data)
		});
		


		$("#li_basic").click(function(){
				$("#li_basic").addClass("hover");
				$("#li_passwd").removeClass("hover");
				$("#div_basic").show();
				$("#div_passwd").hide();
			});

		$("#li_passwd").click(function(){
				$("#li_passwd").addClass("hover");
				$("#li_basic").removeClass("hover");
				$("#div_basic").hide();
				$("#div_passwd").show();
			});
	});
	</script>
	<style>
		#lefttab{
			width: 151px;
		}
		
		#lefttab ul{
			line-height: 36px;
			margin-left: 20px;
		}
		
		#lefttab ul li{
			border-right: 2px solid #E5E5E5;
			padding-right: 15px;
			background: url("${ctxPath}/images/border.png") repeat-x scroll center bottom transparent;
		}
		.form li label{
			width: 114px;
		}
		#lefttab ul li.hover{
			border-right: none;
		}
		
		#lefttab ul li.hover a{
			background: url("${ctxPath}/images/blue.png") no-repeat scroll left center transparent;
			color: #2976AA;
		}
	
		#lefttab ul li a {
			background:url("${ctxPath}/images/gray.png") no-repeat scroll left center transparent;
		    display: inline-block;
		    padding-left: 10px;
		    width: 104px;
		    line-height: 36px;
		}
		
	</style>
</head>
<body>
<%@include file="/common/header.jsp" %>

<div class="centerbody centerbox">
<c:if test="${!(empty isLoginView)}">
	<div class="tt">${i18n.activeuser.title}</div>
</c:if>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
	<tr>
		<td width="208" valign="top">
			<div id="lefttab">
				<ul class="tab">
					<li id="li_basic" class="hover"><a href="#nogo">${i18n.usercenter.title.profile}</a></li>
					<li id="li_passwd"><a href="#nogo">${i18n.usercenter.title.modifyPasswd}</a></li>
					
					<c:if test="${empty activePage && !('student' eq current_user.role.id)}">
						<li id="li_trainReport"><a href="trainPlanReport">${i18n.usercenter.title.trainReport}</a></li>
					</c:if>
					
					<li style="height: 400px; background: none;">&nbsp;</li>
				</ul>
			</div>
		</td>
		<td valign="top">
			<div id="div_basic">
			<form id="userForm" action="${ctxPath}/techc/usercenter/modifyUserInfo" method="post" class="form" enctype="multipart/form-data">
				<table width="760px" align="left">
					<tr align="left">
					<td width="230" align="center">
						<input type="hidden" name="id" value="${curUser.id}" />
						<img id="imgh" src="${ctxPath}${head_img}" width="137" height="160"/>
						<br />
						(137px * 160px)
						<br />
						
						<input type="file" name="headImg" id="myfile" value="${ctxPth}${head_img}" style="float: none;"/>
					</td>
					<td width="530" align="left">
						<ul>
							<li><label>${i18n.user.filed.name}</label><input type="text" name="name" value="${curUser.name}" v="notEmpty" maxlength="10" />
								<span class="msg">${i18n.user.filed.name}${i18n.valid.notEmpty}</span><em>*</em></li>
							
							<li>
								<label>${i18n.user.filed.sex}</label>
								<d:selectDomain domain="SEX" name="sex" value="${curUser.sex}"/>
								<span class="msg"/>${i18n.user.filed.sex}${i18n.valid.notEmpty}</span>
							</li>
							<li><label>${i18n.user.filed.birthday}</label><input type="text" name="birthday" value="${curUser.birthday}" onclick="WdatePicker()"  maxlength="15" />
								<span class="msg">${i18n.user.filed.birthday}${i18n.valid.notEmpty}</span></li>
							<li><label>${i18n.user.filed.nickname}</label><input type="text" name="nickName" value="${curUser.nickName}" v="notEmpty" maxlength="25" />
								<span class="msg">${i18n.user.filed.nickname}${i18n.valid.notEmpty}</span><em>*</em></li>
							<li><label>${i18n.user.filed.cardId}</label><input type="text" name="cardId" value="${curUser.cardId}" v="notEmpty"  maxlength="18" />
								<span class="msg">${i18n.user.filed.cardId}${i18n.valid.notEmpty}</span><em>*</em></li>		
														<li><label>${i18n.user.filed.email}</label><input type="text" name="email" value="${curUser.email}" v="notEmpty" maxlength="30"/>
								<span class="msg">${i18n.user.filed.email}${i18n.valid.notEmpty}</span><em>*</em></li>
							<li>
								<label>${i18n.domains.call}</label>
								<d:selectDomain domain="CALLER" name="caller" value="${curUser.caller}"/>
								<span class="msg"/>${i18n.domains.call}${i18n.valid.notEmpty}</span>
							</li>
							<li>
								<label>${i18n.domains.position}</label>
								<d:selectDomain domain="POSITION" name="position" value="${curUser.position}"/>
								<span class="msg"/>${i18n.domains.position}${i18n.valid.notEmpty}</span>
							</li>
							<li>
								<label>${i18n.domains.information}</label>
								<input type="file" name="information" value="${curUser.information}"/>
								<span class="msg">${i18n.domains.information}${i18n.valid.notEmpty}</span>
							</li>
								
							<li><label>${i18n.user.filed.tel}</label><input type="text" name="tel" value="${curUser.tel}"  maxlength="15" />
								<span class="msg">${i18n.user.filed.tel}${i18n.valid.notEmpty}</span></li>
							<li><label>${i18n.user.filed.mobile}</label><input type="text" name="mobile" value="${curUser.mobile}"  maxlength="11"/>
								<span class="msg">${i18n.user.filed.mobile}${i18n.valid.notEmpty}</span></li>
							<li style="display:none;"><label>${i18n.user.filed.company}</label><d:selectDomain domain="COMPANY" name="company" value="${curUser.company}" /></li>
							<%--DELETE PROTYPE
							<li><label>${i18n.user.filed.proType}</label>
								<d:selectDomain domain="PROTYPE" name="proType" value="${curUser.proType}" />
							</li>
							 --%>
						</ul>
					</td>
					</tr>
				</table>
				</form>
				<c:choose>
					<c:when test="${empty isLoginView}">
						<div class="btns" style="padding-left: 160px;">
							<span class="btn">
								<a href="javascript:void(0);" onclick="$('#userForm').submit()">${i18n.button.submit}</a>
							</span>
							<c:if test="${!(empty curUser.information)}">
								<span class="btn">
									<a href="${ctxPath}${curUser.information}" target="_blank">${i18n.usercenter.button.downloadResume}</a>
								</span>
							</c:if>
						</div>		
					</c:when>
					<c:otherwise>
						<div class="btns" style="padding-left: 160px;">
							<span class="btn">
								<a href="javascript:void(0);" onclick="$('#userForm').submit()">${i18n.activeuser.button.next}</a>
							</span>
						</div>
					</c:otherwise>
				</c:choose>	
			</div>
			<div style="width: 760px; display: none;" id="div_passwd">
				<form id="passForm" action="${ctxPath}/techc/usercenter/modifyPwd" method="post" class="form">
					<input type="hidden" name="id" value="${curUser.id}" />
					<ul>
						<li><label>${i18n.user.filed.oldPasswd}</label><input type="text" name="oldpassword" value="" v="notEmpty" maxlength="25" />
							<span class="msg">${i18n.user.filed.oldPasswd}${i18n.valid.notEmpty}</span></li>
						
						<li><label>${i18n.user.filed.newPasswd}</label><input type="text" name="newpassword" value="" v="notEmpty" maxlength="25" />
							<span class="msg">${i18n.user.filed.newPasswd}${i18n.valid.notEmpty}</span></li>
							
						<li><label>${i18n.user.filed.conloginPasswd}</label><input type="text" name="conformpass" value="" v="notEmpty" maxlength="25" />
							<span class="msg">${i18n.user.filed.conloginPasswd}${i18n.valid.notEmpty}</span></li>
						
					</ul>
				</form>
				<c:choose>
					<c:when test="${empty isLoginView}">
						<div class="btns" style="padding-left: 160px;">
							<span class="btn">
								<a href="javascript:void(0);" onclick="$('#passForm').submit()">${i18n.button.submit}</a>
							</span>
						</div>
					</c:when>
					<c:otherwise>
						<div class="btns" style="padding-left: 160px;">
							<span class="btn">
								<a href="javascript:void(0);" onclick="$('#passForm').submit()">${i18n.activeuser.button.active}</a>
							</span>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
		</td>
	</tr>
</table>
	<div class="clearfix_b"></div>
</div>
<%@include file="/common/footer.jsp" %>
</body> 
</html>