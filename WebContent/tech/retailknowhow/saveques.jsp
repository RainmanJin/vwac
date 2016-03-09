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
			location.href = "sktchapterlist?id=${param.cid}";
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
            <c:when test="${empty dto}">配置题目新增</c:when>
            <c:otherwise>配置题目编辑</c:otherwise>
          </c:choose>
        </div>
        <form id="dbForm" action="quessave" method="post" class="form">
          <input type="hidden" name="id" value="${dto.id}" /> <input type="hidden" name="cid" value="${param.cid}" />
          <ul style="width: 700px">
            <li><label>类型</label> <select name="type" style="width: 150px;">
                <option value="1">结构图</option>
                <option value="2">排序图</option>
                <option value="3">树状图</option>
            </select></li>
            <li><label>题目名称</label> <input style="width: 350px;" type="text" name="CName" v="notEmpty"
              maxlength="255" value="${dto.CName}"></> <span class="msg">题目名称${i18n.valid.notEmpty}</span><em>*</em></li>
            <li><label>是否锁定</label><input type="checkbox" name="islock"
              <c:if test="${dto.islock eq '1'}">checked="checked"</c:if> /></li>
            <li>
              <li><label>排序</label> <input style="width: 150px;" type="text" name="sort" v="notEmpty"
                maxlength="255" value="${dto.sort}"></> <span class="msg">填写数字</span><em>*</em></li>
              <li style="height: 100px;"><label>课程介绍</label> <textarea
                  style="width: 350px; height: 100px; resize: none" name="content" v="notEmpty">${dto.content}</textarea>
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