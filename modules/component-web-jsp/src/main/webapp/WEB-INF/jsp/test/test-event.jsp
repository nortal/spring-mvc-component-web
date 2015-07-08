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
		  		<th>Sündmus ilma kinnituseta:</th>
		  		<td><tag:eventLink eventName="eventWithoutConfirmation" label="#Käivita" useControllerPrefix="false" /></td>
		  		<td><tag:eventButton eventName="eventWithoutConfirmation" label="#Käivita" useControllerPrefix="false" cssClass="button" /></td>
		  	</tr><tr>
		  		<th>Sündmus kinnitusega:</th>
		  		<td><tag:eventLink eventName="eventWithConfirmation" label="#Käivita" useControllerPrefix="false" /></td>
		  		<td><tag:eventButton eventName="eventWithConfirmation" label="#Käivita" useControllerPrefix="false" cssClass="button" /></td>
		  	</tr>
		  </table>
		  
		</tag:controllerComponent>
	</tag:modelForm>
	
</tag:html>
