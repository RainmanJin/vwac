<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@include file="/common/head.jsp" %>
	<script type="text/javascript">
		function edit(id)
		{
			$.fn.jmodal({
	            title: '${i18n.i18n.title}',
	            width:600,
	            content: function(body) {
	                body.html('loading...');
	                body.load('view?id=' + id);
	            },
	            buttonText: { ok: '${i18n.button.ok}', cancel: '${i18n.button.cancel}' },
	            okEvent: function(data, args) {
	            	//args.hide();
	            	$("#i18nForm").submit();
	            }
	        });
		}
	</script>
</head>
<body>
<%@include file="/common/header.jsp" %>

<div class="centerbody centerbox">
	<div id="contentwrapper">
		<div id="content_right">
			<div class="tt">
				${i18n.i18n.title}
			</div>
			<ec:table action="" method="get" items="list" var="var" view="ecan" width="99%"
					imagePath="${coreImgPath}/table/*.gif" showExports="false">
					<ec:row>
						<ec:column title="Comments" property="comments" width="30%"></ec:column>
						<ec:column title="English" property="en" width="30%"></ec:column>
						<ec:column title="简体中文" property="zh_CN" width="30%"></ec:column>
						<ec:column title="${i18n.oper.text}" property="oper" width="10%" filterCell="oper" sortable="false">
							<a href="javascript:void(0)" onclick="edit('${var.id}')" class="oper">${i18n.oper.edit}</a>
						</ec:column>
					</ec:row>
			</ec:table>
			
		</div>
	</div>
	
	<%@include file="/common/leftmenu.jsp"%>
	<div class="clearfix_b"></div>
</div>
<%@include file="/common/footer.jsp" %>
</body> 
</html>