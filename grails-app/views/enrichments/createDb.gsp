



<!DOCTYPE html>
<html>
<head>
 <!-- JSON support for IE (needed to use JS API) -->
 
 <g:javascript src="cytoscape/min/json2.min.js" />
        
 <!-- Flash embedding utility (needed to embed Cytoscape Web) -->

 <g:javascript src="cytoscape/min/AC_OETags.min.js" />
        
<!-- Cytoscape Web JS API (needed to reference org.cytoscapeweb.Visualization) -->
<g:javascript src="cytoscape/min/cytoscapeweb.min.js" />
     
         
<title>Enrichments</title>
  <style>
            /* The Cytoscape Web container must have its dimensions set. */
            html, body { height: 100%; width: 100%; padding: 0; margin: 0; }
            #cytoscapeweb { width: 100%; height: 100%; }
        </style>
  
  <script type="text/javascript">
            window.onload=function() {
                // id of Cytoscape Web container div
                var div_id = "cytoscapeweb";
                
                // you could also use other formats (e.g. GraphML) or grab the network data via AJAX
               
				var t = ${check};
				console.log(t)
               // var networ_json = { 
				// data :  ${check}
                  //     };

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
                                 { name: "dbid", type: "string" },
                                 { name: "dbid2", type: "string" }
                               
                        ]
                    },
                    // NOTE the custom attributes on nodes and edges
                    data:  ${check}
            };
				 var visual_style = {
		                    
		                    nodes: {
		                        shape: "ELLIPSE",
		                        borderWidth: 3,
		                        borderColor: "#ffffff",
		                        size: {
		                            defaultValue: 25,
		                            continuousMapper: { attrName: "comNo", minValue: 25, maxValue: 75 }
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
		                            continuousMapper: { attrName: "thick", minValue: 1, maxValue: 10 }
		                        },
		                        color: "#D1D0CE"
		                        	
		                    }
		                };

				
                         
                
                // initialization options
                var options = {
                        // where you have the Cytoscape Web SWF
                        swfPath: "/conceptmetab/swf/CytoscapeWeb",
                        // where you have the Flash installer SWF
                        flashInstallerPath: "/conceptmetab/swf/playerProductInstall"

                        
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
                           // console.log("i = "+ i + " value = "+ target.data[i]);
                            if(i == "id")
		                           {
		                           console.log("found pval"+ target.data[i]);
		                           var id = target.data[i];
		                           }
                          
                             }
                        
                         window.open('displayMsg?q='+id,'Popup','toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=no, width=420,height=400,left=430,top=23'); 
                         
                    }

                    function handle_edge_click(event) {
                        var target = event.target;
                        
                        clear();
                        
                        var test = "event.group = " + event.group ;
                        var test2 = "0"+ test;
                        var map = new Object();
						//console.log("target data"+target.id)
                        
                        for (var i in target.data) {
                           var variable_name = i;
                           var variable_value = target.data[i];
                           //print( "    " + variable_name + " = " + variable_value );
                         // console.log("i = "+ i + " value = "+ target.data[i]);
                          var variable_info = "    " + variable_name + " = " + variable_value + ";";
                            test =test+variable_info;
			                   if(i == "db_id")
			                           {
			                           console.log("found db_id == "+ target.data[i]);
			                           var id = target.data[i];
			                           }
			                 
                            }
                        console.log("test"+test)
                        window.open('displayEdge?q='+id,'Popup','toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=no, width=420,height=400,left=430,top=23'); 
                        
                   }
                    
                    function clear() {
                        document.getElementById("note").innerHTML = "";
                    }
                
                    function print(msg) {
                        document.getElementById("note").innerHTML += "<p>" + msg + "</p>";
                       // alert(msg);
                       

                      window.open('displayMsg?q='+msg,'Popup','toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=no, width=420,height=400,left=430,top=23'); 
                            
                     //   window.open( <g:remoteFunction action="show" id="1" />,'Popup','toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=no, width=420,height=400,left=430,top=23'); 
                        
                         
                        
                    }
                });
              
                var draw_options = {
                    // your data goes here
                    network:  networ_json,
                    
                    // show edge labels too
                   // edgeLabelsVisible: true,
                    
                    // let's try another layout
                    layout: "ForceDirected",
                    
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
<div id="cytoscapeweb">
            Cytoscape Web will replace the contents of this div with your graph.
            
            
        </div>
         <div id="note">
            <p>Click nodes or edges.</p>
        </div>
        <div id="dialog" title="Basic dialog">
  <p>This is the default dialog which is useful for displaying information. The dialog window can be moved, resized and closed with the 'x' icon.</p>
</div>
        <div id="dialogPlaceholder"></div>
        <div id="dialog-message" title="Download complete">
 
        </body>
        </html>