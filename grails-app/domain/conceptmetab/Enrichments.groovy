

package conceptmetab

class Enrichments {
	
	Concepts id1
	Concepts id2
	Integer intersection
	BigDecimal pval
	BigDecimal qval
	BigDecimal odds
	
	
	static hasMany =[id1:Concepts, id2:Concepts]

	static constraints = {
		
		intersection nullable:false
		pval nullable:false
		qval nullable:false
	}
	
	static mapping = {
		table "enrichments"
		
		
	}
}