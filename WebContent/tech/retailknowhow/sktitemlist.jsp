<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/context.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- 配置项目 -->
<%@include file="/common/head.jsp"%>
<script type="text/javascript">
	function delsktitem(id) {
		if (confirm("${i18n.delete.comfirm}")) {
			ajaxGet("${ctxPath}/techc/retailknowhow/delsktitem", {
				"id" : id
			}, function(d) {
				if (d.success) {
					location.reload();
				} else {
					alert(d.data);
				}
			}, "json");
		}
	}
</script>
</head>
<body>
  <%@include file="/common/header.jsp"%>
  <div class="centerbody centerbox">
    <div id="contentwrapper">
      <div id="content_right">
        <div class="tt">
          配置项目 <span class="btn" style="float: right;"><a href="sktchapterlist?id=${var.cid}">${i18n.button.back}</a></span>
          <span class="btn" style="float: right;"> <a
            href="addsktitem?chapterid=${param.id}&?cid=${param.cid}&type=${param.type}">${i18n.button.add}</a>
          </span>
        </div>
        <ec:table action="sktitemlist" method="get" items="dblist" var="var" view="ecan" width="99%"
          imagePath="${coreImgPath}/table/*.gif" showExports="false">
          <ec:row>
            <ec:column title="序号" property="id" width="10%">${var.id}</ec:column>
            <ec:column title="内容" property="cname">
              ${var.cname}
            </ec:column>
            <ec:column title="颜色" property="ccolor" width="10%">
              <c:choose>
                <c:when test="${var.ccolor eq '1'}">绿 </c:when>
                <c:when test="${var.ccolor eq '2'}">深绿 </c:when>
                <c:when test="${var.ccolor eq '3'}">蓝 </c:when>
                <c:when test="${var.ccolor eq '4'}">深蓝 </c:when>
                <c:when test="${var.ccolor eq '5'}">橙 </c:when>
                <c:otherwise></c:otherwise>
              </c:choose>
            </ec:column>
            <ec:column title="样式" property="ctype" width="10%">
              <c:choose>
                <c:when test="${var.ctype eq '1'}">六边形</c:when>
                <c:when test="${var.ctype eq '2'}">五边形</c:when>
                <c:when test="${var.ctype eq '3'}">正方形</c:when>
                <c:when test="${var.ctype eq '4'}">椭圆</c:when>
                <c:otherwise></c:otherwise>
              </c:choose>
            </ec:column>
            <ec:column title="${i18n.oper.text}" property="oper" width="18%" filterCell="oper" sortable="false">
              <a href="addsktitem?chapterid=${param.id}&?cid=${param.cid}&type=${param.type}" class="oper">添加子项</a>
              <a href="editsktitem?id=${var.id}" class="oper">${i18n.oper.edit}</a>
              <a href="javascript:void(0);" onclick="delsktitem('${var.id}')" class="oper">${i18n.oper.del}</a>
            </ec:column>
          </ec:row>
        </ec:table>
      </div>
    </div>
    <%@include file="/common/leftmenu.jsp"%>
    <div class="clearfix_b"></div>
  </div>
  <%@include file="/common/footer.jsp"%>
</body>
</html>