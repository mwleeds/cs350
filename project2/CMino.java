import java.awt.Color;
import java.awt.Graphics;

public class CMino {

	private int _x;
	private int _y;
	private int _diameter = 25;
	private Color _color = Color.black;
	
	public CMino(int x, int y) {
		_x = x;
		_y = y;
	}
	
	public CMino(int x, int y, Color color) {
		_x = x;
		_y = y;
		_color = color;
	}
	
	public CMino(int x, int y, int diameter, Color color) {
		_x = x;
		_y = y;
		_diameter = diameter;
		_color = color;
	}

	public void draw(Graphics g) {
		g.setColor(Color.black);
		g.drawOval(_x, _y, _diameter, _diameter);
		g.setColor(_color);
		g.fillOval(_x, _y, _diameter, _diameter);
	}
	
}
