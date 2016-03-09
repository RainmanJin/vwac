<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.util.Set"%>
<%@page import="com.ecannetwork.tech.facade.HolidayFacade"%>
<%@page import="com.ecannetwork.dto.tech.TechTrainCourse"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="com.ecannetwork.dto.tech.TechTrainPlan"%>
<%@page import="java.util.Iterator"%>
<html>
<head>
	<%@include file="/common/head.jsp" %>
<style>
.form li input{
	width: 150px;
}

#teacherDiv{
	display: inline-block;
}

.urow{
	display: block;
	height: 25px; line-height: 25px;
	border-bottom: 1px dotted #ddd;
}

a.operText{
	color: blue;
	text-decoration: underline;
	font-size: 14px;	
}
.w800{
	width: 500px;
	float: left;
}
.form td.label label{
	width: 160px;
	float: none;
	text-align: right;
	display: block;
}
td .eXtremeTable{
	float: left;
}
		.fill{
			background: #999;
		}
		
		.plan{
			background: #003c65;/*green*/
		}
		
		.confirm{
			background: #51ae30;/*blue*/
		}
		
		.cancel{
			background: #faaa00;/*yellow*/
		}
</style>
<script type="text/javascript">
$(document).ready(function(){

	initJsonForm("#myForm",function(d){
		location.reload();
	},function(d){
		alert(d.data)
	});
});

function delPlan()
{
	//?year=2012&brand=VW
	ajaxGet("del",{"id":"${dto.id}"},function(r){
		if(r.success)
			location.href="view?year=${dto.yearValue}&brand=${dto.brand}";
		else
			alert(r.data);
	},"json");
	
}

function cancelPlan()
{
	var reason = prompt("${i18n.trainplan.msg.plsCancelReason}:", "${i18n.valid.notEmpty}");
	if(reason != "" && reason != undefined)
	{
		ajaxGet("cancel",{"id":"${dto.id}", "reason":reason},function(r){
			if(r.success)
				location.href="view?year=${dto.yearValue}&brand=${dto.brand}";
			else
				alert(r.data);
		},"json");	
	}
}

function finishPlan()
{
	if(!confirm("${i18n.trainplan.msg.confirmSubmitComplete}?"))
	{
		return;
	}
	
	ajaxGet("complete",{"id":"${dto.id}"},function(r){
		if(r.success)
			location.href="view?year=${dto.yearValue}&brand=${dto.brand}";
		else
			alert(r.data);
	},"json");	
}


function submitPlan()
{
	<c:choose>
		<c:when test="${dto.status eq 'draft'}">
			if(!confirm("${i18n.trainplan.msg.confirmSubmitDraft}?"))
			{
				return;
			}
		</c:when>
		<c:when test="${dto.status eq 'fill'}">
			if(!confirm("${i18n.trainplan.msg.confirmSubmitFill}?"))
			{
				return;
			}
		</c:when>
	</c:choose>
	
	ajaxPost("savePlan",{"id":"${dto.id}", 
							"status":$("#status").val(), 
							"remark":$("#remark").val(), 
							"module":$("#modulesID").val(),
							"planQuesID":$("#planQuesID").val()
						},function(r){
		if(r.success)
			location.href="view?year=${dto.yearValue}&brand=${dto.brand}";
		else
			alert(r.data);
	},"json");
}

/**
 * @param yearWeek: 周格式为 年度*100+第几周， 例如201101表示2011年第一周
 */
function loadTeacherDiv(yearWeek)
{
	$("#teacherDiv").load("listOnWorkTeacher?id=${dto.id}&yearWeek=" + yearWeek + "&trainCourseId=${dto.trainId}&brand=${dto.brand}");
}
 

function delResource(planResId,type)
{
	ajaxGet("delResource",{"planResourceId":planResId,"type":type},function(r){
		if(r.success)
			location.reload();
		else
			alert(r.data);
	},"json");
}

function addCSResource()
{
	$.fn.jmodal({
        title: '${i18n.trainplan.filed.csresource}',
        width:600,
        content: function(body) {
            body.html('loading...');
            body.load('listCSResources?id=${dto.id}');
        },
        buttonText: { ok: '${i18n.button.ok}', cancel: '${i18n.button.cancel}' },
        okEvent: function(data, args) {
        	var css = $("input[name=csResource]");
    		
        	var ids = new Array();
    		var vals = new Array();
    		
        	for(var i=0; i<css.length; i++)
        	{
        		var cc = css[i];
        		var count = $(cc).val();
        		
        		if(!count || count == undefined || count == "")
        		{
        			continue;
        		}else
        		{
        			count = parseFloat(count);
					
        			if(!count || count == "NaN" || count<=0)
            		{
            			alert("${i18n.trainplan.msg.inputResourceCountError}");	
            			return;
            		}else
            		{
            			count = count.toFixed(1);
            			if((count * 10) % 5 != 0)
            			{
                			alert("${i18n.trainplan.msg.inputResourceCountError2}");	
                			return;
            			}
            			ids.push($(cc).attr("id"));
            			vals.push(count);
            		}
        		}
        	}
        	
        	if(ids.length>0)
        	{
        		ajaxGet("selResources",{"resourceid":ids.join(","),"count":vals.join(","),"type":"cs","id":"${dto.id}"},function(r){
    				if(r.success)
    					location.reload();
    				else
    					alert(r.data);
    			},"json");
        	}else{
    			alert("${i18n.trainplan.msg.inputResourceCountError}");	
        	}
        }
   });
}
function addXHPResource()
{
	$.fn.jmodal({
        title: '${i18n.trainplan.filed.xhpresource}',
        width:600,
        content: function(body) {
            body.html('loading...');
            body.load('listXHPResources');
        },
        buttonText: { ok: '${i18n.button.ok}', cancel: '${i18n.button.cancel}' },
        okEvent: function(data, args) {
        	var css = $("input[name=xhpResource]");
    		
        	var ids = new Array();
    		var vals = new Array();
    		
        	for(var i=0; i<css.length; i++)
        	{
        		var cc = css[i];
        		var count = $(cc).val();
        		
        		if(!count || count == undefined || count == "")
        		{
        			continue;
        		}else{
        			count = parseInt(count);
					
        			if(!count || count == "NaN" || count<=0)
            		{
            			alert("${i18n.trainplan.msg.inputResourceCountError}");	
            			return;
            		}else
            		{
            			ids.push($(cc).attr("id"));
            			vals.push(count);
            		}
        		}
        	}
        	
        	if(ids.length>0)
        	{
        		ajaxGet("selResources",{"resourceid":ids.join(","),"count":vals.join(","),"type":"xhp","id":"${dto.id}"},function(r){
    				if(r.success)
    					location.reload();
    				else
    					alert(r.data);
    			},"json");
        	}else{
    			alert("${i18n.trainplan.msg.inputResourceCountError}");	
        	}
        }
    });
}
function addTeacher()
{
	$.fn.jmodal({
        title: '${i18n.trainplan.filed.teacher}',
        width:600,
        content: function(body) {
            body.html('loading...');
            body.load('listOnWorkTeacher?id=${dto.id}&year=${dto.yearValue}&brand=${dto.brand}&week=${dto.planWeek}&courseId=${dto.trainId}');
        },
        buttonText: { ok: '${i18n.button.ok}', cancel: '${i18n.button.cancel}' },
        okEvent: function(data, args) {
        	//args.hide();
        	var ids = checkedByName("selIds");
        	ajaxGet("saveTeacherOrStudents",{"planId":"${dto.id}","ids": ids, "type":"teacher"},function(r){
    			if(r.success)
    				location.reload();
    			else
    				alert(r.data);
    		},"json");
    	}
    });
}

function addStudent()
{
	$.fn.jmodal({
        title: '${i18n.trainplan.filed.student}',
        width:600,
        content: function(body) {
            body.html('loading...');
            body.load('listStudents?year=${dto.yearValue}&brand=${dto.brand}&week=${dto.planWeek}&courseId=${dto.trainId}');
        },
        buttonText: { ok: '${i18n.button.ok}', cancel: '${i18n.button.cancel}' },
        okEvent: function(data, args) {
        	var ids = checkedByName("selIds");
        	ajaxGet("saveTeacherOrStudents",{"planId":"${dto.id}","ids": ids, "type":"student"},function(r){
    			if(r.success)
    				location.reload();
    			else
    				alert(r.data);
    		},"json");
        }
    });
}

function deleteTeacherOrStudent(id)
{
	ajaxGet("deleteTeacherOrStudent",{"id":id},function(r){
		if(r.success)
			location.reload();
		else
			alert(r.data);
	},"json");
}

function createNew()
{
	$.fn.jmodal({
        title: '请选择计划培训日期',
        width:600,
        content: function(body) {
            body.html('loading...');
            body.load('checkCreatePlanDays?year=${dto.yearValue}&brand=${dto.brand}&week=${dto.planWeek}&courseId=${dto.trainId}');
        },
        buttonText: { ok: '${i18n.button.ok}', ok2: '${i18n.button.cancel}' },
        okEvent: function(data, args) {
        	var ids = checkedByName("wday");
        	if(ids == "")
        	{
	        	return;
        	}
			//创建
        	ajaxPost("createPlanDays",{"year":"${dto.yearValue}",
        								"brand": "${dto.brand}", 
        								"week":"${dto.planWeek}",
        								"courseId":"${dto.trainId}",
        								"planDays":ids,
        								"remark":$("#_dRemark").val()
        								},
        								function(r){
    			if(r.success)
    				location.href="viewPlanWithPlanID?planid=" + r.data;
    			else
    				alert(r.data);
    		},"json");
        },
        ok2Event: function(){
        	location.href="view?year=${dto.yearValue}&brand=${dto.brand}";
        }
    });	
}

function saveBasic()
{
	var modulesID = $("#modulesID").val();
	var planQuesID = $("#planQuesID").val();
	var remark = $("#remark").val();
	
	if(modulesID == undefined)
		modulesID ="";
	if(planQuesID == undefined)
		planQuesID = "";
	if(remark == undefined)
		remark = "";
	
	ajaxPost("saveBasic",
			{
				"id":"${dto.id}",
				"module":modulesID,
				"planQuesID":planQuesID,
				"remark":remark
			},
			function(r){},
			"json");
}
</script>
</head>
<body>
<%@include file="/common/header.jsp" %>

<%
Set emptyDays = (Set) request.getAttribute("emptyDays");
TechTrainPlan plan = (TechTrainPlan) request.getAttribute("dto");

if(plan.getId() == null || "".equals(plan.getId()))
{//还没有选择日期的
%>
<script>
	$(document).ready(function(){
		
		$.fn.jmodal({
	        title: '请选择计划培训日期',
	        width:600,
	        content: function(body) {
	            body.html('loading...');
	            body.load('checkCreatePlanDays?year=${dto.yearValue}&brand=${dto.brand}&week=${dto.planWeek}&courseId=${dto.trainId}');
	        },
	        buttonText: { ok: '${i18n.button.ok}', ok2: '${i18n.button.cancel}' },
	        okEvent: function(data, args) {
	        	var ids = checkedByName("wday");
	        	if(ids == "")
	        	{
		        	location.href="view?year=${dto.yearValue}&brand=${dto.brand}";
		        	return;
	        	}
				//创建
	        	ajaxPost("createPlanDays",{"year":"${dto.yearValue}",
	        								"brand": "${dto.brand}", 
	        								"week":"${dto.planWeek}",
	        								"courseId":"${dto.trainId}",
	        								"planDays":ids
	        								},
	        								function(r){
	    			if(r.success)
	    				location.href="viewPlanWithPlanID?planid=" + r.data;
	    			else
	    				alert(r.data);
	    		},"json");
	        },
	        ok2Event: function(){
	        	location.href="view?year=${dto.yearValue}&brand=${dto.brand}";
	        }
	    });
		
	});
</script>
<%
}else{
%>

			<div class="tt btns">
				<span style="float: left; line-height: 36px; height: 36px; font-size: 14px; font-weight: bold;">
				${dto.yearValue}&nbsp;<d:viewDomain value="${dto.brand}" domain="BRAND"/>${i18n.trainplan.title}&nbsp;&nbsp;&nbsp;
				</span>
				<span class="btn" style="float: right;">
					<a href="view?year=${dto.yearValue}&brand=${dto.brand}">${i18n.button.back}</a>
				</span>
				
				<span class="btn" style="float: right;">
					<a target="_blank" href="exportUsers?id=${dto.id}">${i18n.trainplan.btn.exportUsers}</a>
				</span>
				<c:if test="${(dto.status eq 'confirm') && !(empty dto.planQuesID)}">
					<span class="btn" style="float: right;">
						<a target="_blank" href="${ctxPath}/techc/questionmanager/pretest?planid=${dto.id}&id=${dto.planQuesID}">${i18n.trainplan.btn.ques}</a>
					</span>
				</c:if>
				
				<c:if test="${(dto.status eq 'draft') or ('admin' eq current_user.role.id)}">
				<span class="btn" style="float: right;">
					<a href="javascript:delPlan()">${i18n.button.del}</a>
				</span>
				</c:if>
				
				<c:if test="${!(dto.status eq 'cancel')}">
					<c:if test="${('planadm' eq current_user.role.id) or ('admin' eq current_user.role.id)}">
						<span class="btn" style="float: right;">
							<a href="javascript:cancelPlan()">${i18n.button.cancel}</a>
						</span>
					</c:if>
					
					<c:if test="${(dto.status eq 'draft' && 'planadm' eq current_user.role.id) or (dto.status eq 'fill' && 'resadm' eq current_user.role.id) or ('admin' eq current_user.role.id)}">
						<span class="btn" style="float: right;">
							<a href="javascript:submitPlan()">${i18n.button.submit}</a>
						</span>
					</c:if>
					
					<c:if test="${(dto.status eq 'plan') and ('planadm' eq current_user.role.id || 'admin' eq current_user.role.id)}">
						<span class="btn" style="float: right;">
							<a href="javascript:finishPlan()">${i18n.button.finish}</a>
						</span>
					</c:if>
				</c:if>
			</div>
			
			<div class="subTab">
				<ul>
					<c:forEach var="v" items="${plans}">
						<li <c:if test="${dto.id eq v.id}">class="active"</c:if>>
							<a  href="viewPlanWithPlanID?planid=${v.id}">
								<%
									TechTrainPlan tp = (TechTrainPlan)pageContext.getAttribute("v");
									if(tp != null)
									{
									for(String d: tp.getWeekDays())
									{
										if(d.equals("1"))
										{
								%>
											${i18n.week.Monday}
								<%
										}else
										{
											if(d.equals("2"))
											{
									%>
												${i18n.week.Tuesday}
									<%
											}else
											{
												if(d.equals("3"))
												{
										%>
													${i18n.week.Wednesday}
										<%
												}else
												{
													if(d.equals("4"))
													{
											%>
														${i18n.week.Thursday}
											<%
													}else
													{
														if(d.equals("5"))
														{
												%>
															${i18n.week.Friday}
												<%
														}else
														{
															if(d.equals("6"))
															{
												%>
															${i18n.week.Saturday}
												<%
															}else
															{
												%>
															${i18n.week.Sunday}
												<%
															}
														}
													}
												}
											}
										}
									}
									}
								%>
							</a>
						</li>
					</c:forEach>
				</ul>
				
				<c:if test="${('planadm' eq current_user.role.id) or ('admin' eq current_user.role.id)}">
				<%
				if(emptyDays != null && emptyDays.size()>0)
				{
				%>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="javascript:createNew()" style="color: blue; text-decoration: underline;">
					${i18n.button.add}
					</a>
				<%
					}
				%>
				</c:if>
				<div class="clearfix_l"></div>
			</div>
			<div style="width: 700px; margin-top: 10px;">
			<c:if test="${!(empty dto.cancelReason)}">
				<div style="font-size: 14px; color: red; padding-left: 110px; padding-top: 20px; padding-bottom: 20px;">
				${i18n.trainplan.msg.cancelReason}:&nbsp;&nbsp;&nbsp;${dto.cancelReason}
				</div>
			</c:if>
		<form id="myForm" name="myForm" action="savePlan" method="post" class="form">
			<ul>
				<li>
					<label>${i18n.trainplan.filed.status}</label>
					<input type="hidden" name="status" id="status" value="${dto.status}" />
					<input type="text" class="gray" readonly="readonly" value="<d:viewDomain domain="TRAINPLANSTATUS" value="${dto.status}" />" />
				</li>
				<li>
					<label>${i18n.trainplan.filed.trainCourse}</label>
					<input type="text" readonly="readonly" class="gray" value="<ecan:viewDto property="name" dtoName="TechTrainCourse" id="${dto.trainId}"/>" />
				</li>
				<c:if test="${not empty modules}">
				<li>
					<label>${i18n.trainplan.filed.trainModules}</label>
					<select name="modules" id="modulesID" onchange="saveBasic()">
						<option value="">${i18n.select.pls}</option>
						<c:forEach items="${modules}" var="var">
							<option <c:if test="${dto.moduleId eq var.id}">selected="selected"</c:if> value="${var.id}">${var.name}</option>					
						</c:forEach>
					</select>
				</li>
				</c:if>
				<li style="height: 50px;">
					<label>${i18n.trainplan.filed.remark}</label>
					<textarea name="remark" id="remark" onchange="saveBasic()">${dto.remark}</textarea>
				</li>
				
				<c:choose>
					<c:when test="${(dto.status eq 'draft')}">
						<c:if test="${quesSize>0}">
						<li>
							<label>${i18n.question.name}</label>
							<select id="planQuesID" name="planQuesID" onchange="saveBasic()">
								<c:forEach var="v" items="${ques}">
									<option value="${v.id}">${v.questionName}</option>
								</c:forEach>
							</select>
						</li>
						</c:if>
					</c:when>
					<c:otherwise>
						<c:if test="${not empty dto.planQuesID}">
						<li>
							<label>${i18n.question.name}</label>
							<input value="<ecan:viewDto property="questionName" dtoName="TechQuestionManager" id="${dto.planQuesID}" />" disabled="disabled" class="gray" />
						</li>
						</c:if>
					</c:otherwise>
				</c:choose>
								
				<li style="height: auto; width: 700px;">
					<table width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td class="label"><label>${i18n.trainplan.filed.teacher}</label></td>
							<td>
								<ec:table action="" method="get" items="teachers" var="var" view="ecan" width="500" rowsDisplayed="10000"
						imagePath="${coreImgPath}/table/*.gif" showExports="false" filterable="false" sortable="false" showStatusBar="false" autoIncludeParameters="false" form="myForm">
										<ec:row>
											<ec:column title="${i18n.user.filed.name}" property="name" width="30%">
												<ecan:viewDto property="name" dtoName="EcanUser" id="${var.userId}"/>
											</ec:column>
											<ec:column title="${i18n.user.filed.company}" property="xx" width="30%">
												<d:viewDomain value="${ef:viewDto('EcanUser',var.userId,'company')}" domain="COMPANY" />
											</ec:column>
											
											<c:if test="${(dto.status eq 'draft') or (!(dto.status eq 'cancel') && 'planadm' eq current_user.role.id) or ('admin' eq current_user.role.id)}">
												<ec:column title="<a class='operText' href='javascript:addTeacher()'>${i18n.oper.add}</a>" width="15%" property="oper">
													<a href="javascript:void(0)" onclick="deleteTeacherOrStudent('${var.id}')" class="oper">${i18n.oper.del}</a>
													<a href="${ctxPath}/techc/usermanager/profile?id=${var.userId}" target="_blank" class="oper">${i18n.oper.profile}</a>
												</ec:column>
											</c:if>
										</ec:row>
								</ec:table>
							</td>
						</tr>
						<tr>
							<td class="label"><label>${i18n.trainplan.filed.student}</label></td>
							<td>
								<ec:table action="" method="get" items="students" var="var" view="ecan" width="500" rowsDisplayed="10000"
							imagePath="${coreImgPath}/table/*.gif" showExports="false" filterable="false" sortable="false" showStatusBar="false" autoIncludeParameters="false" form="myForm">
									<ec:row>
										<ec:column title="${i18n.user.filed.name}" property="name" width="30%">
											<ecan:viewDto property="name" dtoName="EcanUser" id="${var.userId}"/>
										</ec:column>
										<ec:column title="${i18n.user.filed.company}" property="xx" width="30%">
											<d:viewDomain value="${ef:viewDto('EcanUser',var.userId,'company')}" domain="COMPANY" />
										</ec:column>
										<c:if test="${(dto.status eq 'draft') or (!(dto.status eq 'cancel') && 'planadm' eq current_user.role.id) or ('admin' eq current_user.role.id)}">
											<ec:column title="<a class='operText' href='javascript:addStudent()'>${i18n.oper.add}</a>" property="oper" width="15%" filterCell="oper" sortable="false">
												<a href="javascript:void(0)" onclick="deleteTeacherOrStudent('${var.id}')" class="oper">${i18n.oper.del}</a>
												<a href="${ctxPath}/techc/usermanager/profile?id=${var.userId}" target="_blank" class="oper">${i18n.oper.profile}</a>
											</ec:column>
										</c:if>
									</ec:row>
							</ec:table>
							</td>
						</tr>
						
						<c:if test="${!(dto.status eq 'draft')}">
						<tr>
							<td class="label"><label>${i18n.trainplan.filed.csresource}</label></td>
							<td>
								<ec:table action="" method="get" items="csresources" var="var" view="ecan" width="500" rowsDisplayed="10000"
							imagePath="${coreImgPath}/table/*.gif" showExports="false" filterable="false" sortable="false" showStatusBar="false" autoIncludeParameters="false" form="myForm">
									<ec:row>
										<ec:column title="${i18n.trainplan.filed.resourceName}" property="name" width="30%">
											<ecan:viewDto property="resName" dtoName="TechResourseManager" id="${var.resId}"/>
										</ec:column>
										<ec:column title="${i18n.trainplan.filed.resourceCount}" property="resSum" width="30%">
										</ec:column>
										<c:if test="${(!(dto.status eq 'cancel') && 'resadm' eq current_user.role.id) or ('admin' eq current_user.role.id)}">
											<ec:column title="<a class='operText' href='javascript:addCSResource()'>${i18n.oper.add}</a>" property="oper" width="15%" filterCell="oper" sortable="false">
												<a href="javascript:void(0)" onclick="delResource('${var.id}','cs')" class="oper">${i18n.oper.del}</a>&nbsp;
											</ec:column>
										</c:if>
									</ec:row>
							</ec:table>
							</td>
						</tr>
						<tr>
							<td class="label"><label>${i18n.trainplan.filed.xhpresource}</label></td>
							<td>
									<ec:table action="" method="get" items="xhpresources" var="var" view="ecan" width="500" rowsDisplayed="10000"
							imagePath="${coreImgPath}/table/*.gif" showExports="false" filterable="false" sortable="false" showStatusBar="false" autoIncludeParameters="false" form="myForm">
									<ec:row>
										<ec:column title="${i18n.trainplan.filed.resourceName}" property="name" width="30%">
											<ecan:viewDto property="conName" dtoName="TechConsumablesManager" id="${var.resId}"/>
										</ec:column>
										<ec:column title="${i18n.trainplan.filed.resourceCount}" property="resSumInt" width="30%">
										</ec:column>
										<c:if test="${(!(dto.status eq 'cancel') && 'resadm' eq current_user.role.id) or ('admin' eq current_user.role.id)}">
											<ec:column title="<a class='operText' href='javascript:addXHPResource()'>${i18n.oper.add}</a>" property="oper" width="15%" filterCell="oper" sortable="false">
												<a href="javascript:void(0)" onclick="delResource('${var.id}','xhp')" class="oper">${i18n.oper.del}</a>&nbsp;
											</ec:column>
										</c:if>
									</ec:row>
									</ec:table>
							</td>
						</tr>
						</c:if>
					</table>
				</li>
			</ul>
		</form>
</div>
<div style="clear: left"></div>
<br />
<br />
<br />

<%@include file="/common/footer.jsp" %>
<%
}
%>
</body> 
</html>