<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/context.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- 调查结果图形报表 -->
<%@include file="/common/head.jsp"%>
<script type="text/javascript">
	function SetProgress(id) {
		//alert(id);
		var val = $("#" + id + " > div > span").html();
		$("#" + id + " > div").css("width", val + "%"); //控制#loading div宽度 
		$("#" + id + " > div").html(val + "%"); //显示百分比 
	}
</script>

<style>
.rate {
	width: 350px;
	height: 15px;
	border: 1px solid #ccc;
	text-shadow: 0.5px 0.5px 1px #fff;
	border-radius: 3px;
}

.rate div {
	width: 0px;
	height: 14px;
	background: #2696df;
	text-shadow: 0.5px 0.5px 1px #fff;
	border-radius: 3px;
	color: #fff;
	text-align: center;
	font-family: Tahoma;
	font-size: 14px;
	line-height: 13px;
}
</style>
</head>
<body>
	<%@include file="/common/header.jsp"%>
	<div class="centerbody centerbox">
		<div id="contentwrapper">
			<div id="content_right">
				<div class="tt">${i18n.appname.mw.vwsurveystatvote}</div>
				<div>当前调查问卷有效样本数：${userfullCount}</div>
				<ec:table action="statreport" method="get" items="SubDatalist" var="var" view="ecan" width="99%" imagePath="${coreImgPath}/table/*.gif" showExports="false">
					<ec:row>
						<ec:column title="调查名称" property="sub" width="90%">
						</ec:column>
						<ec:column title="所占比例" property="rate" width="10%">
							<c:choose>
								<c:when test="${empty var.rate}"></c:when>
								<c:otherwise>
									<div id="${var.id}" class="rate">
										<div>
											<span>${var.rate}</span>
										</div>
									</div>
									<script>
										SetProgress("${var.id}");
									</script>
								</c:otherwise>
							</c:choose>
						</ec:column>
					</ec:row>
				</ec:table>
			</div>
		</div>
		<%@include file="/common/leftmenu.jsp"%>
		<div class="clearfix_b"></div>
	</div>
	<%@include file="/common/footer.jsp"%>
</body>
</html>