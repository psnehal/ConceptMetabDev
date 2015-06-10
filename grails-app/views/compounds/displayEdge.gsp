<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'popup.css')}" type="text/css">
		<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
		<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
	</head>
 


<body> 
	<h1> Edge Information</h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			
			<hr>
			<table id = "result">
				<tr><td><span class="formText" >Concept 1:</span></td>
					<td><span class="formText">${enrichInstance?.id1.getName()}</span></td></tr>
				<tr><td><span class="formText" >Concept 2:</span></td>
					<td><span class="formText">${enrichInstance?.id2.getName()}</span></td></tr>
				<tr><td><span class="formText" >P-value</span></td>
					<td><span class="formText"><g:formatNumber number="${enrichInstance?.pval}" format="0.###E0" /></span></td></tr>
				<tr><td><span class="formText" >q-Value</span></td>
					<td><span class="formText"><g:formatNumber number="${enrichInstance?.qval}" format="0.###E0" /></span></td></tr>
				<tr><td><span class="formText" >Odds Ratio</span></td>
					<td><span class="formText"><g:formatNumber number="${enrichInstance?.odds}" format="0.###E0" /></span></td></tr>
				<tr><td><span class="formText" >Intersection</span></td>
					<td><span class="formText">${enrichInstance?.intersection}</span></td></tr>	
			
			</table>
			<br/>
			<h1> Overlapping Compounds</h1>
			<hr>
			<table id ="result">				
					<tr>
					<th>Index</th>
					<th>Compound Name</th>
					<th align="center">Pubchem ID</th>
					<th align="center">Kegg Id</th>	
					</tr>
                    <g:each in="${ map}" status="i" var="var" >                   
                    <tr>
                         <td>${i+1 }</td>       
                         <td width><g:link controller="Compounds" action="show"  id="${var.value.cid}" target="_blank">${var.value.names }</g:link></td>
                         <g:if test="${var.value.pubid == null ||  var.value.pubid.equals('NULL')  }"> <td>--</td></g:if>                         
                        	<g:else><td> <span class="comtable">${var.value.pubidurl }</span></td></g:else>                        	             	
                       	<g:if test="${var.value.keid == null ||  var.value.keid.equals('NULL')  }"> <td>--</td></g:if>                         
                        	<g:else><td><span class="comtable">${var.value.keidurl }</span></td></g:else>
                    <tr>
                    </g:each>
                   
                   
	 </table>
			
			
	

 </body>
 </html>
