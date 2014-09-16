package backEnd;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;


public class MidiContentExtractor {
	Sequence seq;
	File myFile;
	
	public MidiContentExtractor(String url){
		try {
			myFile = new File(url);
			seq = MidiSystem.getSequence(myFile);
		} catch (Exception e){e.printStackTrace();}
	}
	
	public int howManyTracks(){
		return seq.getTracks().length;
	}
	
	public ArrayList<note> getMelody(int TrackNumber){
		Track track = seq.getTracks()[TrackNumber];
		ArrayList<note> ret = new ArrayList<>();
		for(int i = 0; i<track.size(); i++){
			MidiMessage mm= track.get(i).getMessage();
			if(mm instanceof ShortMessage){
				ShortMessage s= (ShortMessage) mm;
				if(s.getCommand()==0x90){	//"Note On"
					if(s.getData2()>0){
						note n = new note(track.get(i).getTick(),s.getData1(),s.getData2());	//create a new note
						ret.add(n);	//add it to the list
					}else{
						ret = finish(ret, track.get(i).getTick(),s.getData1());
					}
				}else if (s.getCommand()==0x80){//"Note Off"
					ret = finish(ret, track.get(i).getTick(),s.getData1());					//then finish the note
				}
			}
		}
		for(int i = 0; i<ret.size()-1; i++){
			note a = ret.get(i);
			note b = ret.get(i+1);
			if(a.startTick==b.startTick){
				if(a.key>b.key){
					ret.remove(i+1);
				}else{
					ret.remove(i);
				}
				i--;
			}
		}
		return ret;
	}

	/**
	 * Gets information about the midi file
	 * @return Array List with the contents [numerator time signature, denominator time signature, midi ticks per beat] 
	 */
	public ArrayList<Integer> getInfo(){
		ArrayList<Integer> ret = new ArrayList<Integer>();//0 is nn, 1 is dd, 2 is cc, 3 is bb
		Path path = Paths.get(myFile.getAbsolutePath());
		try {
			byte[] data = Files.readAllBytes(path);
			for(int i = 0; i<data.length-7; i++){
				if((data[i]+"").equals("-1") && (data[i+1]+"").equals("88") && (""+data[i+2]).equals(04+"")){
					ret.add((int) data[i+3]);
					ret.add((int) Math.pow(2,(int) data[i+4]));
					//int cc = data[i+5];
					int bb = data[i+6];
//					System.out.println(cc);
//					System.out.println(bb);
					ret.add(bb*48);
				}
			}

			//ret[4]*ret[3]/8
		} catch (IOException e) {e.printStackTrace();}
		
		return ret;
	}

	private ArrayList<note> finish(ArrayList<note> a, long endtick, int key) {
		for(int i = a.size()-1; i>=0; i--){
			note t = a.get(i);
			if(t.key==key&&t.stopTick==0){
				t.stopTick=endtick;
			}
		}
		return a;
	}

	public String getFileName() {
		try{
			return myFile.getName().substring(0, myFile.getName().indexOf(".midi"));
		}catch(Exception e){
			return myFile.getName().substring(0, myFile.getName().indexOf(".mid"));
		}	
	}
}
