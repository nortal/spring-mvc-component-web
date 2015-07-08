<%@ tag body-content="scriptless" isELIgnored="false" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@ taglib prefix="cw" uri="/WEB-INF/tld/cw.tld"%>
<%@ taglib prefix="cwf" uri="/WEB-INF/tld/cw-function.tld"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>

<%@ attribute name="list" required="true" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="emptyMessage" required="false" rtexprvalue="true" type="java.lang.String" description="Kui andmed puuduvad siis kuvatakse vastav teade"%>

<c:set var="component" value="${empty compItem ? controllerComponent.components[list] : compItem.components[list]}" scope="request" />

<c:if test="${component.visible }">
	<c:set var="datas" value="${component.pagedListHolder.allDataRows }" />
	
	<tag:componentCaption sourceComponent="${component}" heading="h2" />
	
	<c:choose>
		<c:when test="${empty datas}">
			<spring:message code="${emptyMessage}"/>
		</c:when>
		<c:otherwise>
			<ul class="${component.properties.cssClassValue}">	
				<c:forEach items="${datas}" var="row" varStatus="status">
					<li>
					<c:forEach items="${row.rowCell}" var="cell" varStatus="cellIdx">
						<tag:simpleFormDisplay element="${cell.element}" />	
					</c:forEach>
					</li>
	 			</c:forEach>
			  </ul>
			  <jsp:doBody />
		</c:otherwise>
	</c:choose>
</c:if>

<c:remove var="component" />