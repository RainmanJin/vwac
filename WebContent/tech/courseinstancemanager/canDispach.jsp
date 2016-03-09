<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@include file="/common/head.jsp" %>
	
	<script type="text/javascript">
		function publish()
	{
		$.fn.jmodal({
            title: '${i18n.traincourse.filed.publish}',
            width:600,
            content: function(body) {
                body.html('loading...');
                body.load('canPublishCourse');
            },
            buttonText: {cancel: '${i18n.button.cancel}' }
        });
	}
	
	function del(id)
	{
		ajaxGet('del',{"id":id},
		function(r){
						if(r.success)
						{
								//alert("${i18n.commit.success}");
								location.reload();
						}
						else
						{
							alert(r.data);
						}
					},"json");
	}
	
	function dispach(id)
	{
		ajaxGet('dispached',{"id":id},
		function(r){
						if(r.success)
						{
								//alert("${i18n.commit.success}");
								location.reload();
						}
						else
						{
							alert(r.data);
						}
					},"json");
	}
	
	function ing(id)
	{
		ajaxGet('ing',{"id":id},
		function(r){
						if(r.success)
						{
								//alert("${i18n.commit.success}");
								location.reload();
						}
						else
						{
							alert(r.data);
						}
					},"json");
	}
	</script>
</head>
<body>
<%@include file="/common/header.jsp" %>

<div class="centerbody centerbox">
	<div id="contentwrapper">
		<div id="content_right">
		<div class="tt">${i18n.traincourse.title}</div>
			<div class="btns">
			<span class="btn">
				<a href="javascript:publish();">${i18n.traincourse.filed.publish}</a>
			</span>
			 </div>
			 
			<ec:table action="" items="list" var="var" view="ecan" width="100%"
					imagePath="${coreImgPath}/table/*.gif">
					<ec:row highlightRow="true">
						<ec:column title="${i18n.traincourse.filed.name}" property="name" width="20%"></ec:column>
						<ec:column title="${i18n.traincourse.filed.proType}" property="protype" width="8%" filterCell="droplist" filterOptions="DOMAIN_PROTYPE">
							<d:viewDomain value="${var.proType}" domain="PROTYPE" />
						</ec:column>
						<ec:column title="${i18n.traincourse.filed.expiretime}" property="expireTime" width="10%" cell="date" format="yyyy-MM-dd"></ec:column>
						<ec:column title="${i18n.traincourse.filed.status}" property="protype" width="6%" filterCell="droplist" filterOptions="DOMAIN_COURSESTATUS">
							<d:viewDomain value="${var.status}" domain="COURSESTATUS" />
						</ec:column>
						
						<ec:column title="${i18n.oper.text}" property="oper" width="18%" filterCell="oper" sortable="false">
							<a href="view?id=${var.id}&cousreid=${var.courseId }" class="oper">${i18n.oper.edit}</a>&nbsp;
							<c:if test="${'0' eq var.status}">
								<a href="javascript:del('${var.id}');">${i18n.oper.del}</a>
							</c:if>
							<c:if test="${'1' eq var.status}">
								<a href="javascript:del('${var.id}');">${i18n.oper.del}</a>
								<a href="javascript:ing('${var.id}');">${i18n.oper.publish}</a>
							</c:if>
							<c:if test="${!('0' eq var.status)}">
								<a href="javascript:dispach('${var.id}');">${i18n.oper.disable}</a>
							</c:if>
						</ec:column>
					</ec:row>
			</ec:table>	 
		</div>
	</div>
	
	<%@include file="/common/leftmenu.jsp" %>
	<div class="clearfix_b"></div>
</div>
<%@include file="/common/footer.jsp" %>
</body> 
</html>