package conceptmetab

import org.springframework.dao.DataIntegrityViolationException

class Concept_typesController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
		
		
		def result = Concepts.list()
		
		println("list of interactions == "+result.size())
		def id1= result.collect{ ids -> return(Concepts.get(ids.id).concept_types.getName())}
		
		
	  
		def map = [:]  //1
		id1.each {  //2
			if(map.containsKey(it)) map[it] = map[it] + 1  //3
			else map[it] = 1;
		}
		   
		
	
		
		

        [map:map]
    }

    def create() {
        [concept_typesInstance: new Concept_types(params)]
    }

    def save() {
        def concept_typesInstance = new Concept_types(params)
        if (!concept_typesInstance.save(flush: true)) {
            render(view: "create", model: [concept_typesInstance: concept_typesInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'concept_types.label', default: 'Concept_types'), concept_typesInstance.id])
        redirect(action: "show", id: concept_typesInstance.id)
    }

    def show(Long id) {
        def concept_typesInstance = Concept_types.get(id)
        if (!concept_typesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'concept_types.label', default: 'Concept_types'), id])
            redirect(action: "list")
            return
        }

        [concept_typesInstance: concept_typesInstance]
    }

    def edit(Long id) {
        def concept_typesInstance = Concept_types.get(id)
        if (!concept_typesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'concept_types.label', default: 'Concept_types'), id])
            redirect(action: "list")
            return
        }

        [concept_typesInstance: concept_typesInstance]
    }

    def update(Long id, Long version) {
        def concept_typesInstance = Concept_types.get(id)
        if (!concept_typesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'concept_types.label', default: 'Concept_types'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (concept_typesInstance.version > version) {
                concept_typesInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'concept_types.label', default: 'Concept_types')] as Object[],
                          "Another user has updated this Concept_types while you were editing")
                render(view: "edit", model: [concept_typesInstance: concept_typesInstance])
                return
            }
        }

        concept_typesInstance.properties = params

        if (!concept_typesInstance.save(flush: true)) {
            render(view: "edit", model: [concept_typesInstance: concept_typesInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'concept_types.label', default: 'Concept_types'), concept_typesInstance.id])
        redirect(action: "show", id: concept_typesInstance.id)
    }

    def delete(Long id) {
        def concept_typesInstance = Concept_types.get(id)
        if (!concept_typesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'concept_types.label', default: 'Concept_types'), id])
            redirect(action: "list")
            return
        }

        try {
            concept_typesInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'concept_types.label', default: 'Concept_types'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'concept_types.label', default: 'Concept_types'), id])
            redirect(action: "show", id: id)
        }
    }
}
