<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="cw" uri="/WEB-INF/tld/cw.tld"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>

<tag:componentItem item="vorm">
	<table class="form">
		<tbody>
			<tr>
				<tag:formInput element="dateTimeField" />
			</tr>
			<tr>
				<tag:formInput element="dateField" />
			</tr>
			<tr>
				<tag:formInput element="timeField" />
			</tr>
			<tr>
				<tag:formInput element="list" />
			</tr>
			<tr>
				<tag:formInput element="decimalField" />
			</tr>
			<tr>
				<tag:formInput element="integerField" />
			</tr>
			<tr>
				<tag:formInput element="longField" />
			</tr>
			<tr>
				<tag:formInput element="textField" />
			</tr>
			<tr>
				<tag:formInput element="requiredTextField" />
			</tr>
			<tr>
				<tag:formTextareaLarge element="longTextField" />
			</tr>
		</tbody>
	</table>
</tag:componentItem>

<tag:pageActions />


