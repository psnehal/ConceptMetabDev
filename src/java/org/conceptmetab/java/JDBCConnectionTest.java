package org.conceptmetab.java;


import java.awt.Color;
import java.awt.Font;


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
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;

import java.util.Map.Entry;
import java.util.ResourceBundle;


import javax.imageio.ImageIO;


import com.mysql.jdbc.PreparedStatement;


public class JDBCConnectionTest {

    public static void main(String argv[]) throws Exception {
        JDBCConnectionTest jbc = new JDBCConnectionTest();
    //    ArrayList <Compounds> cmp = jbc.getCompounds(456);
       
    //System.out.println(cmp.get(210135).getCompound_id());
    //System.out.println(cmp.get(210135).getConcept_id());
   
       // fil, id2,odds,db)
        ArrayList db = new ArrayList();
        db.add("GOCC");
        db.add("GOBP");
       
        jbc.getConcept(4784,"qval", "0.05","0","'GO Biological Process','GO Cellular Component','MeSH Diseases'");
        //jbc.createHashmap(3460);
    //jbc.createHashmap(456);
        //jbc.createImage(1514);
        //jbc.getCompounds(15727);
        //jbc.getConceptId(3);

       
    }
   
    public BufferedImage getConcept(int id,String fil, String id2, String odds, String dbL) throws ClassNotFoundException, SQLException
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
                "ON concept_types.id = concepts.concept_types_id WHERE enrichments.id1_id = ? and ("+fil+" < ?) and (odds > ?) and concept_types.fullname in ("+dbL+") "+
                "UNION SELECT concepts.id, concepts.name,concept_types.name, concepts.num_compounds, enrichments.intersection, enrichments.pval, enrichments.qval FROM " +
                "enrichments JOIN concepts ON enrichments.id1_id = concepts.id JOIN concept_types ON concept_types.id = " +
                "concepts.concept_types_id WHERE enrichments.id2_id = ? and ("+fil+"< ?) and (odds > ?) and concept_types.fullname in ("+dbL+") limit 10";
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
       
        System.out.println("From copy of JDBC ConceptArrays "+ sb);
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
        //System.out.println("Size of Compounds of Interest = "+ com_int.size());
        //System.out.println("************************Concepts size :" + concepts.size()+ "Compounds size is : "+ com_int.size() +  "size of hashmap is :" + frequencies.size()  );
       
       
        Object[][] board = createImage(com_int,frequencies,concepts,id );
        Object[][] mtr= createClusteredMatrix(board);
        BufferedImage bImage =createBufferredImage(mtr,id);
        resultOfInt.close();
        s.close();
        rs.close();
        preparedStatement2.close();
        results.close();
        preparedStatement.close();
        c.close();
        //bImage
        return bImage ;
    }
   
public Object[][] createImage(ArrayList<Compounds> compList,HashMap<String, ArrayList<Long>> frequencies,ArrayList<Concepts> concepts ,int id) throws ClassNotFoundException, SQLException
    {
   
    int total = 0 ;
    int row =frequencies.size() +2; // for total and compounds
    int col = compList.size()+1; // for concpets name
   
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
                //    System.out.println(" [j , i] "+ j+","+ i + "count is" + count);
   
         
         
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
    System.out.println(total);*/
                return board;
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

public BufferedImage createBufferredImage(Object[][] board, int id )
{
           

    int rc_width = 20;
    int compoundsize = board[1].length;
    int BI_WIDTH =  compoundsize*20 + 250;
    System.out.println("Width of rectangle "+ rc_width + "Compounds no :"+ compoundsize + "Total size of the rectacle is" + BI_WIDTH );
   
    int rc_height = 20;
    int BI_HEIGHT = board.length*20+200;
    System.out.println("Height of rectangle :"+rc_height + "Concepts length :"+  board.length + "Total height of rectalgle is" + BI_HEIGHT );
   
   
    System.out.println("Width of image : " + BI_WIDTH + "height is " + BI_HEIGHT );
   
    BufferedImage bImage = new BufferedImage(BI_WIDTH, BI_HEIGHT,BufferedImage.TYPE_INT_RGB);   
    Graphics2D g2d = bImage.createGraphics();
   
    //Loop for all the compounds
    //System.out.println("Compounds size is"+ compoundsize);
    StringBuffer sb = new StringBuffer();
    int count = 0;
    int y_cor = 150;
    int x_cor = 0;
    int y_fcor = 165;
   
   
    int rowlen = board.length;
    //System.out.println("Board length"+board.length);
    //Row wise
   
    for(int col = 0 ;col < compoundsize;col ++)
    {
       
       
        int ch = x_cor+150;
        final AffineTransform saved = g2d.getTransform();
        final AffineTransform rotate = AffineTransform.getRotateInstance(-Math.PI /2, ch, 140);
        g2d.transform(rotate);
       
       
             String colname = board [0][col].toString();
            // System.out.println("Entering column  " + col + "with colname"+ colname + "with x_cor" + ch);
             if(colname.length() > 30)
                {
                 colname = colname.substring(0, 30);
                }
                 g2d.setColor(Color.red);
                g2d.setFont(new Font( "SansSerif", Font.BOLD, 12 ));                   
               
               
                System.out.println("Colname is " +  colname + "added to "  + "[" + 0 +"," +col +"]" + "with x  and y cor" + ch + "and 100"  );
                g2d.drawString(colname, ch, 0);
               
                g2d.setTransform(saved);           
                     
        int comcheck = compoundsize-1;
                 for(int row = 1; row< rowlen;row++)
                 {
                      if(col == 0 )
                     {
                       
                        String rowname = board[row][col].toString();
                        /*if(rowname.length() > 30)
                        {
                            rowname = rowname.substring(0, 30);
                        }
                        */
                        g2d.setColor(Color.red);
                        g2d.setFont(new Font( "SansSerif", Font.BOLD, 12 ));
                        int ch2 = compoundsize*20 +10;
                        g2d.drawString(rowname, ch2, y_fcor);
                    //    System.out.println("Rowname is " +  rowname + "added to "  + "[" + row +"," +col +"]" + "with x "+ch2+" and y cor" + y_fcor  );
                        y_fcor = y_fcor+ rc_height;
                           
                     }
                      else{
                     
                     
                                          int check = (Integer) board[row][col];
                                         
                                          if(check == 0)
                                             {
                                               
                                                  g2d.setPaint ( new Color ( 0,0,0 ) );
                                           // System.out.println("Its for no with check" +check +"and cord "+x_cor+" " + y_cor+ " " );
                                                  g2d.fillRect(x_cor,y_cor,rc_width,rc_height);
                                                  y_cor = y_cor+ rc_height;
                                                          
                                               
                                                 
                                               
                                             }
                                             else
                                             {
                                                 int g = getColor(check);
                                                    if(row == 85 && col == 5)
                                                    {
                                                     //   System.out.println("board is" + board [86][5] + " value of g is" +  g);
                                                       
                                                       
                                                    }
                                                     g2d.setPaint ( new Color ( 255,g,0 ) );
                                                    System.out.println("Its for yes with check" +check +"and cord "+x_cor+" " + y_cor+ " " );
                                                     g2d.fillRect(x_cor,y_cor,rc_width,rc_height);
                                                     y_cor = y_cor+ rc_height;
                                                           
                                               
                                   
                                         }
                     
                      }
                     
                     
                 }//For loop of rows -concepts
       
               
       
       
                 x_cor = x_cor+rc_height;   
               
        y_cor = 150;
       
    }//For loop of compound
   

   
                try {
                     String fileName = "jan20_500"+id+".png";
                       if (ImageIO.write(bImage, "png", new File(fileName)))
                       {
                           System.out.println("-- saved" +  fileName);
                       }
               } catch (IOException e) {
                       // TODO Auto-generated catch block
                       e.printStackTrace();
               }
               
    System.out.println("-- saved" );      
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
        g= 75;
       
    }else if (per<100)
    {
        g= 50;
       
    }
    else
    {
        g =0;
    }
return g;
}

   
   
   
    public Integer[] getConceptId (ArrayList<Concepts> concepts) throws ClassNotFoundException, SQLException
    {
        //System.out.println("Inside getConceptId  "+  concepts.size());
       
        ArrayList <Integer> cons = new ArrayList<Integer> ();
        for (int i = 0; i<  concepts.size(); i++)
        {
            cons.add(concepts.get(i).getId());
        }
        System.out.println("Size of class "+ concepts.size() + " size of new array = " +  cons.size());
        Integer[] sb = cons.toArray(new Integer[cons.size()]);
        System.out.println("ConceptArrays "+ sb);
        return sb;
       
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
   
   /* public ArrayList<Compounds> getConceptOfInterstComp(int id) throws ClassNotFoundException, SQLException
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
        String sql = "SELECT compounds_in_concepts.compound_id,compounds.display_id, compounds.name FROM compounds_in_concepts JOIN compounds ON compounds_in_concepts.compound_id = compounds.internal_id WHERE compounds_in_concepts.concept_id ="+id;
        s = c.createStatement();
        ResultSet results = s.executeQuery(sql);
        if (results != null)
        {
            while (results.next())
            {
                //System.out.println("patient_num = " + results.getLong("compound_id"));
                com_int.add(new Compounds(results.getString("name"),results.getInt("compound_id")));
               
            }
        }
        //System.out.println(com_int);
        results.close();
        s.close();
        c.close();
        return com_int;
       
    }*/
   
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
           
             for (Entry<String, ArrayList<Long>> entry : frequencies.entrySet())
             {
                System.out.println("Concepts is --->" +  entry.getKey());
                System.out.println("Compounds is -->" +  entry.getValue().get(1));
                 
             }
           
       
        return frequencies;
    }
   
   
//this approch is test column wise to create heatmap   
   

       
}