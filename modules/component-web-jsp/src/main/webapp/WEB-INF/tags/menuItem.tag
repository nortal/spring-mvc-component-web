<%@ tag body-content="scriptless" isELIgnored="false" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>

<%@ attribute name="item" required="true" rtexprvalue="true" type="com.nortal.spring.cw.core.web.menu.model.MenuItem" %>
<%@ attribute name="autoOpen" required="false" rtexprvalue="true" type="java.lang.Boolean" description="Menüü alampunktid avatakse automaatselt" %>
<%@ attribute name="hideEmptyMenu" required="false" rtexprvalue="true" type="java.lang.Boolean" description="Menüü peidetakse, kui alamelemente menüüs ei ole" %>

<c:if test="${not empty item and (not hideEmptyMenu or (hideEmptyMenu and not empty item.subMenuItems))}">
	<c:choose>
		<c:when test="${item.selected}">
			<c:set var="itemClass" value="active"/>
		</c:when>
		<c:otherwise>
			<c:set var="itemClass" value=""/>
		</c:otherwise>
	</c:choose>
	<li class="${itemClass}">
		<a href="${item.menuUrl}"><spring:message code="${item.parameters.text}"/></a>
		<c:if test="${not empty item.subMenuItems and item.selected or autoOpen}">
			<ul>
				<c:forEach items="${item.subMenuItems}" var="subItem">
					<c:choose>
						<c:when test="${subItem.selected}">
							<c:set var="itemClass" value="active"/>
						</c:when>
						<c:otherwise>
							<c:set var="itemClass" value=""/>
						</c:otherwise>
					</c:choose>
					<li class="${itemClass}"><a href="${subItem.menuUrl}"><spring:message code="${subItem.parameters.text}"/></a>
						<c:if test="${not empty subItem.subMenuItems and subItem.selected}">
							<ul>
								<tag:menuItem item="${subItem.subMenuItems}" autoOpen="${autoOpen}"/>
							</ul>
						</c:if>
					</li>
				</c:forEach>
			</ul>
		</c:if>
	</li>
</c:if>
