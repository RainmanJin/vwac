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
			location.href = "votesubject?sysId=${mwVotesubject.NSysId==null ? sysId : mwVotesubject.NSysId}";
		}, function(d) {
			alert(d.data)
		});
		
	});
	
	$(function(){
		var a=$("#sent").find("option[value^='${mwVotesubject.NType}']").attr("selected",true);
		var a=$("#sepa").find("option[value^='${mwVotesubject.parentid}']").attr("selected",true);
	})

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
				<form id="dbForm" action="votesubjectsave" method="post" class="form">
					<input type="hidden" name="id" value="${mwVotesubject.id}" />
					<input type="hidden" name="NSysId" value="${mwVotesubject.NSysId==null ? sysId : mwVotesubject.NSysId}" />
					<ul style="width: 800px">
						<li style="height: 50px;"><label>题目名称</label> 
							<textarea style="width: 350px; height: 50px; resize: none" name="CSubTitle"
								params="v=notEmpty">${mwVotesubject.CSubTitle}</textarea>
							<span class="msg">题目名称${i18n.valid.notEmpty}</span><em>*</em>
						</li>
						<li><label>排序</label> 
							<input style="width: 150px;" type="text" name="NOrderId" value="${mwVotesubject.NOrderId}" maxlength="255" /> 
						</li>
						<li ><label>是否必答</label>
							<select name="NNeed" style="width:150px;">
								<option <c:if test="${mwVotesubject.NNeed==0}">selected="selected"</c:if> value="0">否</option>
								<option <c:if test="${mwVotesubject.NNeed==1}">selected="selected"</c:if> value="1">是</option>
							</select>
						</li>
						<li ><label>调查单元</label>
							<select name="NType" style="width: 150px;" id="sent">
								<option value="1">满意度调查</option>
								<option value="2">意见框</option>
							</select>
						</li>
						<li ><label>所属类别</label>
							<select name="parentid" style="width: 180px;" id="sepa">
								<option value="0">一级目录</option>
								<option value="1">您对此次培训组织作何评价 How satisfied are you with….</option>
								<option value="7">您对此次培训内容作何评价 How satisfied are you with….</option>
								<option value="12">您对课程内容有何建议？ How have you experienced the content?</option>
								<option value="17">您对此次培训讲师作何评价How satisfied are you with….</option>
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