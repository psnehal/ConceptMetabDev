package conceptmetab
import java.awt.Image
import java.awt.event.ItemEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.rosuda.REngine.*;
import org.apache.commons.io.output.WriterOutputStream

//import org.conceptmetab.java.CreateHeatmap
import org.conceptmetab.java.HeatMapNewJS
import org.conceptmetab.java.JDBCConnectionTest;
import org.conceptmetab.java.MockupHeatMap
import javax.imageio.*;


import grails.converters.JSON
import groovy.json.JsonBuilder

import org.compass.core.converter.mapping.osem.ClassMappingConverter.IdsAliasesObjectKey;
import org.springframework.dao.DataIntegrityViolationException

class EnrichmentsController {
	
	//Injecting service in controller
	def connectRService
	def createEnrichedConceptService
	 def exportService
	def grailsApplication

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def index() {
		redirect(action: "list", params: params)
	}
	//*****************************************************************FindbyID**************************************************************************
	def findById() {
		def con = params.q;
		println(con.class)
		println("parameter is"+con)
			println(Enrichments.get(1).id1.id)
				//println(id1.toString())
		List<Enrichments> b = new ArrayList<Enrichments>()
		b = TestEnrich.executeQuery("SELECT e.intersection from conceptmetab.Enrichments e where e.id1.id=3460 and e.id2.id=4268")
		List<Enrichments> enrichList = Enrichments.where {(id1.id == 3460) || (id2.id == 3460)    }.toList()
		def enrichL = Enrichments.createCriteria()
		def result= enrichL.list {
			or {
				("id1.id" == con.toInteger())
				("id2.id" == con.toInteger())
		   }
		   maxResults(100)
		order("intersection")
		}
	   
 //UNION select e.id1.id from conceptmetab.Enrichments  e where e.id2 =3460")
		println(b.size())
		HashMap jsonMap = new HashMap()
		jsonMap.edges = result.collect {en ->
			return [source: en.id1.id.toString(), target: en.id2.id.toString(), id: (en.intersection.toString())]
			}
		def cid = jsonMap as JSON
		 [cid:cid]
		   
	}
//*****************************************************************CheckToolTip**************************************************************************
	def checkToolTip ()
	{
		def msg = "hello"
		[msg:msg]
	}
	
//*****************************************************************EnrichedConcepts**************************************************************************

//*****************************************************************filterSlider**************************************************************************

	
	def filterSlider2(){
		
		def filter
		def con
		def id1_inter
		def id2_inter
		def result
		def odds
		println("From filter slider parameters a" + params)
		
		HashMap jsonMap = new HashMap()
		
		println("its going for p and q filter")
		filter = params.fil;
		con = params.q.toLong();
		id1_inter = params.id1.toBigDecimal();
		id2_inter = params.id2.toBigDecimal();
		odds = params.odds.toBigDecimal()
	
		def enrichL =Enrichments.createCriteria()
		
		result= enrichL.list {
			 or {
				 eq('id1.id',con)
				 eq('id2.id',con)
			 }
			 between(filter, id1_inter, id2_inter)
			 le('odds',odds)
			 maxResults(1000)
			 order(filter)
		 }
		
		def enrichP =Enrichments.createCriteria()
		
		
	 println("list of interactions = "+result.size() +" id1 = "+ id1_inter+"  id2 = "+ id2_inter+"  id  = "+ con +"  filter  = "+ filter)
		
	 
		if (params.containsKey("db"))
		{
			println("its going for database filter")
			
		}
		else
		{
			println("p and q")
			
			
		}
		
	
		
		def id1= result.collect{ ids -> return [id:ids.id1.id.toString()]}
		def id2= result.collect{ ids -> return [id:ids.id2.id.toString()]}
		def allid = (id1+id2)
			def allids = allid.unique()
		
			
			println(allids.sort())
			println("Size of all concepts"+ allids.size())
		def allR =allids.collect {ids -> return [id:ids.id,label:(Concepts.get(ids.id).getName().capitalize()),comNo:(Concepts.get(ids.id).getNum_compounds()),conTypes:(Concepts.get(ids.id).concept_types.getName())]}
	
		//allids.add(con)
		jsonMap.nodes = allR
		   
		jsonMap.edges = result.collect {en ->
				return [source: en.id1.id.toString(), target: en.id2.id.toString(),id: (en.pval.toString()),db_id: en.id.toString(),thick: (en.intersection),label: (Concepts.get(en.id1.id).getOriginal_id())]
			//	println(Concepts.get(en.id1.id).getOriginal_id())
				}
	   
	println(jsonMap)
	   
		def check = jsonMap as JSON
	 //  [check:check, resultcount : result.size()]
		render jsonMap
	   
	   /*def con = params.q.toLong();
		println("parameter is"+con)
		HashMap jsonMap = new HashMap()
		def enrichL =Enrichments.createCriteria()
		println("ln checkin"+con)
		def result= enrichL.list {
			or {
				eq('id1.id',con)
				eq('id2.id',con)
			}
			maxResults(100)
			order("pval")
		} */
			//def id1= result.collect{ ids -> return [id:ids.id1.id.toString(),conTypes:(Concepts.get(ids.id1.id).concept_types.getName()),label:(Concepts.get(ids.id1.id).getName()),comNo:(Concepts.get(ids.id1.id).getNum_compounds())]}.unique()
			//def id2= result.collect{ ids -> return [id:ids.id2.id.toString(),conTypes:(Concepts.get(ids.id1.id).concept_types.getName()),label:(Concepts.get(ids.id2.id).getName()),comNo:(Concepts.get(ids.id1.id).getNum_compounds())]}.unique()
			//def id1= result.collect{ ids -> return [id:ids.id1.id.toString()]}.unique()
			//def id2= result.collect{ ids -> return [id:ids.id2.id.toString()]}.unique()
			 //def id1= result.collect{ ids -> return [id:ids.id1.id.toString(),label:ids.id1.id.toString()]}.unique()
		   // def id2= result.collect{ ids -> return [id:ids.id2.id.toString(),label:ids.id2.id.toString()]}.unique()
		  //  def allids = (id1+id2)
	}
	///*****************************************************************CreateJson**************************************************************************
	def createJson(){
		
		println("from createJson"+params)
		def filter = params.fil;
		def con = params.q.toString();
		def id1 = params.id1.toString();
		def id2 = params.id2.toString();
		
		def db = params.statement.toList()
	   
		HashMap jsonMapN = new HashMap()
		println("db  is"+ db.class)
		String odds = params.odds.toString()
		def check
		
		
	   
		if (odds.length() ==0)
		{
			odds = '4.5035996273705e+15'
		}
				
				
			   
	   if(params.containsKey("slider"))
		   {
			   println("inside slider cond ************************************************")
			   def db2 = params.statement;
			   ArrayList list = db2.tokenize(",")
			   println(list.get(1));
			   check = createEnrichedConceptService.createNetwork(filter, con,  id1, id2,odds, list)
			   println("From redirect view action" +check);
		   }
		   else
		   {
			   check = createEnrichedConceptService.createNetwork(filter, con,  id1, id2,odds, db)
			   println("From redirect view action" +check);
		   }
				//println(dbname.class)
		   
		   def concept = Concepts.get(con.toInteger())
		[check:check,concept:concept]
	}
	
//*****************************************************************DislayMsg**************************************************************************
	
	def displayMsg()
	{
		def msg  = params.q;
	println(params)
			println(" ************************************************************id is"+msg + msg.class)
		ArrayList arM;
	   //[msg:msg]
			def conceptsInstance = Concepts.get(msg.toLong())
		
			println("got the instance")
		[conceptsInstance: conceptsInstance]
		
	}
//*****************************************************************DisplayEdge**************************************************************************
	
	def displayEdge()
	{
		def msg  = params.q;
		def con = params.con
		println(params)
		
		def enrichInstance = Enrichments.get(msg.toLong())
		
		println(enrichInstance.id1.compounds_in_concepts.compound.id)
		List id1_com=enrichInstance.id1.compounds_in_concepts.compound.id
		List id2_com=enrichInstance.id2.compounds_in_concepts.compound.id
		
		
		List inter = id1_com.intersect(id2_com)
		println("intesection"+inter)
		
		def comp = Compounds.createCriteria()
		
		def res = comp.list {
			
			'in'("internal_id",inter)
		}
	
		println(res)
		
		def comIns = res.collect { ids ->
			
			if(enrichInstance.id1.concept_types.fullname.contains("MeSH") && enrichInstance.id2.concept_types.fullname.contains("MeSH"))
			{
					 return [ id: ids.pubchem_id, name :ids.name, id2:ids.kegg_id, iid: ids.internal_id ]
			}
			else
			{
					 return [ id: ids.kegg_id, name : ids.name, id2:ids.pubchem_id, iid:ids.internal_id]
			}
			
			
			}
		//BigDecimal pval = intersection.pval
		
		
		
		[enrichInstance:enrichInstance,comIns:comIns]
		
	}
	
//*****************************************************************DispalyJson**************************************************************************
	
def displayJson(){
	
	println(params)
	
	def con = params.id.toLong();
	def id1_inter = 1.45e-323.toBigDecimal()
	def id2_inter = params.id2.toBigDecimal();
	HashMap jsonMap = new HashMap()
	
	def enrichL =Enrichments.createCriteria()
	 def result= enrichL.list {
		  or {
			 eq('id1.id',con)
			 eq('id2.id',con)
				
		 }
		 between("pval", id1_inter, id2_inter)
		  maxResults(100)
		  order("pval")
	 }
			 
	/*def con = params.q.toLong();
	println("parameter is"+con)
	HashMap jsonMap = new HashMap()
	def enrichL =Enrichments.createCriteria()
	println("ln checkin"+con)
	def result= enrichL.list {
		or {
			eq('id1.id',con)
			eq('id2.id',con)
		}
		maxResults(100)
		order("pval")
	} */
		println("list of interactions"+result.size())
		
		
	   
		//def id1= result.collect{ ids -> return [id:ids.id1.id.toString(),conTypes:(Concepts.get(ids.id1.id).concept_types.getName()),label:(Concepts.get(ids.id1.id).getName()),comNo:(Concepts.get(ids.id1.id).getNum_compounds())]}.unique()
		//def id2= result.collect{ ids -> return [id:ids.id2.id.toString(),conTypes:(Concepts.get(ids.id1.id).concept_types.getName()),label:(Concepts.get(ids.id2.id).getName()),comNo:(Concepts.get(ids.id1.id).getNum_compounds())]}.unique()
		
		//def id1= result.collect{ ids -> return [id:ids.id1.id.toString()]}.unique()
		//def id2= result.collect{ ids -> return [id:ids.id2.id.toString()]}.unique()
	   
		//def id1= result.collect{ ids -> return [id:ids.id1.id.toString(),label:ids.id1.id.toString()]}.unique()
	   // def id2= result.collect{ ids -> return [id:ids.id2.id.toString(),label:ids.id2.id.toString()]}.unique()
	  //  def allids = (id1+id2)
		
		
		def id1= result.collect{ ids -> return [id:ids.id1.id.toString()]}
		def id2= result.collect{ ids -> return [id:ids.id2.id.toString()]}
		def allids = (id1+id2)
		
		def allR =allids.collect {ids -> return [id:ids.id,label:(Concepts.get(ids.id).getName().capitalize()),comNo:(Concepts.get(ids.id).getNum_compounds()),conTypes:(Concepts.get(ids.id).concept_types.getName())]}
		println(allids.sort())
		//allids.add(con)
		jsonMap.nodes = allR
		println(allR.get(0))
		
		jsonMap.edges = result.collect {en ->
			return [source: en.id1.id.toString(), target: en.id2.id.toString(),id: (en.pval.toString()),db_id: en.id.toString(),thick: (en.intersection),label: (Concepts.get(en.id1.id).getOriginal_id())]
			println(Concepts.get(en.id1.id).getOriginal_id())
			}
		
		def check = jsonMap as JSON
	   [check:check]
	//render ("hello")
}


def createDb(){
	
		println(params)
		def con = params.id.toLong();
		def database = params.db.toString()
		//def id1_inter = 1.45e-323.toBigDecimal()
		//def id2_inter = params.id2.toBigDecimal();
		HashMap jsonMap = new HashMap()
		def enrichL =Enrichments.createCriteria()
		 def result= enrichL.list {
			  or {
				 eq('id1.id',con)
				 eq('id2.id',con)
					
			 }
			// between("pval", id1_inter, id2_inter)
			// maxResults(10)
			  order("pval")
		 }
				 
		 
		
		
			println("list of interactions"+result.size())
			def id1= result.collect{ ids -> return [id:ids.id1.id.toString()]}
			def id2= result.collect{ ids -> return [id:ids.id2.id.toString()]}
			def allid = (id1+id2)
			def allids = allid.unique()
			println(allids.sort())
			//def allR =allids.collect {ids -> return [id:ids.id,label:(Concepts.get(ids.id).getName()),comNo:(Concepts.get(ids.id).getNum_compounds()),conTypes:(Concepts.get(ids.id).concept_types.getName())]}
			//println(allids.sort())
			//def testc = allR.findAll {id -> id.conTypes.equals(database)}
			
			
			
			
			 def enrichT= result.collect {en ->
				return [source: en.id1.id.toString(), target: en.id2.id.toString(),id: (en.pval.toString()),db_id: en.id.toString(),thick: (en.intersection),label: (Concepts.get(en.id1.id).getOriginal_id()),dbid: (Concepts.get(en.id1.id).concept_types.getName()),dbid2: (Concepts.get(en.id2.id).concept_types.getName())]
				//println(Concepts.get(en.id1.id).getOriginal_id())
				}
			 
			def r1 = enrichT.findAll {it.dbid.equals(database)|| it.dbid2.equals(database)}
			def i1 = r1.collect {it.source}.unique()
			def i2 = r1.collect {it.target}.unique()
			def ifin = (i1+i2)
			
			def allR =ifin.collect {ids -> return [id:ids,label:(Concepts.get(ids).getName()),comNo:(Concepts.get(ids).getNum_compounds()),conTypes:(Concepts.get(ids).concept_types.getName())]}
			
			jsonMap.nodes = allR
			
			 jsonMap.edges = r1
			 println(jsonMap)
			
			def check = jsonMap as JSON
			[check:check]
}

//***********************************************************LIST,CREATE,SAVE,SHOW,EDIT,UPDATE,DELETE**************************************************************************
 def createChart() {
		 
		   println(params)
		
		 def filter
		 def con
		 def id1_inter
		 def id2_inter
		 def odds
		 
		 println("From filter slider parameters a")
		 HashMap jsonMap = new HashMap()
		 
		 println("its going for p and q filter")
		 filter = params.fil;
		 con = params.q.toLong();
		 id1_inter = params.id1.toBigDecimal();
		 id2_inter = params.id2.toBigDecimal();
		 
		 odds = params.odds.toString()
		 //println(dbname.class)
		 if (odds.length() ==0)
		 {
			 odds = '4.5035996273705e+15'
		 }
		 
		
		
		 def enrichL =Enrichments.createCriteria()
		 println("filter :-->  " + filter + "  id :--> "   +  con + "  id1 :--> " + id1_inter + "   id2 ::--> " + id2_inter+"odds : :->" +odds)
		 def result= enrichL.list {
			 
			 or {
				 eq('id1.id',con)
				 eq('id2.id',con)
			 }
			 
			 le(filter, id2_inter)
			 ge('odds',odds.toBigDecimal())
			 
			 order(filter)
		 }
		 
		  println("From Create Chart Size :== " + result.size())
		 /* println("list of interactions == "+result.size()+ "for id is = " + con)
		  def id1= result.collect{ ids -> return(Concepts.get(ids.id1.id).concept_types.getName())}
		  def id2= result.collect{ ids -> return(Concepts.get(ids.id2.id).concept_types.getName())}
		  def allid = id1+id2
		*/
		 
		 def allid = result.collect {en ->
				 if(en.id1.id == con )
				 {
						  return en.id2.concept_types.getFullname()
						 //println("Id1 is concept of interest")
						 
				 }
					 else if (en.id2.id == con){
						 
						  return en.id1.concept_types.getFullname()
					 }
				 
				 }
		 println(allid)
		  def map = [:]  //1
		  allid.each {  //2
			  if(map.containsKey(it)) map[it] = map[it] + 1  //3
			  else map[it] = 1;
		  }
		  
		  println("Map is" +map.size())
		  
		  def emptyList = []
		  map.each{
			 //println("this is start of loop"+it.getKey())
			  if(it.getKey().equals("GO Biological Process"))
			  {
				 
				  def index =map.findIndexOf {it.key =='GO Biological Process'}
				  emptyList.addAll(index, "#227207")
			  }
			  if(it.getKey().equals("GO Cellular Component"))
			  {
			 
				  def index =map.findIndexOf {it.key=='GO Cellular Component'}
				  emptyList.addAll(index, "#98E6CA")
			  }
			  if(it.getKey().equals("GO Molecular Function"))
			  {
				  
				  def index =map.findIndexOf {it.key=='GO Molecular Function'}
				  emptyList.addAll(index, "#49FFB9")
			  }
			  if(it.getKey().equals("Enzyme"))
			  {
				 
				  def index =map.findIndexOf {it.key=='Enzyme'}
				  emptyList.addAll(index, "#BE3A40")
			  }
			  if(it.getKey().equals("KEGG Pathway"))
			  {
				  
				  def index =map.findIndexOf {it.key=='KEGG Pathway'}
				  emptyList.addAll(index, "#CC2EFA")
			  }
			 if(it.getKey().equals("Chemical Clusters"))
			  {
				 
				  def index =map.findIndexOf {it.key=='Chemical Clusters'}
				 //println("index of cluster is)" + index)
				  emptyList.addAll(index, "#ffab9b")
			  }
			  if(it.getKey().equals("MeSH Anatomy"))
			  {
				 
				  def index =map.findIndexOf {it.key=='MeSH Anatomy'}
				  emptyList.addAll(index, "#7B3F00")
			  }
			  if(it.getKey().equals("MeSH Diseases"))
			  {
				 
				  def index =map.findIndexOf {it.key=='MeSH Diseases'}
				  emptyList.addAll(index, "#F47D00")
			  }
			  if(it.getKey().equals("MeSH Chemicals and Drugs"))
			  {
				 
				  def index =map.findIndexOf {it.key=='MeSH Chemicals and Drugs'}
				  emptyList.addAll(index, "#FFB86D")
			  }
			  if(it.getKey().equals("MeSH Organisms"))
			  {
				 
				  def index =map.findIndexOf {it.key=='MeSH Organisms'}
				  emptyList.addAll(index, "#FCDC3B")
			  }
			  if(it.getKey().equals("MeSH Phenomena and Processes"))
			  {
				 
				  def index =map.findIndexOf {it.key=='MeSH Phenomena and Processes'}
				  emptyList.addAll(index, "#F7EA2B")
			  }
			  if(it.getKey().equals("MeSH Psychology and Psychiatry"))
			  {
				 
				  def index =map.findIndexOf {it.key=='MeSH Psychology and Psychiatry'}
				  emptyList.addAll(index, "#00F5FF")
			  }
			  if(it.getKey().equals("MeSH Technology, Industry, and Agriculture"))
			  {
				 
				  def index =map.findIndexOf {it.key=='MeSH Technology, Industry, and Agriculture'}
				  emptyList.addAll(index, "#00C5CD")
			  }
			 //println(em
		  }
			
	def columns = []
		 columns << ["id": 'Col1',"label": 'database', "type": 'string']
		 columns << ["id": 'Col2',"label": 'no', "type": 'number']
		// println(columns)
	   
		 def rows = []
		 def cells
		 map.each {
		 cells = []
		 cells << [v: it.getKey()] << [v: it.getValue()]
		 rows << ['c': cells]
		 }
	   
		 def table = [cols: columns, rows: rows]
		 jsonMap.table = table
		println map
		 
		 println("jsonMap is+" +  emptyList)
	   
		 //def allR =map.collect {ids -> return [ids.getKey().toString(),ids.getValue()]}
		 
	   
	   
		//render map
		def dbc = table as JSON
		
		
		def clst = emptyList as grails.converters.JSON
		
		println emptyList.getAt(0)
			[dbc:dbc, map:map,clst:clst,rsize:result.size(),emptyList:emptyList]
	 
 }
 
 def redirectView()
 {
	 println("from redirectView"+params)
	 def filter = params.fil;
	 def con = params.q.toString();
	 def id1 = params.id1.toString();
	 def id2 = params.id2.toString();
	 def db = []

	 
	 
	 
	 if(params.statement instanceof java.lang.String)
	 {
		db.add(params.statement);
	 }
	 else
	 {
		 db = params.statement.toList()
	 }
	 
	 
	 
	
	 HashMap jsonMapN = new HashMap()
	 println("db  is"+ db.class)
	 String odds = params.odds.toString()
	 def map
	 

 
 String fil =params.sort
 String order =params.order
 if(params?.sort && fil.equals("name") && order.equals("asc"))
 {
	println("Sorting by name")
	map = map.sort{it.name}
 }

 if(params?.sort && fil.equals("pval"))
 {
	
println("Sorting by pval")
	map = map.sort{it.pval}
 }
 
 if(params?.sort && fil.equals("qval"))
 {
	
	//println("Sorting by qval")
	map = map.sort{it.qval}
 }
	 
if (params.containsKey("chart"))
{
		   forward(action: "createChart", params:params)
}


	 
	 
	
	 if (odds.length() ==0)
	 {
		 odds = '4.5035996273705e+15'
	 }
			 
	 if(params.containsKey("CompleteNetwork"))
	 {
		 
		 forward(action: "createJson", params:params)
		 
	 }
	else {
				 if(params.containsKey("slider"))
					{
						println("inside slider cond ************************************************")
						def db2 = params.statement;
						ArrayList list = db2.tokenize(",")
						println(list.get(1));
						map = createEnrichedConceptService.showEnrichedConcepts(filter, con,  id1, id2,odds, list)
						//println("From redirect view action" +map);
					}
					else
					{
						map = createEnrichedConceptService.showEnrichedConcepts(filter, con,  id1, id2,odds, db)
						//println("From redirect view action" +map);
					}
						 //println(dbname.class)
					
				
			println("map size from redirect view is"+map.size())
			println('params are'+ params)
			println('db is '+ db + 'db type is' + db.class)
			println"Class of the Statement param"+(params.statement.class)
			
		
			//if (map.size < 500){
			  println("its inside map if action")
			  
				 if(params.containsKey("network"))
				 {
					 println("map from network"  + map)
					 def allR =map.collect {
						 ids ->  return [id:ids.id.toString(),label:ids.name.capitalize(),comNo:ids.comNo,conTypes:ids.ctypes]
					 }
					 def query = Concepts.get(con)
					 def conId = [id: query.id.toString(),label:query.name.capitalize(), comNo:query.num_compounds,conTypes:query.concept_types.getName() ]
					 println("ConId" + conId)
					//allids.add(con)+
					 jsonMapN.nodes = allR+conId
					 jsonMapN.edges = map.collect {en ->
									 return [source: con.toString(), target: en.id.toString(),id: (en.pval),db_id: en.enid.toString(),thick: (en.ins),label: en.eid]
												  }
						  def check = jsonMapN as JSON
						  println(jsonMapN)
						 [check:check, resultcount : map.size()]
					 forward(action: "filterSlider", params:[check:check, ct:map.size(),q:con])
				 }
				 
				 
				 else if(params.containsKey("table"))
				 {
					  def columns = []
					 columns << ["id": 'Col1',"label": 'Concept Name', "type": 'string']
					 columns << ["id": 'Col2',"label": 'Original ID', "type": 'string']
					 columns << ["id": 'Col1',"label": 'Concept Type', "type": 'string']
					 columns << ["id": 'Col2',"label": 'P-Value', "type": 'number']
					 columns << ["id": 'Col1',"label": 'Q-Value', "type": 'number']
					 columns << ["id": 'Col2',"label": 'Overlap', "type": 'number']
					 columns << ["id": 'Col1',"label": 'Odds Ratio', "type": 'number']
					
					// println(columns)
					
					 HashMap jsonMap = new HashMap()
					 def rows = []
					 def cells
					 map.each {
					 cells = []
					 cells << [v: it.name] << [v: it.eid]<< [v: it.ctfull]<< [v: it.pval]<< [v: it.qval]<< [v: it.ins]<< [v: it.odds]
					 rows << ['c': cells]
					 }
					
					 
					 def table = [cols: columns, rows: rows]
					 jsonMap.table = table
					// println map
					 
					 println("jsonMap is+" +  table)
					// [map:map]
					 def dbc = table as JSON
					 if(params?.format && params.format != "html")
					 {
						 response.contentType = grailsApplication.config.grails.mime.types[params.format]
						// response.setHeader("Content-disposition", "attachment; filename=Compounds_in_concepts.${params.extension}")
						 exportService.export(params.format, response.outputStream,dbc, [:], [:])
					 }
					
					 //redirect(action: "showEnrichedConcepts", params: params)
					 forward(action:"showEnrichedConcepts", params:[dbc:dbc, params: params])
					
					 
				 }
				 else{
					redirect(action: "clusterStr", params: params)
					//redirect(action: "mockup", params: params)
					//redirect(action: "test", params: params)
				 }
				 
				 /*
			}
			else
			{
				println("its inside map ifelse action")
				
				flash.message = message(code: 'please select less than 500 concepts', args: [message(code: 'concepts.label', default: 'Concepts')])
				redirect(controller:"Concepts",action:"show",id:params.q, params:[id2: '0.05', odds: '1.1',fil:'qval'])
				//forward (action:"showError")
			}
			*/
	}
 }
 def cluster(){
	 
	 def fil = params.fil
	 def id2 = params.id2
	  def db = []
	 
	 if(params.statement instanceof java.lang.String)
	 {
		db.add(params.statement);
	 }
	 else
	 {
		 db = params.statement.toList()
	 }
	 
	 def odds= params.odds
	 MockupHeatMap hm = new MockupHeatMap();
	 String dbname = "";
	 db.each{ dbname = dbname+"'"+it+"'"+"," }
	 println("DBNAME is "+dbname)
	 dbname =dbname.substring(0,dbname.lastIndexOf(","))
	 Object[][] test  = hm.getConcept(params.q.toInteger(), fil, id2,odds,dbname);
	 List<String[]> l = new ArrayList<String[]>(Arrays.asList(test));
	 def con = Concepts.get(params.q.toInteger())
	 def no = l.size()-1
	 println("from list " + l.get(no))
	 l.remove(no)
	
	 Object [][] forCluster = l.toArray()
	 
	 
	 //l.remove(1);

	 
	 println("2D array from java is " +  forCluster)
	 
	 def te = new JsonBuilder( forCluster ).toString()
	 println(te)
	 def clusterMat = connectRService.clusterAnalysis(te)
	 
	 
	 int[] coll = clusterMat.colOrd
	 int[] rowl = clusterMat.rowOrd
	 println("column are" + coll + "Rowsa are " + rowl )
	 String html =""
	 String html2 =""
	 
	println("Row is " +  forCluster.size()  + " Col is   :=" + forCluster[1].length + "   col order is :=" + coll.size() +  "row order is := " + rowl.size())
		 
	//render forCluster
	 println("from controller" + clusterMat)
	 
	 
	 
	 //CclusterMat has compounds in columns and concepts in rows ..For html we need to transpose matrix with compounds in cols
	 for (int col = 0 ; col < forCluster[1].length;col++)
	 {
		 String rownames = "<tr>"
			 for (int row = 0 ; row< forCluster.size(); row++)
			 {
				 String colname =" ";
				 String color;
				
				 int comInt = coll[col-1]//Column order
				 int conInt = rowl[row-1]//Row order
				 //println( "ConInt" + [conInt] + "Comint" +[comInt])
			 
			 if (col == 0 && row != 0)
			 {
				 
				 println("all conscepts are getting added to the column which were in rows")
				 def conName = test[conInt][0]
				 colname = "<th class='rotate-45'><div class='tableHeader'><span>" +conName +" </span></div></th>"
				 println("colnames are "+ conName + "with row" + conInt )
				  html = html+colname;
				 
			 }
			 
			else
			{
				
				if (row == 0 && col != 0)
				{
					
					println(col+  " compound is " + test[0][comInt])
					def comName  =  test[0][comInt].toString()
					println("*********************************************************************************"+comName.length())
					if(comName.length() > 31)
					{
					 comName = comName.substring(0, 30);
					}
					
					rownames =rownames + '<th class="row-header">' +comName+ '</th>'
					println(comName)
					
					
				}
				else if(col != 0 && row !=0)
				{
					println(test[0][comInt]+'values are' + "ConInt" + [conInt] + "Comint" +[comInt])
					def value = test[conInt][comInt];
					println (value)
					
					if(value > 90 && value <= 100)
					{
						
						color = 'red '
					}
					else if(80<value && value <= 90)
					{
						
						color = '#FF3300 '
					}
					else if(value > 70 && value <= 80)
					{
					
						color = '#ff6600 '
					}
					else if(value > 60 && value <= 70)
					{
						
						color = '#ff9900 '
					}
					else if(value > 50 && value <= 60)
					{
						
						color = '#FFCC00  '
					}
					else if(value > 40 && value <= 50)
					{
						
						color = '#FFFF00 '
					}
					else if(value > 30 && value <= 40)
					{
						
						color = '#ffff33 '
					}
					else if(value > 20 && value <= 30)
					{
					
						color = 'ffff66'
					}
					else if(value > 10 && value <= 20)
					{
					
						color = '#ffff99 '
					}
					else if(value >0 && value <= 10 )
					{
						println('inside')
						color = 'ffffcc '
					}
					else{
						color = 'white '
					}
					rownames =rownames + '<td><div style="height:100%; width:100%;background-color:'+color+'  ">' +value+ '</div></td>'
					
				}
				
				
			}
				
			 
		 }
		 //End of loop
		
		 html2 = html2+rownames;
		
	 }
	 println(html2)
	 [html:html,html2:html2,con:con]
	 
 }
 
 def clusterStr(){
	 def fil = params.fil
	 def id2 = params.id2
	  def db = []
	 
	 if(params.statement instanceof java.lang.String)
	 {
		db.add(params.statement);
	 }
	 else
	 {
		 db = params.statement.toList()
	 }
	 
	 def odds= params.odds
	 MockupHeatMap hm = new MockupHeatMap();
	 String dbname = "";
	 db.each{ dbname = dbname+"'"+it+"'"+"," }
	 println("DBNAME is "+dbname)
	 dbname =dbname.substring(0,dbname.lastIndexOf(","))
	 Object[][] test  = hm.getConcept(params.q.toInteger(), fil, id2,odds,dbname);
	 List<String[]> l = new ArrayList<String[]>(Arrays.asList(test));
	 def con = Concepts.get(params.q.toInteger())
	 def no = l.size()-1
	 println("from list " + l.get(no))
	 l.remove(no)
	
	 Object [][] forCluster = l.toArray()
	 
	 
	 //l.remove(1);

	 
	 println("2D array from java is " +  forCluster)
	 
	 def te = new JsonBuilder( forCluster ).toString()
	 println(te)
	 def clusterMat = connectRService.clusterAnalysis(te)
	 
	 
	 int[] coll = clusterMat.colOrd
	 int[] rowl = clusterMat.rowOrd
	 println("column are" + coll + "Rowsa are " + rowl )
	 String html =""
	 String html2 =""
	 
	println("Row is " +  forCluster.size()  + " Col is   :=" + forCluster[1].length + "   col order is :=" + coll.size() +  "row order is := " + rowl.size())
		 
	//render forCluster
	 println("from controller" + clusterMat)
	 
	 
	 
	 //CclusterMat has compounds in columns and concepts in rows ..For html we need to transpose matrix with compounds in cols
	 for (int col = 0 ; col < forCluster[1].length;col++)
	 {
		 String rownames = "<tr>"
			 for (int row = 0 ; row< forCluster.size(); row++)
			 {
				 String colname =" ";
				 String color;
				
				 int comInt = coll[col-1]//Column order
				 int conInt = rowl[row-1]//Row order
				 //println( "ConInt" + [conInt] + "Comint" +[comInt])
					 
					 if (col == 0 && row != 0)
					 {
						 
						/* println("all conscepts are getting added to the column which were in rows")
						 def conName = test[conInt][0]
						 colname = "<th class='rotate-45'><div class='tableHeader'><span>" +conName +" </span></div></th>"
						 println("colnames are "+ conName + "with row" + conInt )
						  html = html+colname;
						 */
						 def conName = test[conInt][0]
						
						 if ( col == 0)
						 {
							  colname  = " <td><div style='position:relative' class='colhead_item' id='first_item'>" + conName +" </div></td>"
						 }
						 else
						 {
							  colname  = "  <td><div style='position:relative' class='colhead_item'>" + conName +"</div></th>"
						 }
						 
						 
						// colname  = "<th class='rotate'><div class='rotateD'><span class='intact'>"+conName+"</div></span></th>"
						 
						 
						 html = html+colname;
					 }
			 
				else
				{
					
					if (row == 0 && col != 0)
					{
						
						println(col+  " compound is " + test[0][comInt])
						def comName  =  test[0][comInt].toString()
						println("*********************************************************************************"+comName.length())
						if(comName.length() > 31)
						{
						 comName = comName.substring(0, 30);
						}
						
						rownames =rownames + '<td class="row-header">' +comName+ '</td>'
					
						println(comName)
						
						
					}
					else if(col != 0 && row !=0)
					{
						println(test[0][comInt]+'values are' + "ConInt" + [conInt] + "Comint" +[comInt])
						def value = test[conInt][comInt];
						println (value)
						
						if(value > 90 && value <= 100)
						{
							
							color = 'red '
						}
						else if(80<value && value <= 90)
						{
							
							color = '#FF3300 '
						}
						else if(value > 70 && value <= 80)
						{
						
							color = '#ff6600 '
						}
						else if(value > 60 && value <= 70)
						{
							
							color = '#ff9900 '
						}
						else if(value > 50 && value <= 60)
						{
							
							color = '#FFCC00  '
						}
						else if(value > 40 && value <= 50)
						{
							
							color = '#FFFF00 '
						}
						else if(value > 30 && value <= 40)
						{
							
							color = '#ffff33 '
						}
						else if(value > 20 && value <= 30)
						{
						
							color = 'ffff66'
						}
						else if(value > 10 && value <= 20)
						{
						
							color = '#ffff99 '
						}
						else if(value >0 && value <= 10 )
						{
							println('inside')
							color = 'ffffcc '
						}
						else{
							color = 'white '
						}
						rownames =rownames + '<td><div class="area" style="background-color:'+color+'  ">' +value+ '</div></td>'
						
					}
				
				
					}
				
			 
		 }
		 //End of loop
			 if(col !=0)
			 {
			 rownames = rownames+'<td><div class="area"> --</div></td></tr>'
			 }
		 html2 = html2+rownames;
		
	 }
	 println(html2)
	 [html:html,html2:html2,con:con]
	 
	
	 
 }
 
 
 
 
 def showError()
 {
	 render "network has more than 500 compounds please select more stringent filter"
 }
 
 def mockup()
 {
	 
	 println("from test"+params)
	 def fil = params.fil
	 def id2 = params.id2
	  def db = []
	 
	 if(params.statement instanceof java.lang.String)
	 {
		db.add(params.statement);
	 }
	 else
	 {
		 db = params.statement.toList()
	 }
	 
	 def odds= params.odds
	 MockupHeatMap hm = new MockupHeatMap();
	 String dbname = "";
	 db.each{ dbname = dbname+"'"+it+"'"+"," }
	 println("DBNAME is "+dbname)
	 dbname =dbname.substring(0,dbname.lastIndexOf(","))
	 Object[][] test  = hm.getConcept(params.q.toInteger(), fil, id2,odds,dbname);
	 test[1].collect().toArray().toString()
	 BufferedImage bImage =hm.createBufferredImage(test,params.q.toInteger());
	 
	 final String uuid = UUID.randomUUID().toString().replaceAll("-", "");
	 
	 def name = uuid+".png"
	 println("name of file is " +  name)
	 
	 
	 
	 try {
		 String fileName = "/tmp/images/"+name
		   if (ImageIO.write(bImage, "png", new File(fileName)))
		   {
			   System.out.println("-- saved" +  fileName);
		   }
   } catch (IOException e) {
		   
		   e.printStackTrace();
   }
   
	 //OutputStream outputStream = response.getOutputStream();
	 //ImageIO.write(bImage, "png", outputStream)
	 bImage.getGraphics()
	 int coll = test[1].length
	 int rowl = test.size()-1
	 println("column are" + coll + "Rowsa are " + rowl)
	 String html =""
	 String html2 =""
	 
	 //Go through each col..First column is concept name and then for each column take values and add compund name at the end of the row.
 for(int col = 0; col< coll; col++)
 {
	 String colname =" ";
	 String color;
	 String rownames = "<tr>"
				 for (int row = 0 ; row < rowl ; row++)
				 {
					 if(row ==  0 && col == 0 )
					 {
						 System.out.println("inside row adn column zero"+ row + "   " + col);
					 }
					 else
					{
					if ( col == 0 )//THis is for first column only to extract conceptname
					{
						if ( row == 1)
						{
							 colname  = " <th class='rotate-45'><div class='tableHeader'><span>" + test[row][col] +" </span></div></th>"
						}
						else
						{
							 colname = "<th class='rotate-45'><div class='tableHeader'><span>" + test[row][col] +" </span></div></th>"
						}
					
						html = html+colname;
					}
					else{
						if (row == 0)
						{
						rownames =rownames + '<th class="row-header">' +test[row][col]+ '</th>'
							
						}
						else
						{
							println("create a <= 90 && a > 80 te"  + test[row][col]  + test[row][col].class)
						if(test[row][col] > 90 && test[row][col] <= 100)
						{
						
							color = 'red '
						}
						else if(test[row][col] > 80 && test[row][col] <= 90)
						{
							
							color = '#FF3300 '
						}
						else if(test[row][col] > 70 && test[row][col] <= 80)
						{
							
							color = '#ff6600 '
						}
						else if(test[row][col] > 60 && test[row][col] <= 70)
						{
							
							color = '#ff9900 '
						}
						else if(test[row][col] > 50 && test[row][col] <= 60)
						{
							
							color = '#FFCC00  '
						}
						else if(test[row][col] > 40 && test[row][col] <= 50)
						{
							
							color = '#FFFF00 '
						}
						else if(test[row][col] > 30 && test[row][col] <= 40)
						{
							
							color = '#ffff33 '
						}
						else if(test[row][col] > 20 && test[row][col] <= 30)
						{
						
							color = '#ffff66'
						}
						else if(test[row][col] > 10 && test[row][col] <= 20)
						{
							
							color = '#ffff99 '
						}
						else if(test[row][col] >0 && test[row][col] <= 10 )
						{
							
							color = '#ffffcc '
						}
						else{
							color = 'white '
						}
					  rownames =rownames + '<td><div style="height:100%; width:100%;background-color:'+color+'  ">' +test[row][col]+ '</div></td>'
						}
					}
					}
				 }
				
				 html2 = html2+rownames+"</tr>";
				
		 
	 }
 
	 println ("html is" +  html)
	 println("rowname is /n"+html2)
	 
	 File outputfile = new File("saved.png");
	 ImageIO.write(bImage, "png", outputfile);
	 
	// render html
	 [html:html,html2:html2,name:name]
 }
 def test()
 {
	 //def id = params.id
	 println("from test"+params)
	 def fil = params.fil
	 def id2 = params.id2
	 def db = params.statement.toList()
	 def odds= params.odds
	 
	 /*
	 JDBCConnectionTest jbc = new JDBCConnectionTest();
	 String dbname = "";
	 db.each{ dbname = dbname+"'"+it+"'"+"," }
	 println("DBNAME is "+dbname)
	 dbname =dbname.substring(0,dbname.lastIndexOf(","))
	  //Object[][] BufferedImage
	  BufferedImage image =jbc.getConcept(params.q.toInteger(), fil, id2,odds,dbname);
	 // def img = image.bytes
	 /*
	 OutputStream outputStream = response.getOutputStream();
	 ImageIO.write(image, "png", outputStream)*/
	 BufferedImage bimage, ximage, yimage;
	 Image bi, xi, yi;
	 HeatMapNewJS hm = new HeatMapNewJS();
	 String dbname = "";
	 db.each{ dbname = dbname+"'"+it+"'"+"," }
	 println("DBNAME is "+dbname)
	 dbname =dbname.substring(0,dbname.lastIndexOf(","))
	 Map<String,BufferedImage> mapImages  = hm.getConcept(params.q.toInteger(), fil, id2,odds,dbname);
	//  ImageIO.write(image, "PNG", response.outputStream)
	 //render image
	for (Map.Entry<String, String> mapE : mapImages.entrySet())
	 {
		 System.out.println(mapE.getKey() + "/" + mapE.getValue());
		 String image = mapE.getKey();
		if(image.equals("image"))
		{
			 bimage=  mapE.getValue();
			 
		}
		else if(image.equals("xaxis"))
		{
			 ximage=  mapE.getValue();
		}
		else{
			yimage=  mapE.getValue();
		}
	 }
	 OutputStream outputStream = response.getOutputStream();
	 OutputStream outputStream2 = response.getOutputStream();
	 [bimage:ImageIO.write(bimage, "png", outputStream), ximage:ImageIO.write(ximage, "png", outputStream2), yimage:ImageIO.write(yimage, "png", outputStream)]
 }
 
 def filterSlider()
 {
		 
	 println("FilterSlider params"+params)
	 def check = params.check
	 def q = params.q
	 def con = Concepts.get(q.toInteger())
	 def resultcount = params.ct
	 def statement = params.statement.toList();
	 
	 if(params.containsKey("json"))
	 {
		 def jsonobj = params.json
		 println(jsonobj.class)
		 
		 
	 }
	
		 
	 println("From controller" + check);
	 
	 
	 
	 [check:check, resultcount:resultcount, con:con, statemnt:statement]
		
		   
		
	 //  render jsonMap
	  
	 
	 
 }
 def showEnrichedConcepts()
 {
		 
	 println("From showEnrichedConcepts" +  params.q)
	def dbc = params.dbc
	def q = params.q
	def con = Concepts.get(q.toInteger())
	
	
	
	
	
	
	
	
	[dbc:dbc, con:con]
	/*
	// List<heatMapResults> pdfFile = new LinkedList<heatMapResults>();
	  
	 
	  
	  HashMap jsonMap = new HashMap();
	 
	
		// println(pdfFile[i].getRow()+ "=="+ pdfFile[i].getCol()+ "=="+pdfFile[i].getVal());
		 jsonMap.data = pdfFile.collect {en -> return [en.getVal(),en.getRow(),en.getCol()]}
		 
		
			 
	// println jsonMap
	 
	 println("From controller"+pdfFile.size())
	 [pdfFile:pdfFile, dataSize:pdfFile.size() ] */
	 
	 
		  /*
		   *  <g:set var="cnm" value="${namePdf}"/>
  ${cnm}
	 <embed src="${resource(dir:'pdf', file:'3_100.pdf')}" type="application/pdf" height="800" width="1000"/>
   <g:img uri="/pdf/Kunal.jpg"/>
   <a href="${resource(file:'hist.pdf')}" target="_blank">
		   */
		  
		
		  
		  //[result:result, b:b]
	 }
	 

	 
 
	 



def showHeatmap()
{
	println("say hello")
}
//***********************************************************LIST,CREATE,SAVE,SHOW,EDIT,UPDATE,DELETE**************************************************************************
	
 def list(Integer max) {
	 
	 
		params.max = Math.min(max ?: 10, 100)
		[enrichmentsInstanceList: Enrichments.list(params), enrichmentsInstanceTotal: Enrichments.count()]
	}

	def create() {
		[enrichmentsInstance: new Enrichments(params)]
	}

	def save() {
		def enrichmentsInstance = new Enrichments(params)
		if (!enrichmentsInstance.save(flush: true)) {
			render(view: "create", model: [enrichmentsInstance: enrichmentsInstance])
			return
		}

		flash.message = message(code: 'default.created.message', args: [message(code: 'enrichments.label', default: 'Enrichments'), enrichmentsInstance.id])
		redirect(action: "show", id: enrichmentsInstance.id)
	}

	def show(Long id) {
		def enrichmentsInstance = Enrichments.get(id)
		if (!enrichmentsInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'enrichments.label', default: 'Enrichments'), id])
			redirect(action: "list")
			return
		}

		[enrichmentsInstance: enrichmentsInstance]
	}

	def edit(Long id) {
		def enrichmentsInstance = Enrichments.get(id)
		if (!enrichmentsInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'enrichments.label', default: 'Enrichments'), id])
			redirect(action: "list")
			return
		}

		[enrichmentsInstance: enrichmentsInstance]
	}

	def update(Long id, Long version) {
		def enrichmentsInstance = Enrichments.get(id)
		if (!enrichmentsInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'enrichments.label', default: 'Enrichments'), id])
			redirect(action: "list")
			return
		}

		if (version != null) {
			if (enrichmentsInstance.version > version) {
				enrichmentsInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
						  [message(code: 'enrichments.label', default: 'Enrichments')] as Object[],
						  "Another user has updated this Enrichments while you were editing")
				render(view: "edit", model: [enrichmentsInstance: enrichmentsInstance])
				return
			}
		}

		enrichmentsInstance.properties = params

		if (!enrichmentsInstance.save(flush: true)) {
			render(view: "edit", model: [enrichmentsInstance: enrichmentsInstance])
			return
		}

		flash.message = message(code: 'default.updated.message', args: [message(code: 'enrichments.label', default: 'Enrichments'), enrichmentsInstance.id])
		redirect(action: "show", id: enrichmentsInstance.id)
	}

	def delete(Long id) {
		def enrichmentsInstance = Enrichments.get(id)
		if (!enrichmentsInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'enrichments.label', default: 'Enrichments'), id])
			redirect(action: "list")
			return
		}

		try {
			enrichmentsInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'enrichments.label', default: 'Enrichments'), id])
			redirect(action: "list")
		}
		catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'enrichments.label', default: 'Enrichments'), id])
			redirect(action: "show", id: id)
		}
	}
}