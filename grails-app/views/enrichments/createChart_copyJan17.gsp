<html>
  <head>
<link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}" type="text/css">
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script type="text/javascript">
      google.load("visualization", "1", {packages:["corechart"]});
      google.setOnLoadCallback(drawChart);
      function drawChart() {
          //['GOMF':4899, 'GOCC':287, 'Enzyme':142, 'KEGG':68, 'Cluster':46]
          //[['database':'GOBP', 'no':340], ['database':'KEGG', 'no':4], ['database':'GOMF', 'no':57], ['database':'Cluster', 'no':1], ['database':'Enzyme', 'no':2]]
        var jsonData = ${dbc}
        console.log(jsonData);
        for (var i = 0, len = jsonData.length; i < len; ++i) {
            var db = objJSON[i];
            console.log("testing"+db);
        }
        var data = new google.visualization.DataTable(jsonData);

     
        
        var options = {
        	
         	   title: 'Concept Distribution by Type',
         	   'width': 500,
         	   'height': 300,
                legend:{position: 'right', textStyle: {color: 'blue', fontSize: 10, maxLines:2}},         
                 chartArea:{'left':20,'top':10,width:"100%",height:"100%"},
                 fontSize:'12',
                 colors:${clst},
                 
                // slices: {0: {color: '#FF0000'}, 1:{color: '#00FF08'}, 2:{color: 'blue'}, 3: {color: 'red'}, 4:{color: 'grey'}}
               
        
        
        };

        var chart = new google.visualization.PieChart(document.getElementById('chart_div'));
        chart.draw(data, options);

        google.visualization.events.addListener(chart, 'select', selectHandler);

        function selectHandler() {
            var selection = chart.getSelection();
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
   
  
   <g:form action="redirectView" method="get">
   
	<table id ="chart"> 
	<tr><td> <div id="chart_div" ></div></td>
	<td>  
	     
	      <table id = "chart">
	      <td><span class="formText">Select database to see results</span></td>
	      <tr>
	      <td><font size ="4" color="red">Please select atleast two database to see result</font></td>
	      </tr>
	      	<g:each in="${map}" status="i" var="test">
			 <tr><td><g:checkBox name="statement" value="${test.getKey()}" checked="false" />${ test.getKey()}</td>
			    	<td>${test.getValue() }</td> </tr> 
		    </g:each>
		    <td><p><g:checkBox name="statement" value="all" checked="true" />All</p></td><td>${rsize }</td>
		    </table>
	</td>
	</tr>

   <tr><td><br/><br/> <span class ="formTextHeader">Visualize / Check Results</span></td>
   <tr> <g:hiddenField name="id2"  value="${params.id2 }"/>
	   <g:hiddenField name="id1"  value="1.45e-323"/>
	   <g:hiddenField name="q" value="${params.q}" />
	   <g:hiddenField name="odds" value="${params.odds}" />
	   <g:hiddenField name="fil" value="${params.fil}" />
	   <td><g:submitButton name="network" class="submit"/>	
	   <g:submitButton name="table" class="submit"/>
	   <g:submitButton name="heatmap" class="submit"/>
	   <g:submitButton name="CompleteNetwork" class="submit"/></td>
	  
	   </tr>
   
   </table>
  
   
	  

	   </g:form>   
  
  </body>
</html>