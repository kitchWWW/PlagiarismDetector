package backEnd;
import java.util.ArrayList;


public class BagOfWordsCompare extends CompareBehavior{

	@Override
	public double compareMelodies(ArrayList<note> a, ArrayList<note> b) {
		ArrayList<Double> fpA = FingerPrinter.generateFingerprint(a);
		ArrayList<Double> fpB = FingerPrinter.generateFingerprint(b);
		return FingerPrinter.compareFingerprints(fpA, fpB, FingerPrinter.suggestedSeverity(a.size(), b.size()));
	}
}
