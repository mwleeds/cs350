import java.awt.*;
import javax.swing.*;

public class NewTetris {
	
	public static void main(String[] args) {
		JFrame application = new JFrame("NewTetris");
		TetrisPanel panel = new TetrisPanel();
		application.add(panel);
		application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		application.setSize(800, 600);
		application.setMinimumSize(new Dimension(800,600));
		application.setVisible(true);
	}
}
