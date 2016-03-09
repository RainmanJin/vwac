<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@include file="/common/head.jsp"%>
<script type="text/javascript">
	$(document).ready(function() {
		initJsonForm("#dbForm", function(d) {
			alert(d.data);
			location.href = "voteunit?voteId=${mwVoteunit.NSysId==null ? voteId:mwVoteunit.NSysId}";
		}, function(d) {
			alert(d.data)
		});
	});

	function courseAjax() {
		var brand = $('#brand').val();
		var proType = $('#proType').val();

		$.fn
				.jmodal({
					title : '${i18n.techdb.filed.courseName}',
					width : 800,
					content : function(body) {
						body.html('loading...');
						body
								.load('${ctxPath}/techc/commonresource/listTrainCourseModelByProTypeAndBrand?brand='
										+ brand + "&proType=" + proType);
					},
					buttonText : {
						cancel : '${i18n.button.cancel}'
					}
				});
	}

</script>
</head>
<body>
	<%@include file="/common/header.jsp"%>

	<div class="centerbody centerbox">
		<div id="contentwrapper">
			<div id="content_right">
				<form id="dbForm" action=voteunitsave method="post" class="form">
					<input type="hidden" name="id" value="${mwVoteunit.id}" />
					<input type="hidden" name="NSysId" value="${mwVoteunit.NSysId==null ? voteId:mwVoteunit.NSysId}" />
					<ul style="width: 800px">
						<li style="height: 50px;"><label>调查单元名称</label> 
							<textarea style="width: 350px; height: 50px; resize: none" name="CSubTitle"
								params="v=notEmpty">${mwVoteunit.CSubTitle}</textarea>
							<span class="msg">调查单元名称${i18n.valid.notEmpty}</span><em>*</em>
						</li>
						<li style="height: 50px;"><label>调查类型</label>
							<select name="NType" style="width:152px;">
								<option <c:if test="${mwVoteunit.NType==2}">selected="selected"</c:if> value="2">单选</option>
								<option	<c:if test="${mwVoteunit.NType==3}">selected="selected"</c:if> value="3">多选</option>
								<option <c:if test="${mwVoteunit.NType==5}">selected="selected"</c:if> value="5">多行文本</option>
							</select>
						</li>	
					</ul>
				</form>
				<div class="btns" style="padding-left: 300px;">
					<span class="btn"> <a href="javascript:void(0);" onclick="$('#dbForm').submit();">${i18n.button.submit}</a>
					</span> <span class="btn"> <a href="javascript:history.back();">${i18n.button.back}</a>
					</span>
				</div>

			</div>
		</div>

		<%@include file="/common/leftmenu.jsp"%>
		<div class="clearfix_b"></div>
	</div>
	<%@include file="/common/footer.jsp"%>
</body>
</html>