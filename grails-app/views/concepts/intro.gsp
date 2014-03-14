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

Conceptmetab information will come here
</body>
</html>