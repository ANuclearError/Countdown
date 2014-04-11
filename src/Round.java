import java.util.Scanner;

public abstract class Round {
	
	public String playerSolution;
	public Scanner scanner;
	
	public Round(Scanner in){
		scanner = in;
		playerSolution = "";
	}
	
	public void submitSolution(String solution){
		playerSolution = solution;
	}
	
	public abstract void playGame();
	
	public abstract boolean checkSolution();
	
	public abstract int scoreSolution();
	
	public abstract void revealSolution();
	
}
