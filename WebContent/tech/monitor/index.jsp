<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@include file="/common/head.jsp" %>
	<script type="text/javascript">
		function gc()
		{
		    //Çå³ý»º´æ
			ajaxGet("/mw/clearcache.aspx");

			ajaxGet("gc",{},function(d){
				if(d.success)
				{
					location.reload();
				}
				else
				{
					alert(d.data);
				}
			},"json");			
		}
	</script>
</head>
<body>
<%@include file="/common/header.jsp" %>

<div class="centerbody centerbox">
	<div id="contentwrapper">
		<div id="content_right">
			<div class="tt">
				${i18n.monitor.title}
			</div>
			
			<div style="font-size: 14px; padding: 20px;">
					${i18n.monitor.remark}
			</div>
			
			<table width="60%" align="center" cellpadding="0" cellspacing="1" bgcolor="#dfdfdf" style="text-align: center;">
				<tr>
					<th style="font-size: 14px; font-weight: bold; background: #f0f0f0;" width="160">${i18n.monitor.mem.total}</th>
					<td style="font-size: 14px; font-weight: bold; background: #fff; color: blue;">${totalMemory}MB</td>
				</tr>
				<tr>
					<th style="font-size: 14px; font-weight: bold; background: #f0f0f0;">${i18n.monitor.mem.used}</th>
					<td style="font-size: 14px; font-weight: bold; background: #fff; color: blue;">${usedMemory}MB</td>
				</tr>
				<tr>
					<th style="font-size: 14px; font-weight: bold; background: #f0f0f0;">${i18n.monitor.mem.free}</th>
					<td style="font-size: 14px; font-weight: bold; background: #fff; color: blue;">${freeMemory}MB</td>
				</tr>
			</table>
			
			<br />
			<br />
			
			<div class="btns">
				<!-- <span class="graybtn">
					<a href="#nogo" onclick="del()">${i18n.button.del}</a>
				</span>
				 -->
				<span class="btn">
					<a href="javascript:gc();">${i18n.monitor.clean}</a>
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