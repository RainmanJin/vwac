<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/context.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- 调查管理 -->
<%@include file="/common/head.jsp"%>
<script type="text/javascript">
	function del(id) {
		if (confirm("${i18n.delete.comfirm}")) {
			ajaxGet("${ctxPath}/techc/vwsurvey/del", {
				"id" : id
			}, function(d) {
				if (d.success) {
					location.reload();
				} else {
					alert(d.data);
				}
			}, "json");
		}
	}

	function copy(id) {
		if (confirm("确定复制该项目吗？")) {
			ajaxGet("${ctxPath}/techc/vwsurvey/copy", {
				"id" : id
			}, function(d) {
				if (d.success) {
					alert("复制项目成功！");
					location.reload();
				} else {
					alert(d.data);
				}
			}, "json");
		}
	}

	function build(id, NSysId) {
		if (confirm("确定要生成问卷吗？")) {
			ajaxGet("${ctxPath}/techc/vwsurvey/build", {
				"id" : id,
				"NSysId" : NSysId
			}, function(d) {
				if (d.success) {
					alert("生成问卷成功！");
					location.reload();
				} else {
					alert(d.data);
				}
			}, "json");
		}
	}

	//发布问卷
	function pushreport(id) {
		$.fn.jmodal({
			title : '发布问卷',
			width : 800,
			height : 800,
			content : function(body) {
				body.html('loading...');
				body.load('${ctxPath}/techc/vwsurvey/pushreport?id=' + id);
			},
			buttonText : {
				cancel : '关闭'
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
				<div class="tt">${i18n.appname.mw.vwsurveyvotelist}</div>
				<ec:table action="index" method="get" items="dblist" var="var" view="ecan" width="99%" imagePath="${coreImgPath}/table/*.gif" showExports="false">
					<ec:row>
						<ec:column title="${i18n.survey.name}" property="CTitle" width="30%">
							<!-- <a href="edit?id=${var.id}"> <ecan:SubString content="${var.CTitle}" end="14" fix="..." />
							</a> -->
							<a href="${ctxPath}/mw/onlinesurvey/${var.id}.html" target="_blank"><ecan:SubString content="${var.CTitle}" end="14" fix="..." /></a>
						</ec:column>
						<ec:column title="${i18n.app.trainer}" property="CTearcher" width="20%"></ec:column>
						<ec:column title="${i18n.operman.starttime}" property="dtStartDate" cell="date" format="yyyy-MM-dd" width="10%"></ec:column>
						<ec:column title="${i18n.operman.endtime}" property="dtOverDate" cell="date" format="yyyy-MM-dd" width="10%"></ec:column>
						<ec:column title="${i18n.oper.text}" property="oper" width="30%" filterCell="oper" sortable="false">
							<a href="edit?id=${var.id}" class="oper">${i18n.oper.edit}</a>
							<a href="javascript:void(0);" onclick="copy('${var.id}')" class="oper">${i18n.app.copy}</a>
							<a href="javascript:void(0);" onclick="del('${var.id}')" class="oper">${i18n.oper.del}</a>
							<a href="javascript:void(0);" onclick="build('${var.id}','${var.NSysId}')" class="oper">${i18n.app.generatequestion}</a>
							<a href="javascript:pushreport('${var.id}');" class="oper">${i18n.app.postquestion}</a>
						</ec:column>
					</ec:row>
				</ec:table>
				<div class="btns">
					<span class="btn"> <a href="add">${i18n.button.add}</a>
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