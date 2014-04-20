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
	public String playerSolution;
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
		playerSolution = "";
		if(timer == 0) //Timer is to be off
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
	
	/**
	 * The user wishes to finalize their answer.
	 * 
	 * @param solution The user's answer.
	 * 
	 */
	public void submitSolution(String solution){
		playerSolution = solution;
	}
	
	/**
	 * This method controls the game, calling the methods that are required.
	 */
	public abstract void playGame();
	
	/**
	 * Determines whether or not the player's answer is valid.
	 * 
	 * @return True if correct, else false.
	 */
	public abstract boolean checkSolution();
	
	/**
	 * Scores the user's answer, in relation to the round's scoring scheme.
	 * 
	 * @return The user's score for the round.
	 * 
	 */
	public abstract int scoreSolution();
	
	/**
	 * Reveals the solution of that round.
	 */
	public abstract void revealSolution();
	
}
