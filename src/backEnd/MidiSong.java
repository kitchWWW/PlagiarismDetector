package backEnd;
import java.util.ArrayList;


public class MidiSong {
	
	ArrayList<ArrayList<note>> melodies = new ArrayList<>();
	int TSdenom;
	int TSnumer;
	int ClicksPerBeat;
	String fileName;

	public MidiSong(String URL){
		
		MidiContentExtractor b = new MidiContentExtractor(URL);
		fileName = b.getFileName();
		int tracks = b.howManyTracks();
		for(int i = 0; i<tracks; i++){
			melodies.add(b.getMelody(i));
		}
		ArrayList<Integer> temp = b.getInfo();
		TSnumer = temp.get(0);
		TSdenom = temp.get(1);
		ClicksPerBeat = temp.get(2);
		for(ArrayList<note> a: melodies){
			for(note n: a){
				n.length=((n.stopTick-n.startTick)/(ClicksPerBeat+0.0));
			}
		}
		ArrayList<ArrayList<note>> newMelodies = new ArrayList<>();
		for(ArrayList<note> a:melodies){
			if(a.size()!=0){
				newMelodies.add(a);
			}
		}
		melodies = newMelodies;
	}

	public String toString(){
		String ret = fileName+"\n"+TSnumer+"/"+TSdenom+": "+ClicksPerBeat+"\n";
		for(int i = 0; i<melodies.size();i++){
			ret = ret+"Melody #"+(i+1)+":\n";
			for(int j = 0; j<melodies.get(i).size();j++){
				ret = ret + ""+melodies.get(i).get(j).toString()+"\n";
			}
			ret = ret+"\n";
		}
		return ret;
	}
	
}
