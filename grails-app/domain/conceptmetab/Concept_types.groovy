package conceptmetab

class Concept_types {
	
	
	String name
	String fullname
	Integer min
	Integer max
	Integer median
	Integer mean
	String color
	static hasMany  = [ concepts:Concepts ]
	
	 static searchable = true
	
    static constraints = {
		
		name blank:false	
		
    }
	
	String toString() {return fullname}
}
