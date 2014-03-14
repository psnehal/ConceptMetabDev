<%@ page import="conceptmetab.Concepts" %>



<div class="fieldcontain ${hasErrors(bean: conceptsInstance, field: 'original_id', 'error')} ">
	<label for="original_id">
		<g:message code="concepts.original_id.label" default="Originalid" />
		
	</label>
	<g:textField name="original_id" value="${conceptsInstance?.original_id}"/>
</div>



<div class="fieldcontain ${hasErrors(bean: conceptsInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="concepts.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${conceptsInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: conceptsInstance, field: 'num_compounds', 'error')} required">
	<label for="num_compounds">
		<g:message code="concepts.num_compounds.label" default="Numcompounds" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="num_compounds" type="number" value="${conceptsInstance.num_compounds}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: conceptsInstance, field: 'num_enriched', 'error')} required">
	<label for="num_enriched">
		<g:message code="concepts.num_enriched.label" default="Numenriched" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="num_enriched" type="number" value="${conceptsInstance.num_enriched}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: conceptsInstance, field: 'ctypes', 'error')} ">
	<label for="ctypes">
		<g:message code="concepts.ctypes.label" default="Ctypes" />
		
	</label>
	<g:select name="ctypes" from="${conceptmetab.Concept_types.list()}" multiple="multiple" optionKey="id" size="5" value="${conceptsInstance?.ctypes*.id}" class="many-to-many"/>
</div>

