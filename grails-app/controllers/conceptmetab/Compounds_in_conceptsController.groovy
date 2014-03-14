package conceptmetab

import grails.converters.JSON
import org.compass.needle.gigaspaces.service.SearchResults;
import org.springframework.dao.DataIntegrityViolationException

class Compounds_in_conceptsController {
	def exportService
	
	def beforeInterceptor =
	[action:this.&auth, except:["index", "list", "show", "atom", "search","create","findComp"]]


    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }
	
	def findComp(){
		
		
		def con = params.q.toLong()
		def concept = Concepts.get(con)
		
		println(concept.concept_types)
		
		def ccl =Compounds_in_concepts.createCriteria()
		
		def result= ccl.list {
			
			eq('concept.id',con)
			
			
		}
		
		def id1= result.collect{ ids -> return ids.compound.id}
		
		
		def comp = Compounds.createCriteria()
			
		def res = comp.list {
			
			'in'("internal_id",id1)	
		}
		
	
		def download= res.collect{ids ->
							
								return [pubid:ids.pubchem_id,name:ids.name, keid : ids.kegg_id, id : ids.internal_id]
							
		}		
		
		def colName
		if(concept.concept_types.fullname.contains("MeSH"))
		{
			 colName = "Pubchem"
		}
		else
		{
			colName = "KEGG"
		}
		
		println("Download is " + download)
		 
		def map = [:]
		String names
		download.each {
			
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
		
			
		

	
	//println("Size of the map"+download.class+ "    " + download)
	
	
	def fields = ["pubid", "name", "keid"]
	def labels = ["pubid": "Pubchem_id", "name": "name","keid" :"kegg_id"]
	
	if(params?.format && params.format != "html")
	{ 
		response.contentType = grailsApplication.config.grails.mime.types[params.format] 
		response.setHeader("Content-disposition", "attachment; filename=Compounds_in_concepts.${params.extension}")		
		exportService.export(params.format, response.outputStream,download,fields,labels, [:], [:])
	}
		
		
	//def searchResults = Compounds_in_concepts.executeQuery("select c.compounds_id from Compounds_in_concepts.name c  where concepts_id =1")	
	//println(SearchResults)
	//def json = JSON.parse(Item.markings)
	println("map from the findcomp is"+map.values().keid.join(","))
		return [download:download,map:map,colName:colName]
	}


    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [compounds_in_conceptsInstanceList: Compounds_in_concepts.list(params), compounds_in_conceptsInstanceTotal: Compounds_in_concepts.count()]
    }

    def create() {
        [compounds_in_conceptsInstance: new Compounds_in_concepts(params)]
    }

    def save() {
        def compounds_in_conceptsInstance = new Compounds_in_concepts(params)
        if (!compounds_in_conceptsInstance.save(flush: true)) {
            render(view: "create", model: [compounds_in_conceptsInstance: compounds_in_conceptsInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'compounds_in_concepts.label', default: 'Compounds_in_concepts'), compounds_in_conceptsInstance.id])
        redirect(action: "show", id: compounds_in_conceptsInstance.id)
    }

    def show(Long id) {
        def compounds_in_conceptsInstance = Compounds_in_concepts.get(id)
        if (!compounds_in_conceptsInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'compounds_in_concepts.label', default: 'Compounds_in_concepts'), id])
            redirect(action: "list")
            return
        }

        [compounds_in_conceptsInstance: compounds_in_conceptsInstance]
    }

    def edit(Long id) {
        def compounds_in_conceptsInstance = Compounds_in_concepts.get(id)
        if (!compounds_in_conceptsInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'compounds_in_concepts.label', default: 'Compounds_in_concepts'), id])
            redirect(action: "list")
            return
        }

        [compounds_in_conceptsInstance: compounds_in_conceptsInstance]
    }

    def update(Long id, Long version) {
        def compounds_in_conceptsInstance = Compounds_in_concepts.get(id)
        if (!compounds_in_conceptsInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'compounds_in_concepts.label', default: 'Compounds_in_concepts'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (compounds_in_conceptsInstance.version > version) {
                compounds_in_conceptsInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'compounds_in_concepts.label', default: 'Compounds_in_concepts')] as Object[],
                          "Another user has updated this Compounds_in_concepts while you were editing")
                render(view: "edit", model: [compounds_in_conceptsInstance: compounds_in_conceptsInstance])
                return
            }
        }

        compounds_in_conceptsInstance.properties = params

        if (!compounds_in_conceptsInstance.save(flush: true)) {
            render(view: "edit", model: [compounds_in_conceptsInstance: compounds_in_conceptsInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'compounds_in_concepts.label', default: 'Compounds_in_concepts'), compounds_in_conceptsInstance.id])
        redirect(action: "show", id: compounds_in_conceptsInstance.id)
    }

    def delete(Long id) {
        def compounds_in_conceptsInstance = Compounds_in_concepts.get(id)
        if (!compounds_in_conceptsInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'compounds_in_concepts.label', default: 'Compounds_in_concepts'), id])
            redirect(action: "list")
            return
        }

        try {
            compounds_in_conceptsInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'compounds_in_concepts.label', default: 'Compounds_in_concepts'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'compounds_in_concepts.label', default: 'Compounds_in_concepts'), id])
            redirect(action: "show", id: id)
        }
    }
}
