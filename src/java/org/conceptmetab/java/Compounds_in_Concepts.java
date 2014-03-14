package org.conceptmetab.java;

public class Compounds_in_Concepts {
	private	String concept_name;
		private long compound_id;
				
		
	public Compounds_in_Concepts(String cid, long l) {
				this.concept_name = cid;
				this.compound_id = l;
				
	}
	
	

	public String getConcept_id() {
		return concept_name;
	}
	
	public void setConcept_id(String concept_id) {
		this.concept_name = concept_id;
	}
	
	public long getCompound_id() {
		return compound_id;
	}
	
	public void setCompound_id(long compound_id) {
		this.compound_id = compound_id;
	}
}
