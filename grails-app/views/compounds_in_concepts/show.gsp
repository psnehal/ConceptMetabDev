
<%@ page import="conceptmetab.Compounds_in_concepts" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'compounds_in_concepts.label', default: 'Compounds_in_concepts')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-compounds_in_concepts" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-compounds_in_concepts" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list compounds_in_concepts">
			
				<g:if test="${compounds_in_conceptsInstance?.compounds}">
				<li class="fieldcontain">
					<span id="compounds-label" class="property-label"><g:message code="compounds_in_concepts.compounds.label" default="Compounds" /></span>
					
						<span class="property-value" aria-labelledby="compounds-label"><g:link controller="compounds" action="show" id="${compounds_in_conceptsInstance?.compounds?.id}">${compounds_in_conceptsInstance?.compounds?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${compounds_in_conceptsInstance?.concepts}">
				<li class="fieldcontain">
					<span id="concepts-label" class="property-label"><g:message code="compounds_in_concepts.concepts.label" default="Concepts" /></span>
					
						<span class="property-value" aria-labelledby="concepts-label"><g:link controller="concepts" action="show" id="${compounds_in_conceptsInstance?.concepts?.id}">${compounds_in_conceptsInstance?.concepts?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${compounds_in_conceptsInstance?.id}" />
					<g:link class="edit" action="edit" id="${compounds_in_conceptsInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
