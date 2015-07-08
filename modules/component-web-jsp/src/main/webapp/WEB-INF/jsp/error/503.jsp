<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="cw" uri="/WEB-INF/tld/cw.tld"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>

<tag:htmlHead>
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
</body>
</tag:htmlHead>