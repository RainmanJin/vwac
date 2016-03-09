<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>

	<title>
		<c:if test="${!(empty current_menu)}">${current_menu.appName} &lt; </c:if>${i18n.system.title}
	</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta http-equiv="pragma" content="no-cache" /> 
    <meta http-equiv="cache-control" content="no-cache" /> 
    <meta http-equiv="expires" content="0" />
    
	<link rel="shortcut icon" href="${imgPath}/favicon.ico" />
	<link rel="stylesheet" type="text/css" href="${cssPath}/site.css" />
	<link rel="stylesheet" type="text/css" href="${coreCssPath}/form.css" />
	<link rel="stylesheet" type="text/css" href="${coreCssPath}/extremecomponents.css" />
	<script type="text/javascript" src="${jqueryPath}/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="${jqueryPath}/jquery.form-2.43.js"></script>
	<script type="text/javascript" src="${coreJsPath}/common.js"></script>
	<link rel="stylesheet" type="text/css" href="${jqueryPath}/jmodal/css/jquery.jmodal.css" />
    <script type="text/javascript" src="${jqueryPath}/jmodal/js/jquery.jmodal.js"></script>
	<link rel="stylesheet" type="text/css" href="${jqueryPath}/jquery.autocomplete/jquery.autocomplete.css" />
    <script type="text/javascript" src="${jqueryPath}/jquery.autocomplete/jquery.autocomplete.js"></script>
    <script type="text/javascript" src="${ctxPath}/platform/My97DatePicker/WdatePicker.js"></script>
    
    <script>
    $(document).ready(function(){
		initForm(".form");
	});

	function changeLang(lang)
	{
		//location.href="${ctxPath}/?_LANG=" + lang;
		var uri = location.href;
		uri = uri.replace("#nogo","");
		if(uri.indexOf("?") == -1)
		{
			location.href=uri + "?_LANG=" + lang;
		}else
		{
			location.href=uri + "&_LANG=" + lang;
		}
	}
    </script>