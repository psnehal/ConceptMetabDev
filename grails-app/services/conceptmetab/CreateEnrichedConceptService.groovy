package conceptmetab

import grails.converters.JSON
import java.text.DecimalFormat


class CreateEnrichedConceptService {

	def serviceMethod() {

	}
	
	
	def showEnrichedConcepts(String filterI, String idI, String id1I,String id2I, String odds, ArrayList db)
	{
		
		println("filter :-->  " + filterI + "  id :--> "   +  idI + "  id1 :--> " + id1I + "   id2 ::--> " + id2I+"odds : :->" +odds + "db ::->" + db)
		String filter = filterI;
		long con = idI.toLong();
		BigDecimal id1_inter = id1I.toBigDecimal();
		BigDecimal id2_inter = id2I.toBigDecimal();
		def result
		
	
		
	   def enrichL =Enrichments.createCriteria()
	   result= enrichL.list {
			 or {
				 eq('id1.id',con)
				 eq('id2.id',con)
			 }
			 
			 le(filter,id2_inter)
			 ge('odds',odds.toBigDecimal())
			
			 order(filter)
		 }
		
		 println("list of interactions = "+result.size() +"   id1 = "+ id1_inter+"  id2 = "+ id2_inter+"  id  = "+ con +"  filter  = "+ filter+ "   db is " + db)
		DecimalFormat format = new DecimalFormat("0.###E0");
		DecimalFormat format2 = new DecimalFormat("0.###");
		def map = result.collect {en ->
			//println(en.id1.id  + "and con is " + con )
			
	//Flag id1 and id2 are added to the map to indentify position of conceptOfInterst in the table. If its at position at 1 then id1 else id2.
	//This position is important to map the remation in subset ans set relation mapping.		
					
			if(en.id1.id == con )
		{
				
				return [enid: en.id,id:en.id2.id,name:(Concepts.get(en.id2.id).getName()),comNo: (Concepts.get(en.id2.id).getNum_compounds()),eid: (Concepts.get(en.id2.id).getOriginal_id()),ctypes: en.id2.concept_types.getName(),ctfull: en.id2.concept_types.getFullname(),pval: format.format(en.pval),qval:format.format(en.qval),ins: en.intersection,odds:format2.format(en.odds),rel : en.relation,flag :"id1"]
				//println("Id1 is concept of interest")
				
		}
			else if (en.id2.id == con){
				
				return [enid: en.id,id:en.id1.id,name:(Concepts.get(en.id1.id).getName()), comNo: (Concepts.get(en.id1.id).getNum_compounds()),eid: (Concepts.get(en.id1.id).getOriginal_id()),ctypes: en.id1.concept_types.getName(),ctfull: en.id1.concept_types.getFullname(),pval: format.format(en.pval),qval:format.format(en.qval),ins:(en.intersection), odds:format2.format(en.odds),rel : en.relation,flag:"id2"]
			}
		
		}
		
		
		println("map from service"+map)
		println("db Size is"+ db.size() +"   map: === " + map )
		int c = 1
		
		List re = new ArrayList()
		if(db.contains("all"))
		{
				map.each{re.add(it) }
		}
		else{
			
			for (int i = 0; i<db.size(); i++)
			{
					
					map.each{
						println("ct full is  '"+ it.ctfull + "'  db is '"+ db.get(i)+"'")
						if (it.ctfull.trim().equals(db.get(i).trim())) {
							println("inside loop ct full is"+ it.ctfull + "db is "+ db.get(i))
							re.add(it)
						}
					}
			}
		}
		
		//println("Claas for filter"+ re.class + "Class for tets"+ re)
		
		//println("Claas for map"+ map.class + "Class for tets"+ test.class)
		//println("after loops" + test)
		//println("after loops size is" + test.size())*/
		
		return re
	}
	
	
	def createNetwork(String filterI, String idI, String id1I,String id2I, String odds, ArrayList db)
	{
		println("filter :-->  " + filterI + "  id :--> "   +  idI + "  id1 :--> " + id1I + "   id2 ::--> " + id2I+"odds : :->" +odds + "db ::->" + db)
		String filter = filterI;
		long con = idI.toLong();
		BigDecimal id1_inter = id1I.toBigDecimal();
		BigDecimal id2_inter = id2I.toBigDecimal();
		def result
		HashMap jsonMap = new HashMap()
		
	
		
		//This one gets list of concepts enriched with concept of interest
		
	   def enrichL =Enrichments.createCriteria()
	   result= enrichL.list {
			 or {
				 eq('id1.id',con)
				 eq('id2.id',con)
			 }
			 
			 le(filter, id2_inter)
			 ge('odds',odds.toBigDecimal())
			 order(filter)
		 }
		
	   
	   
		 println("list of interactions = "+result.size() +"   id1 = "+ id1_inter+"  id2 = "+ id2_inter+"  id  = "+ con +"  filter  = "+ filter)
		 
		 //Map is collection of concept ids 
			 def map = result.collect {en ->
							 //println(en.id1.id  + "and con is " + con )
							 if(en.id1.id == con )
						 {
								 
								 return en.id2.id.toLong()
								 //println("Id1 is concept of interest")
								 
						 }
							 else if (en.id2.id == con){
								 
								 return en.id1.id.toLong()
							 }
						 
						 }
			 
			 
			 def query = Concepts.get(con)
			 def allCon = map+ query.id
			 
			 //This is too check the concept types or to extract the concept id which belong to the user selection of database
			 
			 List re =[]
			 for (int i = 0; i<db.size(); i++)
			 {
					 map.each{
						 def stdb = Concepts.get(it).concept_types.getFullname()
						// println("ct full is =  "+ stdb  + "  db is = "+ db.get(i))
						 if (stdb.equals(db.get(i))) {
							 println("ct full is =  "+ stdb  + "  db is = "+ db.get(i))
							 re.add(it)
						 }
					 }
			 }
			 re.add(con)
			 println("re.size" + re.size() + "re is    "+re + "re.class " + re.class  +"map.class is " + map.class)
				
			
			 def conId = [id: query.id.toString(),label:query.name.capitalize(), comNo:query.num_compounds,conTypes:query.concept_types.getName() ]
			 
			// println("map i s:="+ map)
			 def enrichP =Enrichments.createCriteria()
			 
			 def res = enrichP.list {
			 
					 'in' ('id1.id',re)
					  'in' ('id2.id',re)
				le(filter,id2_inter)
				ge('odds',odds.toBigDecimal())
				//maxResults(1000)
				order(filter)
				 
			  }
			 
			 /* def id1= result.collect{ ids -> return [id:ids.id1.id.toString()]}
			 def id2= result.collect{ ids -> return [id:ids.id2.id.toString()]}
			 def allid = (id1+id2)
			 def allids = allid.unique()
			 println("allids id" +  allids)
			 
			 */
			 
			 def allR =re.collect {ids -> return [id:ids.toString(),label:(Concepts.get(ids).getName()),comNo:(Concepts.get(ids).getNum_compounds()),conTypes:(Concepts.get(ids).concept_types.getName())]}
			 
			 jsonMap.nodes = allR
			 
			 def f2 = res.collect {en ->
				
				 if(re.contains(en.id1.id) && re.contains(en.id2.id))
				 {
				 return [source: en.id1.id.toString(), target: en.id2.id.toString(),id: (en.pval.toString()),db_id: en.id.toString(),thick: (en.intersection),label: (Concepts.get(en.id1.id).getOriginal_id()),rel : en.relation]
			 //	println(Concepts.get(en.id1.id).getOriginal_id())
				 //	return [enid: en.id,id:en.id1.id,name:(Concepts.get(en.id1.id).getName()), comNo: (Concepts.get(en.id1.id).getNum_compounds()),eid: (Concepts.get(en.id1.id).getOriginal_id()),ctypes: en.id1.concept_types.getName(),ctfull: en.id1.concept_types.getFullname(),pval: format.format(en.pval),qval:format.format(en.qval),ins:(en.intersection), odds:format2.format(en.odds),rel : en.relation,flag:"id2"]
				 }
			 }
			 
			 
			
			 println("f2 is "+f2)
			 jsonMap.edges = f2
			 def check = jsonMap as JSON
			 
		 println("check is " +check)
			return check
		 
		
	}
	def formatDouble(BigDecimal no)
	{
		
	}
	
	def createChart(ArrayList enrich)
	{
//		def map = [:]  //1
//		enrich.each {  //2
//			if(map.containsKey(it)) map[it] = map[it] + 1  //3
//			else map[it] = 1;
//		}
		
		
//   return map
		return [:]
	}
	
}