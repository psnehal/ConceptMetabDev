package conceptmetab



import org.junit.*
import grails.test.mixin.*

@TestFor(TestEnrichController)
@Mock(TestEnrich)
class TestEnrichControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/testEnrich/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.testEnrichInstanceList.size() == 0
        assert model.testEnrichInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.testEnrichInstance != null
    }

    void testSave() {
        controller.save()

        assert model.testEnrichInstance != null
        assert view == '/testEnrich/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/testEnrich/show/1'
        assert controller.flash.message != null
        assert TestEnrich.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/testEnrich/list'

        populateValidParams(params)
        def testEnrich = new TestEnrich(params)

        assert testEnrich.save() != null

        params.id = testEnrich.id

        def model = controller.show()

        assert model.testEnrichInstance == testEnrich
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/testEnrich/list'

        populateValidParams(params)
        def testEnrich = new TestEnrich(params)

        assert testEnrich.save() != null

        params.id = testEnrich.id

        def model = controller.edit()

        assert model.testEnrichInstance == testEnrich
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/testEnrich/list'

        response.reset()

        populateValidParams(params)
        def testEnrich = new TestEnrich(params)

        assert testEnrich.save() != null

        // test invalid parameters in update
        params.id = testEnrich.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/testEnrich/edit"
        assert model.testEnrichInstance != null

        testEnrich.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/testEnrich/show/$testEnrich.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        testEnrich.clearErrors()

        populateValidParams(params)
        params.id = testEnrich.id
        params.version = -1
        controller.update()

        assert view == "/testEnrich/edit"
        assert model.testEnrichInstance != null
        assert model.testEnrichInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/testEnrich/list'

        response.reset()

        populateValidParams(params)
        def testEnrich = new TestEnrich(params)

        assert testEnrich.save() != null
        assert TestEnrich.count() == 1

        params.id = testEnrich.id

        controller.delete()

        assert TestEnrich.count() == 0
        assert TestEnrich.get(testEnrich.id) == null
        assert response.redirectedUrl == '/testEnrich/list'
    }
}
