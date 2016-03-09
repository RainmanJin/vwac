<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@include file="/common/head.jsp" %>
	
	<style>
		.rowAns {
			height: 32px;
			display: block;
		}
		
		.rowAns span {
			display: inline-block;
			border: 1px solid #dfdfdf;
			float: left;
			height: 30px;
			line-height: 30px;
			text-align: center;
		}
		
		.rowAns a{
			text-decoration:underline;
			color: blue;
			height: 25px; line-height: 25px;
		}
		
		#AnsDIV{
			display: inline-block;
		}

	</style>
	
	<script type="text/javascript">
	initJsonForm("#myFormInfo",function(d){
		location.href="index";
	},function(d){
		alert(d.data)
	});
	</script>
</head>
<body>
<%@include file="/common/header.jsp" %>

<div class="centerbody centerbox">
	<div id="contentwrapper">
		<div id="content_right">
			<div class="tt">
				<%-- ${i18n.resourse.title} --%>
			</div>
			<c:choose>
				<c:when test="${not empty appPlatForm.id}">
					<form id="myFormInfo" action="editres" method="post" class="form">
				</c:when>
				<c:otherwise>
					<form id="myFormInfo" action="save" method="post" class="form">
				</c:otherwise>
			</c:choose>
				<input type="hidden" name="id" value="${appPlatForm.id}" />
					<ul>
						<li>
							<label>${i18n.mdttstat.pkgName}</label>
							<input type="text" name="name" value="${appPlatForm.name}" v="notEmpty" maxlength="100" />
							<span class="msg">${i18n.resourse.name}${i18n.valid.notEmpty}</span><em>*</em>
						</li>
						<li>
							<label>${i18n.app.Thumbnails}</label>
							<input type="file" name="iconUrl" value="${appPlatForm.iconUrl}" />
							<span class="msg">${i18n.resourse.name}${i18n.valid.notEmpty}</span>
						</li>
						<li>
							<label>${i18n.app.Mark}</label>
							<input type="text" name="pkgid" value="${appPlatForm.pkgid}"/><%-- &nbsp; <img src="${ctxPath}${mwarticle.filePath}" width="138" height="160" alt="headImage" /> --%>
						</li>
						<li>
							<label>${i18n.mdttpkg.filed.version}</label>
							<input type="text" name="version" value="${appPlatForm.version}" maxlength="100" />
							<span class="msg">${i18n.resourse.name}${i18n.valid.notEmpty}</span>
						</li>
						<li>
								<label>
									${i18n.techdb.filed.brand}
								</label>
								<d:selectDomain domain="BRAND" uid="brand" name="brand"
									value="${appPlatForm.brand}" />
							</li>
							<li>
								<label>
									${i18n.techdb.filed.protype}
								</label>
								<d:selectDomain domain="PROTYPE" uid="proType" name="proType"
									value="${appPlatForm.proType}" />
							</li>
							<li>
								<label>
									${i18n.techdb.filed.status}
								</label>
								<d:selectDomain domain="STATUS" uid="status" name="status"
									value="${appPlatForm.status}" />
							</li>
							<li>
							<label>${i18n.app.AndroidCourseApk}</label>
							<input type="file" name="apkurl" value="${appPlatForm.apkurl}" />
							<span class="msg">${i18n.resourse.name}${i18n.valid.notEmpty}</span>
						</li>
						<li>
							<label>${i18n.app.IOSCourseApk}</label>
							<input type="file" name="iosurl" value="${appPlatForm.iosurl}" />
							<span class="msg">${i18n.resourse.name}${i18n.valid.notEmpty}</span>
						</li>
						<li>
							<label>${i18n.course.filed.remark}</label>
							<textarea rows="4" cols="4" name="remark">${appPlatForm.remark}</textarea>
							<span class="msg">${i18n.resourse.remark}${i18n.valid.notEmpty}</span>
						</li>
					</ul>
			</form>
				<div class="btns" style="margin-left: 160px;">
							<span class="btn">
								<a href="javascript:void(0)" onclick="$('#myFormInfo').submit()">${i18n.button.submit}</a>
							</span>
							<span class="btn">
								<a href="javascript:history.back()">${i18n.button.back}</a>
							</span>		
				</div>
		</div>
	</div>
	
	<%@include file="/common/leftmenu.jsp" %>
	<div class="clearfix_b"></div>
</div>
<%@include file="/common/footer.jsp" %>
</body> 
</html>