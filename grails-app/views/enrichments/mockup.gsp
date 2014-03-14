<%@ page contentType="text/html;charset=UTF-8" %>
<html>

<head>
<title>Heat Map </title>
  

  <link rel="stylesheet" href="${resource(dir: 'css', file: 'defaultTheme.css')}" type="text/css" media="screen">
  <link rel="stylesheet" href="${resource(dir: 'css', file: 'myTheme.css')}" type="text/css" media="screen">
       
  <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
  <script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
 
  <g:javascript src="mockup/jQueryRotate.js" />
  <g:javascript src="mockup/jquery.fixedheadertable.js" />
  
  <script>

//http://jimmybonney.com/articles/column_header_rotation_css/


  
  $(function() {  

	    $('#chart_table').fixedHeaderTable({
	    	footer: true,
	    	cloneHeadToFoot: true,
	    	footer: false,
	    	fixedColumns: 1,
	    });
	         
        
  });
  
  </script>
</head>
<body>
<div id= "fieldpanel">
<center>
				<a href="${createLink(uri: '/')}"><img src="${resource(dir: 'images', file: 'logoconceptmetab.gif')}" alt="Grails"  style="max-height: 400px; max-width: 400px;"/></a>
				<br/>
				<span class="header-sub">Metabolite Set Network Tool</span>
	</center>
	
</div>	
	
<div >
<div class="scrollable-table"  style="float:left">
  <table class="table table-striped table-header-rotated" id = "chart_table"  >
    <thead>
      <tr>
        <!-- First column header is not rotated -->
        <th class='row-header'>Compounds</th>
     ${html}
      </tr>
    </thead>
    
    <tbody>
     ${html2}
    </tbody>
  </table>
</div>

<div class="scrollable-image" style="float:right ; ">
<ii:imageTag indirect-imagename="${name }" width="300" height="600"  /> 
</div>

</div>

</body>
</html>
  
  