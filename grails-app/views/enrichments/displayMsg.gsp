<!DOCTYPE html>
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
				<td><span class="formText" ><g:fieldValue bean="${conceptsInstance}" field="original_id"/></span></td></tr>
			<tr><td><span class="formText" ># of Compounds</span></td>
				<td><span class="formText">	<g:link controller="Compounds_in_concepts" action="findComp" params="[q:cnm]" target="_blank"><g:fieldValue bean="${conceptsInstance}" field="num_compounds"/></g:link></span></td></tr>
			<tr><td><span class="formText" >Concept Type</span></td>
				<td><span class="formText">${conceptsInstance.concept_types}</span></td> </tr>	
		</table>
			
			
	

 </body>
 </html>


