<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="java.util.List"%>
<%@page import="com.ecannetwork.tech.controller.bean.testactive.Row"%>
<%@page import="com.ecannetwork.dto.tech.TechTestActive"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.ecannetwork.dto.tech.TechTestActivePoint"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="com.ecannetwork.tech.util.TechConsts"%><html>
<head>
	<%@include file="/common/head.jsp" %>
	<style type="text/css">
		.lineSeperater{
			border-top: 1px dotted #dfdfdf;
			padding: 20px 0px;
		}
		.form li label{
			width: 120px;
		}
		
		.rowTitle{
			border: 1px solid #dfdfdf; background: #f0f0f0; color: #2274AC; font-size: 14px; font-weight: bold;
			height: 25px; line-height: 25px;
		}
		.eXtremeTable .tableRegion{
			margin: 0;
		}
		
		.eXtremeTable .tableHeader{
			font-weight: bold;
			font-size: 14px;
		}
		
		.eXtremeTable td.avg,.eXtremeTable td.stp{
			font-size: 14px;
		}
		.blueRow{
			background: #f4f4f4;font-size: 14px; font-weight: bold; color: #777;
		}
	</style>
	<script type="text/javascript">
		function chgUser(id)
		{
			if(id=="")
			{
				return;
			}
			location.href="feedback?id=${dto.id}&userid=" + id;
		}

		var cipt;
		$(document).ready(function(){
			initJsonForm("#myForm",function(d){
				alert("${i18n.commit.success}");
				location.href="index";
			},function(d){
				alert(d.data)
			});


			$(".cipt").click(function(){

				cipt = $(this);
				
				$.fn.jmodal({
		            title: '${i18n.techdb.filed.courseName}',
		            width:800,
		            content: function(body) {
		                body.html('loading...');
		                body.load('${ctxPath}/techc/commonresource/listTrainCourseModelByProTypeAndBrand?brand=${dto.brand}&proType=${dto.proType}');
		            },
		            buttonText: { cancel: '${i18n.button.cancel}' }
		        });
			});

			<c:if test="${not empty curUserid}">
			$("#viewResult").load("${ctxPath}/techc/testactive/viewTestResult?id=${dto.id}&userid=${curUserid}&r_=" + Math.random());
			$("#userLevels").load("${ctxPath}/techc/testactive/viewPointLevels?id=${param.id}&userid=${param.userid}&r_=" + Math.random());
			</c:if>
		});

		function selectCourseModule(moduleid, moduleName, courseid, coursename,days)
		{
			$(cipt).val(coursename + "/" + moduleName);
			var cs = $(cipt).parent().parent().find(".days");
			cs.val(days);
			cs.attr("name", "cs_" + courseid + "_" + moduleid);
			
			$.fn.jmodalClose();
		}
	</script>
</head>
<body>
<%@include file="/common/header.jsp" %>

<div class="centerbody centerbox">
	<div id="contentwrapper">
		<div id="content_right">
			<div class="tt">${i18n.testactive.feedbackTitle}
				<span class="btn" style="float: right;">
					<a href="index">${i18n.button.back}</a>
				</span>
			</div>
			<form method="post" action="feedback" id="myForm">
			<table border="0"  cellspacing="0"  cellpadding="0"  class="tableRegion"  width="100%" >
				<tr>
					<td height="25" width="100" align="right" style="padding-right: 20px;">${i18n.testactive.filed.name}:</td>
					<td align="left">${dto.name}</td>
				</tr>
				<tr>
					<td height="25" width="100" align="right" style="padding-right: 20px;">${i18n.testactive.filed.mainMan}:</td>
					<td align="left">
						<ecan:viewDto property="name" dtoName="EcanUser" id="${dto.mainManId}"/>
					</td>
				</tr>
				<tr>
					<td height="25" width="100" align="right" style="padding-right: 20px;">${i18n.testactive.filed.hoster}:</td>
					<td align="left">
						<ecan:viewDto property="name" dtoName="EcanUser" id="${dto.hosterId}"/>
					</td>
				</tr>

				<tr>
					<td height="25" width="100" align="right" style="padding-right: 20px;">${i18n.testactive.text.selUserid}:</td>
					<td align="left">
						<select style="width: 180px;" onchange="chgUser(this.value)">
							<option value="">-- 请选择 --</option>
							<c:forEach items="${dto.userids}" var="var" varStatus="status">
							<option value="${var}" <c:if test="${curUserid eq var}">selected="selected"</c:if>>
								<ecan:viewDto property="name" dtoName="EcanUser" id="${var}"/>
							</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				
				<tr>
					<td colspan="2">&nbsp;</td>
				</tr>		
						
				<c:if test="${not empty curUserid}">
				<tr>
					<td colspan="2" class="rowTitle" style="height: 30px; line-height: 30px;">
						${i18n.testactive.filed.student}:&nbsp;&nbsp;<ecan:viewDto property="name" dtoName="EcanUser" id="${curUserid}"/>
					</td>
				</tr>
				<tr>
					<td colspan="2" class="eXtremeTable" id="m_s">
						<input type="hidden" name="userid" value="${curUserid}" />
						<input type="hidden" name="activeid" value="${dto.id}" />
						
													<table width="100%" cellspacing="0" cellpadding="0" border="0" class="tableRegion">
														<tbody class="tableBody">
																<tr>
																	<td width="100" align="center" class="tableHeader">${i18n.testactive.feedback.activities}</td>
																	<td width="310" align="center" class="tableHeader">${i18n.testactive.feedback.advantage}</td>
																	<td width="310" align="center" class="tableHeader">${i18n.testactive.feedback.weakness}</td>
																</tr>
																<tr height="100" class="even">
																	<td width="100" align="center">
																		<input type="hidden" name="dim" value="1000" />
																		<d:viewDomain value="1000" domain="TESTSTEP"/>
																	</td>
																	<td width="310"><textarea name="advantage" cols="47" rows="5">${p1000.advantage}</textarea></td>
																	<td width="310"><textarea name="weakness" cols="47" rows="5">${p1000.weakness}</textarea></td>
																</tr>
																<tr height="100" class="odd">
																	<td width="100" align="center">
																		<input type="hidden" name="dim" value="1003" />
																		<d:viewDomain value="1003" domain="TESTSTEP"/>
																	</td>
																	<td width="310"><textarea name="advantage" cols="47" rows="5">${p1003.advantage}</textarea></td>
																	<td width="310"><textarea name="weakness" cols="47" rows="5">${p1003.weakness}</textarea></td>
																</tr>
																<tr height="100" class="even">
																	<td width="100" align="center">
																		<input type="hidden" name="dim" value="1001" />
																		<d:viewDomain value="1001" domain="TESTSTEP"/>
																	</td>
																	<td width="310"><textarea name="advantage" cols="47" rows="5">${p1001.advantage}</textarea></td>
																	<td width="310"><textarea name="weakness" cols="47" rows="5">${p1001.weakness}</textarea></td>
																</tr>
																<tr height="100" class="odd">
																	<td width="100" align="center">
																		<input type="hidden" name="dim" value="1004" />
																		<d:viewDomain value="1004" domain="TESTSTEP"/>
																	</td>
																	<td width="310"><textarea name="advantage" cols="47" rows="5">${p1004.advantage}</textarea></td>
																	<td width="310"><textarea name="weakness" cols="47" rows="5">${p1004.weakness}</textarea></td>
																</tr>
																<tr height="100" class="even" style="display: none;">
																	<td width="100" align="center">
																		<input type="hidden" name="dim" value="knowledge" />
																		${i18n.testactive.feedback.filedKnowledgeTest}
																	</td>
																	<td width="310"><textarea name="advantage" cols="47" rows="5">${pknowledge.advantage}</textarea></td>
																	<td width="310"><textarea name="weakness" cols="47" rows="5">${pknowledge.weakness}</textarea></td>
																</tr>
																<tr class="odd">
																	<td colspan="3">&nbsp;</td>
																</tr>
														</tbody>
													</table>
					</td>
				</tr>
				<tr>
					<td colspan="2" class="rowTitle">${i18n.testactive.feedback.recommendationTitle}</td>
				</tr>
				<tr>
					<td colspan="2" class="eXtremeTable">
						<table width="100%" cellspacing="0" cellpadding="0" border="0" class="tableRegion">
							<tbody class="tableBody">
								<tr>
									<td width="400" align="center" class="tableHeader">${i18n.testactive.feedback.recommendationCourse}/${i18n.testactive.feedback.recommendationModule}</td>
									<td width="210" align="center" class="tableHeader">${i18n.testactive.feedback.recommendationDays}</td>
									<td align="center" class="tableHeader">${i18n.oper.del}</td>
								</tr>
								<c:forEach var="var" items="${list}" varStatus="status">
									<tr class="odd">
										<td align="center">
											<input class="cipt" readonly="readonly" style="width: 389px;" value="<c:if test="${!(empty var.trainCourseId)}"><ecan:viewDto property="name" dtoName="TechTrainCourse" id="${var.trainCourseId}"/><c:if test="${!(empty var.moduleId)}">/<ecan:viewDto property="name" dtoName="TechTrainCourseItem" id="${var.moduleId}"/></c:if></c:if>" />
										</td>
										<td align="center">
											<input class="days" name="cs_${var.trainCourseId}_${var.moduleId}" style="width: 200px;" value="${var.courseDays}" />
										</td>
										<td align="center">
											<a href="javascript:;" onclick="$(this).parent().parent().remove()">${i18n.oper.del}</a>
										</td>
									</tr>
								</c:forEach>
								
								<c:forEach begin="0" end="5" varStatus="status">
									<tr class="odd">
										<td align="center">
											<input class="cipt" readonly="readonly" style="width: 389px;" />
										</td>
										<td align="center">
											<input class="days" name="cs_" style="width: 200px;" />
										</td>
										<td align="center">
											<a href="javascript:;" onclick="$(this).parent().parent().remove()">${i18n.oper.del}</a>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="2">
							<div class="btns">
								<c:if test="${not empty curUserid}">
								<span class="btn">
									<a href="javascript:void(0)" onclick="$('#myForm').submit();">${i18n.button.submit}</a>
								</span>
								</c:if>
								<span class="btn">
									<a href="index">${i18n.button.back}</a>
								</span>
							</div>
					</td>
				</tr>
				</c:if>
			</table>
			</form>
			<div id="viewResult">
				
			</div>
			<br />
			<div id="userLevels" class="eXtremeTable">
			
			</div>
		</div>
	</div>
	
	<%@include file="/common/leftmenu.jsp" %>
	<div class="clearfix_b"></div>
</div>
<%@include file="/common/footer.jsp" %>
</body> 
</html>