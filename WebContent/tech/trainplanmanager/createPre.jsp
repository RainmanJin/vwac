<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<script type="text/javascript">
initJsonForm("#myForm",function(d){
	location.href="view?year=" + $("#year_").val() + "&brand=" + $("#brand_").val();
},function(d){
	alert(d.data)
});
</script>
<form id="myForm" action="create" method="post" class="form">
		<ul>
			<li>
				<label>${i18n.trainplan.filed.yearValue}</label>
				<select id="year_" name="year">
					<%
						Integer year = (Integer) request.getAttribute("year");
					
						for(int yyyy=2011; yyyy<2031; yyyy++)
						{
					%>
					<option <%if(year == yyyy){%>selected="selected" <%}%>value="<%=yyyy%>"><%=yyyy%>${i18n.year.text}</option>
					<%
						}
					%>
				</select>
			</li>
			<li>
				<label>${i18n.trainplan.filed.brand}</label>
				<d:selectDomain uid="brand_" domain="BRAND" name="brand" />
			</li>
		</ul>
</form>