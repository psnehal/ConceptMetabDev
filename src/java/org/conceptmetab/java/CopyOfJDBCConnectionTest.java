package org.conceptmetab.java;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Set;

import javax.imageio.ImageIO;

import javassist.bytecode.Descriptor.Iterator;

import antlr.collections.List;

import com.mysql.jdbc.PreparedStatement;


public class CopyOfJDBCConnectionTest {

	public static void main(String argv[]) throws Exception {
		CopyOfJDBCConnectionTest jbc = new CopyOfJDBCConnectionTest();
	//	ArrayList <Compounds> cmp = jbc.getCompounds(456);
		
	//System.out.println(cmp.get(210135).getCompound_id());
	//System.out.println(cmp.get(210135).getConcept_id());
	
		
		//jbc.getConcept(15727);
		//jbc.createHashmap(3460);
	//jbc.createHashmap(456);
		jbc.createImage(1514);
		//jbc.createClusteredMatrix(1514);
		//jbc.getCompounds(15727);
		//jbc.getConceptId(3);

		
	}
	
	public ArrayList<Concepts> getConcept(int id) throws ClassNotFoundException, SQLException
	{
		
		Connection c;
		Statement s;
		
		
		ResourceBundle db = ResourceBundle.getBundle("org.conceptmetab.java.database");
		String username = db.getString("username");
		String password = db.getString("password");
		String url = db.getString("url");
		Class.forName("com.mysql.jdbc.Driver");
		c = DriverManager.getConnection(
				url, username,password);
		
		
		int count = 0;
		String sql = "SELECT concepts.id, concepts.name, concept_types.name, concepts.num_compounds, enrichments.intersection, " +
				"enrichments.pval, enrichments.qval FROM enrichments JOIN concepts ON enrichments.id2_id = concepts.id JOIN concept_types " +
				"ON concept_types.id = concepts.concept_types_id WHERE enrichments.id1_id = ?  "+
				"UNION SELECT concepts.id, concepts.name,concept_types.name, concepts.num_compounds, enrichments.intersection, enrichments.pval, enrichments.qval FROM " +
				"enrichments JOIN concepts ON enrichments.id1_id = concepts.id JOIN concept_types ON concept_types.id = " +
				"concepts.concept_types_id WHERE enrichments.id2_id = ? ORDER BY qval limit 5";
		//System.out.println(sql);
		PreparedStatement preparedStatement = (PreparedStatement) c.prepareStatement(sql);
		preparedStatement.setLong(1, id);
		preparedStatement.setLong(2, id);
		ResultSet results = preparedStatement.executeQuery();
		ArrayList<Concepts> concepts =   new ArrayList<Concepts>();
		
		StringBuilder sb = new StringBuilder();
		//System.out.println(results.getFetchSize());
		if (results != null)
		{
			while (results.next())
			{
				//System.out.println("patient_num = " + results.getString("id"));
				sb.append(results.getString("id")+",");
				concepts.add(new Concepts(results.getString("name"),results.getInt("id")));
				
				count++;
			}
			System.out.println("Size of resultset is "+ count);
		
		}
		
		//Integer[] con = concepts.toArray(new Integer[concepts.size()]);
		System.out.println("ConceptArrays "+ sb);
		results.close();
		preparedStatement.close();
		c.close();
		return concepts;
	}
	
	public Integer[] getConceptId ( int id) throws ClassNotFoundException, SQLException
	{
		System.out.println("Inside getConceptId  "+  id);
		ArrayList <Integer> cons = new ArrayList<Integer> ();
	
		ArrayList<Concepts> concepts =   new ArrayList<Concepts>();
		concepts = getConcept(id);
		
		for (int i = 0; i<  concepts.size(); i++)
		{
			cons.add(concepts.get(i).getId());
		}
		System.out.println("Size of class "+ concepts.size() + " size of new array = " +  cons.size());
		Integer[] sb = cons.toArray(new Integer[cons.size()]);
		System.out.println("ConceptArrays "+ sb);
		return sb;
		
	}
	
	public ArrayList<Compounds_in_Concepts_old> getCompounds(int id) throws SQLException, ClassNotFoundException
	{
		
		Connection c;
		Statement s;
	//	String database = "transmart";
		ResourceBundle db = ResourceBundle.getBundle("org.conceptmetab.java.database");
		String username = db.getString("username");
		String password = db.getString("password");
		String url = db.getString("url");
		Class.forName("com.mysql.jdbc.Driver");
		c = DriverManager.getConnection(
				url, username,password);
		
		int count = 0;
		
		Integer[]  sb = getConceptId(id);
	
		System.out.println("From getCompounds"+ sb);
		//final String SQL_FIND = "SELECT id, name, value FROM data WHERE id IN (%s)";
		String SQL_FIND = "select concept_id,compound_id from compounds_in_concepts where concept_id in (%s)";
		String sql = String.format(SQL_FIND, preparePlaceHolders(sb.length));
		//System.out.println("1");
		PreparedStatement preparedStatement = (PreparedStatement) c.prepareStatement(sql);
		//System.out.println("2");
		setValues(preparedStatement, sb);
		//System.out.println("3");
		ResultSet rs = preparedStatement.executeQuery();
		//System.out.println("4");
	
		ArrayList <Compounds_in_Concepts_old> cmp = new ArrayList<Compounds_in_Concepts_old> ();
		if (rs != null)
		{
			
			while (rs.next())
			{
				//System.out.println("patient_num = " + rs.getString("compound_id"));
				cmp.add(new Compounds_in_Concepts_old(rs.getInt("concept_id"),rs.getInt("compound_id")));
				count++;
			}
			//System.out.println("Size of resultset is "+ count);
			System.out.println("Size of compounds obejct "+ cmp.size());
			
			
		
		}
		
		rs.close();
		preparedStatement.close();
		c.close();
		
		return cmp;
		
	}
	
	public static String preparePlaceHolders(int length) {
	    StringBuilder builder = new StringBuilder();
	    for (int i = 0; i < length;) {
	        builder.append("?");
	        if (++i < length) {
	            builder.append(",");
	        }
	    }
	    return builder.toString();
	}

	public static void setValues(PreparedStatement preparedStatement, Object... values) throws SQLException {
	    for (int i = 0; i < values.length; i++) {
	        preparedStatement.setObject(i + 1, values[i]);
	    }
	}
	
	public ArrayList<Compounds> getConceptOfInterstComp(int id) throws ClassNotFoundException, SQLException
	{
		Connection c;
		Statement s;
		ResourceBundle db = ResourceBundle.getBundle("org.conceptmetab.java.database");
		String username = db.getString("username");
		String password = db.getString("password");
		String url = db.getString("url");
		Class.forName("com.mysql.jdbc.Driver");
		c = DriverManager.getConnection(
				url, username,password);
		
		
		ArrayList<Compounds> com_int = new ArrayList<Compounds>();
		String sql = "SELECT compounds_in_concepts.compound_id,compounds.display_id, compounds.name FROM compounds_in_concepts JOIN compounds ON compounds_in_concepts.compound_id = compounds.id WHERE compounds_in_concepts.concept_id ="+id;
		s = c.createStatement();
		ResultSet results = s.executeQuery(sql);
		if (results != null)
		{
			while (results.next())
			{
				//System.out.println("patient_num = " + results.getLong("compound_id"));
				com_int.add(new Compounds(results.getString("name"),results.getLong("compound_id")));
				
			}
		}
		//System.out.println(com_int);
		results.close();
		s.close();
		c.close();
		return com_int;
		
	}
	
	/*Integer[] concepts = getConcept(id);
		System.out.println("It is hashmap afer getting concepts");
		HashMap hm = new HashMap();
		ArrayList<Integer> com_int = getConceptOfInterstComp(id);
		System.out.println("It is hashmap afer getting getConceptOfInterstComp");
		//Loops thro all the concepts to check value
			for (int i = 0; i < concepts.length;i++)
			{
				
				hm.put(concepts[i],getConceptOfInterstComp(concepts[i]) );				
				
				
			}
		
		System.out.println(hm.toString());
	
	 */
	
	public HashMap createHashmap(int id) throws ClassNotFoundException, SQLException
	{
			ArrayList<Compounds_in_Concepts_old> cmp = new ArrayList<Compounds_in_Concepts_old>();
			cmp = getCompounds(id);
			
			HashMap<Integer,ArrayList<Integer>> frequencies=new HashMap<Integer,ArrayList<Integer>>();
			for (int i =0 ; i < cmp.size();i++)
			{
				//System.out.println("for id"+ cmp.get(i).getConcept_id());
			
				ArrayList<Integer> uniList = new ArrayList<Integer> () ;
				if(frequencies.containsKey(cmp.get(i).getConcept_id()))
				{
					//System.out.println("for "+ cmp.get(i).getConcept_id() + "Added compounds: ");
					uniList =  frequencies.get(cmp.get(i).getConcept_id());
					if(!uniList.contains(cmp.get(i).getCompound_id()))
					{
						uniList.add(cmp.get(i).getCompound_id());
					}
					
				
				}
				else
				{
					uniList.add(cmp.get(i).getCompound_id());
				}
				//System.out.println("for "+ cmp.get(i).getConcept_id() + "Added compounds: " + uniList.size());
				frequencies.put(cmp.get(i).getConcept_id(), uniList);
						
				
			}
		
	//	System.out.println("Sixe of cmp"+frequencies.get(1293).size());
		return frequencies;
	}
	
	public BufferedImage createImage(int id) throws ClassNotFoundException, SQLException
	
	{
		/*HashMap<Integer,ArrayList<Integer>> conList= createHashmap(id);
	//	Integer[] cmp = getConceptId(id);
		Integer[] concepts = getConceptId(id);
		
		
		System.out.println("*************************Concepts size :" + concepts.length + "Compounds size is : "+ compList.size() +  "size of hashmap is :" + conList.size()  );
		
		 int rc_width = 20; 
				 //(BI_WIDTH/compList.size());
		 int BI_WIDTH =  compList.size()*20 + 100;
		 
		 System.out.println("Width of rectangle "+ rc_width + " Compounds no :"+ compList.size());
		 
		 int tc_height = 20; 
				 //(BI_HEIGHT/cmp.length);
		 int BI_HEIGHT = concepts.length*20;
		 System.out.println("Height of rectangle :"+tc_height + "  Concepts length :"+ concepts.length);
		 System.out.println("Width of image : " + BI_WIDTH + "height is " + BI_HEIGHT );
		 BufferedImage bImage = new BufferedImage(BI_WIDTH, BI_HEIGHT,
		            BufferedImage.TYPE_INT_RGB);
		 
		 Graphics2D g2d = bImage.createGraphics();*/
		
		ArrayList<Compounds> compList = getConceptOfInterstComp(id);
		Integer[] concepts = getConceptId(id);
		HashMap<Integer,ArrayList<Integer>> conList= createHashmap(id);
		
		//Loop for all the compounds
		System.out.println("Compounds size is"+compList.size());
		StringBuffer sb = new StringBuffer();
		int total = 0 ;
		int y_cor = 100;
		int x_cor = 50;
		int y_fcor = 115;
		int row = concepts.length+1;
		
		double[][] board = new double[row][compList.size()];
		System.out.println("matrix no of rows"+board.length);
		//Running for each compound
		for (int i =0 ; i < compList.size(); i++)
		{
			
			int j =0;
			int count = 0;
					 for (Entry<Integer, ArrayList<Integer>> entry : conList.entrySet())
						
					 {
						 String key = entry.getKey().toString();
						 ArrayList<Integer> no = entry.getValue();
						 
						 if(no.contains(compList.get(i).getId()))
						 {
							count++;	
							total++;
							board[j][i] = 1;
						 }
						 else
						 {
							 board[j][i] = 0;
						 }
						 j++;
					 }//For loop
			board[j][i]=count;
			 
			 System.out.println("for compound" +compList.get(i).getId() + "  count is  " +  count + "j here is " + j);
		}
		// System.out.println(sb);
		 
		board= createClusteredMatrix(board);
		
		BufferedImage bImage = createBufferredImage(board);
		
		try {
			 String fileName = "oct21_"+id+".png";
	            if (ImageIO.write(bImage, "png", new File(fileName)))
	            {
	                System.out.println("-- saved");
	            }
	    } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	    }
		System.out.println(total);
			return bImage;
	}
//this approch is test column wise to create heatmap	
	

	public double [][] createClusteredMatrix(double[][] mtr) throws ClassNotFoundException, SQLException
	{
		DecimalFormat format2 = new DecimalFormat("0.###");
		System.out.println(mtr.length);
		int coumpound = mtr[1].length;
	
		for (int col=0; col < coumpound; col++) {

			int colTotal = (int) mtr[mtr.length-1][col];
			
			System.out.println("For compound "+ col + "colTotal is" +  colTotal);
		    // Loop through the columns in the rowth row.
		    for (int row=0; row < mtr.length; row++){
		    // Add the addMe value to the [row][col] entry
			// in the array.
		    	    	
		    	//System.out.println(mtr[row][col]);
		    	if(mtr[row][col] == 1)
		    	{
		    	mtr[row][col] = (colTotal*100)/mtr.length;
		    	}
		    	
		    }
		   
		}
		return mtr;
		
	}
	
	
	public BufferedImage createBufferredImage(double [][]mtr )
	{
				int rc_width = 20; 
				int compoundsize = mtr[1].length;
				 //(BI_WIDTH/compList.size());
		int BI_WIDTH =  compoundsize*20 + 100;
		
		System.out.println("Width of rectangle "+ rc_width + " Compounds no :"+ compoundsize );
		
		int tc_height = 20; 
				 //(BI_HEIGHT/cmp.length);
		int BI_HEIGHT = mtr.length*20;
		System.out.println("Height of rectangle :"+tc_height + "  Concepts length :"+  BI_HEIGHT);
		System.out.println("Width of image : " + BI_WIDTH + "height is " + BI_HEIGHT );
		BufferedImage bImage = new BufferedImage(BI_WIDTH, BI_HEIGHT,
		           BufferedImage.TYPE_INT_RGB);
		
		Graphics2D g2d = bImage.createGraphics();
		
		
		
		//Loop for all the compounds
		System.out.println("Compounds size is"+ compoundsize);
		StringBuffer sb = new StringBuffer();
		int count = 0;
		int y_cor = 100;
		int x_cor = 100;
		int y_fcor = 115;
		
		for (int row=0; row < mtr.length; row++) {

		    // Loop through the columns in the rowth row.
		    for (int col=0; col < mtr[row].length; col++)
		    	{
			    
			    	
			    	double no = mtr[row][col];
			    	
			    	if(mtr[row][col] == 0)
			    	{	    	
				    	 g2d.setPaint ( new Color ( 0,0,0 ) );
							// System.out.println("Its for yes "+x_cor+" " + y_cor+ " " + rc_width+ " "+ tc_height);
						 g2d.fillRect(x_cor,y_cor,rc_width,tc_height);
						 x_cor = x_cor+20;
			    	}
			    	else
			    	{
			    		
			    		int g = getColor(no);
				    	//System.out.println((8*no) + ((1-no)*255));
				    	 g2d.setPaint ( new Color ( 255,g,0 ) );
							// System.out.println("Its for yes "+x_cor+" " + y_cor+ " " + rc_width+ " "+ tc_height);
						 g2d.fillRect(x_cor,y_cor,rc_width,tc_height);
						 x_cor = x_cor+20;
			    	}
			    	
					 
		    	}
		    
		   
		    y_cor = y_cor+20;
		    x_cor = 100;
		   
		}
		return bImage;
	}
	
	
	public int getColor(double per){
					int g =0;
					if(per < 10)
					{
						g = 255;
					}
					else if (per<20)
					{
						g= 225;
						
					}
					else if (per<30)
					{
						g= 220;
						
					}
					else if (per<40)
					{
						g= 200;
						
					}
					else if (per<50)
					{
						g= 175;
						
					}
					else if (per<60)
					{
						g= 150;
						
					}
					else if (per<70)
					{
						g= 125;
						
					}
					else if (per<80)
					{
						g= 100;
						
					}
					else if (per<90)
					{
						g= 50;
						
					}else if (per<100)
					{
						g= 0;
						
					}
		return g;
		}
		
		
	
	/*public int getColumnTotal()(int [][] mtr, int index)
	{
		
		
	}*/
		
}

