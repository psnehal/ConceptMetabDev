<%@ page import="conceptmetab.Concepts" %>
<%@ page import="conceptmetab.Compounds" %>
<%@ page import="conceptmetab.Concept_types" %>
<%@ page import="conceptmetab.Meshid2treenum" %>
<!DOCTYPE html>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main" />
    <title>${filter.capitalize()} Search Results</title>
  </head>
  <body>
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>

    <div class="body">
      <div class="list">
      <ol class="property-list concepts">
      <g:if test="${filter == 'concept'}">
     	<table id = "basic">
	        <g:each in="${searchResults}" status="i" var="concepts">
	        	<tr><td> ${i+1 }. <g:link action="show"id="${concepts.id}" params="[id2: '0.05', odds: '0',fil:'qval']"><b>${concepts.name.capitalize()}</b></g:link>
	        	 <g:if test="${concepts.concept_types.fullname.contains("MeSH")}">
	        	 		<%def meshid2treenumInstance =Meshid2treenum.findAllWhere(mesh_id : concepts.original_id ) %>
	        	 		 <g:if test="${meshid2treenumInstance.size() != 0}">	
						  		    <br/><span class="filter-label"> ${meshid2treenumInstance.get(0).tree_id}  (${concepts.concept_types.fullname})</span><br/><br/></td></tr>
						 </g:if>
						 <g:else>					
						  	<br/><span class="filter-label"> ${concepts.original_id}  (${concepts.concept_types.fullname})</span><br/><br/></td></tr>
						 </g:else>
	        	 </g:if>
	        	 <g:else>	        	 
	        	<br/><span class="filter-label"> ${concepts.original_id}  (${concepts.concept_types.fullname})</span><br/><br/></td></tr>
	        	</g:else>
	        </g:each>
	 </table>
      </g:if>
      <g:else>
	       <table id = "basic">
		        <g:each in="${searchResults}" status="i" var="comp">
		        	<tr><td> ${i+1 }. <g:link controller="Compounds" action="show" id="${comp.id}" params="[id2: '0.05', odds: '0',fil:'qval']"><b>${comp.name}</b></g:link>
		        	<br/><span class="filter-label-mod"> # of Concepts: ${comp.num_concepts}</span><br/><br/></td></tr>
		            
		        </g:each>
		 </table>
	  </g:else>
      </ol>
      </div>
    </div>
 
    <div class="pagination">
	  		
				<g:paginate  max="50" params='${params}'  total="${resultCount}" />
			</div>
			
  </body>
</html>