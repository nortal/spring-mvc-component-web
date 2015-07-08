<%@ tag body-content="scriptless" isELIgnored="false" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="cw" uri="/WEB-INF/tld/cw.tld"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>

<%@ attribute name="stepHolderComponent" required="false" rtexprvalue="true" type="com.nortal.spring.cw.core.web.component.step.StepHolderComplexComponent" %>

<c:choose>
	<c:when test="${not empty stepHolderComponent}">
		<c:set var="hasStepComp" value="true" scope="request" />
	</c:when>
	<c:when test="${not empty controllerComponent.stepComplexComponent}">
		<c:set var="stepHolderComponent" value="${controllerComponent.stepComplexComponent.activeStep}" scope="request" />
		<c:set var="hasStepComp" value="true" scope="request" />
	</c:when>
</c:choose>

<c:if test="${hasStepComp and stepHolderComponent.parent.visible}">
	<tag:pageActions actions="${stepHolderComponent.pageActions}" />
</c:if>

<c:remove var="hasStepComp"/>