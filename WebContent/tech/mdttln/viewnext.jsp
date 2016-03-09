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
	});
	function selcourseware(){
		$.fn.jmodal({
            title: '${i18n.oper.select}',
            width:500,
            height:500,
            content: function(body) {
                body.html('loading...');
                body.load("listcourse?id=${dto.id}");
            },
            buttonText: { ok: '${i18n.button.add}', cancel: '${i18n.button.cancel}' },
            okEvent: function(data, args) {
            	var cids = checkedByName("selIds");
	            	 ajaxGet("chgcourses",
	            			{"id":"${dto.id}","cids":cids},
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
	function delcour(cid)
	{
		ajaxGet("delcour",{"id":"${dto.id}","cid": cid},function(r){
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
				<div class="tt">${i18n.mdttln.title}</div>
				<form id="lnForm" action="savenext" method="post">
				<input type="hidden" name="id" value="${dto.id}" />
				<div class="lineSeperater">
				<ul >
						<li style="height: auto;">
						   <label>${i18n.mdttln.taskcour}</label>
							<%-- <textarea id="courseware" name="courseware" v="notEmpty" style="width: 450px; height: 300px;">${dto.courseware}</textarea>
							<span class="msg">${i18n.valid.notEmpty}</span> --%>
							<table width="530" cellpadding="0" cellspacing="0" border="0">
										<tr>
											<td>
												<div class="eXtremeTable" >
													<table border="0"  cellspacing="0"  cellpadding="0"  class="tableRegion"  width="100%" >
														<tbody class="tableBody" >
															<c:forEach items="${dto.lnpkgs}" var="var" varStatus="status">
																<c:set var="cls" value="odd"></c:set>
																<c:if test="${status.index%2==0}">
																	<c:set var="cls" value="even"></c:set>
																</c:if>
																
																<tr class="${cls}">
																	<td width="30">${status.index+1}</td>
																	<td><ecan:viewDto property="fixedName" dtoName="TechMdttPkg" id="${var}"/></td>
																	<td width="50">
																				<a href="javascript:void(0)" onclick="delcour('${var}')">${i18n.oper.del}</a>
																	</td>
																</tr>
															</c:forEach>
														</tbody>
													</table>
												</div>
											</td>
											<td align="left" width="200" valign="top">
													<span class="btn" style="margin-left: 20px;">
														<a href="javascript:selcourseware();">${i18n.button.add}</a>
													</span>
											</td>
										</tr>
									</table>
						</li>
						</ul>
						</div>
				
				</form>
			<div class="btns" style="padding-left: 160px;">
				<span class="btn">
					<a href="javascript:void(0)" onclick="$('#lnForm').submit()">${i18n.mdttln.button.savenext}</a>
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