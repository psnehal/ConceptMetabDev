package conceptmetab

import org.springframework.dao.DataIntegrityViolationException

class CompoundsController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
	
	def beforeInterceptor =
	[action:this.&auth, except:["index", "list", "show", "atom", "search","create"]]

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
		
		println("result size fromCompounds_in_concepts"+result.size());		
		if(result.size() == 0)
		{
			
			conceptsInstance = null
			
		}
		else
		{			
			def concept = result.collect { ids -> return (ids.concept.id)}
			println(concept)
			def conRes= Concepts.createCriteria()
			 conceptsInstance = conRes.list {'in' ('id', concept) }
			println( "No of concepts compounds belong to"+ conceptsInstance.size())
			
		}
        [compoundsInstance: compoundsInstance,conceptsInstance: conceptsInstance ]
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
