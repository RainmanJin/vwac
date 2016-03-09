<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="ec" uri="/WEB-INF/tld/extremecomponents.tld"%>
<%@ taglib prefix="d" uri="/WEB-INF/tld/ecan-domain.tld"%>
<%@ taglib prefix="ecan" uri="/WEB-INF/tld/ecan.tld"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="ef" uri="/WEB-INF/tld/ecan-functions.tld" %>

<%@ taglib prefix="fn" uri="/WEB-INF/tld/functions.tld" %>

<c:set var="ctxPath" scope="request">${pageContext.request.contextPath}</c:set>
<c:set var="themeName" scope="request">default</c:set>

<c:set var="iconPath16" scope="request"><c:out value="${ctxPath}"/>/platform/images/icons/16x16</c:set>
<c:set var="iconPath22" scope="request"><c:out value="${ctxPath}"/>/platform/images/icons/22x22</c:set>
<c:set var="iconPath32" scope="request"><c:out value="${ctxPath}"/>/platform/images/icons/32x32</c:set>
<c:set var="jqueryPath" scope="request"><c:out value="${ctxPath}"/>/platform/jquery</c:set>
<c:set var="coreImgPath" scope="request"><c:out value="${ctxPath}"/>/platform/images</c:set>
<c:set var="coreCssPath" scope="request"><c:out value="${ctxPath}"/>/platform/css</c:set>
<c:set var="coreJsPath" scope="request"><c:out value="${ctxPath}"/>/platform/js</c:set>


<c:set var="imgPath" scope="request"><c:out value="${ctxPath}"/>/images</c:set>
<c:set var="cssPath" scope="request"><c:out value="${ctxPath}"/>/css</c:set>
<c:set var="jsPath" scope="request"><c:out value="${ctxPath}"/>/js</c:set>


<c:set var="RN_NUM" scope="request">^1-9</c:set>
<c:set var="RN_CHAR" scope="request">^_\-a-zA-Z</c:set>
<c:set var="RN_CHAR_NUM" scope="request">^_\-a-zA-Z1-9</c:set>
<c:set var="RN_WORD" scope="request">^_\-a-zA-Z1-9\u4e00-\u9fa5</c:set>