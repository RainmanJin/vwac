<%@page import="java.util.Set"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>

<%
	Set set = (Set) request.getAttribute("emptyDays");
%>
<div>
	<%
		if(set.contains("1"))
		{
	%>
		<input name="wday" value="1" type="checkbox" class="native" checked="checked" /> ${i18n.week.Monday} &nbsp;&nbsp;&nbsp;
	<%
		}
	%>
	
	<%
		if(set.contains("2"))
		{
	%>
		<input name="wday" value="2" type="checkbox" class="native" checked="checked" /> ${i18n.week.Tuesday} &nbsp;&nbsp;&nbsp;
	<%
		}
	%>
	
	<%
		if(set.contains("3"))
		{
	%>
		<input name="wday" value="3" type="checkbox" class="native" checked="checked" /> ${i18n.week.Wednesday} &nbsp;&nbsp;&nbsp;
	<%
		}
	%>
	
	<%
		if(set.contains("4"))
		{
	%>
		<input name="wday" value="4" type="checkbox" class="native" checked="checked" /> ${i18n.week.Thursday} &nbsp;&nbsp;&nbsp;
	<%
		}
	%>
	
	<%
		if(set.contains("5"))
		{
	%>
		<input name="wday" value="5" type="checkbox" class="native" checked="checked" /> ${i18n.week.Friday} &nbsp;&nbsp;&nbsp;
	<%
		}
	%>
	
	<%
		if(set.contains("6"))
		{
	%>
		<input name="wday" value="6" type="checkbox" class="native" checked="checked" /> ${i18n.week.Saturday} &nbsp;&nbsp;&nbsp;
	<%
		}
	%>
	
	<%
		if(set.contains("7"))
		{
	%>
		<input name="wday" value="7" type="checkbox" class="native" checked="checked" /> ${i18n.week.Sunday}
	<%
		}
	%>
</div>
<%--
<div>
	<label>${i18n.trainplan.filed.remark}</label>
	<textarea id="_dRemark"></textarea>
</div>
 --%>