<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="cw" uri="/WEB-INF/tld/cw.tld"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>

<tag:html> 
	<body>
	    <div class="message">
	    	<c:forEach items="${messages}" var="msgByType">
	    		<c:forEach items="${msgByType.value}" var="msg">
	    			<c:if test="${msg.activeLanguageMessage}">
	    				<h2>${msg.message}</h2>
	    				<p>${msg.messageBody}</p>
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
</tag:html>

