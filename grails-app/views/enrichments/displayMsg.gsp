<!DOCTYPE html>
<%@ page import="conceptmetab.Meshid2treenum" %>
<html>
<head>
	<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
	<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
	<link rel="stylesheet" href="${resource(dir: 'css', file: 'popup.css')}" type="text/css">
</head>
<body>

<h1>Concept Information</h1>
 
	<table id ="result">
		  
			<tr><td><span class="formText" >Name</span></td>
			    <td><g:set var="cnm" value="${conceptsInstance?.id}"/><span class="formText" ><b>${conceptsInstance?.name.capitalize()}</b></span>
				</td></tr>
			<tr><td><span class="formText" >Concept-Id</span></td>
			 <g:if test="${conceptsInstance.concept_types.getFullname().contains("MeSH")}">		
				<%def meshid2treenumInstance = Meshid2treenum.findAllWhere(mesh_id : conceptsInstance?.original_id ) %>						  
			  	<g:if test="${meshid2treenumInstance.size() != 0}">
		          	 <td><span class="formText" >${meshid2treenumInstance.tree_id.toString().replace("[", "") .replace("]", "") }</span></td></tr>        
			  	</g:if>
			  	<g:else>		
			  	
				    <td><span class="formText" >${conceptsInstance.original_id }</span></td></tr> 
				</g:else>	  
			  </g:if>
			  <g:else>			  	
				    <td><span class="formText" >${conceptsInstance.original_id }</span></td></tr> 
				</g:else>
			<tr><td><span class="formText" ># of Compounds</span></td>
				<td><span class="formText">	<g:link controller="Compounds_in_concepts" action="findComp" params="[q:cnm]" target="_blank"><g:fieldValue bean="${conceptsInstance}" field="num_compounds"/></g:link></span></td></tr>
			<tr><td><span class="formText" >Concept Type</span></td>
				<td><span class="formText">${conceptsInstance.concept_types}</span></td> </tr>	
		</table>
			
			
	

 </body>
 </html>


