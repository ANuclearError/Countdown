import java.io.IOException;
import java.util.Scanner;
import java.util.Timer; 
import java.util.TimerTask; 

/**
 * An implementation of timer, used in rounds.
 * 
 * @author Kristine Semjonova
 *
 */

public class CountdownTimer { 
	/**
	 * Stores duration of countdown timer.
	 */
	static int interval;
	/**
	 * Stores user input.
	 */
	static String str;
	/**
	 * Timer.
	 */
	static Timer timer;
	/**
	 * Detects whether player provided input.
	 */
	static Boolean input;
	/**
	 * Detects timeout.
	 */
	static Boolean timeout;

	/** Sets timer for specified amount of time.
	 * 
	 * @param secs How many seconds timer ticks.
	 */
	public static void setTimer(int secs) { 
		timer = new Timer();
		timeout = false;
		interval = secs;
		int delay = 1000; 
		int period = 1000; 
		timer.scheduleAtFixedRate(new TimerTask() { 
			public void run() {
				setInterval();
			} 
		}, delay, period);
	} 

	/** Sets timer for specified amount of time and prompts input.
	 * 
	 * @param secs How many seconds timer ticks.
	 *
	 * @return Players input or empty String if user didn't type in before timeout.
	 */
	public static String getAnswer(int secs)  { 
		timer = new Timer();
		str = null;
		input = true;
		timer.schedule( new TimerTask(){

			public void run(){
				if( str == null){
					System.out.println( "\nTimeout! No solution received. " );
					input = false;
					str = "";
				}
			}
		}, secs*1000 );
		Scanner in = new Scanner(System.in);

		//tries to get input until timeout
		while(input)
		{
			try {
				if(System.in.available() > 0)
				{
					str = in.nextLine(); //if gets, cancels timer and returns input
					break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		timer.cancel();
		return str;
	}

	/** Checks whether time is out. If it is then timer is cancelled,
	 *  otherwise number of seconds left is decreased by 1.
	 * 
	 * @return Number of seconds left.
	 */
	private static final int setInterval(){ 
		if( interval== 1) {
			timeout = true;
			timer.cancel(); 
		}
		return --interval; 
	}

}