
<%@ page import="conceptmetab.TestEnrich" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'testEnrich.label', default: 'TestEnrich')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-testEnrich" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-testEnrich" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="id1_id" title="${message(code: 'testEnrich.id1_id.label', default: 'Id1id')}" />
					
						<g:sortableColumn property="id2_id" title="${message(code: 'testEnrich.id2_id.label', default: 'Id2id')}" />
					
						<g:sortableColumn property="intersection" title="${message(code: 'testEnrich.intersection.label', default: 'Intersection')}" />
					
						<g:sortableColumn property="pval" title="${message(code: 'testEnrich.pval.label', default: 'Pval')}" />
					
						<g:sortableColumn property="qval" title="${message(code: 'testEnrich.qval.label', default: 'Qval')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${testEnrichInstanceList}" status="i" var="testEnrichInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${testEnrichInstance.id}">${fieldValue(bean: testEnrichInstance, field: "id1_id")}</g:link></td>
					
						<td>${fieldValue(bean: testEnrichInstance, field: "id2_id")}</td>
					
						<td>${fieldValue(bean: testEnrichInstance, field: "intersection")}</td>
					
						<td>${fieldValue(bean: testEnrichInstance, field: "pval")}</td>
					
						<td>${fieldValue(bean: testEnrichInstance, field: "qval")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${testEnrichInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
