import java.util.InputMismatchException;
import java.util.Scanner;


public class Countdown {
	private String playerName;
	private int playerScore;
	private int round;
	private Scanner scanner;
	
	public Countdown(){
		System.out.println("Welcome to Countdown!\n");
		scanner = new Scanner(System.in);
		playerName = getPlayerName();
		playerScore = 0;
		round = 0;
		while(true)
			displayMenu();
	}
	
	private String getPlayerName() {
		System.out.println("Please enter your name");
		String name = scanner.nextLine();
		return name;
	}

	private void displayMenu(){
		System.out.println("Select an option " + playerName + ": \n" +
		"\t1) Play a full game of Countdown.\n" +
		"\t2) Play a single round in Countdown.\n" +
		"\t3) View the highscores.\n" +
		"\t4) Quit.\n");
		try{
			int input = scanner.nextInt();
			scanner.nextLine();
			switch(input){
			case 1:
				playFullGame();
				break;
			case 2:
				playSingleRound();
				break;
			case 3:
				viewHighScores();
				break;
			case 4:
				exitGame();
				break;
			default: 
				System.out.println("The number you entered does not represent an action.");
				break;
			}
		}
		catch(InputMismatchException e){
			System.out.println("Please input a number between 1 and 4.");
			scanner.next();
		}
	}

	

	private void playFullGame() {
		System.out.println("Play full game.\n");
		
	}

	private void playSingleRound() {
		System.out.println("Play single game.\n");
		
	}

	private void viewHighScores() {
		System.out.println("View high scores.\n");
		
	}
	
	private void exitGame() {
		scanner.close();
		System.out.println("Thank you for playing!");
		System.exit(0);
	}
	
}
