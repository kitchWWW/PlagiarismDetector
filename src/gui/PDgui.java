package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import backEnd.MidiDatabase;
import backEnd.MidiSong;

public class PDgui {
	
	JFrame main;
	JTextField dbfield = new JTextField("file URL of database");
	JTextField sfield = new JTextField("file URL of song");
	JButton dbbut = new JButton("Locate URL");
	JButton sbut = new JButton("Locate URL");
	JTextArea ta = new JTextArea();
	JButton go = new JButton("Compare");
	
	public PDgui(){
		main = new JFrame("Plagerism Detector");
		Box biggest = Box.createVerticalBox();
		Box db = Box.createHorizontalBox();
		Box s = Box.createHorizontalBox();
		dbbut.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = fc.showOpenDialog(null);
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			    	dbfield.setText(""+fc.getSelectedFile().getAbsolutePath());
			    }	
			}
		});
		sbut.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = fc.showOpenDialog(null);
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			    	sfield.setText(""+fc.getSelectedFile().getAbsolutePath());
			    }	
			}
		});
		go.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> URLStoTest = new ArrayList<>();
				URLStoTest = bigMain.getAllMidi("/Users/Admin/Music/midiTestFiles");		
				ArrayList<String> urlsToAd = new ArrayList<>();
				urlsToAd = bigMain.getAllMidi("/Users/Admin/Music/midiDatabase");
				MidiDatabase md = new MidiDatabase();
				for(String s: urlsToAd){
					if(!URLStoTest.contains(s)){
						try{
							md.add(s);
						}catch(Exception ex){
							ex.printStackTrace();
						}	
					}
				}
				for(String t: URLStoTest){
					md.checkAgainstDatabase(new MidiSong(t));
				}
				
			}
		});
		
		
		db.add(dbfield);
		db.add(dbbut);
		s.add(sfield);
		s.add(sbut);
		biggest.add(db);
		biggest.add(s);
		biggest.add(go);
		main.add(biggest);
		main.pack();
		main.setVisible(true);
	}
}
