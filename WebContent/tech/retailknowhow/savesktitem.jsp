<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@include file="/common/head.jsp"%>
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						initJsonForm(
								"#dbForm",
								function(d) {
									alert(d.data);
									location.href = "sktitemlist?id=${param.chapterid}&cid=${param.cid}&type=${param.type}";
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
            <c:when test="${empty dto}">配置项目新增</c:when>
            <c:otherwise>配置项目编辑</c:otherwise>
          </c:choose>
        </div>
        <form id="dbForm" action="sktitemsave" method="post" class="form">
          <input type="hidden" name="id" value="${dto.id}" /> <input type="hidden" name="chapterid"
            value="${param.chapterid}" /> <input type="hidden" name="cid" value="${param.cid}" />
          <ul style="width: 700px">
          <ul style="width: 700px">
            <li><label>项目名称</label> <input style="width: 350px;" type="text" name="cname" v="notEmpty"
              maxlength="255" value="${dto.cname}"></> <span class="msg">项目名称${i18n.valid.notEmpty}</span><em>*</em></li>
            <li><label>设定颜色</label> <select name="ccolor" style="width: 150px;">
                <option value="0">默认</option>
                <option value="1">绿</option>
                <option value="2">深绿</option>
                <option value="3">蓝</option>
                <option value="4">深蓝</option>
                <option value="5">橙</option>
            </select></li>
            <li><label>样式</label><select name="ctype" style="width: 150px;">
                <option value="0">默认</option>
                <option value="1">六边形</option>
                <option value="2">五边形</option>
                <option value="3">正方形</option>
                <option value="4">椭圆</option>
            </select></li>
            <li><label>所属上级</label><select name="colpath" style="width: 350px;">
                <c:forEach items="${mwsktitemlist}" var="item">
                  <option value="${item.id}">${item.cname}</option>
                </c:forEach>
            </select></li>
            <li><label>排序</label> <input style="width: 100px;" type="text" name="sort" v="notEmpty" maxlength="255"
              value="${dto.sort}"></> <span class="msg">填写数字</span><em>*</em></li>
            <li><label>预留1</label> <input style="width: 100px;" type="text" name="a1" v="notEmpty" maxlength="255"
              value="${dto.a1}"></> <span class="msg">填写数字</span><em>*</em></li>
            <li><label>预留2</label> <input style="width: 100px;" type="text" name="a2" v="notEmpty" maxlength="255"
              value="${dto.a2}"></><span class="msg">填写数字</span><em>*</em></li>
            <li><label>预留3</label> <input style="width: 100px;" type="text" name="a3" maxlength="255"
              value="${dto.a3}"></></li>
            <li><label>预留4</label> <input style="width: 100px;" type="text" name="a4" maxlength="255"
              value="${dto.a4}"></></li>
            <li>
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