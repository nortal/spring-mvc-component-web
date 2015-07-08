<%@ tag body-content="scriptless" isELIgnored="false" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="cw" uri="/WEB-INF/tld/cw.tld"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>

<%@ attribute name="modalName" required="true" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="sourceComponent" required="false" rtexprvalue="true" type="com.nortal.spring.cw.core.web.component.composite.complex.ComplexComponent" %>

<c:if test="${empty sourceComponent and not empty compItem and not empty compItem.modalComps}">
	<c:set var="sourceComponent" value="${compItem}" scope="page" />
</c:if>

<c:if test="${empty sourceComponent}">
	<c:set var="sourceComponent" value="${controllerComponent}" scope="page" />	
</c:if>

<tag:componentExists componentName="${modalName}" sourceComponent="${sourceComponent}">
	<c:set var="previousCompItem" value="${compItem}"/>
	<c:set var="compItem" value="${sourceComponent.components[modalName]}" scope="request" />
	
	<c:if test="${compItem.properties.showLink}">
		<a href="javascript:void(0);" onclick="return showModal(escapeId('${compItem.displayId}'), '${compItem.properties.sizeStyleClass}');"><spring:message code="${compItem.properties.linkLabel}" /></a>
	</c:if>
	<c:if test="${compItem.type eq 'POPUP'}">
		<jsp:doBody var="tagBody" />
		<c:if test="${not compItem.hasSimpleContent}">
			<c:set property="jspContent" value="${tagBody}" target="${compItem}" />
		</c:if>
	</c:if>
	<c:set var="compItem" value="${previousCompItem}" scope="request"/>
</tag:componentExists>
