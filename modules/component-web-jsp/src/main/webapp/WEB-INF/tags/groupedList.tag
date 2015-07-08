<%@ tag body-content="scriptless" isELIgnored="false" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@ taglib prefix="cw" uri="/WEB-INF/tld/cw.tld"%>
<%@ taglib prefix="cwf" uri="/WEB-INF/tld/cw-function.tld"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>

<%@ attribute name="list" required="true" rtexprvalue="true" type="java.lang.String"%>

<c:set var="component" value="${empty compItem ? controllerComponent.components[list] : compItem.components[list]}" scope="request" />

<c:if test="${component.visible}">

	<tag:componentCaption sourceComponent="${component}" heading="h2" />
	<tag:componentMessages sourceComponent="${component}" />
	
	<jsp:doBody />
	
	<ul class="subject-list" data-ng-controller="DataListController" data-ng-init="init(componentId='${list}', displayId='${component.displayId}')">
		<c:forEach items="${component.groupElements}" var="groupItem">
			<c:set var="row" value="${component.groupedRows[groupItem.displayValue]}" />
			<li>
				<div class="subject-header clear">
					<h3>${groupItem.displayValue}</h3>
					<p class="actions">
						<c:if test="${component.showActionColumn}">
							<c:set var="buttonCount" value="0" />
							<c:forEach items="${row.listRowButtons.elementHolder}" var="buttonMapEntry" varStatus="status">
								<c:set var="actionButton" value="${buttonMapEntry.value.element}" />
								<c:if test="${actionButton.visible}">
									<c:set var="buttonCount" value="${buttonCount + 1}" />
									<c:if test="${not status.first and buttonCount > 1}">
										|
									</c:if>
									<c:choose>
										<c:when test="${buttonMapEntry.key eq 'listDeleteButton'}">
											<tag:eventLink eventName="${actionButton.eventName}" 
												elementName="${row.displayId}" 
												jsFunction="deleteRow" 
												label="${actionButton.fullLabel}" 
												params="${list}" cssClass="${actionButton.cssClass.value}" 
												angularCall="true" 
												hasConfirmation="${actionButton.hasConfirmation}" 
												sendFormDataOnEvent="${actionButton.sendFormDataOnEvent}" />
										</c:when>
										<c:otherwise>
											<tag:eventLink eventName="${actionButton.eventName}" 
												label="${actionButton.fullLabel}" 
												elementName="${actionButton.displayId}" 
												cssClass="${actionButton.cssClass.value}" 
												sendFormDataOnEvent="${actionButton.sendFormDataOnEvent}" />
										</c:otherwise>
									</c:choose>
								</c:if>
							</c:forEach>
							<c:if test="${not hasActionButtons }">
								&nbsp;
							</c:if>
						</c:if>
					</p>
				</div>
				<div class="subject-content">
					<table class="form">
						<tbody>
							<c:forEach items="${row.rowCell}" var="cell">
								<c:set var="element" value="${cell.element}" />
								<c:if test="${element.visible}">
									<tr>
										<th>
											<label for="${element.displayId}" id="${element.displayId}_label"><spring:message code="${element.fullLabel}" /></label>:
										</th>
										<td class="data">
											<tag:simpleFormDisplay element="${element}" />
										</td>
									</tr>
								</c:if>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</li>
		</c:forEach>
	</ul>
</c:if>
<c:remove var="component" />

