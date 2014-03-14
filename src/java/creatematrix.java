/*	int total = 0 ;
			int row = concepts.size()+2; // for total and compounds
			int col = compList.size(); // for concpets name
			
			Object [][] board = new Object[row][col];
			System.out.println("matrix no of rows"+board.length + "size of hashmap" + conList.size() +    "Size of col" + compList.get(5).getName());
			//Running for each compound
			for (int i =0 ; i < col; i++)
			{
				int countComp = i-1;
				int j = 0;
			//	System.out.println("Loop started with i " + i + " j =" +  j);
				int count = 0;
				if(i == 0 && j == 0)
				{
					board[j][i] =0;
				//	System.out.println(" [0,0] i " + i + " j =" +  j);
					j++;
					
					
				}
				else if(i > 0 && j == 0)//Addition for compound name
				{
					
				//	System.out.println("[ " + j  +" ,"+  i + " ] ="+ "Added column Name: " + compList.get(countComp).getName());
					
					board[j][i] = compList.get(countComp).getName();//Add compound name in first row starting from second column
					j++;
				}
				
						
				
						 for (Entry<String, ArrayList<String>> entry : conList.entrySet())
							
						 {
							//First row is  name of the concept
							 String key = entry.getKey().toString();
							 ArrayList<String> no = entry.getValue();
							
							 System.out.println("for loop  " +countComp+"Key is " + key);
							 
							 //For fist columns start adding names of concepts
							 if(i==0)
							 {
								 board[j][i]=key;
							//	 System.out.println(" [ " + j + " i =" +  i + " ]   key " + key);
								 
							 }
							 else
							 {  
								 if(key.contains("glucosidase activity"))
								 {
									 System.out.println("inside glucosidase activity  with check for "  );
									 if(no.contains(compList.get(countComp).getName()))
									 {
										System.out.println(j + " "+ i);
									 }
									 
								 }
								 
								 if(no.contains(compList.get(i).getName()))
								 {
									count++;	
									total++;
									board[j][i] = 1;
								//	 System.out.println("[" + j + "," + i +"]" + "for 1");
								 }
								 else
								 {
									 board[j][i] = 0;
								//	 System.out.println("[" + j + "," + i +"]" + "for 0");
								 }
								// System.out.println("for compound  " +compList.get(countComp).getName() + "  count is  " +  count + "j here is " + j);
							
							 }//else
							 j++;
						 }//For loop
						
							board[j][i]=count;
				
			
				 
				 
			}
			
			 System.out.println("Newly formed leength is"+board[5][1]);
			 
		/*	board= createClusteredMatrix(board);
			
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
