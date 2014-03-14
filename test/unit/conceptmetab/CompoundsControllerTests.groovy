package conceptmetab



import org.junit.*
import grails.test.mixin.*

@TestFor(CompoundsController)
@Mock(Compounds)
class CompoundsControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/compounds/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.compoundsInstanceList.size() == 0
        assert model.compoundsInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.compoundsInstance != null
    }

    void testSave() {
        controller.save()

        assert model.compoundsInstance != null
        assert view == '/compounds/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/compounds/show/1'
        assert controller.flash.message != null
        assert Compounds.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/compounds/list'

        populateValidParams(params)
        def compounds = new Compounds(params)

        assert compounds.save() != null

        params.id = compounds.id

        def model = controller.show()

        assert model.compoundsInstance == compounds
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/compounds/list'

        populateValidParams(params)
        def compounds = new Compounds(params)

        assert compounds.save() != null

        params.id = compounds.id

        def model = controller.edit()

        assert model.compoundsInstance == compounds
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/compounds/list'

        response.reset()

        populateValidParams(params)
        def compounds = new Compounds(params)

        assert compounds.save() != null

        // test invalid parameters in update
        params.id = compounds.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/compounds/edit"
        assert model.compoundsInstance != null

        compounds.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/compounds/show/$compounds.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        compounds.clearErrors()

        populateValidParams(params)
        params.id = compounds.id
        params.version = -1
        controller.update()

        assert view == "/compounds/edit"
        assert model.compoundsInstance != null
        assert model.compoundsInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/compounds/list'

        response.reset()

        populateValidParams(params)
        def compounds = new Compounds(params)

        assert compounds.save() != null
        assert Compounds.count() == 1

        params.id = compounds.id

        controller.delete()

        assert Compounds.count() == 0
        assert Compounds.get(compounds.id) == null
        assert response.redirectedUrl == '/compounds/list'
    }
}
