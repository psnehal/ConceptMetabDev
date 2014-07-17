
<%@ page import="conceptmetab.Enrichments" %>
<%@ page import="conceptmetab.Meshid2treenum" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		 <g:javascript src="tablesorter/jquery-latest.js" /> 
 		<g:javascript src="tablesorter/jquery.tablesorter.js" />
		<script>
		$(document).ready(function() 
			    { 


			function log10(val) {
				console.log("values is" + val);
				document.getElementById('cell1').dataset.value= Math.log(val) / Math.LN10;
				}

			
			 $.tablesorter.addParser({
			        // set a unique id 
			        id: 'rangesort',
			        is: function (s) {
			            // return false so this parser is not auto detected 
			            return false;
			        },
			        format: function (s, table, cell, cellIndex) {
			            // get data attributes from $(cell).attr('data-something');
			            // check specific column using cellIndex
			            console.log($(cell).attr('data-value'));
			            return $(cell).attr('data-value');
			        },
			        // set type, either numeric or text 
			        type: 'numeric'
			    });

				    
			        $("#myTable").tablesorter({
			        			 headers: {
			        			        4: {
			        			            sorter: 'rangesort'
			        			        },
			        			        5: {
			        			            sorter: 'rangesort'
			        			        },
			        			        7: {
			        			            sorter: 'rangesort'
			        			        }
			        		 }
			        }); 
			    }); 
			    

		</script>
		 <r:require module="export"/>
   
	</head>
	<body>
	
	 <g:set var="id2" value="${params.id2}"/>
	<g:set var="odds" value="${params.odds}"/>
				<g:set var="fil" value="${params.fil}"/>
				<g:link controller="concepts" action="show" id="${params.q}" params="[id2: id2, odds: odds,fil:fil]">Go Back to Filter Page</g:link></td>
	<% List statements = params.list('statement') %>			
	 <export:formats formats="['csv', 'excel']" action="table" params="[q:"${params.q}", odds:"${params.odds }", id1:"1.45e-323",id2:"${params.id2 }",fil:"${params.fil }", statement:"${params.statement}", table:"table"]" />
	 <p>${statements}</p>
   <div id="show-concepts" class="content scaffold-show" role="main"> 						
    <div id ="title" style="width: 676px;float:left;">
	             <h1>Concept Information</h1>
	            <div style="width:676px;float:left ;">
      		<ol class="property-list concepts">
	          <li class= "fieldcontain">
	           	 <span id="name-label" class="property-label"><g:message code="concepts.name.label" default="Name" /></span>
	             <span class="property-value">${conceptInstance.name.capitalize()} </span>
	          </li>
	           <g:if test="${conceptInstance.concept_types.getFullname().contains("MeSH")}">		
						  		   <%def meshid2treenumInstance =Meshid2treenum.findAllWhere(mesh_id : conceptsInstance.original_id ) %>
						  		  
			  	<g:if test="${meshid2treenumInstance.size() != 0}">		  
			   			<li class= "fieldcontain">
		          	 <span class="property-label">Concept Id:</span>
		          	 <span class="property-value">${meshid2treenumInstance.toString().replace("[", "") .replace("]", "") } </span></li>        
			  	</g:if>
			  	<g:else>					
				  <li class= "fieldcontain">
	          	 <span class="property-label">Concept Id:</span>
	          	 <span class="property-value">${meshid2treenumInstance.toString().replace("[", "") .replace("]", "") } </span></li>	           
				</g:else>	  
			  </g:if>
	        <g:else>
	          <li class= "fieldcontain"><span class="property-label">Concept Id:</span>
	          <span class="property-value">${conceptInstance.original_id} </span></li>
	        </g:else>  
	        <li class= "fieldcontain"><span class="property-label">Concept Type:</span> 
	          <span class="property-value">${conceptInstance.concept_types.getFullname()} </span>
	        </li>
	        <li class= "fieldcontain"><span class="property-label"># of compounds:</span> 
	           <span class="property-value">${conceptInstance.num_compounds} </span>
	        </li>
	        </ol>
	        	
	 </div>	 
	</div>
	<br />
	<br />
	<br />
	<br />
	</div>


 
			<table id="myTable" class="tablesorter"> 
			
				<thead>
					<tr>
						<th><g:message code="overlap" default="Index" /></th>
						<th><g:message code="overlap" default="Concept Name" /></th>
						<th><g:message code="overlap" default="Concept ID" /></th>
						<th><g:message code="overlap" default="Concept Type" /></th>
						<th><g:message code="overlap" default="P-value" /></th>
						<th><g:message code="overlap" default="Q-Value" /></th>
						<th><g:message code="overlap" default="Overlap" /></th>
						<th><g:message code="odds" default="Odds Ratio" /></th>
					
					</tr>
				</thead>
				<tbody>
							
				<g:each in="${allR}" status="i" var="fileMap">
				 <tr>
				 <td>${i+1 }</td>
				 	<td>${fileMap.name}</td>				 	
				  	<td>${fileMap.conid}</td>				  	
				   	<td>${fileMap.conTypes}</td>
				   
				    <td id="pval" data-value="${-Math.log10(fileMap.pval)}">${fileMap.pval}</td>
				   <td id="qval" data-value="${-Math.log10(fileMap.qval)}">${fileMap.qval}</td>
				    <td>${fileMap.ins}</td>
				     <g:if test="${fileMap.odds == 9.99999999E8 }">
				     <td data-value="999999999"><%  Infinity%> &infin;</td>
				     </g:if>
				     <g:else>
				      <td data-value=${fileMap.odds}>${fileMap.odds}</td>
				      </g:else>
				     
				  
				</tr>
				</g:each>
				</tbody>
			</table>
		
	</body>
</html>
