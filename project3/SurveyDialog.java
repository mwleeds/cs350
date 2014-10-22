// File: SurveyDialog.java
// For: CS 350, Project #3
// Author: Matthew Leeds
// Last Edit: 10.21.2014
// Purpose: Define a dialog box to be used for adding or modifying records.

import java.awt.Color;
import java.awt.Container;
import java.awt.event.*;
import javax.swing.*; 

public class SurveyDialog extends JDialog implements ActionListener {
    
	private JLabel question1;
    private JLabel question2;
    private JLabel question3;
    private JLabel question4;
    private JLabel question5;

    private JLabel recordNum;
    public JLabel getRecordNum() { return recordNum; }
    
    private JTextField zipField;
    public JTextField getZipField() { return zipField; }
    
    private JCheckBox group1_1;
    private JCheckBox group1_2;
    private JCheckBox group1_3;
    private JCheckBox group1_4;
    private JCheckBox group1_5;
    private JCheckBox[] group1arr;
    public JCheckBox[] getGroup1arr() { return group1arr; }
    
    private ButtonGroup group2;
    private JRadioButton group2_1;
    private JRadioButton group2_2;
    private JRadioButton group2_3;
    private JRadioButton group2_4;
    private JRadioButton[] group2arr;
    public JRadioButton[] getGroup2arr() { return group2arr; }
    
    private ButtonGroup group3;
    private JRadioButton group3_1;
    private JRadioButton group3_2;
    private JRadioButton group3_3;
    private JRadioButton group3_4;
    private JRadioButton[] group3arr;
    public JRadioButton[] getGroup3arr() { return group3arr; }
    
    private JButton submitButton;
    private JButton cancelButton;
    
    private boolean cancelled;
    public boolean isCancelled() { return cancelled; }

    private CSample dataset;
    public CSample getData() { return dataset; }
    public void setData(CSample inDataset) { dataset = inDataset; }
    
    public SurveyDialog(JFrame owner, String title, int initVal, int maxRecNum,
    		            CSample surveyData, boolean isNewRecord) {
		super(owner, title, true);
		
	    Container content = getContentPane();
	    content.setLayout(null);
	    
	    dataset = new CSample(surveyData);
	    
	    question1 = new JLabel("Record No.");
	    question1.setSize(120, 20);
	    question1.setLocation(50, 30);
	    question1.setForeground(Color.blue);
	    content.add(question1);

	    recordNum = new JLabel("00000000");
	    recordNum.setSize(150, 20);
	    recordNum.setLocation(150, 30);
	    recordNum.setForeground(Color.red);
	    content.add(recordNum);
	    
	    question2 = new JLabel("Zip Code");
	    question2.setSize(120, 20);
	    question2.setLocation(50, 60);
	    question2.setForeground(Color.blue);
	    content.add(question2);
	    
	    zipField = new JTextField(20);
	    zipField.setEditable(true);
	    zipField.addActionListener(this);
	    zipField.setSize(100, 20);
	    zipField.setLocation(150, 60);
	    content.add(zipField);
	    
	    question3 = new JLabel("What social media do you use?");
	    question3.setSize(300, 20);
	    question3.setLocation(50, 110);
	    question3.setForeground(Color.blue);
	    content.add(question3);
	    
	    group1arr = new JCheckBox[5];
	    group1_1 = new JCheckBox("Facebook");
	    group1_1.setSize(150, 20);
	    group1_1.setLocation(50, 135);
	    content.add(group1_1);
	    group1arr[0] = group1_1;
	    group1_2 = new JCheckBox("Twitter");
	    group1_2.setSize(150, 20);
	    group1_2.setLocation(200, 135);
	    content.add(group1_2);
	    group1arr[1] = group1_2;
	    group1_3 = new JCheckBox("LinkedIn");
	    group1_3.setSize(150, 20);
	    group1_3.setLocation(350, 135);
	    content.add(group1_3);
	    group1arr[2] = group1_3;
	    group1_4 = new JCheckBox("Pinterest");
	    group1_4.setSize(150, 20);
	    group1_4.setLocation(500, 135);
	    content.add(group1_4);
	    group1arr[3] = group1_4;
	    group1_5 = new JCheckBox("Other");
	    group1_5.setSize(150, 20);
	    group1_5.setLocation(650, 135);
	    content.add(group1_5);
	    group1arr[4] = group1_5;
	    
	    question4 = new JLabel("What is your age?");
	    question4.setSize(300, 20);
	    question4.setLocation(50, 180);
	    question4.setForeground(Color.blue);
	    content.add(question4);
	    
	    group2arr = new JRadioButton[4];
	    group2_1 = new JRadioButton("19 or less");
	    group2_1.setSize(150, 20);
	    group2_1.setLocation(50, 205);
	    content.add(group2_1);
	    group2arr[0] = group2_1;
	    group2_2 = new JRadioButton("20-35");
	    group2_2.setSize(150, 20);
	    group2_2.setLocation(200, 205);
	    content.add(group2_2);
	    group2arr[1] = group2_2;
	    group2_3 = new JRadioButton("36-49");
	    group2_3.setSize(150, 20);
	    group2_3.setLocation(350, 205);
	    content.add(group2_3);
	    group2arr[2] = group2_3;
	    group2_4 = new JRadioButton("50 and up");
	    group2_4.setSize(150, 20);
	    group2_4.setLocation(500, 205);
	    content.add(group2_4);
	    group2arr[3] = group2_4;
	    group2 = new ButtonGroup();
	    group2.add(group2_1);
	    group2.add(group2_2);
	    group2.add(group2_3);
	    group2.add(group2_4);
	    
	    question5 = new JLabel("How much time do you spend on social media on an average day?");
	    question5.setSize(500, 20);
	    question5.setLocation(50, 250);
	    question5.setForeground(Color.blue);
	    content.add(question5);

	    group3arr = new JRadioButton[4];
	    group3_1 = new JRadioButton("< 0.5 hours (L)");
	    group3_1.setSize(130, 20);
	    group3_1.setLocation(50, 275);
	    content.add(group3_1);
	    group3arr[0] = group3_1;
	    group3_2 = new JRadioButton("between 0.5 and 1 hour (M)");
	    group3_2.setSize(220, 20);
	    group3_2.setLocation(190, 275);
	    content.add(group3_2);
	    group3arr[1] = group3_2;
	    group3_3 = new JRadioButton("between 1 and 2 hours (H)");
	    group3_3.setSize(220, 20);
	    group3_3.setLocation(420, 275);
	    content.add(group3_3);
	    group3arr[2] = group3_3;
	    group3_4 = new JRadioButton("longer than 2 hours (X)");
	    group3_4.setSize(200, 20);
	    group3_4.setLocation(650, 275);
	    content.add(group3_4);
	    group3arr[3] = group3_4;
	    group3 = new ButtonGroup();
	    group3.add(group3_1);
	    group3.add(group3_2);
	    group3.add(group3_3);
	    group3.add(group3_4);

		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		cancelButton.setSize(150, 30);
		cancelButton.setLocation(500, 350);
		content.add(cancelButton);	

		submitButton = new JButton("Submit");
		submitButton.addActionListener(this);
		submitButton.setSize(150, 30);
		submitButton.setLocation(200, 350);
		content.add(submitButton);	
		
		if (!isNewRecord) {
			// pre-load the values from the record we're modifying
			recordNum.setText(String.format("%08d", surveyData.getRecordNumber()));
			zipField.setText(surveyData.getZipCode());
			for (int i = 0; i < surveyData.getSocialMedia().length; i++) {
				if (surveyData.getSocialMedia()[i]) {
					group1arr[i].setSelected(true);
				}
			}
			group2arr[surveyData.getAgeGroup()].setSelected(true);
			group3arr[surveyData.getAvgTime()].setSelected(true);
		} else {
			// generate record number
			recordNum.setText(String.format("%08d", (maxRecNum + 1)));
		}
	    setSize(900, 450);
		setLocationRelativeTo(owner);
		setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
		if (e.getSource() == submitButton) {
			// collect data from all the fields and put it in dataset
			CSample _dataset = new CSample(getData());
			_dataset.setRecordNumber(Integer.parseInt(getRecordNum().getText()));
			_dataset.setZipCode(getZipField().getText());
			JCheckBox[] group1arr = getGroup1arr();
			_dataset.setSocialMedia(new boolean[]{group1arr[0].isSelected(), group1arr[1].isSelected(),
					                             group1arr[2].isSelected(), group1arr[3].isSelected(),
					                             group1arr[4].isSelected()});
			JRadioButton[] group2arr = getGroup2arr();
			for (int i=0; i < group2arr.length; i++) {
				if (group2arr[i].isSelected()) {
					_dataset.setAgeGroup(i);
				}
			}
			JRadioButton[] group3arr = getGroup3arr();
			for (int i=0; i < group3arr.length; i++) {
				if (group3arr[i].isSelected()) {
					_dataset.setAvgTime(i);
				}
			}
			setData(_dataset);
		    cancelled = false;
		    setVisible(false);
		}
		else if(e.getSource() == cancelButton) {
		    cancelled = true;
		    setVisible(false);
		}
    }
    
}
