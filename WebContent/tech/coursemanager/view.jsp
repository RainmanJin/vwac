<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@include file="/common/head.jsp" %>
	
	<style>
		.form li label{
			width: 100px;
		}
		.rowTitle{
			border: 1px solid #dfdfdf;background: #f0f0f0;color: #2274AC; font-size: 14px; font-weight: bold;
			height: 25px; line-height: 25px;
		}
		.eXtremeTable .tableRegion{
			margin: 0px;
		}
	</style>
	<script type="text/javascript">
	$(document).ready(function(){
		initJsonForm("#courseForm",function(d){
			location.href="index";
		},function(d){
			alert(d.data)
		});

		attachement();
	});

	//增加附件
	function addAttachement()
	{
		$.fn.jmodal({
            title: '${i18n.course.tab.uploadAttachement}',
            width:600,
            content: function(body) {
                body.html('loading...');
                body.load('forUploadAttachement?id=${dto.id}');
            },
            buttonText: { ok: '${i18n.button.upload}', cancel: '${i18n.button.cancel}' },
            okEvent: function(data, args) {
            	$("#myForm").submit();
            }
        });
	}

	function delAttachement(id)
	{
		ajaxGet("delAttachement",{"id":id},function(r){
				if(r.success)
					location.reload();
				else
					alert(r.data);
			},"json");
	}

	//增加测试题
	function addTestting(courseid)
	{
		$.fn.jmodal({
            title: '${i18n.course.tab.addTestting}',
            width:600,
            content: function(body) {
                body.html('loading...');
                body.load('listTestting?courseId=${dto.id}');
            },
            buttonText: { ok: '${i18n.button.add}', cancel: '${i18n.button.cancel}' },
            okEvent: function(data, args) {
            	var str="";
            	$('input[name="chktestingid"]:checked').each(function(){
					str+=$(this).val()+",";  
				});  
				ajaxGet("addTesting",{"id":str,"courseid":courseid},function(r){
				if(r.success)
				{
					location.reload();
				}else
					alert(r.data);
				},"json");
            }
        });
	}


	function delTestting(id)
	{
		ajaxGet("delTestting",{"id":id},function(r){
			if(r.success)
				
				location.reload();
			else
				alert(r.data);
		},"json");
	}
	</script>
</head>
<body>
<%@include file="/common/header.jsp" %>

<div class="centerbody centerbox">
	<div id="contentwrapper">
		<div id="content_right">
			<div class="tt">${i18n.course.edit}</div>
			<form id="courseForm" action="save" method="post" class="form" enctype="multipart/form-data">
				<input type="hidden" name="id" value="${dto.id}" />
				<input type="hidden" name="scormId" value="${dto.scormId}" />
				<input type="hidden"" name="preview" value="${dto.preview}" />
				
				<table cellpadding="0" cellspacing="0" border="0">
					<tr>
						<td>
							<b>${i18n.course.filed.preview}</b><br />
							<img src="${ctxPath}${dto.preview}" width="240" height="160" border="0" /><br /><br />
							<b style="line-height: 25px;">${i18n.oper.upload}${i18n.course.filed.preview}</b><br />
								<input type="file" name="file" />
						</td>
						<td>
							<ul>
								<li>
									<label>${i18n.course.filed.name}</label>
									<input type="text" name="name" value="${dto.name}" v="notEmpty" maxlength="100" style="width: 240px"/>
									<span class="msg">${i18n.valid.notEmpty}</span>
								</li>
								<li>
									<label>${i18n.course.filed.brand}</label>
									<d:selectDomain domain="BRAND" name="brand" value="${dto.brand}" />
								</li>
								<li><label>${i18n.course.filed.proType}</label>
									<d:selectDomain domain="PROTYPE" name="proType" value="${dto.proType}" />
								</li>
								<li>
									<label>${i18n.course.filed.contentType}</label>
									<d:selectDomain domain="SCORMTYPE" name="contentType" value="${dto.contentType}" />
								</li>
								<li>
									<label>${i18n.course.filed.status}</label>
									<d:selectDomain domain="STATUS" name="status" value="${dto.status}" />
								</li>
								<li>
									<label>${i18n.course.filed.remark}</label>
									<textarea name="remark" rows="4" cols="40" style="width: 300px;">${dto.remark}</textarea>
								</li>
							</ul>
						</td>
					</tr>
					<tr>
						<td colspan="2" height="20">&nbsp;</td>
					</tr>
					<tr>
						<td colspan="2" class="rowTitle">${i18n.course.tab.atachement}</td>
					</tr>
					
					<tr>
						<td colspan="2">
							<div class="eXtremeTable" >
								<table border="0"  cellspacing="0"  cellpadding="0"  class="tableRegion"  width="100%" >
									<thead>
										<tr>
											<td class="tableHeader"  width="70%">${i18n.scormpkg.filed.name}</td>
											<td class="tableHeader"  width="30%">${i18n.oper.text}</td>
										</tr>
									</thead>
									<tbody class="tableBody" >
										<c:forEach items="${attachements}" var="var" varStatus="status">
											<c:set var="cls" value="odd"></c:set>
											<c:if test="${status.index%2==0}">
												<c:set var="cls" value="even"></c:set>
											</c:if>
											
											<tr class="${cls}"  onmouseover="this.className='highlight'"  onmouseout="this.className='${cls}'" >
												<td>${var.name}</td>
												<td>
													<a target="_blank" href="${ctxPath}${var.url}">${i18n.oper.download}</a>
													<a href="javascript:delAttachement('${var.id}');">${i18n.oper.del}</a>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<div class="btns" style="text-align: right; margin: 3px 3px 10px;">
								<span class="btn">
									<a href="javascript:addAttachement();">${i18n.button.upload}</a>
								</span>
							</div>
						</td>
					</tr>
					
					<tr>
						<td colspan="2" class="rowTitle">${i18n.course.tab.testting}</td>
					</tr>
					
					<tr>
						<td colspan="2">
							<div class="eXtremeTable" >
								<table border="0"  cellspacing="0"  cellpadding="0"  class="tableRegion"  width="100%" >
									<thead>
										<tr>
											<td class="tableHeader"  width="70%">${i18n.testting.filed.title}</td>
											<td class="tableHeader"  width="30%">${i18n.oper.text}</td>
										</tr>
									</thead>
									<tbody class="tableBody" >
										<c:forEach items="${testtings}" var="var" varStatus="status">
										<c:set var="cls" value="odd"></c:set>
										<c:if test="${status.index%2==0}">
											<c:set var="cls" value="even"></c:set>
										</c:if>
										<tr class="${cls}"  onmouseover="this.className='highlight'"  onmouseout="this.className='${cls}'" >
											<td><ecan:viewDto dtoName="TechCourseTestingDb" id="${var.titleId}" property="title"/></td>
											<td><a href="javascript:delTestting('${var.id}');">${i18n.oper.del}</a></td>
										</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
	
							<div class="btns" style="text-align: right; margin: 3px 3px 10px;">
								<span class="btn">
									<a href="javascript:addTestting('${dto.id}');">${i18n.button.add}</a>
								</span>
							</div>
						</td>
					</tr>
				</table>
			</form>
			
			
			<div class="btns">
				<span class="btn">
					<a href="javascript:void(0)" onclick="$('#courseForm').submit()">${i18n.button.submit}</a>
				</span>
				<span class="btn">
					<a href="preview?id=${dto.id}">${i18n.button.preview}</a>
				</span>
				<span class="btn">
					<a href="index">${i18n.button.back}</a>
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