// File: SocialMediaSurvey.java
// Author: Matthew Leeds
// Last Edit: 10.21.2014
// Purpose: Define the main window of an app for collecting, viewing, 
// and modifying data from a survey on social media usage.

import java.util.ArrayList;
import java.util.List;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;

public class SocialMediaSurvey extends JFrame 
                               implements ActionListener, ListSelectionListener {
	
	private int maxRecordNum;
	
	private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JLabel label5;

    private List<CSample> records; // list of the record objects

    private JList<String> textBox; // main text box for the records
    private List<String> recordStrings; // string versions of each record
    private String[] recordStringsArr;
    
    private JButton button1; // Add
    private JButton button2; // Modify
    private JButton button3; // Delete
    private JButton button4; // Delete All

    public int getMaxRecNum() { return maxRecordNum; }
    
    public List<CSample> getRecords() { return records; }

    private int selectedIndex;
    
    public SocialMediaSurvey() {
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
		
		records = new ArrayList<CSample>();
		CSample testRecord = new CSample();
		testRecord.setRecordNumber(1);
		testRecord.setZipCode("35223");
		testRecord.setSocialMedia(new boolean[]{true, false, true, true, false});
		testRecord.setAgeGroup(1);
		testRecord.setAvgTime(1);
		records.add(testRecord);
		textBox = new JList<String>();
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
		
		setSize(900, 600);
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
					records = temp;
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
		}
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
    	int i = 0;
    	for (String s : recordStrings) {
    		recordStringsArr[i] = s;
    		i++;
    	}
    	textBox.setListData(recordStringsArr);
    }
    
	public static void main(String[] args) {
    	SocialMediaSurvey mainWnd = new SocialMediaSurvey();
    }

}
