<%@ page import="conceptmetab.Concepts" %>
<%@ page import="conceptmetab.Concept_types" %>
<!DOCTYPE html>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main" />
    <title>ConceptMetab</title>
    <r:require module="export"/>
    <export:resource />
  </head>
  <body>
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
    
    <div>
     <br/><br/>
     <center> <export:formats formats="['csv', 'excel']" params="[q:"${params.q}"]" />
	   <% url = "http://portal.ncibi.org/cytoscape/3/cytoscape.php?cids="+"${map.values().keid}"+"&networkType=C-G"  %>
	                     <a href="<%=url %>" target="_blank">  Open Compound gene network in MetScape</a>      
      </center>
    <g:set var="i" value="1"/>
     
					<table>
					<thead>
					<tr>
					<th>Index</th>
					<th>Compound Name</th>
					<th>Pubchem id</th>
					<th>Kegg id</th>		
							
					</thead>
					<tbody>
                    <g:each in="${ map}" status="i" var="var" >
                   
                    <tr>
                          <td>${i+1 }</td>       
                       
                        <td>${var.value.names }</td>
                        <td>${var.value.pubid }</td>
                        <td>${var.value.keid }</td>
                        
                        <tr>
                    </g:each>
                   </tbody> 
                   
	 </table>
	</div> 
	

	</table>

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
   
    
  </body>
</html>