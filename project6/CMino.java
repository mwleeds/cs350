// File: CMino.java
// Author: Matthew Leeds
// Last Edit: 10.06.2014
// Purpose: This class defines a circle that is used for the tetris shapes.

import java.awt.Color;
import java.awt.Graphics2D;

public class CMino {

	private int _x;
	private int _y;
	private int _diameter = 25;
	private Color _color = Color.black;

    // constructor for using the default color	
	public CMino(int x, int y) {
		_x = x;
		_y = y;
	}
	
	public CMino(int x, int y, Color color) {
		_x = x;
		_y = y;
		_color = color;
	}
	
	public void draw(Graphics2D g2d) {
		g2d.setColor(Color.black);
		g2d.drawOval(_x, _y, _diameter, _diameter);
		g2d.setColor(_color);
		g2d.fillOval(_x, _y, _diameter, _diameter);
	}
	
}