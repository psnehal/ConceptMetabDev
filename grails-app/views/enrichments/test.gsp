<%@ page contentType="text/html;charset=UTF-8" %>
<html>

		  
		<head>
		<title>heat map test</title>
		<g:javascript src="heatmap_viewer.js" />
		<g:javascript src="elevatezoom/query.elevatezoom.js" />
		<g:javascript src="elevatezoom/jquery.elevateZoom-3.0.8.min.js" />
		<g:javascript library="jquery" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<script>
		$("#xaxis").elevateZoom({constrainType:"height", constrainSize:274, zoomType: "lens", containLensZoom: true, gallery:'gallery_01', cursor: 'pointer', galleryActiveClass: "active"});
		 //pass the images to Fancybox 
		 $("#xaxis").bind("click", function(e) { var ez = $('#xaxis').data('elevateZoom');	
		  $.fancybox(ez.getGalleryList()); 
		  return false;
		});
		



		</script>
		</head>
		<body bgcolor="#ffffff" onload="start(${height}, ${width})">
		<table border="1" cellpadding="0" cellspacing="0" width="100%" style="border-left-width: 10px; border-top-width: 10px;"> 
		<tbody id="heatmap" style="border-left-width: 0px; border-top-width: 0px;"><tr>
		<td width= "1000px" height= "100px"><ii:imageTag indirect-imagename="${ximage }" id="xaxis"  width="1000px" height="10px" border="0" alt="" /></td>
		<td><img id="placeHolder" src="images/hm_r1_c3.png" width="200px" height="100px"" border="0" alt="" /></td></tr>
		<tr><td width="1000px"><ii:imageTag indirect-imagename="${ bimage}" id="main" width="1000px" height="600px" border="0" alt="" background="black"/></td>
		<td><ii:imageTag indirect-imagename="${yimage}" id="yaxis" width="100%" height="600px" border="0" alt="" /></td></tr>
		</tbody></table>
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