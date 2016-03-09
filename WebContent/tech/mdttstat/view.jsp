<%@page import="java.util.Iterator"%>
<%@page import="com.ecannetwork.dto.tech.TechMdttOption"%>
<%@page import="java.util.Set"%>
<%@page import="com.ecannetwork.dto.tech.TechMdttQustion"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@include file="/common/context.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@include file="/common/head.jsp" %>
	<style>
		.qus h1{
			line-height: 30px; font-size: 14px; font-weight: bold; border-bottom: 1px dotted #999; margin-top: 20px;
		}
		
		.qus li{
			line-height: 25px; padding-left: 2em;
		}
		.qus .pointbg{
			width: 200px; float: right; background: #efefef; height: 10px;
		}
		.qus .pointbg .point{
			height: 8px; margin: 1px; background: red;
		}
	</style>
</head>
<body>
<%@include file="/common/header.jsp" %>

<div class="centerbody centerbox">
	<div id="contentwrapper">
		<div id="content_right">
			<div class="tt">
				${i18n.mdttstat.ques}
				<span class="btn" style="float: right;">
					<a href="javascript:history.go(-1)">${i18n.button.back}</a>
				</span>
			</div>

			<%
				List list = (List) request.getAttribute("list");
				if(list.size()>0)
				{
					for(int q=0; q<list.size(); q++)
					{
						TechMdttQustion qus = (TechMdttQustion) list.get(q);
			%>
						<div class="qus">
							<h1><%=qus.getQusName()%></h1>
							<ul>
							<%
								Set set = qus.getOptions().keySet();
								for(Iterator it = set.iterator(); it.hasNext();)
								{
									String o = (String) it.next();
									TechMdttOption opt = (TechMdttOption) qus.getOptions().get(o);
							%>
								<li>
									<span><%=opt.getOptName()%></span>
									<%
										if(qus.getAnsCount()>0)
										{
									%>
									<div class="pointbg">
										<div class="point" style="width: <%=200*opt.getAnsList().size()/qus.getAnsCount()%>px;"></div>
									</div>									
									<%
										}
									%>
								</li>							
							<%
								}
							%>
							</ul>
						</div>			
			<%
					}
				}else
				{
			%>
				<div style="height: 200px; line-height: 40px; font-size: 14px; font-weight: bold; color: blue;">
				${i18n.mdttstat.notFound}					
				</div>
			<%
				}
			%>			
		</div>
	</div>
	
	<%@include file="/common/leftmenu.jsp"%>
	<div class="clearfix_b"></div>
</div>
<%@include file="/common/footer.jsp" %>
</body> 
</html>