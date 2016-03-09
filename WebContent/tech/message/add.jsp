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
	var user_map = {};
	
	
	
	
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
            	
            	if(uids){
            		var result = uids.split(",");
                	for(var r=0;r<result.length;r++){
                		if(!user_map.hasOwnProperty(result[r]) || user_map[result[r]]=="no"){
							
							var tech_user = result[r].split("@");
		                	user_map[result[r]] = tech_user[0];
	    					var tr_class = "even";
	    					var table_tr = "<tr id="+tech_user[0]+" class="+tr_class+"><td width='30'></td><td>"+tech_user[1]+"</td><td width='50'><a href='javascript:void(0)' onclick=deluser('"+tech_user[0]+"','"+tech_user[1]+"')>${i18n.oper.del}</a></td></tr>";
	    					$("#add_tech_message tbody").append(table_tr); 
	    					
						}
						
                    }
                }
				
            	mapToValue();
        		args.hide();
        		/*if(selVals==""){
	        		alert("没有选择任何项！");
	        	}else{
		            ajaxGet("chgusers",
		            	{"id":"${dto.id}","uids": selVals},
		            	function(r){
					        if(r.success){
					        	location.reload();
					        }
					        else{
					        	alert(r.data);
					        }
		        		},"json");
		         }
		         args.hide();*/
	        }
	        
            	
        });
}

	function mapToValue(){
		var temp=[];
		for (var key in user_map) { 
            if(user_map[key]!="no"){
            	temp.push(user_map[key]);
            } 
            
        }   
		$("#user_uids_hide").val(temp.join(","));
	}
	
	function deluser(uid,value)
	{
		user_map[uid+"@"+value]="no";
		$("#"+uid).remove();
		mapToValue();
	}

	function unique(arr) {
	    var result = [], hash = {};
	    for (var i = 0, elem; (elem = arr[i]) != null; i++) {
	        if (!hash[elem]) {
	            result.push(elem);
	            hash[elem] = true;
	        }
	    }
	    return result;
	}
	
	</script>
</head>
<body>
<%@include file="/common/header.jsp" %>

<div class="centerbody centerbox">
	<div id="contentwrapper">
		<div id="content_right">
				<div class="tt">
					${i18n.message.add}
				</div>
				<form id="msForm" action="addsave" method="post">
				<input type="hidden" name="id" value="${dto.id}" />
				<input type="hidden" name="uids" id="user_uids_hide" />
				<ul class="form">
						<li>
							<label id="testtitle">${i18n.message.title}</label>
							<input type="text" id="title" name="title" value="${dto.title}" v="notEmpty" maxlength="450" style="width: 450px" />
							<span class="msg">${i18n.valid.notEmpty}</span>
						</li>
						<li style="height: 310px;"><label>${i18n.message.content}</label>
							<textarea id="content" name="content" v="notEmpty" style="width: 450px; height: 300px;">${dto.content}</textarea>
							<span class="msg">${i18n.valid.notEmpty}</span>
						</li>
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
													<table border="0" id="add_tech_message"  cellspacing="0"  cellpadding="0"  class="tableRegion"  width="100%" >
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