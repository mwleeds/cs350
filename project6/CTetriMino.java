// File: CTetriMino.java
// Author: Matthew Leeds and CJ Guttormsson
// Last Edit: 10.06.2014
// Purpose: This class defines the 7 types of shapes.

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.*;
import java.io.Serializable;
import java.lang.Math;

public class CTetriMino implements Serializable{
	
	private int _type; // shape type, 0-6
	private int _x;
	private int _y;
	private Color _color;
	// a 2D array showing whether each square in the grid should have a circle
	private Boolean[][] _shapeConfiguration = null;
	// rotation in degrees
	
	public int getX() {	return _x;	}
	
	public void setX(int x) { _x = x; }
	
	public int getY() {	return _y;	}
	
	public void setY(int y) { _y = y; }
	
	public int getWidth() { return _shapeConfiguration.length; }
	
	public int getHeight() { return _shapeConfiguration[0].length; }
	
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
			_shapeConfiguration = new Boolean[][]{{true,true,false},{true,true,false}};
			_color = new Color(255,255,0); // yellow
			break;
		}
		case 1: {
			// OOOO
			_shapeConfiguration = new Boolean[][]{{true,true,true,true}};
			_color = new Color(255,200,0); // orange
			break;
		}
		case 2: {
			//  O
			// OOO
			_shapeConfiguration = new Boolean[][]{{false,true,false},{true,true,true}};
			_color = new Color(0,0,255); // blue
			break;
		}
		case 3: {
			//   O
			// OOO
			_shapeConfiguration = new Boolean[][]{{false,false,true},{true,true,true}};
			_color = new Color(255,0,255); // light purple
			break;
		}
		case 4: {
			// O  
			// OOO
			_shapeConfiguration = new Boolean[][]{{true,false,false},{true,true,true}};
			_color = new Color(0,255,255); // light blue
			break;
		}
		case 5: {
			//  OO
			// OO
			_shapeConfiguration = new Boolean[][]{{false,true,true},{true,true,false}};
			_color = new Color(0,255,0); // green
			break;
		}
		case 6: {
			// OO
			//  OO
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
//		_width = src.getWidth();
//		_height = src.getHeight();
		_color = src.getColor();
		_shapeConfiguration = src.getShapeConfiguration();
	}
	
	public void draw(Graphics2D g2d) {
		//Graphics2D g2d = (Graphics2D)g;
//		AffineTransform old = g2d.getTransform();
//		AffineTransform rotation = new AffineTransform();
//		rotation.rotate(Math.toRadians(_rotateDegrees), _x + (_width*25)/2, _y + (_height*25)/2);
//		g2d.setTransform(rotation);
		for (int i = 0; i < _shapeConfiguration.length; i++) {
			for (int j = 0; j < _shapeConfiguration[0].length; j++) {
				if (_shapeConfiguration[i][j]) {
					CMino circle = new CMino(_x + (j * 25), _y + (i * 25), _color);
					circle.draw(g2d);
				}
			}
		}
//		g2d.setTransform(old);
	}

    public boolean containsPoint(int x, int y) {
        boolean pointInShape = false; // true if (x,y) is in the shape
		for (int i = 0; i < _shapeConfiguration.length; i++) {
			for (int j = 0; j < _shapeConfiguration[0].length; j++) {
				if (_shapeConfiguration[i][j] && !pointInShape) {
                    double r = 25/2.0;
                    double xc = (_x + (j * 25)) + r;
                    double yc = (_y + (i * 25)) + r;
                    //System.out.println("old center: " + new Double(xc).toString() + " " + new Double(yc).toString());
                    // use the distance formula to determine if (x,y) is within the current circle
                    pointInShape = (Math.pow(x - xc, 2) / Math.pow(r, 2) + Math.pow(y - yc, 2) / Math.pow(r, 2) <= 1.0);
                    if (pointInShape) {
                    	//System.out.printf("Determined in shape with %d %d\n", i, j);
                    }
                }
            }
        }
		if (pointInShape) {
			//System.out.println("Point was in shape.");
		} else {
			//System.out.println("Point NOT in shape");
		}
        return pointInShape;
    }
    
    //Figure out which sub mino was clicked
    public int[] getSubMino(int x, int y) {
        boolean pointInShape = false; // true if (x,y) is in the shape
		for (int i = 0; i < _shapeConfiguration.length; i++) {
			for (int j = 0; j < _shapeConfiguration[0].length; j++) {
				if (_shapeConfiguration[i][j] && !pointInShape) {
                    double r = 25/2.0;
                    double xc = (_x + (j * 25)) + r;
                    double yc = (_y + (i * 25)) + r;
                    //System.out.println("old center: " + new Double(xc).toString() + " " + new Double(yc).toString());
                    // use the distance formula to determine if (x,y) is within the current circle
                    pointInShape = (Math.pow(x - xc, 2) / Math.pow(r, 2) + Math.pow(y - yc, 2) / Math.pow(r, 2) <= 1.0);
                    if (pointInShape) {
                    	//System.out.printf("Determined in shape with %d %d\n", i, j);
                    	return new int[]{i,j};
                    }
                }
            }
        }
		return new int[]{-2,-2};
    }
    
    
    //Spin the tertimino 90 degrees counter clockwise
    public void rotate(int my_x, int my_y) {
    	int initialX = _shapeConfiguration.length;
    	int initialY = _shapeConfiguration[0].length;
    	int[] offsets = getSubMino(my_x, my_y);
    	
    	//Create temp array with inverted lengths (x->y, y->x)
    	Boolean[][] tempConfig = new Boolean[initialY][initialX];
    	
    	//Map old points to new ones
    	for (int i = 0; i < initialX; i++) {
    		for (int j = 0; j < initialY; j++) {
    			tempConfig[j][initialX - 1 - i] = _shapeConfiguration[i][j];
    		}
    	}
    	
    	_shapeConfiguration = tempConfig;
    	
    	//Offset x and y
    	//System.out.printf("%d %d\n", offsets[0], offsets[1]);
    	int offsetY = offsets[1] - offsets[0];
    	int offsetX = initialX - 1 - offsets[0] - offsets[1];
    	
    	_x -= offsetX * 25;
    	_y -= offsetY * 25;
    	
    }
    
    // if (x,y) is within the shape, it returns the Mino's center (x,y)
    public int[] findCenter(int x, int y) {
    	int[] circleCenter = {0, 0};
    	for (int i = 0; i < _shapeConfiguration[0].length; i++) {
    		for (int j = 0; j < _shapeConfiguration.length; j++) {
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
    	return new int[]{(_x + ((_shapeConfiguration.length * 25)/2)), (_y + ((_shapeConfiguration[0].length * 25)/2))};
    }
}