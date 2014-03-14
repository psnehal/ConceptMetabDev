



<!DOCTYPE html>
<html>
<head>
 <!-- JSON support for IE (needed to use JS API) -->
 
 <g:javascript src="cytoscape/min/json2.min.js" />
        
 <!-- Flash embedding utility (needed to embed Cytoscape Web) -->

 <g:javascript src="cytoscape/min/AC_OETags.min.js" />
        
<!-- Cytoscape Web JS API (needed to reference org.cytoscapeweb.Visualization) -->
<g:javascript src="cytoscape/min/cytoscapeweb.min.js" />
     
         
<title>Path_Enzyme</title>
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
                                 { name: "foo", type: "string" }
                            ],
                        edges: [ { name: "label", type: "string" },
                                 { name: "bar", type: "string" }
                        ]
                    },
                    // NOTE the custom attributes on nodes and edges
                    data:  ${check}
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
                vis.draw({ network: networ_json });
            };
        </script>
        
  
  
</head>


<body> 	
<div id="cytoscapeweb">
            Cytoscape Web will replace the contents of this div with your graph.
            
            
        </div>
        </body>
        </html>