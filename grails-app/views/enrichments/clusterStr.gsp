<%@ page contentType="text/html;charset=UTF-8" %>

<html>

<head>
<title>Heat Map </title>
  
  <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
   
  
  <link rel="stylesheet" href="${resource(dir: 'css/clusterStr', file: 'myTheme.css')}" type="text/css" media="screen">
  <link rel="stylesheet" href="${resource(dir: 'css/clusterStr', file: '960.css')}" type="text/css" media="screen">
  <link rel="stylesheet" href="${resource(dir: 'css/clusterStr', file: 'defaultTheme.css')}" type="text/css" media="screen">
  
       
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
    $(".title").rotate({angle:ang});
    });
  </script>
  
  <script>
//http://jimmybonney.com/articles/column_header_rotation_css/  
  $(function() {
  	var w = 200;
  	var h = 10;

console.log(w + " and h is "+ h);
    var ang = -90;
    var top = w;
    $(".colhead_item").rotate({angle:ang,center:[0,0]});
    $(".colhead_item").css("top",top);
    $(".colhead_item").width(h);    
    $(".colhead_item").height(w);
  
   
   
   
    $('#myTable05').fixedHeaderTable({
        'fixedColumn'       : true,
        'footer'            : true,
        'height'            : '550',
        'width'             : '950',
        'autoShow'          : true,
        'cloneHeadToFoot'   : false
    });
    });


  </script>
</head>
<body>


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
<td ><table class="fancyTable" id="myTable05" cellpadding="0" cellspacing="0">
		<thead>
		    <tr class="colhead">
		 	<td class='row-header'><img src="${resource(dir: 'images', file: 'tableImage.png')}" alt="Grails"  style="max-height: 300px; max-width: 200px"/></td>
			${html }
			<td class="row-header">--</td>
			</tr>
		</thead>
		<tfoot>
			<tr>
			  <td></td>
			</tr>
		</tfoot>
		<tbody>
			<tr></tr>
			${html2 }
			
		</tbody>
		</table>
	
        	<div class="clear"></div>
        </div>
	
</td>

		

</tr>

</table>
</center>
</div>

</body>
</html>
  
  
  
  
  
	
        		
	
	


</body>
</html>
  