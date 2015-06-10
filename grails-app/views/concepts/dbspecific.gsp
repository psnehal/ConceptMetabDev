<%@ page import="conceptmetab.Concepts" %>
<%@ page import="conceptmetab.Concept_types" %>
<%@ page import="conceptmetab.Meshid2treenum" %>
<!DOCTYPE html>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main" />
    <title>Concepts for ${name}</title>
    	 <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.0/themes/base/jquery-ui.css" />
		 <script src="http://code.jquery.com/jquery-1.8.3.js"></script>
        <script src="http://code.jquery.com/ui/1.10.0/jquery-ui.js"></script> 
        	 <g:javascript src="tablesorter/jquery-latest.js" /> 
 		<g:javascript src="tablesorter/jquery.tablesorter.js" />
        
        <g:javascript>
         function doExport() {
         
			  $('#city').autocomplete({   			    		  
			            source: '<g:createLink controller="Concepts" action="ajaxFindCity" params="[radio:'concept']"/>' });
	 			 
			   jQuery(function () {
	                jQuery("[name='passsel']").submit(function () {
	                    jQuery("[name='id']").val(id);
	                });
	            }); 
			    
			    
			  
			}
			
            $(document).ready(function() {
			 	$.tablesorter.addParser({
					  id: "fancyNumber",
					  is: function(s) {
					    return /^[0-9]?[0-9,\.]*$/.test(s);
					  },
					  format: function(s) {
					    return jQuery.tablesorter.formatFloat( s.replace(/,/g,'') );
					  },
					  type: "numeric"
				});                
                 $("#myTable").tablesorter({
			        			 headers: {
			        			        3: {
			        			            sorter: 'fancyNumber'
			        			        },
			        			         4: {
			        			            sorter: 'fancyNumber'
			        			        }
			        			        
			        		 }
			        }); 
			   
              
            });       
        </g:javascript>
  </head>
  <body>
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
	<g:set var="ct" value="${params.name}"/>
    
  
		
  	<div id="list-concepts" class="content scaffold-list" role="main">		
	<div class="pagination">
	  		
				<g:paginate  max="500" params='['name':"${ct}"]'  total="${resultcount}" />
			</div>
			
	

</div>
			<br/>
    <div id="list-concepts" class="content scaffold-list" role="main">
		<table id="myTable" class="tablesorter"> 
			
				<thead>
					<tr>
						<!--  <th><g:message code="overlap" default="Index" /></th>-->
						<th><g:message code="overlap" default="Concept Name" /></th>
						<th><g:message code="overlap" default="Concept ID" /></th>
						<th><g:message code="overlap" default="Concept Type" /></th>
						
						<th><g:message code="overlap" default="Concept Size" /></th>
						<th class=\"{sorter: 'fancyNumber'}\"><g:message code="overlap" default="# of Enrichments" /></th>
						
					</tr>
				</thead>
				<tbody>
				<g:each in="${result}" status="i" var="conceptsInstance">
					<tr>
					<g:set var="counter" value="${params.offset?:0+i+1}"/>		
						<!--  <td> ${i+1} </td>	-->
							<td>${fieldValue(bean: conceptsInstance, field: "name").capitalize()}</td>						
						<g:set var="cnm" value="${conceptsInstance.concept_types.fullname}"/>						
						 <g:if test="${conceptsInstance.concept_types.fullname.contains("MeSH")}">		
						  		   <%def meshid2treenumInstance =Meshid2treenum.findAllWhere(mesh_id : conceptsInstance.original_id ) %>
						  		  
						  	<g:if test="${meshid2treenumInstance.size() != 0}">		  
						  
						  		  <td><g:link action="show" id="${conceptsInstance.id}" params="[id2: '0.05', odds: '0',fil:'qval']">${meshid2treenumInstance.get(0).tree_id}</g:link></td>
						  	</g:if>
						  	<g:else>					
						  	<td><g:link action="show" id="${conceptsInstance.id}" params="[id2: '0.05', odds: '0',fil:'qval']">${conceptsInstance.original_id }</g:link></td>
						  		</g:else>	  
						  </g:if>
						  <g:else>
			<td><g:link action="show" id="${conceptsInstance.id}" params="[id2: '0.05', odds: '0',fil:'qval']">${conceptsInstance.original_id }</g:link></td>
						</g:else>
						 <td>${cnm} </td>
									
						<td>${fieldValue(bean: conceptsInstance, field: "num_compounds")}</td>					
						<td>${fieldValue(bean: conceptsInstance, field: "num_enriched")}</td>					
					</tr>
				</g:each>
				</tbody>
			</table>
		
			<g:set var="maxs" value="${resultcount/100}"/>		
	<div id="list-concepts" class="content scaffold-list" role="main">		
	<div class="pagination">
	  		
				<g:paginate  max="500" params='['name':"${ct}"]'  total="${resultcount}" />
			</div>
			
	

</div>
	

			
</body>
</html>