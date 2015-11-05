
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
		
			<g:form action="search" method="get">        
		<div class ="searchm" align="center">
	          
                  <span class="formText">
                  Search By :<g:radioGroup name="fil" id="radio" values="['concept','compound']" value="concept" labels = "['Concept','Compound']" onClick="doExport()">
                        ${it.radio} <g:message code="${it.label}" />
                        </g:radioGroup></span>
                         <span class="formText"><g:checkBox name="true" value="Exact Match" checked="false" />Exact Match </span>
                         <br/>
                        <g:textField name="q" id="city" value="${params.q}"/>
                        <g:submitButton name="Search" class="submit"/>
                        <br/>
         </div>  
         <div class ="searchm" align="center">             
                       
          </div>           
        
		
		
		 </g:form> 
		
		
		
		<div id="list-concept_types" class="content scaffold-list" role="main">
			<h1>Browse Concepts</h1>
		
			<div class="datagrid">
			<table id = "basic">
				<thead>
					<tr>
					<th rowspan="2">Concept Type</th>					
					<th rowspan="2">Number of Concepts</th>
					<th rowspan="2">Average Concept Size</th>		
					<th colspan="2" >Range of Concept Size</th>							
					</tr>
					<tr><th>Min</th><th>Max</th></tr>
					
				</thead>
				<tbody>
				
				 
                    <g:each in="${ id1}" >
   
                <tr>
                    	<td>
						 	<g:set var="cnm" value="${it.ctypes}"/>
						 	<g:link controller="Concepts" action="dbspecific" params="[name:cnm, offset:'0']">${cnm}</g:link>
                        </td>
					<td>${it.count}</td>
					<g:set var="link" value="${createLink(action:'intro')}#${cnm}"/>
					<td>${it.ctypes.mean}</td>
					<td>${it.ctypes.min}</td>
					<td>${it.ctypes.max}</td>
                       
                        <tr>
                    </g:each>
                   </tbody> 
                   
	 </table>
		</div>	
		
		</div>
		<div class="nav" role="navigation">
			<ul>
			 <li><a class="intro"  href="${createLink(uri:'/concepts/intro')}">About</a></li>
			 <li><a class="intro"  href="${createLink(uri:'/concepts/demo')}">Reference</a></li>
			<!-- -  <li><a class="intro"  href="${createLink(uri:'/concepts/contact_us')}">Contact Us</a></li>-->
			 <li><a class="intro"  href="${createLink(uri:'/concepts/tutorial')}">Manual</a></li>
			</ul>
		</div>
		<br/>
		<span class="text" STYLE="font-size: 10pt;margin-left: 0.5cm;"> For support and questions email: conceptmetab-help@umich.edu</span> 
		<br/>
		<span class="text" STYLE="font-size: 10pt;margin-left: 0.5cm;"> Version Updates <a class="log"  href="${createLink(uri:'/concepts/log')}">log:</a></span>
		<center>	
		<img src="${resource(dir: 'images', file: 'dcmb.jpg')}" alt="DCMB"/>	<br />	<br />
					
					<span class="text" STYLE="font-size: 10pt">
						Copyright 2015 The University of Michigan 
						<br />
						Developed under the support of 
						<br />
						Grant #U24 DK097153 & #T32 HG00040
						<br />
						</a>
					<a href="${resource(dir: 'pdf', file: 'TermsOFUse.pdf')}" ><span class="textSmallLink">Terms of Use</span></a>	
					</span>
					</center>
	</body>
</html>
