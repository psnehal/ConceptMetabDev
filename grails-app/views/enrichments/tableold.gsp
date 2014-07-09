
<%@ page import="conceptmetab.Enrichments" %>
<%@ page import="conceptmetab.Meshid2treenum" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		   <g:javascript library='jquery' />
		 <g:javascript src="table-sort-master/jquery-1.8.3.min.js" /> 
		  <g:javascript src="table-sort-master/jquery.ay-table-sort.js" /> 
		
 	
		
		<script type="text/javascript">
		$(function(){
			$.ay.tableSort({target: $('table'), debug: true});
		});
		</script>
		
		 <r:require module="export"/>
   
	</head>
	<body>
	
	 <g:set var="id2" value="${params.id2}"/>
	<g:set var="odds" value="${params.odds}"/>
				<g:set var="fil" value="${params.fil}"/>
				<g:link controller="concepts" action="show" id="${params.q}" params="[id2: id2, odds: odds,fil:fil]">Go Back to Filter Page</g:link></td>
				
	 <export:formats formats="['csv', 'excel']" action="table" params="[q:"${params.q}", odds:"${params.odds }", id1:"1.45e-323",id2:"${params.id2 }",fil:"${params.fil }", statement:"${params.statement}", table:"table"]" />
	 
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
	          <%def meshid2treenumInstance =Meshid2treenum.findAllWhere(mesh_id : conceptInstance.original_id ).tree_id %>
	          <li class= "fieldcontain">
	          	 <span class="property-label">Concept Id:</span>
	          	 <span class="property-value">${meshid2treenumInstance.toString().replace("[", "") .replace("]", "") } </span></li>	           
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


 
			<table>
		<thead>
					<tr>
						<th><g:message code="overlap" default="Index" /></th>
						 <th class="ay-sort"><g:message code="overlap" default="Concept Name" /></th>
						 <th class="ay-sort">><g:message code="overlap" default="Concept ID" /></th>
						 <th class="ay-sort">><g:message code="overlap" default="Concept Type" /></th>
						 <th class="ay-sort"><g:message code="overlap" default="P-value" /></th>
						 <th class="ay-sort"><g:message code="overlap" default="Q-Value" /></th>
						 <th class="ay-sort"><g:message code="overlap" default="Overlap" /></th>
						 
						 <g:sortableColumn property="odds" defaultOrder="desc"
                  title="odds ratio" titleKey="allR.odds" />
					
					</tr>
				</thead>
				<tbody>
							
				<g:each in="${allR}" status="i" var="fileMap">
				 <tr>
				 <td>${i+1 }</td>
				 	<td>${fileMap.name}</td>				 	
				  	<td>${fileMap.conid}</td>				  	
				   	<td>${fileMap.conTypes}</td>
				    <td>${fileMap.pval}</td>
				    <td>${fileMap.qval}</td>
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
 