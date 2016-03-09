<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@include file="/common/head.jsp" %>
	<style type="text/css">
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
	function selHoster()
	{//主持人
		saveActive(false);
		$.fn.jmodal({
            title: '${i18n.oper.select}${i18n.testactive.filed.hoster}',
            width:700,
            content: function(body) {
                body.html('loading...');
                body.load('listUsers?id=${dto.id}&type=hoster');
            },
            buttonText: {cancel: '${i18n.button.cancel}' }
        });
	}

	function chgHoster(hosterid)
	{//更改主持人
		ajaxGet("chgHoster",{"id":"${dto.id}","hosterid": hosterid},function(r){
			if(r.success)
				location.reload();
			else
				alert(r.data);
		},"json");
	}

	function selWatchmen()
	{//观察员
		$.fn.jmodal({
            title: '${i18n.oper.select}${i18n.testactive.filed.watchmen}',
            width:700,
            content: function(body) {
                body.html('loading...');
                body.load('listUsers?id=${dto.id}&type=watchmen');
            },
            buttonText: { ok: '${i18n.button.add}', cancel: '${i18n.button.cancel}' },
            okEvent: function(data, args) {
            	var ids = checkedByName("selIds");
            	
            	ajaxGet("addUsers",{"id":"${dto.id}","userids": ids, "type":"watchmen"},function(r){
        			if(r.success)
        				location.reload();
        			else
        				alert(r.data);
        		},"json");
            }
        });
	}

	function selUsers()
	{//候选人
		saveActive(false);
		$.fn.jmodal({
            title: '${i18n.oper.select}${i18n.testactive.filed.testuser}',
            width:700,
            content: function(body) {
                body.html('loading...');
                body.load('listUsers?id=${dto.id}&type=user');
            },
            buttonText: { ok: '${i18n.button.add}', cancel: '${i18n.button.cancel}' },
            okEvent: function(data, args) {
				var ids = checkedByName("selIds");
            	
            	ajaxGet("addUsers",{"id":"${dto.id}","userids": ids, "type":"user"},function(r){
        			if(r.success)
        				location.reload();
        			else
        				alert(r.data);
        		},"json");
            }
        });
	}

	function delUser(userid,type)
	{
		ajaxGet("delUsers",{"id":"${dto.id}","userid": userid, "type":type},function(r){
			if(r.success)
				location.reload();
			else
				alert(r.data);
		},"json");
	}

	/**
		reload:提交按钮提交是为true,自动提交为false
	*/
	function saveActive(reload)
	{
		if(reload && (!validFrom("#myForm") || !confirm("${i18n.testactive.msg.submitAlert}")))
		{
			return;
		}

		var type = reload?"none":"auto";
		
		ajaxGet("save",
					{
						"id":"${dto.id}",
						"testcount":$("#_testcount").val(),
						"testtime":$("#_testtime").val(),
						"hosterid":$("#_hosterid").val(),
						"brand":$("#_brand").val(),
						"type":type
					},
					function(r){
						if(r.success)
						{
							if(reload)
							{
								alert("${i18n.commit.success}");
								location.href="index";
							}
						}
						else
						{
							alert(r.data);
						}
					},"json");
	}
	</script>
</head>
<body>
<%@include file="/common/header.jsp" %>

<div class="centerbody centerbox">
	<div id="contentwrapper">
		<div id="content_right">
			<div class="tt">${i18n.testactive.title}</div>
			<form class="form" id="myForm" onsubmit="return false;">
						<ul>
							<li>
								<label>${i18n.testactive.filed.name}</label>
								<input type="text" disabled="disabled" value="${dto.name}" class="gray" />
							</li>
							<li>
								<label>${i18n.testactive.filed.brand}</label>
								<d:selectDomain domain="BRAND" name="brand" uid="_brand" value="${dto.brand}" />
							</li>
						</ul>						
						
						<div class="lineSeperater"><!-- 测试部分 -->
							<ul>
								<li>
									<label>${i18n.testactive.filed.testcount}</label>
									<input type="text" name="name" value="${dto.testCount}" id="_testcount" v="notEmpty num" maxlength="3" />
									<span class="msg">${i18n.testactive.filed.testcount}${i18n.valid.notEmpty}</span>
								</li>
								<li>
									<label>${i18n.testactive.filed.testtime}</label>
									<input type="text" name="name" value="${dto.testTimelimit}" id="_testtime" v="notEmpty num" maxlength="3" />
									<span class="info">(${i18n.testactive.filed.testtimeUnit})</span>
									<span class="msg">${i18n.testactive.filed.testtime}${i18n.valid.notEmpty}</span>
								</li>
							</ul>
						</div>
						<div class="lineSeperater"><!-- 人员部分 -->
							<ul>
								<li>
									<label>${i18n.testactive.filed.mainMan}</label>
									<input type="text" value="<ecan:viewDto property="name" dtoName="EcanUser" id="${dto.mainManId}"/>" class="gray" disabled="disabled" />
								</li>
								<li><!-- 主持人 -->
									<label>${i18n.testactive.filed.hoster}</label>
									<input type="hidden" name="hosterId" value="${dto.hosterId}" id="_hosterid" />
									<c:choose><!-- 仅未开始的活动能够编辑 -->
										<c:when test="${dto.status eq 'edit'}">
											<input type="text" v="notEmpty" value='<ecan:viewDto property="name" dtoName="EcanUser" id="${dto.hosterId}"/>' class="search" onclick="selHoster()" />
										</c:when>
										<c:otherwise>
											<input type="text" v="notEmpty" value='<ecan:viewDto property="name" dtoName="EcanUser" id="${dto.hosterId}"/>' class="gray" />
										</c:otherwise>									
									</c:choose>
								</li>
								<li style="height: auto;"><!-- 观察员 -->
									<label>${i18n.testactive.filed.watchmen}</label>
									<table width="530" cellpadding="0" cellspacing="0" border="0">
										<tr>
											<td>
												<div class="eXtremeTable" >
													<table border="0"  cellspacing="0"  cellpadding="0"  class="tableRegion"  width="100%" >
														<tbody class="tableBody" >
															<c:forEach items="${dto.watchMenIds}" var="var" varStatus="status">
																<c:set var="cls" value="odd"></c:set>
																<c:if test="${status.index%2==0}">
																	<c:set var="cls" value="even"></c:set>
																</c:if>
																
																<tr class="${cls}"  onmouseover="this.className='highlight'"  onmouseout="this.className='${cls}'" >
																	<td width="30">${status.index+1}</td>
																	<td>
																		<ecan:viewDto property="name" dtoName="EcanUser" id="${var}"/>
																	</td>
																	<td>
																		<d:viewDomain value="${ef:viewDto('EcanUser',var,'company')}" domain="COMPANY" />
																	</td>
																	<td width="50">
																		<c:choose><!-- 仅未开始的活动能够编辑 -->
																			<c:when test="${dto.status eq 'edit'}">
																				<a href="javascript:void(0)" onclick="delUser('${var}','watchmen')">${i18n.oper.del}</a>
																			</c:when>
																			<c:otherwise>&nbsp;</c:otherwise>									
																		</c:choose>
																	</td>
																</tr>
															</c:forEach>
														</tbody>
													</table>
												</div>
											</td>
											<td align="left" width="200" valign="top">
													<c:if test="${dto.status eq 'edit'}">
													<span class="btn" style="margin-left: 20px;">
														<a href="javascript:selWatchmen();">${i18n.button.add}</a>
													</span>
													</c:if>
											</td>
										</tr>
									</table>
								</li>
								<li style="height: auto;"><!-- 候选人 -->
									<label>${i18n.testactive.filed.testuser}</label>
									<table width="530" cellpadding="0" cellspacing="0" border="0">
										<tr>
											<td>
												<div class="eXtremeTable" >
													<table border="0"  cellspacing="0"  cellpadding="0"  class="tableRegion"  width="100%" >
														<tbody class="tableBody" >
															<c:forEach items="${dto.userids}" var="var" varStatus="status">
																<c:set var="cls" value="odd"></c:set>
																<c:if test="${status.index%2==0}">
																	<c:set var="cls" value="even"></c:set>
																</c:if>
																
																<tr class="${cls}"  onmouseover="this.className='highlight'"  onmouseout="this.className='${cls}'" >
																	<td width="30">${status.index+1}</td>
																	<td><ecan:viewDto property="name" dtoName="EcanUser" id="${var}"/></td>
																	<td>
																		<d:viewDomain value="${ef:viewDto('EcanUser',var,'company')}" domain="COMPANY" />
																	</td>
																	<td width="50">
																		<c:choose><!-- 仅未开始的活动能够编辑 -->
																			<c:when test="${dto.status eq 'edit'}">
																				<a href="javascript:void(0)" onclick="delUser('${var}','user')">${i18n.oper.del}</a>
																			</c:when>
																			<c:otherwise>&nbsp;</c:otherwise>									
																		</c:choose>
																	</td>
																</tr>
															</c:forEach>
														</tbody>
													</table>
												</div>
											</td>
											<td align="left" width="200" valign="top">
												<c:if test="${dto.status eq 'edit'}">
													<span class="btn" style="margin-left: 20px;">
														<a href="javascript:selUsers();">${i18n.button.add}</a>
													</span>
												</c:if>
											</td>
										</tr>
									</table>
								</li>
							</ul>
						</div>
					
						<div class="btns" style="padding-left: 30px;">
							<c:choose><!-- 仅未开始的活动能够编辑 -->
								<c:when test="${dto.status eq 'edit'}">
									<span class="btn">
										<a href="javascript:void(0)" onclick="saveActive(true)">${i18n.button.submit}</a>
									</span>
								</c:when>
								<c:otherwise><span class="gray">${i18n.testactive.msg.editStarted}</span></c:otherwise>									
							</c:choose>
							
							<span class="btn">
								<a href="index">${i18n.button.back}</a>
							</span>
						</div>			
			</form>
		</div>
	</div>
	
	<%@include file="/common/leftmenu.jsp" %>
	<div class="clearfix_b"></div>
</div>
<%@include file="/common/footer.jsp" %>
</body> 
</html>