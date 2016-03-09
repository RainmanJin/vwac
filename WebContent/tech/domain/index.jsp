<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@include file="/common/head.jsp" %>
</head>
<body>
<%@include file="/common/header.jsp" %>
<script type="text/javascript">
	function changeDomain(v)
	{
		this.location.href = "${ctxPath}/techc/domain/index?domain=" + v;
	}
	
	function status(id,del)
	{
			ajaxGet("${ctxPath}/techc/domain/status",{"id":id,"del":del},function(d){
				if(d.success)
					location.reload();
				else
					alert(d.data);
			},"json");
	}
	
	function edit(domainvalue)
	{
		if(!domainvalue)
		{
			domainvalue = "";
		}
		
		$.fn.jmodal({
            title: '${i18n.domain.title}',
            width:600,
            content: function(body) {
                body.html('loading...');
                body.load('${ctxPath}/techc/domain/view?domain=${domain.id}&value=' + domainvalue);
            },
            buttonText: { ok: '${i18n.button.ok}', cancel: '${i18n.button.cancel}' },
            okEvent: function(data, args) {
            	//args.hide();
            	$("#domainForm").submit();
            }
        });
	}
</script>
<div class="centerbody centerbox">
	<div id="contentwrapper">
		<div id="content_right">
			<div class="tt">
					<label style="font-size: 14px; line-height: 30px; font-weight: bold;">${i18n.text.please_select_domain}:&nbsp;</label>
					<select onchange="changeDomain(this.value)">
						<c:forEach items="${domains}" var="d">
							<option value="${d.id}" <c:if test="${domain.id eq d.id}">selected="selected"</c:if>>&nbsp;&nbsp;${d.i18nRemark}&nbsp;&nbsp;</option>
						</c:forEach>
					</select>
			</div>
			
			<div><b class="bold">${domain.i18nRemark}</b>&nbsp;${i18n.text.childs_of}：</div>
			<ec:table action="" items="values" var="var" view="ecan" width="100%"
					imagePath="${coreImgPath}/table/*.gif" showExports="false" filterable="false" showStatusBar="false" sortable="false" rowsDisplayed="1000">
					<ec:row highlightRow="true">
						<ec:column title="${i18n.domain.filed.label}" property="domainlabel" width="40%"></ec:column>
						<ec:column title="${i18n.domain.filed.value}" property="domainvalue" width="15%"></ec:column>
						<ec:column title="${i18n.domain.filed.idx}" property="indexsequnce" width="15%" ></ec:column>
						<ec:column title="${i18n.i18n.filed.status}" property="indexsequnce" width="8%" >
						<c:choose>
								<c:when test="${var.isDelete eq '1'}">${i18n.oper.disable}</c:when>
								<c:otherwise>${i18n.oper.active}</c:otherwise>
							</c:choose>
						</ec:column>
						<ec:column title="${i18n.oper.text}" property="OPER" width="20%" >
							<a href="#nogo" onclick="edit('${var.domainvalue}')" class="oper">${i18n.oper.edit}</a>&nbsp;
							<c:choose>
								<c:when test="${var.isDelete eq '1'}">
									<a href="#nogo" onclick="status('${var.id}','0')" class="oper">${i18n.oper.active}</a>
								</c:when>
								<c:otherwise>
									<a href="#nogo" onclick="status('${var.id}','1')" class="oper">${i18n.oper.disable}</a>
								</c:otherwise>
							</c:choose>
						</ec:column>
					</ec:row>
			</ec:table>
			<div class="btns">
				<span class="btn">
					<a href="#nogo" onclick="edit()">${i18n.button.add}</a>
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