<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@include file="/common/head.jsp" %>
	<script type="text/javascript">
		function chgStatus(s,id)
		{
			ajaxGet("status",{"id":id,"status":s},function(d){
				if(d.success)
					location.reload();
				else
					alert(d.data);
			},"json");
		};
		function chgVersion(id){
			$.fn.jmodal({
		        title: '${i18n.resourse.delete}',
		        width:800,
		        content: function(body) {
		            body.html('loading...');
		            body.load('beforeupdate?id='+id);
		        },
		        buttonText: { ok: '${i18n.button.ok}', cancel: '${i18n.button.cancel}' },
		            okEvent: function(data, args) {
			        	$('#myForm1').submit();
			        }
		    });
			
			/* ajaxGet("version",{"id":id},function(d){
				if(d.success)
					location.reload();
				else
					alert(d.data);
			},"json"); */	
		}
	</script>
</head>
<body>
<%@include file="/common/header.jsp" %>

<div class="centerbody centerbox">
	<div id="contentwrapper">
		<div id="content_right">
			<div class="tt">${i18n.mdttpkg.title}</div>
			
			<ec:table action="" method="get" items="list" var="var" view="ecan" width="99%"
					imagePath="${coreImgPath}/table/*.gif" showExports="false">
					<ec:row>
						<ec:column title="${i18n.mdttpkg.filed.name}/DMP" property="name" width="20%">
							<c:choose>
								<c:when test="${var.valid eq '1'}">
									<a href="view?id=${var.id}" class="oper">${var.name}</a>
								</c:when>
								<c:otherwise>
									<span style="color: red; font-weight: bold;">${i18n.mdttpkg.msg.fmtError}|</span>${var.name}
								</c:otherwise>
							</c:choose>
						</ec:column>
						<ec:column title="${i18n.mdttpkg.filed.name}/iPad" property="fixedName" width="20%">
						</ec:column>
						
						<ec:column title="${i18n.mdttpkg.filed.contentType}" property="conentType" width="5%">
                              <c:choose>
								<c:when test="${var.conentType eq 'SSP'}">
									SP
								</c:when>
								<c:otherwise>
									CP
								</c:otherwise>
							</c:choose>
                        </ec:column>
						<ec:column title="${i18n.mdttpkg.filed.proType}" property="proType" width="10%"  filterCell="droplist" filterOptions="DOMAIN_PROTYPE">
							<d:viewDomain value="${var.proType}" domain="PROTYPE" />
						</ec:column>
						<ec:column title="${i18n.mdttpkg.filed.update}" property="lastUpdateTime" width="15%" filterable="false" cell="date" format="yyyy-MM-dd HH:mm"></ec:column>
						<ec:column title="${i18n.mdttpkg.filed.version}" property="version" width="5%" filterable="false"></ec:column>
						<ec:column title="${i18n.mdttpkg.filed.status}" property="status" width="5%" filterCell="droplist" filterOptions="DOMAIN_MDTT_PKG_STATUS">
							<d:viewDomain value="${var.status}" domain="MDTT_PKG_STATUS" />
						</ec:column>
						<ec:column title="${i18n.oper.text}" property="oper" width="15%"
								filterCell="oper" sortable="false">
							<a href="pkgpower?id=${var.id}" class="oper">${i18n.button.pkgpower}</a>
							<a href="view?id=${var.id}" class="oper">${i18n.oper.edit}</a>&nbsp;
							<c:choose>
								<c:when test="${var.status eq '1'}">
									<a href="javascript:void(0)" onclick="chgStatus('0','${var.id}')" class="oper">${i18n.oper.disable}</a>
								</c:when>
								<c:otherwise>
									<a href="javascript:void(0)" onclick="chgStatus('1','${var.id}')" class="oper">${i18n.oper.active}</a>
								</c:otherwise>
								<a href="javascript:void(0)" onclick="chgVersion('${var.id}')" class="oper">${i18n.oper.coursewareUpdate}</a>
							</c:choose>
						</ec:column>
					</ec:row>
			</ec:table>
			<div class="btns">
				<span class="btn"> <a href="view?conentType=SSP">${i18n.button.add}</a></span>
				<!-- <span class="btn"> <a href="view?&conentType=CP">${i18n.button.add}CP</a></span>  -->
			</div>
			<!--<div style="color: #2274AC; line-height: 25px; margin: 20px;">
				标注有"<span style="color: red; font-weight: bold;">格式错误</span>"的课件包为本系统不可识别的课件包格式，请在DMP系统中重新上传课件包<br />系统暂时仅支持PDF和SCORM课件包。
			</div>  -->
			<br /><br />
		</div>
	</div>
	
	<%@include file="/common/leftmenu.jsp"%>
	<div class="clearfix_b"></div>
</div>
<%@include file="/common/footer.jsp" %>
</body> 
</html>