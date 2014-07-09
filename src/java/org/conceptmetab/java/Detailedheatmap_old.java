package org.conceptmetab.java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Map.Entry;

import com.mysql.jdbc.PreparedStatement;

public class Detailedheatmap_old {
	 public static void main(String argv[]) throws Exception {
		 Detailedheatmap_old jbc = new Detailedheatmap_old();
	     jbc.getConcept(254,"qval", "0.05","0","'GO Biological Process','GO Cellular Component','MeSH Diseases'");
	        

	       
	    }
	 
	 
	   
	    public Object[][] getConcept(int id,String fil, String id2, String odds, String dbL) throws ClassNotFoundException, SQLException
	    {
	       
	    	Connection c;
	        Statement s;
	        ArrayList<Concepts> concepts =   new ArrayList<Concepts>(); //Object with name and id
	         //Array of only ids
	        ArrayList <Compounds_in_Concepts> cmp = new ArrayList<Compounds_in_Concepts> (); //Concept id and compound id
	        ArrayList<Compounds> com_int = new ArrayList<Compounds>(); //Compounds from concept of interest
	    	//String [] dbnames = (String[]) dbL.toArray(new String[dbL.size()]);
	           
	        ResourceBundle db = ResourceBundle.getBundle("org.conceptmetab.java.database");
	        String username = db.getString("username");
	        String password = db.getString("password");
	        String url = db.getString("url");
	        Class.forName("com.mysql.jdbc.Driver");
	        c = DriverManager.getConnection(
	                url, username,password);
	       
	        //Get all the concepts matching to concepts of interest *******************************************************************************************************************
	        int count = 0;
	        String sql = "SELECT concepts.id, concepts.name, concept_types.name, concepts.num_compounds, enrichments.intersection, " +
	                "enrichments.pval, enrichments.qval FROM enrichments JOIN concepts ON enrichments.id2_id = concepts.id JOIN concept_types " +
	                "ON concept_types.id = concepts.concept_types_id WHERE enrichments.id1_id = ? and ("+fil+" <= ?) and (odds >= ?) and concept_types.fullname in ("+dbL+") "+
	                "UNION SELECT concepts.id, concepts.name,concept_types.name, concepts.num_compounds, enrichments.intersection, enrichments.pval, enrichments.qval FROM " +
	                "enrichments JOIN concepts ON enrichments.id1_id = concepts.id JOIN concept_types ON concept_types.id = " +
	                "concepts.concept_types_id WHERE enrichments.id2_id = ? and ("+fil+"<= ?) and (odds >= ?) and concept_types.fullname in ("+dbL+")";
	        System.out.println("dbname from java"+ dbL);
	        
	       // String sqlDb = String.format(sql, preparePlaceHolders(dbnames.length),preparePlaceHolders(dbnames.length));
	        
	        //System.out.println("1");
	        PreparedStatement preparedStatement = (PreparedStatement) c.prepareStatement(sql);
	        //System.out.println("2");
	        //setValues(preparedStatement, dbnames);      
	        //setValues(preparedStatement, dbnames); 
	        //PreparedStatement preparedStatement = (PreparedStatement) c.prepareStatement(sql);
	        preparedStatement.setLong(1, id);       
	        preparedStatement.setDouble(2, Double.parseDouble(id2));
	        preparedStatement.setDouble(3, Double.parseDouble(odds));
	       // preparedStatement.setNString(4, dbL);   
	        preparedStatement.setLong(4, id);   
	        preparedStatement.setDouble(5, Double.parseDouble(id2));
	        preparedStatement.setDouble(6, Double.parseDouble(odds));
	        //preparedStatement.setString(7, dbL);   
	        ResultSet results = preparedStatement.executeQuery();
	        
	        System.out.println(preparedStatement);
	       
	       
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
	        
	        Integer[] allConcepts = getConceptId(concepts);
	       
	//Get compounds for all the concepts in this above query.*******************************************************************************************************************
	       
	        //String SQL_FIND = "select c.name,cc.concept_id,cc.compound_id from compounds_in_concepts cc join concepts c on cc.concept_id = c.id where cc.concept_id in (%s)";
	        //select c.name,cc.concept_id,cc.compound_id,com.name from compounds_in_concepts cc join concepts c on cc.concept_id = c.id join compounds com on cc.compound_id = com.id  where cc.concept_id in (1369,3592,494,492,3601);
	        String SQL_FIND ="select c.name as conname,cc.concept_id,cc.compound_id,com.name as comname from compounds_in_concepts cc join concepts c on cc.concept_id = c.id join compounds com on cc.compound_id = com.internal_id where cc.concept_id in (%s)";
	        String sql2 = String.format(SQL_FIND, preparePlaceHolders(allConcepts.length));
	        //System.out.println("1");
	        PreparedStatement preparedStatement2 = (PreparedStatement) c.prepareStatement(sql2);
	        //System.out.println("2");
	        setValues(preparedStatement2, allConcepts);
	        //System.out.println("3");
	        ResultSet rs = preparedStatement2.executeQuery();
	       
	       
	       
	        if (rs != null)
	        {
	            while (rs.next())
	            {
	                //System.out.println("patient_num = " + rs.getString("compound_id"));
	                cmp.add(new Compounds_in_Concepts(rs.getString("conname"),rs.getLong("compound_id")));
	                count++;
	            }
	            //System.out.println("Size of resultset is "+ count);
	            System.out.println("Size of compounds obejct "+ cmp.size());
	       
	        }
	       
	        //Got the hashmap of all the concepts and there compounds *******************************************************************************************************************
	        HashMap<String, ArrayList<Long>> frequencies = createHashmap(cmp);
	        System.out.println("Size of Hashmap = "+ frequencies.size());
	        
	        
	        //Get the compounds of concept of interest.*******************************************************************************************************************
	       String compOfInt = "SELECT compounds_in_concepts.compound_id,compounds.internal_id, compounds.name FROM compounds_in_concepts JOIN compounds ON compounds_in_concepts.compound_id = compounds.internal_id WHERE compounds_in_concepts.concept_id ="+id;
	      // System.out.println("Compounds of interest SQl" +compOfInt);
	        s = c.createStatement();
	        ResultSet resultOfInt = s.executeQuery(compOfInt);
	        if (resultOfInt != null)
	        {
	            while (resultOfInt.next())
	            {
	                //System.out.println("going in comp_int = "+resultOfInt.getString("name")+"With id is " + resultOfInt.getInt("compound_id"));
	                com_int.add(new Compounds(resultOfInt.getString("name"),resultOfInt.getLong("compound_id")));
	               
	            }
	        }
	        
	        ArrayList<Compounds> exactComInt = new ArrayList<Compounds>();
	        exactComInt = createExactComInt(com_int);
	       
	        Object[][] board = createImage(exactComInt,frequencies,concepts,id );       
	        Object[][] mtr= createClusteredMatrix(board);
	        resultOfInt.close();
	        s.close();
	        rs.close();
	        preparedStatement2.close();
	        results.close();
	        preparedStatement.close();
	        c.close();
	        
	        return mtr;

}
	    
	    
private ArrayList<Compounds> createExactComInt(ArrayList<Compounds> com_int) {
	    	
	    	ArrayList<Compounds> exactComInt = new ArrayList<Compounds>();
	    	Map<Long,String > mapComInt = new HashMap<Long,String >();
	    	String name;
	    	
	    	for (int i = 0; i < com_int.size(); i++){
	    		Long id = com_int.get(i).getId();
	    		String comName = com_int.get(i).getName();
	    		//System.out.println("id from input list " + id  + "name is " + comName);
	    		if (!(mapComInt.containsKey(id))){
	    			//System.out.println("inside loop " + id  + "name is " + comName);
	    			mapComInt.put(id, comName);
	    		}
	    		
	    			
	    	}
	    	
	    	for (Map.Entry entry : mapComInt.entrySet()) {
	    		
	    		Long mid = (Long) entry.getKey();
	    		String mname = (String) entry.getValue();
	    		exactComInt.add(new Compounds(mname, mid));
	    	
	    	}
	    	
	    	
	    	
	    	
	    	
	    	return exactComInt;
}

public Object[][] createImage(ArrayList<Compounds> compList,HashMap<String, ArrayList<Long>> frequencies,ArrayList<Concepts> concepts ,int id) throws ClassNotFoundException, SQLException
{

		int total = 0 ;
		int row =frequencies.size() +2; // for total and compounds
		int col = compList.size()+1; // for concpets name
		ArrayList track = new ArrayList();
		
		
		Object [][] board = new Object[row][col];
		//System.out.println("matrix no of rows"+board.length + "size of hashmap" + frequencies.size());
		//Running for each compound
		for (int i =0 ; i < col; i++)
		{
		    int countComp = i-1;
		    int j = 0;
		//    System.out.println("Loop started with i " + i + " j =" +  j);
		    int count = 0;
		    if(i == 0 && j == 0)
		    {
		        board[j][i] =0;
		    //    System.out.println(" [0,0] i " + i + " j =" +  j);
		        j++;
		       
		       
		    }
		    else if(i > 0 && j == 0)//Addition for compound name
		    {
		       
		    //    System.out.println("[ " + j  +" ,"+  i + " ] ="+ "Added column Name: " + compList.get(countComp).getName());
		       
		        board[j][i] = compList.get(countComp).getName();//Add compound name in first row starting from second column
		        j++;
		    }
		  
		   
		           
		   
		             for (Entry<String, ArrayList<Long>> entry : frequencies.entrySet())
		               
		             {
		                //First row is  name of the concept
		                 String key = entry.getKey().toString();
		                 ArrayList<Long> no = entry.getValue();
		                 
		              //  System.out.println("for loop  " +countComp+"Key is " + key +"length of value is" + no.size() + no.toString());
		                
		                 
		                 //For fist columns start adding names of concepts
		                             if(i==0)
		                             {
		                                 board[j][i]=key;
		                        //     System.out.println(" [ " + j + " i =" +  i + " ]   key " + key);
		                                 
		                             }
		                             else
		                             { 
		                                 
		                                 if(no.contains(compList.get(countComp).getId()))
		                                 {
		                                     //System.out.println("match found for " +  key  + "for compoud is  " + compList.get(countComp).getId() + " [" + j + " ," + i + "]" ) ;
		                                    count++;   
		                                    total++;
		                                    board[j][i] = 1;
		                                    // System.out.println("[" + j + "," + i +"]" + "for 1");
		                                 }
		                                 else
		                                 {
		                                    // System.out.println("match not found for " +  key  + "for compoud is  " + compList.get(countComp).getId() + " [" + j + " ," + i + "]" ) ;
		                                     board[j][i] = 0;
		                                    // System.out.println("[" + j + "," + i +"]" + "for 0");
		                                 }
		                             //  System.out.println("for compound  " +compList.get(countComp).getId() + "  count is  " +  count + "j here is " + j);
		                           
		                             }//else
		                 j++;
		             }//For loop
		           
		                board[j][i]=count;
		                
		                if( !(count == 0))
		                		{
		                
		                
		                track.add(i);
		                
		             //   board =deleteColumn(board, col);
		                		}
		
		     
		     
		}
		
		// System.out.println("Newly formed leength is"+board[85][5]);
		// System.out.println("Newly formed leength is"+board[86][5]);
		 
		/*    board= createClusteredMatrix(board);
		
		BufferedImage bImage = createBufferredImage(board);
		
		try {
		     String fileName = "oct23_obj_"+id+".png";
		        if (ImageIO.write(bImage, "png", new File(fileName)))
		        {
		            System.out.println("-- saved");
		        }
		} catch (IOException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		}
		*/
		
		System.out.println("Board row no is" + board.length  + "column no is " + board[1].length);
		board =deleteColumn (board,track);
		
		System.out.println("After effect: Board row no is" + board.length  + "column no is " + board[1].length  + " track size is :" + track.size());
		
		            return board;
}



public static Object[][] deleteColumn(Object[][] board,ArrayList track)
{
		Object[][] nargs = new Object [board.length][track.size()];
		
		//System.out.println("row size is " +  board.length + " col size is " +  track.size());
		//System.out.println( "row size for nargs is " + nargs.length + "column size is " + nargs[1].length);
		int col = 0;
		for (int i = 0; i < track.size();i++)
		{
		
			for(int row = 0 ; row< board.length; row++)
			{
				if (i == 0)
				{
					nargs[row][col] = board[row][0]	;
					
				}
				else
				{
					//System.out.println ("adding at row  " + row + " at col " + col + " value " + board[row][(Integer) track.get(i)] );
					nargs[row][i] = board[row][(Integer) track.get(i)];				
				}
				}
			
		}
		return nargs;
}

public Object[][] createClusteredMatrix(Object[][] board) throws ClassNotFoundException, SQLException
{
    DecimalFormat format2 = new DecimalFormat("0.###");
    //System.out.println(board.length);
    int coumpound = board[1].length;
    int total = board.length -2;
    int totCol = board.length -1;

//    System.out.println("board length is = " + board.length + "column length is = " +coumpound );
    for (int col=1; col < coumpound; col++) {
       // System.out.println(col);
        int colTotal =  (Integer) board[ totCol][col];
       
       // System.out.println("For compound "+ col + "colTotal is" +  colTotal);
        // Loop through the columns in the rowth row.
        for (int row=1; row < board.length; row++){
        // Add the addMe value to the [row][col] entry
        // in the array.
                   
         //   System.out.println(board[row][col]);
            int test = (Integer) board[row][col];
            if(test == 1)
            {
            board[row][col] = (colTotal*100)/total;
            }
           
        }
      
    }
    return board;
   
}


public Integer[] getConceptId (ArrayList<Concepts> concepts) throws ClassNotFoundException, SQLException
{
    //System.out.println("Inside getConceptId  "+  concepts.size());
   
    ArrayList <Integer> cons = new ArrayList<Integer> ();
    for (int i = 0; i<  concepts.size(); i++)
    {
        cons.add(concepts.get(i).getId());
    }
   // System.out.println("Size of class "+ concepts.size() + " size of new array = " +  cons.size());
    Integer[] sb = cons.toArray(new Integer[cons.size()]);
   // System.out.println("ConceptArrays "+ sb);
    return sb;
   
}

public static void setValues(PreparedStatement preparedStatement, Object... values) throws SQLException {
    for (int i = 0; i < values.length; i++) {
        preparedStatement.setObject(i + 1, values[i]);
    }
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

public LinkedHashMap createHashmap(ArrayList<Compounds_in_Concepts> cmp) throws ClassNotFoundException, SQLException
{
       
           
    LinkedHashMap<String,ArrayList<Long>> frequencies=new LinkedHashMap<String,ArrayList<Long>>();
   
        for (int i =0 ; i < cmp.size();i++)
        {
           
            //System.out.println("for id :"+ cmp.get(i).getConcept_id());
       
            ArrayList<Long> uniList = new ArrayList<Long> () ;
            if(frequencies.containsKey(cmp.get(i).getConcept_id()))
            {
               
                uniList =  frequencies.get(cmp.get(i).getConcept_id());
                //System.out.println("for "+ cmp.get(i).getConcept_id() + "  Already have compounds with size   " + uniList.get(0) + "    new id is :" + cmp.get(i).getCompound_id());
               
               
                if(!uniList.contains(cmp.get(i).getCompound_id()))
                {
                    //System.out.println("inside if loop");
                    uniList.add(cmp.get(i).getCompound_id());
                }
               
               
           
            }
            else
            {
                //sSystem.out.println("for "+ cmp.get(i).getConcept_id() + " Adding concpet and compound in hasmap ::::" + cmp.get(i).getCompound_id());
                uniList.add(cmp.get(i).getCompound_id());
            }
            //System.out.println("for "+ cmp.get(i).getConcept_id() + "Added compounds: " + uniList.size());
            frequencies.put(cmp.get(i).getConcept_id(), uniList);
                   
           
        }
   
//    System.out.println("Sixe of cmp"+frequencies.get(1293).size());
       
         
       
   
    return frequencies;
}


}
