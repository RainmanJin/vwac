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
	
	function chgStatus(s,id)
	{
		ajaxGet("status",{"id":id,"status":s},function(d){
			if(d.success)
				location.reload();
			else
				alert(d.data);
		},"json");
	}
	</script>
</head>
<body>
<%@include file="/common/header.jsp" %>

<div class="centerbody centerbox">
	<div id="contentwrapper">
		<div id="content_right">
				<div class="tt">${i18n.mdttqa.title}</div>
				<form id="myForm" action="save" method="post">
				<input type="hidden" name="id" value="${dto.id}" />
				<input type="hidden" name="brand" value="VW"/>
				<ul class="form">
					<li>
						<label>${i18n.mdttqa.qustion}</label>
						<input type="text" name="question" value="${dto.question}" v="notEmpty" maxlength="450" style="width: 450px" />
						<span class="msg">${i18n.valid.notEmpty}</span>
					</li>
					<%-- 
					<li>
						<label>${i18n.scormpkg.filed.brand}</label>
						<d:selectDomain domain="BRAND" name="brand" value="${dto.brand}" />
					</li>
					--%>
					<li><label>${i18n.scormpkg.filed.proType}</label>
						<d:selectDomain domain="PROTYPE" name="proType" value="${dto.proType}" />
					</li>
					<li style="height: 310px;"><label>${i18n.mdttqa.ans}</label>
						<textarea name="ans" v="notEmpty" style="width: 450px; height: 300px;">${dto.ans}</textarea>
						<span class="msg">${i18n.valid.notEmpty}</span>
					</li>
					<li>
						<label>${i18n.scormpkg.filed.status}</label>
						<input type="text" value="<d:viewDomain domain="MDTT_PKG_STATUS" value="${dto.status}" />" readonly="readonly" class="gray"/>
					</li>
					<li>
						<label>${i18n.mdttqa.updatetime}</label>
						<input type="text" value="<ecan:dateFormart value="${dto.lastUpdateTime}" formart="yyyy-MM-dd HH:mm:ss" />" readonly="readonly" class="gray"/>
					</li>
					<li>
						<label>${i18n.mdttqa.editor}</label>
						<input type="text" value="<ecan:viewDto property="name" dtoName="EcanUser" id="${dto.userID}" />" readonly="readonly" class="gray"/>
					</li>
					<c:if test="${not empty dto.qusUserID}">
					<li>
						<label>${i18n.mdttqa.submitor}</label>
						<input type="text" value="<ecan:viewDto property="name" dtoName="EcanUser" id="${dto.qusUserID}" />" readonly="readonly" class="gray"/>
					</li>
					</c:if>
				</ul>
				</form>
			
			<div class="btns" style="padding-left: 160px;">
				<span class="btn">
					<a href="javascript:void(0)" onclick="$('#myForm').submit()">${i18n.button.save}</a>
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