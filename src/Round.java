import java.util.Scanner;

public abstract class Round {
	
	public String playerSolution;
	public Scanner scanner;
	public boolean timer;
	
	public Round(Scanner in, int timer){
		scanner = in;
		playerSolution = "";
		if(timer == 0)
			this.timer = false;
		else if(timer == 1)
			this.timer = true;
		else
			this.timer = setTimer();
	}
	
	private boolean setTimer(){
			System.out.print("Do you want a timer? Y/N: ");
			char answer = scanner.next().toLowerCase().charAt(0);
			if(answer == 'y')
				return true;
			else if(answer == 'n')
				return false;
			else
				System.out.println("Invalid response");
				return setTimer();
	}
	
	public void submitSolution(String solution){
		playerSolution = solution;
	}
	
	public abstract void playGame();
	
	public abstract boolean checkSolution();
	
	public abstract int scoreSolution();
	
	public abstract void revealSolution();
	
}
