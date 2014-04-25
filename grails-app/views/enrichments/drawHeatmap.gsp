<%@ page contentType="text/html;charset=UTF-8" %>
<html>

		  
		<head>
		<title>heat map test</title>
		<meta name="layout" content="main">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		</head>
		
		
		<body>
		<br/>
		<div id ="title" style="float:none ;border = 1">
		<br/>
		<center>Data for the heatmap can be downloaded here :<g:link action="downloadFile" params="[filename: "${textimage}"]">here.</g:link>
		
		  <h1>Heat Map</h1>
		  </center>
		  </div>
		<table border="1" cellpadding="0" cellspacing="0" width="100%" style="border-left-width: 10px; border-top-width: 10px;"> 
		<tbody id="heatmap" style="border-left-width: 0px; border-top-width: 0px;"><tr>
			
		<tr><td width="1000px"><ii:imageTag indirect-imagename="${ bimage}" id="main" width="900px" height="600px" border="0" alt="" background="black"/></td>
		
		</tbody></table>
		
		<hr />
		<div id='debug'>Text file : <a href ="/tmp/${text}"></a> <br />
		${params.statement}
			
		 <g:form action="detailedHeatmap" method="get">
		    <g:hiddenField name="id1"  value="1.45e-323"/>                            
            <g:hiddenField name="network"  value="network"/>
            <g:hiddenField name="q" value="${params.q}" />
            <g:hiddenField name="id2" value="${params.id2}" />
             <g:set var="db" value="${params.statement.toString()}"/>    
             <g:hiddenField name="statement" value="${db}"/>
            <g:hiddenField name="odds" value="${params.odds}" />
            <g:hiddenField name="fil" value="${params.fil}" />
            <g:submitButton name="Submit"/>
          </g:form>
		 
		</body>
		
		
		

  
  
</html>