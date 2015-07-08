<%@ tag body-content="scriptless" isELIgnored="false" pageEncoding="UTF-8" description="Lai vormi textarea väli" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>
<%@ taglib prefix="cw" uri="/WEB-INF/tld/cw.tld"%>

<%@ attribute name="element" required="true" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="tableCell" rtexprvalue="true" type="java.lang.Boolean" description="Kui tõene, tekitatakse tabeli lahter. Vaikeväärtus on true" %>
<%@ attribute name="showLabel" rtexprvalue="true" type="java.lang.Boolean" %>
<%@ attribute name="inputFragment" required="false" fragment="true" %>
<%@ attribute name="onChange" required="false" rtexprvalue="true" type="java.lang.String"%>

<c:set var="tableCell" value="${empty tableCell ? true : tableCell}" /> 

<c:choose>
	<c:when test="${tableCell}">
		<td colspan="2">
			<p class="comment">
				<tag:formInput element="${element }" tableCell="false" onChange="${onChange}" inputFragment="${inputFragment}" showLabel="${showLabel}" />
			</p>	
		</td>
	</c:when>
	<c:otherwise>
		<p class="comment">
			<tag:formInput element="${element}" tableCell="false" onChange="${onChange}" inputFragment="${inputFragment}" showLabel="${showLabel}" />
		</p>
	</c:otherwise>
</c:choose>