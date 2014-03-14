<%@ page import="conceptmetab.Enrichments" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
<link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}" type="text/css">
<link rel="stylesheet" href="${resource(dir: 'css', file: 'createChart.css')}" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <meta name="layout" content="main" />
<g:javascript src="jquery-latest.js" />
<g:javascript src="jquery.tablesorter.js" />
		<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
		<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script>

$(document).ready(function() 
	    { 
	        $("#myTable").tablesorter(); 
	    } 
	);

</script>

<title>Enriched Concepts</title>
</head>
<body>
<br/> <br/>

	<div align ="center">
		<g:set var="cnm" value="${params.q}"/>	
	   <g:include action=  "createChart"  params="[id1:1e-45,id2:0.01,q:cnm,fil:'qval' ]"/>	
	</div>
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>    
    <div>
	<table id="myTable" class="tablesorter">
					<thead>
					<tr>				
					<th>Concept Name </th>
					<th>External Id </th>	
					<th>Concept type</th>	
					<th>P-Value</th>
					<th>Q-value</th>
					<th>Intersection</th>				
					<th>odds</th>							
					</thead>
					<tbody>
	                    <g:each in="${ map}" >
	                    <tr>            
	                         <td width="30%">${ it.name}</td>
	                         <td width="8%">${ it.eid}</td>
	                         <td width="15%">${it.ctypes }</td>
	                         <td width="10%">${it.pval }</td>
	                         <td width="10%">${it.qval }</td>
	                         <td width="3%">${it.ins }</td>
	                         <td width="5%">${it.odds }</td>
	                    <tr>
	                    </g:each>
                     </tbody> 
                   
	 </table>
	</div> 
  <export:formats formats="['csv', 'excel']" params="[q:"${params.q}", id1:"${params.id1}",id2:"${params.id2}",fil:"${params.fil}"]" />
  
</body>
</html>