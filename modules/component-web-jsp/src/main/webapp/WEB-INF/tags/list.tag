<%@ tag body-content="scriptless" isELIgnored="false" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@ taglib prefix="cw" uri="/WEB-INF/tld/cw.tld"%>
<%@ taglib prefix="cwf" uri="/WEB-INF/tld/cw-function.tld"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>

<%@ attribute name="list" required="true" rtexprvalue="true" type="java.lang.String"%>

<!-- List fragments -->
<%@ attribute name="headerFragment" required="false" fragment="true" %>
<%@ attribute name="filterFragment" required="false" fragment="true" %>
<%@ attribute name="rowFragment" required="false" fragment="true" %>
<%@ attribute name="footerFragment" required="false" fragment="true" description="Jaluse rida. Kuvatakse uue rea olemasolul selle alla" %>

<c:set var="component" value="${empty compItem ? controllerComponent.components[list] : compItem.components[list]}"
	scope="request" />
<c:set var="order" value="${component.pagedListHolder.order}" />

<c:set var="editable" value="${component.editable}" scope="page"/>
<c:set var="hasActionButtons" value="${component.hasActionButtons}" scope="request"/>

<c:if test="${component.visible}">

<tag:componentCaption sourceComponent="${component}" heading="h2" />

<jsp:doBody />

<div data-ng-controller="DataListController" data-ng-init="init(componentId='${list}', displayId='${component.displayId}')">
	<c:if test="${component.properties.showMarkAll }">
		<p class="meta">
			<strong><spring:message code="global.list.mark" />:</strong>
				<a href="javascript: void(0);" class="check-all"><spring:message code="global.list.check-all" /></a> <span>|</span> 
				<a href="javascript: void(0);" class="check-none"><spring:message code="global.list.check-none" /></a>
		</p>
	</c:if>
	<table class="${component.properties.cssClassValue}" id="${component.displayId}" data-bind-html-unsafe="listData">
		<thead>
			<tr>
				<c:choose>
					<c:when test="${not empty headerFragment}">
						<jsp:invoke fragment="headerFragment"/>
					</c:when>
					<c:otherwise>
						<c:if test="${component.properties.showRowNumber}">
							<th><spring:message code="global.list.rownumber" /></th>
						</c:if>
						<c:forEach items="${component.listHeader}" var="cell" varStatus="cellIdx">
							<c:if test="${cell.element.visible}">
								<c:set var="customCellNr" value="${component.displayId}rowHCell${cellIdx.index + 1}Fragment" />
								<c:choose>
									<c:when test="${not empty requestScope['__FRAGMENT_REG__'][customCellNr]}">
										<c:set var="cell" value="${cell}" scope="request" />
										<cw:useFragment name="${customCellNr}" />
									</c:when>
									<c:when test="${cell.sortable}">
									<th class="sort ${order.orderedField eq cell.element.id ? (order.desc ? 'descending' : 'ascending') : ''}"><a
										href="javascript: void(0)" data-ng-click="sort('${cell.element.id}', ${!order.desc})">
											<spring:message code="${cell.element.fullLabel}" /></a> <tag:tooltip element="${cell.element }" />
										</th>
									</c:when>
									<c:otherwise>
										<th><spring:message code="${cell.element.fullLabel}" />
										<tag:tooltip element="${cell.element}" /></th>
									</c:otherwise>
								</c:choose>
							</c:if>
						</c:forEach>
						<c:if test="${component.showActionColumn}">
							<th>&nbsp;</th>
						</c:if>
					</c:otherwise>
				</c:choose>
			</tr>
			<c:if test="${component.showFilter}">
				<tr class="filter"> 
					<c:choose>
						<c:when test="${not empty filterFragment}">
							<jsp:invoke fragment="filterFragment" />
						</c:when>
						<c:otherwise>
							<c:if test="${component.properties.showRowNumber}">
								<td>&nbsp;</td>
							</c:if>
							<c:forEach items="${component.listHeader}" var="cell" varStatus="cellIdx">
								<td><c:choose>
										<c:when test="${cell.filterable}">
											<c:choose>
												<c:when test="${cell.allowFilterRange}">
													<tag:simpleFormInput editable="true" element="${cell.between.left.element}" /><br />
													<tag:simpleFormInput editable="true" element="${cell.between.right.element}" />
												</c:when>
												<c:otherwise>
													<tag:simpleFormInput editable="true" element="${cell.element}" />
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise>
										&nbsp;
									</c:otherwise>
									</c:choose></td>
							</c:forEach>
							<td class="action">
								<cw:button cssClass="button alt2" data-ng-click="filter()" submitButton="false"><spring:message code="global.list.button.filter" /></cw:button>
							</td>
						</c:otherwise>
					</c:choose>
				</tr>
			</c:if>
		</thead>
		<tbody>	
			<c:forEach items="${component.listRow}" var="row" varStatus="rowIdx">
				<c:set var="row" value="${row}" scope="request"/>
				<c:set var="rowIdx" value="${rowIdx}" scope="request"/>
				<c:if test="${not row.deleted and row.visible }">
					<tr class="${row.editable ? 'form' : '' }" id="${row.displayId}">
						<c:choose>
							<c:when test="${not empty rowFragment}">
								<jsp:invoke fragment="rowFragment"/>
							</c:when>
							<c:otherwise>
								<c:if test="${component.properties.showRowNumber}">
									<td>${(rowIdx.index + 1) + (component.properties.pageSize * component.pagedListHolder.page)}.</td>
								</c:if>
								<c:forEach items="${row.rowCell}" var="cell" varStatus="cellIdx">
									<c:if test="${cell.element.visible}">
										<c:set var="customCellNr" value="${component.displayId}rowBCell${cellIdx.index + 1}Fragment" />
										<c:choose>
											<c:when test="${not empty requestScope['__FRAGMENT_REG__'][customCellNr]}">
												<c:set var="cell" value="${cell}" scope="request" />
												<cw:useFragment name="${customCellNr}" />
											</c:when>
											<c:otherwise>
											<td class="${cell.element.cssClass.value} ${cell.element.elementType eq 'DOUBLE' or cell.element.elementType eq 'LONG' ? cell.editable ? 'input right' : 'right' : ''}">
												<tag:simpleFormInput editable="${row.isElementEditable(cell.element)}" element="${cell.element}" />
											</td>
											</c:otherwise>
										</c:choose>
									</c:if>
								</c:forEach>
								<c:if test="${component.showActionColumn}">
									<td class="action">
										<c:set var="buttonCount" value="0" />
										<c:forEach items="${row.listRowButtons.elementHolder}" var="buttonMapEntry" varStatus="status">
											<c:set var="actionButton" value="${buttonMapEntry.value.element}" />
											<c:if test="${actionButton.visible}">
												<c:set var="buttonCount" value="${buttonCount + 1}" />
												<c:if test="${not status.first and buttonCount > 1}">
													|
												</c:if>
												<c:choose>
													<c:when test="${buttonMapEntry.key eq 'listChangeButton'}">
														<tag:eventLink eventName="${actionButton.eventName }" 
															elementName="${row.displayId}" 
															jsFunction="editRow" 
															label="${actionButton.fullLabel}" 
															params="${list}" 
															cssClass="${actionButton.cssClass.value}" 
															angularCall="true" 
															hasConfirmation="${actionButton.hasConfirmation}"
															sendFormDataOnEvent="${actionButton.sendFormDataOnEvent}" />
													</c:when>
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
													<c:when test="${buttonMapEntry.key eq 'listChangeCancelButton'}">
														<tag:eventLink eventName="${actionButton.eventName}" 
															label="${actionButton.fullLabel}" 
															elementName="${row.displayId}" 
															cssClass="${actionButton.cssClass.value}" 
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
									</td>
								</c:if>
							</c:otherwise>
						</c:choose>
					</tr>
				</c:if>
			</c:forEach>
		</tbody>
		<tfoot>
			<c:if test="${component.properties.allowAdd}">
				<tr class="form">
					<c:if test="${component.properties.showRowNumber}">
						<td>&nbsp;</td>
					</c:if>
					
					<c:set var="newRow" value="${component.newRow}" />
					
					<c:forEach items="${newRow.listRow}" var="row" varStatus="rowIdx">
						<c:forEach items="${row.rowCell}" var="cell" varStatus="cellIdx">
							<td class="${cell.element.elementType eq 'DOUBLE' or cell.element.elementType eq 'LONG' ? editable ? 'input right' : 'right' : ''}">
								<tag:simpleFormInput editable="${row.isElementEditable(cell.element)}" element="${cell.element}" />
							</td>
						</c:forEach>
						<td>
							<cw:button submitButton="false" cssClass="button alt2" data-ng-click="addRow($event, '${row.displayId}')">
								<spring:message code="global.list.add" />
							</cw:button> 
						</td>
					</c:forEach>
				</tr>
			</c:if>
			<c:choose>
				<c:when test="${not empty footerFragment}">
					<jsp:invoke fragment="footerFragment"/>
				</c:when>
				<c:when test="${not component.showFilter and not component.properties.allowAdd and not component.hasData}">
					<tr>	
						<td colspan="${component.cellCount + (component.showActionColumn ? 1 : 0)}">
							<spring:message code="global.list.label.no-data" />
						</td>
					</tr>
				</c:when>
			</c:choose>
		</tfoot>
	</table>
	
	<div class="pager clear" id="${component.displayId}.pagination" data-bind-html-unsafe="listPaginationData">
		<c:if test="${component.properties.allowPagination and (component.pagedListHolder.pageCount > 1 or component.pagedListHolder.showAll)}">
			<ul>
				<c:if test="${component.pagedListHolder.pageCount > 1}">
					<c:choose>
						<c:when test="${component.pagedListHolder.firstPage}">
							<li class="first"><span><spring:message code="global.list.link.first" /></span></li>
							<li class="prev"><span><spring:message code="global.list.link.previous" /></span></li>
						</c:when>
						<c:otherwise>
							<li class="first"><a href="javascript: void(0)" data-ng-click="pagination(1)"><spring:message
										code="global.list.link.first" /></a></li>
							<li class="prev"><a href="javascript: void(0)" data-ng-click="pagination(${component.pagedListHolder.page})"><spring:message
										code="global.list.link.previous" /></a></li>
						</c:otherwise>
					</c:choose>
					<c:if test="${component.pagedListHolder.firstLinkedPage > 0}">
						<li><a href="javascript: void(0)" data-ng-click="pagination(1)">1</a></li>
					</c:if>
				
					<c:if test="${component.pagedListHolder.firstLinkedPage > 1}">
						<li><span>...</span></li>
					</c:if>
					<c:forEach begin="${component.pagedListHolder.firstLinkedPage}" end="${component.pagedListHolder.lastLinkedPage}"
						var="i">
						<c:choose>
							<c:when test="${component.pagedListHolder.page == i}">
								<li class="active"><strong>${i+1}</strong>
							</c:when>
							<c:otherwise>
								<li><a href="javascript: void(0)" data-ng-click="pagination(${i+1})">${i+1}</a></li>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					<c:if test="${component.pagedListHolder.lastLinkedPage < component.pagedListHolder.pageCount - 2}">
						<li><span>...</span></li>
					</c:if>
					<c:if test="${component.pagedListHolder.lastLinkedPage < component.pagedListHolder.pageCount - 1}">
						<li><a href="javascript: void(0)" data-ng-click="pagination(${component.pagedListHolder.pageCount})">${component.pagedListHolder.pageCount}</a></li>
					</c:if>
					<c:choose>
						<c:when test="${component.pagedListHolder.lastPage}">
							<li class="next"><span><spring:message code="global.list.link.next" /></span></li>
							<li class="last"><span><spring:message code="global.list.link.last" /></span></li>
						</c:when>
						<c:otherwise>
							<li class="next"><a href="javascript: void(0)" data-ng-click="pagination(${component.pagedListHolder.page + 2})"><spring:message
										code="global.list.link.next" /></a></li>
							<li class="last"><a href="javascript: void(0)" data-ng-click="pagination(${component.pagedListHolder.pageCount})"><spring:message
										code="global.list.link.last" /></a></li>
						</c:otherwise>
					</c:choose>
				</c:if>
			</ul>
			
			<c:if test="${component.pagedListHolder.size > 0}">
				<p>
					<spring:message code="global.list.showing" />
					: ${component.pagedListHolder.firstElementOnPage + 1}-${component.pagedListHolder.lastElementOnPage + 1} <span>|</span>
					<spring:message code="global.list.total" />
					: ${component.pagedListHolder.size} <span>|</span>
					<c:choose>
						<c:when test="${component.pagedListHolder.showAll}">
							<a href="javascript: void(0)" data-ng-click="showAll(false)"><spring:message
									code="global.list.link.hide-all" /></a>
						</c:when>
						<c:otherwise>
							<a href="javascript: void(0)" data-ng-click="showAll(true)"><spring:message
									code="global.list.link.show-all" /></a>
						</c:otherwise>
					</c:choose>
			</c:if>
		</c:if>
	</div>
</div>
</c:if>
<c:remove var="component" />

