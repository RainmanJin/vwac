<%@include file="/common/context.jsp" %>
<script type="text/javascript">
	initJsonForm("#myForm1",function(d){
		location.reload();
	},function(d){
		alert(d.data)
	});
</script>
<form id="myForm1" action="version" method="post" class="form">
				<input type="hidden" name="id" value="${pkg.id}" />
					<ul>
						<li>
							<label>${i18n.mdttstat.pkgName}</label>
							<input type="file" name="filePath" value="${pkg.filePath}"/>
							<span class="msg">${i18n.resourse.name}${i18n.valid.notEmpty}</span>
						</li>
					</ul>
			</form>
