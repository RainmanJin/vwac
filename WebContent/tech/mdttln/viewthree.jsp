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
	</style>
	<script type="text/javascript">
	console.log("----1-------");
	console.log("${userList}");
	console.log("----2-------");
	$(document).ready(function(){
		initJsonForm("#lnForm",function(d){
			location.href="index";
		},function(d){
			alert(d.data);
		});
		$("#userid").focus(function(){
			$.fn.jmodal({
	            title: '${i18n.oper.select}',
	            width:500,
	            height:500,
	            content: function(body) {
	                body.html('loading...');
	                body.load("listUsers?id=${dto.id}");
	            },
	            buttonText: { ok: '${i18n.button.add}', cancel: '${i18n.button.cancel}' },
	            okEvent: function(data, args) {
	            	var selVal = [];
	            	var rightSel = $("#selectR");
	        		rightSel.find("option").each(function(){
	        			selVal.push(this.value);
	        		});
	        		selVals = selVal.join(",");
	        		/* var title = $("#title").val();
	        		alert(title); 
	        		var status = document.getElementByName("style").value;
	        		alert(status);*/
	        		//var 
	        		if(selVals==""){
	        			alert("没有选择任何项！");
	        		}else{
		            	 ajaxGet("chgusers",
		            			{"id":"${dto.id}","userid": selVals},
		            			function(r){
					        			if(r.success){
					        				location.reload();
					        			}
					        			else{
					        				alert(r.data);
					        			}
		        		        },"json");
		            }
	        		}
	            	
	        });
		});
	});

	function selusers(){
		$.fn.jmodal({
            title: '${i18n.oper.select}',
            width:500,
            height:500,
            content: function(body) {
                body.html('loading...');
                body.load("listUsers?id=${dto.id}");
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

	function deluser(uid)
	{
		ajaxGet("deluser",{"id":"${dto.id}","uid": uid},function(r){
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
				<form id="lnForm" action="savelast" method="post">
				<input type="hidden" name="id" value="${dto.id}" />
				<ul class="form">
						<li style="height: auto;">
						   <label>${i18n.message.senduser}</label>
							<table width="530" cellpadding="0" cellspacing="0" border="0">
										<tr>
											<td>
												<div class="eXtremeTable" >
													<table border="0" id="add_tech_message"  cellspacing="0"  cellpadding="0"  class="tableRegion"  width="100%" >
														<tbody class="tableBody" >
															<c:forEach items="${userList}" var="var" varStatus="status">
																<c:set var="cls" value="odd"></c:set>
																<c:if test="${status.index%2==0}">
																	<c:set var="cls" value="even"></c:set>
																</c:if>
																<tr class="${cls}">
																	<td width="30">${status.index+1}</td>
																	<td><ecan:viewDto property="name" dtoName="EcanUser" id="${var.id}"/></td>
																	<td width="50">
																				<a href="javascript:void(0)" onclick="deluser('${var.id}')">${i18n.oper.del}</a>
																	</td>
																</tr>
															</c:forEach>
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
					<a href="javascript:void(0)" onclick="$('#lnForm').submit()">${i18n.mdttln.button.savepush}</a>
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