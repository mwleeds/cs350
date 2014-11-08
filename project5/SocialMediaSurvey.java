// File: SocialMediaSurvey.java
// For: CS 350, Project #5
// Author: Matthew Leeds
// Last Edit: 11.07.2014
// Purpose: Define the main window of an app for collecting, viewing, 
// and modifying data from a survey on social media usage. This can now
// also read and write serialized record data to files.

import java.util.ArrayList;
import java.util.List;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.io.*;

public class SocialMediaSurvey extends JFrame 
                               implements ActionListener, ListSelectionListener {
	
	private int maxRecordNum;
	
	private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JLabel label5;

    private List<CSample> records; // list of the record objects
    //private static String initialDataFilename; // filename of records data passed on the command line

    private JList<String> textBox; // main text box for the records
    private List<String> recordStrings; // string versions of each record
    private String[] recordStringsArr;
    
    private JButton button1; // Add
    private JButton button2; // Modify
    private JButton button3; // Delete
    private JButton button4; // Delete All
    private JButton button5; // Save to File
    private JButton button6; // Open File
    
    public int getMaxRecNum() { return maxRecordNum; }
    
    public List<CSample> getRecords() { return records; }
    public void setRecords(List<CSample> inRecords) { records = inRecords; } 
    private int selectedIndex;
    private String filename;
    
    public SocialMediaSurvey(String initialDataFilename) 
    		throws ClassNotFoundException, IOException {
		super("Survey on Social Media");

	    Container content = getContentPane();
	    content.setLayout(null);
	    
	    maxRecordNum = 0;
	    
	    label1 = new JLabel("Record No.");
		label1.setSize(150, 30);
		label1.setLocation(100, 50);
		label1.setForeground(Color.blue);
		content.add(label1);
		
		label2 = new JLabel("Zip Code");
		label2.setSize(150, 30);
		label2.setLocation(250, 50);
		label2.setForeground(Color.blue);
		content.add(label2);
		
		label3 = new JLabel("Social Media");
		label3.setSize(150, 30);
		label3.setLocation(400, 50);
		label3.setForeground(Color.blue);
		content.add(label3);
		
		label4 = new JLabel("Age Group");
		label4.setSize(150, 30);
		label4.setLocation(550, 50);
		label4.setForeground(Color.blue);
		content.add(label4);
		
		label5 = new JLabel("Avg Time");
		label5.setSize(150, 30);
		label5.setLocation(700, 50);
		label5.setForeground(Color.blue);
		content.add(label5);
		
		textBox = new JList<String>();
		records = new ArrayList<CSample>();
		// initialize this if necessary
		if (initialDataFilename != "") {
			readFromFile(initialDataFilename);
		}
		
		/*CSample testRecord = new CSample();
		testRecord.setRecordNumber(1);
		testRecord.setZipCode("35223");
		testRecord.setSocialMedia(new boolean[]{true, false, true, true, false});
		testRecord.setAgeGroup(1);
		testRecord.setAvgTime(1);
		records.add(testRecord);*/
		
		updateTextArea(); // updates recordStrings
		textBox.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		textBox.setFont(new Font("Monospaced", Font.PLAIN, 12));
		textBox.setSize(700, 350);
		textBox.setLocation(100,100);
		textBox.setBorder(BorderFactory.createBevelBorder(1));
		textBox.addListSelectionListener(this);
		content.add(textBox);
				
		button1 = new JButton("Add");
		button1.setSize(150, 30 );
		button1.setLocation(100, 500 );
		button1.addActionListener(this);
		content.add(button1);
		
		button2 = new JButton("Modify");
		button2.setSize(150, 30);
		button2.setLocation(300, 500);
		button2.addActionListener(this);
		content.add(button2);
	    
		button3 = new JButton("Delete");
		button3.setSize(150, 30);
		button3.setLocation(500, 500);
		button3.addActionListener(this);
		content.add(button3);
		
		button4 = new JButton("Delete All");
		button4.setSize(150, 30);
		button4.setLocation(700, 500);
		button4.addActionListener(this);
		content.add(button4);
		
		button5 = new JButton("Save to File");
		button5.setSize(150, 30);
		button5.setLocation(300, 550);
		button5.addActionListener(this);
		content.add(button5);
		
		button6 = new JButton("Open File");
		button6.setSize(150, 30);
		button6.setLocation(500, 550);
		button6.addActionListener(this);
		content.add(button6);
		
		setSize(900, 650);
	    setLocation(100, 100);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setVisible(true);
   }
    
    public void actionPerformed(ActionEvent e) {
		if(e.getSource() == button1) {
		    // new record
			SurveyDialog dialogWnd = new SurveyDialog(this, "Add a Survey Record", 1, 
					                                  getMaxRecNum(), new CSample(), true);
		    if (!dialogWnd.isCancelled()) {
		    	records.add(dialogWnd.getData());
		    	updateTextArea();
		    	maxRecordNum++;
		    }
		} else if (e.getSource() == button2) {
			// modify existing record
			if (selectedIndex == -1) {
				return;
			} else {
				SurveyDialog dialogWnd = new SurveyDialog(this, "Modify a Survey Record", 1, 
					                                      maxRecordNum, getRecords().get(selectedIndex), false);
				if (!dialogWnd.isCancelled()) {
					// only change the desired record
					List<CSample> backup = new ArrayList<CSample>(records);
					List<CSample> temp = new ArrayList<CSample>();
					for (int i=0; i < backup.size(); i++) {
						if (i == selectedIndex) {
							temp.add(dialogWnd.getData());
						} else {
							temp.add(backup.get(i));
						}
					}
					setRecords(temp);
					updateTextArea();
				}
			}
		} else if (e.getSource() == button3) {
			// delete record
			if (selectedIndex == -1) {
				return;
			} else {
				records.remove(selectedIndex);
				updateTextArea();
			}
		} else if (e.getSource() == button4) {
			// delete all records
			// ideally there would be a confirmation dialog
			records = new ArrayList<CSample>();
			updateTextArea();
		} else if (e.getSource() == button5) {
			// save records to file
			JFileChooser chooser = new JFileChooser();
			int option = chooser.showSaveDialog(SocialMediaSurvey.this);
			if (option == JFileChooser.APPROVE_OPTION) {
				filename = chooser.getSelectedFile().getAbsolutePath();
				try {
					writeToFile(filename);
				} catch(ClassNotFoundException | IOException e1) {
					e1.printStackTrace();
				}
			}
		} else if (e.getSource() == button6) {
			// open records from a file
			JFileChooser chooser = new JFileChooser();
			int option = chooser.showOpenDialog(SocialMediaSurvey.this);
			if (option == JFileChooser.APPROVE_OPTION) {
				filename = chooser.getSelectedFile().getAbsolutePath();
				try {
					readFromFile(filename);
				} catch(ClassNotFoundException | IOException e2) {
					e2.printStackTrace();
				}
			}
		}
    }
    
    // read record data from a file and update the list shown
    // must be passed an absolute filepath
    @SuppressWarnings("unchecked")
	private void readFromFile(String filename) 
    		throws ClassNotFoundException, IOException {
    	records.removeAll(records);
    	ObjectInputStream input = new ObjectInputStream(new FileInputStream(filename));
    	records = (ArrayList<CSample>) input.readObject();
    	updateTextArea();
    	input.close();
    }
    
    // write current record data to a file
    // must be passed an absolute filepath
    private void writeToFile(String filename) 
    		throws ClassNotFoundException, IOException {
    	ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(filename));
    	output.writeObject(records);
    	output.close();
    }
    
	@Override
	public void valueChanged(ListSelectionEvent e) {
		JList thelist = (JList) e.getSource();
		selectedIndex = thelist.getSelectedIndex();
	}
	
    public void updateTextArea() {
    	// update the rows to match the data we have
    	recordStrings = new ArrayList<String>();
    	for (CSample s : records) {
    		recordStrings.add(s.stringify());
    	}
    	recordStringsArr = new String[records.size()];
    	//recordStringsArr = new String[0];
    	int i = 0;
    	for (String s : recordStrings) {
    		recordStringsArr[i] = s;
    		i++;
    	}
    	textBox.setListData(recordStringsArr);
    }
    
	public static void main(String[] args) 
			throws ClassNotFoundException, IOException {
		String initialDataFilename = "";
		// allow the user to specify a file as a command line argument
		// with data to initialize the list of records
		if (args.length != 0) {
			initialDataFilename = args[0];
		}
    	@SuppressWarnings("unused")
		SocialMediaSurvey mainWnd = new SocialMediaSurvey(initialDataFilename);
    }

}
