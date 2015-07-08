<%@ tag body-content="scriptless" isELIgnored="false" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>

<%@ attribute name="regionId" required="true" rtexprvalue="true" type="java.lang.String" %>

<div id="regionId" data-ng-controller="UpdateRegionController" data-ng-init="init(componentId='${component}', displayId='${component.displayId}')">
	<jsp:doBody/>
</div>
