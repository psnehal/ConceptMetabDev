package conceptmetab

import org.springframework.dao.DataIntegrityViolationException

class Meshid2treenumController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [meshid2treenumInstanceList: Meshid2treenum.list(params), meshid2treenumInstanceTotal: Meshid2treenum.count()]
    }

    def create() {
        [meshid2treenumInstance: new Meshid2treenum(params)]
    }

    def save() {
        def meshid2treenumInstance = new Meshid2treenum(params)
        if (!meshid2treenumInstance.save(flush: true)) {
            render(view: "create", model: [meshid2treenumInstance: meshid2treenumInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'meshid2treenum.label', default: 'Meshid2treenum'), meshid2treenumInstance.id])
        redirect(action: "show", id: meshid2treenumInstance.id)
    }

    def show(Long id) {
        def meshid2treenumInstance = Meshid2treenum.get(id)
        if (!meshid2treenumInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'meshid2treenum.label', default: 'Meshid2treenum'), id])
            redirect(action: "list")
            return
        }

        [meshid2treenumInstance: meshid2treenumInstance]
    }

    def edit(Long id) {
        def meshid2treenumInstance = Meshid2treenum.get(id)
        if (!meshid2treenumInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'meshid2treenum.label', default: 'Meshid2treenum'), id])
            redirect(action: "list")
            return
        }

        [meshid2treenumInstance: meshid2treenumInstance]
    }

    def update(Long id, Long version) {
        def meshid2treenumInstance = Meshid2treenum.get(id)
        if (!meshid2treenumInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'meshid2treenum.label', default: 'Meshid2treenum'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (meshid2treenumInstance.version > version) {
                meshid2treenumInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'meshid2treenum.label', default: 'Meshid2treenum')] as Object[],
                          "Another user has updated this Meshid2treenum while you were editing")
                render(view: "edit", model: [meshid2treenumInstance: meshid2treenumInstance])
                return
            }
        }

        meshid2treenumInstance.properties = params

        if (!meshid2treenumInstance.save(flush: true)) {
            render(view: "edit", model: [meshid2treenumInstance: meshid2treenumInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'meshid2treenum.label', default: 'Meshid2treenum'), meshid2treenumInstance.id])
        redirect(action: "show", id: meshid2treenumInstance.id)
    }

    def delete(Long id) {
        def meshid2treenumInstance = Meshid2treenum.get(id)
        if (!meshid2treenumInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'meshid2treenum.label', default: 'Meshid2treenum'), id])
            redirect(action: "list")
            return
        }

        try {
            meshid2treenumInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'meshid2treenum.label', default: 'Meshid2treenum'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'meshid2treenum.label', default: 'Meshid2treenum'), id])
            redirect(action: "show", id: id)
        }
    }
}
