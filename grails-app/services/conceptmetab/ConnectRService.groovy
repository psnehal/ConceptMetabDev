package conceptmetab
import grails.converters.JSON
import org.conceptmetab.heatMapResults
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.RList
import org.rosuda.REngine.Rserve.*;
import org.rosuda.Rserve.*;



class ConnectRService {
boolean transactional = false


RConnection c = new RConnection();



			def clusterAnalysis(def te)
			{
				println("inside Service")
					RConnection connection;
			connection = new RConnection("localhost",
				   6311);
			   
			   println("Connected to R")
			   
			connection.assign("mat", te);
			connection.voidEval("library('rjson')");
			connection.voidEval("library('gplots')");
			connection.voidEval("test <- fromJSON (mat)");
			connection.voidEval("m = do.call(rbind, test)");
			connection.voidEval("data = data.frame(m[2:nrow(m), 2:ncol(m)], row.names=m[2:nrow(m),1],stringsAsFactors=F)");
			connection.voidEval("colnames(data) = m[1,2:ncol(m)]");
			//		/pdf("heatmap.pdf", height=10, width=10)
			/*
			connection.voidEval('hmcols<-colorRampPalette(c("white","yellow","red"))(256)')
			//connection.voidEval('test2 =heatmap(data.matrix(data),col=hmcols)')
			connection.voidEval("png('/tmp/images/test.png')")
			connection.voidEval("test2 =heatmap(data.matrix(data),col=hmcols))");
			connection.voidEval("dev.off()")
			*/
			
			connection.voidEval('hclust.ave <- function(x) hclust(x, method="average")')
			connection.voidEval('par(bg="#F7F8E0")')
			connection.voidEval('myCol <- c("white","#ffffcc","#ffff99","#ffff66","#ffff33","#FFFF00","#FFCC00","#ff9900","#ff6600","#FF3300","red" )')
			connection.voidEval(' myBreaks <- c(0,1, 10, 20,30,40,50,60,70,80,90,100)')
			
			try{
				println("its in png block")
			    connection.voidEval("png(filename ='/tmp/images/test2.png'),bg = '#F7F8E0' ")
			}
			catch(e) {		
				println("its in pdf block")
				connection.voidEval("pdf('/tmp/images/test3.pdf')")				
			}
			connection.voidEval('test2 =heatmap.2((data.matrix(data)),hclustfun=hclust.ave,col = myCol , breaks = myBreaks,trace="none" )')
			connection.voidEval("dev.off()")
			int[] rowOrd = connection.eval('test2$rowInd').asIntegers();
			int[] colOrd = connection.eval('test2$colInd').asIntegers();;
			println("Got results")
			def mapOrd = ["rowOrd": rowOrd, "colOrd": colOrd]
			
			
			return mapOrd
			
			}
			
			
			/*
	def assigVar(String a)
	{
		println(a)
		
		c.assign("A", a)
		def e = c.eval("A")
		println(e)
		
			
	}

		def doCalculations(int a, int b )
		{
			REXP x = c.eval("R.version.string");
			println(x.asString())
			println("a is"+ a + "b is "+ b)
			c.assign("a",a.abs())
			c.assign("b", b.abs())
				c.eval("A<-c(3,5,3,6)");
				c.eval("B<-c(3,6,4,5)");
				c.eval("C<-A+B");
				//get the result from R
				double[] result = (double[])c.eval("summary(A)").asDoubles();
				
				//easy print function to console
				
				for (int i = 0; i < result.length; i++) {
				System.out.print(result[i] + " ");
				}
			
			
			
		}
		def createHeatmap(String idn)
		{
		   def con = idn.toLong();
		   def enrichL =Enrichments.createCriteria()
		   def result= enrichL.list {
				 or {
					eq('id1.id',con)
					eq('id2.id',con)
								}
				le("qval", 0.01)
			   maxResults(1000)
			   order("qval")
			}
			def id1= result.collect{ ids -> return [id:ids.id1.id]}
			def id2= result.collect{ ids -> return [id:ids.id2.id]}
			def allid = (id1+id2)
			
			def allids = allid.unique()
			println(allids.unique().sort())
			def allidcheck2 = allids.unique().sort()
			println(result)
			
			def map = [:]
			 List<Compounds_in_concepts> b = new ArrayList<Compounds_in_concepts>()
			 b = Compounds_in_concepts.executeQuery("SELECT cc from conceptmetab.Compounds_in_concepts cc where cc.concept.id="+con)
		   println("its b"+ b.compound.id )
		   
			   String name = con+"_qval100.csv"
			   String name2 = "/home/snehal/ConceptMetabGG/conceptmetabVersion/v4/conceptmetab/web-app/pdf/"+name
				 def out = new File(name2)
				 println("name of the new csv file"+name2)
				 
				 b.each{
					 println("id from loop" +it.compound.id)
				 }
				allidcheck2.each {
					def cons = Concepts.get(it.id)
					def cname = cons.name.replaceAll(/,/, '')
					cname = cname.replaceAll(/,/, '')
					def comp = cons.compounds_in_concepts.compound.id.toList().sort().toString()
					def ja = comp.substring(1, comp.length()-1)
					//println(cname + ","+ ja)
					out.append(cname + ","+ja)
					out.append '\n'
			   
				 }
				def countmap = [:]
				//For each compound
		   b.each {
			   def compid = it
			   for( i in map)
				   {
					   //println("key is"+ i.key)
					   //println( "value is"+ i.value)
					   def ts = i.value
					   //println(ts.findAll {b.equals(360)})
					   ts.collect {
						   if(it == compid )
						   {
							   println(" id from concept is"+ it + "original"+ b.id)
						   }
					   }
				   }//for map
				} //for b
		   def values = b.compound.id ;
		   println("vomp for concepts in query are :" + values);
		   
		 def m_list=  calPar(values,name,con);
		 
		 
		 
		  String namePdf = con+"_100.pdf"
		  //return namePdf;
		//  println m_list;
		  
		  
		  HashMap jsonMap = new HashMap();
		  jsonMap.col = b.compound.id ;
		  jsonMap.row = allidcheck2;
		  jsonMap.data = m_list.collect {en -> return [en.getVal(),en.getRow(),en.getCol()]};
		  
		  
		 
		  return jsonMap as JSON
				
		}
		
		def calPar(ArrayList values,String name,long con)
		{
			
				RConnection connection;
		connection = new RConnection("localhost",
			   6311);
		   
		//connection = new RConnection("127.0.0.1", 6311);
		//LRPathRServer rserver = new LRPathRServer(RServerConfiguration.rserverAddress(),
		   //     RServerConfiguration.rserverPort());
		//connection.voidEval(command);
		   
		   
		println("Comp id is"+ values)
		//
		int [] values2 = values;
		connection.assign("a", values2);
		
		
		String filepath = "/home/snehal/ConceptMetabGG/conceptmetabVersion/v4/conceptmetab/web-app/pdf/"+name;
		println("filepath is"+ filepath)
		connection.assign("filepath", filepath);
		String command  = "no_col <- max(count.fields(filepath,sep = \" "+","+ "\"))";
		System.out.println(command);
		connection.voidEval(command);
		int colNo = connection.eval("no_col").asInteger();
		System.out.println(colNo);
		String c2 = "data = read.table(filepath,sep=\"" +","+"\",fill=TRUE,col.names=1:no_col)";
		connection.voidEval(c2);
		connection.voidEval("enriched.concepts <- as.list(as.data.frame(t(data)))");
		connection.voidEval("membership = t(sapply(enriched.concepts, function(enriched.concepts){ as.numeric(a %in% enriched.concepts) }))");
		connection.voidEval("colnames(membership) = a");
		connection.voidEval("rownames(membership) = data[,1]");
		connection.voidEval("m_matrix <- data.matrix(membership)");
		connection.voidEval("distancem <- dist(m_matrix)");
		connection.voidEval("hclust_completem <- hclust(distancem, method = 'complete')")
		connection.voidEval("dendcompletem <- as.dendrogram(hclust_completem)")
		
		
		
		
		
		
		
		
		
		
		def matrix_list = connection.eval("m_matrix").asDoubleMatrix()
		
		connection.voidEval("aa <- data.frame(r=as.vector(row(m_matrix)), c=as.vector(col(m_matrix)), v=as.vector(m_matrix))")
		RList list = connection.eval("aa").asList()
		connection.voidEval("cc=list(colnames(m_matrix))");
		RList colnames = connection.eval("cc").asList()
		connection.voidEval("rr= list(rownames(m_matrix))");
		RList rownames = connection.eval("rr").asList()
		String[] r = ((REXP) list.get(0)).asStrings()
		String[] c = ((REXP) list.get(1)).asStrings();
		String[]  v = ((REXP) list.get(2)).asStrings();
		String[] rname = ((REXP) rownames.get(0)).asStrings()
		String[] cname = ((REXP) colnames.get(0)).asStrings()
		
		println("Columnames are "+cname)
		println("Rowmnames are "+rname)
		
		println("Rows size is  "+r);
		println("Columns is"+ c)
		println("Values are"+ v)
		println("Row size is"+ r.length)
		
		List<heatMapResults> resultData = new LinkedList<heatMapResults>();
		for (int i =0 ; i < r.length; i++)
		{
			def hm = new heatMapResults();
			hm.setRow(r[i].toLong());
			hm.setCol(c[i].toLong());
			hm.setVal(v[i].toDouble());
			resultData.add(hm);
			
		}
		
		
		
		String pdfName = "pdf(file='/home/snehal/ConceptMetabGG/conceptmetabVersion/v4/conceptmetab/web-app/pdf/"+con+"_100.pdf')"
		println(pdfName)
		connection.voidEval(pdfName)
		//connection.voidEval("nba_heatmap <- heatmap(m_matrix, Rowv=NA, Colv=NA, col = cm.colors(256))")
		connection.voidEval('heatmap(m_matrix, Rowv=dendcompletem, Colv=NA)')
		connection.voidEval("dev.off()")
		return resultData;
		}
		
		
	*/
		
}
