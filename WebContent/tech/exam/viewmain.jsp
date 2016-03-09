<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@include file="/common/head.jsp" %>
	
	<style>
		/* .form li label{
			width: 100px;
		} */
		.lineSeperater{
			border-top: 1px dotted #dfdfdf;
			padding: 20px 0px;
		}
		.form li label{
			width: 140px;
		}
		
		.rowTitle{
			border: 1px solid #dfdfdf;background: #f0f0f0;color: #2274AC; font-size: 14px; font-weight: bold;
			height: 25px; line-height: 25px;
		}
		.eXtremeTable .tableRegion{
			margin: 0px;
		}
	</style>
	<script type="text/javascript">
	$(document).ready(function(){
		initJsonForm("#exForm",function(d){
			location.href="index";
		},function(d){
			alert(d.data);
		});

		});
	function push(id)
	{
		if(confirm("${i18n.pushpkg.comfirm}"))
		{
			ajaxGet("push",{"id":"${dto.id}"},function(d){
				if(d.success)
					location.href="index";
				else
					alert(d.data);
			},"json");
		}
	}
	</script>
</head>
<body>
<%@include file="/common/header.jsp" %>

<div class="centerbody centerbox">
	<div id="contentwrapper">
		<div id="content_right">
				<div class="tt">
				       <c:choose>
							<c:when test="${empty dto}">
							        ${i18n.exam.addquestions}
							</c:when>
							<c:otherwise>
							        ${i18n.exam.editquestions}
							</c:otherwise>
						</c:choose>
				</div>
				<form id="exForm" action="savemain" method="post">
				<input type="hidden" name="id" id="id" value="${dto.id}"/>
				<ul class="form">
						<li>
							<label>${i18n.exam.maintitle}</label>
							<input type="text" id="title" name="title" value="${dto.title}" v="notEmpty" maxlength="255" style="width: 255px" />
							<span class="msg">${i18n.valid.notEmpty}</span>
						</li>
						<li>
							<label>${i18n.exam.timeflag}</label>
							<input type="text" id="timeFlag" name="timeFlag" value="${dto.timeFlag}" v="notEmpty" maxlength="50" style="width: 50px" />
							<span class="msg">${i18n.valid.notEmpty}</span>
						</li>
						<li>
							<label>${i18n.exam.singlescort}</label>
							<input type="text" id="singleScort" name="singleScort" value="${dto.singleScort}" v="notEmpty" maxlength="50" style="width: 50px" />
							<span class="msg">${i18n.valid.notEmpty}</span>
						</li>
						<li>
							<label>${i18n.exam.multiscort}</label>
							<input type="text" id="multiScort" name="multiScort" value="${dto.multiScort}" v="notEmpty" maxlength="50" style="width: 50px" />
							<span class="msg">${i18n.valid.notEmpty}</span>
						</li>
						<li>
							<label>${i18n.exam.papertype}</label>
							<d:selectDomain  domain="EXAM_PAPER_TYPE" name="type" value="${dto.type}"/>
							<span class="msg">${i18n.valid.notEmpty}</span>
						</li>
						<li>
							<label>${i18n.exam.showanswer}</label>
							<d:selectDomain  domain="EXAM_SHOW_ANSWER" name="showAnswer" value="${dto.showAnswer}"/>
							<span class="msg">${i18n.valid.notEmpty}</span>
						</li>
						<li>
							<label>${i18n.exam.enableflag}</label>
							<d:selectDomain  domain="EXAM_ENABLE_FLAG" name="flag" value="${dto.flag}"/>
							<span class="msg">${i18n.valid.notEmpty}</span>
						</li>
						<li>
							<label>${i18n.exam.randomcount}</label>
							<input type="text" id="randomCount" name="randomCount" value="${dto.randomCount}" v="notEmpty" maxlength="50" style="width: 50px" />
							<span class="msg">${i18n.valid.notEmpty}</span>
						</li>
						<li>
							<label>${i18n.exam.passlevel}</label>
							<input type="text" id="passLevel" name="passLevel" value="${dto.passLevel}" v="notEmpty" maxlength="50" style="width: 50px" />
							<span class="msg">${i18n.valid.notEmpty}</span>
						</li>
						<li>
							<label>${i18n.exam.leftcount}</label>
							<input type="text" id="leftCount" name="leftCount" value="${dto.leftCount}" v="notEmpty" maxlength="50" style="width: 50px" />
							<span class="msg">${i18n.valid.notEmpty}</span>
						</li>
				</ul>
				</form>
			<div class="btns" style="padding-left: 160px;">
				<span class="btn">
					<a href="javascript:void(0)" onclick="$('#exForm').submit()">${i18n.button.save}</a>
				</span>
				<c:if test="${ !empty dto}">
				<span class="btn">
					<a href="editques?id=${dto.id}">${i18n.button.editquestion}</a>
				</span>
				<span class="btn">
					<%-- <a href="push?id=${dto.id}" id="push">${i18n.button.pushpkg}</a> --%>
					<a href="javascript:void(0)" onclick="push('${dto.id}')">${i18n.button.pushpkg}</a>
				</span>
				</c:if>
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