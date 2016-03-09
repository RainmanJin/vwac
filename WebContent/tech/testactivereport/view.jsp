<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@include file="/common/head.jsp" %>
	<style type="text/css">
		#contents{
			padding-top: 20px;
			padding-bottom: 30px;
		}
		
		/*for view point result stat*/
		.blueRow{
			background: #2274AC; color: #fff; font-size: 14px;
		}
 		
		.pipt{
			text-align: center; font-size: 14px; color: blue;
		}
		.rowTitle{
			border: 1px solid #dfdfdf; background: #f0f0f0; color: #2274AC; font-size: 14px; font-weight: bold;
			height: 25px; line-height: 25px;
		}
		
		.eXtremeTable .tableRegion{
			margin: 0;
		}
		
	</style>
	<script type="text/javascript">
		$(document).ready(function(){
			$(".subTab li a").click(function(){
				$(".subTab li").removeClass("active");
				$(this).parent().addClass("active");
				ajaxLoad($(this).attr("u"),"#contents",{"id":"${id}", "userid":"${userid}"});
			});
			
			ajaxLoad("basic","#contents",{"id":"${id}", "userid":"${userid}"});
		});

		function chgStudent(v)
		{
			location.href="view?id=${id}&userid=" + v;
		}
	</script>
</head>
<body>
<%@include file="/common/header.jsp" %>

<div class="tt">
	${i18n.activereport.title}
	&nbsp;&nbsp;&nbsp;&nbsp;${i18n.testactive.filed.student}:
	<select onchange="chgStudent(this.value)">
		<c:forEach var="var" items="${dto.userids}">
		<option value="${var}" <c:if test="${userid eq var}">selected="selected"</c:if>>
			<ecan:viewDto property="name" dtoName="EcanUser" id="${var}"/>
		</option>
		</c:forEach>
	</select>
		
	<span class="btn" style="float: right;">
		<a href="index">${i18n.button.back}</a>
	</span>
</div>

<div class="subTab">
	<ul>
		<li class="active">
			<a href="javascript:;" u="basic">${i18n.activereport.basic}</a>
		</li>
		<li>
			<a href="javascript:;" u="${ctxPath}/techc/testactive/viewPointStat">${i18n.activereport.viewpoint}</a>
		</li>
		<li>
			<a href="javascript:;" u="${ctxPath}/techc/testactive/viewTestResult">${i18n.activereport.test}</a>
		</li>
		<li>
			<a href="javascript:;" u="feedback">${i18n.activereport.feedback}</a>
		</li>
	</ul>
	<div class="clearfix_l"></div>
</div>

<div id="contents">
	
</div>

		<%@include file="/common/footer.jsp"%>

</body> 
</html>