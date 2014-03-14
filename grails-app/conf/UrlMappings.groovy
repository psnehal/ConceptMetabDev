class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

		"/" ( controller:'Concepts', action:'main')
		"500"(view:'/error')
	}
}
