
<%@ page import="conceptmetab.Compounds_in_concepts" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'compounds_in_concepts.label', default: 'Compounds_in_concepts')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-compounds_in_concepts" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-compounds_in_concepts" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<th><g:message code="compounds_in_concepts.compounds.label" default="Compounds" /></th>
					
						<th><g:message code="compounds_in_concepts.concepts.label" default="Concepts" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${compounds_in_conceptsInstanceList}" status="i" var="compounds_in_conceptsInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${compounds_in_conceptsInstance.id}">${fieldValue(bean: compounds_in_conceptsInstance, field: "compounds")}</g:link></td>
					
						<td>${fieldValue(bean: compounds_in_conceptsInstance, field: "concepts")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${compounds_in_conceptsInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
