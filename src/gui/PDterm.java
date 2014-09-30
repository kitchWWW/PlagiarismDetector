package gui;

import java.util.ArrayList;
import java.util.Scanner;

import backEnd.MidiDatabase;
import backEnd.MidiSong;

public class PDterm {
	
	public PDterm(){
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		System.out.println("URL of database:");
		String db = in.nextLine();
		System.out.println("URL of files to check:");
		String song = in.nextLine();
		ArrayList<String> URLStoTest = new ArrayList<>();
		URLStoTest = bigMain.getAllMidi(song);		
		ArrayList<String> urlsToAd = new ArrayList<>();
		urlsToAd = bigMain.getAllMidi(db);
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

}
