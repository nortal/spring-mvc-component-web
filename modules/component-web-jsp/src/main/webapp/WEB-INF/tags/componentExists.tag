<%@ tag body-content="scriptless" isELIgnored="false" pageEncoding="UTF-8" description="Kontrollitakse kas komponent esineb kas aktiivses või ette antud komponendis. Kui eisineb siis renderdatakse tagi sisend" trimDirectiveWhitespaces="true" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>

<%@ attribute name="componentName" required="true" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="sourceComponent" required="false" rtexprvalue="true" type="com.nortal.spring.cw.core.web.component.composite.complex.ComplexComponent" %>

<c:choose>
	<c:when test="${empty sourceComponent and not empty compItem}">
		<c:set var="sourceComponent" value="${compItem}" />
	</c:when>
	<c:when test="${empty sourceComponent}">
		<c:set var="sourceComponent" value="${controllerComponent}" />	
	</c:when>
</c:choose>
 
 <c:if test="${sourceComponent.isComponentExists(componentName)}">
 	<jsp:doBody />
 </c:if>