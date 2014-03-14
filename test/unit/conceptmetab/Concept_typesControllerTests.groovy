package conceptmetab



import org.junit.*
import grails.test.mixin.*

@TestFor(Concept_typesController)
@Mock(Concept_types)
class Concept_typesControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/concept_types/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.concept_typesInstanceList.size() == 0
        assert model.concept_typesInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.concept_typesInstance != null
    }

    void testSave() {
        controller.save()

        assert model.concept_typesInstance != null
        assert view == '/concept_types/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/concept_types/show/1'
        assert controller.flash.message != null
        assert Concept_types.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/concept_types/list'

        populateValidParams(params)
        def concept_types = new Concept_types(params)

        assert concept_types.save() != null

        params.id = concept_types.id

        def model = controller.show()

        assert model.concept_typesInstance == concept_types
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/concept_types/list'

        populateValidParams(params)
        def concept_types = new Concept_types(params)

        assert concept_types.save() != null

        params.id = concept_types.id

        def model = controller.edit()

        assert model.concept_typesInstance == concept_types
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/concept_types/list'

        response.reset()

        populateValidParams(params)
        def concept_types = new Concept_types(params)

        assert concept_types.save() != null

        // test invalid parameters in update
        params.id = concept_types.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/concept_types/edit"
        assert model.concept_typesInstance != null

        concept_types.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/concept_types/show/$concept_types.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        concept_types.clearErrors()

        populateValidParams(params)
        params.id = concept_types.id
        params.version = -1
        controller.update()

        assert view == "/concept_types/edit"
        assert model.concept_typesInstance != null
        assert model.concept_typesInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/concept_types/list'

        response.reset()

        populateValidParams(params)
        def concept_types = new Concept_types(params)

        assert concept_types.save() != null
        assert Concept_types.count() == 1

        params.id = concept_types.id

        controller.delete()

        assert Concept_types.count() == 0
        assert Concept_types.get(concept_types.id) == null
        assert response.redirectedUrl == '/concept_types/list'
    }
}
