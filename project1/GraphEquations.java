// File: GraphEquations.java
// Author: Matthew Leeds
// Last Edit: 9.17.2014
// Purpose: Draw various equations  in a window.
// For: CS 350, Project #1, Matthew Leeds

import java.io.*;
import java.util.*;
import javax.swing.JFrame;

public class GraphEquations {
	
	public static void main(String[] args) {
		// there should be a "curve.txt" file with two integers
		// on separate lines denoting which equation to graph
		// using how many line segments
		int equation = 1;
		int resolution = 10;
		Scanner input;
		try {
			input = new Scanner(new File("curve.txt"));
			equation = input.nextInt();
			resolution = input.nextInt();
		}
		catch(IOException e) {
			System.err.println(e);
			System.exit(1);
		}
		
		Graph panel = new Graph(equation, resolution);
		JFrame application = new JFrame("Math!");
		application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		application.add(panel);
		application.setSize(600,600);
		application.setVisible(true);
	}
}
