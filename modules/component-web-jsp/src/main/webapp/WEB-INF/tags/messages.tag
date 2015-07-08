<%@ tag body-content="scriptless" isELIgnored="false" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>

<%@ attribute name="messageMap" rtexprvalue="true" type="java.util.Map"%>
<%@ attribute name="id" rtexprvalue="true" type="java.lang.String"%>

<c:set var="controllerMessages" value="${empty messageMap and empty id}"/>

<c:set var="messageMap" value="${controllerMessages ? model.returnMessages : messageMap}"/>

<div class="messages" id="${controllerMessages ? 'messages' : id.concat('_messages')}">

<c:if test="${not empty messageMap }">
	<c:if test="${not empty messageMap['ERROR']}">
		<c:forEach items="${messageMap['ERROR']}" var="msg">
			<div class="message msg-error">
				<h2>${msg.message}</h2>
				<c:if test="${not empty msg.message}">
					<p>${msg.messageBody}</p>
				</c:if>
			</div>
		</c:forEach>
	</c:if>
	<c:if test="${not empty messageMap['OK']}">
		<c:forEach items="${messageMap['OK']}" var="msg">
			<div class="message msg-ok">
				<h2>${msg.message }</h2>
				<c:if test="${not empty msg.message}">
					<p>${msg.messageBody}</p>
				</c:if>
			</div>
		</c:forEach>
	</c:if>
	<c:if test="${not empty messageMap['WARNING']}">
		<c:forEach items="${messageMap['WARNING']}" var="msg">
			<div class="message msg-warning">
				<h2>${msg.message }</h2>
				<c:if test="${not empty msg.message}">
					<p>${msg.messageBody}</p>
				</c:if>
			</div>
		</c:forEach>
	</c:if>
	<c:if test="${not empty messageMap['INFO']}">
		<c:forEach items="${messageMap['INFO']}" var="msg">
			<div class="message msg-info">
				<h2>${msg.message }</h2>
				<c:if test="${not empty msg.message}">
					<p>${msg.messageBody}</p>
				</c:if>
			</div>
		</c:forEach>
	</c:if>
	<c:if test="${not empty messageMap['LABEL']}">
		<c:forEach items="${messageMap['LABEL']}" var="msg">
			<div class="message">
				<h2>${msg.message }</h2>
				<c:if test="${not empty msg.message}">
					<p>${msg.messageBody}</p>
				</c:if>
			</div>
		</c:forEach>
	</c:if>
	<c:if test="${not empty messageMap['UNKNOWN']}">
		<c:forEach items="${messageMap['UNKNOWN']}" var="msg">
			<div class="message">
				<h2>${msg.message }</h2>
				<c:if test="${not empty msg.message}">
					<p>${msg.messageBody}</p>
				</c:if>
			</div>
		</c:forEach>
	</c:if>
</c:if>

</div>
