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
  $(function() {
  	var w = 100;
  	var h = 40;
    var ang = 90;
    var top = 180;
console.log('w is' + w + 'h is '+ h);
    $(".title").rotate({angle:ang});
    
   
    });
  </script>
  
  <script>
//http://jimmybonney.com/articles/column_header_rotation_css/  
  $(function() {  

	    $('#chart_table').fixedHeaderTable({
	    	footer: true,
	    	cloneHeadToFoot: true,
	    	footer: false,
	    	fixedColumns: 1,
	    	 themeClass: 'table table-striped table-header-rotated',
	    	 width: '600',
	    	 height : '800'
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
<center>
<table id = "title">
<tr>
	<td>
				<div id ="title">
					<table id ="result">
					<br/>
					
					<tr> <h1>Concept  Information</h1></tr>
					
					<tr><td>Concept name: </td><td> ${con.name} </td><tr>
					<tr><td>Concept Id:</td><td> ${con.original_id}</td></tr>
					<tr><td>Concept Type:</td><td> ${con.concept_types.getFullname()}</td><tr>
					<tr><td>Number of Compounds:</td><td> ${con.num_compounds}</td><tr>
					</table>
				</div>
		</td>
		<td rowspan = 2 >
		<div class="title" >
		<ii:imageTag indirect-imagename="test2.png"  /> 
		</div>
		</td>
</tr>
<tr>
<td >
		<div class="scrollable-table"  style="float:left">
		  <table class="table table-striped table-header-rotated" id = "chart_table"  >
		    <thead>
		      <tr>
		        <!-- First column header is not rotated -->
		      th class='row-header'><img src="${resource(dir: 'images', file: 'tableImage.png')}" alt="Grails"  style="max-height: 300px; max-width: 200px"/></th>
		     ${html}
		      </tr>
		    </thead>
		    
		    <tbody>
		     ${html2}
		    </tbody>
		  </table>
		</div>
</td>

		

</tr>

</table>
</center>
</div>

</body>
</html>
  
  