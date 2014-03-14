
<%@ page import="conceptmetab.Concepts" %>
<%@ page import="conceptmetab.Concept_types" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'concepts.label', default: 'Concepts')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
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
	
		<a href="#list-concepts" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		
		<div class ="searchm" align="left">
		 <g:form action="search" method="get">
	            <g:textField name="q" id="city" value="${params.q}"/>
	            <g:submitButton name="search"/>
	        </g:form>		
		</div>
		
		<div id="list-concepts" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="original_id" title="${message(code: 'concepts.original_id.label', default: 'Originalid')}" />
					
					
						<g:sortableColumn property="concept_name" title="Conceptname" />
						<g:sortableColumn property="name" title="${message(code: 'concepts.name.label', default: 'Name')}" />
					
						<g:sortableColumn property="num_compounds" title="${message(code: 'concepts.num_compounds.label', default: 'Numcompounds')}" />
					
						<g:sortableColumn property="num_enriched" title="${message(code: 'concepts.num_enriched.label', default: 'Numenriched')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${conceptsInstanceList}" status="i" var="conceptsInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${conceptsInstance.id}">${fieldValue(bean: conceptsInstance, field: "original_id")}</g:link></td>
								
						 <g:set var="cnm" value="${conceptsInstance.concept_types}"/>						
						<td>${cnm} </td>
						
						
						<td>${fieldValue(bean: conceptsInstance, field: "name")}</td>
					
						<td>${fieldValue(bean: conceptsInstance, field: "num_compounds")}</td>
					
						<td>${fieldValue(bean: conceptsInstance, field: "num_enriched")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${conceptsInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
