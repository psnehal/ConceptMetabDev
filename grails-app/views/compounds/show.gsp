
<%@ page import="conceptmetab.Compounds" %>
<%@ page import="conceptmetab.Concept_types" %>
<%@ page import="conceptmetab.Meshid2treenum" %>


<!DOCTYPE html>
<html>
	<head>
	  <meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'compounds.label', default: 'Compounds')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>		
		 <g:javascript src="tablesorter/jquery-1.11.1.min.js" /> 
 		<g:javascript src="tablesorter/jquery.dataTables.min.js" />
 		<script>
		$(document).ready(function() {

			 $('#MyButton').click(function(){
				 checkboxes = document.getElementsByName('cname');
	    		 console.log(checkboxes.length);
	    		 for (var i =0, n=checkboxes.length;i<n;i++)
		   			  {
		   			  console.log("its iniside for loop");
		   			  checkboxes[i].checked = true;
		   			 
		   			  }
   		
			    });
			 $('#UnSelect').click(function(){
				 checkboxes = document.getElementsByName('cname');
	    		 console.log(checkboxes.length);
	    		 for (var i =0, n=checkboxes.length;i<n;i++)
		   			  {
		   			  console.log("its iniside for loop");
		   			  checkboxes[i].checked = false;
		   			 
		   			  }
   		
			    });
			    
		    // Setup - add a text input to each footer cell
		    $('#example tfoot th').each( function () {
		        var title = $('#example thead th').eq( $(this).index() ).text();
		        $(this).html( '<center><input type="text" placeholder="Search '+title+'" /><center>' );
		    } );
		 
		    // DataTable
		    var table = $('#example').DataTable(
					{  paging: false,
					    }
				    );


		    var showSpinner = function() {
		        $("#spinner").fadeIn('fast');
		    };

		    // Global handlers for AJAX events
		    $("#spinner").bind("ajaxSend", function() {
		        $(this).show();
		    }).bind("ajaxStop", function() {
		        $(this).hide();
		    }).bind("ajaxError", function() {
		        $(this).hide();
		    });
	        
		    // Apply the search
		  
		} );


		$.ajax
	    ({
	     type: "GET",
	     url: "http://jquery.com/jquery-wp-content/themes/jquery.com/i/feature-sprites.png",
	     dataType: 'image/png',
	     async: false,

	     success: function (data) {
	        console.log(data);
	     }

	     });


		function validation2(source)
	    {

	    	checkboxes = document.getElementsByName('cname');
			
			var check = 0;

			 for (var i =0, n=checkboxes.length;i<n;i++)
			  {
			  if(checkboxes[i].checked)
				  {

					check++;
					console.log(check);
				  }
			  }
			
			  if(check < 2)
				  {
					alert("Please select atleast two concepts to see results");
					return false;
					
				  }
			  else
				  {
				  return true;
				  }

	        }

		
		</script>
		 <r:require module="export"/>
		 


   
		 
	</head>
	<body>
	<div id="spinner"  class="spinner" style="display:none;">
    <g:img uri="/images/spinner.gif" alt="Loading..."/>    
	</div>
				<div id="show-concepts" class="content scaffold-show" role="main">
				
				 	<h1> Compound Information</h1>
				
				<div id ="title" style="width: 600px; border:1;float:left;">
				<div style="width:400px; float:left ;">
				<ol class="property-list concepts">	   
				    	<g:if test="${compoundsInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label">Name</span>
					<span class="property-value" aria-labelledby="original_id-label"><g:fieldValue bean="${compoundsInstance}" field="name"/></span>		
				</li>
				</g:if>
				
				<g:if test="${compoundsInstance.kegg_id == null ||  compoundsInstance.kegg_id.equals('NULL') }">
				<li class="fieldcontain">
					<span id="original_id-label" class="property-label">Kegg ID</span>
					<span class="comtable"> -- </span>
				</li>
				</g:if>
				<g:else>
				<li class="fieldcontain">
				<span id="original_id-label" class="property-label">Kegg ID</span></td>		
				<span class="comtable">${urlKegg}</span>
				</li>
				</g:else>
				
				<g:if test="${compoundsInstance.pubchem_id == null ||  compoundsInstance.pubchem_id.equals('NULL') }">
				<li class="fieldcontain">
				<span class="property-label">Pubchem ID</span></td>
				<span class="comtable">-- </span><
				</li>	
				</g:if>
				<g:else>
				<li class="fieldcontain">
				<span class="property-label">Pubchem ID</span>		
				<span class="comtable">${urlPubchem}</span>
				</li>
				</g:else>
				
				
				
				<g:if test="${compoundsInstance?.num_concepts}">	
				<li class="fieldcontain">	
				<span class="property-label"># of Concepts</span>
				<span class="comtable"><g:fieldValue bean="${compoundsInstance}" field="num_concepts"/></span>
				</g:if>
				</ol>
				</div>
				
				  <div style="width: 80px;float:left ;border = 1">
				           <br/><br/>		         
				           <% directLink = "http://metab2mesh.ncibi.org/?term="+"${compoundsInstance?.name} "+"&qtype=compound&exact=on&m2msearchbutton=Metab2MeSH+Search"  %>
				           <a href="<%=directLink %>" target="_blank"><img src="${resource(dir: 'images', file: 'Metab2Mesh.jpg')}" alt="Metab2mesh"  title="View metabolites annotated with this MeSH term" style="max-height: 150px; max-width: 150px;padding:5px;"/></a>
								<br/>
				  </div>
					        
					        
				</div>
				
					<div  style="margin-left: 620px;height:100%;border:1px; "> 
				<g:if test="${compoundsInstance.kegg_id == null ||  compoundsInstance.kegg_id.equals('NULL') }">
				<% 
				def pubchemid = compoundsInstance.pubchem_id.split(";")
				def pubcheckurl = "https://pubchem.ncbi.nlm.nih.gov/rest/pug/compound/CID/"+"${pubchemid.getAt(0)}"+"/PNG"
				
				%>	
				<img src="${pubcheckurl}" height="150" width="250"  border="1"/>
				<br/><span class="property-label"" style="font-size: .70em; margin-left: 20px;">Pubchem Structure</span>
				</g:if>
				<g:else>
				<%
				def keggid = compoundsInstance.kegg_id.split(";")	
				  def urlimage = "http://www.kegg.jp/Fig/compound/" +"${keggid.getAt(0)}"+".gif" %>
				<img src="${urlimage}" max-height="250" max-width="250"  border="1"/>	 
				<br/><span class="property-label"style="font-size: .70em; margin-left: 20px; ">KEGG Structure</span>	
				</g:else>   
					</div>
				</div>

	<div  style="height:100%;border:1px;clear:both;padding: 10px;">  
	
	<export:formats formats="['csv', 'excel']" action="show" id="${compoundsInstance.id}" params="[id:"${compoundsInstance.id}",id2: '0.05', odds: '0',fil:'qval']"/>
	<g:form action="conceptCompleteNetwork" method="get" id="myform" onsubmit="return validation2()"> 
			<input type="button" value="Select All" id="MyButton" class="submitsmall" >
			<input type="button" value="Select None" id="UnSelect" class="submitsmall" >
			  <g:hiddenField name="compoundsId" value="${compoundsInstance.id}" />
			<g:submitButton name="network" value="Complete Network" class="submitsmall"/>
			
			<table id="example" class="display" cellspacing="0" width="100%"> 				
					<thead>
					<tr>
					<th><g:message code="overlap" default="Index" /></th>
					<th><g:message code="overlap" default="Concept Name" /></th>
					<th><g:message code="overlap" default="Concept ID" /></th>				
				    <th data-placeholder="Exact matches only"><g:message code="overlap" default="Concept Type" /></th>
					<th><g:message code="overlap" default="Concept Size" /></th>
					<th><g:message code="overlap" default="# of Enrichments" /></th>					
					</tr>		
					</thead>
					<tbody>
					<g:each in="${conceptsInstance}" status="i" var="concept">
						<tr>
						<g:set var="cnm" value="${concept.conTyp}"/>
							
							<td>${i+1}</td>
							<td><g:checkBox name="cname" value="${concept.id}" checked="false" />${concept.name.capitalize()}  </td>			 				
							 <td><g:link controller="concepts" action="show" id="${concept.id}" target="_blank" params="[id2: '0.05', odds: '0',fil:'qval']">${concept.conid }</g:link></td>
							 <td>${cnm} </td>										
							<td>${concept.numCom}</td>					
							<td>${concept.numEnc}</td>					
						</tr>
					</g:each>
					</tbody>
				</table>
				</g:form>
	
	</div>


	
	</body>
</html>
