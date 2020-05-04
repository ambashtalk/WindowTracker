package wintrack;

import java.util.Timer;
import java.util.TimerTask;

public class TimeIt {

	public static void main(String[] args) {
		
		Timer timer = new Timer();
		
		TimerTask task = new Main();
		// delay before first execution
		long delay = 500; //in milliseconds
		// delay between succesive execution
		long period = 3 * 60 * 1000; //in milliseconds
		
		timer.schedule(task, delay, period);
	}

}
