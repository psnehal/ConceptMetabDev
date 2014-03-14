
<%@ page import="conceptmetab.Compounds" %>

<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'compounds.label', default: 'Compounds')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-compounds" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div>
		
			<g:form action="search" method="get">
	            <g:textField name="q" value="${params.q}"/>
	            <g:submitButton name="search"/>
	        </g:form>
	        
		</div>
		<div>
		
		
		
		</div>
		<div id="list-compounds" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="display_id" title="${message(code: 'compounds.display_id.label', default: 'Displayid')}" />
					
						
					
						<g:sortableColumn property="name" title="${message(code: 'compounds.name.label', default: 'Name')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${compoundsInstanceList}" status="i" var="compoundsInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${compoundsInstance.id}">${fieldValue(bean: compoundsInstance, field: "display_id")}</g:link></td>
					
						
					
						<td>${fieldValue(bean: compoundsInstance, field: "name")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${compoundsInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
