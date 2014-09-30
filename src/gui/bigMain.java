package gui;
import java.io.File;
import java.util.ArrayList;
import backEnd.MidiDatabase;
import backEnd.MidiSong;

public class bigMain {

	public static void main(String[] args) {
		int version = 2;
		if(version==0){
			ArrayList<String> URLStoTest = new ArrayList<>();
			URLStoTest = getAllMidi("/Users/Admin/Music/midiTestFiles");		
			ArrayList<String> urlsToAd = new ArrayList<>();
			urlsToAd = getAllMidi("/Users/Admin/Music/midiDatabase");
			MidiDatabase md = new MidiDatabase();
			for(String s: urlsToAd){
				if(!URLStoTest.contains(s)){
					try{
						md.add(s);
					}catch(Exception e){
						e.printStackTrace();
					}	
				}
			}
			for(String t: URLStoTest){
				md.checkAgainstDatabase(new MidiSong(t));
			}
		}else if(version == 1){
			new PDgui();
		}else if(version ==2){
			new PDterm();
		}
	}

	public static ArrayList<String> getAllMidi(String directoryName) {
		ArrayList<String> ret = new ArrayList<>();
	    File directory = new File(directoryName);
	    File[] fList = directory.listFiles();
	    for (File file : fList) {
	    	int n = file.getName().indexOf(".midi");
	    	int m = file.getName().indexOf(".mid");
	        if (file.isFile()&&((n!=-1)||(m!=-1))) {
	            ret.add(file.getAbsolutePath());
	        } else if (file.isDirectory()) {
	            ret.addAll(getAllMidi(file.getAbsolutePath()));
	        }
	    }
	    return ret;
	}

}
