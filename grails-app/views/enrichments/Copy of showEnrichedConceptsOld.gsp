<head>
 <meta name="layout" content="main" />
  <link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}" type="text/css">
  <link rel="stylesheet" href="${resource(dir: 'css', file: 'createChart.css')}" type="text/css">
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script type="text/javascript" src="https://www.google.com/jsapi"></script>

    <script type="text/javascript">
   
      google.load('visualization', '1.0', {'packages':['controls']});

      google.setOnLoadCallback(drawDashboard);
      
      //google.setOnLoadCallback(drawChart);
      function drawDashboard(){
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
        
        var dashboard = new google.visualization.Dashboard(document.getElementById('dashboard_div'));

        var donutRangeSlider = new google.visualization.ControlWrapper({
            'controlType': 'NumberRangeFilter',
            'containerId': 'filter_div',
            'options': {
              'filterColumnLabel': 'Odds Ratio',
              
            	  
            }
          });

        var stringFilter = new google.visualization.ControlWrapper({
            'controlType': 'StringFilter',
            'containerId': 'control1',
            'options': {
              'filterColumnLabel': 'Odds Ratio',
              'ui':{'label':'Odds-Ratio','labelStacking':'vertical','cssClass':"TestStyle"}
               
            }
          });
                

        var cssClassNames = {
        	    'headerRow': 'orange-background gold-border italic-darkblue-font large-font bold-font',
        	    'tableRow': '',
        	    'oddTableRow': 'beige-background',
        	    'selectedTableRow': 'orange-background large-font',
        	    'hoverTableRow': '',
        	    'headerCell': 'gold-border',
        	    'tableCell': 'gold-border ',
        	    'rowNumberCell': 'black-font'};


        var table = new google.visualization.ChartWrapper({
            'chartType': 'Table',
            'containerId': 'chart2',
            'options': {
            	  
            	'showRowNumber': true, 'allowHtml': true, 'cssClassNames': cssClassNames	
            }
       
          });

        
     

        //google.visualization.events.addListener(chart, 'select', selectHandler);
        dashboard.bind( [stringFilter],table);

        // Draw the dashboard.
        dashboard.draw(data);


      
     
	
        
      }

      function getUrlVars() {
          var vars = {};
          var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m,key,value) {
              vars[key] = value;
          });
          return vars;
      }



      
   
    </script>
  </head>
  <body>
    <g:set var="id2" value="${params.id2}"/>
	<g:set var="odds" value="${params.odds}"/>
				<g:set var="fil" value="${params.fil}"/>
				<g:link controller="concepts" action="show" id="${params.q}" params="[id2: id2, odds: odds,fil:fil]">Go Back to Filter Page</g:link></td>						
  
     <div id="dashboard_div">
   
            <div id="filter_div"></div>
		           <div id="chart2"></div>
		         <div id="control1"></div>
    </div>
   
 
  </body>
</html>