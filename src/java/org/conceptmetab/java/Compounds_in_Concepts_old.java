package org.conceptmetab.java;

public class Compounds_in_Concepts_old {
	private	int concept_id;
		private int compound_id;
				
		
	public Compounds_in_Concepts_old(int cid, int compid) {
				this.concept_id = cid;
				this.compound_id = compid;
				
	}
	
	

	public int getConcept_id() {
		return concept_id;
	}
	
	public void setConcept_id(int concept_id) {
		this.concept_id = concept_id;
	}
	
	public int getCompound_id() {
		return compound_id;
	}
	
	public void setCompound_id(int compound_id) {
		this.compound_id = compound_id;
	}
}
