<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@include file="/common/head.jsp" %>
	<style type="text/css">
		.row{
			margin-bottom:20px; border-bottom: 1px dotted #ddd; width: 800px; 
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
		
		#loading{
			filter:alpha(Opacity=80);
			-moz-opacity:0.9;
			opacity: 0.9;
			background: #333;
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
			position: absolute; top: 400px; width: 100px; left:860px;
		}
		#timerDIV span.t{
			 font-size: 30px; height: 40px; line-height: 40px; font-weight: bold;
		}
	</style>
	<c:if test="${!(empty list)}">
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
			});
	</script>
	</c:if>
</head>
<body>
<%@include file="/common/header.jsp" %>

<div class="tt">${i18n.question.training}</div>

		<form id="myForm" action="test" method="post">
		<input type="hidden" name="questionId" value="${id}" />
		<ul>
			<c:forEach items="${list}" var="var" varStatus="status">
			<li class="row">
				<dl>
					<dt>${status.index+1}:&nbsp;&nbsp;${var.subitmesName}</dt><br/>
					<c:if test="${var.subitmeType eq '4'}">
						<textarea rows="5" cols="50" name="t_${var.id}"></textarea>
					</c:if>
					<c:if test="${var.subitmeType eq '5'}">
						<d:radioDomain domain="QUESTIONTRAINCOM"   name="c_${var.id}"/>
					</c:if>
					<c:if test="${var.subitmeType eq '1' || var.subitmeType eq '2' || var.subitmeType eq '3'}">
						<d:radioDomain domain="QUESTIONTYPE"  name="r_${var.id}"/>
					</c:if>
				</dl>
			</li>
			</c:forEach>
		</ul>
		</form>		
 		<div>
			<span class="btn">
				<a href="javascript:window.close();">${i18n.button.close}</a> 
			</span>
		</div>

<%@include file="/common/footer.jsp" %>
</body> 
</html>