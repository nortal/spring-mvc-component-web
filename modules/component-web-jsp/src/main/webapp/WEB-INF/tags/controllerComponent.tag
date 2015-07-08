<%@ tag body-content="scriptless" isELIgnored="false" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="cw" uri="/WEB-INF/tld/cw.tld"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>

<%@ attribute name="controllerComponentAttribute" required="false" rtexprvalue="true" type="com.nortal.spring.cw.core.web.component.composite.ControllerComponent" %>
<%@ attribute name="showCaption" required="false" rtexprvalue="true" type="java.lang.Boolean" %>

<c:set var="errorBindingResultKey" value="org.springframework.validation.BindingResult.${model.modelCompName}" />
<c:set var="errorBindingResult" value="${requestScope[errorBindingResultKey]}" scope="request" />
<c:set var="controllerComponent" value="${empty controllerComponentAttribute ? requestScope[requestScope['org.springframework.web.servlet.tags.form.AbstractFormTag.modelAttribute']] : controllerComponentAttribute}" scope="request" />

<c:set var="subMenu" value="${controllerComponent.menuComponent}" />

<tag:sideMenu subMenu="${controllerComponent.menuComponent}"/>

<div id="${not empty subMenu and not empty subMenu.menuItems ? 'content' : 'menu'}">
	<c:if test="${(empty showCaption or showCaption) and controllerComponent.visible}">
		<tag:componentCaption sourceComponent="${controllerComponent}" heading="h1"/>
	</c:if>
	
	<!-- Üldine sisu -->
	<c:if test="${controllerComponent.visible}">
		<jsp:doBody />
		<tag:modalDialogs />
	</c:if>
	
	<c:remove var="composite"/>
</div>

