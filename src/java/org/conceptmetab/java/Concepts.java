package org.conceptmetab.java;

public class Concepts {

	/**
	 * @param args
	 */
	
	private	String name ;
	
	public Concepts(String name, int id) {
		super();
		this.name = name;
		this.id = id;
	}


	private int id;
	
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
