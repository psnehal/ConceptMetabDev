package org.conceptmetab.java;

public class Compounds {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	
	
	private	String name ;
	private Long id;
	
	
	
	public Compounds(String name, Long id) {
		super();
		this.name = name;
		this.id = id;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

}
