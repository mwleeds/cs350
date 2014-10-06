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
	
	public int getY() {	return _y;	}
	
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
			_width = 2;
			_height = 2;
			_shapeConfiguration = new Boolean[][]{{true,true},{true,true}};
			_color = new Color(255,255,0); // yellow
			break;
		}
		case 1: {
			_width = 4;
			_height = 1;
			_shapeConfiguration = new Boolean[][]{{true,true,true,true}};
			_color = new Color(255,200,0); // orange
			break;
		}
		case 2: {
			_width = 3;
			_height = 2;
			_shapeConfiguration = new Boolean[][]{{false,true,false},{true,true,true}};
			_color = new Color(0,0,255); // blue
			break;
		}
		case 3: {
			_width = 3;
			_height = 2;
			_shapeConfiguration = new Boolean[][]{{false,false,true},{true,true,true}};
			_color = new Color(255,0,255); // light purple
			break;
		}
		case 4: {
			_width = 3;
			_height = 2;
			_shapeConfiguration = new Boolean[][]{{true,false,false},{true,true,true}};
			_color = new Color(0,255,255); // light blue
			break;
		}
		case 5: {
			_width = 3;
			_height = 2;
			_shapeConfiguration = new Boolean[][]{{false,true,true},{true,true,false}};
			_color = new Color(0,255,0); // green
			break;
		}
		case 6: {
			_width = 3;
			_height = 2;
			_shapeConfiguration = new Boolean[][]{{true,true,false},{false,true,true}};
			_color = new Color(255,0,0); // red
			break;
		}
		}
	}
	
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

}
