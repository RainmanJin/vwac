<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@include file="/common/head.jsp"%>

<style>
.form li label {
	width: 150px;
}
</style>
<script type="text/javascript">
	var url = "index";
	$(document).ready(function() {
		//index?ec_i=ec&ec_crd=30&ec_f_a=&ec_p=2&ec_s_name=&ec_s_fixedName=&ec_s_conentType=&ec_s_proType=&ec_s_lastUpdateTime=&ec_s_version=&ec_s_status=&ec_f_name=&ec_f_fixedName=&ec_f_conentType=&ec_f_proType=&ec_f_status=&ec_rd=30
		url = document.referrer;
		initJsonForm("#myForm", function(d) {
			alert("${i18n.commit.success}");
			location.href = url;
		}, function(d) {
			alert(d.data);
		});

		initJsonForm("#imgForm", function(d) {
			location.reload();
		}, function(d) {
			alert(d.data);
		});
		initJsonForm("#imgOForm", function(d) {
			location.reload();
		}, function(d) {
			alert(d.data);
		});
		initJsonForm("#imgTForm", function(d) {
			location.reload();
		}, function(d) {
			alert(d.data);
		});

		$("#motorcycle").focus(function() {
			$.fn.jmodal({
				title : '${i18n.oper.select}',
				width : 460,
				height : 300,
				content : function(body) {
					body.html('loading...');
					//body.load("listtag?id=${dto.id}");
					body.load("listtag");//id值没有使用
				},
				buttonText : {
					ok : '${i18n.button.add}',
					cancel : '${i18n.button.cancel}'
				},
				okEvent : function(data, args) {
					var selVal = [];
					var rightSel = $("#selectR");
					rightSel.find("option").each(function() {
						selVal.push(this.value);
					});
					selVals = selVal.join(",");
					if (selVals == "") {
						alert("没有选择任何项！");
					} else {
						$("#motorcycle").val(selVals);
						args.hide();
						/*ajaxPost("chgtags", {
							"id" : "${dto.id}",
							"tagid" : selVals
						}, function(r) {
							if (r.success) {
								location.reload();
							} else {
								alert(r.data);
							}
						}, "json");*/
					}
				}

			});
		});
	});
</script>
</head>
<body>
	<%@include file="/common/header.jsp"%>

	<div class="centerbody centerbox">
		<div id="contentwrapper">
			<div id="content_right">
				<div class="tt">${i18n.mdttpkg.detail}</div>
				<form id="myForm" action=<c:choose>
						<c:when test="${dto.id!=null}">
							"saveSSP"
						</c:when>
						<c:otherwise>
							"addSSP"
						</c:otherwise>
					</c:choose>
					method="post" class="form" enctype="multipart/form-data">
					<input type="hidden" name="id" value="${dto.id}" />
					<%-- <input type="hidden" name="status" value="${dto.status}"/> --%>
					<ul>
						<c:choose>
							<c:when test="${dto.name==null}">
								<li><label>${i18n.scormpkg.filed.name}(DMP)</label> <input type="text" name="name" value="${dto.name}" maxlength="100" style="width: 220px" v="notEmpty" v="notEmpty" /> <span class="msg">${i18n.valid.notEmpty}</span>
									<em>*</em></li>
								<li><label>${i18n.scormpkg.filed.name}(iPad)</label> <input type="text" name="fixedName" value="${dto.fixedName}" maxlength="100" style="width: 220px" v="notEmpty" /> <span class="msg">${i18n.valid.notEmpty}</span>
									<em>*</em></li>
								<li><label>${i18n.scormpkg.filed.brand}</label> <d:selectDomain domain="BRAND" name="brand" value="${dto.brand}" /> <em>*</em></li>
								<li><label>${i18n.scormpkg.filed.proType}</label> <d:selectDomain domain="PROTYPE" name="proType" value="${dto.proType}" /> <em>*</em></li>

								<li><label>${i18n.domains.osType}</label> <d:selectDomain domain="osType" name="osType" value="${dto.osType}" /> <em>*</em></li>
								<li><label>${i18n.mdttpkg.learntype}</label> <select name="conentType" style="width: 150px;">
										<option value="1">自学材料</option>
										<option value="2">课堂学习</option>
								</select></li>

								<c:if test="${dto.lastUpdateTime!=null}">
									<li><label>Last Update</label> <input type="text" value="<ecan:dateFormart value="${dto.lastUpdateTime}" formart="yyyy-MM-dd HH:mm:ss" />" readonly="readonly" class="gray" /></li>
								</c:if>
								<li><label>Version</label> <input type="text" value="${dto.version}" readonly="readonly" class="gray" /></li>
								<li><label>${i18n.mdttpkg.filed.status}</label> <input type="text" value="${i18n.domainlabel.userstatus.disable}" readonly="readonly" class="gray" /></li>
								<li><label>${i18n.mdttstat.pkgName}</label> <input type="file" name="filePath" value="${dto.filePath}" /> <span class="msg">${i18n.resourse.name}${i18n.valid.notEmpty}</span></li>
							</c:when>

							<c:otherwise>
								<li><label>${i18n.scormpkg.filed.name}(DMP)</label> <input type="text" name="name" value="${dto.name}" readonly="readonly" maxlength="100" style="width: 220px" class="gray" /> <span
									class="msg">${i18n.valid.notEmpty}</span></li>
								<li><label>${i18n.scormpkg.filed.name}(iPad)</label> <input type="text" name="fixedName" value="${dto.fixedName}" maxlength="100" style="width: 220px" v="notEmpty" /> <span class="msg">${i18n.valid.notEmpty}</span>
									<em>*</em></li>
								<li><label>${i18n.scormpkg.filed.brand}</label> <d:selectDomain domain="BRAND" name="brand" value="${dto.brand}" /> <em>*</em></li>
								<li><label>${i18n.scormpkg.filed.proType}</label> <d:selectDomain domain="PROTYPE" name="proType" value="${dto.proType}" /> <em>*</em></li>
								<%-- <li><label>${i18n.scormpkg.filed.motorcycle}</label>
								<d:selectDomain domain="CARTYPE" name="motorcycle" value="${dto.motorcycle}" />
								<em>*</em>
							</li> --%>
								<!--改造资料库修改标签  -->
								<li style="height: 85px;"><label>${i18n.scormpkg.filed.motorcycle}</label> <textarea id="motorcycle" name="motorcycle" v="notEmpty" style="width: 500px; height: 80px;">${dto.motorcycle}</textarea>
									<span class="msg">${i18n.valid.notEmpty}</span></li>
								<!--改造资料库修改标签  -->
								<li><label>${i18n.domains.osType}</label> <d:selectDomain domain="osType" name="osType" value="${dto.osType}" /> <em>*</em></li>
								<%-- <li>
								<label>${i18n.scormpkg.filed.status}</label>
								<d:selectDomain domain="STATUS" name="status" value="${dto.status}" />
							</li> --%>

								<li style="height: 50px;"><label>${i18n.scormpkg.filed.remark}</label> <textarea name="remark" rows="4" cols="40" style="width: 500px;">${dto.remark}</textarea></li>
								<c:if test="${dto.lastUpdateTime!=null}">
									<li><label>Last Update</label> <input type="text" value="<ecan:dateFormart value="${dto.lastUpdateTime}" formart="yyyy-MM-dd HH:mm:ss" />" readonly="readonly" class="gray" /></li>
								</c:if>
								<li><label>Version</label> <input type="text" value="${dto.version}" readonly="readonly" class="gray" /></li>
								<li><label>${i18n.mdttpkg.filed.status}</label> <d:selectDomain domain="MDTT_PKG_STATUS" name="status" value="${dto.status}" /> <em>*</em></li>
							</c:otherwise>
						</c:choose>

					</ul>
				</form>

				<c:if test="${dto.id!=null}">
					<div class="tt">Preview</div>
					<form id="imgForm" action="img" method="post" class="form" enctype="multipart/form-data">
						<input type="hidden" name="pkgID" value="${dto.id}" />
						<li style="height: 136px;"><label>iPad Preview Image(324x232)</label> <input type="file" name="img" onchange="$('#imgForm').submit();" />
							<div style="display: block;">
								<img src="${ctxPath}${dto.iconURL}" width="162" height="116" style="display: block;" />
							</div></li>
					</form>
					<form id="imgOForm" action="oimg" method="post" class="form" enctype="multipart/form-data">
						<input type="hidden" name="pkgID" value="${dto.id}" />

						<li style="height: 136px;"><label>Mobile Preview small Image(490x270)</label> <input type="file" name="oimg" onchange="$('#imgOForm').submit();" />
							<div style="display: block;">
								<img src="${ctxPath}${dto.icon1URL}" width="162" height="116" style="display: block;" />
							</div></li>
					</form>
					<form id="imgTForm" action="timg" method="post" class="form" enctype="multipart/form-data">
						<input type="hidden" name="pkgID" value="${dto.id}" />

						<li style="height: 136px;"><label>Mobile Preview Image(1080x630)</label> <input type="file" name="timg" onchange="$('#imgTForm').submit();" />
							<div style="display: block;">
								<img src="${ctxPath}${dto.icon2URL}" width="162" height="116" style="display: block;" />
							</div></li>
					</form>
				</c:if>
				<div class="btns" style="padding-left: 160px;">
					<span class="btn"> <a href="javascript:void(0)" onclick="$('#myForm').submit()">${i18n.button.save}</a>
					</span>

					<c:if test="${dto.id!=null}">
						<span class="btn"> <a href="${ctxPath}${dto.filePath}" target="_blank">${i18n.mdttpkg.download}</a>
						</span>
					</c:if>
					<%-- <span class="btn">
					<a href="${ctxPath}${dto.filePath}" target="_blank">${i18n.mdttpkg.download}</a>
				</span> --%>
					<span class="btn"> <a href="javascript:;" onclick="history.back();">${i18n.button.back}</a>
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