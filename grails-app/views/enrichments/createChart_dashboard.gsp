<html>
  <head>
  <link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}" type="text/css">
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

        console.log("rraylist is "+ ${clst})
        
   
       // var chart = new google.visualization.PieChart(document.getElementById('chart_div'));
        //chart.draw(data, options);
        
        var dashboard = new google.visualization.Dashboard(document.getElementById('dashboard_div'));

        var donutRangeSlider = new google.visualization.ControlWrapper({
            'controlType': 'NumberRangeFilter',
            'containerId': 'filter_div',
            'options': {
              'filterColumnLabel': 'no'
            }
          });

        var pieChart = new google.visualization.ChartWrapper({
            'chartType': 'PieChart',
            'containerId': 'chart1',
            'options': {
            	   title: 'Concept Distribution by Type',
            	   'width': 500,
            	   'height': 300,
                   legend:{position: 'right', textStyle: {color: 'blue', fontSize: 10, maxLines:2}},         
                    chartArea:{'left':100,'top':30,width:"100%",height:"70%"},
                    fontSize:'12',
                    pieSliceText: 'value',
                   // slices: {0: {color: '#FF0000'}, 1:{color: '#00FF08'}, 2:{color: 'blue'}, 3: {color: 'red'}, 4:{color: 'grey'}}
                    colors:${clst}
            }
          });


        var table = new google.visualization.ChartWrapper({
            'chartType': 'Table',
            'containerId': 'chart2',
            'options': {
            	   'width': '400px',
            		'height': '300px',
            		 colors:${clst}
            }
       
          });

        
     

        //google.visualization.events.addListener(chart, 'select', selectHandler);
        dashboard.bind( [donutRangeSlider],[table,pieChart]);

        // Draw the dashboard.
        dashboard.draw(data);
        google.visualization.events.addListener(table, 'select', selectHandler);

        function selectHandler() {
        	visualization.setSelection([])
            var selection = table.getChart().getSelection();
            console.log(selection);
            var message = '';
            for (var i = 0; i < selection.length; i++) {
              var item = selection[i];
              if (item.row != null && item.column != null) {
                  console.log('1');
                var str = data.getFormattedValue(item.row, item.column);
                message += '{row:' + item.row + ',column:' + item.column + '} = ' + str + '\n';
              } else if (item.row != null) {
                  console.log('2');
                var str = data.getFormattedValue(item.row, 0);
                message += str+ '\n';
              } else if (item.column != null) {
                  console.log('3');
                var str = data.getFormattedValue(0, item.column);
                message += '{row:none, column:' + item.column + '}; value (row 0) = ' + str + '\n';
              }
            }
            if (message == '') {
              message = 'nothing';
            }
           // alert('You selected ' + message);
            var first = getUrlVars()["id1"];
            var second = getUrlVars()["id2"];
            var q = getUrlVars()["q"];
           console.log("q ="+ q);
           var query = 'createDb?id='+q+'&db='+message+'&id1='+first+'&id2='+second;
            window.open(query,'Popup','toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=no, width=620,height=500,left=430,top=23');
          }

        
	
        
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
  
    
    <div>${allR }</div>
  
    
     <div id="dashboard_div">
       <table>
        
           <tr>
		          <td style='width: 300px;'>
		           <div id="chart1"></div>
		           </td>
		           <td>
		             <div id="chart3"></div>
		           </td>
		           <td>
		           <div id="chart2"></div>
		           </td>
       </tr>
       <tr style='vertical-align: top'>
          <td style='width: 300px; font-size: 0.9em;'>
            <div id="filter_div"></div>
            <div id="control2"></div>
            <div id="control3"></div>
          </td>
           </tr>
      </table>
    
      
    </div>
   
  
  </body>
</html>