<%@ tag body-content="scriptless" isELIgnored="false" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>
 
<tag:htmlHead>
	<c:set var="menuItems" value="${model.mainMenuComponent.allUserMenuItems}" />
	<body>
		<div id="wrap">
			<div id="header">
				
			</div>
				<div id="nav">
					<div class="inner clear">
						<ul id="nav-inner" class="clear">
							<c:forEach items="${menuItems['TEST'].subMenuItems}" var="item">
								<tag:menuItem item="${item}"/>
							</c:forEach>
						</ul>
					</div>
				</div>
			<div id="content-wrap">
				<div class="inner clear">
					<jsp:doBody/>
				</div>
			</div>
			<div id="footer">
				<div class="inner clear">
					
				</div>
			</div>
		</div>
	</body>
</tag:htmlHead>