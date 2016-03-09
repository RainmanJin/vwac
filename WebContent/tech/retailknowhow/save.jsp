<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@include file="/common/head.jsp"%>
<script type="text/javascript">
	$(document).ready(function() {
		initJsonForm("#dbForm", function(d) {
			alert(d.data);
			location.href = "index";
		}, function(d) {
			alert(d.data);
		});
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
            <c:when test="${empty dto}">课堂卡片新增</c:when>
            <c:otherwise>课堂卡片编辑</c:otherwise>
          </c:choose>
        </div>

        <form id="dbForm" action="save" method="post" class="form">
          <input type="hidden" name="id" value="${dto.id}" />
          <ul style="width: 700px">
            <li><label>知识名称</label> <input style="width: 350px;" type="text" name="CName" v="notEmpty"
              maxlength="255" value="${dto.CName}"></> <span class="msg">知识名称${i18n.valid.notEmpty}</span><em>*</em></li>
            <li><label>识别码</label> <input style="width: 350px;" type="text" name="CCode" v="notEmpty"
              maxlength="255" value="${dto.CCode}"></> <span class="msg">识别码${i18n.valid.notEmpty}</span><em>*</em></li>
            <li><label>是否锁定</label><input type="checkbox" name="islocked"
              <c:if test="${dto.islocked eq '1'}">checked="checked"</c:if> /></li>
            <li>
              <li style="height: 100px;"><label>课程介绍</label> <textarea
                  style="width: 350px; height: 100px; resize: none" name="CContent" v="notEmpty">${dto.CContent}</textarea>
                <span class="msg">课程介绍${i18n.valid.notEmpty}</span><em>*</em></li>
          </ul>
        </form>

        <div class="btns" style="padding-left: 100px;">
          <span class="btn"> <a href="javascript:void(0);" onclick="$('#dbForm').submit();">${i18n.button.submit}</a>
          </span> <span class="btn"> <a href="javascript:history.back();">${i18n.button.back}</a>
          </span>
        </div>
      </div>
    </div>
    <br /> <br />
    <%@include file="/common/leftmenu.jsp"%>
    <div class="clearfix_b"></div>
  </div>
  <%@include file="/common/footer.jsp"%>
</body>
</html>