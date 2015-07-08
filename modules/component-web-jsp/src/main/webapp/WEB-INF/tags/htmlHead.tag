<%@ tag body-content="scriptless" isELIgnored="false" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>

<jsp:useBean id="requestUtil" class="com.nortal.spring.cw.core.web.util.RequestUtil" />
 
<c:set var="activeLang" value="${model.activeLanguageCode}" scope="request" />
<c:set var="activeLangCode" value="${model.activeLanguageCode}" scope="request" />
<c:set var="activeUrlPrefix" value="${model.activeUrlPrefix}" scope="request" />
<c:set var="contextPath" value="${requestScope['javax.servlet.forward.context_path']}" scope="request" />
<c:set var="modelCompName" value="${requestUtil.getModalCompName()}" scope="request" />


<!DOCTYPE html>
<html data-ng-app="epmModule" id="ng-app">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link rel="stylesheet" href="${contextPath}/gfx/_main.css" />
	<link rel="stylesheet" href="${contextPath}/gfx/_app.css" />
	
	<tag:secured privilege="LOGGED_IN_USER">
		<link rel="stylesheet" href="${contextPath}/gfx/jquery/jquery.loader.css" />
	</tag:secured>
	
	<script type="text/javascript">
		var activeLang = "${activeLangCode}"; 
		var contextPath = "${contextPath}";
		var modelCompName = "${modelCompName}";
	</script>
	
	
	<script type="text/javascript" src="${contextPath}/js/jquery/jquery.js"></script>
	<script type="text/javascript" src="${contextPath}/js/jquery/jquery-ui.js"></script>
	<script type="text/javascript" src="${contextPath}/js/jquery/jquery.maskedinput.js"></script>
	<script type="text/javascript" src="${contextPath}/js/jquery/jquery.multiselect.js"></script>
	<script type="text/javascript" src="${contextPath}/js/jquery/jquery.placeholder.js"></script>
	<script type="text/javascript" src="${contextPath}/js/jquery/jquery.datepick.js"></script>
	<script type="text/javascript" src="${contextPath}/js/jquery/jquery.numeric.js"></script>
	<script type="text/javascript" src="${contextPath}/js/jquery/jquery.loader.js"></script>
	<script type="text/javascript" src="${contextPath}/js/jquery/jquery.cookie.js"></script>
	
	<script type="text/javascript" src="${contextPath}/js/angular/angular.js"></script>
	<script type="text/javascript" src="${contextPath}/js/angular/angular-resource.js"></script>
	<script type="text/javascript" src="${contextPath}/js/tinymce/tinymce.min.js"></script>
	<script type="text/javascript" src="${contextPath}/js/app.js"></script>
	<script type="text/javascript" src="${contextPath}/js/form.js"></script>
	<script type="text/javascript" src="${contextPath}/js/resources.js"></script>
	<script type="text/javascript" src="${contextPath}/js/custom.js"></script>
	<script type="text/javascript" src="${contextPath}/js/controllers.js"></script>
	<script type="text/javascript" src="${contextPath}/js/services.js"></script>
	<script type="text/javascript" src="${contextPath}/js/idCard.js"></script>
	
	<title><spring:message code="${model.mainMenuComponent.activeMenuText}" /> - TEST</title>
</head>

<jsp:doBody />

</html>

