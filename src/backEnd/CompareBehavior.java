package backEnd;
import java.util.ArrayList;

public abstract class CompareBehavior {
	
	protected int severity;
	
	public abstract double compareMelodies(ArrayList<note> a, ArrayList<note> b);

}
