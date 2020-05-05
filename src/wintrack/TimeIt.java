package wintrack;

import java.util.Timer;
import java.util.TimerTask;

public class TimeIt {

	// delay before first execution
	static final long delay = 500; //in milliseconds
	
	// delay between succesive execution
	static final long period = 3 * 1000; //3 sec in milliseconds
	
	public static void main(String[] args) {
		
		Timer timer = new Timer();
		
		TimerTask task = new Main();
		
		timer.schedule(task, delay, period);
	}

}
