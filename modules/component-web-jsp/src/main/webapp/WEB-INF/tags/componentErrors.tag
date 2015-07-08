<%@ tag body-content="scriptless" isELIgnored="false" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>

<%@ attribute name="componentName" required="true" rtexprvalue="true" type="java.lang.String" %>

<spring:hasBindErrors name="${model.modelCompName}">
	<c:forEach items="${errors.globalErrors}" var="error"> 
		<c:if test="${error.objectName eq componentName}">
			<div class="message msg-error">
				<h2><spring:message code="${error.code}" arguments="${error.arguments}" /></h2>
			</div>
		</c:if>
	</c:forEach>
</spring:hasBindErrors>
