package conceptmetab

import grails.converters.JSON
import groovy.sql.Sql.QueryCommand;

import org.springframework.dao.DataIntegrityViolationException

class TestEnrichController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "createJson", params: params)
    }
	
	def findById() {
		List<TestEnrich> b = new ArrayList<TestEnrich>()
		b = TestEnrich.executeQuery("SELECT e.id2 from conceptmetab.TestEnrich e where e.id1=3460 UNION select e.id1 from testenrich  e where e.id2 =3460")
		println(b.size())
			[b:b]
}
	
	def createJson(){
		def con = params.q;
		println("parameter is"+con)
		
		HashMap jsonMap = new HashMap()
		
		//List<TestEnrich> testEnrichList = TestEnrich.where {(id1 == con.toInteger()) || (id2 == con.toInteger())}.toList()
		List<TestEnrich> b = new ArrayList<Enrichments>()
		List<TestEnrich> id1List = new ArrayList<TestEnrich>()
		List<TestEnrich> id2List = new ArrayList<TestEnrich>()
		
		
		def testEnrichList =TestEnrich.createCriteria()
		def result = testEnrichList.list {				
				or {
				eq("id2",con.toInteger())
				eq("id1",con.toInteger())}
				maxResults(100)
				order("intersection")
				
			}
			println("list of interactions"+result.size())
			
			def id1= result.collect{ ids -> return [id:ids.id1.toString()]}.unique()
			def id2= result.collect{ ids -> return [id:ids.id2.toString()]}.unique()
			def allids = (id1+id2)
			jsonMap.nodes = allids
			
			
			
			

	
	
			
		id1List = TestEnrich.executeQuery("select e.id1 from conceptmetab.TestEnrich  e where e.id2 =:cid ",[cid: con.toInteger()] )
		id2List = TestEnrich.executeQuery("select e.id2 from conceptmetab.TestEnrich  e where e.id1 =:cid ",[cid: con.toInteger()] )
		println("size of id2List only id list"+id2List.size())
		println("size of id1List "+id1List.unique().size())
		List<TestEnrich> idList = new ArrayList<TestEnrich>()
		idList.addAll(id1List)
		idList.addAll(id2List)
		idList.add(con.toInteger())
		println("list of ids:"+idList.size())
		
		
		
		
		//b = TestEnrich.executeQuery("SELECT e.id2 from conceptmetab.TestEnrich e where e.id1= :cid UNION select e.id1 from testenrich  e where e.id2 = :cid",[cid: con.toInteger()] )
		//println(b.add(con));
		//println(b.get(1))
		//println(b.size())
		
		jsonMap.edges = result.collect {en ->
			return [source: en.id1.toString(), target: en.id2.toString(), id: (en.intersection.toString()),label: (Concepts.get(en.id1).getOriginal_id())]
			println(Concepts.get(en.id1).getOriginal_id())
			}
	
		
		def check = jsonMap as JSON
	   [check:check]
	
		
		/*
		List<Enrichments> id1List = new ArrayList<Enrichments>()
		List<Enrichments> id2List = new ArrayList<Enrichments>()
		id1List = TestEnrich.executeQuery("select e.id1 from conceptmetab.TestEnrich  e where e.id2 =3460")
		id2List = TestEnrich.executeQuery("select e.id2 from conceptmetab.TestEnrich  e where e.id1 =3460")
		println("size of id2List only id list"+id2List.size())
		println("size of id1List "+id1List.unique().size())
		List<Enrichments> idList = new ArrayList<Enrichments>()
		idList.addAll(id1List)
		idList.addAll(id2List)
		println(idList.size())
		jsonMap.nodes = idList.collect{ ids -> return [id:ids.toString()]}
		
		
		jsonMap.edges = testEnrichList.collect {en ->
			return [source: en.id1.toString(), target: en.id2.toString(), id: (en.intersection.toString())]
			}
		
		*/
		
		render check
	}
	
	def displayJson(){
		
		def con = params.q;
		println("parameter is"+con)
		
		HashMap jsonMap = new HashMap()
		
		//List<TestEnrich> testEnrichList = TestEnrich.where {(id1 == con.toInteger()) || (id2 == con.toInteger())}.toList()
		List<TestEnrich> b = new ArrayList<Enrichments>()
		List<TestEnrich> id1List = new ArrayList<TestEnrich>()
		List<TestEnrich> id2List = new ArrayList<TestEnrich>()
		
		
		def testEnrichList =TestEnrich.createCriteria()
		def result = testEnrichList.list {				
				or {
				eq("id2",con.toInteger())
				eq("id1",con.toInteger())}
				maxResults(100)
				order("intersection")
				
			}
			println("list of interactions"+result.size())
			
			def id1= result.collect{ ids -> return [id:ids.id1.toString(),label:(Concepts.get(ids.id1).getOriginal_id())]}.unique()
			def id2= result.collect{ ids -> return [id:ids.id2.toString(),label:(Concepts.get(ids.id1).getOriginal_id())]}.unique()
			def allids = (id1+id2)
			jsonMap.nodes = allids
			
			
			
			

	
	
			
		id1List = TestEnrich.executeQuery("select e.id1 from conceptmetab.TestEnrich  e where e.id2 =:cid ",[cid: con.toInteger()] )
		id2List = TestEnrich.executeQuery("select e.id2 from conceptmetab.TestEnrich  e where e.id1 =:cid ",[cid: con.toInteger()] )
		println("size of id2List only id list"+id2List.size())
		println("size of id1List "+id1List.unique().size())
		List<TestEnrich> idList = new ArrayList<TestEnrich>()
		idList.addAll(id1List)
		idList.addAll(id2List)
		idList.add(con.toInteger())
		println("list of ids:"+idList.size())
		
		
		
		
		//b = TestEnrich.executeQuery("SELECT e.id2 from conceptmetab.TestEnrich e where e.id1= :cid UNION select e.id1 from testenrich  e where e.id2 = :cid",[cid: con.toInteger()] )
		//println(b.add(con));
		//println(b.get(1))
		//println(b.size())
		
		println(Concepts.get(con).getOriginal_id())
		jsonMap.edges = result.collect {
			
			en ->
			return [source: en.id1.toString(), target: en.id2.toString(), id: (en.intersection.toString()),label: Concepts.get(en.id1).getOriginal_id()]
			}
	
		
		def check = jsonMap as JSON
	   [check:check]
		//render check
	}
	
	def test(){
		
		List<Enrichments> b = new ArrayList<Enrichments>()
	// (select e.id1 from conceptmetab.Enrichments  e where e.id2 ='3460') UNION (SELECT e.id2 from conceptmetab.Enrichments ewhere  e.id1='3460');	
      //b = TestEnrich.executeQuery("SELECT e.id,e.id1,e.id2,e.intersection, e.pval, e.qval  FROM conceptmetab.Enrichments e WHERE e.id1 = 3460 or e.id2 = 3460 ")
		b = TestEnrich.executeQuery("select e.id1 from conceptmetab.Enrichments  e where e.id2 =3460 UNION SELECT e.id2 from conceptmetab.Enrichments e where  e.id1=3460")
	  println(b.collect().unique())
	  
	}

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [testEnrichInstanceList: TestEnrich.list(params), testEnrichInstanceTotal: TestEnrich.count()]
    }

    def create() {
        [testEnrichInstance: new TestEnrich(params)]
    }

    def save() {
        def testEnrichInstance = new TestEnrich(params)
        if (!testEnrichInstance.save(flush: true)) {
            render(view: "create", model: [testEnrichInstance: testEnrichInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'testEnrich.label', default: 'TestEnrich'), testEnrichInstance.id])
        redirect(action: "show", id: testEnrichInstance.id)
    }

    def show(Long id) {
        def testEnrichInstance = TestEnrich.get(id)
        if (!testEnrichInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'testEnrich.label', default: 'TestEnrich'), id])
            redirect(action: "list")
            return
        }

        [testEnrichInstance: testEnrichInstance]
    }

    def edit(Long id) {
        def testEnrichInstance = TestEnrich.get(id)
        if (!testEnrichInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'testEnrich.label', default: 'TestEnrich'), id])
            redirect(action: "list")
            return
        }

        [testEnrichInstance: testEnrichInstance]
    }

    def update(Long id, Long version) {
        def testEnrichInstance = TestEnrich.get(id)
        if (!testEnrichInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'testEnrich.label', default: 'TestEnrich'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (testEnrichInstance.version > version) {
                testEnrichInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'testEnrich.label', default: 'TestEnrich')] as Object[],
                          "Another user has updated this TestEnrich while you were editing")
                render(view: "edit", model: [testEnrichInstance: testEnrichInstance])
                return
            }
        }

        testEnrichInstance.properties = params

        if (!testEnrichInstance.save(flush: true)) {
            render(view: "edit", model: [testEnrichInstance: testEnrichInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'testEnrich.label', default: 'TestEnrich'), testEnrichInstance.id])
        redirect(action: "show", id: testEnrichInstance.id)
    }

    def delete(Long id) {
        def testEnrichInstance = TestEnrich.get(id)
        if (!testEnrichInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'testEnrich.label', default: 'TestEnrich'), id])
            redirect(action: "list")
            return
        }

        try {
            testEnrichInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'testEnrich.label', default: 'TestEnrich'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'testEnrich.label', default: 'TestEnrich'), id])
            redirect(action: "show", id: id)
        }
    }
}
