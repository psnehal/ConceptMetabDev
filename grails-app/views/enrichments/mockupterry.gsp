<%@ page contentType="text/html;charset=UTF-8" %>
<html>

<head>
<title>heat map test</title>
  
  
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
	         

    var table = document.getElementById("chart_table");
    for (var i = 1, row; row = table.rows[i]; i++) {
        //iterate through rows
        //rows would be accessed using the "row" variable assigned in the for loop
        for (var j = 0, col; col = row.cells[j]; j++) {
            //iterate through columns
            //columns would be accessed using the "col" variable assigned in the for loop
           //http://www.computerhope.com/htmcolor.htm
            var cell = col.innerText;
            var a = parseInt(cell)
            console.log(typeof(a));
            if (a <= 100 && a > 90)
                {
            	 console.log(col.innerText);
                	col.style.backgroundColor = "red ";
                }
            else if (a <= 90 && a > 80)
            {
        	 console.log(col.innerText);
            	col.style.backgroundColor = "#FF3300 ";
            	
            }
            else if (a <= 80 && a > 70)
            {
        	 console.log(col.innerText);
            	col.style.backgroundColor = "#ff6600";
            }
            else if (a <= 70 && a > 60)
            {
        	 console.log(col.innerText);
            	col.style.backgroundColor = "#ff9900";
            }
            else if (a <= 60 && a > 50)
            {
        	 console.log(col.innerText);
            	col.style.backgroundColor = "#FFCC00 ";
            }
            else if (a <= 50 && a > 40)
            {
        	 console.log(col.innerText);
            	col.style.backgroundColor = "#FFFF00";
            }
            else if (a <= 40 && a > 30)
            {
        	 console.log(col.innerText);
            	col.style.backgroundColor = "#ffff33";
            }
            else if (a < 30 && a > 20)
            {
        	 console.log(col.innerText);
            	col.style.backgroundColor = "#ffff66";
            }
            else if (a <= 20 && a > 10)
            {
        	 console.log(col.innerText);
            	col.style.backgroundColor = "#ffff99";
            }
            else if (a <=10 && a>0)
            {
        	 console.log(col.innerText);
            	col.style.backgroundColor = "#ffffcc";
            }
            else if (a= 0)
            {
        	 console.log(col.innerText);
            	col.style.backgroundColor = "white";
            }
            
        }
    }

  


	   			
            		

        
  });
  


	 
  
  
  </script>
  
    <script>
  $(function() {
  	var w = 100;
  	var h = 40;
    var ang = -45;
    var top = 80;
console.log('w is' + w + 'h is '+ h);
    $(".tableHeader").rotate({angle:ang,center:[0,0]});
    $(".tableHeader").css("top",top);
    $(".tableHeader").width(h);    
    $(".tableHeader").height(w);
    $(".item1").css("background","#AA0000");
    $(".item2").css("background","#FF0000");
    $(".item3").css("background","#FF6600");
    $("#chart_table").fixedHeaderTable({ footer: true, cloneHeadToFoot: true, fixedColumn: false,  height: 500 });
    });
  </script>
</head>
<body>

<table class="table table-striped table-header-rotated" id="chart_table" style="position:relative">
<thead>
<tr class="row-header">
 <td><div style='position:relative' class='tableHeader'>compound </div></td>
	${html}
	
</tr>
</thead>
<tfoot>
<tr>
  <td></td>
</tr>
</tfoot>
<tbody>
${html2 }
</tbody>
</body>
</html>
  
  