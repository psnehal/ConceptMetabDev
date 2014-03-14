package org.conceptmetab.java;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
/*
public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				// the colored square class
				class ColoredSquare {
					Color color;
					Rectangle rect;
					int width;
					int height;

					public ColoredSquare(Color c, int x, int y) {
						color = c;
						width = 32;
						height = 32;
						rect = new Rectangle(x, y, width, height);
					}

					public void draw(Graphics g) {
						g.setColor(color);
						g.fillRect(rect.x, rect.y, width, height);
					}
				}// end ColoredSquare class.

				JPanel panel = null;
				try {
					panel = new JPanel() {

						int id = 10;
						JDBCConnectionTest jbc = new JDBCConnectionTest();
						HashMap<Integer, ArrayList<Integer>> conList = jbc
								.createHashmap(id);
						ArrayList<Concepts> cmp = jbc.getConcept(id);
						ArrayList<Compounds> compList = jbc
								.getConceptOfInterstComp(id);
						ColoredSquare[][] squares;
						int squareSize;
						{
							// System.out.println("compList.size() = "+
							// compList.size() + "cmp.length" + cmp.length);
							// squares = new
							// ColoredSquare[compList.size()][cmp.length];
							squareSize = 30; // 32 with x 32 height for each
												// square.
							setBackground(Color.black);
							setForeground(Color.black);
							setPreferredSize(new Dimension(600, 600));
							populateSquares();
						}

						private void populateSquares()
								throws ClassNotFoundException, SQLException {

							int row = 0;
							// squares.length == is compound list
							// squares[0].length = concept list
							System.out
									.println("squares.length or compound list"
											+ squares.length);
							for (int col = 0; col < squares.length; col++) { // each
																				// column.
								// System.out.println("squares[0].length.length"
								// +squares[0].length );
								int count2 = 0;
								// System.out.println("For compound : "+compList.get(col)+
								// "  This is for height" + conList.size());
								for (ArrayList<Integer> no : conList.values()) {

									if (no.contains(compList.get(col))) {
										// System.out.println(new
										// Color(255,255,224)+ "  " +col *
										// squareSize+ "  " + row * squareSize);
										squares[col][row] = new ColoredSquare(
												new Color(255, 255, 224), col
														* squareSize, row
														* squareSize);
										count2++;

									} else {
										squares[col][row] = new ColoredSquare(
												new Color(0, 0, 0), col
														* squareSize, row
														* squareSize);
										// System.out.println("From no");

									}
									row++;
								}

								row = 0;
								System.out.println("Count for "
										+ compList.get(col) + " compound is"
										+ count2);

							}

						}

						private void paintSquares(Graphics g) {
							for (int col = 0; col < squares.length; col++) { // each
																				// column.
								for (int row = 0; row < squares[0].length; row++) { // each
																					// row.
									squares[col][row].draw(g);
								}
							}
						}

						public void paintComponent(Graphics g) {
							super.paintComponent(g);
							paintSquares(g);

						}
					};
				} catch (SQLException e) {
					e.printStackTrace();
				}// end jpanel

				// the main window
				JFrame frame = new JFrame("Colored squares.");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.add(panel);
				frame.setResizable(false);
				frame.pack();
				frame.setVisible(true);
			}
		});// end invokeLater()
	}// end main();
}// end class.

*/