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
		
		.blueRow{
			background: #2274AC; color: #fff; font-size: 14px;
		}
		.blueRow2{
			background: #4f90bd; color: #fff; font-size: 14px;
		}
		
		.pipt, .pipt2{
			width: 55px;height:25px; line-height:25px; padding: 0px; margin: 0px; border: 0px; overflow: hidden; text-align: center; font-size: 14px;
		}
		
		.eXtremeTable .tableHeader{
			font-weight: normal;
		}
		
		#quickNav{
			position: absolute;
			top: 580px;
			left: 68px;
			width: 150px;
			border: 1px solid #f0f0f0;
		}
		#quickNav a{
			    background: none repeat scroll 0 0 #2274AC;
			    color: #FFFFFF;
			    display: block;
			    font-size: 14px;
			    font-weight: bold;
			    height: 25px;
			    line-height: 25px;
			    padding-left: 10px;
			    border-bottom: 1px dotted #fff;
		}
		.eXtremeTable td.avg,.eXtremeTable td.stp{
			font-size: 14px;
		}
	</style>
	<script type="text/javascript">
		function chgUser(id)
		{
			if(id=="")
			{
				return;
			}
			location.href="addPoint?id=${dto.id}&userid=" + id;
		}

		function confirmSubmit()
		{
			if(confirm("${i18n.testactive.text.confirmAddPointFinish}"))
			{
				ajaxGet("submitPoint", {"id":"${dto.id}"}, function(d){
						if(d.success)
						{
							alert("${i18n.commit.success}");
							location.href="index";
						}else
						{
							alert(d.data);
						}
					},"json");
			}
		}

		$().ready(function(){
				//快速导航
				$("#bd").css("position","relative");
				
				$(window).scroll( function() {
					var top = 580;
					if($(this).scrollTop()>610)
					{
						top = $(this).scrollTop() + 30;
					}
					$("#quickNav").css("top",top + "px");
				});
				

				<c:if test="${'1' eq dto.finAddPoint}">
				$("input").each(function(){
						$(this).attr("readonly","readonly");
					});
				</c:if>
				
				<c:if test="${'0' eq dto.finAddPoint}">
				//inputs
				$("input.pipt").focus(function(){
						$(this).css("border","1px inset #f0f0f0");
						$(this).select();
					}).blur(function(){
						$(this).css("border","none");
											
					}).change(function(){
						//save change
						_this = this;
						
						var ids = $(this).attr("id").split("_");
						
						var _val = 0;
						if($(this).val() != "")
							_val = parseInt($(this).val());
												
						
						if(ids[2] != "100")
						{//
							if(_val < 0 || _val >6){
								alert("${i18n.testactive.msg.point0to6}");
								$(this).css("color","red");
								return;
							}
							
							//avg,stp
							var total=0;
							var count = 0;
	
							$(this).parent().parent().find("input").each(function(){
									if($(this).val() != "")
									{
										var v = parseInt($(this).val());
										
										count++;
										total += v;
									}
								});
							if(count>0)
								$(this).parent().parent().find(".avg").html(Math.round(total/count*100)/100);
						}else
						{//ptp
							if(_val < 0 || _val >100){
								alert("${i18n.testactive.msg.point0to100}");
								$(this).css("color","red");
								return;
							}

							if($(this).val() != "")
							{	
								var v =  parseInt($(this).val()) /20 +1;
								$(this).parent().parent().find(".ptp6").html(v);
							}else
							{
								$(this).parent().parent().find(".ptp6").html("");
							}
						}
						

						$.get("savePoint",{
							"id":"${dto.id}",
							"watcherId":ids[2],
							"dimId":ids[1],
							"userid":"${curUserid}",
							"point":$(this).val()
						},function(){
							$(_this).css("color","blue");
						});
					});

				</c:if>

				showHide('#m_1000');
			});

			function showHide(id)
			{
				$(id).parent().children().hide();
				$(id).parent().children().css("background-color","#2274AC");
				$(id).css("background-color","#dfdfdf");
				$(id).show();
			}

			function reloadPtpImage()
			{
				$("#ptpImage").attr("src","ptpImage.jpeg?id=${param.id}&userid=${param.userid}&r_=" + Math.random());
			}
	</script>
</head>
<body>
<%@include file="/common/header.jsp" %>

<div class="centerbody centerbox">
	<div id="contentwrapper">
		<div id="content_right">
			<div class="tt">${i18n.testactive.oper.addPoint}</div>
			
			<c:if test="${not empty curUserid}">
			<div id="quickNav"><!-- 快速导航 -->
				<!-- <a href="javascript:void()" style="background: #f0f0f0; color: #2274AC">${i18n.testactive.text.nav}</a> -->
				<c:forEach items="${dimension}" var="var">
						<a href="javascript:void(0)" onclick="showHide('#m_${var.dimensionId}')">
							<d:viewDomain value="${var.dimensionId}" domain="TESTSTEP" />
						</a>
				</c:forEach>
			</div>
			</c:if>
			
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
					<td height="25" width="100" align="right" style="padding-right: 20px;">${i18n.testactive.filed.watchmen}:</td>
					<td align="left">
						<c:forEach items="${dto.watchMenIds}" var="var" varStatus="status">
							<ecan:viewDto property="name" dtoName="EcanUser" id="${var}"/>,&nbsp;
						</c:forEach>
					</td>
				</tr>
				<tr>
					<td height="25" width="100" align="right" style="padding-right: 20px;">${i18n.testactive.text.hasPointUser}:</td>
					<td align="left">
						<select style="width: 180px;" onchange="chgUser(this.value)">
							<option value="">-- ${i18n.select.pls} --</option>
							<c:forEach items="${hasPoints}" var="var" varStatus="status">
							<option value="${var}" <c:if test="${curUserid eq var}">selected="selected"</c:if>>
								<ecan:viewDto property="name" dtoName="EcanUser" id="${var}"/>
							</option>
							</c:forEach>
						</select>
						<b class="bold">(${hasPointCount})</b>
						
						<c:if test="${'0' eq dto.finAddPoint && haventPointCount==0}">
							<span class="btn">
								<a href="javascript:confirmSubmit()">${i18n.testactive.button.finAddPoint}</a>
							</span>
						</c:if>
						
						<c:if test="${'1' eq dto.finAddPoint}">
							<span style="color: red;">${i18n.testactive.text.finAddPointCannotEdit}</span>
						</c:if>
					</td>
				</tr>

				<c:if test="${haventPointCount>0}">
				<tr>
					<td height="25" width="100" align="right" style="padding-right: 20px;">${i18n.testactive.text.haventPointUser}:</td>
					<td align="left">
						<select style="width: 180px;" onchange="chgUser(this.value)">
							<option value="">-- ${i18n.select.pls} --</option>
							<c:forEach items="${dto.userids}" var="var" varStatus="status">
							<option value="${var}" <c:if test="${curUserid eq var}">selected="selected"</c:if>>
								<ecan:viewDto property="name" dtoName="EcanUser" id="${var}"/>
							</option>
							</c:forEach>
						</select>
						<b class="bold">(${haventPointCount})</b>
					</td>
				</tr>
				</c:if>
				
				<tr>
					<td colspan="2">&nbsp;</td>
				</tr>		
						
				<c:if test="${not empty curUserid}">
				<tr>
					<td colspan="2" class="rowTitle" style="height: 30px; line-height: 30px;">
						${i18n.testactive.filed.student}:&nbsp;&nbsp;<ecan:viewDto property="name" dtoName="EcanUser" id="${curUserid}"/>
						
						<span class="btn" style="float: right;">
							<a href="downloadPointStat?id=${dto.id}&userid=${curUserid}" target="_blank">${i18n.testactive.button.downloadPointStat}</a>
						</span>
						
						<span class="btn" style="float: right;">
							<a href="viewPointStat?id=${dto.id}&userid=${curUserid}" target="_blank">${i18n.testactive.button.viewPointStat}</a>
						</span>
						
					</td>
				</tr>
				<tr>
					<td colspan="2" class="eXtremeTable" id="m_s" style="border: 1px solid #ddd;">
					<%
						DecimalFormat dft = new DecimalFormat("####.##");
					
						Row ptpRow = null;
						TechTestActive active = (TechTestActive) request.getAttribute("dto");
						List rows = (List)request.getAttribute("dimension");
						for(int i=0; i<rows.size(); i++)
						{
							Row row = (Row)rows.get(i);
							
							
							if(row.getDimensionId().equals(TechConsts.PTP))
							{
								ptpRow = row;
								continue;
							}
							
							int watchMensCount = active.getWatchMenIds().size();
					%>
						<table border="0"  cellspacing="0"  cellpadding="0"  class="tableRegion"  width="100%" id="m_<%=row.getDimensionId()%>" style="margin-top: 20px;">
							<tbody class="tableBody" >
							<tr align="center">
								<td class="blueRow" colspan="<%=watchMensCount+2 %>">
									<d:viewDomain value="<%=row.getDimensionId()%>" domain="TESTSTEP" />
								</td>
							</tr>
							<%
								int tabIdx = 0;
								for(int j=0; j<row.getChilds().size(); j++)
								{
									Row row2 = row.getChilds().get(j);
							%>
								<tr align="center">
									<td align="left" class="blueRow2" colspan="<%=watchMensCount+1%>">
										<d:viewDomain value="<%=row2.getDimensionId()%>" domain="TESTSTEP" />
									</td>
									<td class="blueRow2" width="55">${i18n.testactive.viewStat.avgPoint}</td>
								</tr>
								<tr align="center">
									<td align="right" class="tableHeader">${i18n.testactive.viewStat.watchmen}</td>
									<c:forEach items="${dto.watchMenIds}" var="var" varStatus="status">
									<td width="55" class="tableHeader"><ecan:viewDto property="name" dtoName="EcanUser" id="${var}"/></td>
									</c:forEach>
									<td width="55" class="tableHeader">&nbsp;</td>
								</tr>
							<%
									for(int m=0; m<row2.getChilds().size(); m++)
									{
										tabIdx++;
										
										Row row3 = row2.getChilds().get(m);
										String cls = "odd";
										
										if(m%2==0)
										{
											cls = "even";
										}
							%>
										<tr class="<%=cls%>"  onmouseover="this.className='highlight'"  onmouseout="this.className='<%=cls%>'" >
											<td>
												<d:viewDomain value="<%=row3.getDimensionId()%>" domain="TESTSTEP" />
											</td>
											<%
												int w = 1;
												for(Iterator it = active.getWatchMenIds().iterator(); it.hasNext(); )
												{
													String watchMen = (String)it.next();
													TechTestActivePoint point = (TechTestActivePoint) row3.getPoints().get(watchMen);
													String p = (point==null?"":point.getPoints().intValue() + "");
											%>
												<td>
													<input tabindex="<%=w++*1000 + tabIdx %>" <%if(!"".equals(p)){%>style="color:blue;"<%}%> id="i_<%=row3.getDimensionId()%>_<%=watchMen%>" type="text" class="pipt" size="3" maxlength="1" value="<%=p%>" />
												</td>
											<%
												}
											%>
											<td align="center" class="avg"><%=dft.format(row3.getWatchMensAvgPoint()) %></td>
										</tr>	
							<%
									}
								}
							%>
							</tbody>
						</table>
					<%
						}
						
						if(ptpRow != null)
						{
					%>
						<table border="0"  cellspacing="0"  cellpadding="0"  class="tableRegion"  width="400" id="m_<%=ptpRow.getDimensionId()%>" style="margin-top: 20px;">
							<tbody class="tableBody" >
							<tr align="center">
								<td class="blueRow" colspan="3">
									<d:viewDomain value="<%=ptpRow.getDimensionId()%>" domain="TESTSTEP" />
								</td>
							</tr>
							<tr align="center">
									<td class="tableHeader">${i18n.testactive.viewStat.dim}</td>
									<td width="60" class="tableHeader">${i18n.testactive.viewStat.ptp100}</td>
									<td width="55" class="tableHeader">${i18n.testactive.viewStat.ptp6}</td>
							</tr>
							<%
							for(int i=0; i<ptpRow.getChilds().size(); i++)
							{
								Row rowp = (Row) ptpRow.getChilds().get(i);
								
								String cls = "odd";
								
								if(i%2==0)
								{
									cls = "even";
								}
								
								TechTestActivePoint point = (TechTestActivePoint) rowp.getPoints().get("100");
								String p = (point==null?"":point.getPoints().intValue() + "");
								
							%>
							<tr class="<%=cls%>"  onmouseover="this.className='highlight'"  onmouseout="this.className='<%=cls%>'" >
								<td>
									<d:viewDomain value="<%=rowp.getDimensionId()%>" domain="TESTSTEP" />
								</td>
								<td>
									<input class="pipt" <%=(point==null?"":"style='color:blue;'") %> id="i_<%=rowp.getDimensionId()%>_100" value="<%=p%>" />
								</td>
								<td class="ptp6" style="font-size: 14px; text-align: center; color: blue;"><%=(point==null?"":dft.format(point.getPoints()/20+1))%></td>
							</tr>								
							<%
							}
							%>
							<tr>
								<td colspan="3">
									<br />
									<br />
									<div style="text-align: right; padding-right:20px; line-height: 25px; border-bottom: 1px dotted #ddd; height: 25px; background: #f0f0f0;">
										<a href="javascript:;" onclick="reloadPtpImage()" style="color: #2274AC; text-decoration: underline;font-size: 14px; height: 25px; line-height: 25px;">${i18n.oper.refresh}</a>
									</div>
									<img id="ptpImage" src="ptpImage.jpeg?id=${param.id}&userid=${param.userid}" alt="ptp chart image" />
								</td>
							</tr>
							</tbody>
						</table>
					<%
						}
					%>
					</td>
				</tr>
				</c:if>
			</table>
			
			<div class="btns">
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