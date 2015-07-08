<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="cw" uri="/WEB-INF/tld/cw.tld"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>
<tag:html>
<tag:modelForm>
	<tag:controllerComponent>
		<!-- kirjutame üle listi veeru -->
		<tag:list list="fragment-cell-list">
			<!-- kirjutame üle viienda veeru sisu kuvamise loogika -->
			<tag:listCellFragment position="TBODY" cellNr="4">
			Lisatekst + õige väärtus: <strong><tag:simpleFormInput editable="false" element="${cell.element}"/></strong>
			</tag:listCellFragment>
		</tag:list>
		
		<!-- NB: kasutada järgnevat funktsionaalsust ainult erandjuhtudel -->
		<!-- kirjutame üle listi rea -->
		<tag:list list="fragment-row-list">
			<jsp:attribute name="rowFragment">
				<c:if test="${component.properties.showRowNumber}">
					<td>${(rowIdx.index + 1) + (component.properties.pageSize * component.pagedListHolder.page)}.</td>
				</c:if>
				<c:choose>
					<c:when test="${(rowIdx.index + 1) % 2 eq 0}">
						<!-- tekitame enda rea -->
						<td colspan="${fn:length(row.rowCell) + (component.showActionColumn ? 1 : 0)}">
							tühi rida: ${(rowIdx.index + 1) + (component.properties.pageSize * component.pagedListHolder.page)}
						</td>
					</c:when>
					<c:otherwise>
						<!-- kasutame standardfunktsionaalsust -->
						<c:forEach items="${row.rowCell}" var="cell" varStatus="cellIdx">
							<c:set var="customCellNr" value="${component.displayId}rowBCell${cellIdx.index + 1}Fragment" />
							<c:choose>
								<c:when test="${not empty requestScope['__FRAGMENT_REG__'][customCellNr]}">
									<c:set var="cell" value="${cell}" scope="request" />
									<cw:useFragment name="${customCellNr}" />
								</c:when>
								<c:otherwise>
									<td class="${cell.element.cssClass.value} ${cell.element.elementType eq 'DOUBLE' or cell.element.elementType eq 'LONG' ? cell.element.editable ? 'input right' : 'right' : ''}">
										<tag:simpleFormInput editable="${cell.element.editable}" element="${cell.element}" />
									</td>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:if test="${component.showActionColumn}">
							<td class="action">
							<c:forEach items="${row.listRowButtons.elementHolder}" var="buttonMapEntry" varStatus="status">
								<c:if test="${buttonMapEntry.value.element.visible}">
									<c:choose>
										<c:when test="${buttonMapEntry.key eq 'listChangeButton'}">
											<tag:eventLink eventName="${buttonMapEntry.value.element.eventName }" elementName="${row.displayId}" jsFunction="editRow" label="${buttonMapEntry.value.element.fullLabel}" params="${list}" cssClass="${buttonMapEntry.value.element.cssClass.value}" angularCall="true" hasConfirmation="${buttonMapEntry.value.element.hasConfirmation}" />
										</c:when>
										<c:when test="${buttonMapEntry.key eq 'listDeleteButton'}">
											<tag:eventLink eventName="${buttonMapEntry.value.element.eventName}" elementName="${row.displayId}" jsFunction="deleteRow" label="${buttonMapEntry.value.element.fullLabel}" params="${list}" cssClass="${buttonMapEntry.value.element.cssClass.value}" angularCall="true" hasConfirmation="${buttonMapEntry.value.element.hasConfirmation}" />
										</c:when>
										<c:otherwise>
											<tag:eventLink eventName="${buttonMapEntry.value.element.eventName}" label="${buttonMapEntry.value.element.fullLabel}" elementName="${buttonMapEntry.value.element.displayId}" cssClass="${buttonMapEntry.value.element.cssClass.value}" />
										</c:otherwise>
									</c:choose>
										<c:if test="${not status.last }">
											|
										</c:if>
								</c:if>
							</c:forEach>
							<c:if test="${not hasActionButtons }">
								&nbsp;
							</c:if>
							</td>
						</c:if>
					</c:otherwise>
				</c:choose>
				
			</jsp:attribute>
		</tag:list>
		
	</tag:controllerComponent>
</tag:modelForm>

</tag:html>
