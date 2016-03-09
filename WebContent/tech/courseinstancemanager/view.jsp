<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@include file="/common/head.jsp"%>
		<style type="text/css">
.lineSeperater {
	border-top: 1px dotted #dfdfdf;
	padding: 20px 0px;
}

.form li label {
	width: 120px;
}

.rowTitle {
	border: 1px solid #dfdfdf;
	background: #f0f0f0;
	color: #2274AC;
	font-size: 14px;
	font-weight: bold;
	height: 25px;
	line-height: 25px;
}

.eXtremeTable .tableRegion {
	margin: 0px;
}
</style>
		<script type="text/javascript">
	$(document).ready(function(){
		initJsonForm("#myForm",function(d){
			alert("${i18n.commit.success}");
			location.href="index";
		},function(d){
			alert(d.data)
		});
	});


	function selUsers()
	{//候选人
	saveCourse();
		$.fn.jmodal({
            title: '${i18n.oper.select}${i18n.testactive.filed.testuser}',
            width:700,
            content: function(body) {
                body.html('loading...');
                body.load('listUsers?id=${dto.id}&courseinstanceid=${courseinstanceid}');
            },
            buttonText: { ok: '${i18n.button.add}', cancel: '${i18n.button.cancel}' },
            okEvent: function(data, args) {
				var ids = checkedByName("selIds");
            	
            	ajaxGet("addUsers",{"id":"${courseinstanceid}","userids": ids},function(r){
        			if(r.success)
        				location.reload();
        			else
        				alert(r.data);
        		},"json");
            }
        });
	}
	function selTitle()
	{//对应课后测试题
		saveCourse();
		$.fn.jmodal({
            title: '${i18n.oper.select}${i18n.testting.title}',
            width:700,
            content: function(body) {
                body.html('loading...');
                body.load('listTitle?courseid=${dto.courseId}&protype=${dto.proType}');
            },
            buttonText: { ok: '${i18n.button.add}', cancel: '${i18n.button.cancel}' },
            okEvent: function(data, args) {
				var ids = checkedByName("selIdss");
            	
            	ajaxGet("addTitle",{"courseid":"${dto.courseId}","titleid": ids},function(r){
        			if(r.success)
        				location.reload();
        			else
        				alert(r.data);
        		},"json");
            }
        });
	}
	

function delUser(userid)
	{
		saveCourse();
		ajaxGet("delUsers",{"id":userid},function(r){
			if(r.success)
				location.reload();
			else
				alert(r.data);
		},"json");
	}
	
function delTitle(titleid,courseId)
	{
		saveCourse();
		ajaxGet("delTitle",{"id":titleid,"courseId":courseId},function(r){
			if(r.success)
				location.reload();
			else
				alert(r.data);
		},"json");
	}
	
		
	function saveCourse(reload)
	{
		var url="save";
		if(reload && !validFrom("#myForm"))
		{
			return;
		}
		if(reload)
		{
			url = "saveAll";
		}
		jQuery.post(url,
					{
						"courseInstanceId":"${courseinstanceid}",
						"startTime":$("#_createTime").val(),
						"name":$("#_name").val(),
						"expireTime":$("#_expireTime").val(),
						"remark":$("#_remark").val()
					},
					function(r){
						if(r.success)
						{
							if(reload)
							{
								alert("${i18n.commit.success}");
								location.href="canDispach";
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
		<%@include file="/common/header.jsp"%>
		<div class="centerbody centerbox">
			<div id="contentwrapper">
				<div id="content_right">
					<div class="tt">${i18n.courseassin.title}</div>

					<form id="myForm" action="save" method="post" class="form">
						<input type="hidden" name="id" value="${dto.id}" />

						<ul>
							<li>
								<label>
									${i18n.traincourse.filed.name}
								</label>
								<input type="text" name="name" value="${dto.name}" id="_name"
									v="notEmpty" maxlength="50" />
								<span class="msg">${i18n.traincourse.filed.name}${i18n.valid.notEmpty}</span>
							</li>
							<li style="height: auto;">
								<label>
									${i18n.traincourse.filed.remark}
								</label>
								<textarea rows="20" cols="5" id="_remark">${dto.remark}</textarea>
								<span class="msg">${i18n.traincourse.filed.remark}${i18n.valid.notEmpty}</span>
								<div class="clearfix_l"></div>
							</li>
							<li>
								<label>
									${i18n.traincourse.filed.startTime}
								</label>
								<ecan:dateInupt name="createName" value="${dto.createTime}"
									params="v=notEmpty&id=_createTime" />
								<span class="msg">${i18n.traincourse.filed.startTime}${i18n.valid.notEmpty}</span>
							</li>
							<li>
								<label>
									${i18n.traincourse.filed.expiretime}
								</label>
								<ecan:dateInupt name="expireTime" value="${dto.expireTime}"
									params="v=notEmpty&id=_expireTime" />
								<span class="msg">${i18n.traincourse.filed.expiretime}${i18n.valid.notEmpty}</span>
							</li>
						</ul>


						<div class="lineSeperater">
							<!-- 人员部分 -->
							<ul>
								<li style="height: auto;">
									<!-- 候选人 -->
									<label>
										${i18n.domainlabel.role.student}
									</label>
									<table width="560" cellpadding="0" cellspacing="0" border="0">
										<tr>
											<td>
												<div class="eXtremeTable">
													<table border="0" cellspacing="0" cellpadding="0"
														class="tableRegion" width="100%">
														<tbody class="tableBody">
															<c:forEach items="${statusList}" var="var"
																varStatus="status">
																<c:set var="cls" value="odd"></c:set>
																<c:if test="${status.index%2==0}">
																	<c:set var="cls" value="even"></c:set>
																</c:if>

																<tr class="${cls}"
																	onmouseover="this.className='highlight'"
																	onmouseout="this.className='${cls}'">
																	<td>
																		<ecan:viewDto property="name" dtoName="EcanUser"
																			id="${var.studentId}" />
																	</td>
																	<td width="50">
																		<c:choose>
																			<c:when test="${'1' eq dto.status}">
																				<a href="javascript:void(0)"
																					onclick="delUser('${var.id}');">${i18n.oper.del}</a>
																			</c:when>
																			<c:otherwise>
																				&nbsp;
																			</c:otherwise>
																		</c:choose>
																	</td>
																</tr>
															</c:forEach>
														</tbody>
													</table>
												</div>
											</td>
											<td align="left" width="200">
												<span class="btn" style="margin-left: 20px;"> <a
													href="javascript:selUsers();">${i18n.button.add}</a> </span>
											</td>
										</tr>
									</table>
								</li>
							</ul>
						</div>
						<%--
						<div class="lineSeperater">
							<ul>
								<li style="height: auto;">
									<!-- 课后测试题 -->
									<label>
										${i18n.testting.title}
									</label>
									<table width="560" cellpadding="0" cellspacing="0" border="0">
										<tr>
											<td>
												<div class="eXtremeTable">
													<table border="0" cellspacing="0" cellpadding="0"
														class="tableRegion" width="100%">
														<tbody class="tableBody">
															<c:forEach items="${testingList}" var="var1"
																varStatus="status">
																<c:set var="cls" value="odd"></c:set>
																<c:if test="${status.index%2==0}">
																	<c:set var="cls" value="even"></c:set>
																</c:if>

																<tr class="${cls}"
																	onmouseover="this.className='highlight'"
																	onmouseout="this.className='${cls}'">
																	<td>
																		<ecan:viewDto property="title" dtoName="TechCourseTestingDb"
																			id="${var1.titleId}" />
																	</td>
																	<td width="50">
																		<c:choose>
																			<c:when test="${'1' eq dto.status}">
																				<a href="javascript:void(0)"
																					onclick="delTitle('${var1.titleId}','${dto.courseId}');">${i18n.oper.del}</a>
																			</c:when>
																			<c:otherwise>
																				&nbsp;
																			</c:otherwise>
																		</c:choose>
																	</td>
																</tr>
															</c:forEach>
														</tbody>
													</table>
												</div>
											</td>
											<td align="left" width="200">
												<span class="btn" style="margin-left: 20px;"> <a
													href="javascript:selTitle();">${i18n.button.add}</a> </span>
											</td>
										</tr>
									</table>
								</li>
							</ul>
						</div>
						--%>

						<div class="btns">
							<c:if test="${dto.status eq '1'}">
							<span class="btn"> 
									<a href="javascript:void(0);" onclick="saveCourse(true);">${i18n.button.submit}</a>
							</span>
							</c:if> 
							<span class="btn"> <a href="canDispach">${i18n.button.back}</a>
							</span>
							<c:if test="${dto.status eq '2'}">
								<span class="gray">${i18n.commit.edit.desc}</span>
							</c:if>
							<c:if test="${dto.status eq '3'}">
								<span class="gray">${i18n.commit.edit.desc}</span>
							</c:if>
						</div>
					</form>
				</div>
			</div>
			<br />
			<br />
			<%@include file="/common/leftmenu.jsp"%>
			<div class="clearfix_b"></div>
		</div>
		<%@include file="/common/footer.jsp"%>
	</body>
</html>