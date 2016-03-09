<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@include file="/common/head.jsp"%>
		<style>

body,div,dl,dt,dd,ul,ol,li,h1,h2,h3,h4,h5,h6,pre,form,fieldset,input,textarea,blockquote,p
	{
	padding: 0;
	margin: 0;
}

table,td,tr,th {
	font-size: 12px;
}

li {
	list-style-type: none;
}

table {
	margin: 0 auto;
	border-collapse: collapse;
}

img {
	vertical-align: top;
	border: 0;
}

ul {
	list-style: none;
}

h1,h2,h3,h4,h5,h6 {
	font-size: 12px;
	font-weight: normal;
}

a {
	color: #333;
	text-decoration: none;
}

.clr {
	clear: both;
	height: 0;
	overflow: hidden;
}

.fl {
	float: left;
}

.fr {
	float: right;
}

.warpL {
	width: 960px;
	margin: 0 auto;
	height: 420px;
	overflow: hidden;
	border: 1px solid #797979;
}

.leftL {
	width: 240px;
	border: 1px solid #dfdfdf;
}

.leftL h2 {
	height: 60px;
	line-height: 60px;
	font-size: 14px;
	text-align: center;
}

.leftL .tab {
	background: #f2f2f2;
	height: 360px;
	border-top: 1px solid #797979
}

.leftL .tab h3 {
	width: 135px;
	height: 20px;
	line-height: 20px;
	color: #fff;
	background: #3399fe;
	margin: 20px 0px 10px 20px;
	font-family: "微软雅黑";
	text-align: center;
}

.leftL .tab h3 a {
	color: #fff;
}

.leftL .tab table {
	background: #fff;
}

.leftL .tab table td {
	text-align: center;
	font-size: 16px;
}

.leftL .tab table td a {
	display: inline-block;
	width: 38px;
	height: 38px;
	line-height: 38px;
	text-align: center;
}

.leftL .tab table td a:link {
	background: #fff;
}

.leftL .tab table td a:visited {
	background: #cccccc;
}

.leftL .tab table td a:active {
	background: #fff;
}

.rightL {
	width: 640px;
	margin-left: 10px;
}

.rightL p {
	text-align: right;
	height: 20px;
	line-height: 20px;
	font-size: 14px;
}

.rightL ul {
	margin-top: 17px;
}

.rightL ul li {
	margin-left: 30px;
	height: 30px;
	line-height: 30px;
	overflow: hidden;
}

.rightL ul li label {
	vertical-align: middle;
	font-family: Tahoma;
}

.rightL ul li span {
	vertical-align: middle;
}

.rightL .bt a {
	margin-right: 25px;
}
span.item{
	width: 36px; height: 36px; line-height:36px; display: inline-block; 
	border: 1px solid #ddd; float: left; text-align: center;background: #fff; font-size: 14px;
}
span.item a{
	width: 36px; height: 36px; line-height:36px; display: inline-block;
}
li.red{
	background: #ff0000;
}
li.greed{
	background: #4db748;
}
span.hover{
	background: #3675AC;
}
span.hover a{
	color: #fff;
}
span.odd{
	background: #dfdfdf;
}
</style>
	<script>
		
		function finish()
		{
			location.href="${ctxPath}/techc/elearning/index";
		}
	</script>
	</head>
	<body>
		<%@include file="/common/header.jsp"%>

		<div class="warpL">
			<div class="leftL fl">
				<!--
				 <h2>
					${i18n.course.nowtestting}（${currectPoint}%${i18n.course.do}）
				</h2>
				 -->
				<div class="tab">
					<!--
					<h3>
						${i18n.course.testting.href}
					</h3>
					-->
					<br /><br />
					<div style="width: 190px; margin-left: 10px;">
						<c:forEach items="${list}" var="var" varStatus="status">
							<c:set var="cls" value=""></c:set>
							<c:set var="titlesize" value="${status.index}"></c:set>
							<c:choose>
								<c:when test="${idx eq status.index}">
									<c:set var="cls" value="hover"></c:set>
								</c:when>
							</c:choose>
							<span class="item ${cls}">
								<a href="viewTestting?testtingInstanceId=${param.testtingInstanceId}&idx=${status.index}">${status.index+1}</a>
							</span>
						</c:forEach>
					</div>
				</div>
			</div>
			<div class="rightL fl">
				<p>
				</p>
				<div class="imgArea" style="height: 192px; overflow: hidden;">
					<div class="tt">${courseInstance.name}</div>
					<div>${dto.title}</div>
					<div class="clr"></div>
				</div>
				<ul
					style="width: 568px; height: 150px; overflow: hidden; border: 1px solid #797979; font-size: 14px;">
					
					<c:forEach items="${dto.ans}" var="ans" varStatus="status">
						<%-- 判断是选中并且还是正确答案  绿色  --%>
						<c:set value="" var="_cl"/>
						<c:if test="${'1' eq ans.isRight}">
							<c:set value="greed" var="_cl"/>
						</c:if>
						<%-- 正确答案没选中、或者选中错误答案，红色 
						<c:if test="${('1' eq ans.isRight && !ans.checked) || ('0' eq ans.isRight && ans.checked) }">
							<c:set value="red" var="_cl"/>
						</c:if> --%>
						<li class="li ${_cl}" style="border-bottom: 1px solid #fff;">
							<input disabled="disabled" type="checkbox" class="ckbox" value="${ans.id}" <c:if test="${ans.checked}">checked="checked"</c:if>/>
							${status.index+1}、&nbsp;${ans.title}
						</li>
					</c:forEach>
				</ul>
				<div class="btns" style="text-align: right; margin: 3px 3px 10px;">
					<c:if test="${idx!=titlesize}"> 
						<span class="btn"> 
						<a href="viewTestting?testtingInstanceId=${param.testtingInstanceId}&idx=${idx+1}">${i18n.oper.downtitle}</a>
						</span>
					</c:if>
					<span class="btn"> <a
						href="javascript:finish('${dto.id}');">${i18n.goback}</a>
					</span>
				</div>
			</div>
			<div class="clr"></div>
		</div>


		<%@include file="/common/leftmenu.jsp"%>
		<div class="clearfix_b"></div>
		<%@include file="/common/footer.jsp"%>
	</body>
</html>