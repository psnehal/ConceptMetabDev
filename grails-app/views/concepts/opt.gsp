<%@ page import="conceptmetab.Concepts" %>
<%@ page import="conceptmetab.Concept_types" %>
<!DOCTYPE html>


<html>
    <head>
        <meta name="layout" content="main" />

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
        <div class="demo">
            <div class="ui-widget">
                <label for="city">Concepts: </label>
                <input id="city">
                 <g:form action="search" method="get">
	            <g:textField name="q" id="city" value="${params.q}"/>
	            <g:submitButton name="search"/>
	        </g:form>
            </div>
        </div>
        
       
    </body>
</html>
