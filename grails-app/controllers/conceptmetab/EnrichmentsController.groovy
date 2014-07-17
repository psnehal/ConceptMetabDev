package conceptmetab
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image
import java.awt.event.ItemEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import org.rosuda.REngine.*;
import org.apache.commons.io.output.WriterOutputStream

//import org.conceptmetab.java.CreateHeatmap
import org.conceptmetab.java.DetailedHeatmap
import org.conceptmetab.java.DrawHeatMap
import org.conceptmetab.java.HeatMapNewJS
import org.conceptmetab.java.JDBCConnectionTest;
import org.conceptmetab.java.MockupHeatMap
import org.conceptmetab.java.DrawHeatMapInR
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
	
	def mojozoom(){
		
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
		def db = []
		 
		 if(params.statement instanceof java.lang.String)
		 {
			println("Statement is only one string")
			db.add(params.statement);
		 }
		 else
		 {
			 db = params.statement.toList()
		 }
	   
		HashMap jsonMapN = new HashMap()
		println("db  is"+ db.class)
		String odds = params.odds.toString()
		def check
		
		
	   
		if (odds.length() ==0)
		{
			odds = '4.5035996273705e+15'
		}	
		
	   check = createEnrichedConceptService.createNetwork(filter, con,  id1, id2,odds, db)
	   println("From redirect view action" +check);
 
				//println(dbname.class)
		   
		   def concept = Concepts.get(con.toInteger())
		  
		  
		 [ check:check,concept:concept,db:db,resultcount:check.toString().length() ]
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
			if(conceptsInstance.concept_types.fullname.contains('MeSH'))
			{
				
				def meshid2treenumInstance = Meshid2treenum.findAllWhere(mesh_id : conceptsInstance?.original_id )		
				//println("meshid2treenumInstance" + meshid2treenumInstance)		
				conceptsInstance.original_id = meshid2treenumInstance.tree_id
				 
			}
			
		
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
								return [pubid:ids.pubchem_id,name:ids.name, keid : ids.kegg_id, id : ids.internal_id]
				}
		
		def map = [:]
		String names
		comIns.each {
			
			if(map.containsKey(it.id))
			{
				 names= map.get(it.id).names
				names = names+"; "+ it.name
				
			}
			else
			{
				names = it.name
			}
			
			
				def value = [:]
				println("names" + names)
				value.put('names', names)		
				value.put('pubid', it.pubid)
				value.put('keid', it.keid)			
				map.put(it.id, value)
				
		}		
		
		//BigDecimal pval = intersection.pval
		
		
		println ("comins"+comIns)
		[enrichInstance:enrichInstance,map:map]
		
	}
	
	def displayLgd()
	{
		println("displayLgd")
		
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
		 println("CreateChart"+params)
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
		// println(allid)
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
	   
		 def tableold = [cols: columns, rows: rows]
		 jsonMap.table = tableold
		println map
		 
		// println("jsonMap is+" +  emptyList)
		 //def allR =map.collect {ids -> return [ids.getKey().toString(),ids.getValue()]}	   
		//render map
		
		def dbc = tableold as JSON
		def clst = emptyList as grails.converters.JSON
		println ("dbc is" + dbc)
			[dbc:dbc, map:map,clst:clst,rsize:result.size(),emptyList:emptyList]
	 
 }
 
 
 def createChartnew()
 {
	 println("Statement is only one string")
	 def filter = params.fil;
	 def con = params.q.toString();
	 def id1 = params.id1.toString();
	 def id2 = params.id2.toString();
	 def db = []
	 def odds = params.odds
	 HashMap jsonMap = new HashMap()
	 
	 
	//Error handling for single database selectiom 
	 if(params.statement instanceof java.lang.String)
	 {
		println("Statement is only one string")
		db.add(params.statement);
	 }
	 else
	 {
		 db = params.statement.toList()
	 }
	 
	 
	 //Error handling for no odds ration selection:
	 
	 
	 if (odds.length() ==0)
	 {
		 odds = '4.5035996273705e+15'
	 }
			 
	
	 
	 
	def mapR = createEnrichedConceptService.showEnrichedConcepts(filter, con,  id1, id2,odds, db)
	
	println("from Show"+params)
	
	println("Map paramaters from CreateChart form Enrichment controller is : fillter"+filter + "con: "+ con + "db " + db)
	 
	println("Map size from CreateChart form Enrichment controller is : "+mapR.size())
	
	
	
	def allid = mapR.collect {en ->
			 return en.ctfull
				//println("Id1 is concept of interest")	
		}
	
	def query = Concepts.get(con)
	def conId = [id: query.id.toString(),label:query.name.capitalize(), comNo:query.num_compounds,conTypes:query.concept_types.getName() ]
	
	
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
 
   def tableold = [cols: columns, rows: rows]
   jsonMap.table = tableold
  println map
   
   println("jsonMap is+" +  emptyList)
 
   //def allR =map.collect {ids -> return [ids.getKey().toString(),ids.getValue()]}
   
 
 
  //render map
  def dbc = tableold as JSON
  
  
  def clst = emptyList as grails.converters.JSON
  
  println emptyList.getAt(0)
	  [dbc:dbc, map:map,clst:clst,rsize:mapR.size(),emptyList:emptyList]
	 
	 
	 
 }
 
 def redirectView()
 {
	println("from redirectView"+params)
	 def filter = params.fil;
	 def con = params.q.toString();
	 def id1 = params.id1.toString();
	 def id2 = params.id2.toString();
	 def db = []
	 HashMap jsonMapN = new HashMap()
     String odds = params.odds.toString()
	 def map	 
	 if(params.statement instanceof java.lang.String)
	 {
		println("Statement is only one string")
		db.add(params.statement);
	 }
	 else
	 {
		 db = params.statement.toList()
	 } 
	
	 //**************************************************************For Sorting *****************************************
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

//************************************************************Parameter handling *****************************************
	
	 if (odds.length() ==0)
	 {
		 odds = '4.5035996273705e+15'
	 }
			 
	 if(params.containsKey("CompleteNetwork"))
	 {
		 
		 forward(action: "createJson", params:params)
		 
	 }
	 if(params.containsKey("heatmap"))
	 {
		 
		 forward(action: "drawHeatmap", params:params)
		 
	 }		 
	 if(params.containsKey("table"))
	 {
		 
		 forward(action: "table", params:params)
		 
	 }
	 if(params.containsKey("testHeatMapInR"))
	 {
		 
		 forward(action: "drawHeatmapInR", params:params)
		 
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
					
				
			println("Map size from redirect view is : "+map.size())
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
						 println(en.flag)
						 if(en.rel != 0 && en.flag.equals("id2") )
						 {
							 println("it in another loop")
							 return [source: en.id.toString() , target: con.toString(),id: (en.pval),db_id: en.enid.toString(),thick: (en.ins),label: en.eid,rel:en.rel]
						 }
						 else
						 {
							 	 return [source: con.toString(), target: en.id.toString(),id: (en.pval),db_id: en.enid.toString(),thick: (en.ins),label: en.eid,rel:en.rel]
								  
						 }		  
												  }
						  def check = jsonMapN as JSON
						  println(jsonMapN)
						 [check:check, resultcount : map.size()]
					 forward(action: "filterSlider", params:[check:check, ct:map.size(),q:con])
				 }
				 
				 else if (params.containsKey("chart"))
				 {
					
					 forward(controller:"concepts", action:"show", id: params.q,params:[map:map])
					
				 }
				 
				 
				 else if(params.containsKey("tableOld"))
				 {
					 println("inside enrichment ttable"+params)
					 println(map.getClass())
					 def html;
				
						def map2 =[]
						map2= map 
						
						println(map2)
						
					 
					 redirect(action: "tableOld",params:[map:map2])					
					 
				 }
				 else{
					 /*
					 //map size for no of concepts selected and 
					 def con_com = Concepts.get(con.toInteger()).num_compounds
					 if (map.size > 300 || con_com > 300 )
					 {
					redirect(action: "drawHeatmap", params: params)
					 }
					 else
					 {
						 redirect(action: "clusterStr", params: params)
					 }
					//redirect(action: "mockup", params: params)
					//redirect(action: "test", params: params) */
					 
					 render "Please select atlest on option"
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
 
 
 def table()
 {
	 println("inside ttable"+params)
	 println("statement are" + params.statement + "class " + params.statement.getClass())
	 def db = []
	 
	 if(params.containsKey("extension"))
	 {
		println("insde extension loop")
		def dbe = params.statement.replace("[", "").replaceAll("^\\s+", "");
		def databaseNames = dbe.replace("]", "").tokenize(",")
		println(databaseNames.getClass())
		println( databaseNames)
		db = databaseNames.collect()
		 
		 
	 }
	 else
	 {
		 
		 if(params.statement instanceof java.lang.String)
		 {
			println("Statement is only one string")
			db.add(params.statement);
		 }
		 else
		 {
			 db = params.statement.toList()
		 }
				 
		 
	 }
	 
	 println("at the end" + db.size())
	 def filter = params.fil;
	 def con = params.q.toString();
	 println("inside ttable con is"+con)
	 def id1 = params.id1.toString();
	 def id2 = params.id2.toString();
	
	 def conceptInstance = Concepts.get(con.toBigDecimal())
	 HashMap jsonMapN = new HashMap()
	 String odds = params.odds.toString()
	 def map
	 
	 //**************************************************************For Sorting *****************************************
	 String fil =params.sort
	 String order =params.order
	 
	 map = createEnrichedConceptService.showEnrichedConcepts(filter, con,  id1, id2,odds, db)
	 //[enid:3138797, id:2722, name:ketone body biosynthetic process, comNo:13, eid:GO:0046951, ctypes:GOBP, ctfull:GO Biological Process, pval:5.533E-9, qval:7.682E-8, ins:5, odds:999999999],
	 println("After the call"+map)
	 def html
	def allR =map.collect {
			ids -> 
			if(ids.ctfull.contains("MeSH")){								 
						    def meshid2treenumInstance = Meshid2treenum.findAllWhere(mesh_id : ids.eid)
							if(meshid2treenumInstance.size() != 0)
							{							
						    return [id:ids.id.toString(),name:ids.name.capitalize(),conid:meshid2treenumInstance.get(0).tree_id,conTypes:ids.ctfull, pval:ids.pval.toDouble(),qval:ids.qval.toDouble(), ins:ids.ins, odds:ids.odds.toDouble()]
							}
							else
							{
								return [id:ids.id.toString(),name:ids.name.capitalize(),conid:ids.eid,conTypes:ids.ctfull, pval:ids.pval.toDouble(),qval:ids.qval.toDouble(), ins:ids.ins, odds:ids.odds.toDouble()]
							}
						 }
					 
		  
		  else{
				    return [id:ids.id.toString(),name:ids.name.capitalize(),conid:ids.eid,conTypes:ids.ctfull, pval:ids.pval.toBigDecimal(),qval:ids.qval.toDouble(), ins:ids.ins, odds:ids.odds.toDouble()]
			 
	      }
	 }
	 println(allR.getClass() )
	 println( "Map class is " + allR)
	 
	 
	 
	 def fields = ["name", "conid", "conTypes","pval","qval","ins","odds"]
	 def labels = ["name": "Concept name","conid": "Concept ID", "conTypes": "Concept Types","pval" :"P-Value","qval" :"Q-Value","ins" :"Overlap","odds" :"Odds ratio"]
	 
	 if(params?.format && params.format != "html")
	 { 
		 response.contentType = grailsApplication.config.grails.mime.types[params.format] 
		 response.setHeader("Content-disposition", "attachment; filename=Compounds_in_concepts.${params.extension}")
		 exportService.export(params.format, response.outputStream,allR,fields,labels, [:], [:]) }
	 
	
	
	 	 
	 [conceptInstance:conceptInstance,allR:allR]
	 
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
	// println("column are" + coll + "Rowsa are " + rowl )
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
				 
				 //println("all conscepts are getting added to the column which were in rows")
				 def conName = test[conInt][0]
				 colname = "<th class='rotate-45'><div class='tableHeader'><span>" +conName +" </span></div></th>"
				// println("colnames are "+ conName + "with row" + conInt )
				  html = html+colname;
				 
			 }
			 
			else
			{
				
				if (row == 0 && col != 0)
				{
					
					println(col+  " compound is " + test[0][comInt])
					def comName  =  test[0][comInt].toString()
					
					if(comName.length() > 31)
					{
					 comName = comName.substring(0, 30);
					}
					
					rownames =rownames + '<th class="row-header">' +comName+ '</th>'
					
					
					
				}
				else if(col != 0 && row !=0)
				{
					
					def value = test[conInt][comInt];					
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
 
 def drawHeatmapInR(){
	 
	 println "detalied heatmap"+ params;
	 
	 def fil = params.fil
	 def id2 = params.id2
	 def odds= params.odds
	 def db =[]
	  if(params.statement instanceof java.lang.String)
	  {
		 println("Statement is only one string")
		 db.add(params.statement);
	  }
	  else
	  {
		  db = params.statement.toList()
	  }
	 def q = params.q.toInteger()
	  String dbname = "";
	 db.each{ dbname = dbname+"'"+it+"'"+"," }
	 dbname =dbname.substring(0,dbname.lastIndexOf(","))
	 DrawHeatMapInR dh = new DrawHeatMapInR();
	 Object[][] mtr  = dh.getConcept(q, fil, id2,odds,dbname);
	 println("Got the matrix")
	 
	 UUID uuid = UUID.randomUUID();
	 String imageename = ""+uuid+".png";
	 String textfile = "/tmp/"+uuid+".txt";
	 String filename = ""+uuid+".txt";
	 dh.writeArrayToFileRowise(mtr,textfile);	 
	println("got the file")
	 
	 def colM = mtr[1].length;
	 def rowM = mtr.length
	 def con = Concepts.get(q)
	 
	 Object[][] test = mtr
	 List<String[]> l = new ArrayList<String[]>(Arrays.asList(test));
	 def no = l.size()-1
	 println("removed this line")
	 l.remove(no)
	 Object [][] forCluster = l.toArray()	 
	 println("removed last row and back to object")
	
	 //def te = new JsonBuilder( forCluster ).toString()
	 int compoundsize = mtr[1].length;
	 int BI_WIDTH =  (colM-1)*20 ;
	 int BI_HEIGHT =(rowM -1)*20;
	
	 int wXaxis = BI_WIDTH ;
	 int hXaxis = 150;
	
	int wYaxis = 250;
	int hYaxix = BI_HEIGHT
	
	 def clusterMat = connectRService.clusterAnalysisForR(filename,BI_HEIGHT,BI_WIDTH)
	
	 int[] coll = clusterMat.colOrd

	 
	 String html =""
	 String html2 =""
	// println("col is " + clusterMat.colOrd)
	// println("row is " + clusterMat.rowOrd)
	 println("Row is " +  forCluster.size()  + " Col is   :=" + forCluster[1].length + "   col order is :=" + coll.size() +  "row order is := " + coll.size())
	 def rowl = clusterMat.rowOrd.toList()
	 rowl = rowl.reverse()	 
	 println("row is reversed " + rowl) 
	 
	 int rc_width = 20;
	 int rc_height = 20;
	 
	
	
	println("create image" + "with mtr cols "+ colM + " width " + BI_WIDTH )
	println("create image" + "with mtr rows "+ rowM + " width " + BI_HEIGHT )
	
	
	println("height"+ BI_HEIGHT)
	println("width"+ BI_WIDTH)
	BufferedImage bImage = new BufferedImage(BI_WIDTH, BI_HEIGHT,BufferedImage.TYPE_INT_RGB);
	 BufferedImage bXaxis = new BufferedImage(wXaxis, hXaxis,BufferedImage.TYPE_INT_RGB);
	 BufferedImage bYaxis = new BufferedImage(wYaxis, hYaxix,BufferedImage.TYPE_INT_RGB);
	 def x_cor = 0
	 def   x_fcor = 0
	 def y_cor =0
	 def y_fcor=0
	 Graphics2D g2d = bImage.createGraphics();
	 Graphics2D gXaxis = bXaxis.createGraphics();
	 Graphics2D gYaxis = bYaxis.createGraphics();
	
	 gXaxis.setBackground(Color.WHITE);
	 gYaxis.setBackground(Color.WHITE);	 
	 gXaxis.fillRect(0, 0, wXaxis, hXaxis);
	 gYaxis.fillRect(0, 0, wYaxis, hYaxix);
	 
	 
	
	 for (int row = 0 ; row< forCluster.size(); row++)
	 
		 {
			 x_fcor = 0
			 
				 int conInt = rowl[row-1]//R
				
				 for (int col = 0 ; col <  forCluster[1].length;col++)
					 {
						 int comInt = coll[col-1]
						 
						  if(row==0 && col != 0){
							
							
							 //println("its in compound name loop"+  comInt)
							 println()
							 
								 int ch = x_cor+160;
								 final AffineTransform saved = gXaxis.getTransform();
								 final AffineTransform rotate = AffineTransform.getRotateInstance(-Math.PI /2, ch, 140);
								 gXaxis.transform(rotate);
								
								
									  String colname = mtr [0][comInt].toString();
									// System.out.println("Entering column  " + col + "with colname"+ colname + "with x_cor" + ch);
									  if(colname.length() > 30)
										 {
										  colname = colname.substring(0, 30);
										 }
									  gXaxis.setColor(Color.black);
									  if (compoundsize < 50 )
									  {
										 // System.out.println("compound size is less than 50 loop");
									  gXaxis.setFont(new Font( "SansSerif", Font.BOLD, 15 ));
									  }
									  else
									  {
										  gXaxis.setFont(new Font( "SansSerif", Font.BOLD, 25 ));
									  }
										
										
								   //   System.out.println("Colname is " +  colname + "added to "  + "[" + 0 +"," +col +"]" + "with x  and y cor" + ch + "and 0"  );
									  gXaxis.drawString(colname, ch, 0);										
									  gXaxis.setTransform(saved);
									  x_cor = x_cor+rc_height;
									  
						 }
						  
						  else if(col == 0 )
						  {
							  
							  String rowname = mtr[conInt][0].toString();
							  gYaxis.setColor(Color.black);
							  gYaxis.setFont(new Font( "Sansserif", Font.BOLD, 20 ));
							  int ch2 = compoundsize*20 +10;
							 // System.out.println("Entering row  " + row + "with Rowname  "+ rowname + "  with y_cor  " + y_fcor);
							  gYaxis.drawString(rowname, 10, y_fcor);
							  //System.out.println("Rowname is " +  rowname + "added to "  + "[" + row +"," +col +"]" + "with x "+x_cor+" and y cor" + y_fcor  );
							  y_fcor = y_fcor+ rc_height;
							  
						  }
						  
						  else
						  {
							  int check = (Integer) mtr[conInt][comInt];
							  
							  if(check == 0)
								  {									
									   g2d.setPaint ( new Color ( 255, 255, 255 ) );
									   //System.out.println("Its for no with check  " +check +"  and cord   x ="+x_fcor+"  y=" + y_cor+  "for [ " +row +"," +col+"]" );
									   g2d.fillRect(x_fcor,y_cor,rc_width,rc_height);
									   x_fcor = x_fcor+ rc_height;
								  }
								  else
								  {
									  int g = dh.getColor(check);										
									  g2d.setPaint ( new Color ( 255,g,0 ) );
									 // System.out.println("Its for yes with check " +check +"and cord x = "+x_fcor+" y= " + y_cor+ "for [ " +row +"," +col+"]");
									   g2d.fillRect(x_fcor,y_cor,rc_width,rc_height);
									   x_fcor = x_fcor+ rc_height;
							   }
						  
						  }
						  
						 //Column order
						  
							 
					}//for inside 
					 
					 if(row != 0)
					 {
					 y_cor = y_cor+rc_height;
					 }
					// println("ycor" + y_cor + "["+ row+"]")
			
		 }//Foroops
				 
		 String fileXaxis ="/tmp/"+ uuid+"_"+ "_Xaxis"+".png";
		 ImageIO.write(bXaxis, "png", new File(fileXaxis))
		 String fileYaxis ="/tmp/"+ uuid+"_"+ "_Yaxis"+".png";		
		 ImageIO.write(bYaxis, "png", new File(fileYaxis))
		 String main ="/tmp/"+ uuid+"main"+".png";
		 ImageIO.write(bImage, "png", new File(main))
		 
		 System.out.println("-- saved" +  main);
	 
		 def height= bYaxis.getHeight();
		 def width = bXaxis.getWidth();
		 
		 println("Width from java is"+width +"  from R is "  + " height from java is " + height + "  height from R is " ) 
	 
		 [ximage:fileXaxis.replace("/tmp/", ""),bimage:imageename,yimage:fileYaxis.replace("/tmp/", ""),height:height,width:width,con:con]
	 
	 
	 
 }
 
 
 def drawHeatmap()
 {
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
	 DrawHeatMap hm = new DrawHeatMap();
	 String dbname = "";
	 db.each{ dbname = dbname+"'"+it+"'"+"," }
	 println("DBNAME is "+dbname)
	 dbname =dbname.substring(0,dbname.lastIndexOf(","))
	 def  filename = hm.getConcept(params.q.toInteger(), fil, id2,odds,dbname);
	 
	def bimage = filename+".png"
	def textimage = filename+".txt"	
	
	def clusterMat = connectRService.ComHeatmap(filename)
	def con = Concepts.get(params.q.toInteger())
	[bimage:bimage,textimage:textimage,con:con]
	 
 }
 
 def downloadFile()
 {
	 
	 println params;
	 InputStream contentStream
	
		 String filepath = "/tmp/"+params.filename		
		 def file = new File(filepath)
		 response.setContentType("text/csv") // or or image/JPEG or text/xml or whatever type the file is
		 response.setHeader("Content-disposition", "attachment;filename=${file.name}")
		 response.outputStream << file.bytes		
	
	
 }
 
 def tryHeatmap()
 {
	 
	 def fil = params.fil
	 def id2 = params.id2
	 def odds= params.odds
	 def filename = params.filename
	 def db =[]
	  if(params.statement instanceof java.lang.String)
	  {
		 println("Statement is only one string")
		 db.add(params.statement);
	  }
	  else
	  {
		  db = params.statement.toList()
	  }
	 def q = params.q.toInteger()
	  String dbname = "";
	 db.each{ dbname = dbname+"'"+it+"'"+"," }
	 dbname =dbname.substring(0,dbname.lastIndexOf(","))
	 DetailedHeatmap dh = new DetailedHeatmap();
	 Object[][] mtr  = dh.getConcept(q, fil, id2,odds,dbname);
	 mtr.collect {println it}
	 
	 	println("mtr compounds are"+ mtr[0])
		 def comName = mtr[0]
		 def te = new JsonBuilder( comName ).toString()
		 
		 
		 println("compound names are in json format are" + te )
		 for(int i=0; i < mtr.length; i++)
		 {
			// println ( "row is" +mtr[i]);
			 
		 }
		 
		 def rowlen = mtr.length
		 def comsize = mtr[1].length
		 def conName = [];
		 conName[0]=0
		 Map<String, Object> data = new HashMap<String, Object>();
		 Object[][] newmap;
		 
		 
		 List<Object> toReturn = new ArrayList<Object>();
		 def f = new File("/tmp/data.csv")
		 def f2 = new File("/tmp/genes.csv")
		 f.append('row_concept'+","+'col_compound'+","+'count'+"\r\n")
		 def addrowname ="["
		 for( int row =1; row< rowlen-1;row++)
		 {			 			
					
			 def rownew = row-1
			addrowname = addrowname + "["
			def lastrow = comsize-1;
		 for(int col =0; col< comsize;col++)
		 {
			 
			 
					if (col == 0)
					{
						conName.add(mtr[row][col])
						
					}
					else if (col == lastrow && col != 0)
					{
						//println("last col ["+mtr[row][col]+","+ row +","+col+"]]")
						addrowname= addrowname+ "["+mtr[row][col]+","+ row +","+col+"]],"
					}
					else
					{
						//newmap[rownew][colnew] = mtr [row[col]]
						
						//toReturn.add("["+mtr[row][col]+","+ row +","+col+"],")
						addrowname = addrowname+ "["+mtr[row][col]+","+ row +","+col+"],"						
						
						
					}
			
		 }	
		 	
			
		 
		
		 }
		 
		 /*
		 def ind = addrowname.lastIndexOf(',')
		 String tail = addrowname.substring(0,ind)
		 println("last index " + ind  + "tail "+ tail)
		 addrowname = addrowname + "]"
		 for( int row =0; row< rowlen-1;row++)
		 {
				 for(int col =0; col< comsize;col++)
				 {
					 f2.append(mtr[row][col]+",")
				 }
				 f2.append("\r\n")
		 }
		 */
		
		 
		 
	 
		 def tecon = new JsonBuilder( conName )
		 println("Concept names are in json format are" + tecon )
		 
		 def ind = addrowname.lastIndexOf(',')
		 String tail = addrowname.substring(0,ind)
		 //println("tail is"+tail)
		 addrowname = tail+"]"
		 
	 render addrowname.replaceAll("'", "")
	 
	 
	 
	 
	 
	 
 }
 
 
 def detailedHeatmap()
 {
	 println "detalied heatmap"+ params;
	 
	 def fil = params.fil
	 def id2 = params.id2
	 def odds= params.odds
	 def filename = params.filename
	 def db =[]
	  if(params.statement instanceof java.lang.String)
	  {
		 println("Statement is only one string")
		 db.add(params.statement);
	  }
	  else
	  {
		  db = params.statement.toList()
	  }
	 def q = params.q.toInteger()
	  String dbname = "";
	 db.each{ dbname = dbname+"'"+it+"'"+"," }
	 dbname =dbname.substring(0,dbname.lastIndexOf(","))
	 DetailedHeatmap dh = new DetailedHeatmap();
	 Object[][] mtr  = dh.getConcept(q, fil, id2,odds,dbname);
	 print("mtr is"+ mtr)
	 
	 def colM = mtr[1].length;
	 def rowM = mtr.length
	 
	 if (colM > 200 || rowM > 300 )
	 {
		 String bimage, ximage, yimage,height,width;
		 HeatMapNewJS hm = new HeatMapNewJS();
		 Map<String,String> mapImages =hm.createBufferredImageMod(mtr,q);
		 println("from test"+mapImages)
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
			else if(image.equals("height"))
			{
				 height=  mapE.getValue().toBigInteger();
			}
			else if(image.equals("width"))
			{
				 width=  mapE.getValue().toBigInteger();
			}
			else{
				yimage=  mapE.getValue();
			}
		 }
		 
		 println("from test"+bimage + "x " + ximage + "y " + yimage)
		 
		 
		 forward(action: "test", params: [ximage:ximage,bimage:bimage,yimage:yimage,height:height,width:width,q:q])
		
		 
		
		 
	 }
	 else
	 {
		 def con = Concepts.get(q)
		 
		 Object[][] test = mtr		 
		 List<String[]> l = new ArrayList<String[]>(Arrays.asList(test));		 	
		 def no = l.size()-1
		 println("removed this line"+l.get(no))		
		 l.remove(no)	
		 Object [][] forCluster = l.toArray()	
		 def te = new JsonBuilder( forCluster ).toString()
		 println(test)
		 //def te = new JsonBuilder( forCluster ).toString()				
		 def clusterMat = connectRService.clusterAnalysis(filename)
		 int[] coll = clusterMat.colOrd
		 int[] rowl = clusterMat.rowOrd		
		 String html =""
		 String html2 =""
		 println("col is " + clusterMat.colOrd)		 
		 println("row is " + clusterMat.rowOrd)		
		 println("Row is " +  forCluster.size()  + " Col is   :=" + forCluster[1].length + "   col order is :=" + coll.size() +  "row order is := " + rowl.size())
		 
		 
		//render forCluster
		 //CclusterMat has compounds in columns and concepts in rows ..For html we need to transpose matrix with compounds in cols
		
		 for (int col = 0 ; col <  forCluster[1].length;col++)
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
							def comName  =  test[0][comInt].toString()							
							if(comName.length() > 31)
							{
							 comName = comName.substring(0, 30);
							}							
							rownames =rownames + '<td class="row-header">' +comName+ '</td>'
						}
						else if(col != 0 && row !=0)
						{							
							def value = test[conInt][comInt];
							if(value > 90 && value <= 100)
							{								
								color = '#FF0000'
							}
							else if(80<value && value <= 90)
							{								
								color = '#FF3200'
							}
							else if(value > 70 && value <= 80)
							{							
								color = '#FF4B00'
							}
							else if(value > 60 && value <= 70)
							{								
								color = '#FF6400'
							}
							else if(value > 50 && value <= 60)
							{								
								color = '#FF7D00'
							}
							else if(value > 40 && value <= 50)
							{								
								color = '#FF9600'
							}
							else if(value > 30 && value <= 40)
							{								
								color = '#FFAF00'
							}
							else if(value > 20 && value <= 30)
							{							
								color = '#FFC800'
							}
							else if(value > 10 && value <= 20)
							{							
								color = '#FFE100'
							}
							else if(value >0 && value <= 10 )
							{
								color = '#FFFF00'
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
				 rownames = rownames+'<td><div class="area"> </div></td></tr>'
				 }
			 html2 = html2+rownames;
			
		 }
		
		 [html:html,html2:html2,con:con]
		 forward(action: "clusterStr",params:[html:html,html2:html2,q:q])
		 
	 }
	 
	 
 }
 
 def clusterStr(){
	 def con = Concepts.get(params.q.toInteger())
	
	 [html:params.html,html2:params.html2,con:con]
	 
	
	 
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
	 
	 String bimage, ximage, yimage,height , width;
	 bimage = params.bimage
	 ximage= params.ximage
	 yimage = params.yimage
	 height= params.height
	 width= params.width
	 
	 
	/* println("from test params are"+params)
	 //def id = params.id
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
	 

	 
	 Image bi, xi, yi;
	 HeatMapNewJS hm = new HeatMapNewJS();
	 String dbname = "";
	 db.each{ dbname = dbname+"'"+it+"'"+"," }
	 println("DBNAME is "+dbname)
	 dbname =dbname.substring(0,dbname.lastIndexOf(","))
	 //def test = hm.getConcept(params.q.toInteger(), fil, id2,odds,dbname);
	 Map<String,String> mapImages  = hm.getConcept(params.q.toInteger(), fil, id2,odds,dbname);
	//  ImageIO.write(image, "PNG", response.outputStream)
	 //render image
	 
	 println("from test"+mapImages)
	for (Map.Entry<String, String> mapE : mapImages.entrySet())
	 {
		 
		 
		 println("insilde for loop")
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
		else if(image.equals("height"))
		{
			 height=  mapE.getValue().toBigInteger();
		}
		else if(image.equals("width"))
		{
			 width=  mapE.getValue().toBigInteger();
		}
		else{
			yimage=  mapE.getValue();
		}
	 }
	 
	 println("from test"+bimage + "x " + ximage + "y " + yimage)
	 */
	 
	 def con = Concepts.get(params.q.toInteger())
	
	 
	 [ximage:ximage,bimage:bimage,yimage:yimage,height:height,width:width,con:con] 
	//// render test
	 
 }
 
 def filterSlider()
 {
		 
		 
	 println("FilterSlider params"+params)
	 def check = params.check
	 def q = params.q
	 def con = Concepts.get(q.toInteger())
	 def resultcount = params.ct
	 def statement = params.statement;
	 def db = []
		  
	  if(params.statement instanceof java.lang.String)
	  {
		 println("Statement is only one string")
		 db.add(params.statement);
	  }
	  else
	  {
		  db = params.statement.toList()
	  }
	 
	 if(params.containsKey("json"))
	 {
		 def jsonobj = params.json
		 println(jsonobj.class)
		 
		 
	 }
	
		 
	 println("From controller" + db);
	 
	 
	 
	 [check:check, db:db, resultcount:resultcount, con:con]
		
		   
		
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