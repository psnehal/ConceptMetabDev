<%@ page import="conceptmetab.Meshid2treenum" %>
<%@ page contentType="text/html;charset=ISO-8859-1" %>
<html lang="en">
<head>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<link rel="stylesheet" href="${resource(dir: 'css', file: 'createChart.css')}" type="text/css">
<title>Complete Network for ${compoundsInstance}</title>
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
			//itializes slider
				
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
                 { name: "db_id", type: "string" },
                 { name: "rel", type: "integer" }
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
         	width:3,
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
	            var ed =window.open('${createLink(action:'displayEdge')}?q='+ id+'&con='+con,"_blank",'toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=no, width=620,height=500,left=430,top=23');
	            ed.onload = function() { this.document.title = "Edge Information"; } 
	            
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





</script> 
        
        
</head>
<body>
	<div id =header> 			 
				<a href="${createLink(uri: '/')}"><img src="${resource(dir: 'images', file: 'logoconceptmetab.gif')}" alt="Grails"  style="max-height: 300px; max-width:300px;"/></a>
				<br/><span class="header-sub" style="font-size: .70em;">Compound Set Network Tool</span>
				
	</div>
	<div id="lefttop"> 
	<br/>
		
	 </div>

<div id="navigation">
	<div id="welcomeDiv"  style="display:block;" class="answer_list" >
	
		<div id ="fieldpanel">			
				<span class="formTextHeader">Compounds Information</span>		
				<table id ='result'>
				<tr><td>Compound Name:</td><td> ${compoundsInstance.name}</td><tr>
				<tr><td>Compound KEGG-ID:</td><td> ${compoundsInstance.kegg_id}</td><tr>
				<tr><td>Compound PUBCHEM-ID:</td><td> ${compoundsInstance.pubchem_id}</td><tr>
				<tr><td>Compound Number Of Compounds:</td><td> ${compoundsInstance.num_concepts}</td><tr>
				</table>
			</div>
	
	</div>
</div>	
		

		 <div id ="content">
				<div id = "note"></div>
				<div id="cytoscapeweb" style="width:100%;height:100%;background-color: #efefef;">
					Cytoscape Web will replace the contents of this div with your graph.
				</div> 
		</div>
  

				
</body>
</html>