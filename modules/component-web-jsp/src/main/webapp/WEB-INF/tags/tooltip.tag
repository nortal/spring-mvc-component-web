<%@ tag body-content="scriptless" isELIgnored="false" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>

<%@ attribute name="element" required="true" rtexprvalue="true" type="com.nortal.spring.cw.core.web.component.element.FormElement"%>

<c:if test="${element.tooltip.visible}">
	<spring:message code="global.field.tooltip.title" var="tooltipTitle" />
	<a href="javascript:void(0);" class="help-toggle" title="${tooltipTitle}">${tooltipTitle}</a>
	<div class="help" style="display: none; top: 496px; left: 962px; right: auto;">
		<div class="help-content">
			<spring:message code="field.tooltip.close" var="tooltipClose" />
			<p class="close"><a href="javascript:void(0);" title="${tooltipClose}">${tooltipClose}</a></p>
	  		<p>${element.tooltip.text}</p>
		</div>
	</div>
</c:if>