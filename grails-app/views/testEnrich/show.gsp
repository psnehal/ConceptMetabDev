
<%@ page import="conceptmetab.TestEnrich" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'testEnrich.label', default: 'TestEnrich')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-testEnrich" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-testEnrich" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list testEnrich">
			
				<g:if test="${testEnrichInstance?.id1_id}">
				<li class="fieldcontain">
					<span id="id1_id-label" class="property-label"><g:message code="testEnrich.id1_id.label" default="Id1id" /></span>
					
						<span class="property-value" aria-labelledby="id1_id-label"><g:fieldValue bean="${testEnrichInstance}" field="id1_id"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${testEnrichInstance?.id2_id}">
				<li class="fieldcontain">
					<span id="id2_id-label" class="property-label"><g:message code="testEnrich.id2_id.label" default="Id2id" /></span>
					
						<span class="property-value" aria-labelledby="id2_id-label"><g:fieldValue bean="${testEnrichInstance}" field="id2_id"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${testEnrichInstance?.intersection}">
				<li class="fieldcontain">
					<span id="intersection-label" class="property-label"><g:message code="testEnrich.intersection.label" default="Intersection" /></span>
					
						<span class="property-value" aria-labelledby="intersection-label"><g:fieldValue bean="${testEnrichInstance}" field="intersection"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${testEnrichInstance?.pval}">
				<li class="fieldcontain">
					<span id="pval-label" class="property-label"><g:message code="testEnrich.pval.label" default="Pval" /></span>
					
						<span class="property-value" aria-labelledby="pval-label"><g:fieldValue bean="${testEnrichInstance}" field="pval"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${testEnrichInstance?.qval}">
				<li class="fieldcontain">
					<span id="qval-label" class="property-label"><g:message code="testEnrich.qval.label" default="Qval" /></span>
					
						<span class="property-value" aria-labelledby="qval-label"><g:fieldValue bean="${testEnrichInstance}" field="qval"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${testEnrichInstance?.id}" />
					<g:link class="edit" action="edit" id="${testEnrichInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
