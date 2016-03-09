<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/context.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- 调查管理 -->
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
		ajaxGet("${ctxPath}/techc/b8/search", {
			"isCheck" : isCheck,
			"txtDate" : txtDate
		}, function(d) {
			if (d != undefined && d.length > 0) {
				var strArr = d.split(',');
				$("#td01").html(strArr[0]);
				$("#td02").html(strArr[1]);
				$("#td03").html(strArr[2]);
				$("#td04").html(strArr[3]);
				$("#td05").html(strArr[4]);
				$("#td06").html(strArr[5]);
				$("#td07").html(strArr[6]);
				$("#td08").html(strArr[7]);
				$("#td09").html(strArr[8]);
				$("#td10").html(strArr[9]);
				$("#td11").html(strArr[10]);
				$("#td12").html(strArr[11]);
				$("#td13").html(strArr[12]);
				$("#td14").html(strArr[13]);
				$("#td15").html(strArr[14]);
				$("#td16").html(strArr[15]);
				$("#td17").html(strArr[16]);
				$("#td18").html(strArr[17]);
			} else {
				alert(d);
			}
		}, "string");
	}
	function funexport() {
		var isCheck = $('#check1').attr('checked');
		var txtDate = $('#date').val();
		if (txtDate.length < 10) {
			alert('请先选择日期！');
			return;
		}
		window.open("${ctxPath}/techc/b8/funexport?isCheck=" + isCheck
				+ "&txtDate=" + txtDate);
	}
</script>
</head>
<body>
  <%@include file="/common/header.jsp"%>
  <div class="centerbody centerbox">
    <div id="contentwrapper">
      <div id="content_right">
        <div class="tt">${i18n.appname.mw.funsystem}</div>
        <label style="font-size: 14px; line-height: 30px; font-weight: bold;">选择日期:&nbsp;</label> <input type="text"
          id="date" name="date" value="" onclick="WdatePicker()" maxlength="15" /><input type="checkbox" id="check1"
          name="check1" style="margin-left: 30px; vertical-align: middle" /><label for="check1"
          style="vertical-align: middle">是否为单独课件包（移动学院数据请不要勾选)</label> <span class="btn"
          style="float: right; vertical-align: middle"> <a href="javascript:funexport();">导出</a>
        </span><span class="btn" style="float: right; vertical-align: middle"> <a href="javascript:search();">查询</a>
        </span>
        <table border="1" id="ta" width="99%" style="border-collapse: collapse; margin-top: 20px">
          <tr align="center" class="tableHeader">
            <td width="300px" style="color: #777;"><b style="font-weight: bold">信息娱乐系统评分项</b></td>
            <td width="200px" style="color: #777;"><b style="font-weight: bold">结果</b></td>
            <td width="200px" style="color: #777;"><b style="font-weight: bold">得分数量</b></td>
          </tr>
          <tr align="center">
            <td rowspan="6">全新信息娱乐系统总体印象</td>
            <td>很好</td>
            <td id="td06"></td>
          </tr>
          <tr align="center">
            <td>好</td>
            <td id="td05"></td>
          </tr>
          <tr align="center">
            <td>较好</td>
            <td id="td04"></td>
          </tr>
          <tr align="center">
            <td>较差</td>
            <td id="td03"></td>
          </tr>
          <tr align="center">
            <td>差</td>
            <td id="td02"></td>
          </tr>
          <tr align="center">
            <td>很差</td>
            <td id="td01"></td>
          </tr>
          <tr align="center">
            <td rowspan="6">MIB II的新功能总体印象</td>
            <td>完全非常赞同</td>
            <td id="td12"></td>
          </tr>
          <tr align="center">
            <td>赞同</td>
            <td id="td11"></td>
          </tr>
          <tr align="center">
            <td>偏向于赞同</td>
            <td id="td10"></td>
          </tr>
          <tr align="center">
            <td>偏向于不赞同</td>
            <td id="td09"></td>
          </tr>
          <tr align="center">
            <td>不赞同</td>
            <td id="td08"></td>
          </tr>
          <tr align="center">
            <td>完全不赞同</td>
            <td id="td07"></td>
          </tr>
          <tr align="center">
            <td rowspan="6">MIB II赢得客户的潜力预计</td>
            <td>非常高</td>
            <td id="td18"></td>
          </tr>
          <tr align="center">
            <td>高</td>
            <td id="td17"></td>
          </tr>
          <tr align="center">
            <td>较高</td>
            <td id="td16"></td>
          </tr>
          <tr align="center">
            <td>较低</td>
            <td id="td15"></td>
          </tr>
          <tr align="center">
            <td>低</td>
            <td id="td14"></td>
          </tr>
          <tr align="center">
            <td>非常低</td>
            <td id="td13"></td>
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