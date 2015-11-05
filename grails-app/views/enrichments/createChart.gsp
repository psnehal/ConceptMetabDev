<html>
  <head>

    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
     <g:javascript library='jquery' />   
      <meta name="layout" content="main">
    
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

    function checkAllChart(check)
    {


    		checkboxes = document.getElementsByName('statement');
    		 console.log(checkboxes.length);
    		 if (check ==0 )
        		 {
			    		  for (var i =0, n=checkboxes.length;i<n;i++)
			    			  {
			    			  console.log("its iniside for loop");
			    			  checkboxes[i].checked = true;
			    			  }
        		 }
    		 else
        		 {
			    			 for (var i =0, n=checkboxes.length;i<n;i++)
			   			  {
			   			  console.log("its iniside for loop");
			   			  checkboxes[i].checked = false;
			   			  }



        		 }
    }


    function validation2(source)
    {

    	checkboxes = document.getElementsByName('statement');
		
		var check = true;

		 for (var i =0, n=checkboxes.length;i<n;i++)
		  {
		  if(checkboxes[i].checked)
			  {

				check = false;
				console.log(check);
			  }
		  }
		
		  if(check)
			  {
				alert("Please atleast select one database to see results");
				return false;
				
			  }
		  else
			  {
			  return true;
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
        		
         	   'width': 425,
         	   'height': 325,
                //legend:{position: 'right', textStyle: {color: 'blue', fontSize: 10, maxLines:2}},
                 legend:{position: 'none'},         
                 chartArea:{'left':5,'top':10,width:"100%",'height':'100%'},
                 fontSize:'12',
                 colors:${clst},
                 is3D:false,
                 pieSliceText:'percentage',
                 slices:{},
                 
                // slices: {0: {color: '#FF0000'}, 1:{color: '#00FF08'}, 2:{color: 'blue'}, 3: {color: 'red'}, 4:{color: 'grey'}}
               
        
        
        };

        var chart = new google.visualization.PieChart(document.getElementById('chart_div'));
        chart.draw(data, options);
        google.visualization.events.addListener(chart, 'select', explodeSlide);
        
        document.getElementById('update').onclick = function() {


          var myArray = [];
       	  var wanted=[];
       	  var unwanted=[];
       	  var count = 0;
       	  var count2 = 0;
       	  var data = new google.visualization.DataTable(${dbc});

 			console.log("1");
       	  checkboxes = document.getElementsByName('statement');
     	    		for (var i =0, n=checkboxes.length;i<n;i++)
     	  		  {
     	    			if(checkboxes[i].checked)
     	   			  {
     	  				wanted[count]=checkboxes[i].value;
     	  				count++;
     	
     	   			  }
     	    			else{
         	    			console.log("from unwanted loop"  + checkboxes[i].value);
     	    	  				unwanted[count2]=checkboxes[i].value;
     	    	  				count2++;
     	    	  			}
     	  		  }	  
     	    		console.log("2");
     	    		for (var y = 0, maxrows = data.getNumberOfRows(); y < maxrows; y++) {
     	
     	    			for (var i =0, n=wanted.length;i<n;i++)
     	    	  			{
     	    				 if (data.getValue(y, 0) == wanted[i]) {
     	    					 options.slices[y] = {offset:'.4'} ;
     	    					console.log("slices");   
     	    					console.log("data value " + data.getValue(y, 0) + " wanted " + wanted[i] + "with y" + y);
     	    					console.log(options.slices[y].offset)	  
     	    				 }	
     	    	  		}
     	    			for (var i =0, n=unwanted.length;i<n;i++)
 	    	  			{
 	    				 if (data.getValue(y, 0) == unwanted[i]) {
 	    					options.slices[y] = { color: 'grey',pieSliceText:'none'} ;
 	    					
 	    					console.log("slices");   
 	    					console.log("data value " + data.getValue(y, 0) + " wanted " + unwanted[i] + "with y" + y);
 	    					console.log(options.slices[y])	  
 	    				 }	
 	    	  		}
     	    	  		
     	    		}  

     	    		options.pieSliceText = 'percentage';
     	        	options.width = '480';
     	        	options.height= '350';
     	        	options.chartArea['width']='70%';
     	        	options.chartArea['height']='70%';
     	        	options.chartArea['left']='25';
     	        	options.chartArea['top']='25'; 	
     	        
     	        	options.is3D = true;
     	    		options.pieSliceText = 'none';
     				 chart.draw(data, options);

		
        }

        document.getElementById('selectall').onclick =  function() {
        	console.log("inside function");   
        	for (var y = 0, maxrows = data.getNumberOfRows(); y < maxrows; y++) {

        		console.log("slices" + y);   
        		 options.slices[y] = {offset:'0.4'} ;

        	}

        	options.pieSliceText = 'percentage';
        	options.width = '480';
        	options.height= '350';
        	options.chartArea['width']='70%';
        	options.chartArea['height']='70%';
        	options.chartArea['left']='25';
        	options.chartArea['top']='25'; 	
        
        	options.is3D = true;
        	 checkAllChart(0);
        	 chart.draw(data, options);


        }

        document.getElementById('selectnone').onclick =  function() {

         	for (var y = 0, maxrows = data.getNumberOfRows(); y < maxrows; y++) {

        		console.log("slices" + y);   
        		 options.slices[y] = {offset:'0'} ;

        	}
        	options.is3D = false;
        	options.pieSliceText = 'percentage';
        	 checkAllChart(1);
        	 chart.draw(data, options);

        }

      
        function selectHandler() {
            var selection = chart.getSelection();
            var message = '';
            for (var i = 0; i < selection.length; i++) {
              var item = selection[i];
              if (item.row != null && item.column != null) {
                  console.log('1');
                var str = data.getFormattedValue(item.row, item.column);
                console.log(str);
                message += '{row:' + item.row + ',column:' + item.column + '} = ' + str + '\n';
              } else if (item.row != null) {
                  console.log('2');
                var str = data.getFormattedValue(item.row, 0);
                console.log(str);
                message += str+ '\n';
              } else if (item.column != null) {
                  console.log('3');
                var str = data.getFormattedValue(0, item.column);
                console.log(str);
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


      function explodeSlide()
      {
    	  var myArray = [];
    	  var wanted=[];
    	  var unwanted=[];
    	  var count = 0;
    	  var count2 = 0;
    	  var data = new google.visualization.DataTable(${dbc});
		 
    	  var options = {
    	        	
            	   title: 'Concept Distribution by Type from explosion',
            	   'width': 400,
            	   'height': 300,
                   //legend:{position: 'right', textStyle: {color: 'blue', fontSize: 10, maxLines:2}},
                    legend:{position: 'none'},         
                    chartArea:{'left':5,'top':10,width:"70%",height:"70%"},
                    fontSize:'12',
                    colors:${clst},
                    slices:{},
                    
                   // slices: {0: {color: '#FF0000'}, 1:{color: '#00FF08'}, 2:{color: 'blue'}, 3: {color: 'red'}, 4:{color: 'grey'}}
                  
           
           
           };
    	  checkboxes = document.getElementsByName('statement');
  	    		for (var i =0, n=checkboxes.length;i<n;i++)
  	  		  {
  	    			if(checkboxes[i].checked)
  	   			  {
  	  				wanted[count]=checkboxes[i].value;
  	  				count++;
  	
  	   			  }
  	    			else{
  	    	  				unwanted[count2]=checkboxes[i].value;
  	    	  				count2++;
  	    	  			}
  	  		  }	  

  	    		for (var y = 0, maxrows = data.getNumberOfRows(); y < maxrows; y++) {
  	
  	    			for (var i =0, n=unwanted.length;i<n;i++)
  	    	  			{
  	    				 if (data.getValue(y, 0) == unwanted[i].value) {
  	
  	    					 options.slices[y] = {offset:2};
  	    	  				 
  	
  	    				 }
  	    	  			
  	    	  			}
  	    	  		
  	    		}     	  
  		 //chart.draw(data, options);

          }

     

      function onclick()
      {
          alert("inside onclick function");
    	  explodeSlide();

          }
        
     
    </script>
  </head>
  <body>
   
  
   <g:form action="redirectView" method="get" id="myform"  onsubmit="return validation2()">   
   <div  style="height:100%;border:1px;">  
 
	<div style=" width: 450px ;float:left;border:1px; ">
	  <center><span class="formText">Concept Distribution by Annotation Database</span></center>
	 <div id="chart_div" ></div>
	 <br/>
	 </div>
	 
	 <div style="width: 420px;float:right ;border:1px; margin-right:45px;">  	 
	 
			 <table id = "chart" style="margin-right:100px;" >
			      <tr>
			      <td colspan="2"><span class="formText">Select one or more Annotation Database</span><br/></td>
			     			      <g:each in="${map}" status="i" var="test">
				  <tr>
					 	<td nowrap><g:checkBox name="statement" value="${test.getKey()}" checked="false" />${ test.getKey()}  </td>
					 	<td> <div class="foo" style="background-color:${emptyList.getAt(i)}"></div></td>
						<td>${test.getValue() }</td> 
						</tr> 
				   </g:each>
				   <tr>
				   		 <td><g:checkBox name="checkAllAuto" value="all" id="checkAllAuto" onclick="checkAll(this);" checked="false" />All</td><td>${rsize }</td>
				   </tr>
				   <tr> 
					   <td colspan = 3>
						   <input type="button" name="update" id="update" value="Update Chart"  class="submitsmall" onclick="javascript: onclick();" />
						   <input type="button" name="selectall" id="selectall" value="Select All" class="submitsmall" />
						   <input type="button" name="update" id="selectnone" value="Select None" class="submitsmall"/>
					   </td>
				   </tr>
				    
				</table>
    </div>	
    
    </div>
   <center>
   
   <div style="width: 800px; clear: both;">
   <h1>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;View Enrichment Results </h1>
    <g:hiddenField name="id2"  value="${params.id2 }"/>
	   <g:hiddenField name="id1"  value="1.45e-323"/>
	   <g:hiddenField name="q" value="${params.q}" />
	   <g:hiddenField name="odds" value="${params.odds}" />
	   <g:hiddenField name="fil" value="${params.fil}" />
	   <g:submitButton name="network" value="Star Network" class="submit" />	
	   <g:submitButton name="CompleteNetwork" value="Complete Network" class="submit"/>
	   <g:submitButton name="table" value="Table" class="submit"/>
	   <g:submitButton name="heatmap" value="Heatmap" class="submit"/>
	  
	  <!-- --  <g:submitButton name="testHeatMapInR" value="testHeatMapInR" class="submit"/>-->
	      </div>  
  </center>   
	   </g:form> 
	
  
  </body>
</html>