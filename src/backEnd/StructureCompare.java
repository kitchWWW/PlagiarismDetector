package backEnd;

import java.util.ArrayList;
import java.util.List;

public class StructureCompare extends CompareBehavior{

	@Override
	public double compareMelodies(ArrayList<note> a, ArrayList<note> b) {
		int minSizeSim = 2;
		if(a.size()==0||b.size()==0){
			return 0;
		}
		boolean[] abool = new boolean[a.size()];
		boolean[] bbool = new boolean[b.size()];
		for(int i = 0; i<=a.size()-minSizeSim; i++){
			for(int j = i+minSizeSim; j<=a.size(); j++){
				List<note> testA = a.subList(i, j);
				for(int k = 0; k<=b.size()-testA.size();k++){
					List<note> testB = b.subList(k, k+testA.size());
					if(areLengthExact(testA, testB)){
						for(int t= 0;t<testA.size();t++){
							abool[t+i]=true;
							bbool[t+k]=true;
						}
					}
				}
			}
		}
		int sum = 0;
		for(boolean asdf: abool){
			if(asdf){sum++;}
		}
		for(boolean asdf: bbool){
			if(asdf){sum++;}
		}
		return sum/(abool.length+bbool.length+0.0);
	}

	private boolean areLengthExact(List<note> testA, List<note> testB) {
		for(int i = 0; i<testA.size();i++){
			if(!noteLengthMatch(testA.get(i),testB.get(i))){
				return false;
			}
		}
		return true;
	}

	private boolean noteLengthMatch(note a, note b) {
		return Math.abs(a.length-b.length)<.00005;
	}

}
