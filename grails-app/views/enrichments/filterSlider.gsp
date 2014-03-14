<%@ page contentType="text/html;charset=ISO-8859-1" %>
<html lang="en">
<head>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<link rel="stylesheet" href="${resource(dir: 'css', file: 'createChart.css')}" type="text/css">
<title>Enrichments</title>
<script>
$(function() {


			$( "#tabsF" ).tabs();		
			var first = getUrlVars()["id1"];
		    var second = getUrlVars()["id2"];
		    var odds = getUrlVars()["odds"];
		 
		    var databases = []
		    databases = getUrlList();	    	
	    	console.log("Statemet out "+databases);
		    var max = -(Math.log(second) / Math.log(10));

		    console.log("Second is"+ second+"max is "+ max);
		    var q = getUrlVars()["q"];
		   console.log("q ="+ q);

		   $("#slidertest").slider({
			   
			    range: "min",
			    min: 0.01,
			    max: 400.01,
			    value: 200.009,
			    
			    slide: function(event, ui) {
		
			    	var id2_int = 400.01- ui.value.toFixed(3) ;
			    	var id2 = Math.pow(10, -id2_int);
			    	console.log("id2_int"+ id2_int + "  id2 = " + id2);
			        $("#amount-test").val(id2);
			    },
		
		
				change:function(event, ui)
				{
					var id2_int = 400.01- ui.value.toFixed(3) ;
			    	var id2 = Math.pow(10, -id2_int);
			    	var id = getUrlVars()["q"];
			    	var databases = [];
				    databases = getUrlList();
				    
			        var id1 = 1.45e-323;		
					console.log("changed id1 = "+ id1 +"  id2  = " + id2 );
					window.location.href ='http://localhost:8080/conceptmetab/enrichments/redirectView?id1='+id1+'&id2='+id2+'&q='+id+'&fil=pval&network=network&slider=slider&odds='+odds+'&statement='+databases;
					
				}
			});

		   var e = document.getElementById("myList");
		    var strUser = e.options[e.selectedIndex].value;
		    console.log (" e is "+ strUser)
		   var idc = Math.pow(10, -$("#slidertest").slider("value"));
		   console.log("idc is "+ idc);
			$("#amount-test").val(idc);
				}); //Function


function getUrlVars() {
        var vars = {};
        var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m,key,value) {
            vars[key] = value;
           // console.log('key is'+ key+ 'value is '+ value);
        });
        console.log('final var is'+ vars)
        return vars;
   }


function getUrlList() {
	var vars = [], hash;
    var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
    for(var i = 0; i < hashes.length; i++)
    {
    	hash = hashes[i].split('=');
    	if(hash[0] =='statement')
        	{
    	console.log("i" +hash);   
        vars.push(hash[1]);
        	}
    }
    console.log('final var is'+ vars)
    return vars;
}

function alertForData (){
	console.log("inside function with resultcount = "+ ${resultcount})
	if(${resultcount}== 0)
		{
		console.log("inside if")
		var nPval = getUrlVars()["id2"];
		alert("No result found for pval = "+ nPval)
		}
}


function favBrowser()
		{
		var mylist=document.getElementById("myList");
		document.getElementById("favorite").innerHTML = mylist.options[mylist.selectedIndex].text+".";
		
		}



</script>

 <g:javascript src="cytoscape/min/json2.min.js" />
        
 <!-- Flash embedding utility (needed to embed Cytoscape Web) -->

 <g:javascript src="cytoscape/min/AC_OETags.min.js" />
        
<!-- Cytoscape Web JS API (needed to reference org.cytoscapeweb.Visualization) -->
<g:javascript src="cytoscape/min/cytoscapeweb.min.js" />


  <style>
            /* The Cytoscape Web container must have its dimensions set. */
            html, body { height: 100%; width: 100%; padding: 0; margin: 0;}
            #cytoscapeweb { width: 90%; height: 80%; }
        </style>
  
<script type="text/javascript">
	window.onload=function() {
	    // id of Cytoscape Web container div
	    var div_id = "cytoscapeweb";
	    
		alertForData();
	

	 var networ_json = {
        // you need to specify a data schema for custom attributes!
        dataSchema: {
            nodes: [ { name: "label", type: "string" },
                     { name: "conTypes", type: "string" },
                     { name: "comNo", type: "interger" }
                ],
            edges: [ { name: "label", type: "string" },
                     { name: "thick", type: "integer" },
                     { name: "db_id", type: "string" }
            ]
        },
        // NOTE the custom attributes on nodes and edges
        data:  ${check}
};
	 var visual_style = {
                
                nodes: {
                    shape: "ELLIPSE",
                    borderWidth: 2,
                    labelFontSize:12,
                    labelFontWeight:"bold",
                  
                    size: {
                        defaultValue: 25,
                        continuousMapper: { attrName: "comNo", minValue: 10, maxValue: 40 }
                    },
                    color: {
                        discreteMapper: {
                        	attrName: "conTypes",
	                        entries: [

	                                { attrValue: "GOBP", value: "#227207"},
	  	                            { attrValue: "GOCC", value: "#98E6CA" },
	  	                            { attrValue: "GOMF", value: "#49FFB9" },
	  	                            { attrValue: "Enzyme", value: "#BE3A40" },
	  	                            { attrValue: "KEGG Pathway", value: "#CC2EFA"},
	  	                          	{ attrValue: "Chemical Clusters", value: "#ffab9b" },
									{ attrValue: "MeSH Anatomy", value: "#7B3F00 " },
									{ attrValue: "MeSH Diseases", value: "#F47D00" },
									{ attrValue: "MeSH Chem and Drug", value: "#FFB86D" },
									{ attrValue: "MeSH Organisms", value: "#FCDC3B" },
									{ attrValue: "MeSH Phen and Proc", value: "#F7EA2B" },
									{ attrValue: "MeSH Psy and Psy", value: "#00F5FF" },
									{ attrValue: "MeSH Tech", value: "#00C5CD" }
	                        ]
                        }
                    },
                    labelHorizontalAnchor: "center"
                },
                edges: {
                	width: {
                        defaultValue: 1,
                        continuousMapper: { attrName: "thick", minValue: 1, maxValue: 5 }
                    },
                    color: "#E5E4E2"
                    	
                }
            };

	
             
    
    // initialization options
    var options = {
            // where you have the Cytoscape Web SWF
            swfPath: "/conceptmetab/swf/CytoscapeWeb",
            // where you have the Flash installer SWF
            flashInstallerPath: "/conceptmetab/swf/playerProductInstall",
          	

            
        };
    // init and draw
    var vis = new org.cytoscapeweb.Visualization(div_id, options);
   

    vis.ready(function() {
        
        // add a listener for when nodes and edges are clicked
        vis.addListener("click", "nodes", function(event) {
            handle_click(event);
        })
        .addListener("click", "edges", function(event) {
            handle_click(event);
        });

       
        
        function clear() {
            document.getElementById("note").innerHTML = "";
        }
    
        function print(msg) {
            document.getElementById("note").innerHTML += "<p>" + msg + "</p>";
        }
    });


    vis.ready(function() {
        
        // add a listener for when nodes and edges are clicked
        vis.addListener("click", "nodes", function(event) {
            handle_click(event);
        })
        .addListener("click", "edges", function(event) {
        	handle_edge_click(event);
        });
        
        function handle_click(event) {
             var target = event.target;
             
             clear();
             
             var test = "event.group = " + event.group ;
            
                 var test2 = "0"+ test;
             for (var i in target.data) {
                var variable_name = i;
                var variable_value = target.data[i];
                //print( "    " + variable_name + " = " + variable_value );
                var variable_info = "    " + variable_name + " = " + variable_value + "\n";
                test =test+variable_info;
                console.log("i = "+ i + " value = "+ target.data[i]);
                if(i == "id")
                       {
                       console.log("found pval"+ target.data[i]);
                       var id = target.data[i];
                       }
              
                 }
            
             window.open('displayMsg?q='+id,'Popup','directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=400,height=200');
            // window.open("","huh","width=320,height=210,scrollbars=no,toolbar=no,screenx=0,screeny=0,location=no,titlebar=no,directories=no,status=no,menubar=no,fullscreen=yes"); 
             
        }

        function handle_edge_click(event) {
            var target = event.target;
            
            clear();
            
            var test = "event.group = " + event.group ;
            var test2 = "0"+ test;
            var map = new Object();
			console.log("target data"+target.id)
            
            for (var i in target.data) {
               var variable_name = i;
               var variable_value = target.data[i];
               //print( "    " + variable_name + " = " + variable_value );
              console.log("i = "+ i + " value = "+ target.data[i]);
              var variable_info = "    " + variable_name + " = " + variable_value + ";";
                test =test+variable_info;
                   if(i == "db_id")
                           {
                           console.log("found db_id == "+ target.data[i]);
                           var id = target.data[i];
                           }
                 
                }
            console.log("test"+test)
            var testlink = 'displayEdge?q='+id
            var con = getUrlVars()["q"];
            window.open('${createLink(action:'displayEdge')}?q='+ id+'&con='+con,'Popup','toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=no, width=620,height=500,left=430,top=23'); 
            
       }
        
        function clear() {
            document.getElementById("note").innerHTML = "";
        }
    
        function print(msg) {
            document.getElementById("note").innerHTML += "<p>" + msg + "</p>";
           // alert(msg);
           
	
          window.open('displayMsg?q='+msg,'Popup','toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=no, width=420,height=200,left=230,top=23'); 
                
         //   window.open( <g:remoteFunction action="show" id="1" />,'Popup','toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=no, width=420,height=400,left=430,top=23'); 
            
             
            
        }
    });
  
    var draw_options = {
        // your data goes here
        network:  networ_json,
        
        // show edge labels too
       // edgeLabelsVisible: true,
        
        // let's try another layout
       layout: 'ForceDirected',
      
        
        // set the style at initialisation
        visualStyle: visual_style,
        nodeTooltipsEnabled : true,
        
        // hide pan zoom
        //panZoomControlVisible: false 
    };
    vis.draw(draw_options);
};




</script> 
        
        
</head>
<body>
	<div id ="fieldpanel" >
	<center>
				<a href="${createLink(uri: '/')}"><img src="${resource(dir: 'images', file: 'logoconceptmetab.gif')}" alt="Grails"  style="max-height: 400px; max-width: 400px;"/></a>
				<br/>
				<span class="header-sub">Metabolite Set Network Tool</span>
	</center>
	
	<g:set var="id2" value="${params.id2}"/>
				<g:set var="odds" value="${params.odds}"/>
				<g:set var="fil" value="${params.fil}"/>
				<g:link controller="concepts" action="show" id="${con.id}" params="[id2: id2, odds: odds,fil:fil]">Go Back to Filter Page</g:link></td>			
	</div>


   <div style="width: 69%;height:100%;float:right;">
		<div id = "note"></div>
		<div id="cytoscapeweb" style="width: 100%; height:80%;float:left ;   background-color: #efefef;">
				Cytoscape Web will replace the contents of this div with your graph.
		</div>
  </div>
  <div  style="width: 30%; float:right ; ">
				<div id ="fieldpanel">
					<table id ='result'>
					<tr> <span class="formTextHeader">Concept  Information</span></tr>
					<tr><td>Concept name:</td><td> ${con.name}</td><tr>
					<tr><td>Concept Id:</td><td> ${con.original_id}</td></tr>
					<tr><td>Concept Type:</td><td> ${con.concept_types.getFullname()}</td><tr>
					<tr><td>Number of Compounds:</td><td> ${con.num_compounds}</td><tr>
					</table>
				</div>
					
				<div id ="fieldpanel" >
				 	<table id ='result'>
				 	  <g:form action="redirectView" method="get">
                        <tr>
                          <span class="formTextHeader">Filter Options:</span>
                        </tr>
                        <tr>                       
                          <td><span class="formText"><g:radioGroup name="fil" values="['pval','qval']" value="${params.fil}" labels = "['P-value','Q-value']">${it.radio} <g:message code="${it.label}" /></g:radioGroup></span></td> 
                          <td> <g:textField name="id2" id="id2" value="${params.id2 }"/></td>
                        </tr>
                           
                          <% def list = params.statement %>   
                        <tr> 
                           <td><span class="formTextExp">Odds Ratio </span></td>
                           <td><g:textField name="odds" id="odds" value="${params.odds}" maxlength ="10"/></td> 
                        </tr>
                           <g:hiddenField name="id1"  value="1.45e-323"/>                            
                           <g:hiddenField name="network"  value="network"/>
                           <g:hiddenField name="q" value="${params.q}" />
                          <br/>
	                    <g:if test="${params.containsKey("slider")}">
			                          <g:hiddenField name="slider"  value="slider"/>
			             <tr>
			             		<td><span class="formText">Databases are :</td>
			                           ${list}</span> 
			             </tr>
			                          <g:hiddenField name="statement" value= '${params.statement}' />
	                    </g:if>
	                    <g:else>
	                    
				      				<g:each in="${list}" status="i" var="test">					 		
						    	<tr>	<td> <span class="formText"><g:checkBox name="statement" value="${test}" checked="true" />${ test}</span></td>   </tr>					    	
					    	  		</g:each>
					 
					    </g:else>
					    <tr>
					    <td colspan="2"><br/><center><g:submitButton name="Submit"/></center></td>
					    
					    
					    
                        </tr>
                        <tr><td><g:submitButton name="CompleteNetwork"/></td></tr>   
                  </g:form>
                  
               		</table>   
                </div>
				<form>
							Select value:
							<select id="myList">
							  <option></option>
							  <option>Pvalue</option>
							  <option>Qvalue</option>  
							</select>
								 
							<label for="amount-test"> <span class="footnote">Pvalue range:</span></label>
							
								<input type="text" id="amount-test" style="border:0; color:#f6931f; font-weight:bold;" />
								<div id="slidertest"></div>
							
				</form>
				
				
				
			 
				
				<div id = "fieldpanel">
						Parameters are id1 = ${params.id1} and id2 = ${params.id2} and q= ${params.q } and filter  = ${params.fil} 
						<g:set var="id1" value="${params.id1}"/>	
						<g:set var="id2" value="${params.id2}"/>	
						<g:set var="q" value="${params.q}"/>	
						<g:set var="fil" value="${params.fil}"/>
						<g:set var="odds" value="${params.odds}"/>
						<q:set var ="statement" value ="['MeSH Anatomy', 'MeSH Phenomena and Processes']" />
						${params.statement.toList()}
						<span class="property-value" aria-labelledby="original_id-label">	<g:link controller="Enrichments" action="createJson" params="[id1:id1,id2:id2,q:q,fil:fil,odds:odds,statement:statement ]">Click here for complete network</g:link></span>
				</div>	
	</div>
	</body>
	</html>