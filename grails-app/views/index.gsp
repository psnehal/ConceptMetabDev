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
				
				<li><a class="intro"  href="${createLink(uri:'/concepts/intro')}">Information/About</a></li>
			
			</ul>
		</div>
		
		<div class ="search" align="center">
		 <g:form controller="Concepts" action="search" method="get">
	            <g:textField name="q" id="city" value="${params.q}"/>
	            <g:submitButton name="Search Concepts"/>
	        </g:form>	
	     </div>
	     <div>
	     <center>   
	      	<g:include controller="concept_types" action=  "list" />
	      	</center>   	
		</div>
		</body>
</html>