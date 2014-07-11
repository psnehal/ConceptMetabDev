package org.conceptmetab.java;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;

public class createColumnLabel {
	
	 public static void main(String argv[]) throws Exception {
		 createColumnLabel jbc = new createColumnLabel();
	        
	        
	 }
	
	
	
	
	public Map<String, String> createBufferredImageMod(Object[][] board, int id )
	{
	           

	    int rc_width = 30;
	    int compoundsize = board[1].length;
	    int BI_WIDTH =  board.length*30 ;
	    System.out.println("board.length"+ board.length +"   Width of rectangle "+ rc_width + "   Compounds no :"+ compoundsize + "    Total size of the rectacle is" + BI_WIDTH );
	   
	    int rc_height = 30;
	    int BI_HEIGHT =compoundsize *30;
	    System.out.println(" compoundsize" + compoundsize+ "  Height of rectangle :"+rc_height + "   Concepts length :"+  board.length + "     Total height of rectalgle is" + BI_HEIGHT );
	   
	   int wXaxis =  board.length*30 ;
	   int hXaxis = 150;
	   
	   int wYaxis = 250;
	   int hYaxix = compoundsize*30;
	   
	    System.out.println("Width of image : " + BI_WIDTH + "height is " + BI_HEIGHT );
	   
	    BufferedImage bImage = new BufferedImage(BI_WIDTH, BI_HEIGHT,BufferedImage.TYPE_INT_RGB);   
	    BufferedImage bXaxis = new BufferedImage(wXaxis, hXaxis,BufferedImage.TYPE_INT_RGB);   
	    BufferedImage bYaxis = new BufferedImage(wYaxis, hYaxix,BufferedImage.TYPE_INT_RGB);   
	    Graphics2D g2d = bImage.createGraphics();
	    Graphics2D gXaxis = bXaxis.createGraphics();
	    Graphics2D gYaxis = bYaxis.createGraphics();
	    g2d.setBackground(Color.WHITE);
	    gXaxis.setBackground(Color.WHITE);
	    gYaxis.setBackground(Color.WHITE);
	    g2d.fillRect(0, 0, BI_WIDTH, BI_HEIGHT);
	    gXaxis.fillRect(0, 0, wXaxis, hXaxis);
	    gYaxis.fillRect(0, 0, wYaxis, hYaxix);
	    //Loop for all the compounds
	    //System.out.println("Compounds size is"+ compoundsize);
	    StringBuffer sb = new StringBuffer();
	    int count = 0;
	    int y_cor = 0;
	    int x_cor = 0;
	    int y_fcor = 20;
	   
	   
	    int rowlen = board.length;
	    //System.out.println("Board length"+board.length);
	    //Row wise
	   
	    for(int row = 0; row< rowlen;row++)
	    {
	       
	       
	        int ch = x_cor+165;
	        final AffineTransform saved = gXaxis.getTransform();
	        final AffineTransform rotate = AffineTransform.getRotateInstance(-Math.PI /2, ch, 140);
	        gXaxis.transform(rotate);
	       
	       
	             String colname = board [row][0].toString();
	            // System.out.println("Entering column  " + col + "with colname"+ colname + "with x_cor" + ch);
	             if(colname.length() > 30)
	                {
	                 colname = colname.substring(0, 30);
	                }
	             gXaxis.setColor(Color.black);
	             if (compoundsize < 50 )
	             {
	            	// System.out.println("compound size is less than 50 loop");
	             gXaxis.setFont(new Font( "SansSerif", Font.BOLD, 15 ));   
	             }
	             else
	             {
	            	 gXaxis.setFont(new Font( "SansSerif", Font.BOLD, 25 ));   
	             }
	               
	               
	          //   System.out.println("Colname is " +  colname + "added to "  + "[" + 0 +"," +col +"]" + "with x  and y cor" + ch + "and 0"  );
	             gXaxis.drawString(colname, ch, 0);
	               
	             gXaxis.setTransform(saved);           
	                     
	        int comcheck = compoundsize-1;
	                 for(int col = 1 ;col < compoundsize;col ++)
	                 {
	                      if(row == 0 )
	                     {
	                       
	                        String rowname = board[0][col].toString();
	                        /*if(rowname.length() > 30)
	                        {
	                            rowname = rowname.substring(0, 30);
	                        }
	                        */
	                        gYaxis.setColor(Color.black);
	                        gYaxis.setFont(new Font( "Sansserif", Font.BOLD, 20 ));
	                        int ch2 = compoundsize*20 +10;
	                        gYaxis.drawString(rowname, x_cor, y_fcor);
	                    System.out.println("Rowname is " +  rowname + "added to "  + "[" + row +"," +col +"]" + "with x "+x_cor+" and y cor" + y_fcor  );
	                        y_fcor = y_fcor+ rc_height;
	                           
	                     }
	                      else{
	                     
	                          // System.out.println("added to "  + "[" + row +"," +col +"]" + "with x "+x_cor+" and y cor" + y_fcor  );
	                                          int check = (Integer) board[row][col];
	                                         
	                                          if(check == 0)
	                                             {
	                                               
	                                                  g2d.setPaint ( new Color ( 255, 255, 255 ) );
	                                          // System.out.println("Its for no with check" +check +"and cord "+x_cor+" " + y_cor+ " "  +" rc_width"+ rc_width+ " rc_height"+ rc_height);
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
	                                                   // System.out.println("Its for yes with check" +check +"and cord "+x_cor+" " + y_cor+ " " );
	                                                     g2d.fillRect(x_cor,y_cor,rc_width,rc_height);
	                                                     y_cor = y_cor+ rc_height;
	                                                           
	                                               
	                                   
	                                         }
	                     
	                      }
	                     
	                     
	                 }//For loop of rows -concepts
	       
	               
	       
	       
	                 x_cor = x_cor+rc_height;   
	               
	        y_cor = 0;
	       
	    }//For loop of compound
	   
	    Map<String,String > mapImages = new HashMap<String, String>();
	 
	    UUID uuid = UUID.randomUUID();
	   
	                try {
	                     String fileName = "/tmp/"+uuid+"_"+ id+".png";
	                    
	                     String fileXaxis ="/tmp/"+ uuid+"_"+ id+"_Xaxis"+id+".png";
	                     String fileYaxis = "/tmp/"+uuid+"_"+ id+"_Yaxis"+id+".png";
	                     
	                     mapImages.put("image",fileName.replace("/tmp/", "") );
	                     mapImages.put("xaxis", fileXaxis.replace("/tmp/", ""));
	                     mapImages.put("yaxis", fileYaxis.replace("/tmp/", ""));
	                     mapImages.put("height", ""+BI_HEIGHT);
	                     mapImages.put("width", ""+BI_WIDTH);
	                       if (ImageIO.write(bImage, "png", new File(fileName)))
	                       {
	                           System.out.println("-- saved" +  bImage.getWidth());
	                       }
	                       if (ImageIO.write(bXaxis, "png", new File(fileXaxis)))
	                       {
	                           System.out.println("-- saved" +  fileXaxis);
	                       }
	                       if (ImageIO.write(bYaxis, "png", new File(fileYaxis)))
	                       {
	                           System.out.println("-- saved" +  fileName);
	                       }
	                       
	                       
	               } catch (IOException e) {
	                       // TODO Auto-generated catch block
	                       e.printStackTrace();
	               }
	                
	                
	               
	                
	                
	               
	    System.out.println("-- saved" );      
	    return mapImages;
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
	

}


