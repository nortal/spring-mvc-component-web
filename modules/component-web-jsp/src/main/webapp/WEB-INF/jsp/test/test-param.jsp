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
		  		<th>Parameeter 1:</th>
		  		<td><c:out value="${model.param1 }"/></td>
		  	</tr><tr>
		  		<th>Parameeter 2:</th>
		  		<td><c:out value="${model.param2 }"/></td>
		  	</tr>
		  </table>
		</tag:controllerComponent>
	</tag:modelForm>
	
</tag:html>
