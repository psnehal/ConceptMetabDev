package conceptmetab



import org.junit.*
import grails.test.mixin.*

@TestFor(Compounds_in_conceptsController)
@Mock(Compounds_in_concepts)
class Compounds_in_conceptsControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/compounds_in_concepts/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.compounds_in_conceptsInstanceList.size() == 0
        assert model.compounds_in_conceptsInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.compounds_in_conceptsInstance != null
    }

    void testSave() {
        controller.save()

        assert model.compounds_in_conceptsInstance != null
        assert view == '/compounds_in_concepts/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/compounds_in_concepts/show/1'
        assert controller.flash.message != null
        assert Compounds_in_concepts.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/compounds_in_concepts/list'

        populateValidParams(params)
        def compounds_in_concepts = new Compounds_in_concepts(params)

        assert compounds_in_concepts.save() != null

        params.id = compounds_in_concepts.id

        def model = controller.show()

        assert model.compounds_in_conceptsInstance == compounds_in_concepts
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/compounds_in_concepts/list'

        populateValidParams(params)
        def compounds_in_concepts = new Compounds_in_concepts(params)

        assert compounds_in_concepts.save() != null

        params.id = compounds_in_concepts.id

        def model = controller.edit()

        assert model.compounds_in_conceptsInstance == compounds_in_concepts
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/compounds_in_concepts/list'

        response.reset()

        populateValidParams(params)
        def compounds_in_concepts = new Compounds_in_concepts(params)

        assert compounds_in_concepts.save() != null

        // test invalid parameters in update
        params.id = compounds_in_concepts.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/compounds_in_concepts/edit"
        assert model.compounds_in_conceptsInstance != null

        compounds_in_concepts.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/compounds_in_concepts/show/$compounds_in_concepts.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        compounds_in_concepts.clearErrors()

        populateValidParams(params)
        params.id = compounds_in_concepts.id
        params.version = -1
        controller.update()

        assert view == "/compounds_in_concepts/edit"
        assert model.compounds_in_conceptsInstance != null
        assert model.compounds_in_conceptsInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/compounds_in_concepts/list'

        response.reset()

        populateValidParams(params)
        def compounds_in_concepts = new Compounds_in_concepts(params)

        assert compounds_in_concepts.save() != null
        assert Compounds_in_concepts.count() == 1

        params.id = compounds_in_concepts.id

        controller.delete()

        assert Compounds_in_concepts.count() == 0
        assert Compounds_in_concepts.get(compounds_in_concepts.id) == null
        assert response.redirectedUrl == '/compounds_in_concepts/list'
    }
}
