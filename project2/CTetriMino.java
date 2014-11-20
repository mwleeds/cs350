// File: CTetriMino.java
// Author: Matthew Leeds
// Last Edit: 10.06.2014
// Purpose: This class defines the 7 types of shapes.

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.*;
import java.lang.Math;

public class CTetriMino {
	
	private int _type; // shape type, 0-6
	private int _x;
	private int _y;
	private int _width;
	private int _height;
	private Color _color;
	// a 2D array showing whether each square in the grid should have a circle
	private Boolean[][] _shapeConfiguration = null;
	// rotation in degrees
    private int _rotateDegrees;
	
	public int getX() {	return _x;	}
	
	public void setX(int x) { _x = x; }
	
	public int getY() {	return _y;	}
	
	public void setY(int y) { _y = y; }
	
	public int getWidth() { return _width; }
	
	public int getHeight() { return _height; }
	
	public int getType() { return _type; }
	
	public Color getColor() { return _color; }
	
	public Boolean[][] getShapeConfiguration() { return _shapeConfiguration; }
	
	public int getRotateDegrees() { return _rotateDegrees; }
	
	public void setRotateDegrees(int rotateDegrees) { _rotateDegrees = rotateDegrees;}
	
	public CTetriMino(int x, int y, int type) {
		_x = x;
		_y = y;
		_type = type;
		_rotateDegrees = 0;
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
		_rotateDegrees = src.getRotateDegrees();
	}
	
	public void draw(Graphics2D g2d) {
		//Graphics2D g2d = (Graphics2D)g;
		AffineTransform old = g2d.getTransform();
		AffineTransform rotation = new AffineTransform();
		rotation.rotate(Math.toRadians(_rotateDegrees), _x + (_width*25)/2, _y + (_height*25)/2);
		g2d.setTransform(rotation);
		for (int i = 0; i < _height; i++) {
			for (int j = 0; j < _width; j++) {
				if (_shapeConfiguration[i][j]) {
					CMino circle = new CMino(_x + (j * 25), _y + (i * 25), _color);
					circle.draw(g2d);
				}
			}
		}
		g2d.setTransform(old);
	}

    public boolean containsPoint(int x, int y) {
        boolean pointInShape = false; // true if (x,y) is in the shape
        double rotateRadians = Math.toRadians(_rotateDegrees);
		for (int i = 0; i < _height; i++) {
			for (int j = 0; j < _width; j++) {
				if (_shapeConfiguration[i][j] && !pointInShape) {
                    double r = 25/2.0;
                    double xc = (_x + (j * 25)) + r;
                    double yc = (_y + (i * 25)) + r;
                    System.out.println("old center: " + new Double(xc).toString() + " " + new Double(yc).toString());
                    // compensate for rotation
                    if (_rotateDegrees != 0) {
                    	xc = _x + (xc - _x) * Math.cos(rotateRadians) - (yc - _y) * Math.sin(rotateRadians);
                    	yc = _y + (xc - _x) * Math.sin(rotateRadians) + (yc - _y) * Math.cos(rotateRadians);
                    	System.out.println("new center: " + new Double(xc).toString() + " " + new Double(yc).toString());
                    }
                    // use the distance formula to determine if (x,y) is within the current circle
                    pointInShape = (Math.pow(x - xc, 2) / Math.pow(r, 2) + Math.pow(y - yc, 2) / Math.pow(r, 2) <= 1.0);
                }
            }
        }
        return pointInShape;
    }
    
    // if (x,y) is within the shape, it returns the Mino's center (x,y)
    public int[] findCenter(int x, int y) {
    	int[] circleCenter = {0, 0};
    	for (int i = 0; i < _height; i++) {
    		for (int j = 0; j < _width; j++) {
    			if (_shapeConfiguration[i][j]) {
    				double r = 25/2.0;
    				double xc = (_x + (j * 25)) + r;
    				double yc = (_y + (i * 25)) + r;
    				if (Math.pow(x - xc, 2) / Math.pow(r, 2) + Math.pow(y - yc, 2) / Math.pow(r, 2) <= 1.0) {
    					circleCenter[0] = (int)xc;
    					circleCenter[1] = (int)yc;
    				}
    			}
    		}
    	}
    	return circleCenter;
    }
    
    public int[] getShapeCenter() {
    	return new int[]{(_x + ((_width * 25)/2)), (_y + ((_height * 25)/2))};
    }
}
