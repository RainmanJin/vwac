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
			location.href = "votekey?subId=${mwVotekey.NSubId==null ? subId : mwVotekey.NSubId}";
		}, function(d) {
			alert(d.data)
		});
		
	});
	
	$(function(){
		var a=$("#se").find("option[value^='${mwVotekey.CRule}']").attr("selected",true);;
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
				<form id="dbForm" action="votekeysave" method="post" class="form">
					<input type="hidden" name="id" value="${mwVotekey.id}" />
					<input type="hidden" name="NSubId" value="${mwVotekey.NSubId==null ? subId : mwVotekey.NSubId}" />
					<ul style="width: 800px">
						<li style="height: 50px;"><label>选项名称</label> 
							<textarea style="width: 350px; height: 50px; resize: none" name="CKeyTitle"
								params="notEmpty">${mwVotekey.CKeyTitle}</textarea> 
							<span class="msg">选项名称${i18n.valid.notEmpty}</span><em>*</em>
						</li>
						<li><label>排序</label> 
							<input style="width: 150px;" type="text" name="NOrderId" value="${mwVotekey.NOrderId}" maxlength="255" /> 
						</li>
						<li><label>分值</label> 
							<input style="width: 150px;" type="text" name="NScore" value="${mwVotekey.NScore}" maxlength="255" /> 
						</li>
						<li><label>验证规则</label>
							<select name="CRule" style="width:150px;" id="se">
								<option value="">不限制</option>
								<option value="(\(\d{3}\)|\d{3}-)?\d{8}">电话号码</option>
								<option value="\d{17}[\d|X]|\d{15}">身份证号码</option>
								<option value="\d{6}">邮政编码</option>
								<option value="\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*">电子邮件</option>
								<option value="http(s)?://([\w-]+\.)+[\w-]+(/[\w- ./?%&amp;=]*)?">网址</option>
								<option value="*">可选填写</option>
								<option value="^13[0-3|5-9]\d{8}$">手机号码</option>
								<option value="^[\s\S]{1,20}$">限制字符1-20个</option>
								<option value="^[\s\S]{1,200}$">限制字符1-200个</option>
								<option value="^[1-9][0-9]?$">大于0小于100</option>
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