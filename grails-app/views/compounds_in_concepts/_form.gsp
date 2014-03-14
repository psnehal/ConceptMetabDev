<%@ page import="conceptmetab.Compounds_in_concepts" %>



<div class="fieldcontain ${hasErrors(bean: compounds_in_conceptsInstance, field: 'compounds', 'error')} required">
	<label for="compounds">
		<g:message code="compounds_in_concepts.compounds.label" default="Compounds" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="compounds" name="compounds.id" from="${conceptmetab.Compounds.list()}" optionKey="id" required="" value="${compounds_in_conceptsInstance?.compounds?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: compounds_in_conceptsInstance, field: 'concepts', 'error')} required">
	<label for="concepts">
		<g:message code="compounds_in_concepts.concepts.label" default="Concepts" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="concepts" name="concepts.id" from="${conceptmetab.Concepts.list()}" optionKey="id" required="" value="${compounds_in_conceptsInstance?.concepts?.id}" class="many-to-one"/>
</div>

