<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

<title>Insert title here</title>
</head>
<body>
  <div class="body">
  <div id="logoPanel" style="width: 100%; height:20%;float:left ;  border:1px solid">
		  		<center>
				<a href="${createLink(uri: '/')}"><img src="${resource(dir: 'images', file: 'logoconceptmetab.gif')}" alt="Grails"/></a>
				<br /> <br /> 
				<span class="header-sub">Metabolite Set Network Tool</span>
				</center>
				
				<img src="${createLink(controller: 'enrichments', action: 'test',params :[q:"${params.q}"])}" height= "800" width="600" />
	</div>
  </div>
</body>
</html>