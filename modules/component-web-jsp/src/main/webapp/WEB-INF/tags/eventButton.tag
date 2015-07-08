<%@ tag body-content="scriptless" isELIgnored="false" pageEncoding="UTF-8" description="Sündmuste nupp. Sündmus peab olema kontrolleris defineeritud" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="cw" uri="/WEB-INF/tld/cw.tld"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>

<%@ attribute name="eventName" required="true" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="elementName" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="label" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="cssClass" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="submitButton" required="false" rtexprvalue="true" type="java.lang.Boolean"%>
<%@ attribute name="useControllerPrefix" required="false" rtexprvalue="true" type="java.lang.Boolean" description="True puhul lisatakse label ette kontrolleri prefiks" %>
<%@ attribute name="params" required="false" rtexprvalue="true" type="java.lang.String"%>

<c:if test="${useControllerPrefix and not empty controllerComponent }">
	<c:set var="label" value="${controllerComponent.label}.${label}" />
</c:if>

<c:set var="tabindex" value="${empty model ? '' : model.nextElementTabindex}" />

<cw:button submitButton="${submitButton}" cssClass="${cssClass}" 
	name="${empty elementName ? eventName : elementName.concat('_').concat(eventName)}" tabindex="${tabindex}"
	onclick="epmEventSubmit(this, '${eventName}', '${elementName}', '${params }')">
	<spring:message code="${label}" />
	<jsp:doBody />
</cw:button>
