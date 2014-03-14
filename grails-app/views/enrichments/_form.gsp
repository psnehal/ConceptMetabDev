<%@ page import="conceptmetab.Enrichments" %>



<div class="fieldcontain ${hasErrors(bean: enrichmentsInstance, field: 'intersection', 'error')} required">
	<label for="intersection">
		<g:message code="enrichments.intersection.label" default="Intersection" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="intersection" type="number" value="${enrichmentsInstance.intersection}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: enrichmentsInstance, field: 'pval', 'error')} required">
	<label for="pval">
		<g:message code="enrichments.pval.label" default="Pval" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="pval" value="${fieldValue(bean: enrichmentsInstance, field: 'pval')}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: enrichmentsInstance, field: 'qval', 'error')} required">
	<label for="qval">
		<g:message code="enrichments.qval.label" default="Qval" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="qval" value="${fieldValue(bean: enrichmentsInstance, field: 'qval')}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: enrichmentsInstance, field: 'id1', 'error')} required">
	<label for="id1">
		<g:message code="enrichments.id1.label" default="Id1" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="id1" name="id1.id" from="${conceptmetab.Concepts.list()}" optionKey="id" required="" value="${enrichmentsInstance?.id1?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: enrichmentsInstance, field: 'id2', 'error')} required">
	<label for="id2">
		<g:message code="enrichments.id2.label" default="Id2" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="id2" name="id2.id" from="${conceptmetab.Concepts.list()}" optionKey="id" required="" value="${enrichmentsInstance?.id2?.id}" class="many-to-one"/>
</div>

