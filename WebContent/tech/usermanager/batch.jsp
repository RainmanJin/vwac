<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@include file="/common/head.jsp" %>
	<script type="text/javascript" src="${jqueryPath}/jquery.ajaxfileupload.js"></script>
	<script type="text/javascript">
		initJsonForm("#userForm",function(d){
			ajaxLoad("${ctxPath}/techc/usermanager/listByPrefix?prefix=" + $("#prefix").val() + "&first=" + d.data, "#results",{});
			alert("${i18n.commit.success}");
		},function(d){
			alert(d.data)
		});
		
		function upload1(){
			$.fn.jmodal({
	            title: '${i18n.user.filed.uploaduser}',
	            width:400,
	            content: function(body) {
	                body.html('loading...');
	                body.load('upload');
	            },
	            buttonText: { ok: '${i18n.button.ok}', cancel: '${i18n.button.cancel}' },
		            okEvent: function(data, args) {
		            	$("#uploadForm").submit();
			        }
	        });		
		}
	</script>
</head>
<body>
<%@include file="/common/header.jsp" %>

<div class="centerbody centerbox">
	<div id="contentwrapper">
		<div id="content_right">
			<div class="tt">${i18n.user.batch.title}</div>
			
			<form id="userForm" action="batchSave" method="post" class="form">
					<ul>
						<li><label>${i18n.user.batch.count}</label><input type="text" name="count" v="notEmpty num" maxlength="4" />
							<span class="msg">${i18n.user.batch.count}${i18n.valid.notEmpty}</span></li>
						<li><label>${i18n.user.batch.prefix}</label><input id="prefix" type="text" name="prefix" v="notEmpty" maxlength="6" />
							<span class="msg">${i18n.user.batch.prefix}${i18n.valid.notEmpty}</span></li>
						<li><label>${i18n.user.filed.password}</label><input type="text" name="password" maxlength="10" />
							<span class="info">&nbsp;&nbsp;${i18n.user.batch.emptyPasswd}</span></li>
						<li><label>${i18n.user.filed.company}</label><d:selectDomain domain="COMPANY" name="company" /></li>
						<%-- DELETE PROTYPE
						<li><label>${i18n.user.filed.proType}</label>
							<d:selectDomain domain="PROTYPE" name="proType"/>
						</li>
						--%>
						<li><label>${i18n.user.filed.roleName}</label><d:selectDomain domain="ROLE" name="role" value="student" /></li>
						<li><label>${i18n.user.filed.status}</label><d:selectDomain domain="USERSTATUS" name="status" value="3" /></li>
					</ul>
			</form>
			
			<div class="btns" style="padding-left: 160px;">
				<span class="btn">
					<a href="#nogo" onclick="$('#userForm').submit()">${i18n.button.submit}</a>
				</span>
				<span class="btn">
					<a href="download">${i18n.user.filed.downloaduser}</a>
				</span>
				<span class="btn">
					<a href="#nogo" onclick="upload1();">${i18n.user.filed.uploaduser}</a>
				</span>
			</div>
			<div id="results">
			
			</div>		
		</div>
	</div>
	
	<%@include file="/common/leftmenu.jsp" %>
	<div class="clearfix_b"></div>
</div>
<%@include file="/common/footer.jsp" %>
</body> 
</html>