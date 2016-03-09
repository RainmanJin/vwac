<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
		<%@include file="/common/head.jsp"%>
		<style>
			.table_border td{border-top:1px #DDD solid;border-right:1px #DDD solid;}
		</style>
		<script>
			function view(id)
		{
			$.fn.jmodal({
	            title: '${i18n.operman.viewstudentstatus}',
	            width:800,
	            content: function(body) {
	                body.html('loading...');
	                body.load('viewstudentstatus?id='+id);
	            },
	            buttonText: { cancel: '${i18n.button.cancel}' }
	        });
		}
		</script>
	</head>
	<body>
		<%@include file="/common/header.jsp"%>

		<div class="centerbody centerbox">
	<div id="contentwrapper">
		<div id="content_right">
			 	<div class="tt">
					${i18n.operman.testAchievement} &nbsp; <span>${i18n.operman.allcourse}:&nbsp;${courseCount}&nbsp;&nbsp;&nbsp;${i18n.operman.ingcourse}:&nbsp;${courseListSize}</span>
					
				</div>
				
				<ec:table action="" items="courseList" var="var" view="ecan" width="100%"
					imagePath="${coreImgPath}/table/*.gif" showExports="false" filterable="false" showStatusBar="false" sortable="false" rowsDisplayed="1000">
					<ec:row highlightRow="true">
						<ec:column title="${i18n.domain.filed.label}" property="name" width="30%"></ec:column>
						<ec:column title="${i18n.operman.starttime}" property="createTime" width="10%" cell="date" format="yyyy-MM-dd"></ec:column>
						<ec:column title="${i18n.operman.endtime}" property="expireTime" width="10%" cell="date" format="yyyy-MM-dd"></ec:column>
						<ec:column title="${i18n.operman.doprocess}" property="progress" width="9%" ></ec:column>
						<ec:column title="${i18n.operman.avg}" property="avgPoint" width="9%" >
							${var.avgPoint==null?"0%":var.avgPoint}
						</ec:column>
						<ec:column title="${i18n.operman.nofinish}" property="noFinishCount" width="12%" ></ec:column>
						<ec:column title="${i18n.oper.text}" property="oper" width="8%" filterCell="oper" sortable="false">
							<a href="javascript:view('${var.id}');" class="oper">${i18n.operman.viewdetail}</a>&nbsp;
						</ec:column>
					</ec:row>
				</ec:table>
				
					<div class="tt">
					${i18n.operman.docourse}

				</div>
				
				<ec:table action="testingIndex" items="doList" var="var" view="ecan" width="100%"
					imagePath="${coreImgPath}/table/*.gif" filterable="true" showExports="false" onInvokeAction="$('#ec_ajax').submit();" autoIncludeParameters="false">
					<ec:row highlightRow="true">
						<ec:column title="${i18n.domain.filed.label}" property="name" width="40%"></ec:column>
						<ec:column title="${i18n.operman.starttime}" property="createTime" cell="date" format="yyyy-MM-dd" width="20%" ></ec:column>
						<ec:column title="${i18n.operman.endtime}" property="expireTime" width="20%" cell="date" format="yyyy-MM-dd"></ec:column>
						<ec:column title="${i18n.operman.avg}" property="avgPoint" width="20%">
							${var.avgPoint ==""?"0":var.avgPoint}
						</ec:column>
					</ec:row>
				</ec:table>
				<br />
				<br />
				<br />
				
			 </div>
		</div>
	<%@include file="/common/leftmenu.jsp" %>
	<div class="clearfix_b"></div>
</div>
<%@include file="/common/footer.jsp" %>
</body> 
</html>