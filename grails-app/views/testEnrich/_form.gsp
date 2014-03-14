<%@ page import="conceptmetab.TestEnrich" %>



<div class="fieldcontain ${hasErrors(bean: testEnrichInstance, field: 'id1_id', 'error')} required">
	<label for="id1_id">
		<g:message code="testEnrich.id1_id.label" default="Id1id" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="id1_id" type="number" value="${testEnrichInstance.id1_id}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: testEnrichInstance, field: 'id2_id', 'error')} required">
	<label for="id2_id">
		<g:message code="testEnrich.id2_id.label" default="Id2id" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="id2_id" type="number" value="${testEnrichInstance.id2_id}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: testEnrichInstance, field: 'intersection', 'error')} required">
	<label for="intersection">
		<g:message code="testEnrich.intersection.label" default="Intersection" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="intersection" type="number" value="${testEnrichInstance.intersection}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: testEnrichInstance, field: 'pval', 'error')} required">
	<label for="pval">
		<g:message code="testEnrich.pval.label" default="Pval" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="pval" value="${fieldValue(bean: testEnrichInstance, field: 'pval')}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: testEnrichInstance, field: 'qval', 'error')} required">
	<label for="qval">
		<g:message code="testEnrich.qval.label" default="Qval" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="qval" value="${fieldValue(bean: testEnrichInstance, field: 'qval')}" required=""/>
</div>

