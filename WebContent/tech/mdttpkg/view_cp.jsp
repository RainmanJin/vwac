<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@include file="/common/head.jsp" %>
	
	<style>
		.form li label{
			width: 150px;
		}
	</style>
	<script type="text/javascript">
	function chgStatus(s,id)
	{
		ajaxGet("status",{"id":id,"status":s},function(d){
			if(d.success)
				location.reload();
			else
				alert(d.data);
		},"json");
	}

	function cfgPlan()
	{
		$.fn.jmodal({
	        title: '排期计划',
	        width:800,
	        content: function(body) {
	            body.html('loading...');
	            body.load('cfgPlan');
	        },
	        buttonText: {cancel: '${i18n.button.cancel}' },
	        okEvent: function(data, args) {
				
	        }
	   });		
	}
	
	function saveCfgPlan(id)
	{
		ajaxGet("saveCfgPlan",{"planID":id,"id":"${dto.id}"},function(d){
			if(d.success)
				location.reload();
			else
				alert(d.data);
		},"json");
	}
	
	function updateName()
	{
		var n = $("#fixedName").val();
		
		if(n == undefined || n=="")
		{
			alert("名称不能为空");
			return;
		}
		
		ajaxPost("name",{"name":n,"id":"${dto.id}"},function(d){
			if(d.success)
				location.reload();
			else
				alert(d.data);
		},"json");
	}
	$(document).ready(function(){
		initJsonForm("#imgForm",function(d){
			location.reload();
		},function(d){
			alert(d.data);
		});
		initJsonForm("#imgOForm",function(d){
			location.reload();
		},function(d){
			alert(d.data);
		});
		initJsonForm("#imgTForm",function(d){
			location.reload();
		},function(d){
			alert(d.data);
		});
	});
	</script>
</head>
<body>
<%@include file="/common/header.jsp" %>

<div class="centerbody centerbox">
	<div id="contentwrapper">
		<div id="content_right">
				<div class="tt">${i18n.mdttpkg.basic==null}</div>
				<ul class="form">
					<li>
						<label>${i18n.scormpkg.filed.name}(DMP)</label>
						<c:choose>
    						<c:when test="${dto.name==null}">
        						<input type="text" name="name" value="${dto.name}" maxlength="100" style="width: 220px"/>
    						</c:when>
    						<c:otherwise>
        						<input type="text" name="name" value="${dto.name}" readonly="readonly" maxlength="100" style="width: 220px" class="gray"/>
    						</c:otherwise> 
    					</c:choose>
						<span class="msg">${i18n.valid.notEmpty}</span>
					</li>
					<li>
						<label>${i18n.scormpkg.filed.name}(iPad)</label>
						<input type="text" id="fixedName" value="${dto.fixedName}" maxlength="100" style="width: 220px" />
						&nbsp;<button type="button" onclick="updateName()">${i18n.button.submit}</button>
					</li>
                   <!-- <li><label>${i18n.scormpkg.filed.motorcycle}</label>
						<d:selectDomain domain="CARTYPE" name="motorcycle" value="${dto.motorcycle}" />
						<em>*</em>
					</li>-->
                   <!--  <li><label>${i18n.domains.osType}</label>
						<d:selectDomain domain="osType" name="osType" value="${dto.osType}" />
						<em>*</em>
					</li>-->
					<%-- <li>
						<label>${i18n.scormpkg.filed.status}</label>
						<input type="text" value="<d:viewDomain domain="MDTT_PKG_STATUS" value="${dto.status}" />" readonly="readonly" class="gray"/>
					</li> --%>
					<!-- 
					<li style="height: 50px;">
						<label>${i18n.scormpkg.filed.remark}</label>
						<textarea name="remark" rows="4" cols="40" style="width: 500px;">${dto.remark}</textarea>
					</li>
					 -->
					 <c:if test="dto.lastUpdateTime!=null">
						<li>
							<label>Last Update</label>
							<input type="text" value="<ecan:dateFormart value="${dto.lastUpdateTime}" formart="yyyy-MM-dd HH:mm:ss" />" readonly="readonly" class="gray"/>
						</li>
					 </c:if>
					<li>
						<label>Version</label>
						<input type="text" value="${dto.version}" readonly="readonly" class="gray"/>
					</li>
					<c:if test="${dto.id==null}">
					<li>
							<label>${i18n.mdttstat.pkgName}</label>
							<input type="file" name="filePath" value="${dto.filePath}"/>
							<span class="msg">${i18n.resourse.name}${i18n.valid.notEmpty}</span>
						</li>
					</c:if>
				</ul>
				
				<div class="tt">${i18n.mdttpkg.trianplan}</div>
				<ul class="form">
					<c:choose>
						<c:when test="${empty course}">
							<div style="color:red;">${i18n.mdttpkg.notrainplan}</div>
						</c:when>
						<c:otherwise>
							<li>
								<label>${i18n.mdttpkg.trainplantime}</label>
								<input value="${plan.yearValue}&nbsp;&nbsp;/&nbsp;&nbsp;${plan.planWeek}" type="text" readonly="readonly" class="gray"/>
								<span class="info">Y / W</span>
							</li>
							<li>
								<label>${i18n.mdttpkg.courseName}</label>
								<input value="${course.name}" type="text" readonly="readonly" class="gray"/>
							</li>
							<li>
								<label>${i18n.scormpkg.filed.brand}</label>
								<input value="<d:viewDomain domain="BRAND" value="${course.brand}" />" type="text" readonly="readonly" class="gray"/>
							</li>
							<li><label>${i18n.scormpkg.filed.proType}</label>
								<input value="<d:viewDomain domain="PROTYPE" value="${course.proType}" />" type="text" readonly="readonly" class="gray"/>
							</li>
						</c:otherwise>
					</c:choose>
				</ul>
				<div class="tt">Preview</div>
					<form id="imgForm" action="img" method="post" class="form" enctype="multipart/form-data">
						<input type="hidden" name="pkgID" value="${dto.id}" />

						<li style="height: 136px;">
							<label>iPad Preview Image(324x232)</label>
							<input type="file" name="img" onchange="$('#imgForm').submit();"/>
							<div style="display: block;">
							<img src="${ctxPath}${dto.iconURL}" width="162" height="116" style="display: block;"/>
							</div>
						</li>						
					</form>
                    <!--
                    <form id="imgOForm" action="oimg" method="post" class="form" enctype="multipart/form-data">
						<input type="hidden" name="pkgID" value="${dto.id}" />

						<li style="height: 136px;">
							<label>Mobile Preview small Image(490x270)</label>
							<input type="file" name="oimg" onchange="$('#imgOForm').submit();"/>
							<div style="display: block;">
							<img src="${ctxPath}${dto.icon1URL}" width="162" height="116" style="display: block;"/>
							</div>
						</li>						
					</form>
                    <form id="imgTForm" action="timg" method="post" class="form" enctype="multipart/form-data">
						<input type="hidden" name="pkgID" value="${dto.id}" />

						<li style="height: 136px;">
							<label>Mobile Preview Image(1080x630)</label>
							<input type="file" name="timg" onchange="$('#imgTForm').submit();"/>
							<div style="display: block;">
							<img src="${ctxPath}${dto.icon2URL}" width="162" height="116" style="display: block;"/>
							</div>
						</li>						
					</form>
			-->
			<div class="btns" style="padding-left: 160px;">
				<span class="btn">
					<a href="javascript:void(0)" onclick="cfgPlan()">${i18n.mdttpkg.assignTrainPlan}</a>
				</span>
				
				<%-- <c:choose>
					<c:when test="${dto.status eq '1'}">
						<span class="btn">
							<a href="javascript:void(0)" onclick="chgStatus('0','${dto.id}')">${i18n.oper.disable}</a>
						</span>
					</c:when>
					<c:otherwise>
						<span class="btn">
							<a href="javascript:void(0)" onclick="chgStatus('1','${dto.id}')">${i18n.oper.active}</a>
						</span>						
					</c:otherwise>
				</c:choose> --%>
				
				<c:choose>
				<c:when test="${empty dto.id}">
				<%-- <span class="btn">
					<a href="${ctxPath}${dto.filePath}" target="_blank">上传课件</a>
				</span> --%>
				</c:when>
				<c:otherwise>
				<span class="btn">
					<a href="${ctxPath}${dto.filePath}" target="_blank">${i18n.mdttpkg.download}</a>
				</span>
				</c:otherwise>
				</c:choose>
				
				<%-- <span class="btn">
					<a href="${ctxPath}${dto.filePath}" target="_blank">${i18n.mdttpkg.download}</a>
				</span> --%>
				<span class="btn">
					<a href="javascript:;" onclick="history.back();">${i18n.button.back}</a>
				</span>
			</div>			
		</div>
	</div>
	
	<%@include file="/common/leftmenu.jsp" %>
	<div class="clearfix_b"></div>
</div>
<%@include file="/common/footer.jsp" %>
</body> 
</html>