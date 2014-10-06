// File: CTetriMino.java
// Author: Matthew Leeds
// Last Edit: 10.06.2014
// Purpose: This class defines the 7 types of shapes.

import java.awt.Color;
import java.awt.Graphics;
import java.util.*;

public class CTetriMino {
	
	private int _type; // shape type, 0-6
	private int _x;
	private int _y;
	private int _width;
	private int _height;
	private Color _color;
	// a 2D array showing whether each square in the grid should have a circle
	private Boolean[][] _shapeConfiguration = null;
	
	public int getX() {	return _x;	}
	
	public void setX(int x) { _x = x; }
	
	public int getY() {	return _y;	}
	
	public void setY(int y) { _y = y; }
	
	public int getWidth() { return _width; }
	
	public int getHeight() { return _height; }
	
	public int getType() { return _type; }
	
	public Color getColor() { return _color; }
	
	public Boolean[][] getShapeConfiguration() { return _shapeConfiguration; }
	
	public CTetriMino(int x, int y, int type) {
		_x = x;
		_y = y;
		_type = type;
		switch (type) {
		case 0: {
			// OO
			// OO
			_width = 2;
			_height = 2;
			_shapeConfiguration = new Boolean[][]{{true,true},{true,true}};
			_color = new Color(255,255,0); // yellow
			break;
		}
		case 1: {
			// OOOO
			_width = 4;
			_height = 1;
			_shapeConfiguration = new Boolean[][]{{true,true,true,true}};
			_color = new Color(255,200,0); // orange
			break;
		}
		case 2: {
			//  O
			// OOO
			_width = 3;
			_height = 2;
			_shapeConfiguration = new Boolean[][]{{false,true,false},{true,true,true}};
			_color = new Color(0,0,255); // blue
			break;
		}
		case 3: {
			//   O
			// OOO
			_width = 3;
			_height = 2;
			_shapeConfiguration = new Boolean[][]{{false,false,true},{true,true,true}};
			_color = new Color(255,0,255); // light purple
			break;
		}
		case 4: {
			// O  
			// OOO
			_width = 3;
			_height = 2;
			_shapeConfiguration = new Boolean[][]{{true,false,false},{true,true,true}};
			_color = new Color(0,255,255); // light blue
			break;
		}
		case 5: {
			//  OO
			// OO
			_width = 3;
			_height = 2;
			_shapeConfiguration = new Boolean[][]{{false,true,true},{true,true,false}};
			_color = new Color(0,255,0); // green
			break;
		}
		case 6: {
			// OO
			//  OO
			_width = 3;
			_height = 2;
			_shapeConfiguration = new Boolean[][]{{true,true,false},{false,true,true}};
			_color = new Color(255,0,0); // red
			break;
		}
		}
	}
	
    // copy constructor
	public CTetriMino(CTetriMino src) {
		_type = src.getType();
		_x = src.getX();
		_y = src.getY();
		_width = src.getWidth();
		_height = src.getHeight();
		_color = src.getColor();
		_shapeConfiguration = src.getShapeConfiguration();
	}
	
	public void draw(Graphics g) {
		for (int i = 0; i < _height; i++) {
			for (int j = 0; j < _width; j++) {
				if (_shapeConfiguration[i][j]) {
					CMino circle = new CMino(_x + (j * 25), _y + (i * 25), _color);
					circle.draw(g);
				}
			}
		}
	}

    public boolean containsPoint(int x, int y) {
        boolean pointInShape = false; // true if (x,y) is in the shape
		for (int i = 0; i < _height; i++) {
			for (int j = 0; j < _width; j++) {
				if (_shapeConfiguration[i][j] && !pointInShape) {
                    double r = 25/2.0;
                    double xc = (_x + (j * 25)) + r;
                    double yc = (_y + (i * 25)) + r;
                    // use the distance formula to determine if (x,y) is within the current circle
                    pointInShape = ((x - xc)*(x - xc)/(r * r) + (y - yc)*(y - yc)/(r * r) <= 1.0);
                }
            }
        }
        return pointInShape;
    }
}
