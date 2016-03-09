<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
		<%@include file="/common/head.jsp"%>
		<style>
			.table_border td{border-top:1px #DDD solid;border-right:1px #DDD solid;}
		</style>
		<script>
			function addTestting(id,courseInstanceId)
			{
				var url = "";
				location.href = "${ctxPath}/techc/elearning/testting?id="+id+"&courseInstanceId="+courseInstanceId;
			}
			function publish(courseId)
			{
				$.fn.jmodal({
		            title: '${i18n.traincourse.filed.publish}',
		            width:600,
		            content: function(body) {
		                body.html('loading...');
		                body.load('listAttchment?courseId='+courseId);
		            },
		            buttonText: {cancel: '${i18n.button.cancel}' }
		        });
			}
	
		</script>
	</head>
	<body>
		<%@include file="/common/header.jsp"%>

		<div class="centerbody centerbox">
			<div id="contentwrapper">
				<table width="650px;">
					<tr>
						<td width="30%">
							<img src="${ctxPath}${courseInstance.preview}" width="230px;" height="160px;"/>
						</td>
						<td align="center" width="70%">
							<span style="font-size: 18px; font-weight:bold">${courseInstance.name}</span><br/>
							<span style="font-size: 16px;">${i18n.oper.correctRate}:</span><br/>
							<span style="font-size: 20px;font-weight: bold;">${correctRate}%</span>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="left">
							&nbsp;&nbsp;<span style="font-size: 16px;">${i18n.oper.finish}：   ${studentstatus.testCount}</span><br/>
							&nbsp;&nbsp;<span style="font-size: 16px;">${i18n.oper.avg}：${studentstatus.avgPoint}</span><br/>
							&nbsp;&nbsp;<span style="font-size: 16px;">${i18n.oper.highpoint}：${studentstatus.highPoint}</span><br/>
						</td>
					</tr>
				</table>
				
				<span class="btn">
					<a href="index">${i18n.button.cancel}</a>
				</span>
			</div>
	<%@include file="/common/leftmenu.jsp" %>
	<div class="clearfix_b"></div>
</div>
<%@include file="/common/footer.jsp" %>
</body> 
</html>