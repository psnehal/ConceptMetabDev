
<%@ page import="conceptmetab.Compounds" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'compounds.label', default: 'Compounds')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-compounds" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div id ="title" style="width: 600px ;border = 1">
		<div id="show-compounds" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list compounds">
			
				<g:if test="${compoundsInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="com-label"><g:message code="compounds.name.label" default="Name" /></span>
					<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${compoundsInstance}" field="name"/></span>					
				</li>
				</g:if>
			
				<g:if test="${compoundsInstance?.kegg_id}">
				<li class="fieldcontain">
					<span id="compSrc-label" class="com-label"><g:message code="compounds.compSrc.label" default="Kegg Id" /></span>
					<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${compoundsInstance}" field="kegg_id"/></span>			
				</li>
				</g:if>
				
				<g:if test="${compoundsInstance?.pubchem_id}">
				<li class="fieldcontain">
					<span id="compSrc-label" class="com-label"><g:message code="compounds.compSrc.label" default="Pubchem Id" /></span>
					<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${compoundsInstance}" field="pubchem_id"/></span>						
				</li>
				</g:if>
				
				<g:if test="${compoundsInstance?.kegg_preferred_name}">
				<li class="fieldcontain">
					<span id="compSrc-label" class="com-label"><g:message code="compounds.compSrc.label" default="Kegg Preferred Name" /></span>
					<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${compoundsInstance}" field="kegg_preferred_name"/></span>						
				</li>
				</g:if>
				
					<g:if test="${compoundsInstance?.pubchem_preferred_name}">
				<li class="fieldcontain">
					<span id="compSrc-label" class="com-label"><g:message code="compounds.compSrc.label" default="Pubchem Preferred Name" /></span>
					<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${compoundsInstance}" field="pubchem_preferred_name"/></span>			
				</li>
				</g:if>
				
					<g:if test="${compoundsInstance?.internal_id}">
				<li class="fieldcontain">
					<span id="compSrc-label" class="com-label"><g:message code="compounds.compSrc.label" default="internal_id Id" /></span>
					<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${compoundsInstance}" field="internal_id"/></span>			
				</li>
				</g:if>
			
				
			
			</ol>
		
		</div>
		</div>
		<br/><br/>
		<hr/>
		
		<g:if test="${conceptsInstance == null}">
		<span class="property-value" aria-labelledby="name-label">This compound doesn't belong to any concepts</span>
		</g:if>
		<g:else>
		  <div id="list-concepts" class="content scaffold-list" role="main">
		  <table>						
				<thead>
				<tr>
				
					<g:sortableColumn property="index" title="${message(code: 'concepts.original_id.label', default: 'Index')}"   />
					<g:sortableColumn property="original_id" title ="External Concept Id" params="[name:"${params.name}"]"  />
					<th>Concept Type</th>
					<g:sortableColumn property="name" title ="Concept Name" params="[name:"${params.name}"]" />
					<g:sortableColumn property="num_compounds" title="${message(code: 'concepts.num_compounds.label', default: 'Concept Size')}"   params="[name:"${params.name}"]" />
					<g:sortableColumn property="num_enriched" title ="# of Enrichments" params="[name:"${params.name}"]" />
				
				</tr>
				</thead>
				<tbody>
				<g:each in="${conceptsInstance}" status="i" var="concept">
					<tr>
						<td> ${i+1} </td>
						<td><g:link controller = 'Concepts' action="show" id="${concept.id}" params="[id2: '0.05', odds: '0',fil:'qval']">${concept.id}</g:link></td>
						<g:set var="cnm" value="${concept.concept_types.fullname}"/>		
						 <td>${cnm} </td>
						<td>${concept.name.capitalize()}</td>					
						<td>${concept.num_compounds}</td>					
						<td>${concept.num_enriched}</td>					
					</tr>
				</g:each>
				</tbody>
			</table>
	    </div>
	  </g:else>	
	</body>
</html>
