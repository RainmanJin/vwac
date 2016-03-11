<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@include file="/common/head.jsp"%>

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

.rowAns a {
	text-decoration: underline;
	color: blue;
	height: 25px;
	line-height: 25px;
}

#AnsDIV {
	display: inline-block;
}
</style>

<script type="text/javascript">
	initJsonForm("#myFormInfo", function(d) {
		location.href = "index";
	}, function(d) {
		alert(d.data)
	});
</script>
</head>
<body>
	<%@include file="/common/header.jsp"%>

	<div class="centerbody centerbox">
		<div id="contentwrapper">
			<div id="content_right">
				<div class="tt">
					<c:choose>
						<c:when test="${empty mwarticle}">${i18n.appname.mw.newsmanageradd}</c:when>
						<c:otherwise>${i18n.appname.mw.newsmanageredit}</c:otherwise>
					</c:choose>
				</div>
				<c:choose>
					<c:when test="${not empty mwarticle.id}">
						<form id="myFormInfo" action="editres" method="post" class="form">
					</c:when>
					<c:otherwise>
						<form id="myFormInfo" action="save" method="post" class="form">
					</c:otherwise>
				</c:choose>
				<input type="hidden" name="id" value="${mwarticle.id}" />
				<ul>
					<li><label>${i18n.app.title}</label> <input type="text" name="title" value="${mwarticle.title}" v="notEmpty" maxlength="100" /> <span class="msg">${i18n.resourse.name}${i18n.valid.notEmpty}</span><em>*</em>
					</li>
					<li><label>${i18n.app.hoplinks}</label> <input type="text" name="shareUsers" value="${mwarticle.shareUsers}" maxlength="100" />如设置优先内容 <span class="msg">${i18n.resourse.name}${i18n.valid.notEmpty}</span>
					</li>
					<li><label>${i18n.app.coverart}</label> <input type="file" name="filePath" value="${mwarticle.filePath}" /> <span class="msg">${i18n.resourse.name}${i18n.valid.notEmpty}</span></li>
					<li><label>${i18n.domain.filed.idx}</label> <input type="text" name="sort" value="${mwarticle.sort}" maxlength="100" /> <span class="msg">${i18n.resourse.name}${i18n.valid.notEmpty}</span>
					</li>
					<li><label>${i18n.app.article}</label> <textarea style="width: 350px; height: 30px; resize: none" name="contents">${mwarticle.contents}</textarea> <span class="msg">${i18n.resourse.name}${i18n.valid.notEmpty}</span>
					</li>
					<li><label>${i18n.app.annex}</label> <input type="file" name="fj" value="${mwarticle.fj}" /> <span class="msg">${i18n.resourse.name}${i18n.valid.notEmpty}</span></li>
					<li><label>${i18n.message.sendtime}</label> <input type="text" name="creatTime" onclick="WdatePicker()" readonly="readonly" value="${mwarticle.time}" maxlength="15" /> <%-- <ecan:dateInupt name="creatTime" value="${mwarticle.creatTime}" params="v=notEmpty"/> --%>
					</li>
				</ul>
				</form>
				<div class="btns" style="margin-left: 160px;">
					<span class="btn"> <a href="javascript:void(0)" onclick="$('#myFormInfo').submit()">${i18n.button.submit}</a>
					</span> <span class="btn"> <a href="javascript:history.back()">${i18n.button.back}</a>
					</span>
				</div>
			</div>
		</div>

		<%@include file="/common/leftmenu.jsp"%>
		<div class="clearfix_b"></div>
	</div>
	<%@include file="/common/footer.jsp"%>
</body>
</html>