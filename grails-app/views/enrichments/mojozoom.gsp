<%@ page contentType="text/html;charset=UTF-8" %>
<html>

		  
		<head>
		<title>heat map test</title>		
		<g:javascript src="mojozoom.js" />
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'magnifier.css')}" type="text/css">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		</head>
		
		<body bgcolor="#ffffff">	
		<div id ="title" style="width: 100%;height:100%; float:left ;border = 1">
		<div id ="title" style="width: 100%; float:left ;border = 1">
		 	<img src="${resource(dir: 'images', file: 'da0f2aeb-6c65-47b4-a9f3-d08bc28179cd_4701_Xaxis4701.png')}" data-zoomsrc="${resource(dir: 'images', file: 'da0f2aeb-6c65-47b4-a9f3-d08bc28179cd_4701_Xaxis4701.png')}" data-zoomalwaysshow="true" id="myimage3" height="150" width="600" style="padding:0px;border:0px;" />
		 		<img src="${resource(dir: 'images', file: 'da0f2aeb-6c65-47b4-a9f3-d08bc28179cd_4701.png')}" data-zoomsrc="${resource(dir: 'images', file: 'da0f2aeb-6c65-47b4-a9f3-d08bc28179cd_4701.png')}" data-zoomalwaysshow="true" id="main" height="150" width="600" style="padding:0px;border:0px;" />
		 	 
			<div id="myimage3_zoom" style="position:relative;width:400px;height:150px;float:none;border:1px solid black;"></div>  
			<div id="main_zoom" style="position:relative;width:400px;height:150px;float:none;border:1px solid black;"></div>  
		</div>
		
		
		
		</body>
		

  
  
</html>