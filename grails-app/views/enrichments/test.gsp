<%@ page contentType="text/html;charset=UTF-8" %>
<html>

		  
		<head>
		<title>heat map test</title>
		<script type="text/javascript" src="heatmap_viewer.js"></script>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		</head>
		<body bgcolor="#ffffff" onload="start()">
		<table border="10" cellpadding="0" cellspacing="0" width="770"
			style="border-left-width: 10px; border-top-width: 10px;">
		<tbody id="heatmap" style="border-left-width: 0px; border-top-width: 0px;">
		  <tr>
		   <td><img id="xaxis" src="images/x5.png" 
		   		width="4099" height="149" border="0" alt="" /></td>
		   <td><img id="placeHolder" src="images/hm_r1_c3.png" 
		   		width="250" height="149" border="0" alt="" /></td>
		  </tr>
		  <tr>
		   <td><img id="main" src="${bimage}" 
		   		width="4099" height="831" border="0" alt="" background="black"/></td>
		   <td><img id="yaxis" src="${yimage }" 
		   		width="250" height="831" border="0" alt="" /></td>
		  </tr>
		</tbody>
		</table>
		<div>
		Zoom with mouse wheel; pan with mouse drag. <br />
		You can pan (but not zoom) the x and y axis. <br />
		You can both pan and zoom the heatmap image. <br />
		Keyboard control: x/+/= (zoom in), z/-/_ (zoom out) <br />
		a (pan left), w (pan up), s (pan down), d (pan right)
		
		</div>
		<hr />
		<div id='debug'>
		Debugging information goes here: <br />
		</div>
		</body>
		

  
  
</html>