<!DOCTYPE html>
<html>
	<head>
		
		<g:set var="entityName" value="${message(code: 'concepts.label', default: 'Concepts')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
		
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'createChart.css')}" type="text/css">
		<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
		<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
	</head>
 


<body> 


<body> 

<br/>

	
	<span class = "formTextHeader"> Concept Information</span>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<hr>
			<table id = "display">
				<tr><td><span class="formText" >Name</span></td>
					<td>	<g:set var="cnm" value="${conceptsInstance?.id}"/>	
					<span class="formText" ><b>${conceptsInstance?.name.capitalize()}</b></span>
					</td></tr>
				<tr><td><span class="formText" >Original-Id</span></td>
					<td><span class="formText" ><g:fieldValue bean="${conceptsInstance}" field="original_id"/></span></td></tr>
				<tr><td><span class="formText" ># of Compounds</span></td>
					<td><span class="formText">	<g:link controller="Compounds_in_concepts" action="findComp" params="[q:cnm]" target="_blank"><g:fieldValue bean="${conceptsInstance}" field="num_compounds"/></g:link></span></td></tr>
				<tr><td><span class="formText" >Concept Type</span></td>
					<td><span class="formText">"${conceptsInstance.concept_types}"</span></td> </tr>
			
			
			</table>
			
			
	

 </body>
 </html>


