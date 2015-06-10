<%@ page import="conceptmetab.Meshid2treenum" %>
<%@ page contentType="text/html;charset=ISO-8859-1" %>
<html lang="en">
<head>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<link rel="stylesheet" href="${resource(dir: 'css', file: 'createChart.css')}" type="text/css">
<title>Star Network for ${ con.name }</title>
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
                     { name: "db_id", type: "string" },
                     { name: "rel", type: "integer" },
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
									{ attrValue: "MeSH Phen and Proc", value: "#0000FF" },
									{ attrValue: "MeSH Psy and Psy", value: "#00F5FF" },
									{ attrValue: "MeSH Tech", value: "#00C5CD" }
	                        ]
                        }
                    },
                    borderColor: {
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
									{ attrValue: "MeSH Phen and Proc", value: "#0000FF" },
									{ attrValue: "MeSH Psy and Psy", value: "#00F5FF" },
									{ attrValue: "MeSH Tech", value: "#00C5CD" }
	                        ]
                        }
                    },
                    hoverBorderColor:"red",
                    labelVerticalAnchor :"bottom",
                    labelHorizontalAnchor: "center"
                },
                edges: {
                	width: 3,
                    targetArrowShape: {
    					discreteMapper: {
    						attrName: "rel",
    						entries: [
    							{ attrValue: "0", value: "NONE" },
    							{ attrValue: "1", value: "DELTA" },
    							{ attrValue: "2", value: "NONE" },
    							{ attrValue: "3", value: "NONE" }
    							
    						]
    					}
                    },
    	            sourceArrowShape: {
    					discreteMapper: {
    						attrName: "rel",
    						entries: [
    							{ attrValue: "0", value: "NONE" },
    							{ attrValue: "1", value: "NONE" },
    							{ attrValue: "2", value: "DELTA" },
    							{ attrValue: "3", value: "NONE" },
    						]
    					}
    	            },
                    style: {
                        discreteMapper: {
                        	attrName: "rel",
	                        entries: [

	                                { attrValue: "0", value: "SOLID"},
	  	                            { attrValue: "1", value: "DOT" },
	  	                            { attrValue: "2", value: "DOT" },
	  	                            { attrValue: "3", value: "DOT" }
	  	                         
	                        ]
                        }
                    },
                    color: {
                        discreteMapper: {
                        	attrName: "rel",
	                        entries: [

	                                { attrValue: "0", value: "#B6B6B4"},
	  	                            { attrValue: "1", value: "#0000FF" },
	  	                            { attrValue: "2", value: "#0000FF" },
	  	                            { attrValue: "3", value: "#227207" }
	  	                         
	                        ]
                        }
                    }
                    
                    	
                }
            };

	
             
    
    // initialization options
    var options = {
            // where you have the Cytoscape Web SWF
            swfPath: "/swf/CytoscapeWeb",
            // where you have the Flash installer SWF
            flashInstallerPath: "/swf/playerProductInstall",
          	

            
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
             var w =window.open('${createLink(action:'displayMsg')}?q='+ id,"_blank",'toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=no, width=400,height=150');
             w.onload = function() { this.document.title = "Concept Information"; }
            // window.open('displayMsg?q='+id,'Popup','directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=400,height=200');
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
            var ed =window.open('${createLink(action:'displayEdge')}?q='+ id+'&con='+con,"_blank",'toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=no, width=620,height=500,top=23');
            ed.onload = function() { this.document.title = "Edge Information"; } 
            
       }
        
        function clear() {
            document.getElementById("note").innerHTML = "";
        }
    
        function print(msg) {
            document.getElementById("note").innerHTML += "<p>" + msg + "</p>";        // alert(msg);      
	
          window.open('displayMsg?q='+msg,"_blank",'toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=no, width=420,height=200,left=230,top=23');                 
         //   window.open( <g:remoteFunction action="show" id="1" />,'Popup','toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=no, width=420,height=400,left=430,top=23'); 
         }
    });

    var layout = {
    	    name:    "ForceDirected",
    	    options: { restLength: 240,
    	    	autoStabilize:true,}
    	};
    
    var draw_options = {
        // your data goes here
        network:  networ_json,        
        // show edge labels too
       // edgeLabelsVisible: true,        
        // let's try another layout
       layout: layout,
        // set the style at initialisation
        visualStyle: visual_style,
        nodeTooltipsEnabled : true,
        // hide pan zoom
        //panZoomControlVisible: false 
    };
    vis.draw(draw_options);
};


function showDiv() {
	var div  =document.getElementById('welcomeDiv');

	if (div.style.display !== 'none') {
        div.style.display = 'none';
    }
    else {
        div.style.display = 'block';
        
    }	
	}
</script> 
        
        
</head>
<body>
 
<div style="background-color:#eee;">
	<div id =header  > 			 
				<a href="${createLink(uri: '/')}"><img src="${resource(dir: 'images', file: 'logoconceptmetab.gif')}" alt="Grails"  style="max-height: 300px; max-width:300px;"/></a>
				<br/><span class="header-sub" style="font-size: .70em;">Compound Set Network Tool</span>
				
	</div>
	<div id="lefttop"> 
	<br/>
			<table id ='chart2'>
			<tr>
			<td>
			<g:form controller="concepts" action="show" method="get">
				  <g:hiddenField name="id2"  value="${params.id2}"/>                            
				  <g:hiddenField name="odds"  value="${params.odds}"/>
				  <g:hiddenField name="fil" value="${params.fil}" />
				  <g:hiddenField name="id" value="${con.id}" />
				  <g:submitButton name="Submit" value="Go Back" class="submit"/>
				 </g:form>
				 
			</td>
			
			<td>
			 <g:form action="redirectView" method="get">
			  	<g:submitButton class="submit" value="Complete Network" name="CompleteNetwork"/>
			</td>
			
			<td>
			<g:submitButton class="submit" value="Heatmap" name="heatmap"/>
			</td>
			<td>
				<g:submitButton class="submit" value="Table" name="table"/>
			</td>
			</tr>
				 
			</table>
			
	 </div>

</div>

<div id="navigation">
	<div id="welcomeDiv"  style="display:block;" class="answer_list" >
	
		<% def list = db %>
        <g:hiddenField name="id1"  value="1.45e-323"/>                            
        <g:hiddenField name="network"  value="network"/>
        <g:hiddenField name="q" value="${params.q}" />
        <br/> 
		<div id ="fieldpanel">
		
			<span class="formTextHeader">Concept Information</span>		
			<table id ='result'>
			<tr><td>Concept Name:</td><td> ${con.name}</td><tr>
			<tr>
			 <g:if test="${con.concept_types.getFullname().contains("MeSH")}">		
				<%def meshid2treenumInstance =Meshid2treenum.findAllWhere(mesh_id : con.original_id ) %>						  		  
			    <g:if test="${meshid2treenumInstance.size() != 0}">		  
			    			<td>Concept ID:</td><td> ${meshid2treenumInstance.tree_id.toString().replace("[", "") .replace("]", "") }</td>
				</g:if>
				<g:else>
					  		<td>Concept ID: </td><td>${con.original_id}</span></td>
				</g:else>
			</g:if>
		  	<g:else>
			 	<td>Concept ID: </td><td>${con.original_id}</td>
			</g:else>
			</tr>	
			<tr><td>Concept Type:</td><td> ${con.concept_types.getFullname()}</td><tr>
			<tr><td>Number of Compounds:</td><td> ${con.num_compounds}</td><tr>
			</table>
		</div>
		<br/>
		<div id ="fieldpanel" style="height:100%;"> 
		<span class="formTextHeader">Filter Options:</span>					
	            
				<table id ='result' style='background:white'>
					<tr><td><div>P-value<input type="radio" name="fil" value="pval"/> q-value <input type="radio" name="fil" value="${params.fil}" checked="checked"  /> < <g:textField name="id2" id="id2" size="8" value="${params.id2 }"/></div>  </td></tr>
		            <tr><td>
		            <span class="filter-label">Odds Ratio ></span>
		              <span class="filter-label"> <g:textField name="odds" id="odds" value="${params.odds}"/></span> 
		            </td></tr> 
		            <tr><td><center> <g:submitButton name="network" value="Submit" class ="submit"/></center></td></tr>
	             </table> 
	             <table id ='chart'>				
					   <g:each in="${list}" status="i" var="test">	
					   		<input type="hidden" name="statement" value="${test}">	
						</g:each>		                         
	              	</table>
	              	<g:hiddenField name="id" value="${params.q}" />   
			    </g:form> 
 		</div>
	    <div id ="fieldpanel" >  
         <span class="formTextHeader">Legend</span> 
	  		<table id="chart">
					<tr><td>Enriched Concept</td> <td> <img src="${resource(dir: 'images', file: 'norel.png')}" alt="Grails"  style="max-height: 50px; max-width:50px;"/></td></tr>
					<tr><td>Subset -superset</td> <td> <img src="${resource(dir: 'images', file: 'rel1.png')}" alt="Grails"  style="max-height: 50px; max-width:50px;"/></td></tr>
					<tr><td>Identical Sets</td> <td> <img src="${resource(dir: 'images', file: 'rel3.png')}" alt="Grails"  style="max-height: 50px; max-width:50px;"/></td></tr>
			</table>		
			<table id = "chart">
					      <tr><td>Enzyme  </td><td> <div class="foo" style="background-color:#BE3A40"></div></td></tr>
 						  <tr><td>GO Biological Process  </td><td> <div class="foo" style="background-color:#227207"></div></td></tr>
						  <tr><td>GO Cellular Component</td><td> <div class="foo" style="background-color:#98E6CA"></div></td></tr>
						  <tr><td>GO Molecular Function  </td><td> <div class="foo" style="background-color:#49FFB9"></div></td></tr> 						 
						  <tr><td>KEGG Pathway  </td><td> <div class="foo" style="background-color:#CC2EFA"></div></td></tr>
						  <tr><td>MeSH Anatomy </td><td> <div class="foo" style="background-color:#7B3F00"></div></td></tr>
					      <tr><td>MeSH Chemicals and Drugs </td><td> <div class="foo" style="background-color:#FFB86D"></div></td></tr>
						  <tr><td>MeSH Diseases </td><td> <div class="foo" style="background-color:#F47D00"></div></td></tr>
						  <tr><td>MeSH Organisms</td><td> <div class="foo" style="background-color:#FCDC3B"></div></td></tr>
						  <tr><td>MeSH Phenomena and Processes  </td><td> <div class="foo" style="background-color:#0000FF"></div></td></tr> 
						  <tr><td>MeSH Psychology and Psychiatry  </td><td> <div class="foo" style="background-color:#00F5FF"></div></td></tr> 
						  <tr><td>MeSH Technology, Industry, and Agriculture </td><td> <div class="foo" style="background-color:#00C5CD"></div></td></tr>
						
					</table> 	
         </div>
     
		
	
	</div><!-- left -->
    </div><!-- -Hide dive -->

   		 <div id ="content">
				<div id = "note"></div>
				<div id="cytoscapeweb" style="width:100%;height:100%;background-color: #efefef;">
					Cytoscape Web will replace the contents of this div with your graph.
				</div> 
		</div>
		
