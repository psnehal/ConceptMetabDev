package conceptmetab

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException

class ConceptsController {
	
	
	def beforeInterceptor =
	[action:this.&auth, except:["main2","index", "list", "show", "create","atom","getName", "search","opt","ajaxFindCity","checkQ","main","intro","dbspecific"]]

	 def search = {
		  //render Entry.search(params.q, params)
		// render Concepts.search(params.q, params)
			 println(params)
			 Integer offset = params.int("offset")?:0
			 Integer max2 = Math.min(params.int("max") ?: 50, 1000)
			// def searchResults = Concepts.search(params.q, params)
			 def filter = params.fil
			 def searchResults
			 def resultCount
			 def b = Concepts.createCriteria()
			 def b2 = Concepts.createCriteria()
			 def c = Compounds.createCriteria()
			 def c2 = Compounds.createCriteria()
			 if(filter.equals("concept")){
						 searchResults=  b.list (max: max2, offset: offset) {
				             ilike 'name', params.q + '%'
							 order("name", "asc")
						 	}
						 
						 def forcount =  b2 {
				             ilike 'name', params.q + '%'
						 	}
						 resultCount= forcount.size()
			 }
			 
			 else
			 {
				 searchResults=  c.list (max: max2, offset: offset) {
					 ilike 'name', params.q + '%'
					 order("name", "asc")
					 }
				 
				  def forcount=  c2 {
					 ilike 'name', params.q + '%'
					 }
				 
				 resultCount= forcount.size()
			 }
	         flash.message = "${resultCount} results found for search: ${params.q}"
	         flash.q = params.q
	         println("resultCount"+resultCount)
	         return [searchResults:searchResults, resultCount:resultCount, filter:filter]
	}

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
	def dbspecific (Integer max) {
		

		 Integer offset = params.int("offset")?:0
		 Integer max2 = Math.min(params.int("max") ?: 500, 1000)
		 
		 println(params);
		 println("offset" + offset)
		//List<Concepts> c = new ArrayList<Concepts>()
		//List<Concepts> b = new ArrayList<Concepts>()	
	
		def b = Concepts.createCriteria()
		def result
				 
			println("Else lopop");
			result = b.list (max: max2, offset: offset)
			{ 
				concept_types
			   {
				   eq('fullname' , params.name)
			   }
			  
		   }
		   
			
			def c = Concepts.createCriteria()
			
	   println("Else lopop");
	   def result2 = c
	   {
		   concept_types
		  {
			  eq('fullname' , params.name)
		  }
		 
	  }
			
		
		println("result is "+ result2.size())
		
	
		
		
	[result:result, resultcount :  result2.size()]
	}
	
	def main2 = {
		
		def result = Concepts.list()
		
		println("list of interactions == "+result.size())
		def id1= result.collect{ ids -> return(Concepts.get(ids.id).concept_types.getFullname())}
		
		
	  
		def map = [:]  //1
		id1.each {  //2
			if(map.containsKey(it)) map[it] = map[it] + 1  //3
			else map[it] = 1;
		}
		   
		if (params.containsKey("sort"))
		{
			map = map.sort {it.value}
			
		}
	
	
		
	

		[map:map]
		
	}
	
	def intro = {
	
	
}
	
	def main ={
		
		def conL =Concepts.createCriteria()
		
		def result= conL.list {
			projections{
			count("id")			
			groupProperty("concept_types")
			}
			
		}
		
		println(result.get(0) )
		def id1= result.collect{ ids -> return [count:ids.getAt(0),ctypes:ids.getAt(1)]}
		
		 [ id1:id1]
		
	}
	def checkQ =
	{
		println(params)
		def con = params.q.toLong();
			println("parameter is"+con)
			HashMap jsonMap = new HashMap()
			List<Concepts> b = new ArrayList<Concepts>()
		b = Concepts.executeQuery("select c from conceptmetab.Concepts c  where c.id = (select c from conceptmetab.Concept_types ce where ce.id = 1 )")
		render b
	}

	def getName = {
		def searchResults = Concept_types.search(params.q, params)
		println(Concept_types.get(1))
		println(params)
		def cI = Concept_types.get(1)
		//println(Concept_types.get(params))
		//return [cName :Concept_types.get(1) ]
		render(view: "display",cName :Concept_types.get(1))
	}
	
    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 30, 100)
		println(Concept_types.count())
		println(Concept_types.get(1))
		println(params.id)
		
        [conceptsInstanceList: Concepts.list(params), conceptsInstanceTotal: Concepts.count()]
    }

	def opt()
	{
		//List<Concepts> b = new ArrayList<Concepts>()
		//b = Concepts.executeQuery("SELECT c.name from conceptmetab.Concepts c").toArray()
		//[ b:b]
		
		def ajaxConFinder = {
			def conFound = Concepts.withCriteria {
				  ilike 'con', params.term + '%'
				 }.sort()
		  render (conFound as JSON)
		 }
		
	}
	
	def ajaxFindCity = {
						println("From ajax find city and params are"+params)
					   log.debug "Find city:${params.term}"
					   
					  def filter = params.radio.toString()
					  
					  if(filter.equals("concept"))
					  {
						  def foundCities = Concepts.withCriteria {
							  ilike 'name', params.term + '%'
							  order("name", "asc")	 }

						  render (foundCities?.'name' as JSON)
					  }
					  else
					  {
						  def foundComp = Compounds.withCriteria {
							  ilike 'name', params.term + '%'
							  order("name", "asc")	 }

						  render (foundComp?.'name' as JSON)
					  }
	
					
	
			}
	
    def create() {
        [conceptsInstance: new Concepts(params)]
    }

    def save() {
        def conceptsInstance = new Concepts(params)
        if (!conceptsInstance.save(flush: true)) {
            render(view: "create", model: [conceptsInstance: conceptsInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'concepts.label', default: 'Concepts'), conceptsInstance.id])
        redirect(action: "show", id: conceptsInstance.id)
    }

    def show(Long id) {
		
		def conceptsInstance = Concepts.get(id)
		String url
		def width = "676px"
		println ("Concept type is " + conceptsInstance.concept_types.fullname )
		if (!conceptsInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'concepts.label', default: 'Concepts'), id])
			redirect(action: "list")
			return
		}
		if(conceptsInstance.concept_types.fullname.contains('MeSH'))
		{
			
			def meshid2treenumInstance = Meshid2treenum.findAllWhere(mesh_id : conceptsInstance?.original_id )
			
			
			
			def urlp = "http://www.ncbi.nlm.nih.gov/mesh/?term="+meshid2treenumInstance.get(0).tree_id
			println ("found mesh term")
			 width = "500px"
			 
			 
			 println("meshid2treenumInstance tree id is" + meshid2treenumInstance.get(0).tree_id)
			 
			 url = '<a href="'+urlp+'" target="_blank">'+'<span class="property-value" aria-labelledby="original_id-label">'+meshid2treenumInstance.tree_id.toString().replace("[", "") .replace("]", "")+'</span></a>'
			 
		}
		else if(conceptsInstance.concept_types.fullname.contains('GO'))
		{
			url = "http://amigo.geneontology.org/cgi-bin/amigo/term_details?term="+conceptsInstance?.original_id
		}
		else if(conceptsInstance.concept_types.fullname.contains('KEGG'))
		{
			url = "http://www.kegg.jp/kegg-bin/show_pathway?"+conceptsInstance?.original_id
		}
		else if(conceptsInstance.concept_types.fullname.contains('Enzyme'))
		{
			url = "http://www.genome.jp/dbget-bin/www_bget?"+conceptsInstance?.original_id
		}
		println("url is "+ url)
		[conceptsInstance: conceptsInstance, url:url,width:width]
        
    }

    def edit(Long id) {
        def conceptsInstance = Concepts.get(id)
        if (!conceptsInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'concepts.label', default: 'Concepts'), id])
            redirect(action: "list")
            return
        }

        [conceptsInstance: conceptsInstance]
    }
	

    def update(Long id, Long version) {
        def conceptsInstance = Concepts.get(id)
        if (!conceptsInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'concepts.label', default: 'Concepts'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (conceptsInstance.version > version) {
                conceptsInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'concepts.label', default: 'Concepts')] as Object[],
                          "Another user has updated this Concepts while you were editing")
                render(view: "edit", model: [conceptsInstance: conceptsInstance])
                return
            }
        }

        conceptsInstance.properties = params

        if (!conceptsInstance.save(flush: true)) {
            render(view: "edit", model: [conceptsInstance: conceptsInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'concepts.label', default: 'Concepts'), conceptsInstance.id])
        redirect(action: "show", id: conceptsInstance.id)
    }

    def delete(Long id) {
        def conceptsInstance = Concepts.get(id)
        if (!conceptsInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'concepts.label', default: 'Concepts'), id])
            redirect(action: "list")
            return
        }

        try {
            conceptsInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'concepts.label', default: 'Concepts'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'concepts.label', default: 'Concepts'), id])
            redirect(action: "show", id: id)
        }
    }
}
