
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
	   <r:require module="export"/>
		 
		 
	</head>
	<body>
	<div id="show-concepts" class="content scaffold-show" role="main" style="padding:20px;">
	
	<div id ="title" style="width: 800px;border:2;">
	<h1>Concepts</h1> 
	
	<table  cellpadding="10">
	
		<g:if test="${ConceptInstance1?.name}">
		<tr>
		<td width="150px" height="20px"><span class="property-label">Concept 1:</span></td>
		<td><span class="comtable">${ConceptInstance1.name }</span></td>	
		</tr>
		</g:if>

		<g:if test="${ConceptInstance2?.name}">
		<tr>
		<td  width="150px" height="20px"><span class="property-label">Concept 2:</span></td>
		<td><span class="comtable">${ConceptInstance2.name }</span></td>
		</tr>
		</g:if>
	</table>	 		
	</div>	
	<h1>Overlapping Compounds</h1> 
	<div style="width: 100%;float:left;">
	<div  style="width: 250px;float:left;">
		<export:formats formats="['csv', 'excel']" params="[id1:"${params.id1}",id2:"${params.id2}"]" />
	</div>
	</div>		
	
	    	
		<table>
					<thead>
					<tr>
					<th>Index</th>
					<th width ='50%'>Compound Name</th>
					<th width ='20%'>Pubchem ID</th>
					<th width ='20%'>Kegg ID</th>		
							
					</thead>
					<tbody>
                    <g:each in="${ map}" status="i" var="var" >
                   
                    <tr>
                         <td>${i+1 }</td>       
                         <td>${var.value.names }</td>
                         <g:if test="${var.value.pubid == null ||  var.value.pubid.equals('NULL')  }"> <td>--</td></g:if>                         
                        	<g:else><td>${var.value.pubidurl}</td></g:else>
                       	<g:if test="${var.value.keid == null ||  var.value.keid.equals('NULL')  }"> <td>--</td></g:if>     
                                      
                        	<g:else><td>${var.value.keidurl}</td></g:else>
                    </tr>
                    </g:each>
                   </tbody> 
                   
	 </table>
	
		
		
	
	</body>
</html>
