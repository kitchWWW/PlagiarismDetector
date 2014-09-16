package backEnd;
import java.util.ArrayList;


public class FingerPrinter {
	
	static double sever;
	
	public static ArrayList<Double> generateFingerprint(ArrayList<note> a) {
		ArrayList<Integer> fing= new ArrayList<>();
		ArrayList<Double> ret = new ArrayList<>();
		for(int i = 0; i<130; i++){
			fing.add(0);
			fing.add(0);
		}
		int mid = 130;
		for(int i = 1; i<a.size(); i++){
			int diff = a.get(i).key - a.get(i-1).key;
			fing.set(mid+diff, fing.get(mid+diff)+1);
		}
		for(int i = 0; i<fing.size(); i++){
			ret.add(fing.get(i)/(a.size()+0.0));
		}
		//System.out.println(ret);
		return ret;
	}

	public static double compareFingerprints(ArrayList<Double> fpA, ArrayList<Double> fpB, double severity) {
		int suspicion = 0;
		int nonZero = 0;
		for(int i = 0; i<fpA.size();i++){
			Double a = fpA.get(i);
			Double b = fpB.get(i);
			if(Double.valueOf(a)!=0.0){
				nonZero++;
				if(kindOfEquals(a,b,severity)){
					suspicion++;
				}
			}
		}
		return suspicion/(nonZero+0.0);
	}
	
	public static double suggestedSeverity(int lengthA, int lengthB){
		return 2.0/((lengthA>lengthB)?lengthB:lengthA);
	}
	
	private static boolean kindOfEquals(Double a, Double b, Double sever) {
		if(a+sever>b&&a-sever<b){
			return true;
		}
		return false;
	}

	public static double compareFingerprints(ArrayList<Double> fpA, ArrayList<Double> fpB) {
		int suspicion = 0;
		int nonZero = 0;
		for(int i = 0; i<fpA.size();i++){
			Double a = fpA.get(i);
			Double b = fpB.get(i);
			if(Double.valueOf(a)!=0.0){
				nonZero++;
				if(kindOfEquals(a,b,.05)){
					suspicion++;
				}
			}
		}
		return suspicion/(nonZero+0.0);
	}
	
}
