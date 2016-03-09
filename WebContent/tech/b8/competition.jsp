<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/context.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/common/head.jsp"%>
<style>
.tableHeader {
  background-color: #f4f4f4;
  border-color: #fff;
  color: #777;
  font-size: 13px;
  font-weight: bold;
  height: 20px;
  line-height: 20px;
  margin: 0;
  padding: 1px 3px 2px;
}
</style>
<script type="text/javascript">
	function search() {
		var isCheck = $('#check1').attr('checked');
		var txtDate = $('#date').val();
		if (txtDate.length < 10) {
			alert('请先选择日期！');
			return;
		}
		ajaxGet("${ctxPath}/techc/b8/searchCom", {
			"isCheck" : isCheck,
			"txtDate" : txtDate
		}, function(d) {
			if (d.success) {
				if (d.data.length <= 0) {
					alert('本日无数据！');
					return;
				}
				$('#ta tr:gt(0)').remove();
				$('#ta').append(d.data);
			} else {
				alert(d.data);
			}
		}, "json");
	}
	function competitionexport() {
		var isCheck = $('#check1').attr('checked');
		var txtDate = $('#date').val();
		if (txtDate.length < 10) {
			alert('请先选择日期！');
			return;
		}
		window.open("${ctxPath}/techc/b8/competitionexport?isCheck=" + isCheck
				+ "&txtDate=" + txtDate);
	}
</script>
</head>
<body>
  <%@include file="/common/header.jsp"%>
  <div class="centerbody centerbox">
    <div id="contentwrapper">
      <div id="content_right">
        <div class="tt">${i18n.appname.mw.competition}</div>
        <label style="font-size: 14px; line-height: 30px; font-weight: bold;">选择日期:&nbsp;</label> <input type="text"
          id="date" name="date" value="" onclick="WdatePicker()" maxlength="15" /><input type="checkbox" id="check1"
          name="check1" style="margin-left: 30px; vertical-align: middle" /><label for="check1"
          style="vertical-align: middle">是否为单独课件包（移动学院数据请不要勾选)</label><span class="btn"
          style="float: right; vertical-align: middle"> <a href="javascript:competitionexport();">导出</a>
        </span><span class="btn" style="float: right; vertical-align: middle"> <a href="javascript:search();">查询</a>
        </span>
        <table border="1" id="ta" width="99%" style="border-collapse: collapse; margin-top: 20px">
          <tr align="center" class="tableHeader">
            <td><b style="font-weight: bold">车型</b></td>
            <td><b style="font-weight: bold">大项</b></td>
            <td><b style="font-weight: bold">子项</b></td>
            <td><b style="font-weight: bold">分值</b></td>
          </tr>
        </table>
      </div>
    </div>
    <%@include file="/common/leftmenu.jsp"%>
    <div class="clearfix_b"></div>
  </div>
  <%@include file="/common/footer.jsp"%>
</body>
</html>