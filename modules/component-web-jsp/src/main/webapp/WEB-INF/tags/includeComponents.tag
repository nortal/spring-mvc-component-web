<%@ tag body-content="scriptless" isELIgnored="false" pageEncoding="UTF-8" description="ComplexComponent sees elavate komponentide kuvamine" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="cw" uri="/WEB-INF/tld/cw.tld"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>

<%@ attribute name="sourceComponent" required="true" rtexprvalue="true" type="com.nortal.spring.cw.core.web.component.composite.complex.ComplexComponent" %>

<c:forEach items="${sourceComponent.components}" var="componentEntity">
	<tag:componentErrors componentName="${componentEntity.key}" />
	<tag:includeComp name="${componentEntity.key}" sourceComponent="${sourceComponent}" />
</c:forEach>
