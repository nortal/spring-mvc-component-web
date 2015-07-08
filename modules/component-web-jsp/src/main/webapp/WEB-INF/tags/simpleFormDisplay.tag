<%@ tag body-content="scriptless" isELIgnored="false" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>

<%@ attribute name="element" required="true" rtexprvalue="true" type="com.nortal.spring.cw.core.web.component.element.FormElement"%>
<!-- TODO: margus vaata see koht üle - lisatud sellepärast, et faili element ei väljastaks elemendi kohta topelt veateateid -->
<%@ attribute name="showError" required="false" rtexprvalue="true" type="java.lang.Boolean"%>

<c:choose>
	<c:when test="${element.visible}">
		<c:choose>
			<c:when
				test="${element.elementType eq 'LINK' and not empty element.eventName}">
				<tag:eventLink eventName="${element.eventName}"
					elementName="${element.displayId}" label="${element.fullLabel}" />
			</c:when>
			<c:when
				test="${element.elementType eq 'BUTTON' and not empty element.eventName}">
				<tag:eventButton eventName="${element.eventName}"
					elementName="${element.displayId}" label="${element.fullLabel}"
					cssClass="${element.buttonCssClass.value}" />
			</c:when>
			<c:when test="${not empty element.onClickHandler }">
				<a href="javascript:void(0)"
					onclick="sendOnClickEvent(this, '${element.displayId}', '${component.id}');">
					<c:out value="${element.displayValue}" escapeXml="${element.escapeXml}" />	
				</a>
			</c:when>
			<c:otherwise>
				<c:if test="${element.elementType eq 'DOUBLE'}">
					<c:out value="${element.displayValue}" escapeXml="${element.escapeXml}" />
				</c:if>
				<c:if test="${element.elementType eq 'LONG'}">
					<c:out value="${element.displayValue}" escapeXml="${element.escapeXml}" />
				</c:if>
				<c:if test="${element.elementType eq 'INTEGER'}">
					<c:out value="${element.displayValue}" escapeXml="${element.escapeXml}" />
				</c:if>
				<c:if test="${element.elementType eq 'DATETIME'}">
					<span class="${element.cssClassValue}"><c:out value="${element.displayValue}" escapeXml="${element.escapeXml}" /></span>
				</c:if>
				<c:if test="${element.elementType eq 'SIMPLE_TEXT'}">
					<c:out value="${element.displayValue}" escapeXml="${element.escapeXml}" />
				</c:if>
				<c:if test="${element.elementType eq 'LINK'}">
					<spring:message code="${element.fullLabel }" />
				</c:if>
				<c:if test="${element.elementType eq 'BUTTON'}">
					<spring:message code="${element.fullLabel }" />
				</c:if>
				<c:if test="${element.elementType eq 'BOOLEAN'}">
					<span class="${element.value ? 'checked ' : ''} ${element.cssClassValue}"></span>
				</c:if>
				<c:if test="${element.elementType eq 'FILE'}">
					<c:choose>
						<c:when test="${element.canDownload}">
							<tag:eventLink eventName="${element.downloadLink.element.eventName}" elementName="${element.downloadLink.element.displayId}" label="${element.downloadLink.element.fullLabel}" />
						</c:when>
						<c:otherwise>
							<c:out value="${element.displayValue}" escapeXml="${element.escapeXml}" />
						</c:otherwise>
					</c:choose>
				</c:if>
				<c:if test="${element.elementType eq 'FILE_COLLECTION'}">
					<ul style="margin: 0;">
					<c:forEach items="${element.multiValues.values()}" var="entry" varStatus="fileIdx">
						<li>
							<c:choose>
								<c:when test="${element.allowDownload}">
									<tag:simpleFormDisplay element="${entry.value.element}" />
		 						</c:when>
		 						<c:otherwise>
		 							<c:out value="${entry.value.element.displayValue}" escapeXml="${element.escapeXml}" />
		 						</c:otherwise>
		 					</c:choose>
		 				</li>
					</c:forEach>
					</ul>
				</c:if>
				<c:if test="${element.elementType eq 'STRING_COLLECTION' or element.elementType eq 'LONG_COLLECTION' or element.elementType eq 'INTEGER_COLLECTION'}">
					<c:choose>
						<c:when test="${not empty element.multiValue}">
							<dl class="options">
								<c:forEach items="${element.multiValue.values}" var="val">
									<dd>
										<c:set var="contains" value="false" />
										<c:forEach items="${element.value}" var="check">
											<c:if test="${check eq val.key}">
												<c:set var="contains" value="true" />
											</c:if>
										</c:forEach>
										<c:choose>
											<c:when test="${contains}">
												<span class="checked">${val.value}</span>
											</c:when>
											<c:otherwise>
												<span class="unchecked">${val.value}</span>
											</c:otherwise>
										</c:choose>
									</dd>
								</c:forEach>
							</dl>
						</c:when>
						<c:otherwise>
							<c:out value="${element.displayValue}" escapeXml="${element.escapeXml}" />
						</c:otherwise>
					</c:choose>
				</c:if>
				
				<tag:tooltip element="${element}" />
				<c:if test="${empty showError or showError}">
					<form:errors path="${element.fullPath}" element="p" cssClass="error" id="${element.displayId}_error" />		
				</c:if>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
		&nbsp;
	</c:otherwise>
</c:choose>