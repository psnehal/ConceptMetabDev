<%@ page import="conceptmetab.Meshid2treenum" %>



<div class="fieldcontain ${hasErrors(bean: meshid2treenumInstance, field: 'mesh_id', 'error')} required">
	<label for="mesh_id">
		<g:message code="meshid2treenum.mesh_id.label" default="Meshid" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="mesh_id" type="number" value="${meshid2treenumInstance.mesh_id}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: meshid2treenumInstance, field: 'tree_id', 'error')} ">
	<label for="tree_id">
		<g:message code="meshid2treenum.tree_id.label" default="Treeid" />
		
	</label>
	<g:textField name="tree_id" value="${meshid2treenumInstance?.tree_id}"/>
</div>

