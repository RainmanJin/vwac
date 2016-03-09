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
		initJsonForm("#msForm",function(d){
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
            	var temp_uids = checkedByName("selIds");
            	var temp_array = [];
            	var uids = [];
            	temp_array = temp_uids.split(",");
            	for(var i=0 ;i<temp_array.length;i++){
                	var tet = temp_array[i].split("@");
                	uids.push(tet[0]);
                }
                
	            	 ajaxGet("chgusers",
	            			{"id":"${dto.id}","uids":uids.join(",")},
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
				<div class="tt">
				<c:choose>
					<c:when test="${empty dto}">${i18n.message.edit}</c:when>
					<c:otherwise>${i18n.message.add}</c:otherwise>
				</c:choose>
				</div>
				<form id="msForm" action="save" method="post">
				<input type="hidden" name="id" value="${dto.id}" />
				<ul class="form">
						<li>
							<label>${i18n.message.title}</label>
							<input type="text" id="title" name="title" value="${dto.title}" v="notEmpty" maxlength="450" style="width: 450px" />
							<span class="msg">${i18n.valid.notEmpty}</span>
						</li>
						<li style="height: 310px;"><label>${i18n.message.content}</label>
							<textarea id="content" name="content" v="notEmpty" style="width: 450px; height: 300px;">${dto.content}</textarea>
							<span class="msg">${i18n.valid.notEmpty}</span>
						</li>
						<%-- <li>
							<label>${i18n.message.type}</label>
							<d:selectDomain  domain="MESSAGE_TYPE" name="type"  value="${dto.type}"/>
							<span class="msg">${i18n.valid.notEmpty}</span>
						</li> --%>
						<li>
						<label>${i18n.message.type}</label>
						<select name="type" >
									<c:forEach var="mt" items="${metypeList}" varStatus="s">
										<option value="${mt.typeCode}" ${dto.type == mt.typeCode?'selected':''}>${mt.typeName}</option>
									</c:forEach>
					   </select>
						</li>
						<li style="height: auto;">
						   <label>${i18n.message.senduser}</label>
							<table width="530" cellpadding="0" cellspacing="0" border="0">
										<tr>
											<td>
												<div class="eXtremeTable" >
													<table border="0"  cellspacing="0"  cellpadding="0"  class="tableRegion"  width="100%" >
														<tbody class="tableBody" >
															<c:forEach items="${dto.eusers}" var="var" varStatus="status">
																<c:set var="cls" value="odd"></c:set>
																<c:if test="${status.index%2==0}">
																	<c:set var="cls" value="even"></c:set>
																</c:if>
																<tr class="${cls}">
																	<td width="30">${status.index+1}</td>
																	<td><ecan:viewDto property="name" dtoName="EcanUser" id="${var}"/></td>
																	<td width="50">
																				<a href="javascript:void(0)" onclick="deluser('${var}')">${i18n.oper.del}</a>
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
					<a href="javascript:void(0)" onclick="$('#msForm').submit()">${i18n.button.save}</a>
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