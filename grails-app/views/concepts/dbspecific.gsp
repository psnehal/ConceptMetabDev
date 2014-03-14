<%@ page import="conceptmetab.Concepts" %>
<%@ page import="conceptmetab.Concept_types" %>
<!DOCTYPE html>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main" />
    <title>ConceptMetab</title>
    	 <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.0/themes/base/jquery-ui.css" />
		 <script src="http://code.jquery.com/jquery-1.8.3.js"></script>
        <script src="http://code.jquery.com/ui/1.10.0/jquery-ui.js"></script> 
        
        <g:javascript>
            $(document).ready(function() {
               $('#city').autocomplete({
                      source: '<g:createLink controller="Concepts" action="ajaxFindCity"/>'
                  
                });
              
            });       
        </g:javascript>
  </head>
  <body>
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>

    
    <div class ="searchm" align="center">
		 <g:form action="search" method="get">
	            <g:textField name="q" id="city" value="${params.q}"/>
	            <g:submitButton name="search"/>
	        </g:form>		
	</div>
		
    
    <div id="list-concepts" class="content scaffold-list" role="main">
		<table>						
				<thead>
				<tr>
				
					<g:sortableColumn property="index" title="${message(code: 'concepts.original_id.label', default: 'Index')}"   />
					<g:sortableColumn property="original_id" title ="External Concept Id" params="[name:"${params.name}"]"  />
					<th>Concept Type</th>
					<g:sortableColumn property="name" title ="Concept Name" params="[name:"${params.name}"]" />
					<g:sortableColumn property="num_compounds" title="${message(code: 'concepts.num_compounds.label', default: 'Concept Size')}"   params="[name:"${params.name}"]" />
					<g:sortableColumn property="num_enriched" title ="# of Enrichments" params="[name:"${params.name}"]" />
				
				</tr>
				</thead>
				<tbody>
				<g:each in="${b}" status="i" var="conceptsInstance">
					<tr>
						<td> ${i+1} </td>
						<td><g:link action="show" id="${conceptsInstance.id}" params="[id2: '0.05', odds: '0',fil:'qval']">${fieldValue(bean: conceptsInstance, field: "original_id")}</g:link></td>
						<g:set var="cnm" value="${conceptsInstance.concept_types.fullname}"/>		
						 <td>${cnm} </td>
						<td>${fieldValue(bean: conceptsInstance, field: "name").capitalize()}</td>					
						<td>${fieldValue(bean: conceptsInstance, field: "num_compounds")}</td>					
						<td>${fieldValue(bean: conceptsInstance, field: "num_enriched")}</td>					
					</tr>
				</g:each>
				</tbody>
			</table>
	</div>
			
</body>
</html>