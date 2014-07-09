
<%@ page import="conceptmetab.Compounds" %>
<%@ page import="conceptmetab.Concept_types" %>
<%@ page import="conceptmetab.Meshid2treenum" %>


<!DOCTYPE html>
<html>
	<head>
	  <meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'compounds.label', default: 'Compounds')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
		
		 <g:javascript src="tablesorter/jquery-latest.js" /> 
 		<g:javascript src="tablesorter/jquery.tablesorter.js" />
		<script>
		$(document).ready(function() 
			    {   $("#myTable").tablesorter();

			    });  
			    

		</script>
		 <r:require module="export"/>
		 
		 
	</head>
	<body>
	  <div id="show-concepts" class="content scaffold-show" role="main">
	  		    <a href="#show-compounds" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
				<h1><g:message code="default.show.label" args="[entityName]" /></h1>
				<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
				</g:if>
	<div id ="title" style="width: 500px;border:1">
	<table  cellpadding="10">
		<tr>
		<g:if test="${compoundsInstance?.name}">
		<tr>
		<td width="200px" height="20px"><span class="property-label">Name</span></td>
		<td><span class="comtable"><g:fieldValue bean="${compoundsInstance}" field="name"/></span></td>	
		</tr>
		</g:if>

		<g:if test="${compoundsInstance?.kegg_id}">
		<tr>
		<td  width="200px" height="20px"><span class="property-label">Kegg Id</span></td>
		<td><span class="comtable"><g:fieldValue bean="${compoundsInstance}" field="kegg_id"/></span></td>
		</tr>
		</g:if>

		<g:if test="${compoundsInstance?.pubchem_id}">
		<tr>
		<td  width="200px" height="20px"><span class="property-label">Pubchem Id</span></td>
		<td><span class="comtable"><g:fieldValue bean="${compoundsInstance}" field="pubchem_id"/></span></td>
		</tr>
        	</g:if>	


		<g:if test="${compoundsInstance?.kegg_preferred_name}">
		<tr>
		<td  width="200px" height="20px"><span class="property-label">Kegg Preferred Name</span></td>
		<td><span class="comtable">${compoundsInstance.kegg_preferred_name.replace("NULL","--")}</span></td>
		</tr>
        	</g:if>	

		<g:if test="${compoundsInstance?.pubchem_preferred_name}">
		<tr>
		<td  width="200px" height="20px"><span class="property-label">Pubchem Preferred Name</span></td>
		<td><span class="comtable">${compoundsInstance.pubchem_preferred_name.replace("NULL", "--")}<span></td>
		</tr>
        	</g:if>	


		<g:if test="${compoundsInstance?.num_concepts}">
		<tr>
		<td  width="200px" height="20px"><span class="property-label"># of Concepts</span></td>
		<td><span class="comtable"><g:fieldValue bean="${compoundsInstance}" field="num_concepts"/></span></td>
		</tr>
        	</g:if>	
</table>
	</div>
		
		
		<br/>
		<br/>
		
		 <export:formats formats="['csv', 'excel']" action="show" id="${compoundsInstance.id}" params="[id:"${compoundsInstance.id}",id2: '0.05', odds: '0',fil:'qval']" />
		
		<g:if test="${conceptsInstance == null}">
		<span class="property-value" aria-labelledby="name-label">This compound doesn't belong to any concepts</span>
		</g:if>
		<g:else>
		  <div id="list-concepts" class="content scaffold-list" role="main">
		  <table id="myTable" class="tablesorter"> 				
				<thead>
				<tr>
				<th><g:message code="overlap" default="Index" /></th>
				<th><g:message code="overlap" default="Concept ID" /></th>
				<th><g:message code="overlap" default="Concept Name" /></th>
			    <th><g:message code="overlap" default="Concept Type" /></th>
				<th><g:message code="overlap" default="Concept Size" /></th>
				<th><g:message code="overlap" default="# of Erichments" /></th>					
				</tr>		
				</thead>
				<tbody>
				<g:each in="${conceptsInstance}" status="i" var="concept">
					<tr>
						<td> ${i+1} </td>						
						
						<g:set var="cnm" value="${concept.conTyp}"/>		
									
					<td>${concept.conid }
						  	
						 <td>${concept.name.capitalize()}</td>	
						 <td>${cnm} </td>										
						<td>${concept.numCom}</td>					
						<td>${concept.numEnc}</td>					
					</tr>
				</g:each>
				</tbody>
			</table>
	    </div>
	  </g:else>	
	  </div>
	</body>
</html>
