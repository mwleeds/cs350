// File: NewTetris.java
// Author: Matthew Leeds and CJ Guttormsson
// Last Edit: 11.25.2014
// Purpose: make a window and add an instance of TetrisPanel to it.

import java.awt.*;
import javax.swing.*;

public class NewTetris {
	
	public static void main(String[] args) {
        // initialize the window
		JFrame application = new JFrame("NewTetris");
        TetrisPanel panel; 
        // initialize the main panel
        // default to localhost if no cmdline args given
        if (args.length == 0)
            panel = new TetrisPanel(application, "127.0.0.1");
        else
            panel = new TetrisPanel(application, args[0]);

		application.add(panel);
		application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		application.setSize(800, 600);
		application.setMinimumSize(new Dimension(800,600));
		application.setVisible(true);
		
		panel.runNetworkService();
	}
}