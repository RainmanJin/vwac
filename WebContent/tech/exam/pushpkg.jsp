<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@include file="/common/head.jsp" %>
	<style>
		.form li label{
			width: 100px;
		}
	</style>
	<script type="text/javascript">
	$(document).ready(function(){
		initJsonForm("#pkForm",function(d){
			location.href="index";
		},function(d){
			alert(d.data);
		});

	});
	</script>
</head>
<body>
<%@include file="/common/header.jsp" %>

<div class="centerbody centerbox">
	<div id="contentwrapper">
		<div id="content_right">
				<div class="tt">${i18n.mdttln.title}</div>
				<form id="pkForm" action="push" method="post">
				<input type="hidden" name="id" value="${dto.id}" />
				<input type="hidden" name="title" id="title" value="${dto.title}" />
				<ul class="form">
					    <li><label>${i18n.scormpkg.filed.proType}</label>
						<d:selectDomain domain="PROTYPE" name="proType" value="" />
						<em>*</em>
					    </li>
					    <li><label>${i18n.exam.pkgopen}</label>
						<d:selectDomain domain="EXAM_ENABLE_FLAG" name="openflag" value="${dto.flag}" />
						<%--<select name="openflag" >
							<c:forEach items="${map}" var="item"> 
							<option value='${item.key}' ${item.key==dto.flag?'selected':''}>
								${item.value}
							</option>
							</c:forEach>
						</select>
						--%><em>*</em>
					    </li>
				</ul>
				</form>
			<div class="btns" style="padding-left: 160px;">
				<span class="btn">
					<a href="javascript:void(0)" onclick="$('#pkForm').submit()">${i18n.mdttln.button.savepush}</a>
				</span>
				<span class="btn">
					<a href="index">${i18n.button.back}</a>
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