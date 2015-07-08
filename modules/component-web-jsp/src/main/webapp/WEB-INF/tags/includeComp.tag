<%@ tag body-content="scriptless" isELIgnored="false" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="cw" uri="/WEB-INF/tld/cw.tld"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>

<%@ attribute name="name" required="true" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="sourceComponent" required="false" rtexprvalue="true" type="com.nortal.spring.cw.core.web.component.composite.Component" %>

<c:set var="previousCompItem" value="${compItem}"/>
<c:set var="previousCompItemEditable" value="${previousCompItemEditable}"/>

<c:choose>
	<c:when test="${empty sourceComponent and not empty compItem}">
		<c:set var="compItem" value="${compItem.components[name]}" scope="request" />
	</c:when>
	<c:when test="${empty sourceComponent}">
		<c:set var="compItem" value="${controllerComponent.components[name]}" scope="request" />	
	</c:when>
	<c:otherwise>
		<c:set var="compItem" value="${sourceComponent.components[name]}" scope="request" />
	</c:otherwise>
</c:choose>

<c:set var="compItemEditable" value="${compItem.editable}" scope="request" />
<c:if test="${compItem.visible}">
	<jsp:include page="${compItem.viewPath}" />
</c:if>

<c:set var="compItem" value="${previousCompItem}" scope="request"/>
<c:set var="compItemEditable" value="${previousCompItem}" scope="request"/>

