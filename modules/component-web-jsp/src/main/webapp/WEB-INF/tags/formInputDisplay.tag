<%@ tag body-content="scriptless" isELIgnored="false" pageEncoding="UTF-8" description="Üldine vormi elemendi väärtuse kuvamise tag" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>
<%@ taglib prefix="cw" uri="/WEB-INF/tld/cw.tld"%>

<%@ attribute name="element" required="true" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="tableCell" rtexprvalue="true" type="java.lang.Boolean" description="Kui tõene, liisatakse tabeli lahter koos sildiga, vastasel juhul tabeli lahtrit ei tekitata" %>
<%@ attribute name="showLabel" rtexprvalue="true" type="java.lang.Boolean" %>
<%@ attribute name="inputFragment" required="false" fragment="true" %>

<c:set var="element" value="${component.elements[element]}" />
<c:set var="tableCell" value="${empty tableCell ? true : tableCell}" /> 
<c:set var="hasError" value="${errorBindingResult.hasFieldErrors(element.fullPath)}" />
<c:set var="showLabel" value="${empty showLabel ? true && element.visible : showLabel && element.visible}" />

<c:if test="${element.visible}">
	<c:choose>
		<c:when test="${element.elementType eq 'LANGUAGE'}">
			<tag:simpleFormDisplay element="${element}" />
		</c:when>
		<c:when test="${tableCell and element.elementType eq 'BOOLEAN'}">
			<c:if test="${element.value}">
				<th class="${hasError ? 'error' : ''}"></th>
				<td>
					<dl class="options">
						<dd>
							<c:choose>
								<c:when test="${not empty inputFragment}">
									<jsp:invoke fragment="inputFragment"/>
								</c:when>
								<c:otherwise>
									<tag:simpleFormDisplay element="${element}" />
									<c:choose>
										<c:when test="${showLabel}">
											<label for="${element.displayId}" id="${element.displayId}_label"><spring:message code="${element.fullLabel}" /></label>
										</c:when>
										<c:otherwise>
											&nbsp;
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
						</dd>
					</dl>
				</td>
			</c:if>
		</c:when>
		<c:when test="${tableCell}">
			<th class="${hasError ? 'error' : ''}">
				<c:choose>
					<c:when test="${showLabel}">
						<label for="${element.displayId}" id="${element.displayId}_label"><spring:message code="${element.fullLabel}" /></label>:
					</c:when>
					<c:otherwise>
						&nbsp;
					</c:otherwise>
				</c:choose>
			</th>
			<td class="${element.elementType eq 'SIMPLE_TEXT' ? element.cssClassValue : 'data'}">
				<c:choose>
					<c:when test="${not empty inputFragment}">
						<jsp:invoke fragment="inputFragment"/>
					</c:when>
					<c:otherwise>
						<tag:simpleFormDisplay element="${element}" />
					</c:otherwise>
				</c:choose>
				
			</td>
		</c:when>
		<c:otherwise>
			<c:if test="${showLabel}">
				<label for="${element.displayId}" id="${element.displayId}_label"><spring:message code="${element.fullLabel}"  /></label>
			</c:if>
			<tag:simpleFormDisplay element="${element}" />
		</c:otherwise>
	</c:choose>
</c:if>
