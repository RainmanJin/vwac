<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/platform/include/top.jsp"%>

<input type="text" id="ac3">
<script>
	$(document).ready(function(){
		$.get("mail.jsp", function(data){
			var d = eval(data);
			$("#ac3").autocomplete({
				data: d,
				showResult: function(value, data) {
					return value;
				},
				onItemSelect: function(item) {
				    alert(item);
				}
			});
		});
	});
</script>

<%@ include file="/platform/include/footer.jsp" %>