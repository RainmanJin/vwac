<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@include file="/common/head.jsp" %>
	
	<style>
		/* .form li label{
			width: 100px;
		} */
		.lineSeperater{
			border-top: 1px dotted #dfdfdf;
			padding: 20px 0px;
		}
		.form li label{
			width: 140px;
		}
		
		.rowTitle{
			border: 1px solid #dfdfdf;background: #f0f0f0;color: #2274AC; font-size: 14px; font-weight: bold;
			height: 25px; line-height: 25px;
		}
		.eXtremeTable .tableRegion{
			margin: 0px;
		}
	</style>
	<script type="text/javascript">
	$(document).ready(function(){
		initJsonForm("#lnForm",function(d){
			location.href="index";
		},function(d){
			alert(d.data);
		});
		
		});

	</script>
</head>
<body>
<%@include file="/common/header.jsp" %>

<div class="centerbody centerbox">
	<div id="contentwrapper">
		<div id="content_right">
				<div class="tt">${i18n.mdttln.title}</div>
				<form id="lnForm" action="save" method="post">
				<input type="hidden" name="id" value="" />
				<ul class="form">
						<li>
							<label>${i18n.mdttln.tasktitle}</label>
							<input type="text" id="title" name="title" value="" v="notEmpty" maxlength="450" style="width: 450px" />
							<span class="msg">${i18n.valid.notEmpty}</span>
							<em>*</em>
						</li>
						<li>
							<label>${i18n.mdttln.taskstyle}</label>
							<d:selectDomain  domain="MDTT_LN_STATUS" name="style" value=""/>
							<span class="msg">${i18n.valid.notEmpty}</span>
						</li>
						<%--<li>
						<label>${i18n.mdttln.systype}</label>
						<select name="type" >
						       <option value="">所有</option>
									<c:forEach var="mt" items="${metypeList}" varStatus="s">
										<option value="${mt.typeCode}">${mt.typeName}</option>
									</c:forEach>
					   </select>
						</li>--%>
						<li>
							<label>${i18n.mdttln.taskstart}</label>
							<input type="text" name="starttime" value="" onclick="WdatePicker()"  maxlength="15" />
							<span class="msg">${i18n.valid.notEmpty}</span>
							<em>*</em>
						</li>
						<li>
							<label>${i18n.mdttln.taskend}</label>
							<input type="text" name="endtime" value="" onclick="WdatePicker()"  maxlength="15" />
							<span class="msg">${i18n.valid.notEmpty}</span>
							<em>*</em>
						</li>
				</ul>
				</form>
			<div class="btns" style="padding-left: 160px;">
				<span class="btn">
					<a href="javascript:void(0)" onclick="$('#lnForm').submit()">${i18n.button.save}</a>
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