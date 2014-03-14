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
		    var q = getUrlVars()["q"];
		   console.log("q ="+ q);
		
		   $("#slider-verticalbackwards").slider({
			   
			    range: "min",
			    min: 0.01,
			    max: 400.01,
			    value: 200.009,
			    
			    slide: function(event, ui) {
		
			    	var id2_int = 400.01- ui.value.toFixed(3) ;
			    	var id2 = Math.pow(10, -id2_int);
			    	console.log("id2_int"+ id2_int + "  id2 = " + id2)
			        $("#amount-backwards").val(id2);
			        
			        
			        
			    },
		
		
				change:function(event, ui)
				{
					var id2_int = 400.01- ui.value.toFixed(3) ;
			    	var id2 = Math.pow(10, -id2_int);
			    	var id = getUrlVars()["q"];
			        var id1 = 1.45e-323;
		
					console.log("changed id1 = "+ id1 +"  id2  = " + id2 );
					window.location.href ='http://localhost:8080/conceptmetab/enrichments/createJson?id1='+id1+'&id2='+id2+'&q='+id+'&fil=pval';
					
				}
			});
		   var idc = Math.pow(10, -$("#slider-verticalbackwards").slider("value"));
		   console.log("idc is "+ idc);
			$("#amount-backwards").val(idc);
			//$("#amount-backwards").val(100 - $("#slider-verticalbackwards").slider("value"));
		//****************************************************************qval Slider*****************************************************************
		  $("#slider-qval-back").slider({
			   
			    range: "min",
			    min: 0,
			    max: 500,
			    value: 100,
			    slide: function(event, ui) {
		
			    	var id2_int = 500- ui.value;
			    	var id2 = Math.pow(10, -id2_int);
			    	console.log("id2_int"+ id2_int + "  id2 = " + id2)
			    	
			        $("#amount-slider-qval-back").val(id2);
			        
			        
			        
			    },
		
		
				change:function(event, ui)
				{
					var id2_int = 500- ui.value;
			    	var id2 = Math.pow(10, -id2_int);
			    	var id = getUrlVars()["q"];
			        var id1 = 1.45e-323;
		
					console.log("changed id1 = "+ id1 +"  id2  = " + id2 );
					window.location.href ='http://localhost:8080/conceptmetab/enrichments/createJson?id1='+id1+'&id2='+id2+'&q='+id+'&fil=qval';
					
				}
			});
		   var idq = Math.pow(10, -$("#slider-verticalbackwards").slider("value"));
		   console.log("idc is "+ idq);
			$("#amount-slider-qval-back").val(idq);
			//$("#amount-backwards").val(100 - $("#slider-verticalbackwards").slider("value"));
			//***************************************************************************************************************************************
			
			
			//itializes slider
					$( "#slider-range" ).slider({
						
							range: true,
							min:  0,
							max: 400,
							values: [ 0, 373 ],
							
				
							
							slide: function( event, ui ) {
										console.log("inside slide");
									//getter	
									$( "#amount" ).val( "$" + ui.values[ 0 ] + " - $" + ui.values[ 1 ] );
									//window.alert(ui.values[ 0 ] + " - $" + ui.values[ 1 ]);
									console.log("value 1 ==  "+ ui.values[ 0 ]+ "   value 2 == " + ui.values[ 1 ]);
									//var id2 = Math.pow(10, -ui.values[ 0 ]);
									//var id1 = Math.pow(10, -ui.values[ 1 ]);
									console.log("3");
									var id2 = ui.values[ 1 ];
									var id1 = ui.values[ 0 ];
									console.log("id1 == "+ ui.values[ 0 ]);
									console.log("id2 == "+ ui.values[ 1 ]);
									var uiv =  Math.pow(10, - ui.values[ 1 ]);
									$("#para").text(uiv);
									//var url = window.location.href;
								//window.open(link ,'Popup','toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=no, width=420,height=400,left=430,top=23');
							},
							change:function(event, ui)
							{
								console.log("inside change");
								var id1_int = ui.values[ 1 ];
								var id2_int = ui.values[ 0 ];
								
								//$( "#amount" ).val( "$" + $( "#slider-range" ).slider( "values", 0 ) +" - $" + $( "#slider-range" ).slider( "values", 1 ))
								var id = getUrlVars()["q"];
								console.log("id is "+ id);
								console.log("id is before id1 =  "+ id1 + " id2 = "+ id2);
								
								var id1 = Math.pow(10, -id1_int)
								var id2 = Math.pow(10, -id2_int);
								console.log("changed id1 = "+ id1 +"  id2  = " + id2 );
								window.location.href ='http://localhost:8080/conceptmetab/enrichments/filterSlider?id1='+id1+'&id2='+id2+'&q='+id+'&fil=pval';  
							}
						
					}); //Slider
		
					$( "#qval-range" ).slider({
						
						range: true,
						min:  0,
						max: 400,
						values: [ 0, 373 ],
			
						
						slide: function( event, ui ) {
									console.log("inside slide");
								//getter	
								$( "#qval" ).val( "$" + ui.values[ 0 ] + " - $" + ui.values[ 1 ] );
								//window.alert(ui.values[ 0 ] + " - $" + ui.values[ 1 ]);
								console.log("value 1 ==  "+ ui.values[ 0 ]+ "   value 2 == " + ui.values[ 1 ]);
								//var id2 = Math.pow(10, -ui.values[ 0 ]);
								//var id1 = Math.pow(10, -ui.values[ 1 ]);
								console.log("3");
								var id2 = ui.values[ 1 ];
								var id1 = ui.values[ 0 ];
								console.log("id1 == "+ ui.values[ 0 ]);
								console.log("id2 == "+ ui.values[ 1 ]);
								var uiv =  Math.pow(10, - ui.values[ 1 ]);
								$("#qval").text(uiv);
								//var url = window.location.href;
							//window.open(link ,'Popup','toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=no, width=420,height=400,left=430,top=23');
						},
						change:function(event, ui)
						{
							console.log("inside change");
							var id1_int = ui.values[ 1 ];
							var id2_int = ui.values[ 0 ];
							
							//$( "#amount" ).val( "$" + $( "#slider-range" ).slider( "values", 0 ) +" - $" + $( "#slider-range" ).slider( "values", 1 ))
							var id = getUrlVars()["q"];
							console.log("id is "+ id);
							console.log("id is before id1 =  "+ id1 + " id2 = "+ id2);
							
							var id1 = Math.pow(10, -id1_int)
							var id2 = Math.pow(10, -id2_int);
							console.log("changed id1 = "+ id1 +"  id2  = " + id2 );
							window.location.href ='http://localhost:8080/conceptmetab/enrichments/filterSlider?id1='+id1+'&id2='+id2+'&q='+id+'&fil=qval';  
						}
					
				}); //Slider
				}); //Function


function getUrlVars() {
        var vars = {};
        var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m,key,value) {
            vars[key] = value;
        });
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
                labelFontSize:10,
                labelFontWeight:"bold",
              
                size: {
                    defaultValue: 25,
                    continuousMapper: { attrName: "comNo", minValue: 5, maxValue: 20 }
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
        
         window.open('displayMsg?q='+id,'Popup','directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=620,height=200');
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
        window.open('displayEdge?q='+id,'Popup','toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=no, width=620,height=500,left=430,top=23'); 
        
   }
    
    function clear() {
        document.getElementById("note").innerHTML = "";
    }

    function print(msg) {
        document.getElementById("note").innerHTML += "<p>" + msg + "</p>";
       // alert(msg);
       

      window.open('displayMsg?q='+msg,'Popup','toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=no, width=320,height=300,left=430,top=23'); 
            
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
	</div>

   <div style="width: 69%;height:100%;float:right">
		<div id = "note"></div>
		<div id="cytoscapeweb" style="width: 100%; height:80%;float:left; background-color: #efefef;">
				Cytoscape Web will replace the contents of this div with your graph.
		</div>
  </div>
  <div  style="width: 30%; float:right">
  <div id ="fieldpanel">
					<table id ='result'>
					<tr> <span class="formTextHeader">Concept Information</span></tr>
					<tr><td>Concept name:</td><td> ${concept.name}</td><tr>
					<tr><td>Concept Id:</td><td> ${concept.original_id}</td></tr>
					<tr><td>Concept Type:</td><td> ${concept.concept_types.getFullname()}</td><tr>
					<tr><td>Number of Compounds:</td><td> ${concept.num_compounds}</td><tr>
					</table>
				</div>
				</div>
</body>
</html>