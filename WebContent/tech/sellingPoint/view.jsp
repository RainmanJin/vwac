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
		initJsonForm("#spForm",function(d){
			location.href="index";
		},function(d){
			alert(d.data);
		});
	});
	 function editconent(id){
		 location.href="editconent?id="+id+"&mainId="+"${dto.id}";
     }
	 function addconent(){
		 location.href="addconent?mainId="+"${dto.id}";
     }
	 
	function delconent(cid)
	{
		if(confirm("${i18n.delete.comfirm}"))
		{
		ajaxGet("delconent",
				{"id":"${dto.id}","cid": cid},function(r){
			if(r.success)
				location.reload();
			else
				alert(r.data);
		},"json");
		}
	}
	function selconents(){
		$.fn.jmodal({
            title: '${i18n.oper.select}',
            width:500,
            height:500,
            content: function(body) {
                body.html('loading...');
                body.load("listconents?id=${dto.id}");
            },
            buttonText: { ok: '${i18n.button.add}', cancel: '${i18n.button.cancel}' },
             okEvent: function(data, args) {
            	var temp_uids = checkedByName("selIds");
	            	 ajaxGet("chgconents",
	            			{"id":"${dto.id}","uids":temp_uids},
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
	</script>
</head>
<body>
<%@include file="/common/header.jsp" %>

<div class="centerbody centerbox">
	<div id="contentwrapper">
		<div id="content_right">
				<div class="tt">
				${i18n.sellingpoint.edit}
				</div>
				<form id="spForm" action="savemain" method="post">
				<input type="hidden" name="id" value="${dto.id}" />
				<ul class="form">
						<li>
							<label>${i18n.selling.pointtitle}</label>
							<input type="text" id="title" name="title" value="${dto.title}" v="notEmpty" maxlength="450" style="width: 450px" />
							<span class="msg">${i18n.valid.notEmpty}</span>
						</li>
						 <li style="height: auto;">
						   <label>${i18n.sellingpoint.conentlist}</label>
							<table width="530" cellpadding="0" cellspacing="0" border="0">
										<tr>
											<td>
												<div class="eXtremeTable" >
													<table border="0"  cellspacing="0"  cellpadding="0"  class="tableRegion"  width="100%" >
														<tbody class="tableBody" >
														
															<c:forEach items="${dto.spc}" var="var" varStatus="status">
															<c:if test="${var.title!=null}">
																<c:set var="cls" value="odd"></c:set>
																<c:if test="${status.index%2==0}">
																	<c:set var="cls" value="even"></c:set>
																</c:if>
																<tr class="${cls}">
																	<td width="30">${status.index+1}</td>
																	<td width="200">${var.title}</td>
																	<td width="40">
																				<a href="javascript:void(0)" onclick="delconent('${var.id}')">${i18n.oper.del}</a>
																	</td>
																</tr>
																</c:if>
															</c:forEach>
															
														</tbody>
													</table>
												</div>
											</td>
											<td align="left" width="200" valign="top">
													<span class="btn" style="margin-left: 20px;">
														<a href="javascript:selconents();">${i18n.button.add}</a>
													</span>
											</td>
										</tr>
									</table>
						</li>
				</ul>
				</form>
			<div class="btns" style="padding-left: 160px;">
				<span class="btn">
					<a href="javascript:void(0)" onclick="$('#spForm').submit()">${i18n.button.save}</a>
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