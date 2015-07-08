<%@ tag body-content="scriptless" isELIgnored="false" pageEncoding="UTF-8" description="Katkestamise sündmuse link. Sündmus peab olema kontrolleris või komponendis defineeritud" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="cw" uri="/WEB-INF/tld/cw.tld"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>

<%@ attribute name="eventName" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="label" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="elementName" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="alignLeft" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="sourceComponent" required="false" rtexprvalue="true" type="com.nortal.spring.cw.core.web.component.composite.Component" %>

<c:choose>
	<c:when test="${empty sourceComponent and not empty compItem}">
		<c:set var="sourceComponent" value="${compItem}" />
	</c:when>
</c:choose>

<c:if test="${empty eventName}">
	<c:set var="eventName" value="pageActionButtonPageCancelEvent" />
</c:if>

<c:if test="${empty label}">
	<c:set var="label" value="global.link.cancel" />
</c:if>

<c:if test="${empty elementName}">
	<c:set var="elementName" value="${compItem.displayId}" />
</c:if>

<c:set var="align" value="right"/>

<c:if test="${alignLeft}">
	<c:set var="align" value="left" />
</c:if>

<div class="${align}">
	<tag:eventLink eventName="${eventName}" elementName="${elementName}" label="${label }" />
</div>
