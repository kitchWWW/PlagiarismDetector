package backEnd;

public class note {
	public long startTick;
	public long stopTick;
	public int key;
	public int velocity;
	public double length;	//in terms of one beat. ie, in 4/4 time, length of 1 means quater note, 2 means half, .5 8th etc.
	
	public note (long s, int k, int v){
		startTick = s;
		key = k;
		velocity = v;
	}
	
	public String toString(){
		return ""+startTick+" "+key+" "+velocity+" "+length;
	}
	

}
