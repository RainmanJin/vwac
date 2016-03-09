<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@include file="/common/head.jsp"%>
<style>
.rowAns {
	height: 32px;
	display: block;
}

.rowAns span {
	display: inline-block;
	border: 1px solid #dfdfdf;
	float: left;
	height: 30px;
	line-height: 30px;
	text-align: center;
}

.rowAns .ans1 {
	width: 300px;
}

.rowAns .ans2 {
	width: 100px;
}

#AnsDIV {
	display: inline-block;
}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		initJsonForm("#dbForm", function(d) {
			alert(d.data);
			location.href = "index";
		}, function(d) {
			alert(d.data);
		});

		//生成checkbox，并选中已经选择的项
		$("#checkbox").html("${setchexkbox}");
	});

	jQuery(function($) {
		$("input[name='key']:checkbox").click(function() {
			var ids = '';
			var flag = 0;
			$("#pgfield").attr("value", ids);
			$("input[name='key']:checkbox").each(function() {
				if (true == $(this).attr("checked")) {
					ids += $(this).attr('value') + ',';
					flag += 1;
				}
			});
			if (0 < flag) {
				$("#pgfield").attr("value", ids);
				return true;
			} 
		});
	});
</script>
</head>
<body>
	<%@include file="/common/header.jsp"%>
	<div class="centerbody centerbox">
		<div id="contentwrapper">
			<div id="content_right">
				<div class="tt">
					<c:choose>
						<c:when test="${type eq '0'}">${dto.CName}-字段配置-公路</c:when>
						<c:when test="${type eq '1'}">${dto.CName}-字段配置-运动</c:when>
						<c:otherwise>${dto.CName}-字段配置-越野</c:otherwise>
					</c:choose>
				</div>
				<form id="dbForm" action="changdisave" method="post" class="treeform">
					<input type="hidden" id="mwTestdriverID" name="id" value="${dto.id}" /> 
					<input type="hidden" id="selectType" name="selectType" value="${type}" /> 
					<input type="hidden" id="pgfield" name="allpgfield" /><!-- 最新的选中记录 -->
					<div id="checkbox"></div>
				</form>
				<div class="btns" style="padding-left: 100px;">
					<span class="btn"> <a href="javascript:void(0);" onclick="$('#dbForm').submit();">${i18n.button.submit}</a>
					</span> <span class="btn"> <a href="javascript:history.back();">${i18n.button.back}</a>
					</span>
				</div>
			</div>
		</div>
		<br /> <br />
		<%@include file="/common/leftmenu.jsp"%>
		<div class="clearfix_b"></div>
	</div>
	<%@include file="/common/footer.jsp"%>
</body>
</html>