package conceptmetab

class Compounds {
	Long internal_id 
	String name 
	String kegg_id
	String pubchem_id
	Long num_concepts
	
	static hasMany = [compounds_in_concepts:Compounds_in_concepts]
	
	static searchable = true
    static constraints = {
		internal_id nullable : false
		name nullable:false
		
		
    }
	String toString(){
		" ${internal_id}"
	  }
	
}
