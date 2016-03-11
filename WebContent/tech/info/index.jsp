<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@include file="/common/head.jsp"%>
<script type="text/javascript">
	function del(id) {
		$.fn.jmodal({
			title : '${i18n.resourse.delete}',
			width : 800,
			content : function(body) {
				body.html('loading...');
				body.load('beforedelete?id=' + id);
			},
			buttonText : {
				ok : '${i18n.button.ok}',
				cancel : '${i18n.button.cancel}'
			},
			okEvent : function(data, args) {
				$('#myForm1').submit();
			}
		});
	}

	function modify(id) {
		$.fn.jmodal({
			title : '${i18n.resourse.modify}',
			width : 800,
			content : function(body) {
				body.html('loading...');
				body.load('edit?id=' + id);
			},
			buttonText : {
				ok : '${i18n.button.ok}',
				cancel : '${i18n.button.cancel}'
			},
			okEvent : function(data, args) {
				$('#myForm1').submit();
			}
		});
	}

	function userRecord(id) {
		$.fn.jmodal({
			title : '${i18n.resourse.record}',
			width : 800,
			content : function(body) {
				body.html('loading...');
				body.load('userrecord?id=' + id);
			},
			buttonText : {
				cancel : '${i18n.button.cancel}'
			}
		});
	}

	function status(type, id) {
		ajaxGet("${ctxPath}/techc/info/" + type, {
			"id" : id
		}, function(d) {
			if (d.success)
				location.reload();
			else
				alert(d.data);
		}, "json");
	}
</script>
</head>
<body>
	<%@include file="/common/header.jsp"%>

	<div class="centerbody centerbox">
		<div id="contentwrapper">
			<div id="content_right">
			<div class="tt">${i18n.appname.mw.newsmanager}</div>
				<ec:table action="" method="get" items="articleList" var="var" view="ecan" width="99%" imagePath="${coreImgPath}/table/*.gif" showExports="false" filterable="false">
					<ec:row>
						<ec:column title="ID" property="id" width="60px">
						</ec:column>
						<ec:column title="${i18n.i18n.filed.name}" property="title" width="30%">
							<ecan:SubString content="${var.title}" end="14" fix="..." />
						</ec:column>
						<ec:column title="${i18n.app.categoryname}" property="typeId" width="20%">
						</ec:column>
						<ec:column title="${i18n.app.entrydate}" property="createTime" width="150px" filterable="false" sortable="false">
						</ec:column>
						<ec:column title="${i18n.oper.text}" property="oper" width="120px" filterCell="oper" sortable="false">
							<a href="view?id=${var.id}" onclick="userRecord('${var.id}');" class="oper">${i18n.oper.edit}</a>&nbsp;
							
							<%-- <a href="addques?id=${var.id}" class="oper">${i18n.button.addquestion}</a>&nbsp; --%>
							<a href="javascript:void(0)" onclick="del('${var.id}')" class="oper">${i18n.oper.del}</a>
						</ec:column>
					</ec:row>
				</ec:table>
				<div class="tt">
					<span class="btn"> <a href="view">${i18n.button.add}</a>
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