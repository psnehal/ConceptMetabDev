



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
               
				var t = "${check}";
				console.log(t)
                var networ_json = { 

                		 data :{
                			 "nodes":
                				 [{"id":"4268"},{"id":"4270"},{"id":"4092"},{"id":"3955"},{"id":"4047"},{"id":"4048"},
                				 {"id":"4087"},{"id":"4018"},{"id":"4049"},{"id":"3696"},{"id":"4206"},{"id":"3810"},
                				 {"id":"3888"},{"id":"276"},{"id":"3887"},{"id":"3936"},{"id":"3836"},{"id":"3835"},
                				 {"id":"519"},{"id":"3081"},{"id":"3460"}],

                				 "edges":
                				 [{"source":"3460","target":"4268","id":"594"},{"source":"3460","target":"4270","id":"594"},
                				 {"source":"3460","target":"4092","id":"594"},{"source":"3460","target":"3955","id":"310"},
                				 {"source":"3460","target":"4047","id":"309"},{"source":"3460","target":"4048","id":"309"},
                				 {"source":"3460","target":"4087","id":"305"},{"source":"3460","target":"4018","id":"295"},
                				 {"source":"3460","target":"4049","id":"295"},{"source":"3460","target":"3696","id":"291"},
                				 {"source":"3460","target":"4206","id":"151"},{"source":"3460","target":"3810","id":"161"},
                				 {"source":"3460","target":"3888","id":"144"},{"source":"3460","target":"276","id":"308"},
                				 {"source":"3460","target":"3887","id":"175"},{"source":"3460","target":"3936","id":"119"},
                				 {"source":"3460","target":"3836","id":"238"},{"source":"3460","target":"3835","id":"239"},
                				 {"source":"3460","target":"519","id":"213"},{"source":"3460","target":"3081","id":"140"}]
                         } 
                       };
                         
                
                // initialization options
               

                var options = {
                        // where you have the Cytoscape Web SWF
                        swfPath: "/helloworld/swf/CytoscapeWeb",
                        // where you have the Flash installer SWF
                        flashInstallerPath: "/helloworld/swf/playerProductInstall"
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