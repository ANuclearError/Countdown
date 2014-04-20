import java.util.Scanner;

/**
 * This abstract class is used as the foundations of the various round types
 * that are featured in the game.
 * 
 * @author Aidan O'Grady
 * 
 */
public abstract class Round {
	
	/**
	 * The player's answer.
	 */
	public String player1Solution, player2Solution;
	/**
	 * Handling user input.
	 */
	public Scanner scanner;
	/**
	 * Whether or not the user wants a timer.
	 */
	public boolean timer;
	
	/**
	 * Constructor
	 * 
	 * @param in	Scanner used to control input.
	 * @param timer	Indicates what the condition of the timer should be.
	 * 
	 */
	public Round(Scanner in, int timer){
		scanner = in;
		player1Solution = "";
		player2Solution = "";
		if(timer == 0)
			this.timer = false;
		else if(timer == 1) //Timer is to be on
			this.timer = true;
		else //Timer needs to be determined.
			this.timer = setTimer();
	}
	
	private boolean setTimer(){
			System.out.print("Do you want a timer? Y/N: ");
			String answer = scanner.next().toLowerCase();
			if(answer.equals("y")) //The timer will be enabled
				return true;
			else if(answer.equals("n")) //The timer will be disabled
				return false;
			else //The user's input was not valid.
				System.out.println("Invalid response");
				return setTimer();
	}
	

	public void submitSolution(String solution1, String solution2){
		player1Solution = solution1;
		player2Solution = solution2;
	}
	
	public abstract void playGame(int numberOfPlayers);
	
	public abstract boolean checkSolution(String answer);
	
	public abstract int scoreSolution(String answer);
	
	/**
	 * Reveals the solution of that round.
	 */
	public abstract void revealSolution();
	
}
