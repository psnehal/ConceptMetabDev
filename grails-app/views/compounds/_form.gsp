<%@ page import="conceptmetab.Compounds" %>



<div class="fieldcontain ${hasErrors(bean: compoundsInstance, field: 'display_id', 'error')} ">
	<label for="display_id">
		<g:message code="compounds.display_id.label" default="Displayid" />
		
	</label>
	<g:textField name="display_id" value="${compoundsInstance?.display_id}"/>
</div>



<div class="fieldcontain ${hasErrors(bean: compoundsInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="compounds.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${compoundsInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: compoundsInstance, field: 'compSrc', 'error')} ">
	<label for="compSrc">
		<g:message code="compounds.compSrc.label" default="Comp Src" />
		
	</label>
	
</div>

