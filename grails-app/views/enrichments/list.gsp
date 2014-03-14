
<%@ page import="conceptmetab.Enrichments" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'enrichments.label', default: 'Enrichments')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
	
	
		<a href="#list-enrichments" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-enrichments" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="intersection" title="${message(code: 'enrichments.intersection.label', default: 'Intersection')}" />
					
						<g:sortableColumn property="pval" title="${message(code: 'enrichments.pval.label', default: 'Pval')}" />
					
						<g:sortableColumn property="qval" title="${message(code: 'enrichments.qval.label', default: 'Qval')}" />
					
						<th><g:message code="enrichments.id1.label" default="Id1" /></th>
					
						<th><g:message code="enrichments.id2.label" default="Id2" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${enrichmentsInstanceList}" status="i" var="enrichmentsInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${enrichmentsInstance.id}">${fieldValue(bean: enrichmentsInstance, field: "intersection")}</g:link></td>
					
						<td>${fieldValue(bean: enrichmentsInstance, field: "pval")}</td>
					
						<td>${fieldValue(bean: enrichmentsInstance, field: "qval")}</td>
					
						<td>${fieldValue(bean: enrichmentsInstance, field: "id1")}</td>
					
						<td>${fieldValue(bean: enrichmentsInstance, field: "id2")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${enrichmentsInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
