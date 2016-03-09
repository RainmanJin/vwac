<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@include file="/common/head.jsp" %>
	
	<style>
		.form li label{
			width: 100px;
		}
	</style>
	<script type="text/javascript">
	$(document).ready(function(){
		initJsonForm("#myForm",function(d){
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
			<div class="tt">${i18n.scormpkg.edit}</div>
			<form id="myForm" action="save" method="post" class="form" enctype="multipart/form-data">
				<input type="hidden" name="id" value="${dto.id}" />
				<input type="hidden" name="url" value="${dto.url}" />
				<input type="hidden"" name="preview" value="${dto.preview}" />
				
				<table cellpadding="0" cellspacing="0" border="0">
					<tr>
						<td>
							<b>${i18n.scormpkg.filed.preview}</b><br />
							<img src="${ctxPath}${dto.preview}" width="240" height="160" border="0" /><br /><br />
							<b style="line-height: 25px;">${i18n.oper.upload}&nbsp;${i18n.scormpkg.filed.preview}</b><br />
								<input type="file" name="file" />
						</td>
						<td>
							<ul>
								<li>
									<label>${i18n.scormpkg.filed.name}</label>
									<input type="text" name="name" value="${dto.name}" v="notEmpty" maxlength="100" style="width: 220px"/>
									<span class="msg">${i18n.valid.notEmpty}</span>
								</li>
								<li>
									<label>${i18n.scormpkg.filed.brand}</label>
									<d:selectDomain domain="BRAND" name="brand" value="${dto.brand}" />
								</li>
								<li><label>${i18n.scormpkg.filed.proType}</label>
									<d:selectDomain domain="PROTYPE" name="proType" value="${dto.proType}" />
								</li>
								<li>
									<label>${i18n.scormpkg.filed.contentType}</label>
									<d:selectDomain domain="SCORMTYPE" name="contentType" value="${dto.contentType}" />
								</li>
								<li>
									<label>${i18n.scormpkg.filed.status}</label>
									<d:selectDomain domain="STATUS" name="status" value="${dto.status}" />
								</li>
								<li>
									<label>${i18n.scormpkg.filed.remark}</label>
									<textarea name="remark" rows="4" cols="40" style="width: 300px;">${dto.remark}</textarea>
								</li>
							</ul>
						</td>
					</tr>
				</table>
			</form>
			
			
			<div class="btns" style="padding-left: 160px;">
				<span class="btn">
					<a href="javascript:void(0)" onclick="$('#myForm').submit()">${i18n.button.submit}</a>
				</span>
				<span class="btn">
					<a target="_blank" href="${ctxPath}${dto.url}">${i18n.button.download}</a>
				</span>
				<span class="btn">
					<a href="index">${i18n.button.back}</a>
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