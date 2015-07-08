<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="cw" uri="/WEB-INF/tld/cw.tld"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>

<c:set var="contextPath" value="${requestScope['javax.servlet.forward.context_path']}" scope="request" />

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	
	<link rel="stylesheet" href="${contextPath}/gfx/_main.css" />
	<link rel="stylesheet" href="${contextPath}/gfx/_app.css" />
	<title>ERROR 500</title>
</head>
<body>
	<div class="message msg-error">
    	<c:forEach items="${messages}" var="msgByType">
    		<c:forEach items="${msgByType.value}" var="msg" varStatus="status">
    			<h2>${msg.message}</h2>
    			<p>${msg.messageBody}</p>
	    			
    			<c:if test="${not status.last }">
    				<div class="hr"><hr></div>
    			</c:if>
    		</c:forEach>
    	</c:forEach>
	</div>
	<c:if test="${showErrorStack}">
	<div class="message msg-error">
		<details>
			<summary>
				<spring:message code="global.page.error.details" />
			</summary>
			<pre>
				<c:out value="${exceptionString}" /><br />
			</pre>
		</details>
	</div>
	</c:if>
</body>
</html>