package backEnd;

import java.util.ArrayList;

public class FingerprintCompare extends CompareBehavior{

	public double compareMelodies(ArrayList<note> a, ArrayList<note> b) {
		ArrayList<Double> afp = FingerPrinter.generateFingerprint(a);
		ArrayList<Double> bfp = FingerPrinter.generateFingerprint(b);
		Double sever = FingerPrinter.suggestedSeverity(a.size(), b.size());
		return FingerPrinter.compareFingerprints(afp, bfp,sever);
	}

}
