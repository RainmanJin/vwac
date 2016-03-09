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
				body.load("listteachers");//指向控制器中的listteachers方法
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
		$("#CTeacher").val(temp.join(","));
	}

	//选择学员
	var student_map = {};
	function selstudents() {
		$.fn.jmodal({
			title : '${i18n.oper.select}',
			width : 500,
			height : 500,
			content : function(body) {
				body.html('loading...');
				body.load("liststudents");//指向控制器中的liststudents方法
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
						if (!student_map.hasOwnProperty(result[r])
								|| student_map[result[r]] == "no") {

							var tech_user = result[r].split("@");
							student_map[result[r]] = tech_user[1];
						}
					}
				}
				studentsmapToValue();
				args.hide();
			}
		});
	}

	function studentsmapToValue() {
		var temp = [];
		for ( var key in student_map) {
			if (student_map[key] != "no") {
				temp.push(student_map[key]);
			}
		}
		$("#students").val(temp.join(","));
	}

	//选择车型
	var chexi_map = {};
	function selchexi() {
		$.fn.jmodal({
			title : '${i18n.oper.select}',
			width : 500,
			height : 500,
			content : function(body) {
				body.html('loading...');
				body.load("listchexi");//指向控制器中的listchexi方法
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
						if (!chexi_map.hasOwnProperty(result[r])
								|| chexi_map[result[r]] == "no") {

							var tech_user = result[r].split("@");
							chexi_map[result[r]] = tech_user[1];
						}
					}
				}
				chexi_mapmapToValue();
				args.hide();
			}
		});
	}

	function chexi_mapmapToValue() {
		var temp = [];
		for ( var key in chexi_map) {
			if (chexi_map[key] != "no") {
				temp.push(chexi_map[key]);
			}
		}
		$("#chexi").val(temp.join(","));
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
						<c:when test="${empty dto}">试乘试驾新增</c:when>
						<c:otherwise>试乘试驾编辑</c:otherwise>
					</c:choose>
				</div>
				<form id="dbForm" action="save" method="post" class="form">
					<input type="hidden" name="id" value="${dto.id}" />
					<input type="hidden" name="pgfield" value="${dto.pgfield}" />
					<ul style="width: 700px">
						<li style="height: 100px;"><label>名称</label> <textarea style="width: 350px; height: 100px; resize: none" name="CName" v="notEmpty">${dto.CName}</textarea> <span class="msg">名称${i18n.valid.notEmpty}</span><em>*</em></li>
						<li><label>识别码</label><input type="text" name="CCode"
							<c:choose>
						       <c:when test="${empty NewCCode}">
						       value="${dto.CCode}"</c:when> 
						       <c:otherwise>value="${NewCCode}"</c:otherwise> 
						    </c:choose>
							maxlength="255" readOnly="true" class="gray" /></li>

						<li><label> 所属课程 </label> <select name="CCourse" style="width: 350px;">
								<c:forEach items="${CCourselist}" var="var">
									<option <c:if test="${dto.CCourse eq var.name}">selected="selected"</c:if> value="${var.name}">${var.name}</option>
								</c:forEach>
						</select></li>

						<li><label>所属老师</label> <input readOnly="true" id="CTeacher" style="width: 350px;" type="text" name="CTeacher" value="${dto.CTeacher}" maxlength="255" /> <span class="btn"
							style="margin-left: 20px;"> <a href="javascript:selusers();">${i18n.button.add}</a>
						</span></li>
						<li><label>参加学员</label> <input readOnly="true" id="students" style="width: 350px;" type="text" name="students" value="${dto.students}" maxlength="255" /> <span class="btn"
							style="margin-left: 20px;"> <a href="javascript:selstudents();">${i18n.button.add}</a>
						</span></li>
						<li><label>参加车型</label> <input readOnly="true" id="chexi" style="width: 350px;" type="text" name="chexi" value="${dto.chexi}" maxlength="255" /> <span class="btn"
							style="margin-left: 20px;"> <a href="javascript:selchexi();">${i18n.button.add}</a>
						</span></li>
						<li><label>场地</label> <label style="width: 40px;">公路:</label><input style="width: 20px;" type="checkbox" name="gonglu" <c:if test="${fn:contains(dto.changdi,'0')}">checked="checked"</c:if> />
							<label style="width: 40px;">运动:</label><input style="width: 20px;" type="checkbox" name="yundong" <c:if test="${fn:contains(dto.changdi,'1')}">checked="checked"</c:if> /> <label
							style="width: 40px;">越野:</label><input style="width: 20px;" type="checkbox" name="yueye" <c:if test="${fn:contains(dto.changdi,'2')}">checked="checked"</c:if> /></li>
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