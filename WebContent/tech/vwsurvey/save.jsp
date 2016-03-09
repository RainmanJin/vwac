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

.rowAns .ans1 {
  width: 300px;
}

.rowAns .ans2 {
  width: 100px;
}

#AnsDIV {
  display: inline-block;
}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		initJsonForm("#dbForm", function(d) {
			alert(d.data);
			location.href = "index";
		}, function(d) {
			alert(d.data);
		});
	});

	//选择老师
	var user_map = {};
	function selusers() {
		$.fn.jmodal({
			title : '${i18n.oper.select}',
			width : 500,
			height : 500,
			content : function(body) {
				body.html('loading...');
				body.load("listteachers");
			},
			buttonText : {
				ok : '${i18n.button.add}',
				cancel : '${i18n.button.cancel}'
			},
			okEvent : function(data, args) {
				var uids = checkedByName("selIds");
				if (uids) {
					var result = uids.split(",");
					for ( var r = 0; r < result.length; r++) {
						if (!user_map.hasOwnProperty(result[r])
								|| user_map[result[r]] == "no") {

							var tech_user = result[r].split("@");
							user_map[result[r]] = tech_user[1];
						}
					}
				}
				mapToValue();
				args.hide();
			}
		});
	}

	function mapToValue() {
		var temp = [];
		for ( var key in user_map) {
			if (user_map[key] != "no") {
				temp.push(user_map[key]);
			}
		}
		$("#CTearcher").val(temp.join(","));
	}
</script>
</head>
<body>
  <%@include file="/common/header.jsp"%>
  <div class="centerbody centerbox">
    <div id="contentwrapper">
      <div id="content_right">
        <div class="tt">
          <c:choose>
            <c:when test="${empty dto}">${i18n.app.newinvestigation}</c:when>
            <c:otherwise>${i18n.app.editorinvestigation}</c:otherwise>
          </c:choose>
        </div>

        <form id="dbForm" action="save" method="post" class="form">
          <input type="hidden" name="id" value="${dto.id}" />
          <ul style="width: 700px">
            <li style="height: 100px;"><label>${i18n.survey.name}</label> <textarea
                style="width: 350px; height: 100px; resize: none" name="CTitle" v="notEmpty">${dto.CTitle}</textarea> <span
              class="msg">调查名称${i18n.valid.notEmpty}</span><em>*</em></li>
            <li><label>${i18n.operman.starttime}</label> <ecan:dateInupt name="dtStartDate"
                value="${dto.dtStartDate}" params="v=notEmpty" /> <span class="msg">${i18n.operman.starttime}${i18n.valid.notEmpty}</span><em>*</em></li>
            <li><label>${i18n.operman.endtime}</label> <ecan:dateInupt name="dtOverDate" value="${dto.dtOverDate}"
                params="v=notEmpty" /> <span class="msg">${i18n.operman.endtime}${i18n.valid.notEmpty}</span><em>*</em></li>
            <li><label> ${i18n.app.trainingcoursetitle} </label> <select name="CCourse" style="width: 350px;">
                <c:forEach items="${CCourselist}" var="var">
                  <option <c:if test="${dto.CCourse eq var.name}">selected="selected"</c:if> value="${var.name}">${var.name}</option>
                </c:forEach>
            </select></li>
            <li><label>${i18n.app.coursestarttime}</label> <ecan:dateInupt name="courseStart" value="${dto.courseStart}"
                params="v=notEmpty" /><span class="msg">课程开始时间${i18n.valid.notEmpty}</span><em>*</em></li>
            <li><label>${i18n.app.courseendtime}</label> <ecan:dateInupt name="courseEnd" value="${dto.courseEnd}" params="v=notEmpty" />
              <span class="msg">课程结束时间${i18n.valid.notEmpty}</span><em>*</em></li>
            <li><label>${i18n.app.trainer}</label> <input readOnly="true" id="CTearcher" style="width: 350px;" type="text"
              name="CTearcher" value="${dto.CTearcher}" maxlength="255" /> <span class="btn" style="margin-left: 20px;">
                <a href="javascript:selusers();">${i18n.button.add}</a>
            </span></li>
            <li style="height: 100px;"><label>${i18n.app.trainingsite}</label> <textarea
                style="width: 350px; height: 100px; resize: none" name="CAdrees" v="notEmpty">${dto.CAdrees}</textarea><span
              class="msg">培训地点${i18n.valid.notEmpty}</span><em>*</em></li>
            <li><label>${i18n.app.trainingnumber}</label><input type="text" name="pxnum" value="${dto.pxnum}" maxlength="50" /></li>
            <li><label>${i18n.app.compeltejumpaddress}</label><input style="width: 350px;" type="text" name="CReturnUrl"
              value="${dto.CReturnUrl}" maxlength="255" /></li>
            <li><label>${i18n.app.verify}</label><input type="checkbox" name="NCodeSurvey"
              <c:if test="${dto.NCodeSurvey eq '1'}">checked="checked"</c:if> /></li>
            <li><label>${i18n.app.invescoursecode}</label><input type="text" name="CCode" value="${dto.CCode}" maxlength="255" /></li>
            <li><label>${i18n.resourse.type}</label> <d:selectDomain domain="DCLX" uid="DCLX" name="DCLX" value="${dto.NType}" /></li>
            <li><label> ${i18n.app.surveytemplates}</label> <select name="NSysId" style="width: 350px;">
                <c:forEach items="${list}" var="var">
                  <option <c:if test="${dto.NSysId eq var.id}">selected="selected"</c:if> value="${var.id}">${var.CTitle}</option>
                </c:forEach>
            </select></li>
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