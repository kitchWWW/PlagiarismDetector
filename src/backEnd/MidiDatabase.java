package backEnd;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;



public class MidiDatabase {
	
	ArrayList<MidiSong> songs = new ArrayList<>();
	ArrayList<ArrayList<ArrayList<Double>>> fingerprints = new ArrayList<>();
	CompareBehavior behavior = new SubStringCompare();
	
	public MidiDatabase(){
		
	}
	
	public void add(String FileURL){
		MidiSong s = new MidiSong(FileURL);
		songs.add(s);
		fingerprints.add(new ArrayList<ArrayList<Double>>());
		for(ArrayList<note> mel:s.melodies){
			ArrayList<Double> fp = FingerPrinter.generateFingerprint(mel);
			fingerprints.get(fingerprints.size()-1).add(fp);
		}
	}
	public void add(MidiSong s) {
		songs.add(s);
		fingerprints.add(new ArrayList<ArrayList<Double>>());
		for(ArrayList<note> mel:s.melodies){
			ArrayList<Double> fp = FingerPrinter.generateFingerprint(mel);
			fingerprints.get(fingerprints.size()-1).add(fp);
		}
	}
	
	public void checkAgainstDatabase(MidiSong s) {
		ArrayList<MidiSong> songsToCheck = new ArrayList<>();
		for(ArrayList<note>mel:s.melodies){
			ArrayList<Double> melFP = new ArrayList<Double>();
			melFP = FingerPrinter.generateFingerprint(mel);
			System.out.println("Fingerprinting Melody...");
			ArrayList<Double> bestFP = new ArrayList<>();
			for(ArrayList<ArrayList<Double>> d:fingerprints){
				for(ArrayList<Double> fp: d){
					if(bestFP.size()<3){
						bestFP = fp;
					}
					double bestSoFar = FingerPrinter.compareFingerprints(melFP, bestFP);
					double maybeNew = FingerPrinter.compareFingerprints(melFP, fp);
					if(maybeNew>bestSoFar){
						bestFP = fp;
					}
				}
			}
			songsToCheck.add(lookUpSong(bestFP));
			System.out.println("Close match: "+lookUpSong(bestFP).fileName);
		}
		System.out.println("Fingerprinting sucsessful");
		HashSet<MidiSong> hs = new HashSet<>();
		hs.addAll(songsToCheck);
		songsToCheck.clear();
		songsToCheck.addAll(hs);
		
		System.out.println("\nChecking: ");
		for(MidiSong son: songsToCheck){
			System.out.println(son.fileName);
		}
		
		
		double best = 0;
		String name = "";
		System.out.println("\nChecking songs with similar fingerprints:\n");
		for(MidiSong son: songsToCheck){
			double now = compare(s,son);
			if(now>best){
				best = now;
				name = son.fileName;
			}
		}
		//System.out.println("");
		System.out.println("Largest %match:"+best);
		System.out.println("Found in \""+name+"\"");
	}

	private MidiSong lookUpSong(ArrayList<Double> fp) {
		for(int i = 0; i<fingerprints.size();i++){
			for(ArrayList<Double> finger: fingerprints.get(i)){
				if(fp.equals(finger)){
					return songs.get(i);
				}
			}
		}
		return null;
	}
	
	private double compare(MidiSong songA, MidiSong songB){
		List<Double> matchFactors = new ArrayList<>();
		for(int i = 0; i<songA.melodies.size(); i++){
			for(int j = 0; j<songB.melodies.size(); j++){
				System.out.print(songA.fileName+" #"+i+" vs. "+songB.fileName+" #"+j+": ");
				double thisTime = behavior.compareMelodies(songA.melodies.get(i),songB.melodies.get(j));
				System.out.println(thisTime);
				matchFactors.add(thisTime);
			}
		}
		Collections.sort(matchFactors);
		System.out.println("Largest Track Match: "+matchFactors.get(matchFactors.size()-1)+"\n");
		return matchFactors.get(matchFactors.size()-1);
	}
	
	public String toString(){
		return ""+songs;
	}
}
