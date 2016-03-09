<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="com.ecannetwork.core.i18n.I18N"%>
<%@page import="java.util.List"%>
<%@page import="com.ecannetwork.dto.tech.TechSubitmesDb"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.TreeMap"%>
<%@include file="/common/context.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@include file="/common/head.jsp"%>
		<style type="text/css">
.row {
	margin-bottom: 20px;
	border-bottom: 1px dotted #ddd;
	width: 800px;
}

.row dt {
	font-size: 12px;
	font-weight: bold;
	padding-bottom: 10px;
	line-height: 20px;
}

.row dd {
	padding-left: 40px;
	line-height: 20px;
}

.row dd.hover {
	background: #f0f0f0;
	cursor: pointer;
}

#loading {
	filter: alpha(Opacity =     80);
	-moz-opacity: 0.9;
	opacity: 0.9;
	background: #333;
}

#beginBtn span.btn {
	height: 40px;
	background-image: none;
	background-color: #3675AC;
}

#beginBtn span.btn a {
	font-size: 30px;
	height: 40px;
	line-height: 40px;
	background-image: none;
	background-color: #3675AC;
	text-align: center;
}

#beginBtn {
	position: absolute;
	top: 400px;
	width: 200px;
	left: 400px;
	z-index: 100001;
}

#timerDIV {
	position: absolute;
	top: 400px;
	width: 100px;
	left: 860px;
}

#timerDIV span.t {
	font-size: 30px;
	height: 40px;
	line-height: 40px;
	font-weight: bold;
}
</style>
		<c:if test="${!(empty list)}">
			<script type="text/javascript">

		function submitForm()
		{
			$("#myForm").submit();
		}
		$().ready(function(){
				initJsonForm("#myForm",function(d){
					location.reload();
				},function(d){
					alert(d.data)
				});
			});
	</script>
		</c:if>
	</head>
	<body>
		<%@include file="/common/header.jsp"%>

		<div class="tt">
			${i18n.question.training}
		</div>
		<form id="myForm" action="test" method="post">
			<input type="hidden" name="questionId" value="${id}" />
			<ul>
				<%
					DecimalFormat decimalFormat = new DecimalFormat("####.##");
					int count = 0;
					List<TechSubitmesDb> subList=  (ArrayList)request.getAttribute("sublist");
					Integer usersize =(Integer) request.getAttribute("usercount");
					Map<String, Map<String, Integer>> map = (TreeMap<String, Map<String, Integer>>) request
					        .getAttribute("map");
					if (map != null && map.size() > 0)
					{
						//if(subList!=null && subList.size()>0)
						//{
							//for(int j=0;j<=subList.size();j++)
						//	{
									
							//}
						//}
						
						Iterator i = map.entrySet().iterator();
						while (i.hasNext())
						{
							count++;
							Map.Entry paris = (Map.Entry) i.next();
							if(paris!=null)
							{
								String name = (String)paris.getKey();
							%>
							
							<li class="row">
							<dl>
							<dt>
								<%=count%>:&nbsp;&nbsp;
								<ecan:viewDto dtoName="TechQuestionSubitems" id="<%=name%>"
									property="subitmesName" />
							</dt>
							<br />
							<p>
							<%
							
								Iterator it = ((Map<String,Float>)paris.getValue()).keySet().iterator();
								while(it.hasNext())
								{
									//Map.Entry in = (Map.Entry)it.next();
									String _names = (String)it.next();
									//I _names = ((Map<String,Float>)paris.getValue()).get(in);
									String f ="";
									Float k = ((Map<String,Float>)paris.getValue()).get(_names);
									f = decimalFormat.format(k/usersize*100)+"%";
									%>
								<li>
									<%
									
									if(_names!=null && "1".equals(_names))
									{%>
										<%=I18N.parse("i18n.question.type.stronglydisagree")%>： &nbsp;&nbsp;<%=f%>
									<% 	
									}else if("2".equals(_names))
									{%>
										<%=I18N.parse("i18n.question.type.disagree")%>： &nbsp;&nbsp;<%=f%>
									<%}else if("3".equals(_names))
									{%>
										<%=I18N.parse("i18n.question.type.noresponse")%>： &nbsp;&nbsp;<%=f%>
									<%}else if("4".equals(_names))
									{%>
										<%=I18N.parse("i18n.question.type.agree")%>： &nbsp;&nbsp;<%=f%>
									<%}else if("5".equals(_names))
									{%>
									<%=I18N.parse("i18n.question.type.stronglyagree")%>： &nbsp;&nbsp;<%=f%>
									<%}
									
									
								}
								%>
									</li>
								<%
							}%>
								</p>
						</dl>
						</li>
						<%}
					}
				
				%>
					</p>
				</dl>
			</li>
				<%
					int _count = count;
				Map<String, Map<String, Float>> _map = (TreeMap<String, Map<String, Float>>) request
					        .getAttribute("map1");
					if (_map != null && _map.size() > 0)
					{
						Iterator i = _map.entrySet().iterator();
						while (i.hasNext())
						{
							_count++;
							Map.Entry paris = (Map.Entry) i.next();
							if(paris!=null)
							{
								String name = (String)paris.getKey();
							%>
							
							<li class="row">
							<dl>
							<dt>
								<%=_count%>:&nbsp;&nbsp;
								<ecan:viewDto dtoName="TechQuestionSubitems" id="<%=name%>"
									property="subitmesName" />
							</dt>
							<br />
							<p>
							<%
								
								Iterator it = ((Map<String,Float>)paris.getValue()).keySet().iterator();
								while(it.hasNext())
								{
									String _names = (String)it.next();
									String f ="";
									Float k = ((Map<String,Float>)paris.getValue()).get(_names);
									f = decimalFormat.format(k*100/usersize)+"%";
									%>
								<li>
									<%
									
									if(_names!=null && "1".equals(_names))
									{%>
										<%=I18N.parse("i18n.question.Stronglysatisfactory")%>： &nbsp;&nbsp;<%=f%>
									<% 	
									}else if("2".equals(_names))
									{%>
										<%=I18N.parse("i18n.question.Strongly")%>： &nbsp;&nbsp;<%=f%>
									<%}else if("3".equals(_names))
									{%>
										<%=I18N.parse("i18n.question.Dissatisfactory")%>： &nbsp;&nbsp;<%=f%>
									<%}else if("4".equals(_names))
									{%>
										<%=I18N.parse("i18n.question.stronglydissatisfactory")%>： &nbsp;&nbsp;<%=f%>
									<%}
									
									
								}%>
								</li>
								<%
							}%>
							</p>
						</dl>
						</li>
						<%}
					}
				
				%>
			</ul>
		</form>
		<div>
			<span class="btn"> <a href="javascript:window.close();">${i18n.button.close}</a>
			</span>
		</div>

		<%@include file="/common/footer.jsp"%>
	</body>
</html>