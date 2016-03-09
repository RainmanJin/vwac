<%@page import="com.ecannetwork.dto.tech.TechTrainPlan"%>
<%@page import="java.util.List"%>
<%@page import="com.ecannetwork.dto.tech.TechResourseManager"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@include file="/common/head.jsp" %>
	<style type="text/css">
		.tips{
			display: none;
			position: absolute;
			text-align: left;
			background: #f0f0f0;
			border: 1px dotted #000;
			width: 300px;
		}
		
		.tips b{
			font-weight: bold;
			color: blue;
		}
		
	</style>
	<script type="text/javascript">
	$(document).ready(function(){
		$(".cBox").hover(function(){
				$(this).find(".tips").show();
			},function(){
				$(this).find(".tips").hide();
			});
	});
	</script>
</head>
<body>
<%@include file="/common/header.jsp" %>

<div class="centerbody centerbox">
		<div style="padding: 10px;">
			<div class="tt">
				${i18n.trainplan.btn.resReport}
				
				<span class="btn" style="float: right;">
					<a href="view?year=${param.year}&brand=${param.brand}">${i18n.button.back}</a>
				</span>
			</div>
			
			<div style="width: 900px;">
				<table cellpadding="0" cellspacing="0">
					<tr valign="top">
						<td width="250">
							<ec:table tableId="aa" method="post" items="list" var="var"
							 view="ecan" width="250"
								imagePath="${coreImgPath}/table/*.gif" filterable="false" sortable="false" autoIncludeParameters="false"
								showPagination="false">
									<ec:row>
										<ec:column title="${i18n.resourse.name}" property="resName" width="180">
											<div style="width: 180px; overflow: hidden;">
												<ecan:SubString content="${var.resName}" end="25" fix="..."/>
											</div>
										</ec:column>
										<ec:column title="${i18n.resourse.type}" property="resType" width="70" filterCell="droplist" filterOptions="DOMAIN_COMMONLYUSED">
											<d:viewDomain domain="COMMONLYUSED"  value="${var.resType}"/>
										</ec:column>
									</ec:row>
							</ec:table>	
						</td>
						<td width="650">
							<div style="overflow: auto; width: 650px;">
							<div class="eXtremeTable">
								<table width="1300" cellspacing="0" cellpadding="0" border="0" class="tableRegion" id="bb_table">
									<thead>
									<tr>
										<%
										for(int i=0; i<52; i++)
										{
										%>
										<td width="25" class="tableHeader"><%=i+1 %></td>
										<%
										}
										%>
									</tr>
									<%
										List list = (List) request.getAttribute("list");
										for(int r=0; r<list.size(); r++)
										{
											TechResourseManager res = (TechResourseManager) list.get(r);
									%>
									<tr class="<%=r%2==0?"odd":"even"%>">
									<%
											for(int i=0; i<52; i++)
											{
												pageContext.setAttribute("week", i+1);
												
												Float value = res != null ?res.getWeekUsedMap().get(i+1): new Float(0);
												pageContext.setAttribute("value", value);
												List ps = (List) res.getWeekPlanMap().get(i+1);
												
										%>
										<td width="25" class="cBox">
											${value }
											<%
												if(ps != null && ps.size()>0)
												{
											%>
													<span class="form tips" style="display: none;">
														<%
															for(int m=0; m<ps.size(); m++)
															{
																TechTrainPlan pp = (TechTrainPlan) ps.get(m);
														%>
																<b><ecan:viewDto property="name" dtoName="TechTrainCourse" id="<%=pp.getTrainId()%>" /></b><br />
														<%
															}
														%>
													</span>										
											<%
												}else{
											%>
												&nbsp;											
											<%
												}
											%>
										</td>
										<%
											}
										%>
										</tr>
									<%
										}
									%>
									</thead>
								</table>
							</div>
							</div>
						</td>
					</tr>
				</table>
				
			</div>
		</div>
		<div class="clearfix_b"></div>
</div>
<%@include file="/common/footer.jsp" %>
</body> 
</html>