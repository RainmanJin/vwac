<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/context.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" href='${coreCssPath}/fullcalendar.css'
	rel='stylesheet' />
<link type="text/css" href='${coreCssPath}/fullcalendar.print.css'
	rel='stylesheet' media='print' />
<script type="text/javascript" src='${jqueryPath}/moment.min.js'></script>
<script type="text/javascript" src='${jqueryPath}/jquery.min.js'></script>
<script type="text/javascript" src='${jqueryPath}/fullcalendar.js'></script>
<script type="text/javascript" src='${jqueryPath}/lang-all.js'></script>
<script type="text/javascript">
	$(document).ready(function() {
		
		var eventsJson = {};
		var array = new Array();
		$.ajax({ 
			 type: 'POST', 
			 url: "/techc/client/wxtaskList",
			 dataType:"json",
			 success: function(data){ 
				if(typeof(data.data) != 'undefined'){
					var json = data.data;
					for(var i=0;i<json.length;i++){
						var obj = json[i].starttime.replace('[','').replace(']','').replace(' ','');
						var timeobj = obj.split(',');
						if(json[i].type=='P'){
							for(var j=0;j<timeobj.length;j++){
								var event = {};
								event.title = '个人任务';
								event.url = "/techc/client/wxdetail?selectday="+timeobj[j].replace(' ','')+"&type=P";
								event.start = timeobj[j];
								array.push(event);
							}
						}
						if(json[i].type=='S'){
							for(var k=0;k<timeobj.length;k++){
								var event = {};
								event.title = '分配任务';
								event.url = "/techc/client/wxdetail?selectday="+timeobj[k].replace(' ','')+"&type=S";
								event.start = timeobj[k];
								array.push(event);
							}
						}
					}
					eventsJson = JSON.parse(JSON.stringify(array));
					$('#calendar').fullCalendar({
						header: {
								left: 'prev',
								center: 'title',
								right: 'today  next'
							},
						defaultDate:new Date(),
						lang:'zh-cn',
						editable: true,
						eventLimit: true, 
						events: eventsJson
					});
					$(".fc-title").each(function(){
						var text = $(this).text();
					    if(text == '个人任务'){
					    	$(this).parent().parent().removeClass("fc-event");
					    	$(this).parent().parent().addClass("fc-event1");
					    }
					});
				}else{
					window.location.href = '/techc/client/wechatlogin';
				}
			 }
		});
	});
	
</script>
<style>
body {
	margin: 10px 10px;
	padding: 0;
	font-family: "Lucida Grande", Helvetica, Arial, Verdana, sans-serif;
	font-size: 14px;
}

#calendar {
	max-width: 1000px;
	margin: 0 auto;
}
</style>
</head>
<body>
<div style="max-width: 1000px;height:60px;margin:0px auto;margin-bottom: 30px">
<span style="width:140px;float: right;background-color:#ddd;
			border-radius: 5px 5px 5px 5px;
			font-size: 30px;
			line-height:60px;
			text-align:center;">
<a href="/techc/client/logout">${i18n.logout}</a></span></div>
<div id='calendar'></div>
</body>
</html>
