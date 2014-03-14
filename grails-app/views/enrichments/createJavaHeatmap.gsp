<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

<link rel="stylesheet" href="${resource(dir: 'css', file: 'createChart.css')}" type="text/css">
<title>Java Heatmap</title>
</head>
<body>
<div style="width: 100%;height:100%;float:left;border:1px solid">
	<div id="logoPanel" style="width: 100%; height:30%;float:left ;  border:1px solid">
		  		<center>
			
				<br /> <br /> 
				<span class="header-sub">Metabolite Set Network Tool</span>
				</center>
</div>
	 <img class="thumbnail" src='${createLink(controller: "Enrichments", action: "createJavaHeatmap")}' />
	 
  <img src="<g:createLink controller="Enrichments" action="createJavaHeatmap"/>"/>
  

</body>
</html>