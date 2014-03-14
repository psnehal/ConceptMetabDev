<%@ page import="conceptmetab.Concept_types" %>



<div class="fieldcontain ${hasErrors(bean: concept_typesInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="concept_types.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${concept_typesInstance?.name}"/>
</div>

