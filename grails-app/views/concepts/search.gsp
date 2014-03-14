<%@ page import="conceptmetab.Concepts" %>
<%@ page import="conceptmetab.Concept_types" %>
<!DOCTYPE html>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main" />
    <title>ConceptMetab</title>
  </head>
  <body>
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>

    <div class="body">
      <div class="list">
      <ol class="property-list concepts">
      <g:if test="${filter == 'concept'}">
	        <g:each in="${searchResults}" status="i" var="concepts">
	        <li class="fieldcontain">
	          <h3><g:link action="show"id="${concepts.id}" params="[id2: '0.05', odds: '0',fil:'qval']">${concepts.name.capitalize()}</g:link></h3>          
	    	  <span class="filter-label"> ${concepts.original_id}  (${concepts.concept_types.fullname})</span><br/><br/>	     
	        </g:each>
      </g:if>
      <g:else>
	      	<g:each in="${searchResults}" status="i" var="comp">
	        <div class="concepts">
	        <h3><g:link controller="Compounds" action="show" id="${comp.id}" params="[id2: '0.05', odds: '0',fil:'qval']">${comp.name}</g:link></h3>         
			<p>${comp.name}</p> 
	        </div>
	        </g:each>
      </g:else>
      </ol>
      </div>
    </div>
    <div class="paginateButtons">
      <g:paginate total="${resultCount}" params="${flash}"/>
    </div>
  </body>
</html>