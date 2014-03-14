
<%@ page import="conceptmetab.Enrichments" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'enrichments.label', default: 'Enrichments')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title> 
		
		<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
		<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
		<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
		<link rel="stylesheet" href="/resources/demos/style.css" />
		
		
		
	</head>
	<body>
		<a href="#show-enrichments" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-enrichments" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list enrichments">
			
				<g:if test="${enrichmentsInstance?.intersection}">
				<li class="fieldcontain">
					<span id="intersection-label" class="property-label"><g:message code="enrichments.intersection.label" default="Intersection" /></span>
					
						<span class="property-value" aria-labelledby="intersection-label"><g:fieldValue bean="${enrichmentsInstance}" field="intersection"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${enrichmentsInstance?.pval}">
				<li class="fieldcontain">
					<span id="pval-label" class="property-label"><g:message code="enrichments.pval.label" default="Pval" /></span>
					
						<span class="property-value" aria-labelledby="pval-label"> ${enrichmentsInstance.pval} </span>
					
				</li>
				</g:if>
			
				<g:if test="${enrichmentsInstance?.qval}">
				<li class="fieldcontain">
					<span id="qval-label" class="property-label"><g:message code="enrichments.qval.label" default="Qval" /></span>
					
						<span class="property-value" aria-labelledby="qval-label"> ${enrichmentsInstance.qval} </span>
					
				</li>
				</g:if>
			
				<g:if test="${enrichmentsInstance?.id1}">
				<li class="fieldcontain">
					<span id="id1-label" class="property-label"><g:message code="enrichments.id1.label" default="Id1" /></span>
					
						<span class="property-value" aria-labelledby="id1-label"><g:link controller="concepts" action="show" id="${enrichmentsInstance?.id1?.id}">${enrichmentsInstance.id1.getName()}</g:link></span>
					
				</li>
				</g:if>
				
			
			
				<g:if test="${enrichmentsInstance?.id2}">
				<li class="fieldcontain">
					<span id="id2-label" class="property-label"><g:message code="enrichments.id2.label" default="Id2" /></span>
					
						<span class="property-value" aria-labelledby="id2-label"><g:link controller="concepts" action="show" id="${enrichmentsInstance?.id2?.id}">${enrichmentsInstance?.id2?.getName().encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			
			<p>

</br></br>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${enrichmentsInstance?.id}" />
					<g:link class="edit" action="edit" id="${enrichmentsInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
