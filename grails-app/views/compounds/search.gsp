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
        <g:each in="${searchResults}" status="i" var="compounds">

        <div class="compounds">
          <h3>
            <g:link action="show"
                    id="${compounds.id}">${compounds.display_id}</g:link>
          </h3>
         
          </br>
          <br>
          
          
          
		
          
          
          
        </div>

        </g:each>
      </div>
    </div>
    <div class="paginateButtons">
      <g:paginate total="${resultCount}" params="${flash}"/>
    </div>
  </body>
</html>