<%@ tag body-content="scriptless" isELIgnored="false" pageEncoding="UTF-8" description="Sündmuste link. Sündmus peab olema kontrolleris defineeritud" trimDirectiveWhitespaces="true" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="cw" uri="/WEB-INF/tld/cw.tld"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>

<%@ attribute name="eventName" required="true" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="label" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="labelArguments" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="elementName" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="params" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="cssClass" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="jsFunction" required="false" rtexprvalue="true" type="java.lang.String" description="Vaikeväärtusena kutsutakse välja funktsioon 'epmEventSubmit'. Antud abribuut lubab funktsiooni üle kirjutada, samas meetodi argumendid jäävad samaks: funktsioon(this, eventName, elementName, params) "%>
<%@ attribute name="useControllerPrefix" required="false" rtexprvalue="true" type="java.lang.Boolean" description="True puhul lisatakse label ette kontrolleri prefiks" %>
<%@ attribute name="angularCall" required="false" rtexprvalue="true" type="java.lang.Boolean" description="True puhul kutsutakse välja angularJs kontrolleris defineeritud sündmus"%>
<%@ attribute name="hasConfirmation" required="false" rtexprvalue="true" type="java.lang.Boolean" description="Kas lingiga on seotud kinnitusaken. Kui on siis saadetakse kinnitusakna kuvamiseks täiendav päring"%>
<%@ attribute name="sendFormDataOnEvent" required="false" rtexprvalue="true" type="java.lang.Boolean" description="Kas sündmuse korral saadetakse serverisse ka vormi kõik andmed"%>
<c:if test="${empty jsFunction}">
	<c:set var="jsFunction" value="epmEventSubmit" />
</c:if>
<c:if test="${useControllerPrefix and not empty controllerComponent }">
	<c:set var="label" value="${controllerComponent.label}.${label}" />
</c:if>
<c:choose>
	<c:when test="${angularCall}">
		<a href="javascript: void(0)" data-ng-click="${jsFunction}($event, '${eventName}', '${elementName}', '${params}')" 
		data-send-form-data-on-event="${sendFormDataOnEvent}" 
		data-has-confirmation="${hasConfirmation eq true}" class="${cssClass }">
			<spring:message code="${label}" arguments="${labelArguments}"/><jsp:doBody />
		</a>	
	</c:when>
	<c:otherwise><a href="javascript: void(0)" onclick="${jsFunction}(this, '${eventName}', '${elementName}', '${params}')" 
		data-send-form-data-on-event="${sendFormDataOnEvent}" 
		data-has-confirmation="${hasConfirmation eq true}" class="${cssClass }"><spring:message code="${label}" arguments="${labelArguments}"/><jsp:doBody /></a></c:otherwise>
</c:choose>