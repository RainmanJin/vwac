<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@include file="/common/head.jsp" %>
	<style>
		.lineSeperater{
			border-top: 1px dotted #dfdfdf;
			padding: 20px 0px;
		}
		.form li label{
			width: 140px;
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
		initJsonForm("#pForm",function(d){
			location.href="index";
		},function(d){
			alert(d.data);
		});
	});
	function selusers(){
		$.fn.jmodal({
            title: '${i18n.oper.select}',
            width:500,
            height:500,
            content: function(body) {
                body.html('loading...');
                body.load("listusers?id=${dto.id}");
            },
            buttonText: { ok: '${i18n.button.add}', cancel: '${i18n.button.cancel}' },
             okEvent: function(data, args) {
            	var uids = checkedByName("selIds");
	            	 ajaxGet("chgusers",
	            			{"id":"${dto.id}","uids":uids},
	            			function(r){
				        			if(r.success){
				        				location.reload();
				        			}
				        			else{
				        				alert(r.data);
				        			}
	        		        },"json");
        		} 
            	
        });
}
	function deluser(id)
	{
		ajaxGet("deluser",{"id": id},function(r){
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
				<div class="tt">
				${i18n.mdttpkg.power}
				</div>
				<form id="pForm" action="powerOk" method="post">
				<input type="hidden" name="id" value="${dto.id}" />
				<ul class="form">
					<li>
						<label>${i18n.scormpkg.filed.name}(DMP)</label>
						<input type="text" name="name" value="${dto.name}" readonly="readonly" maxlength="100" style="width: 220px" class="gray"/>
						<span class="msg">${i18n.valid.notEmpty}</span>
					</li>
					<li>
						<label>${i18n.scormpkg.filed.name}(iPad)</label>
						<input type="text" id="fixedName" value="${dto.fixedName}"  readonly="readonly"  maxlength="100" style="width: 220px" class="gray"/>
						<span class="msg">${i18n.valid.notEmpty}</span>
					</li>
					<li style="height: auto;">
						   <label>${i18n.message.senduser}</label>
							<table width="530" cellpadding="0" cellspacing="0" border="0">
										<tr>
											<td>
												<div class="eXtremeTable" >
													<table border="0"  cellspacing="0"  cellpadding="0"  class="tableRegion"  width="100%" >
														<tbody class="tableBody" >
														<c:if test="${not empty dto.tmus}">
															<c:forEach items="${dto.tmus}" var="var" varStatus="status">
																<c:set var="cls" value="odd"></c:set>
																<c:if test="${status.index%2==0}">
																	<c:set var="cls" value="even"></c:set>
																</c:if>
																<tr class="${cls}">
																	<td width="30">${status.index+1}</td>
																	<td><ecan:viewDto property="name" dtoName="EcanUser" id="${var.userId}"/></td>
																	<td width="50">
																				<a href="javascript:void(0)" onclick="deluser('${var.id}')">${i18n.oper.del}</a>
																	</td>
																</tr>
															</c:forEach>
														</c:if>
														</tbody>
													</table>
												</div>
											</td>
											<td align="left" width="200" valign="top">
													<span class="btn" style="margin-left: 20px;">
														<a href="javascript:selusers();">${i18n.button.add}</a>
													</span>
											</td>
										</tr>
									</table>
						</li>
                  </ul>
				</form>
			<div class="btns" style="padding-left: 160px;">
				<span class="btn">
					<a href="javascript:void(0)" onclick="$('#pForm').submit()">${i18n.button.pkgpowerok}</a>
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