<%@ tag body-content="scriptless" isELIgnored="false" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>

<%@ attribute name="allowFileUpload" required="false" rtexprvalue="true" type="java.lang.Boolean" %>

<form:form method="POST" modelAttribute="${model.modelCompName}" enctype="${allowFileUpload ?  'multipart/form-data' : ''}">
<jsp:doBody />
</form:form>