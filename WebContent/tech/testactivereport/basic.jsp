<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<div class="form">
						<ul>
							<li>
								<label>${i18n.testactive.filed.name}</label>
								<input type="text" disabled="disabled" value="${dto.name}" class="gray" />
							</li>
							<li>
								<label>${i18n.testactive.filed.brand}</label>
								<input type="text" disabled="disabled" value="<d:viewDomain value="${dto.brand}" domain="BRAND"/>" class="gray" />
							</li>
						</ul>						
						
						<div class="lineSeperater"><!-- 测试部分 -->
							<ul>
								<li>
									<label>${i18n.testactive.filed.testcount}</label>
									<input type="text" value="${dto.testCount}" class="gray" disabled="disabled" />
								</li>
								<li>
									<label>${i18n.testactive.filed.testtime}</label>
									<input type="text" value="${dto.testTimelimit}" class="gray" disabled="disabled" />
									
								</li>
							</ul>
						</div>
						<div class="lineSeperater"><!-- 人员部分 -->
							<ul>
								<li>
									<label>${i18n.testactive.filed.mainMan}</label>
									<input type="text" value="${dto.mainManId}" class="gray" disabled="disabled" />
								</li>
								<li><!-- 主持人 -->
									<label>${i18n.testactive.filed.hoster}</label>
									<c:choose><!-- 仅未开始的活动能够编辑 -->
										<c:when test="${dto.status eq 'edit'}">
											<input type="text" value='<ecan:viewDto property="name" dtoName="EcanUser" id="${dto.hosterId}"/>' class="gray" />
										</c:when>
										<c:otherwise>
											<input type="text" value='<ecan:viewDto property="name" dtoName="EcanUser" id="${dto.hosterId}"/>' class="gray" />
										</c:otherwise>									
									</c:choose>
								</li>
								<li style="height: auto;"><!-- 观察员 -->
									<label>${i18n.testactive.filed.watchmen}</label>
									<table width="560" cellpadding="0" cellspacing="0" border="0">
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
									<label>${i18n.testactive.filed.student}</label>
									<table width="560" cellpadding="0" cellspacing="0" border="0">
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
</div>