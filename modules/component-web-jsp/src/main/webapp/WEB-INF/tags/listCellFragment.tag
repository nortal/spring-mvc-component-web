<%@ tag body-content="scriptless" isELIgnored="false" pageEncoding="UTF-8" description="Tagi eesmärgiks on võimaldada üle kirjutada listi erinevaid välju, andes ette välja numbri. Võimalik on kasutada järgmisi parameetreid: cell (FromElement), component (ListComponent), editable (Boolean), row (ListRow)" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="cw" uri="/WEB-INF/tld/cw.tld"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>
<%@ attribute name="cellNr" required="true" type="java.lang.Integer" %>
<%@ attribute name="position" required="true" type="com.nortal.spring.cw.core.web.tag.list.ListCellFragmentPosition" description="Kus kohas defineeritud fragmenti kasutatakse kas päises, kehas või jaluses. Vastavalt on võimalikud väärtused THEAD, TBODY, TFOOT. Lisaks kui on määratud kasutuskohaks THEAD on lahtri elemendiks TH vastasel juhul TD" %>

<!-- listCellFragment on kasutusel ainult listi sees, component viitab ümbritsevale listile -->
<cw:createFragment name="${component.displayId}row${position.code}Cell${cellNr}Fragment">
	<c:choose>
		<c:when test="${position.code eq 'H'}">
			<th>
				<jsp:doBody />	
			</th>
		</c:when> 
		<c:otherwise>
			<td>
				<jsp:doBody />
			</td>
		</c:otherwise>
	</c:choose>
</cw:createFragment>
