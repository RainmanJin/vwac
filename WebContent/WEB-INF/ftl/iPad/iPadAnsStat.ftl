<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta name="viewport" content="width=device-width; target-densitydpi=device-dpi; minimum-scale=1.0; maximum-scale=1.0"/>
<style>
	*{
		margin:0px; padding:0px;
	}
	body{
		
	}
	
	dl{
		margin:10px 10px 30px 10px;
		width:500px;
	}
	
	dt{
		border-bottom: 1px solid #DDDDDD;
	    font-size: 18px;
	    font-weight: bold;
	    margin-bottom: 10px;
	}
	dd{
		padding:10px;
		background-color:#f0f0f0;
		border-radius:3px 3px 3px 3px;
	}
	
	dd.even{
		border-top:2px solid #FFFFFF;
	}
	label{
		font-size:14px; font-weight:bold; float:left;
		width:100px; overflow:hidden;
	}
	p{
		font-size:12px; width:380px; float:left;
	}
	em{
		display:block; clear:both;
	}
</style>
</head>
<body>
<#if qusList?exists>
	<#list qusList as x>
		<dl>
			<dt>${x_index+1}：&nbsp;${x.qusName}</dt>
			<#list x.inputAnsList as u>
			<dd <#if u_index%2==1>class="even"</#if>>
				<label>${u.userID}</label>
				<p>${u.opt}</p>
				<em></em>
			</dd>
			</#list>
		</dl>
	</#list>
<#else>
	<dl><dt>没有答题记录</dt></dl>
</#if>
</body>
</html>