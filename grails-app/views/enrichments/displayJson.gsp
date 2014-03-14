<%@ page import="conceptmetab.Enrichments" %>
<!DOCTYPE html>
<html>
<head>

<title>Enrichments</title>
  
</head>


<body> 	
  <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
    
    <div>
     
					<table>
					<thead>
					<tr>
					<th>Compound id</th>
					<th>Compound Name</th>					
					</thead>
					<tbody>
					
				<%def searchResults = EnrichedConcepts() %>	
                    <g:each in="${ searchResults}" >
                    <tr>
                    
                        <td>${ it.compound.id}</td>
                        <td>${it.compound.name }</td>
                        <tr>
                    </g:each>
                   </tbody> 
                   
	 </table>
	</div> 

    <div class="body">
      <div class="list">
        <g:each in="${searchResults}" status="i" var="compounds">

        <div class="compounds">
          <h3>
            <g:link action="show"
                    id="${compound_id}">${compound_id}</g:link>
          </h3>
          
        </div>

        </g:each>
      </div>
    </div>
   
    <export:formats formats="['csv', 'excel']" params="[q:"${params.q}"]" />
        </body>
        </html>