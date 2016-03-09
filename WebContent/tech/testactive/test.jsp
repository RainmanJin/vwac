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
		var curMin = ${active.testTimelimit};
		var curSec = 0; 

		function showTimer()
		{
			$("#timerDIV .min").html(curMin);
			$("#timerDIV .sec").html(curSec>=10?curSec:"0" + curSec);			
		}

		function submitForm()
		{
			$("#myForm").submit();
		}

		function timerInterval()
		{
			if(curSec== 0)
			{
				curMin--;
				curSec = 59;

				if(curMin < 0)
				{
					submitForm();
					return;
				}
			}else
			{
				curSec--;
			}
			showTimer();

			setTimeout(timerInterval,1000);
		}

		$().ready(function(){
				initJsonForm("#myForm",function(d){
					location.reload();
				},function(d){
					alert(d.data)
				});
			
				$("#bd").css("position","relative");
			
				loadingHtml("");
				$("#beginBtn").show();

				showTimer();

				$(".row dd span").click(function(){
						var checked = $(this).parent().find("input").attr("checked");
						$(this).parent().find("input").attr("checked",!checked);
					}).hover(function(){
						$(this).addClass("hover");
					},function(){
						$(this).removeClass("hover");
					});
			});

		
		function beginTest()
		{
			loadEnd();
			$("#beginBtn").hide();

			setTimeout(timerInterval,1000);
		}
	</script>
	</c:if>
</head>
<body>
<c:set var="isLoginView" value="1"></c:set>
<%@include file="/common/header.jsp" %>

<div class="tt">${i18n.testactive.titleTestOnline}:::${active.name}:::<ecan:viewDto dtoName="EcanUser" property="name" id="${user.userId}" /></div>

<c:choose>
	<c:when test="${empty list}">
		<div style="font-size: 20px; text-align: center; color: red;">${i18n.testactive.hasCompletedTest}</div>
	</c:when>
	<c:otherwise>
		<form id="myForm" action="test" method="post">
		<input type="hidden" name="testUserId" value="${param.testUserId}" />
		<input type="hidden" name="type" value="submit" />
		
		<ul>
			<c:forEach items="${list}" var="var" varStatus="status">
			<li class="row">
				<dl>
					<dt>${status.index+1}:&nbsp;&nbsp;${var.title}</dt>
					<c:forEach items="${var.ans}" var="ans">
						<dd>
							<input type="checkbox" name="ans" value="${var.id}_${ans.id}" />&nbsp;&nbsp;
							<span>${ans.title}</span>
						</dd>
					</c:forEach>
				</dl>
			</li>
			</c:forEach>
		</ul>
		</form>		
	</c:otherwise>
</c:choose>

<c:if test="${!(empty list)}">
	<div id="timerDIV">
		<div>
			<span class="min t"></span>
			<span class="t">:</span>
			<span class="sec t"></span>
		</div>
		<div>
			<span class="btn">
				<a href="javascript:submitForm()">${i18n.button.submit}</a>
			</span>
		</div>
	</div>
	
	<div id="beginBtn" style="display: none;">
		<span class="btn">
			<a href="javascript:beginTest()"> 开始测试<br/>(<ecan:viewDto dtoName="EcanUser" property="name" id="${user.userId}" />) </a>
		</span>
	</div>
</c:if>
<%@include file="/common/footer.jsp" %>
</body> 
</html>