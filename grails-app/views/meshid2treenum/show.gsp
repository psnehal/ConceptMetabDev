
<%@ page import="conceptmetab.Meshid2treenum" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'meshid2treenum.label', default: 'Meshid2treenum')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-meshid2treenum" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-meshid2treenum" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list meshid2treenum">
			
				<g:if test="${meshid2treenumInstance?.mesh_id}">
				<li class="fieldcontain">
					<span id="mesh_id-label" class="property-label"><g:message code="meshid2treenum.mesh_id.label" default="Meshid" /></span>
					
						<span class="property-value" aria-labelledby="mesh_id-label"><g:fieldValue bean="${meshid2treenumInstance}" field="mesh_id"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${meshid2treenumInstance?.tree_id}">
				<li class="fieldcontain">
					<span id="tree_id-label" class="property-label"><g:message code="meshid2treenum.tree_id.label" default="Treeid" /></span>
					
						<span class="property-value" aria-labelledby="tree_id-label"><g:fieldValue bean="${meshid2treenumInstance}" field="tree_id"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${meshid2treenumInstance?.id}" />
					<g:link class="edit" action="edit" id="${meshid2treenumInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
