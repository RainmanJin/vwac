<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@include file="/common/head.jsp" %>
	
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
		
		.rowAns a{
			text-decoration:underline;
			color: blue;
			height: 25px; line-height: 25px;
		}
		
		#AnsDIV{
			display: inline-block;
		}

	</style>
	
	<script type="text/javascript">
	initJsonForm("#myForm",function(d){
		location.href="index";
	},function(d){
		alert(d.data)
	});
	</script>
</head>
<body>
<%@include file="/common/header.jsp" %>

<div class="centerbody centerbox">
	<div id="contentwrapper">
		<div id="content_right">
			<div class="tt">
				${i18n.traincourse.title}
			</div>
			<form id="myForm" action="save" method="post" class="form">
				<input type="hidden" name="id" value="${dto.id}" />
					<ul>
						<li>
							<label>${i18n.traincourse.filed.name}</label>
							<input type="text" name="name" value="${dto.name}" v="notEmpty" maxlength="100" />
							<span class="msg">${i18n.traincourse.filed.name}${i18n.valid.notEmpty}</span>
						</li>
						
						<li>
							<label>${i18n.traincourse.filed.type}</label>
							<d:selectDomain domain="TRAINCOURSE_TYPE" name="type" value="${dto.type}"/>
						</li>
						
						<li>
							<label>${i18n.traincourse.filed.days}(${i18n.traincourse.filed.daysUnit})</label>
							<input type="text" name="days" value="${dto.days}" v="notEmpty" maxlength="4" />
							<span class="msg">${i18n.traincourse.filed.days}${i18n.valid.notEmpty}</span>
						</li>
						
						<li>
							<label>${i18n.traincourse.filed.proType}</label>
							<d:selectDomain domain="PROTYPE" name="proType" value="${dto.proType}" />
						</li>
						<li>
							<label>${i18n.traincourse.filed.brand}</label>
							<d:selectDomain domain="BRAND" name="brand" value="${dto.brand}" />
						</li>
						<li>
							<label>${i18n.traincourse.filed.status}</label>
							<d:selectDomain domain="STATUS" name="status" value="${dto.status}" />
						</li>
						<li style="height: auto;">
							<label>${i18n.traincourse.filed.module}</label>
							<div id="AnsDIV">
								<c:forEach items="${dto.items}" var="var">
								<div class="rowAns">
									<input type="text" name="mds_${var.id}" value="${var.name}" />&nbsp;
									<a href="javascript:void(0)" onclick="$(this).parent().remove();">${i18n.oper.del}</a>
								</div>
								</c:forEach>
								
								<c:forEach begin="0" end="4">
								<div class="rowAns">
									<input type="text" name="mds_" />
								</div>
								</c:forEach>
							</div>
						</li>
					</ul>
			</form>
				<div class="btns" style="margin-left: 160px;">
							<span class="btn">
								<a href="javascript:void(0)" onclick="$('#myForm').submit()">${i18n.button.submit}</a>
							</span>
							<span class="btn">
								<a href="javascript:history.back()">${i18n.button.back}</a>
							</span>		
				</div>
		</div>
	</div>
	
	<%@include file="/common/leftmenu.jsp" %>
	<div class="clearfix_b"></div>
</div>
<%@include file="/common/footer.jsp" %>
</body> 
</html>