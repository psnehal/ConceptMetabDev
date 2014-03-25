<%@ page import="conceptmetab.Concepts" %>
<%@ page import="conceptmetab.Compounds" %>
<%@ page import="conceptmetab.Compounds_in_concepts" %>

<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main">
        <g:set var="entityName" value="${message(code: 'concepts.label', default: 'Concepts')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
        <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
        <script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
        <tooltip:resources/>
    </head>
    <body>
        <div id="show-concepts" class="content scaffold-show" role="main">
      
	            <g:if test="${flash.message}">
	            <div class="message" role="status">${flash.message}</div>
	            </g:if>
	            <div id ="title" style="width: 676px; height: 250px;float:left ;border = 1">
	             <h1>Concept Information</h1>
	             
	            <ol class="property-list concepts">	            
	                <g:if test="${conceptsInstance?.name}">
	                <li class="fieldcontain">
	                    <span id="name-label" class="property-label"><g:message code="concepts.name.label" default="Name" /></span>
	                    <g:set var="cnm" value="${conceptsInstance?.id}"/>                        
	                    <span class="property-value" aria-labelledby="original_id-label">    <g:link controller="Enrichments" action="filterSlider" params="[id1:1e-45,id2:0.01,q:cnm,fil:'qval' ]"><b>${conceptsInstance?.name.capitalize()}</b></g:link></span>
	                </li>
	                </g:if>  
	                <g:if test= "${conceptsInstance?.original_id}">
	                    <li class="fieldcontain">
	                        <span id="original_id-label" class="property-label"><g:message code="concepts.original_id.label" default="Concept Id" /></span>
	                        <g:set var="ctype" value="${conceptsInstance.concept_types}"/>    
	                        
	                         <a href="${url}" target="_blank">                    
	                         <span class="property-value" aria-labelledby="original_id-label"><g:fieldValue bean="${conceptsInstance}" field="original_id"/></span>    </a>
	                    </li>
	                    <li class="fieldcontain">
	                        <span id="ctypes-label" class="property-label"><g:message code="concepts.ctypes.label" default="Concept Type" /></span>
	                        <span class="property-value" aria-labelledby="ctypes-label">${conceptsInstance.concept_types}</span>
	                    </li>
	                </g:if>
	                <g:if test="${conceptsInstance?.num_compounds}">
	                <li class="fieldcontain">
	                    <span id="num_compounds-label" class="property-label"># of Compounds</span>
	                    <g:set var="cnm" value="${conceptsInstance?.id}"/>    
	                    <span class="property-value" aria-labelledby="num_compounds-label">    <g:link controller="Compounds_in_concepts" action="findComp" params="[q:cnm]"><g:fieldValue bean="${conceptsInstance}" field="num_compounds" target="_blank"/></g:link></span>
	                </li> 
	                </g:if>
	            </ol>
	            </div>
	            <div id ="title" style="width: 250px; height: 250px; float:right ; ">          
	                  <g:form action="show" method="get">
	                   <ol class="property-list concepts">
	                   <li class= "fieldcontain">
	                   <tooltip:tip value="Benjamini-Hochberg FDR correction"> Q-value</tooltip:tip>
	                   <span class="filter-label"><g:radioGroup name="fil" values="['pval','qval']" value="qval" labels = "['P-value','Q-value']">
	                        ${it.radio} <g:message code="${it.label}" />
	                        </g:radioGroup>  </span>
	                         <span class="filter-label"><g:textField name="id2" id="id2" value="${params.id2 }"/></span>
	                   </li>
	                  <li class= "fieldcontain">      
	                    <span class="filter-label">Odds Ratio</span><br/>
	                        <span class="filter-label"> <g:textField name="odds" id="odds" value="${params.odds}"/></span>
	                        <g:hiddenField name="id" value="${params.id}" />
	                  </li>
	                  <li class= "fieldcontain">
	                      <span class="formText"> <g:submitButton name="  Filter Database chart  " class="submit"/></span>
	                  </li>
	                  </ol>
	                </g:form>
	            </div>
         
               <br/> <br/>
		        <div id ="fieldcontain" style="width: 100%; float:left ; ">   
		          <br/> <br/>         
		              <g:set var="id2" value="${params?.id2}"/>    
		              <g:set var="odds" value="${params?.odds}"/>    
		              <g:set var="fil" value="${params?.fil}"/>  
		               <g:include controller="enrichments" action=  "createChart" params="[id1:1e-45,id2:id2,odds:odds,q:cnm,fil:fil, statement :['all']]"/>
		         </div>
           
           </div> 
    </body>
</html>