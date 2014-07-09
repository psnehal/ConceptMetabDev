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
     <center> 
     <export:formats formats="['csv', 'excel']" params="[q:"${params.q}"]" />
     <g:if test="${colName.equals('kegg')}">
	   <% url = "http://portal.ncibi.org/cytoscape/3/cytoscape.php?cids="+"${map.values().keid}"+"&networkType=C-G"  %>
	   <a href="<%=url %>" target="_blank">  Open Compound gene network in MetScape</a>     
	           </g:if>           
      </center>
    <g:set var="i" value="1"/>
     
					<table>
					<thead>
					<tr>
					<th>Index</th>
					<th width ='50%'>Compound Name</th>
					<th width ='20%'>Pubchem ID</th>
					<th width ='20%'>Kegg ID</th>		
							
					</thead>
					<tbody>
                    <g:each in="${ map}" status="i" var="var" >
                   
                    <tr>
                         <td>${i+1 }</td>       
                         <td width ='50%'>${var.value.names }</td>
                         <g:if test="${var.value.pubid == null ||  var.value.pubid.equals('NULL')  }"> <td>--</td></g:if>                         
                        	<g:else><td width ='20%'><a href="http://pubchem.ncbi.nlm.nih.gov/summary/summary.cgi?cid=${var.value.pubid }"  target="_blank">  ${var.value.pubid }</a></td></g:else>
                       	<g:if test="${var.value.keid == null ||  var.value.keid.equals('NULL')  }"> <td>--</td></g:if>     
                                      
                        	<g:else><td width ='20%'> <a href=" http://www.kegg.jp/dbget-bin/www_bget?cpd:${var.value.keid }" target="_blank"> ${var.value.keid }</a></td></g:else>
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