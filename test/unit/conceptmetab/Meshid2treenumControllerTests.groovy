package conceptmetab



import org.junit.*
import grails.test.mixin.*

@TestFor(Meshid2treenumController)
@Mock(Meshid2treenum)
class Meshid2treenumControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/meshid2treenum/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.meshid2treenumInstanceList.size() == 0
        assert model.meshid2treenumInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.meshid2treenumInstance != null
    }

    void testSave() {
        controller.save()

        assert model.meshid2treenumInstance != null
        assert view == '/meshid2treenum/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/meshid2treenum/show/1'
        assert controller.flash.message != null
        assert Meshid2treenum.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/meshid2treenum/list'

        populateValidParams(params)
        def meshid2treenum = new Meshid2treenum(params)

        assert meshid2treenum.save() != null

        params.id = meshid2treenum.id

        def model = controller.show()

        assert model.meshid2treenumInstance == meshid2treenum
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/meshid2treenum/list'

        populateValidParams(params)
        def meshid2treenum = new Meshid2treenum(params)

        assert meshid2treenum.save() != null

        params.id = meshid2treenum.id

        def model = controller.edit()

        assert model.meshid2treenumInstance == meshid2treenum
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/meshid2treenum/list'

        response.reset()

        populateValidParams(params)
        def meshid2treenum = new Meshid2treenum(params)

        assert meshid2treenum.save() != null

        // test invalid parameters in update
        params.id = meshid2treenum.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/meshid2treenum/edit"
        assert model.meshid2treenumInstance != null

        meshid2treenum.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/meshid2treenum/show/$meshid2treenum.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        meshid2treenum.clearErrors()

        populateValidParams(params)
        params.id = meshid2treenum.id
        params.version = -1
        controller.update()

        assert view == "/meshid2treenum/edit"
        assert model.meshid2treenumInstance != null
        assert model.meshid2treenumInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/meshid2treenum/list'

        response.reset()

        populateValidParams(params)
        def meshid2treenum = new Meshid2treenum(params)

        assert meshid2treenum.save() != null
        assert Meshid2treenum.count() == 1

        params.id = meshid2treenum.id

        controller.delete()

        assert Meshid2treenum.count() == 0
        assert Meshid2treenum.get(meshid2treenum.id) == null
        assert response.redirectedUrl == '/meshid2treenum/list'
    }
}
