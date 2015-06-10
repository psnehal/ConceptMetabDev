<head>
 <meta name="layout" content="main" />
  <link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}" type="text/css">
  <link rel="stylesheet" href="${resource(dir: 'css', file: 'createChart.css')}" type="text/css">
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script type="text/javascript" src="https://www.google.com/jsapi"></script>

    <script type="text/javascript">
   
      google.load('visualization', '1.0', {'packages':['table']});

      google.setOnLoadCallback(drawTable);
      
      

      function drawTable(){
          //['GOMF':4899, 'GOCC':287, 'Enzyme':142, 'KEGG':68, 'Cluster':46]
          //[['database':'GOBP', 'no':340], ['database':'KEGG', 'no':4], ['database':'GOMF', 'no':57], ['database':'Cluster', 'no':1], ['database':'Enzyme', 'no':2]]
        var jsonData = ${dbc}
        console.log(jsonData);
        for (var i = 0, len = jsonData.length; i < len; ++i) {
            var db = objJSON[i];
            console.log("testing"+db);
        }
        
        var data = new google.visualization.DataTable(jsonData);

        
        
   
       // var chart = new google.visualization.PieChart(document.getElementById('chart_div'));
        //chart.draw(data, options);
        
       // var dashboard = new google.visualization.Dashboard(document.getElementById('dashboard_div'));
        var table = new google.visualization.Table(document.getElementById('chart2'));
        table.draw(data, {showRowNumber: true});
        

     

      
                

        
     

        //google.visualization.events.addListener(chart, 'select', selectHandler);
        //dashboard.bind( [stringFilter],table);

        // Draw the dashboard.
       // dashboard.draw(data);


        google.visualization.events.addListener(table, 'sort',
        	      function(event) {
        	        data.sort([{column: event.column, desc: !event.ascending}]);
        	        table.draw(data, {showRowNumber: true});
        	      });
        	        

      
     
	
        
      }

      function getUrlVars() {
          var vars = {};
          var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m,key,value) {
              vars[key] = value;
          });
          return vars;
      }



      
   
    </script>
    <r:require module="export"/>
    <export:resource />
  </head>
  <body>
    <g:set var="id2" value="${params.id2}"/>
	<g:set var="odds" value="${params.odds}"/>
				<g:set var="fil" value="${params.fil}"/>
				<g:link controller="concepts" action="show" id="${params.q}" params="[id2: id2, odds: odds,fil:fil]">Go Back to Filter Page</g:link></td>
				
				
				 <export:formats formats="['csv', 'excel']" action="redirectView" params="[q:"${params.q}", odds:"${params.odds }", id1:"1.45e-323",id2:"${params.id2 }",fil:"${params.fil }", statement:"${params.statement}", table:"table"]" /> 						
  <div style= "width: 100%">
    <div id ="title" style="width: 676px; height: 200px;float:center ;border = 1">
    <span class="formTextHeader">Concept  Information</span>
      			<ol class="property-list concepts">
	            <li class= "fieldcontain"><span class="property-label">Concept Name:</span> <span class="property-value">${con.name.capitalize()} </span></li>
	             <li class= "fieldcontain"><span class="property-label">Concept ID:</span><span class="property-value">${con.original_id} </span></li>
	             <li class= "fieldcontain"><span class="property-label">Concept Type:</span> <span class="property-value">${con.concept_types.getFullname()} </span></li>
	             <li class= "fieldcontain"><span class="property-label"># of compounds:</span> <span class="property-value">${con.num_compounds} </span></li>
	          	 </ol>
	 </div>
	</div>
   <br/>
   
   <br/>
            
		         <div id="chart2"></div>
   
   
 
  </body>
</html>