package conceptmetab

import org.apache.commons.collections.list.LazyList;
import org.apache.commons.collections.FactoryUtils;

class Concepts {
	String original_id 
	String name 
	Integer num_compounds 
	Integer num_enriched 
	 Concept_types concept_types
	
	
	static searchable = true
	
	
	
	
	List ctypes = new ArrayList()
	static hasMany = [compounds_in_concepts:Compounds_in_concepts]
	static hasOne = [ concept_types:Concept_types ]
	
	
	
    static constraints = {
		original_id nullable: false
		name nullable : false
		num_compounds nullable : false
		num_enriched nullable : false		
		 
    }
	
	def getConceptList() {
		return LazyList.decorate(
			  ctypes,
			  FactoryUtils.instantiateFactory(Concept_types.class))
	}
	
	/*String toString(){
		"${original_id}"
	  }*/
	
	
}
