<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<%@page import="java.util.List"%>
<%@page import="com.ecannetwork.tech.controller.bean.testactive.Row"%>
<%@page import="com.ecannetwork.dto.tech.TechTestActive"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.ecannetwork.dto.tech.TechTestActivePoint"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="com.ecannetwork.tech.util.TechConsts"%>
			<table border="0"  cellspacing="0"  cellpadding="0"  class="tableRegion"  width="100%" >
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
																		<d:viewDomain value="1000" domain="TESTSTEP"/>
																	</td>
																	<td width="310">${p1000.advantage}</td>
																	<td width="310">${p1000.weakness}</td>
																</tr>
																<tr height="100" class="odd">
																	<td width="100" align="center">
																		<d:viewDomain value="1003" domain="TESTSTEP"/>
																	</td>
																	<td width="310">${p1003.advantage}</td>
																	<td width="310">${p1003.weakness}</td>
																</tr>
																<tr height="100" class="even">
																	<td width="100" align="center">
																		<d:viewDomain value="1001" domain="TESTSTEP"/>
																	</td>
																	<td width="310">${p1001.advantage}</td>
																	<td width="310">${p1001.weakness}</td>
																</tr>
																<tr height="100" class="odd">
																	<td width="100" align="center">
																		<d:viewDomain value="1004" domain="TESTSTEP"/>
																	</td>
																	<td width="310">${p1004.advantage}</td>
																	<td width="310">${p1004.weakness}</td>
																</tr>
																<tr height="100" class="even" style="display: none;">
																	<td width="100" align="center">
																		${i18n.testactive.feedback.filedKnowledgeTest}
																	</td>
																	<td width="310">${pknowledge.advantage}</td>
																	<td width="310">${pknowledge.weakness}</td>
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
								</tr>
								<c:forEach var="var" items="${list}" varStatus="status">
									<tr class="odd">
										<td align="center">
											<c:if test="${!(empty var.trainCourseId)}"><ecan:viewDto property="name" dtoName="TechTrainCourse" id="${var.trainCourseId}"/><c:if test="${!(empty var.moduleId)}">/<ecan:viewDto property="name" dtoName="TechTrainCourseItem" id="${var.moduleId}"/></c:if></c:if>
										</td>
										<td align="center">
											${var.courseDays}
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</td>
				</tr>
				</c:if>
			</table>