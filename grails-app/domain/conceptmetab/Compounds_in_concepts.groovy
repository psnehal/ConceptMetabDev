package conceptmetab

class Compounds_in_concepts {
	
	
	
	
	
	
	static belongsTo = [concept:Concepts, compound:Compounds]
    static constraints = {
    }
	static mapping = {
		
		table 'compounds_in_concepts'
	    compounds joinTable:[name:'compounds', key:'internal_id', column:'internal_id']
		
	}
}
