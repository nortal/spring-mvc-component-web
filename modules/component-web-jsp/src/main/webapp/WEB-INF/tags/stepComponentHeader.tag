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

<c:set var="hasStepComp" value="${not empty stepHolderComponent}" scope="request" />

<c:if test="${not empty stepHolderComponent and stepHolderComponent.parent.stepCount > 1 and stepHolderComponent.parent.visible}">

	<ol class="steps clear">
		<c:set var="stepNr" value="0" />
		<c:forEach items="${stepHolderComponent.parent.components}" var="step" varStatus="status">
			<c:if test="${step.value.visible}">
				<c:if test="${stepHolderComponent.parent.showStepNumbers}">
					<c:set var="stepNr" value="${stepNr + 1}" />
					<c:set var="stepNrLabel" value="${stepNr}. " />
				</c:if>
				
				<c:choose>
					<c:when test="${not empty step.value.caption }">
						<spring:message code="${step.value.caption.label}" var="stepTitle" />
					</c:when>
					<c:otherwise>
						<spring:message code="${step.value.label}" var="stepTitle" />
					</c:otherwise>
				</c:choose>

				<li class="${status.index eq 0 ? 'first ' : ''}${stepHolderComponent.parent.activeStepNr eq status.index +  1 ? 'active' : ''}">
					<c:choose>
						<c:when test="${stepHolderComponent.parent.activeStepNr gt status.index + 1}">
							<tag:eventLink eventName="goToLastStep" params="${status.index + 1}" elementName="${stepHolderComponent.displayId}" sendFormDataOnEvent="false">
								<span><c:out value="${stepNrLabel}" />${stepTitle}</span>
							</tag:eventLink>
						</c:when>
						<c:otherwise>
							<strong>
								<span><c:out value="${stepNrLabel}" />${stepTitle}</span>
							</strong>
						</c:otherwise>
					</c:choose>
				</li>
			</c:if>
		</c:forEach>
	</ol>
</c:if>

<c:remove var="hasStepComp"/>