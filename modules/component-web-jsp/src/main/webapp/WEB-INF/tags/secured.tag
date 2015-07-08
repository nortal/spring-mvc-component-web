<%@ tag body-content="scriptless" isELIgnored="false" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>

<%@ tag import="java.util.List" %>
<%@ tag import="java.util.ArrayList" %>

<%@ attribute name="privilege" required="true" rtexprvalue="true" type="java.lang.String" %>

<jsp:useBean id="userSecurityUtil" class="com.nortal.spring.cw.core.security.UserSecurityUtil" />

<c:set var="privilege" value="${fn:split(privilege, ',')}" />

<c:set var="hasPrivilege" value="false" />
<c:forEach items="${privilege}" var="priv">
	<c:if test="${userSecurityUtil.hasAnyPrivilege(priv)}">
		<c:set var="hasPrivilege" value="true" />
	</c:if>
</c:forEach>

<c:if test="${hasPrivilege}">
	<jsp:doBody/>
</c:if>