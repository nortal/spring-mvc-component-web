<%@ tag body-content="scriptless" isELIgnored="false" pageEncoding="UTF-8" description="Üldised lehe aktsionid. Defineeritakse ControllerComponent juures" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="cw" uri="/WEB-INF/tld/cw.tld"%>>

<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>

<%@ attribute name="actions" required="false" rtexprvalue="true" type="com.nortal.spring.cw.core.web.component.page.PageActions" %>


<c:if test="${empty actions and not empty compItem}">
	<c:set var="actions" value="${compItem.pageActions}" />
</c:if>

<c:if test="${empty actions}">
	<c:set var="actions" value="${controllerComponent.pageActions}" />
</c:if>

<!-- Optional values to specify which buttons to show -->
<%@ attribute name="showMainButtons" required="false" rtexprvalue="true" type="java.lang.Boolean" %>
<%@ attribute name="showSecondaryButtons" required="false" rtexprvalue="true" type="java.lang.Boolean" %>

<c:set var="showMainButtons" value="${not empty actions.mainButtons && (empty showMainButtons || not empty showMainButtons && showMainButtons)}" />
<c:set var="showSecondaryButtons" value="${not empty actions.secondaryButtons && (empty showSecondaryButtons || not empty showSecondaryButtons && showSecondaryButtons)}" />

<c:if test="${actions.parent.visible and actions.visible and (showMainButtons or showSecondaryButtons)}">
	<div class="action clear">
	<c:if test="${showMainButtons}">
		<p class="main">
			<c:forEach items="${actions.mainButtons}" var="button">
				<c:if test="${button.visible }">
					<tag:eventButton eventName="${button.eventName}" label="${button.fullLabel}" elementName="${empty button.parent ? button.displayId : button.parent.displayId}" submitButton="false" cssClass="${button.buttonCssClass.value}" />
				</c:if>
			</c:forEach>
		</p>
	</c:if>
	
	<c:if test="${showSecondaryButtons}">
		<c:forEach items="${actions.secondaryButtons}" var="link">
			<c:if test="${link.visible }">
				&nbsp;<tag:eventLink eventName="${link.eventName}" elementName="${empty link.parent ? link.displayId : link.parent.displayId}" label="${link.fullLabel}" />
			</c:if>
		</c:forEach>
	</c:if>	
	</div>
</c:if>


