import java.awt.*;
import javax.swing.*;

public class TetrisPanel extends JPanel {

	public TetrisPanel() {
		setBackground(Color.white);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.black);
		g.drawString("Drag shapes from here to build things.", 250, 20);
		g.drawLine(0, 100, 800, 100);
		g.drawLine(0, 500, 800, 500);
		g.setColor(Color.gray);
		g.fillRect(0, 500, 800, 100);
		g.setColor(Color.white);
		g.drawString("Drag shapes here to delete them.", 300, 550);
		//CMino circle = new CMino(100, 25, Color.red);
		//circle.draw(g);
		//CTetriMino shape = new CTetriMino(100, 100, 6);
		//shape.draw(g);
	}

}
