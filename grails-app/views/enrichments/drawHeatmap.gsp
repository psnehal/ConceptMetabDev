<%@ page contentType="text/html;charset=UTF-8" %>
<html>

		  
		<head>
		<title>heat map test</title>
		<meta name="layout" content="main">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	

		<script>
	    function displayMsg(source)
	    {

	    	return alert("Interactive view is based on size of the matrix. If matrix size is less than 300X300(concepts X compounds) then table will be dispalyed.Otherwise image with zoom option will be displayed ");
	    		
	    }


		   
		</script>		
  <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
  <script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script> 
  <g:javascript src="clusterStr/jQueryRotate.js" />
  <g:javascript src="clusterStr/jquery.fixedheadertable.js" />
 
  
	  <script>
  $(function() {
  	var w = 100;
  	var h = 40;
    var ang = 90;
    var top = 180;
    console.log('w is' + w + 'h is '+ h);
    $(".title2").rotate({
        angle:ang,
        center: ["40%", "80%"], 
        });
    });
  </script>
	

		</head>
		
	
		
		
		<body>
		<br/>
		<div id ="title" style="float:none ;border = 1">
		<center>
		  <h1>Heat Map</h1>
		 
		  <span id="name-label" class="property-label">Data for the heatmap can be downloaded :<g:link action="downloadFile" params="[filename: "${textimage}"]">here.</g:link></span>		  
		  <g:form action="detailedHeatmap" method="get">
	 	  <g:submitButton name="submit" value="Click here for Interactive Heatmap" class="myButton" onclick="displayMsg()"/>
	 	  <br/><br/>
		    <g:hiddenField name="id1"  value="1.45e-323"/>                            
            <g:hiddenField name="network"  value="network"/>
            <g:hiddenField name="q" value="${params.q}" />
            <g:hiddenField name="id2" value="${params.id2}" />
            </center>
		  </div>
		  
	  
		  <table>
		  <tr>
		  <td colspan="3"> <span class="property-label">Legends</span></td>
		  </tr>
		  <tr>
			  <td>
			   <div style="width:300px">
			   <br/>
				<table class="legend">
				<tr>
					<td bgcolor="white"></td> <!--0-->
					 <td bgcolor="#FFFF00"></td> <!--0-10-->
					 <td bgcolor="#FFE100"></td><!--10-20-->
					 <td bgcolor="#FFC800"></td><!--20-30-->
					 <td bgcolor="#FFAF00"></td><!--30-40-->
					 <td bgcolor="#FF9600"></td><!--40-50-->
					 <td bgcolor="#FF7D00"></td><!--50-60-->
					 <td bgcolor="#FF6400"></td><!--60-70-->
					 <td bgcolor="#FF4B00"></td><!--70-80-->
					 <td bgcolor="#FF3200"></td><!--80-90-->
					  <td bgcolor="#FF0000"></td><!--90-100-->
					</tr>
					<tr>
					 <td>0</td>
					 <td>10</td>
					 <td>20</td>
					 <td>30</td>
					 <td>40</td>
					 <td>50</td>
					 <td>60</td>
					 <td>70</td>
					 <td>80</td>
					 <td>90</td>
					 <td>100</td>
					</tr>
			</table>
		</div>
		 </td>
		  <td> <span class="property-label">X-axis : Concepts </span></td>
		  <td> <span class="property-label">Y-axis : Compounds</span> </td>
		  </tr>
		  </table>
		  
		  ${bimage }
		<div class="title2" id ="title2"> 
		<ii:imageTag indirect-imagename="${bimage}"  /> 
		</div>
		
		
		<% 
		def db = []
	 
		 if(params.statement instanceof java.lang.String)
		 {
			db.add(params.statement);
		 }
		 else
		 {
			 db = params.statement
		 }
		 
		 %>
       <g:each in="${db}" status="i" var="test">	
						   		<input type="hidden" name="statement" value="${test}">	
							</g:each>  
			
          <g:hiddenField name="odds" value="${params.odds}" />
          <g:hiddenField name="fil" value="${params.fil}" />           
          <g:hiddenField name="filename" value="${textimage}" />             
          </g:form>
          
          
          
  
		 
		 
		</body>
		
		
		

  
  
</html>