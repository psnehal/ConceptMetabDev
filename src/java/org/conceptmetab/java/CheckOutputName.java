package org.conceptmetab.java;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ResourceBundle;

public class CheckOutputName {

	
	public static void main(String[] args) {
		CheckOutputName cho = new CheckOutputName();
		System.out.println("******************"+cho.check("CopyWith100_252"));

	}
	
	public String check(String checkName)
	{
		String chk = "";
		int count = 1;
		ResourceBundle url = ResourceBundle.getBundle("org.conceptmetab.java.database");
		String filePath = url.getString("uploadDirectory");
		File folder = new File(filePath);
		FileWriter fstream;
		File[] listOfFiles = folder.listFiles();  
		for (int i = 0; i < listOfFiles.length; i++) 
		 {
			
		   String names = listOfFiles[i].getName();
		  //  System.out.println("Name is :->"+names);
		   // System.out.println("Chekcname is" + checkName );
		    if(names.contains(checkName))
		    {
		    	
		    
		     count++;  
		    }
		  			    
		 }
		
		if (count != 1)
		{
			checkName= checkName+"_"+count;
		}
		return checkName; 
	}
	
	 

}
