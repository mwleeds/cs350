// File: TetrisPanel.java
// Author: Matthew Leeds
// Last Edit: 10.06.2014
// Purpose: This class defines the panel and draws the shapes on it.

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class TetrisPanel extends JPanel implements MouseListener, MouseMotionListener {

	// lists of the shapes currently on screen
    private ArrayList<CTetriMino> originals;
    private ArrayList<CTetriMino> duplicates;
    // double buffering
    private Image backBuffer;
    private Graphics gBackBuffer;
    // has init() been called?
    private boolean isInitialized;
    // used when a shape is selected (dragged)
    private CTetriMino ShapeToBeMoved;
    // offsets from top left corner at start of drag
    private int dragOffsetX;
    private int dragOffsetY;
    
	public TetrisPanel() {
		isInitialized = false;
		//setBackground(Color.white);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
	}
	
	void init() {
		originals = new ArrayList<CTetriMino>();
        duplicates = new ArrayList<CTetriMino>();
        ShapeToBeMoved = null;
        backBuffer = createImage(800, 600);
        gBackBuffer = backBuffer.getGraphics();
        // fill the originals array with the 7 shapes
        originals.add(new CTetriMino(25, 25, 0));
        originals.add(new CTetriMino(100, 50, 1));
        originals.add(new CTetriMino(225, 25, 2));
        originals.add(new CTetriMino(325, 25, 3));
        originals.add(new CTetriMino(425, 25, 4));
        originals.add(new CTetriMino(525, 25, 5));
        originals.add(new CTetriMino(625, 25, 6));
	}
	
	public void paintComponent(Graphics g) {
		if (!isInitialized) {
			init();
			isInitialized = true;
		}
		// clear the back buffer
		gBackBuffer.setColor(Color.white);
		gBackBuffer.clearRect(0, 0, 800, 600);
		// set up the background
		gBackBuffer.setColor(Color.black);
		gBackBuffer.drawString("Drag shapes from here to build things.", 250, 20);
		gBackBuffer.drawLine(0, 100, 800, 100);
		gBackBuffer.drawLine(0, 500, 800, 500);
		gBackBuffer.setColor(Color.gray);
		gBackBuffer.fillRect(0, 500, 800, 100);
		gBackBuffer.setColor(Color.white);
		gBackBuffer.drawString("Drag shapes here to delete them.", 300, 550);
		// draw originals
		for (int i = 0; i < originals.size(); i++) {
			originals.get(i).draw(gBackBuffer);
		}
		// draw duplicates
		for (int i = 0; i < duplicates.size(); i++) {
			duplicates.get(i).draw(gBackBuffer);
		}
		// copy back buffer into view
		g.drawImage(backBuffer, 0, 0, null);
	}

	public void mouseDragged(MouseEvent e) {
		if (e.isMetaDown()) return; // ignore right click
		if (ShapeToBeMoved != null) {
			ShapeToBeMoved.setX(e.getX() - dragOffsetX);
			ShapeToBeMoved.setY(e.getY() - dragOffsetY);
			repaint();
		}
	}

	public void mouseClicked(MouseEvent e) {
		/*if (e.isMetaDown()) { // right click
			// TODO: implement rotate
		}*/
		
	}

	public void mousePressed(MouseEvent e) {
		if (e.isMetaDown()) return; // ignore right click
		// figure out which shape was selected
		// check the duplicates from front to back
		for (int i = duplicates.size() - 1; i >= 0; i--) {
			CTetriMino currentShape = duplicates.get(i);
			if (currentShape.containsPoint(e.getX(), e.getY())) {
				// move to front
				duplicates.remove(i);
				duplicates.add(currentShape);
				ShapeToBeMoved = currentShape;
				dragOffsetX = e.getX() - ShapeToBeMoved.getX();
				dragOffsetY = e.getY() - ShapeToBeMoved.getY();
				repaint();
				return;
			}
		}
		// check the originals from front to back
		for (int i = originals.size() - 1; i >= 0; i--) {
			CTetriMino currentShape = originals.get(i);
			if (currentShape.containsPoint(e.getX(), e.getY())) {
				CTetriMino currentShapeCopy = new CTetriMino(currentShape);
				duplicates.add(currentShapeCopy);
				ShapeToBeMoved = currentShapeCopy;
				dragOffsetX = e.getX() - ShapeToBeMoved.getX();
				dragOffsetY = e.getY() - ShapeToBeMoved.getY();
				repaint();
				return;
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		ShapeToBeMoved = null;
	}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}

	public void mouseMoved(MouseEvent e) {}

}
