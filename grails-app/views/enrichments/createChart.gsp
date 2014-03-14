<html>
  <head>
<link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}" type="text/css">
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    
    <script type="text/javascript">
    function checkAll(source)
    {


    		checkboxes = document.getElementsByName('statement');
    		 console.log(checkboxes.length);
    		 
    		  for (var i =0, n=checkboxes.length;i<n;i++)
    			  {
    			  console.log("its iniside for loop");
    			  checkboxes[i].checked = source.checked;
    			  }
    		
    }


    function validation(source)
    {

    	checkboxes = document.getElementsByName('statement');
		
		var check = true;

		 for (var i =0, n=checkboxes.length;i<n;i++)
		  {
		  if(checkboxes[i].checked)
			  {

				check = false;
			  }
		  }

		  if(check)
			  {

				alert("Please atlest select one database to see results");
			  }

        }

   
</script>


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
                //legend:{position: 'right', textStyle: {color: 'blue', fontSize: 10, maxLines:2}},
                 legend:{position: 'none'},         
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

      function cutChart(checkBool)
      {
          alert(checkBool);
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
   
  
   <g:form action="redirectView" method="get" id="myform">   
	<table id ="chart"> 
	<tr><td> <div id="chart_div" ></div></td>
	<td>       
	      <table id = "chart">
	      <td><span class="formText">Select database to see results</span></td>
	      <tr>
	      </tr>
	      	<g:each in="${map}" status="i" var="test">
			 <tr>
			 <td><g:checkBox name="statement" value="${test.getKey()}" checked="false" />${ test.getKey()}  </td>
			 <td> <div class="foo" style="background-color:${emptyList.getAt(i)}"></div></td>
			<td>${test.getValue() }</td> </tr> 
		    </g:each>
		    <td><g:checkBox name="checkAllAuto" value="all" id="checkAllAuto" onclick="checkAll(this);" checked="false" />All</p></td><td>${rsize }</td>
		    <td><g:submitButton name="chart" class="submit" /></td>
		    </table>
	</td>
	</tr>
   <tr><td><br/><br/> </td>
   <tr></tr>
   </table>
   <center>
   <h1>Visualize / Check Results</h1>
    <g:hiddenField name="id2"  value="${params.id2 }"/>
	   <g:hiddenField name="id1"  value="1.45e-323"/>
	   <g:hiddenField name="q" value="${params.q}" />
	   <g:hiddenField name="odds" value="${params.odds}" />
	   <g:hiddenField name="fil" value="${params.fil}" />
	   <g:submitButton name="network" value="Network" class="submit"/>	
	   <g:submitButton name="table" value="Table" class="submit" onclick="validation(this);"/>
	   <g:submitButton name="heatmap" value="Heatmap" class="submit"/>
	   <g:submitButton name="CompleteNetwork" value="Complete Network" class="submit"/>
  </center>   
	   </g:form>   
  
  </body>
</html>