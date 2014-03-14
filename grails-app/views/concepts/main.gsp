
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
        <script>
        var sel ;
        function doExport() {
			  var id= $('input:radio[name=fil]:checked').val();

			  if(id == "concept")
			{
						  $('#city').autocomplete({   			    		  
						            source: '<g:createLink controller="Concepts" action="ajaxFindCity" params="[radio:'concept']"/>' });
				  }

			  else
				  {
				  $('#city').autocomplete({     	            	
		    		
				            source: '<g:createLink controller="Concepts" action="ajaxFindCity" params="[radio:'compound']"/>'
             			 });
				  }

			   jQuery(function () {
	                jQuery("[name='passsel']").submit(function () {
	                    jQuery("[name='id']").val(id);
	                });
	            }); 
			    
			    
			  
			}

        $(document).ready(function() {
            
          
        	  var select = doExport()
          
             
            
            
          });  

        </script>
        
        <g:javascript>
      
			
			
			

                
        </g:javascript>
	</head>
	<body>
		<br /> <br /> 
		<a href="#list-concepts" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
					<li><a class="intro"  href="${createLink(uri:'/concepts/intro')}">About</a></li>
			</ul>
		</div>
		
		<div class ="searchm" align="center">
		<g:form action="search" method="get">                  
                  <span class="formText">Search By :<g:radioGroup name="fil" id="radio" values="['concept','compound']" value="concept" labels = "['Concepts','Compounds']" onClick="doExport()">
                        ${it.radio} <g:message code="${it.label}" />
                        </g:radioGroup></span>
                        <g:textField name="q" id="city" value="${params.q}"/>
                        <g:submitButton name="Show" class="submit"/>
                     
         </g:form> 
		
		</div>
		
		
		
		<div id="list-concept_types" class="content scaffold-list" role="main">
			<h1>Browse Concepts</h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			
			<table id = "basic">
				<thead>
					<tr>
					<th>Concept Type</th>					
					<th>Number of Concepts</th>
					<th>Information</th>				
					</tr>
				</thead>
				<tbody>
                    <g:each in="${ id1}" >
                    <tr>
                    	<td>
						 	<g:set var="cnm" value="${it.ctypes}"/>
						 	<g:link controller="Concepts" action="dbspecific" params="[name:cnm]">${cnm}</g:link>
                        </td>
					<td>${it.count}</td>
					<td> <a href="#"><img src="${createLinkTo(dir: 'images/skin', file: 'information.png')}"  alt="Grails"/></a></td>
                       
                        <tr>
                    </g:each>
                   </tbody> 
                   
	 </table>
			
		
		</div>
	</body>
</html>
