<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="ec" uri="/WEB-INF/tld/extremecomponents.tld"%>
<%@ taglib prefix="d" uri="/WEB-INF/tld/ecan-domain.tld"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>


<%
	String contextPath = request.getContextPath();
	request.setAttribute("ctxPath", contextPath);
	
	application.setAttribute("themeName", "default");
	//框架资源路径
	request.setAttribute("iconPath16",contextPath +  "/platform/images/icons/16x16");
	request.setAttribute("iconPath22", contextPath + "/platform/images/icons/22x22");
	request.setAttribute("iconPath32", contextPath + "/platform/images/icons/32x32");
	request.setAttribute("coreImgPath", contextPath + "/platform/images");
	request.setAttribute("coreCssPath", contextPath + "/platform/css");
	request.setAttribute("coreJsPath", contextPath + "/platform/js");
	request.setAttribute("coreJqueryPath",contextPath +  "/platform/jquery");
	
	//应用程序的资源路径
	request.setAttribute("imgPath", contextPath + "/images");
	request.setAttribute("cssPath", contextPath + "/css");
	request.setAttribute("jsPath", contextPath + "/js");
	
	//正则表达式匹配规则
	request.setAttribute("RN_NUM", "^1-9");
	request.setAttribute("RN_CHAR", "^_\\-a-zA-Z");
	request.setAttribute("RN_CHAR_NUM", "^_\\-a-zA-Z1-9");
	request.setAttribute("RN_WORD", "^_\\-a-zA-Z1-9\\u4e00-\\u9fa5");
%>