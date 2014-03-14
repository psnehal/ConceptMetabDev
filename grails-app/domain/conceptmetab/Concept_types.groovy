package conceptmetab

class Concept_types {
	
	
	String name
	String fullname
	static hasMany  = [ concepts:Concepts ]
	
	 static searchable = true
	
    static constraints = {
		
		name blank:false	
		
    }
	
	String toString() {return fullname}
}
