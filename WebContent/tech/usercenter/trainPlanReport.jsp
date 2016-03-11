<%@page import="com.ecannetwork.dto.tech.TechTrainPlan"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="java.util.Map"%>
<%@include file="/common/context.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@include file="/common/head.jsp"%>
<style type="text/css">
b.cBox {
	width: 10px;
	height: 10px;
	display: inline-block;
	margin-right: 4px;
}

.holiday {
	background: #c82d20;
}

.fill {
	background: #999;
}

.plan {
	background: #003c65; /*green*/
}

.confirm {
	background: #51ae30; /*blue*/
}

.cancel {
	background: #faaa00; /*yellow*/
}

.tips {
	display: none;
	position: absolute;
	text-align: left;
	background: #f0f0f0;
	border: 1px dotted #000;
	width: 300px;
}

.tips b {
	font-weight: bold;
	color: blue;
}

.tips ul li label {
	width: 60px;
}

.tips ul li {
	margin: 0px;
	border-bottom: 1px dotted #fff;
}

.tips li span.info {
	line-height: 25px;
}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		$(".fill, .plan, .confirm, .cancel").hover(function() {
			$(this).find(".tips").show();
		}, function() {
			$(this).find(".tips").hide();
		});
	});
</script>
</head>
<body>
	<%@include file="/common/header.jsp"%>

	<div class="centerbody centerbox">
		<div style="padding: 10px;">
			<div style="width: 900px;">
				<div style="height: 25px; line-height: 25px;">
					<span style="float: left;"> <b class="cBox fill"></b>待分配资源&nbsp;&nbsp;&nbsp; <b class="cBox plan"></b>计划中&nbsp;&nbsp;&nbsp; <b class="cBox confirm"></b>确定/已完成&nbsp;&nbsp;&nbsp; <b
						class="cBox cancel"></b>已取消
					</span> <span style="float: right; margin-right: 200px;"> ${i18n.trainplan.filed.yearValue}: <select onchange="location.href='trainPlanReport?year=' + this.value">
							<option <c:if test="${year eq '2012'}">selected="selected"</c:if> value="2012">2012</option>
							<option <c:if test="${year eq '2013'}">selected="selected"</c:if> value="2013">2013</option>
							<option <c:if test="${year eq '2014'}">selected="selected"</c:if> value="2014">2014</option>
							<option <c:if test="${year eq '2015'}">selected="selected"</c:if> value="2015">2015</option>
							<option <c:if test="${year eq '2016'}">selected="selected"</c:if> value="2016">2016</option>
							<option <c:if test="${year eq '2017'}">selected="selected"</c:if> value="2017">2017</option>
							<option <c:if test="${year eq '2018'}">selected="selected"</c:if> value="2018">2018</option>
							<option <c:if test="${year eq '2019'}">selected="selected"</c:if> value="2019">2019</option>
							<option <c:if test="${year eq '2020'}">selected="selected"</c:if> value="2020">2020</option>
					</select>
					</span>
				</div>
				<table cellpadding="0" cellspacing="0">
					<tr valign="top">
						<td width="200"><ec:table method="post" items="list" var="var" view="ecan" width="200" imagePath="${coreImgPath}/table/*.gif" filterable="false" autoIncludeParameters="false"
								showPagination="false" sortable="false">
								<ec:row>
									<ec:column title="${i18n.user.filed.name}" property="resName" width="130">
										<ecan:viewDto property="name" dtoName="EcanUser" id="${var}" />
									</ec:column>
								</ec:row>
							</ec:table></td>
						<td width="700">
							<div style="overflow: auto; width: 700px;">
								<div class="eXtremeTable">
									<table width="1300" cellspacing="0" cellpadding="0" border="0" class="tableRegion" id="ec_table">
										<thead>
											<tr>
												<%
													for (int i = 0; i < 52; i++)
													{
												%>
												<td width="25" class="tableHeader"><%=i + 1%></td>
												<%
													}
												%>
											</tr>
										</thead>
										<tbody class="tableBody">
											<%
												Map plansMap = (Map) request.getAttribute("plansMap");

												List userids = (List) request.getAttribute("list");
												for (int idx = 0; idx < userids.size(); idx++)
												{
													String userid = (String) userids.get(idx);

													Map userPlans = (Map) plansMap.get(userid);

													if (idx % 2 == 0)
													{
											%>
											<tr class="odd">
												<%
													}
														else
														{
												%>
												<tr class="even">
													<%
														}
															for (int i = 0; i < 52; i++)
															{
																TechTrainPlan plan = (TechTrainPlan) userPlans.get(i + 1);
																if (plan == null)
																{
													%>
													<td>&nbsp;</td>
													<%
														}
																else
																{
													%>
													<td class="<%=plan.getStatus()%>"><span class="form tips" style="display: none;"> <b> <ecan:viewDto property="name" dtoName="TechTrainCourse" id="<%=plan.getTrainId()%>" />
														</b><br /> <b><%=plan.getPlanDays()%></b>:<%=plan.getRemark()%>
													</span></td>
													<%
														}
															}
													%>
												</tr>
												<%
													}
												%>
											
										</tbody>
									</table>
								</div>
							</div>
						</td>
					</tr>
				</table>

			</div>
			<div class="tt">
				${i18n.usercenter.title.trainReport} <span class="btn" style="float: right;"> <a href="index">${i18n.button.back}</a>
				</span>
			</div>
		</div>
		<div class="clearfix_b"></div>
	</div>
	<%@include file="/common/footer.jsp"%>
</body>
</html>