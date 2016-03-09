<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@include file="/common/head.jsp" %>
	<style type="text/css">
		
		.tests{
			width: 600px;
			border-right: 1px solid #ddd;
		}
		
		.row{
			margin-bottom:20px; border-bottom: 1px dotted #ddd;
		}
		
		.row dt{
			font-size: 12px; font-weight: bold; padding-bottom:10px;line-height: 20px;
		}
		
		.row dd{
			padding-left: 40px;line-height: 20px;
		}
		.row dd.hover{
			background: #f0f0f0; cursor: pointer;
		}
		
		#beginBtn span.btn{
			height: 40px; background-image: none; background-color: #3675AC;
		}
		#beginBtn span.btn a{
			font-size: 30px; height: 40px; line-height: 40px; background-image: none; background-color: #3675AC; text-align: center;
		}
		
		#beginBtn{
			position: absolute; top: 400px; width: 200px; left:400px;  z-index: 100001;
		}
		
		#timerDIV{
			position: absolute; top: 400px; left:610px;
		}
		#timerDIV span.t{
			 font-size: 30px; height: 40px; line-height: 40px; font-weight: bold;
		}
		
		#resultDiv{
			border-top: 1px solid #ddd;
			width: 350px;
		}
	</style>
	<script type="text/javascript">

		function submitForm()
		{
			$("#myForm").submit();
		}


		$().ready(function(){
				initJsonForm("#myForm",function(d){
					location.reload();
				},function(d){
					alert(d.data)
				});
			
				$("#bd").css("position","relative");

				$(".row dd span").click(function(e){
						var checked = $(this).parent().find("input").attr("checked");
						$(this).parent().find("input").attr("checked",!checked);
					}).hover(function(){
						$(this).addClass("hover");
					},function(){
						$(this).removeClass("hover");
					});

				$("#resultDiv").load("viewTestResult?id=${user.testActiveId}&userid=${user.userId}&r_=" + Math.random());
			});
	</script>
</head>
<body>
<c:set var="isLoginView" value="1"></c:set>
<%@include file="/common/header.jsp" %>

	<div class="tt">${i18n.testactive.titleTestOnline}:::${active.name}:::<ecan:viewDto dtoName="EcanUser" property="name" id="${user.userId}" /></div>

		<form id="myForm" action="confirm" method="post">
		<input type="hidden" name="testUserId" value="${param.testUserId}" />
		<input type="hidden" name="type" value="submit" />
		
		<ul class="tests">
			<c:forEach items="${list}" var="var" varStatus="status">
			<li class="row">
				<dl>
					<dt>
						${status.index+1}:&nbsp;&nbsp;${var.title}
						<!-- &nbsp;&nbsp;&nbsp;							
						<c:if test="${!(empty var.trainCourseId)}"><ecan:viewDto property="name" dtoName="TechTrainCourse" id="${var.trainCourseId}"/><c:if test="${!(empty var.moduleId)}">/<ecan:viewDto property="name" dtoName="TechTrainCourseItem" id="${var.moduleId}"/></c:if></c:if>
						 -->
					</dt>
					<c:forEach items="${var.ans}" var="ans">
						<dd>
							<input type="checkbox" name="ans" value="${var.id}_${ans.id}" ${ans.exts.checked} />&nbsp;&nbsp;
							<span <c:if test="${'1' eq ans.isRight}">style="color:blue;"</c:if>>${ans.title}</span>
						</dd>
					</c:forEach>
				</dl>
			</li>
			</c:forEach>
		</ul>
		</form>		

	<div id="timerDIV">
		<div class="btns">
			<span class="btn">
				<a href="javascript:submitForm()">${i18n.button.submit}</a>
			</span>
			<span class="btn">
				<a href="beginTest?id=${active.id}">${i18n.button.back}</a>
			</span>
		</div>
		
		<div id="resultDiv">
			
		</div>
	</div>
<%@include file="/common/footer.jsp" %>
</body> 
</html>