<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
		<%@include file="/common/head.jsp"%>
		<style>
			.table_border td{border: 1px #DDD solid;}
			.table_border td div{
				line-height: 24px;
			}
			.table_border td.content {
				padding-top: 10px;
				vertical-align: top;
				padding-left: 20px;
			}
			.table_border td p{
				font-weight: bold; padding: 5px 0px;
			}
			span.btn{
				margin: 0px;
			}
			
			.rbtns span.btn a{
				padding: 0px 7px;
			}
			div.tt{
				border: none;
				margin-bottom: 0px;
				margin-top: 5px;
				margin-left: 5px;
			}
		</style>
		<script>
			function addTestting(id,courseInstanceId)
			{
				var url = "";
				location.href = "${ctxPath}/techc/elearning/testting?id="+id+"&courseInstanceId="+courseInstanceId;
			}
			
			function addStudy(courseId, courseInstanceId)
			{
				location.href="${ctxPath}/techc/elearning/study?id="+courseId + "&courseInstanceId=" + courseInstanceId;
			}
			function publish(courseId)
			{
				$.fn.jmodal({
		            title: '${i18n.traincourse.filed.publish}',
		            width:600,
		            content: function(body) {
		                body.html('loading...');
		                body.load('listAttchment?courseId='+courseId);
		            },
		            buttonText: {cancel: '${i18n.button.cancel}' }
		        });
			}
			
			function viewTesttingInstances(courseId,courseInstanceId)
			{
				$.fn.jmodal({
		            title: '${i18n.traincourse.filed.publish}',
		            width:600,
		            content: function(body) {
		                body.html('loading...');
		                body.load('viewTesttingInstances?courseId='+courseId+"&courseInstanceId="+courseInstanceId);
		            },
		            buttonText: {cancel: '${i18n.button.cancel}' }
		        });
			}
	
		</script>
	</head>
	<body>
		<%@include file="/common/header.jsp"%>

		<div class="centerbody centerbox">
			<div id="contentwrapper">
				<div class="tt">${i18n.course.ing}</div>
				<c:if test="${!(empty ingInstance)}">
			 	 	<table class="table_border" width="100%">
				 	 	<c:forEach items="${ingInstance}" var="dto">
							<tr>
								<td style="margin-left: 20px; margin-top: 20px; width: 200px; height: 160px;">
									<img width="200" height="160" src="${ctxPath}${dto.courseInstance.preview}"/>
								</td>
								<td width="546" class="content">
										<p>${dto.courseInstance.name}</p>
										<div style="height: 72px; overflow: hidden;">
											<ecan:SubString content="${dto.courseInstance.remark}" end="240" fix="..."/>
										</div>
										<div class="btns">
											<span class="btn">
												<a href="javascript:addStudy('${dto.courseInstance.courseId}','${dto.courseInstance.id}');">${i18n.oper.startlearn}</a>
											</span>
											<span class="btn">
												<a href="javascript:publish('${dto.courseInstance.courseId}','${dto.courseInstance.id}');">${i18n.oper.download}</a>
											</span>
										</div>
								</td>
								<td width="190" class="content">
										<p>${i18n.testting.filed.action}</p>
										<div style="width: 180px;height: 14px;background: #dfdfdf;border:1px solid #aaa;">
											<span style="width: ${(dto.student.studentCoursePro*180)/100}px;height: 14px;display: block;background:#3675AC;"></span>
										</div>
										<p>${i18n.techdb.filed.testing}</p>
										<label>${i18n.oper.finish}&nbsp;${dto.student.testCount==null?"0":dto.student.testCount}&nbsp;${i18n.oper.count}</label><br/>
										<label>${i18n.oper.avg}：${dto.student.avgPoint==null?"0.00":dto.student.avgPoint}</label><br/>
										<label>${i18n.oper.highpoint}：${dto.student.highPoint==null?"0.00":dto.student.highPoint}</label><br/>
										<div class="btns rbtns">
											<c:if test="${dto.courseInstance.hasDbs}">
												<span class="btn" style="margin-left:40px;">
													<a href="javascript:addTestting('${dto.courseInstance.courseId}','${dto.courseInstance.id}');">${i18n.oper.starttestting}</a>
												</span>
											</c:if>
											<c:if test="${dto.student.testCount >0 && srole!='student'}">
												<span class="btn" style="margin-top: 5px;">
													<a href="javascript:viewTesttingInstances('${dto.courseInstance.courseId}','${dto.courseInstance.id}');">${i18n.oper.viewanswer}</a>
												</span>
											</c:if>
										</div>
								</td>
							</tr>
							</c:forEach>
						</table>
					</c:if>
					<c:if test="${empty ingInstance}">
						<table class="table_border">
							<tr>
								<td>
									<div style="width:753px; ">
										<p align="center" style="font-weight: bold; font-size: 20px;">${i18n.oper.noing }</p><br/>
										</div>
									</div>
								</td>
								<td>
									<div style="width: 180px;">
										<label></label><br/>
									</div>
								</td>
							</tr>
						</table>
					</c:if>
					<div class="tt">${i18n.course.expire}</div>
					
					<c:if test="${!(empty expireInstance)}">	
						<table class="table_border" width="100%">
					 	 	<c:forEach items="${expireInstance}" var="expiredto" begin="0" end="5">
								<tr>
									<td style="margin-left: 20px; margin-top: 20px; width: 200px; height: 160px;">
										<img width="200" height="160" src="${ctxPath}${expiredto.courseInstance.preview}"/>
									</td>
									<td width="546" class="content">
											<p>${expiredto.courseInstance.name}</p>
											<div style="height: 72px; overflow: hidden;">
											<ecan:SubString content="${expiredto.courseInstance.remark}" end="240" fix="..."/>
											</div>
									</td>
									<td width="190" class="content">
										<p>${i18n.testting.filed.action}</p>
										<div style="width: 180px;height: 14px;background: #dfdfdf;border:1px solid #aaa;">
											<span style="width: ${(dto.student.studentCoursePro*180)/100}px;height: 14px;display: block;background:#3675AC;"></span>
										</div>
										<p>${i18n.techdb.filed.testing}</p>
										<label>${i18n.oper.finish}&nbsp;${expiredto.student.testCount==null?"0":expiredto.student.testCount}&nbsp;${i18n.oper.count}</label><br/>
										<label>${i18n.oper.avg}：${expiredto.student.avgPoint==null?"0.00":expiredto.student.avgPoint}</label><br/>
										<label>${i18n.oper.highpoint}：${expiredto.student.highPoint==null?"0.00":expiredto.student.highPoint}</label><br/>
										<div class="btns rbtns">
										<c:if test="${dto.student.testCount >0 && srole!='student'}">
											<span class="btn" style="margin-top: 5px;">
												<a href="javascript:viewTesttingInstances('${expiredto.courseInstance.courseId}','${expiredto.courseInstance.id}');">${i18n.oper.viewanswer}</a>
											</span>
										</c:if>
										</div>
									</td>
								</tr>
							</c:forEach>
							</table>
					</c:if>
					
					<c:if test="${empty expireInstance}">
						<table class="table_border">
							<tr>
								<td>
									<div style="width:753px; ">
										<p align="center" style="font-weight: bold; font-size: 20px;">${i18n.oper.nofinish }</p><br/>
									</div>
								</td>
								<td>
									<div style="width: 180px;">
										<label></label><br/>
									</div>
								</td>
							</tr>
						</table>
					</c:if>
			</div>
	<%@include file="/common/leftmenu.jsp" %>
	<div class="clearfix_b"></div>
</div>
<%@include file="/common/footer.jsp" %>
</body> 
</html>