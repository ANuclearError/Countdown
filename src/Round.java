import java.util.Scanner;

public abstract class Round {
	
	public String player1Solution, player2Solution;
	public Scanner scanner;
	public boolean timer;
	
	public Round(Scanner in, int timer){
		scanner = in;
		player1Solution = "";
		player2Solution = "";
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
	
	public void submitSolution(String solution1, String solution2){
		player1Solution = solution1;
		player2Solution = solution2;
	}
	
	public abstract void playGame(int numberOfPlayers);
	
	public abstract boolean checkSolution(String answer);
	
	public abstract int scoreSolution(String answer);
	
	public abstract void revealSolution();
	
}
