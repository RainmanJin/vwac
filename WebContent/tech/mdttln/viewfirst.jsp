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
	});
	</script>
</head>
<body>
<%@include file="/common/header.jsp" %>

<div class="centerbody centerbox">
	<div id="contentwrapper">
		<div id="content_right">
				<div class="tt">${i18n.mdttln.title}</div>
				<form id="lnForm" action="savefirst" method="post">
				<input type="hidden" name="id" value="${dto.id}" />
				<ul class="form">
						<li>
							<label>${i18n.mdttln.tasktitle}</label>
							<input type="text" id="title" name="title" value="${dto.title}" v="notEmpty" maxlength="450" style="width: 450px" />
							<span class="msg">${i18n.valid.notEmpty}</span>
						</li>
						<li>
							<label>${i18n.mdttln.taskstyle}</label>
							<d:selectDomain  domain="MDTT_LN_STATUS" name="style" value="${dto.style}"/>
							<span class="msg">${i18n.valid.notEmpty}</span>
						</li>
						<%--<li>
						<label>${i18n.mdttln.systype}</label>
						<select name="type" >
						       <option value="">所有</option>
									<c:forEach var="mt" items="${metypeList}" varStatus="s">
										<option value="${mt.typeCode}" ${dto.type == mt.typeCode?'selected':''}>${mt.typeName}</option>
									</c:forEach>
					   </select>
						</li>
						--%><li>
							<label>${i18n.mdttln.taskstart}</label>
							<input type="text" name="starttime" value="${dto.starttime}" onclick="WdatePicker()"  maxlength="15" />
						</li>
						<li>
							<label>${i18n.mdttln.taskend}</label>
							<input type="text" name="endtime" value="${dto.endtime}" onclick="WdatePicker()"  maxlength="15" />
						</li>
				</ul>
				</form>
			<div class="btns" style="padding-left: 160px;">
				<span class="btn">
					<a href="javascript:void(0)" onclick="$('#lnForm').submit()">${i18n.mdttln.button.savenext}</a>
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