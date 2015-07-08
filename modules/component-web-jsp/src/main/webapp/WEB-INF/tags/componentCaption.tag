<%@ tag body-content="scriptless" isELIgnored="false" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="cw" uri="/WEB-INF/tld/cw.tld"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>

<%@ attribute name="sourceComponent" required="true" rtexprvalue="true" type="com.nortal.spring.cw.core.web.component.composite.Component" %>
<%@ attribute name="heading" required="true" rtexprvalue="true" type="java.lang.String" description="Pealkirja vaikeväärtus. Kui elemendi juures on täpsustatud siis kasutatakse elemendis määratud taset" %>

<c:if test="${not empty sourceComponent.caption and sourceComponent.caption.visible}">

	<c:if test="${sourceComponent.caption.level >0 }">
		<c:set var="heading" value="h${sourceComponent.caption.level }" />
	</c:if>

	<spring:message code="${sourceComponent.caption.helpCode}" var="help" />

	<c:choose>
		<c:when test="${help ne sourceComponent.caption.helpCode and sourceComponent.caption.hasSimpleHelpInfoText}">
			<c:set var="help" value="${help} <br /> ${sourceComponent.caption.simpleHelpInfoText}" />
		</c:when>
		<c:when test="${sourceComponent.caption.hasSimpleHelpInfoText}">
			<c:set var="help" value="${sourceComponent.caption.simpleHelpInfoText}"></c:set>
		</c:when>
	</c:choose>
	
	<c:choose>
		<c:when test="${heading eq 'h1'}">
			<c:set var="captionWrapperClass" value="title title2 clear" />
		</c:when>
		<%-- if links --%>
		<c:when test="${not empty sourceComponent.caption.elements}">
			<c:set var="captionWrapperClass" value="title form-title clear" />
		</c:when>
		<%-- if helptext exists --%>
		<c:when test="${help ne sourceComponent.caption.helpCode}">
			<c:set var="captionWrapperClass" value="title clear" />
		</c:when>
		<%-- no underline when h3 --%>
		<c:when test="${heading ne 'h3'}">
			<c:set var="captionWrapperClass" value="" />
		</c:when>
	</c:choose>
	<div class="${captionWrapperClass}">
	
		<${heading} class="${sourceComponent.caption.styleClass}"><spring:message code="${sourceComponent.caption.fullLabel}" />
			<jsp:doBody />
		</${heading}>

		<c:if test="${not empty sourceComponent.caption.elements or help ne sourceComponent.caption.helpCode}">
			<p class="action">
				<c:forEach items="${sourceComponent.caption.elements}" var="link">
					<c:if test="${link.value.visible}">
						<tag:eventLink eventName="${link.value.eventName}" label="${link.value.fullLabel}" elementName="${link.value.displayId}" labelArguments="${link.value.labelArgs}" params="${link.value.params}"></tag:eventLink>
					</c:if>
				</c:forEach>
			</p>
			
			<c:if test="${help ne sourceComponent.caption.helpCode}">
				<a href="javascript:void(0);" class="help toggle ${sourceComponent.caption.openHelpInfo ? 'toggle-open' : ''}"><spring:message code="global.caption.help.title" /></a>
			</c:if>
		</c:if>
	</div>
	
	<c:if test="${help ne sourceComponent.caption.helpCode}">
		<div class="toggle-wrap" style="display: ${sourceComponent.caption.openHelpInfo ? 'block' : 'none'};">
			<c:out value="${help}" escapeXml="false" />
		</div>
	</c:if>

<tag:messages />
<tag:componentMessages sourceComponent="${sourceComponent}" />
</c:if>