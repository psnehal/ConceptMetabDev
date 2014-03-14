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
			<table id = "result">
				<tr><td><span class="formText" >Id1</span></td>
					<td><span class="formText">${enrichInstance?.id1.getName()}</span></td></tr>
				<tr><td><span class="formText" >Id2</span></td>
					<td><span class="formText">${enrichInstance?.id2.getName()}</span></td></tr>
				<tr><td><span class="formText" >P-value</span></td>
					<td><span class="formText"><g:formatNumber number="${enrichInstance?.pval}" format="0.###E0" /></span></td></tr>
				<tr><td><span class="formText" >Q-Value</span></td>
					<td><span class="formText"><g:formatNumber number="${enrichInstance?.qval}" format="0.###E0" /></span></td></tr>
				<tr><td><span class="formText" >Odds Ratio</span></td>
					<td><span class="formText"><g:formatNumber number="${enrichInstance?.odds}" format="0.###E0" /></span></td></tr>
				<tr><td><span class="formText" >Intersection</span></td>
					<td><span class="formText">${enrichInstance?.intersection}</span></td></tr>	
			
			</table>
			<span class = "formTextHeader"> Overlapping Compounds</span>
			<table id = "result">
			
				<g:each in="${comIns}" status="i" var="comp">
				<tr>
					<td>${comp.id}</td>
					<td>${comp.name}</td>
					<td>${comp.id2}</td>
					<td>${comp.iid}</td>
				
				</tr>		
				</g:each>
			</table>
			
			
	

 </body>
 </html>

