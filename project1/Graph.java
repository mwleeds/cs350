// File: Graph.java
// Last Edit: 9.17.2014
// Author: Matthew Leeds
// Purpose: Class used to draw a graph that can be used
// to draw a variety of functions.

import java.awt.*;
import javax.swing.JPanel;

public class Graph extends JPanel {
	private int equation;
	private int resolution;
	
	// set the equation to be drawn and number of line 
	// segments to use in the constructor
	public Graph(int inEquation, int inResolution) {
		equation = inEquation;
		resolution = inResolution;
	}
	
	// draws the graph and the chosen equation onto it
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.black);
		int xMargin = 20; // horizontal margin from edge of frame
		int yMargin = 40; // vertical margin from top of frame
		int xAxisOffset = 210; // vertical distance between end of margin and x axis
		int yAxisOffset = 10; // horizontal distance between end of margin and y axis
		int xAxisLength = 220; // horizontal length of x axis
		int yAxisLength = 420; // vertical length of y axis
		// y axis
		g.drawLine(xMargin + yAxisOffset, yMargin, xMargin + yAxisOffset, yMargin + yAxisLength); 
		// y axis tick marks
		for (int i = 0; i <= yAxisLength - 20; i++) {
			if (i % 100 == 0) {
				g.drawLine(xMargin + yAxisOffset - 3, yMargin + 10 + i, xMargin + yAxisOffset + 3, yMargin + 10 + i);
			}
		}
		// y axis labels
		g.drawString("1.0", xMargin + yAxisOffset - 20, yMargin + 10);
		g.drawString("0.5", xMargin + yAxisOffset - 20, yMargin + 110);
		g.drawString("-0.5", xMargin + yAxisOffset - 24, yMargin + 310);
		g.drawString("-1.0", xMargin + yAxisOffset - 24, yMargin + 410);
		// x axis
		g.drawLine(xMargin, yMargin + xAxisOffset, xMargin + xAxisLength, yMargin + xAxisOffset);
		// x axis tick marks and labels
		for (int i = 0; i <= xAxisLength - 20; i++) {
			if (i % 20 == 0 && i != 0) {
				g.drawLine(xMargin + 10 + i, yMargin + xAxisOffset - 3, xMargin + 10 + i, yMargin + xAxisOffset + 3);
				g.drawString(Float.toString((float) i / 200), xMargin + i + 7, yMargin + xAxisOffset + 15);
			}
		}
		// 0 label
		g.drawString("0", xMargin + yAxisOffset - 7, yMargin + xAxisOffset + 12);
		// draw the desired curve
		float startX = 0; // starting x coordinate
		float currentX = startX; // current x coordinate
		float currentY; // current y coordinate
		float nextX; // to hold calculated x values
		float nextY; // to hold calculated y values
		float endX = 1; // ending x coordinate
		float step = (endX - startX) / resolution; // delta X for each step
		for (int j = 0; j < resolution; j++) {
			switch(equation) {
				case 1: // draw y = 1-x
					currentY = 1 - currentX;
					nextX = currentX + step;
					nextY = 1 - nextX;
					// multiply numbers by 200 to convert to pixels
					g.drawLine((int) ((currentX * 200) + xMargin + yAxisOffset), 
							   (int) (yMargin + xAxisOffset - (currentY * 200)), 
							   (int) ((nextX * 200) + xMargin + yAxisOffset), 
							   (int) (yMargin + xAxisOffset - (nextY * 200)));
					currentX = nextX;
					break;
				case 2: // draw y = e^(-0.25x)
					currentY = (float) Math.exp(-0.25 * currentX);
					nextX = currentX + step;
					nextY = (float) Math.exp(-0.25 * nextX);
					g.drawLine((int) ((currentX * 200) + xMargin + yAxisOffset), 
							   (int) (yMargin + xAxisOffset - (currentY * 200)), 
							   (int) ((nextX * 200) + xMargin + yAxisOffset), 
							   (int) (yMargin + xAxisOffset - (nextY * 200)));
					currentX = nextX;
					break;
				case 3: // draw y = e^(-0.5x)
					currentY = (float) Math.exp(-0.5 * currentX);
					nextX = currentX + step;
					nextY = (float) Math.exp(-0.5 * nextX);
					g.drawLine((int) ((currentX * 200) + xMargin + yAxisOffset), 
							   (int) (yMargin + xAxisOffset - (currentY * 200)), 
							   (int) ((nextX * 200) + xMargin + yAxisOffset), 
							   (int) (yMargin + xAxisOffset - (nextY * 200)));
					currentX = nextX;
					break;
				case 4: // draw y = e^(-0.75x)
					currentY = (float) Math.exp(-0.75 * currentX);
					nextX = currentX + step;
					nextY = (float) Math.exp(-0.75 * nextX);
					g.drawLine((int) ((currentX * 200) + xMargin + yAxisOffset), 
							   (int) (yMargin + xAxisOffset - (currentY * 200)), 
							   (int) ((nextX * 200) + xMargin + yAxisOffset), 
							   (int) (yMargin + xAxisOffset - (nextY * 200)));
					currentX = nextX;
					break;
				case 5: // draw y = e^(-x)
					currentY = (float) Math.exp(-1 * currentX);
					nextX = currentX + step;
					nextY = (float) Math.exp(-1 * nextX);
					g.drawLine((int) ((currentX * 200) + xMargin + yAxisOffset), 
							   (int) (yMargin + xAxisOffset - (currentY * 200)), 
							   (int) ((nextX * 200) + xMargin + yAxisOffset), 
							   (int) (yMargin + xAxisOffset - (nextY * 200)));
					currentX = nextX;
					break;
			}
		}
	}
}