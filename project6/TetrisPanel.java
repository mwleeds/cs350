// File: TetrisPanel.java
// Author: Matthew Leeds and CJ Guttormsson
// Last Edit: 11.25.2014
// Purpose: This class defines the panel and draws the shapes on it.

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.lang.Math;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class TetrisPanel extends JPanel implements MouseListener, MouseMotionListener {

	// lists of the shapes currently on screen
    private ArrayList<CTetriMino> originals;
    private ArrayList<CTetriMino> duplicates;
    // double buffering
    private Image backBuffer;
    private Graphics2D gBackBuffer;
    // has init() been called?
    private boolean isInitialized;
    // used when a shape is selected (dragged)
    private CTetriMino ShapeToBeMoved;
    // offsets from top left corner at start of drag
    private int dragOffsetX;
    private int dragOffsetY;
    // networking: server or client?
    private boolean isServer;        
    // networking: server hostname or IP
    private String serverHostname;
    // networking: data streams
    private ObjectOutputStream output;
    private ObjectInputStream input;
    // networking: sockets
    private Socket connection;
    private ServerSocket server;
    
    private JFrame parentFrame;
    
	public TetrisPanel(JFrame parent, String hostname) {
        parentFrame = parent;
		serverHostname = hostname;
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
        gBackBuffer = (Graphics2D) backBuffer.getGraphics();
        // fill the originals array with the 7 shapes
        originals.add(new CTetriMino(25, 25, 0));
        originals.add(new CTetriMino(100, 50, 1));
        originals.add(new CTetriMino(225, 25, 2));
        originals.add(new CTetriMino(325, 25, 3));
        originals.add(new CTetriMino(425, 25, 4));
        originals.add(new CTetriMino(525, 25, 5));
        originals.add(new CTetriMino(625, 25, 6));
	}
    
    // set up and run server or client
    public void runNetworkService() {
    	Object[] options = {"Server", "Client"};
        int choice = JOptionPane.showOptionDialog(this, "Would you like to run in server or client mode?", "Choose Mode",
                                                  JOptionPane.YES_NO_OPTION,
                                                  JOptionPane.QUESTION_MESSAGE,
                                                  null,
                                                  options,
                                                  options[1]);
        System.out.println((choice==0)?"Server":"Client");
        isServer = (choice==0); // If choice is 0 (Server), then isServer is true; else false
        
        if (isServer) { // server
        	parentFrame.setTitle("NewTetris - Server"); 
	        try {
	            server = new ServerSocket(12345, 100);
	            // continuously accept connections
	            do {
	                try {
	                    waitForConnection();
	                    getStreams();
	                    processConnection();
	                } catch (EOFException eofException) {	
	                    System.out.println("Connection terminated!");
	                } finally {
	                    closeConnection();
	                }
	            } while (true);
	        } catch (IOException ioException) {
	            ioException.printStackTrace();
	        }
        } else { // client
        	parentFrame.setTitle("NewTetris - Client");
        	try {
	            connectToServer();
	            getStreams();
	            processConnection();
	        } catch (EOFException eofException) {	
	            System.out.println("Connection terminated!");
        	} catch (IOException ioException) {
        		ioException.printStackTrace();
        	} finally {
	            closeConnection();
	        }
        }
    }
   
    // wait for connection to arrive  
    private void waitForConnection() throws IOException {
        connection = server.accept();
        System.out.println("Connection received from:" + 
        				   connection.getInetAddress().getHostName());
    }
    
    // connect to server 
    private void connectToServer() throws IOException {
        connection = new Socket(InetAddress.getByName(serverHostname), 12345); 
        //System.out.println("Connected to Server!");
    }
            
    // get streams to send and receive data
    private void getStreams() throws IOException {
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();
        input = new ObjectInputStream(connection.getInputStream());
        System.out.println("I/O Streams acquired.");
    }
   
    // process data from connection
    private void processConnection() throws IOException {
    	System.out.println("Connection successful!");
    	ArrayList<CTetriMino> temporary;
    	
    	while (true) {
    		try {
    			
    			temporary = ( ArrayList<CTetriMino> ) input.readObject();
    			System.out.println("Received Something.");
    			duplicates = temporary;
    			repaint();
    			
    		} catch (ClassNotFoundException classnotfoundException) {
    			System.out.println("Unknown object type received!");
    		}
    	}
    }
    
    // send data to peer
    private void sendData(ArrayList<CTetriMino> shapes) {
    	
	    try {
	    	output.reset();
	    	output.writeObject(shapes);
	    	output.flush();
	    	
	    } catch (IOException ioException) {
	    	System.out.println("Error writing object!");
	    }
	    System.out.println("Sent something.");
    }
    
    // close streams and socket
    private void closeConnection() {
        try {
            output.close();
            input.close();
            connection.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

	public void paintComponent(Graphics g) {
		//g.translate(50,50);
		Graphics2D g2d = (Graphics2D)g;
		//g2d.rotate(Math.PI / 2, 800, 300);
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
		g2d.drawImage(backBuffer, 0, 0, null);
	}

	public void mouseDragged(MouseEvent e) {
		if (e.isMetaDown()) return; // ignore right click
		if (ShapeToBeMoved != null) {
			ShapeToBeMoved.setX(e.getX() - dragOffsetX);
			ShapeToBeMoved.setY(e.getY() - dragOffsetY);
			repaint();
			sendData(duplicates);
		}
	}

	public void mouseClicked(MouseEvent e) {
		if (e.isMetaDown()) { // right click
			// check the duplicates from front to back
			for (int i = duplicates.size() - 1; i >= 0; i--) {
				CTetriMino currentShape = duplicates.get(i);
				if (currentShape.containsPoint(e.getX(), e.getY())) {
					// move to front
					duplicates.remove(i);
					duplicates.add(currentShape);
					currentShape.rotate(e.getX(),e.getY());
					repaint();
					sendData(duplicates);
					repaint();
					return;
				}
			}
		}
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
				sendData(duplicates);
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
				sendData(duplicates);
				repaint();
				return;
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		// delete shapes that are dragged into the bottom area
		if (e.getY() > 500) {
			duplicates.remove(ShapeToBeMoved);
		}
		ShapeToBeMoved = null;
		repaint();
		sendData(duplicates);
		repaint();
	}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}

	public void mouseMoved(MouseEvent e) {}

}