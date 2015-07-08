<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="cw" uri="/WEB-INF/tld/cw.tld"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>
<tag:html> 
	<tag:modelForm> 
		<tag:controllerComponent>
		  
		  <table class="form">
		  	<tr>
		  		<th>Ava tavalise sisuga aken:</th>
		  		<td><tag:modal modalName="popup-with-simple-content" /></td>
		  	</tr><tr>
		  		<th>Ava JSP sisuga aken:</th>
		  		<td><tag:modal modalName="popup-with-jsp-content">
		  			<p>JSP sisu - siia saab lisada erinevaid teisi komponente</p>
		  		</tag:modal></td>
		  	</tr><tr>
		  		<th>Ava JSP sisuga aken koos lisanuppudega:</th>
		  		<td><tag:modal modalName="popup-with-jsp-content-with-buttons">
		  			<p>JSP sisu - siia saab lisada erinevaid teisi komponente. Sellel aknal on ka lisanupud</p>
		  		</tag:modal></td>
		  	</tr>
		  </table>
		</tag:controllerComponent>
	</tag:modelForm>
	
</tag:html>
