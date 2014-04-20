import java.io.IOException;
import java.util.Scanner;
import java.util.Timer; 
import java.util.TimerTask; 


public class CountdownTimer { 
static int interval;
static String str;
static Timer timer;
static Boolean input;
static Boolean timeout;

public static void setTimer(int secs) { 
	timer = new Timer();
	timeout = false;
	interval = secs;
	int delay = 1000; 
	int period = 1000; 
//	System.out.print(interval + " ");
	timer.scheduleAtFixedRate(new TimerTask() { 

			public void run() {
//				System.out.print(setInterval() + " "); 
				setInterval();

			} 
	}, delay, period);
} 


public static String getAnswer(int secs)  { 
	timer = new Timer();
	str = null;
	input = true;
	timer.schedule( new TimerTask(){
		
			public void run(){
				if( str == null){
					System.out.println( "\nTimeout! No solution received. " );
					input = false;
				}
			}
		}, secs*1000 );
	Scanner in = new Scanner(System.in);
	
	while(input)
		{
			try {
				if(System.in.available() > 0)
				{
					str = in.nextLine();
					break;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	timer.cancel();
	return str;
}

private static final int setInterval(){ 
	if( interval== 1) {
		timeout = true;
		timer.cancel(); 
	}
	return --interval; 
}

}