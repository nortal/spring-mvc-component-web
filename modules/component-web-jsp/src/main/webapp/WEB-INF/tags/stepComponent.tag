<%@ tag body-content="scriptless" isELIgnored="false" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="cw" uri="/WEB-INF/tld/cw.tld"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>

<%@ attribute name="stepComponent" required="false" rtexprvalue="true" type="com.nortal.spring.cw.core.web.component.step.StepComplexComponent" %>

<c:if test="${empty stepComponent}">
	<c:set var="stepComponent" value="${controllerComponent.stepComplexComponent}" />
</c:if>

<c:set var="activeStep" value="${stepComponent.activeStep}" />

<c:if test="${activeStep.visible}">
	<tag:stepComponentHeader stepHolderComponent="${activeStep}" />

	<c:forEach items="${activeStep.components}" var="componentEntity">
		<tag:componentErrors componentName="${componentEntity.key}" />
		<tag:includeComp name="${componentEntity.key}" sourceComponent="${stepComponent.activeStep}" />
	</c:forEach>

	<tag:stepComponentFooter stepHolderComponent="${activeStep}" />
</c:if>