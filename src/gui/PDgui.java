package gui;

import javax.swing.*;

import backEnd.MidiDatabase;

public class PDgui {
	
	MidiDatabase mdb;
	JFrame main;
	
	
	public PDgui(MidiDatabase db){
		main = new JFrame("Plagerism Detector");
		
		
		
		
		main.setVisible(true);
	}

}
