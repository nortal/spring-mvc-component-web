<%@ tag body-content="scriptless" isELIgnored="false" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>

<%@ attribute name="subMenu" required="true" rtexprvalue="true" type="com.nortal.spring.cw.core.web.menu.MenuComponent" %>

<c:if test="${not empty subMenu and not empty subMenu.menuItems}">
	<div id="sidebar">
		<ul id="subnav">
			<c:forEach items="${subMenu.menuItems}" var="item">
				<tag:menuItem item="${item}"/>
			</c:forEach>
		</ul>
	</div>
</c:if>

