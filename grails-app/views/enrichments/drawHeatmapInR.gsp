<%@ page contentType="text/html;charset=UTF-8" %>
<html>

		  
		<head>
		<title>heat map test</title>
		<g:javascript src="heatmap_viewer.js" />
		 <link rel="stylesheet" href="${resource(dir: 'css/clusterStr', file: 'defaultTheme.css')}" type="text/css" media="screen">
		 <link rel="stylesheet" href="${resource(dir: 'css/clusterStr', file: 'myTheme.css')}" type="text/css" media="screen">
		  
		  
		 
		
		
		
		<g:javascript library="jquery" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		
		</head>
	
		<body bgcolor="#ddd" onload="start(${height}, ${width})">
		
		<div id= "fieldpanel2">
		<center>
		<div id =header> 			 
			<a href="${createLink(uri: '/')}"><img src="${resource(dir: 'images', file: 'logoconceptmetab.gif')}" alt="Grails"  style="max-height: 300px; max-width:300px;"/></a>
			<br/><span class="header-sub" style="font-size: .70em;">Compound Set Network Tool</span>
		</div>
		</center>
		<hr/>
			

	<table>
	<tr>
	<td colspan="2">
				<div id ="title">
								
					 <h1>Concept  Information</h1>
					 <hr/>
					 
				
					<center>
					 <table width="770" id="result" >
					 <tr>
					 <td><span id="original_id-label" class="property-label"><b>Concept Name: </b>${con.name} </span></td>
					 <td><span id="original_id-label" class="property-label"><b>Concept ID: </b>${con.original_id}</span></td>
					 </tr>
					  <tr>
					 <td><span id="original_id-label" class="property-label"><b>Concept Type: </b>${con.concept_types.getFullname()}</span></td>
					 <td><span id="original_id-label" class="property-label"><b>Number of Compounds: </b>${con.num_compounds}</span></td>
					 </tr>
					 </table>	 
					 
					 </center>
					 
					
				</div>
				
				 <div style="width:300px">
				  <h1>Legend</h1><span class="footnote">(Numbers indicate % of enriched concepts containing the compound)</span>
						
				<table width = "90%" id="result">
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
					 <td><span class="property-label">0</span></td>
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

					
		</tr>
		</table>
		<table border="5" cellpadding="0" cellspacing="0" width="770" style="border-left-width: 5px; border-top-width: 5px;"> 
		<tbody id="heatmap" style="border-left-width: 0px; border-top-width: 0px;"><tr>
		<td><ii:imageTag indirect-imagename="${ximage }" id="xaxis" border="0" alt="" /></td>
		<td><img id="placeHolder" src="images/hm_r1_c3.png"  border="0" alt="" /></td></tr>
		<tr><td><ii:imageTag indirect-imagename="${bimage}" id="main" border="0" alt="" background="black"/></td>
		<td><ii:imageTag indirect-imagename="${yimage}" id="yaxis" border="0" alt="" /></td></tr>
		</tbody></table>
		<div>
		<h1>Instructions</h1>
		<span id="original_id-label" class="property-label">Zoom with mouse wheel; pan with mouse drag.
		You can pan (but not zoom) the x and y axis. You can both pan and zoom the heatmap image. 
		Keyboard control: x/+/= (zoom in), z/-/_ (zoom out) a (pan left), w (pan up), s (pan down), d (pan right)</span>
		
		</div>
		<hr />
		
		</div>
		</body>
		

  
  
</html>