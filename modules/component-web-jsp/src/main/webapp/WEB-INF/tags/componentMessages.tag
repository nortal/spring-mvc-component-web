<%@ tag body-content="scriptless" isELIgnored="false" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>

<%@ attribute name="sourceComponent" required="true" rtexprvalue="true" type="com.nortal.spring.cw.core.web.component.composite.Component" %>

<tag:componentErrors componentName="${sourceComponent.id}" />
<c:set var="compMessageMap" value="${sourceComponent.messagesAndClear }" />

<tag:messages messageMap="${compMessageMap}" id="${sourceComponent.displayId}"/>
