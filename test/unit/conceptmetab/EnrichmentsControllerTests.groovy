package conceptmetab



import org.junit.*
import grails.test.mixin.*

@TestFor(EnrichmentsController)
@Mock(Enrichments)
class EnrichmentsControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/enrichments/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.enrichmentsInstanceList.size() == 0
        assert model.enrichmentsInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.enrichmentsInstance != null
    }

    void testSave() {
        controller.save()

        assert model.enrichmentsInstance != null
        assert view == '/enrichments/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/enrichments/show/1'
        assert controller.flash.message != null
        assert Enrichments.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/enrichments/list'

        populateValidParams(params)
        def enrichments = new Enrichments(params)

        assert enrichments.save() != null

        params.id = enrichments.id

        def model = controller.show()

        assert model.enrichmentsInstance == enrichments
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/enrichments/list'

        populateValidParams(params)
        def enrichments = new Enrichments(params)

        assert enrichments.save() != null

        params.id = enrichments.id

        def model = controller.edit()

        assert model.enrichmentsInstance == enrichments
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/enrichments/list'

        response.reset()

        populateValidParams(params)
        def enrichments = new Enrichments(params)

        assert enrichments.save() != null

        // test invalid parameters in update
        params.id = enrichments.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/enrichments/edit"
        assert model.enrichmentsInstance != null

        enrichments.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/enrichments/show/$enrichments.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        enrichments.clearErrors()

        populateValidParams(params)
        params.id = enrichments.id
        params.version = -1
        controller.update()

        assert view == "/enrichments/edit"
        assert model.enrichmentsInstance != null
        assert model.enrichmentsInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/enrichments/list'

        response.reset()

        populateValidParams(params)
        def enrichments = new Enrichments(params)

        assert enrichments.save() != null
        assert Enrichments.count() == 1

        params.id = enrichments.id

        controller.delete()

        assert Enrichments.count() == 0
        assert Enrichments.get(enrichments.id) == null
        assert response.redirectedUrl == '/enrichments/list'
    }
}
