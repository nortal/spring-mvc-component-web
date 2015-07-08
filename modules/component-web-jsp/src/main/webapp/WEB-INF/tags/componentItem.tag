<%@ tag body-content="scriptless" isELIgnored="false" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>

<%@ attribute name="item" required="true" rtexprvalue="true" type="java.lang.String" %>

<c:set var="component" value="${empty compItem ? controllerComponent.components[item] : compItem.components[item]}" scope="request" />
<c:if test="${component.visible}">
	<c:set var="componentEditable" value="${component.editable}" scope="request" />
	
	<tag:componentCaption sourceComponent="${component}" heading="h2"/>
	
	<jsp:doBody />
	
	<c:remove var="component"/>
	<c:remove var="componentEditable"/>
</c:if>