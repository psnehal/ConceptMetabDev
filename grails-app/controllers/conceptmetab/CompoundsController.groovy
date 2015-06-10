package conceptmetab

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON

class CompoundsController {
	
	def exportService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
	
	def beforeInterceptor =
	[action:this.&auth, except:["index", "list", "show", "atom", "search","create","conceptCompleteNetwork","displayMsg","displayEdge"]]

 def search = {
  // render Compounds.search(params.q, params)
   def searchResults = Compounds.search(params.q, params)
   println(params.q)
   flash.message = "${searchResults.total} results found for search: ${params.q}"
   flash.q = params.q
   println(searchResults.total)
   return [searchResults:searchResults.results, resultCount:searchResults.total]
 }

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [compoundsInstanceList: Compounds.list(params), compoundsInstanceTotal: Compounds.count()]
    }

    def create() {
        [compoundsInstance: new Compounds(params)]
    }

    def save() {
        def compoundsInstance = new Compounds(params)
        if (!compoundsInstance.save(flush: true)) {
            render(view: "create", model: [compoundsInstance: compoundsInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'compounds.label', default: 'Compounds'), compoundsInstance.id])
        redirect(action: "show", id: compoundsInstance.id)
    }

    def show(Long id) {
		
		
		println(params)
        def compoundsInstance = Compounds.get(id)
		def conceptsInstance
		def message = ""
		
        if (!compoundsInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'compounds.label', default: 'Compounds'), id])
            redirect(action: "list")
            return
        }
		
	
		
		
		def ccl =Compounds_in_concepts.createCriteria()		
		def result= ccl.list {			
			eq('compound.id',compoundsInstance.internal_id)			
		}
		
		//println("result size fromCompounds_in_concepts"+result.size());		
		if(result.size() == 0)
		{
			
			conceptsInstance = null
			
		}
		else
		{			
			def concept = result.collect { ids -> return (ids.concept.id)}
			//println(concept)
			def conRes= Concepts.createCriteria()
			 conceptsInstance = conRes.list {'in' ('id', concept) }
			//println( "No of concepts compounds belong to"+ conceptsInstance)
			
		}
		
		def conMod=conceptsInstance.collect {ids ->
		
			if(ids.concept_types.fullname.toString().contains("MeSH")){								 
				
				def meshid2treenumInstance = Meshid2treenum.findAllWhere(mesh_id : ids.original_id)
				
				if(meshid2treenumInstance.size() !=0 )
				{
					return[ id:ids.id, name:ids.name,oriid:ids.original_id, conid: meshid2treenumInstance.get(0).tree_id, numCom: ids.num_compounds, numEnc: ids.num_enriched, conTyp: ids.concept_types.fullname]
				}	
				else
				{
					return[ id:ids.id, name:ids.name,oriid:ids.original_id, conid: ids.original_id, numCom: ids.num_compounds, numEnc: ids.num_enriched, conTyp: ids.concept_types.fullname]
				}	
		}
			else
			{
				return[ id:ids.id, name:ids.name,oriid:ids.original_id, conid: ids.original_id, numCom: ids.num_compounds, numEnc: ids.num_enriched, conTyp: ids.concept_types.fullname]
			}
			
		}
		
		def fields = ["name", "conid", "numCom","numEnc","conTyp"]
		def labels = ["name": "Concept name","conid": "Concept ID", "numCom" :"Concept Size","numEnc" :"# of Enrichments","conTyp": "Concept Types"]
		
		
		if(params?.format && params.format != "html"){ 
			
			response.contentType = grailsApplication.config.grails.mime.types[params.format] 
			response.setHeader("Content-disposition", "attachment; filename=Concepts.${params.extension}")			
			exportService.export(params.format, response.outputStream,conMod,fields,labels, [:], [:])
		}
		
		//println(conMod.oriid)
		def keggid = compoundsInstance.kegg_id.split(";")
			//println(keggid.getClass())
		def urlKegg =""	
		for(int i=0; i < keggid.size(); i++)
		{
			urlKegg= urlKegg+'<a href= "http://www.kegg.jp/dbget-bin/www_bget?cpd:'+keggid.getAt(i)+'" target="_blank">'+keggid.getAt(i)+'</a>; '
		}
	
			def pubchemid = compoundsInstance.pubchem_id.split(";")
			println("****************************************"+pubchemid)
		def urlPubchem =""
		for(int i=0; i < pubchemid.size(); i++)
		{
			urlPubchem= urlPubchem+'<a href= "http://pubchem.ncbi.nlm.nih.gov/summary/summary.cgi?cid='+pubchemid.getAt(i)+'" target="_blank">'+pubchemid.getAt(i)+'</a>; '
		}
		//println(urlKegg)
        [compoundsInstance: compoundsInstance,conceptsInstance: conMod.sort{a,b -> a.conTyp <=> b.conTyp ?: a.name <=> b.name},urlKegg:urlKegg,urlPubchem:urlPubchem ]
    }
	
	def conceptCompleteNetwork()
	{
		print "*********************************************"
		print params
		def fil= 'qval'
		
		
		print "1"
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
		 print db;
		 
		 List re =[]
		 List conceptList =[]
		 def res
		 for (int i = 0; i<db.size(); i++)
		 {
			 re.add(db.get(i).toLong())
			 def conceptInstance =Concepts.get(db.get(i));
			 conceptList.add(conceptInstance)
				 
			 
		 }
		 
		 def allR = conceptList.collect{ids -> return [id:ids.id.toString(),label:ids.name,comNo:ids.num_compounds,conTypes:ids.concept_types.fullname]}
		 print(allR)
		 
		def enrichL =Enrichments.createCriteria()
		res= enrichL.list {
			  and  {
				  'in'('id1.id',re)
				  'in'('id2.id',re)
			  }
			
			  order('qval')
		  }
		
		print (res.size())		
		def f2 = res.collect {en ->		
			 return [source: en.id1.id.toString(), target: en.id2.id.toString(),id: (en.pval.toString()),db_id: en.id.toString(),thick: (en.intersection),label: (Concepts.get(en.id1.id).getOriginal_id()),rel : en.relation]
		 //	println(Concepts.get(en.id1.id).getOriginal_id())
			 //	return [enid: en.id,id:en.id1.id,name:(Concepts.get(en.id1.id).getName()), comNo: (Concepts.get(en.id1.id).getNum_compounds()),eid: (Concepts.get(en.id1.id).getOriginal_id()),ctypes: en.id1.concept_types.getName(),ctfull: en.id1.concept_types.getFullname(),pval: format.format(en.pval),qval:format.format(en.qval),ins:(en.intersection), odds:format2.format(en.odds),rel : en.relation,flag:"id2"]
		}		
		print(f2)
		HashMap jsonMap = new HashMap()
		jsonMap.nodes = allR
		jsonMap.edges = f2
		def check = jsonMap as JSON
		  print jsonMap
		  
		  def compoundsInstance = Compounds.get(params.compoundsId)
		print ("compoundsInstance"+ compoundsInstance)
		
		[check:check,compoundsInstance:compoundsInstance,db:db,resultcount:check.toString().length(),fil:fil]
		
	}
	
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
				//conceptsInstance.original_id = meshid2treenumInstance.tree_id
				 
			}
			
		
			println("got the instance" +  conceptsInstance.original_id)
		[conceptsInstance: conceptsInstance]
		
	}
//*****************************************************************DisplayEdge**************************************************************************
	
	def displayEdge()
	{
		def msg  = params.q;
		def con = params.con
		println(params)
		
		def enrichInstance = Enrichments.get(msg.toLong())
		
		//println(enrichInstance.id1.compounds_in_concepts.compound.id)
		List id1_com=enrichInstance.id1.compounds_in_concepts.compound.id
		List id2_com=enrichInstance.id2.compounds_in_concepts.compound.id
		
		
		List inter = id1_com.intersect(id2_com)
		//println("intesection"+inter)
		
		def comp = Compounds.createCriteria()
		
		def res = comp.list {
			
			'in'("internal_id",inter)
		}
	
		//println(res)
		
		def comIns = res.collect { ids ->
								return [pubid:ids.pubchem_id,name:ids.name, keid : ids.kegg_id, id : ids.internal_id]
				}
		
		def map = [:]
		String names
		comIns.each {
			def urlKegg =""
			def urlPubchem =""
			names = it.name
			def keggid = it.keid.split(";")
			//names = '<g:link controller="Compounds" action="show" id="'+it.id+'"><b>'+it.name+'</b></g:link>'
			for(int i=0; i < keggid.size(); i++)
			{
				urlKegg= urlKegg+'<a href= "http://www.kegg.jp/dbget-bin/www_bget?cpd:'+keggid.getAt(i)+'" target="_blank">'+keggid.getAt(i)+'</a>; '
			}
			def pubchemid = it.pubid.split(";")
		   for(int i=0; i < pubchemid.size(); i++)
		   {
			   urlPubchem= urlPubchem+'<a href= "http://pubchem.ncbi.nlm.nih.gov/summary/summary.cgi?cid='+pubchemid.getAt(i)+'" target="_blank">'+pubchemid.getAt(i)+'</a>; '
		   }
		   def value = [:]
		   value.put('names', names)
		   value.put('pubid', it.pubid)
		   value.put('keid', it.keid)
		   value.put('keidurl', urlKegg)
		   value.put('pubidurl', urlPubchem)
		   value.put('cid', it.id)
			   
			   map.put(it.id, value)
	   }
		
		//BigDecimal pval = intersection.pval
		
		
		//println ("comins"+comIns)
		[enrichInstance:enrichInstance,map:map]
		
	}
	
	
	

    def edit(Long id) {
        def compoundsInstance = Compounds.get(id)
        if (!compoundsInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'compounds.label', default: 'Compounds'), id])
            redirect(action: "list")
            return
        }

        [compoundsInstance: compoundsInstance]
    }

    def update(Long id, Long version) {
        def compoundsInstance = Compounds.get(id)
        if (!compoundsInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'compounds.label', default: 'Compounds'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (compoundsInstance.version > version) {
                compoundsInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'compounds.label', default: 'Compounds')] as Object[],
                          "Another user has updated this Compounds while you were editing")
                render(view: "edit", model: [compoundsInstance: compoundsInstance])
                return
            }
        }

        compoundsInstance.properties = params

        if (!compoundsInstance.save(flush: true)) {
            render(view: "edit", model: [compoundsInstance: compoundsInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'compounds.label', default: 'Compounds'), compoundsInstance.id])
        redirect(action: "show", id: compoundsInstance.id)
    }

    def delete(Long id) {
        def compoundsInstance = Compounds.get(id)
        if (!compoundsInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'compounds.label', default: 'Compounds'), id])
            redirect(action: "list")
            return
        }

        try {
            compoundsInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'compounds.label', default: 'Compounds'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'compounds.label', default: 'Compounds'), id])
            redirect(action: "show", id: id)
        }
    }
}
