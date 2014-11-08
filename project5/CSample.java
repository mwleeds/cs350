import java.io.Serializable;

// File: CSample.java
// For: CS 350, Project #5
// Author: Matthew Leeds
// Last Edit: 11.07.2014
// Purpose: define a single survey record

public class CSample implements Serializable {
	private int recordNumber; // unique record number
	private String zipCode; // 5-digit zip
	private boolean[] socialMedia; // 5-slot array for each social media network
	private int ageGroup; // 0: <19, 1: 20-35, 2: 36-49, 3: 50>
	private int avgTime; // 0: L, 1: M, 2: H, 3: X
	
	public void setRecordNumber(int inRecordNumber) { recordNumber = inRecordNumber; }
	public int getRecordNumber() { return recordNumber; }
	
	public void setZipCode(String inZipCode) { zipCode = inZipCode; }
	public String getZipCode() { return zipCode; }
	
	public void setSocialMedia(boolean[] inSocialMedia) { socialMedia = inSocialMedia; }
	public boolean[] getSocialMedia() { return socialMedia; }
	
	public void setAgeGroup(int inAgeGroup) { ageGroup = inAgeGroup; }
	public int getAgeGroup() { return ageGroup; }
	
	public void setAvgTime(int inAvgTime) { avgTime = inAvgTime; }
	public int getAvgTime() { return avgTime; }
	
	public CSample() {	
		recordNumber = new Integer(0);
		zipCode = new String("");
		socialMedia = new boolean[]{false, false, false, false, false};
		ageGroup = new Integer(0);
		avgTime = new Integer(0);
	}
	
	public CSample(CSample src) {
		setRecordNumber(src.getRecordNumber());
		setZipCode(src.getZipCode());
		setSocialMedia(src.getSocialMedia());
		setAgeGroup(src.getAgeGroup());
		setAvgTime(src.getAvgTime());
	}
	
	public String stringify() {
		String outString = "";
		outString += String.format("%08d", recordNumber);
		outString += "               ";
		outString += zipCode;
		outString += "                  ";
		for (int i=0; i < socialMedia.length; i++) {
			if (socialMedia[i]) {
				switch(i) {
				case 0:
					outString += "F";
					break;
				case 1:
					outString += "T";
					break;
				case 2:
					outString += "L";
					break;
				case 3:
					outString += "P";
					break;
				case 4:
					outString += "O";
					break;
				}
			} else {
				outString += "-";
			}
		}
		outString += "               ";
		switch(ageGroup) {
		case 0:
			outString += "  -19";
			break;
		case 1:
			outString += "20-35";
			break;
		case 2:
			outString += "36-49";
			break;
		case 3:
			outString += "50-  ";
			break;
		}
		outString += "                  ";
		switch(avgTime) {
		case 0:
			outString += "L";
			break;
		case 1:
			outString += "M";
			break;
		case 2:
			outString += "H";
			break;
		case 3:
			outString += "X";
			break;
		}

		return outString;
	}
	
}