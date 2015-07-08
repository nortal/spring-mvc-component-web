<%@ tag body-content="scriptless" isELIgnored="false" pageEncoding="UTF-8" description="Üldine vormi elemendi tag" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>
<%@ taglib prefix="cw" uri="/WEB-INF/tld/cw.tld"%>

<%@ attribute name="element" required="true" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="tableCell" rtexprvalue="true" type="java.lang.Boolean" description="Kui tõene, liisatakse tabeli lahter koos sildiga, vastasel juhul tabeli lahtrit ei tekitata" %>
<%@ attribute name="showLabel" rtexprvalue="true" type="java.lang.Boolean" %>
<%@ attribute name="inputFragment" required="false" fragment="true" %>
<%@ attribute name="onChange" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="region" required="false" rtexprvalue="true" type="java.lang.String" %>

<c:set var="element" value="${component.elementHolder[element].element}" />
<c:set var="editable" value="${element.editable}" />
<c:set var="tableCell" value="${empty tableCell ? true : tableCell}" /> 
<c:set var="hasError" value="${errorBindingResult.hasFieldErrors(element.fullPath)}" />
<c:set var="showLabel" value="${empty showLabel ? true && element.visible : showLabel && element.visible}" />

<c:if test="${element.visible}">
	<c:choose>
		<c:when test="${element.elementType eq 'LANGUAGE'}">
			<cw:languageInput element="${element}" />
		</c:when>
		<c:when test="${tableCell and element.elementType eq 'BOOLEAN'}">
			<th class="${hasError ? 'error' : ''}">
				<c:if test="${element.mandatory and editable}">
					<span class="req">*</span>
				</c:if>
			</th>
			<td>
				<dl class="options">
					<dd>
						<c:choose>
							<c:when test="${not empty inputFragment}">
								<jsp:invoke fragment="inputFragment"/>
							</c:when>
							<c:otherwise>
								<tag:simpleFormInput editable="${editable}" element="${element}" onChange="${onChange}" region="${region}"/>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${showLabel}">
								<label for="${element.displayId}" id="${element.displayId}_label"><spring:message code="${element.fullLabel}" /></label>
							</c:when>
							<c:otherwise>
								&nbsp;
							</c:otherwise>
						</c:choose>
					</dd>
				</dl>
			</td>
		</c:when>
		<c:when test="${tableCell}">
			<th class="${hasError ? 'error' : ''}">
				<c:choose>
					<c:when test="${showLabel}">
						<label for="${element.displayId}" id="${element.displayId}_label" class="${element.mandatory and editable ? 'req' : ''}"><spring:message code="${element.fullLabel}" /></label>:
					</c:when>
					<c:otherwise>
						&nbsp;
					</c:otherwise>
				</c:choose>
			</th>
			<td class="${editable ? '' :  'data'}">
				<c:choose>
					<c:when test="${not empty inputFragment}">
						<jsp:invoke fragment="inputFragment"/>
					</c:when>
					<c:otherwise>
						<tag:simpleFormInput editable="${editable}" element="${element}" onChange="${onChange}" region="${region}"/>
					</c:otherwise>
				</c:choose>
			</td>
		</c:when>
		<c:otherwise>
			<c:if test="${showLabel}">
				<label for="${element.displayId}" id="${element.displayId}_label" class="${element.mandatory and editable ? 'req' : ''}"><spring:message code="${element.fullLabel}" /></label>
			</c:if>
			<tag:simpleFormInput editable="${editable}" element="${element}" onChange="${onChange}" region="${region}"/>
		</c:otherwise>
	</c:choose>
</c:if>
