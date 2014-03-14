
<%@ page import="conceptmetab.Concept_types" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'concept_types.label', default: 'Concept_types')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-concept_types" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		
		<div id="list-concept_types" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			
			<table>
				<thead>
					<tr>
					
					<g:sortableColumn property="name" title="${message(code: 'concept_types.name.label', default: 'Name')}" />
						<g:sortableColumn property="info" title="Information" />
							<g:sortableColumn property="size" title="Concept_size" />
					
						
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${map}" status="i" var="ci_inst">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td>${ci_inst.getKey()}</td>
						
						<td> <a href="#"><img src="${createLinkTo(dir: 'images/skin', file: 'information.png')}"  alt="Grails"/></a></td>
						<td>${ci_inst.getValue() }</td>
					
					</tr>
				</g:each>
				
				</tbody>
			</table>
			
		
		</div>
	</body>
</html>
