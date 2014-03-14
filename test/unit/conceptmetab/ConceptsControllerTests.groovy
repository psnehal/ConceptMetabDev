package conceptmetab



import org.junit.*
import grails.test.mixin.*

@TestFor(ConceptsController)
@Mock(Concepts)
class ConceptsControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/concepts/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.conceptsInstanceList.size() == 0
        assert model.conceptsInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.conceptsInstance != null
    }

    void testSave() {
        controller.save()

        assert model.conceptsInstance != null
        assert view == '/concepts/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/concepts/show/1'
        assert controller.flash.message != null
        assert Concepts.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/concepts/list'

        populateValidParams(params)
        def concepts = new Concepts(params)

        assert concepts.save() != null

        params.id = concepts.id

        def model = controller.show()

        assert model.conceptsInstance == concepts
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/concepts/list'

        populateValidParams(params)
        def concepts = new Concepts(params)

        assert concepts.save() != null

        params.id = concepts.id

        def model = controller.edit()

        assert model.conceptsInstance == concepts
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/concepts/list'

        response.reset()

        populateValidParams(params)
        def concepts = new Concepts(params)

        assert concepts.save() != null

        // test invalid parameters in update
        params.id = concepts.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/concepts/edit"
        assert model.conceptsInstance != null

        concepts.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/concepts/show/$concepts.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        concepts.clearErrors()

        populateValidParams(params)
        params.id = concepts.id
        params.version = -1
        controller.update()

        assert view == "/concepts/edit"
        assert model.conceptsInstance != null
        assert model.conceptsInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/concepts/list'

        response.reset()

        populateValidParams(params)
        def concepts = new Concepts(params)

        assert concepts.save() != null
        assert Concepts.count() == 1

        params.id = concepts.id

        controller.delete()

        assert Concepts.count() == 0
        assert Concepts.get(concepts.id) == null
        assert response.redirectedUrl == '/concepts/list'
    }
}
