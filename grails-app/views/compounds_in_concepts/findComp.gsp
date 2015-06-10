<%@ page import="conceptmetab.Concepts" %>
<%@ page import="conceptmetab.Concept_types" %>
<!DOCTYPE html>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
     <script src="http://code.jquery.com/jquery-1.8.3.js"></script>
        <script src="http://code.jquery.com/ui/1.10.0/jquery-ui.js"></script> 
        	 <g:javascript src="tablesorter/jquery-latest.js" /> 
 		<g:javascript src="tablesorter/jquery.tablesorter.js" />
    <meta name="layout" content="main" />
    <title>Compounds in ${concept.name }</title>
    <r:require module="export"/>
    <export:resource />
    <script>
    $(document).ready(function() {
    $("#myTable").tablesorter(); 			
		});  
		


    </script>
  </head>
  <body>
  <br/><br/>
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
  
    <div>
    <center>
    	 <div>
	     <g:if test="${colName.equals('kegg')}">
		   <% url = "http://metscape.med.umich.edu/cytoscape.php?cids="+"${map.values().keid.toString().replace(" ", "").replaceAll(";",",").replaceAll("\\[", "").replaceAll("\\]","")}"+"&networkType=C-G"  %>
		   <a href="<%=url %>" target="_blank">Open Compound-Gene Network in Metscape</a>     
		 </g:if>	     
		</div>    
    </center>
      <div style="width: 200px;float:left;">
		 <export:formats formats="['csv', 'excel']" params="[q:"${params.q}"]"/>
	  </div>
	 	
	</div>	
   

    <g:set var="i" value="1"/>
     
					<table id="myTable" class="tablesorter" >
					<thead>
					<tr>					
					<th>Compound Name</th>
					<th>Pubchem ID</th>
					<th>Kegg ID</th>							
					</thead>
					<tbody>
                    <g:each in="${ map}" status="i" var="var" >
                   
                    <tr>
                          
                         <td style="word-wrap: break-word; cellpadding:100px" >${var.value.names }</td>
                         <g:if test="${var.value.pubid == null ||  var.value.pubid.equals('NULL')  }"> <td>--</td></g:if>                         
                        	<g:else><td style="word-wrap: break-word;" >  ${var.value.pubidurl }</td></g:else>
                       	<g:if test="${var.value.keid == null ||  var.value.keid.equals('NULL')  }"> <td>--</td></g:if>     
                                      
                        	<g:else><td style="word-wrap: break-word;" > ${var.value.keidurl }</td></g:else>
                    </tr>
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