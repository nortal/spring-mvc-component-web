<%@ tag body-content="scriptless" isELIgnored="false" pageEncoding="UTF-8" description="Erinevate komponendi poolt defineeritud modaalakende raamid" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="cw" uri="/WEB-INF/tld/cw.tld"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>

<%@ attribute name="sourceComponent" required="false" rtexprvalue="true" type="com.nortal.spring.cw.core.web.component.composite.complex.ComplexComponent" %>

<c:if test="${empty sourceComponent and not empty compItem and compItem.modalComps}">
	<c:set var="sourceComponent" value="${compItem}" scope="request" />
</c:if>

<c:if test="${empty sourceComponent}">
	<c:set var="sourceComponent" value="${controllerComponent}" scope="request" />	
</c:if>

<!-- Modal aknad -->
<div id="modalwrap">
	<c:if test="${not empty sourceComponent.modalComps}">
		<c:if test="${not empty sourceComponent.openedModalDialog }">
			<c:set var="openModal" value="${sourceComponent.openedModalDialog}" />
			<script>
			$(document).ready(function() {
				showModal(escapeId("${openModal.displayId}"), "${not empty openModal.properties.sizeStyleClass ? openModal.properties.sizeStyleClass : '' }");
			});
			</script>
		</c:if>
	
		<c:forEach items="${sourceComponent.modalComps}" var="modal">
			<c:if test="${modal.type eq 'DIALOG'}">
				<div id="${modal.displayId}" class="modaldialog">
					<h1><spring:message code="${modal.caption.fullLabel}" /></h1>
					<tag:componentMessages sourceComponent="${modal}" />
					<p><spring:message code="${modal.content}" /></p>
					<div class="action clear">
						<c:if test="${not empty modal.modalButtons.buttons}">
							<p class="main">
								<c:forEach var="button" items="${modal.modalButtons.buttons}">
									<c:if test="${button.element.visible}">
										<c:set var="tabindex" value="${empty model ? '' : model.nextElementTabindex}" />
										<cw:button cssClass="${button.element.buttonCssClass.value}" onclick="modalEventSubmit(this, '${button.element.eventDisplayId}', '${button.element.eventName}');hideModal();return false;" tabindex="${tabindex}">
											<spring:message code="${button.element.label}"/>
										</cw:button>
									</c:if>
								</c:forEach>
							</p>
						</c:if>
						<c:if test="${modal.properties.showCancelLink}">
							<p class="alt"><a href="javascript:void(0);" class="cancel" onclick="return modalDialogCancel(this, '${modal.displayId}', '${modal.id}');hideModal();"><spring:message code="modal.dialog.cancel" /></a></p>
						</c:if>
					</div> 
				</div>	
			</c:if>
			<c:if test="${modal.type eq 'POPUP'}">
				<div id="${modal.displayId}" class="modalpopup">
					<div class="modalpopup-header clear">
						<h1><spring:message code="${modal.caption.fullLabel}" /></h1>
						<p class="close"><a href="javascript:void(0);" onclick="return modalDialogClose(this, '${modal.displayId}', '${modal.id}'); hideModal();"><spring:message code="modal.dialog.close" /></a></p>
					</div>
					<div class="modalpopup-content">
						<tag:componentMessages sourceComponent="${modal}" />
						${modal.content}
					</div>
					<div class="modalpopup-footer action clear">
						<c:if test="${not empty modal.modalButtons.buttons}">
							<p class="main">
								<c:forEach var="button" items="${modal.modalButtons.buttons}">
									<c:if test="${button.element.visible}">
										<c:set var="tabindex" value="${empty model ? '' : model.nextElementTabindex}" />
										<cw:button cssClass="${button.element.buttonCssClass.value}" onclick="modalEventSubmit(this, '${button.element.eventDisplayId}', '${button.element.eventName}');hideModal();return false;" tabindex="${tabindex}">
											<spring:message code="${button.element.label}" />
										</cw:button>
									</c:if>
								</c:forEach>
							</p>
						</c:if>
						<c:if test="${modal.properties.showCancelLink}">
							<p class="alt"><a href="javascript:void(0);" class="cancel" onclick="return modalDialogCancel(this, '${modal.displayId}', '${modal.id}'); hideModal();return false;"><spring:message code="modal.dialog.cancel" /></a></p>
						</c:if>
					</div>
				</div>	
			</c:if>
	
		</c:forEach>
	</c:if>
</div>
	
<c:remove var="composite"/>

