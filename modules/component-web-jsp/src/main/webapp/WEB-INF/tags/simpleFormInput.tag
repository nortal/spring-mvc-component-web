<%@ tag body-content="scriptless" isELIgnored="false" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="cw" uri="/WEB-INF/tld/cw.tld"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>

<%@ attribute name="element" required="true" rtexprvalue="true" type="com.nortal.spring.cw.core.web.component.element.FormElement"%>
<%@ attribute name="editable" required="true" rtexprvalue="true" type="java.lang.Boolean"%>
<%@ attribute name="valuePath" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="onChange" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="region" required="false" rtexprvalue="true" type="java.lang.String" %>

<c:set var="hasError"
	value="${errorBindingResult.hasFieldErrors(element.fullPath)}" />
<c:set var="addClass" value="${hasError ? ' error' : ''}" />
<c:set var="valuePath" value="${empty valuePath ? 'value' : valuePath}" />

<c:if test="${ not empty element.onChangeHandler }">
	<c:set var="onChange"
		value="${onChange};sendOnChangeEvent(this, '${element.displayId}', '${component.id}');" />
</c:if>

<%-- Work in progress --%>
<%-- <c:choose> --%>
<%-- 	<c:when test="${not empty region and not empty onChange}"> --%>
<%-- 		<c:set var="ngChange" value="send(this, '${element.displayId}', '${component.id}');" />	 --%>
<%-- 	</c:when> --%>
<%-- 	<c:otherwise> --%>
<%-- 		<c:if test="${not empty onChange}"> --%>
<%-- 			<c:set var="ngChange" value="sendSubmit(this, '${element.displayId}', '${component.id}', '${region}');" /> --%>
<%-- 		</c:if> --%>
<%-- 	</c:otherwise> --%>
<%-- </c:choose> --%>

<c:choose>
	<c:when test="${editable and element.visible}">
		<c:set var="tabindex" value="${empty model ? '' : model.nextElementTabindex}" />
		
		<c:choose>
			<c:when test="${ not empty element.onClickHandler }">
				<c:set var="onClickMethod"
					value="sendOnClickEvent(this, '${element.displayId}', '${component.id}');" />
			</c:when>
			<c:otherwise>
				<c:set var="onClickMethod" value="" />
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when
				test="${element.elementType ne 'BOOLEAN' and element.elementType ne 'FILE' and element.multiValueElement}">
				<c:choose>
					<c:when
						test="${element.multiValue.elementType eq 'SELECT' or element.multiValue.elementType eq 'MULTISELECT'}">
						<form:select path="${element.fullPath}.${valuePath}"
 							cssClass="${element.cssClassValue}${addClass}"
 							multiple="${element.multiValue.multiSelectAllowed ? 'multiple' : 'single'}" 
 							id="${element.displayId}" onclick="${onClickMethod}" 
 							onchange="${onChange}" data-ng-keypress="${element.displayId}"
 							ng-change="${ngChange}" 
 							data-element-event="${element.displayId}" 
 							data-use-ajax-validation="${element.useAjaxValidation}" 
							data-element-type="${element.elementType.string}" 
							tabindex="${tabindex}">
							<c:forEach items="${element.multiValues}" var="item">
								<c:choose>
									<c:when test="${empty item.key and empty item.value.value}">
										<form:option value="${item.key}" disabled="${item.value.disabled}" cssClass="label"><spring:message code="global.select.choose" /></form:option>
									</c:when>
									<c:when test="${item.value.value eq '--------------------------'}">
										<form:option value="${item.key}" cssClass="separator" disabled="true"><c:out value="${item.value}" escapeXml="true" /></form:option>
									</c:when>
									<c:otherwise>
										<form:option value="${item.key}" disabled="${item.value.disabled}"><c:out value="${item.value}" escapeXml="true" /></form:option>	
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</form:select>
					</c:when>
					<c:when test="${element.multiValue.elementType eq 'RADIO'}">
						<dl class="options">
							<form:radiobuttons path="${element.fullPath}.${valuePath}"
								cssClass="${element.cssClassValue}${addClass}"
								items="${element.multiValues}"
								id="${element.displayId}" element="dd"
								onclick="${onClickMethod}" onchange="${onChange}"
	 							ng-change="${ngChange}" 
								data-element-event="${element.displayId}"
								data-use-ajax-validation="${element.useAjaxValidation}"
								data-element-type="${element.elementType.string}" 
								tabindex="${tabindex}"  />
						</dl>
					</c:when>
					<c:when test="${element.multiValue.elementType eq 'CHECKBOX'}">
						<dl class="options">
							<form:checkboxes path="${element.fullPath}.${valuePath}"
								cssClass="${element.cssClassValue}${addClass}"
								items="${element.multiValues}"
								id="${element.displayId}" element="dd"
								onclick="${onClickMethod}" onchange="${onChange}"
	 							ng-change="${ngChange}" 
								data-element-event="${element.displayId}"
								data-use-ajax-validation="${element.useAjaxValidation}"
								data-element-type="${element.elementType.string}" 
								tabindex="${tabindex}"  />
						</dl>
					</c:when>
				</c:choose>
			</c:when>
			<c:otherwise>
				<c:if test="${element.elementType eq 'STRING'}">
					<c:choose>
						<c:when test="${element.rows > 1}">
							<form:textarea path="${element.fullPath}.${valuePath}"
								maxlength="${element.length}"
								cssClass="${element.cssClassValue}${addClass}"
								rows="${element.rows}" cols="${element.cols}"
								id="${element.displayId}" onchange="${onChange}"
	 							ng-change="${ngChange}" 
								data-element-event="${element.displayId}"
								data-use-ajax-validation="${element.useAjaxValidation}"
								data-element-type="${element.elementType.string}" 
								tabindex="${tabindex}" />
						</c:when>
						<c:otherwise>
							<form:input path="${element.fullPath}.${valuePath}"
								maxlength="${element.length}"
								cssClass="${element.cssClassValue}${addClass}"
								id="${element.displayId}" onchange="${onChange}"
	 							ng-change="${ngChange}" 
								data-element-event="${element.displayId}"
								data-use-ajax-validation="${element.useAjaxValidation}"
								data-element-type="${element.elementType.string}" 
								tabindex="${tabindex}" />
						</c:otherwise>
					</c:choose>
				</c:if>
				<c:if test="${element.elementType eq 'DOUBLE'}">
					<form:input path="${element.fullPath}.${valuePath}"
						cssClass="${element.cssClassValue}${addClass}"
						id="${element.displayId}" onchange="${onChange}"
						ng-change="${ngChange}" 
						data-element-event="${element.displayId}"
						data-min="${element.range.minimum}"
						data-max="${element.range.maximum}"
						data-use-ajax-validation="${element.useAjaxValidation}"
						data-element-type="${element.elementType.string}" 
						tabindex="${tabindex}" />
				</c:if>
				<c:if test="${element.elementType eq 'LONG'}">
					<form:input path="${element.fullPath}.${valuePath}"
						cssClass="${element.cssClassValue}${addClass}"
						id="${element.displayId}" onchange="${onChange}"
						ng-change="${ngChange}" 
						data-element-event="${element.displayId}"
						data-min="${element.range.minimum}"
						data-max="${element.range.maximum}"
						data-use-ajax-validation="${element.useAjaxValidation}"
						data-element-type="${element.elementType.string}" 
						tabindex="${tabindex}" />
				</c:if>
				<c:if test="${element.elementType eq 'INTEGER'}">
					<form:input path="${element.fullPath}.${valuePath}"
						cssClass="${element.cssClassValue}${addClass}"
						id="${element.displayId}" onchange="${onChange}"
						ng-change="${ngChange}" 
						data-element-event="${element.displayId}"
						data-use-ajax-validation="${element.useAjaxValidation}"
						data-element-type="${element.elementType.string}" 
						tabindex="${tabindex}" />
				</c:if>
				<c:if test="${element.elementType eq 'DATETIME'}">
					<cw:dateTime element="${element}"
						path="${element.fullPath}.${valuePath}"
						cssClass="${element.cssClassValue}${addClass}"
						maxlength="16" id="${element.displayId}"
						ng-change="${ngChange}" 
						data-element-event="${element.displayId}"
						data-min="${element.range.minimum}"
						data-max="${element.range.maximum}"
						data-use-ajax-validation="${element.useAjaxValidation}"
						data-element-type="${element.elementType.string}" 
						tabindex="${tabindex}" />
				</c:if>
				<c:if test="${element.elementType eq 'BOOLEAN'}">
					<form:checkbox path="${element.fullPath}.${valuePath}"
						id="${element.displayId}" onchange="${onChange}"
						ng-change="${ngChange}" 
						data-element-event="${element.displayId}"
						data-use-ajax-validation="${element.useAjaxValidation}"
						data-element-type="${element.elementType.string}" 
						tabindex="${tabindex}" 
						onclick="${onClickMethod}" />
				</c:if>
				<c:if test="${element.elementType eq 'SIMPLE_TEXT'}">
					<tag:simpleFormDisplay element="${element}" />
				</c:if>
				<c:if
					test="${element.elementType eq 'FILE' or element.elementType eq 'FILE_COLLECTION'}">
					<c:choose>
						<c:when test="${element.elementType eq 'FILE_COLLECTION'}">
						
							<c:if test="${not empty element.multiValues}">
								<div style="margin-bottom: 10px; width: 400px;">
									<c:forEach items="${element.multiValues}" var="entry" varStatus="fileIdx">
										<div>
											<tag:eventLink label="${element.removeLabel}"
												elementName="${element.displayId}" eventName="removeFile"
												params="${entry.key}" /> |
											<tag:simpleFormDisplay element="${entry.value.value.element}" />
										</div>
									</c:forEach>
								</div>
							</c:if>
						</c:when>
						<c:when test="${element.elementType ne 'FILE_COLLECTION' and element.canDownload}">
							<tag:simpleFormDisplay element="${element}" showError="false" />
						</c:when>
					</c:choose>
					<input type="file" name="${element.fullPath}.multipartFile"
						onchange="${onChange}" data-element-event="${element.displayId}"
						data-use-ajax-validation="${element.useAjaxValidation}" 
						data-element-type="${element.elementType.string}" 
						tabindex="${tabindex}" />
				</c:if>
			</c:otherwise>
		</c:choose>

		<tag:tooltip element="${element}" />
		<form:errors path="${element.fullPath}" element="p" cssClass="error" id="${element.displayId}_error" htmlEscape="true" />

	</c:when>
	<c:when
		test="${not element.visible and not empty element.id}">
		<form:hidden path="${element.fullPath}.${valuePath}"
			id="${element.displayId}" />
	</c:when>
	<c:otherwise>
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
				<tag:simpleFormDisplay element="${element}" />
			</c:when>
			<c:otherwise>
				<tag:simpleFormDisplay element="${element}" />
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>