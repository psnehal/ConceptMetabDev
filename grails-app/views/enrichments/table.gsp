
<%@ page import="conceptmetab.Enrichments" %>
<%@ page import="conceptmetab.Meshid2treenum" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		 <title>Enrichment Table for ${conceptInstance.name}</title>
		 <g:javascript src="tablesorter/jquery-latest.js" /> 
 		<g:javascript src="tablesorter/jquery.tablesorter.js" />
		<script>
		$(document).ready(function() { 
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
			        	sortList: [[1,0],[3,0]],
			        			 headers: {
				        			 3: {
				        				 filter: true
					        			 },
			        			        4: {
			        			            sorter: 'rangesort'
			        			        },
			        			        5: {
			        			            sorter: 'rangesort'
			        			        },
			        			        7: {
			        			            sorter: 'rangesort'
			        			        }
			        		 		},
			        		 		
			        		 	    widgets: ["filter"],
						        	    widgetOptions: {
						        	      filter_functions: {
						        	        // Add select menu to this column
						        	        // set the column value to true, and/or add "filter-select" class name to header
						        	    	  filter_columnFilters : true,
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
	 
				 
<!----<g:link controller="concepts" action="show" id="${params.q}" params="[id2: id2, odds: odds,fil:fil]">Go Back to Filter Page</g:link></td> -->
	<% List statements = params.list('statement') %>			
	 <% def dbs =""
							  def db =[]
						  if(params.statement instanceof java.lang.String)
						  {
							
							 db.add(params.statement);
						  }
						  else
						  {
							  db = params.statement.toList()
						  }
					%>
	
   <div id="show-concepts" class="content scaffold-show" role="main"> 		
   	<div style="width: 100%;float:left;">
		<div style="padding:10px 1px 10px 1px">
		 <div style="width:100px; float:left">
			  <g:set var="fil" value="${params.fil}"/>
					<g:form controller="concepts" action="show" method="get">
					  <g:hiddenField name="id2"  value="${id2}"/>                            
					  <g:hiddenField name="odds"  value="${odds}"/>
					  <g:hiddenField name="fil" value="${fil}" />
					  <g:hiddenField name="id" value="${conceptInstance.id}" />
					  <g:submitButton name="Submit" value="Go Back" class="submit"/>
					</g:form>
			</div>

		<div style="margin-left:10px; float:none">
		<g:form action="redirectView" method="get" id="myform">   
						   <g:hiddenField name="id2"  value="${params.id2 }"/>
						   <g:hiddenField name="id1"  value="1.45e-323"/>
						   <g:hiddenField name="q" value="${params.q}" />
						   <g:hiddenField name="odds" value="${params.odds}" />
						   <g:hiddenField name="fil" value="${params.fil}" />
						   <g:each in="${db}" status="i" var="test">	
							 <input type="hidden" name="statement" value="${test}">	
						   </g:each>
						   <g:submitButton name="network" value="Star Network" class="submit"/>			
							<g:submitButton name="CompleteNetwork" value="Complete Network" class="submit"/>		
							<g:submitButton name="heatmap" value="Heatmap" class="submit"/>	 
						   </g:form> 	
				
				
				</div>
  
</div>
			
			
	</div>					
    <div id ="title" style="width: 99%;float:left;">								
					 <h1>Concept  Information</h1>					
					 <table id="result2" cellpadding="0" cellspacing="0" >
					 <tr>
					 <td width="40%"><span id="original_id-label" class="property-label"><b>Concept Name: </b>${conceptInstance.name} </span></td>
					 <g:if test="${conceptInstance.concept_types.getFullname().contains("MeSH")}">		
						  <%def meshid2treenumInstance =Meshid2treenum.findAllWhere(mesh_id : conceptInstance.original_id ) %>						  		  
					  		<g:if test="${meshid2treenumInstance.size() != 0}">		  
					   		<td><span id="original_id-label" class="property-label"><b>Concept ID: </b>${meshid2treenumInstance.tree_id.toString().replace("[", "") .replace("]", "") }</span></td>			          	 
					  		</g:if>
					  		<g:else>
					  		<td><span id="original_id-label" class="property-label"><b>Concept ID: </b>${conceptInstance.original_id}</span></td>
					  		</g:else>
					  	</g:if>
					  	<g:else>
					 	<td><span id="original_id-label" class="property-label"><b>Concept ID: </b>${conceptInstance.original_id}</span></td>
					 	</g:else>
					 </tr>
					  <tr>
					 <td width="40%"><span id="original_id-label" class="property-label"><b>Concept Type: </b>${conceptInstance.concept_types.getFullname()}</span></td>
					 <td><span id="original_id-label" class="property-label"><b>Number of Compounds: </b>${conceptInstance.num_compounds}</span></td>
					 </tr>
					 </table>	 
					 
					
					 <h1>Input Parameters</h1>
					
					<g:each in="${db}" status="i" var="test">	
					   	<% dbs = test+ ", "+ dbs %>	
					</g:each> 
					
					 <table id="result2" >
					 <tr>
					 <g:if test="${params.fil == 'qval'}">
					 <td width="40%"><span id="original_id-label" class="property-label"><b> q-Value < </b>  ${params.id2} </span></td>
					 </g:if>
					 <g:else>
					  <td width="40%"><span id="original_id-label" class="property-label"><b>P-Value < </b>${params.id2} </span></td>
					 </g:else>
					 <td><span id="original_id-label" class="property-label"><b>Odds Ratio >  </b>${params.odds}</span></td>
					 </tr>
					  <tr>
					 
					 <td colspan =2 ><span id="original_id-label" class="property-label"><b>Selected Annotation Databases: </b>${dbs}</span></td>
					
					 </tr>
					 </table>
					
					 
					
				
	</div>
	<br />
	<br />
	<br />
	<br />
	</div>


		
		<div  style="width: 250px;float:left;">
		  <export:formats formats="['csv', 'excel']" action="table" params="[q:"${params.q}", odds:"${params.odds }", id1:"1.45e-323",id2:"${params.id2 }",fil:"${params.fil }", statement:"${params.statement}", table:"table"]"
		 />
		</div>
 
			<table id="myTable" class="tablesorter"> 
			
				<thead>
					<tr>
						<th><g:message code="overlap" default="Index" /></th>
						<th><g:message code="overlap" default="Concept Name" /></th>
						<th><g:message code="overlap" default="Concept ID" /></th>
						<th data-placeholder="Exact matches only"><g:message code="overlap" default="Concept Type" /></th>
						<th><g:message code="overlap" default="P-value" /></th>
						<th><g:message code="overlap" default="q-Value" /></th>
						<th><g:message code="overlap" default="Overlap" /></th>
						<th><g:message code="odds" default="Odds Ratio" /></th>
					
					</tr>
				</thead>
				<tbody>
							
				<g:each in="${allR}" status="i" var="fileMap">
				 <tr>
				 <td>${i+1 }</td>
				 <td>${fileMap.name}</td>
				 <td><g:link controller="concepts" action="show" target="_blanck" id="${fileMap.id}" params="[id2: '0.05', odds: '0',fil:'qval']">${fileMap.conid}</g:link></td>				 	
				 <td>${fileMap.conTypes}</td>
				 <td id="pval" data-value="${-Math.log10(fileMap.pval)}">${fileMap.pval}</td>
				 <td id="qval" data-value="${-Math.log10(fileMap.qval)}">${fileMap.qval}</td>
			     <g:set var="id2" value="${fileMap.id}"/>					
				 <g:set var="id1" value="${params.q}"/>
				 <td><g:link action="displayOverlap" target="_blank" title="Shows overalapping compounds between two concepts" params="[id2: id2, id1: id1]">${fileMap.ins}</g:link></td>				
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
<center>			
<g:form action="redirectView" method="get" id="myform">   
 <g:hiddenField name="id2"  value="${params.id2 }"/>
	   <g:hiddenField name="id1"  value="1.45e-323"/>
	   <g:hiddenField name="q" value="${params.q}" />
	   <g:hiddenField name="odds" value="${params.odds}" />
	   <g:hiddenField name="fil" value="${params.fil}" />
	    <g:each in="${db}" status="i" var="test">	
			<input type="hidden" name="statement" value="${test}">	
		</g:each>
	   <g:submitButton name="network" value="Star Network" class="submit"/>	
	    <g:submitButton name="CompleteNetwork" value="Complete Network" class="submit"/>
	   <g:submitButton name="heatmap" value="Heatmap" class="submit"/>	 
	  
	   </g:form> 
</center>
		
	</body>
</html>
