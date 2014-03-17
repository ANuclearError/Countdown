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
		System.out.println("Hello " + playerName + ". \n");
		round = 0;
		while(true)
			displayMenu();
	}
		
	private void lineBreak(){
		for(int i=0; i<80; i++)
			System.out.print("-");
		System.out.println("\n");
	}
	
	private String getPlayerName() {
		System.out.print("Please enter your name: ");
		String name = scanner.nextLine();
		return name;
	}

	private void displayMenu(){
		lineBreak();
		System.out.println("Select an option: \n" +
		"\t1) Play a full game of Countdown.\n" +
		"\t2) Play a single round in Countdown.\n" +
		"\t3) View the highscores.\n" +
		"\t4) Quit.");
		try{
			int input = scanner.nextInt();
			scanner.nextLine();
			System.out.println();
			switch(input){
			case 1:
				fullGameMenu();
				break;
			case 2:
				singleRoundMenu();
				break;
			case 3:
				lineBreak();
				viewHighScores();
				break;
			case 4:
				lineBreak();
				exitGame();
				break;
			default: 
				System.out.println("Your input does not represent an action.");
				break;
			}
		}
		catch(InputMismatchException e){
			System.out.println("Please input a number between 1 and 4.");
			scanner.next();
		}
	}

	private void fullGameMenu() {
		displayMenu: while (true) {
			lineBreak();
			System.out.println("Please choose one of the following\n"
					+ "\t1) Start new game.\n"
					+ "\t2) Load game.\n"
					+ "\t3) Return.\n");
			try {
				int input = scanner.nextInt();
				scanner.nextLine();
				System.out.println();
				switch (input) {
				case 1:
					lineBreak();
					playFullGame();
					break;
				case 2:
					lineBreak();
					resumeGame();
					break;
				case 3:
					lineBreak();
					System.out.println("Returning.\n");
					break displayMenu;
				default:
					System.out.println("Your input does not represent an action.");
					break;
				}
			} catch (InputMismatchException e) {
				System.out.println("Please input a number between 1 and 4.");
				scanner.next();
			}
		}	
	}

	private void singleRoundMenu() {
		displayMenu: while (true) {
			lineBreak();
			System.out.println("Which round would you like to play?\n"
					+ "\t1) Play a letters round.\n"
					+ "\t2) Play a numbers round.\n"
					+ "\t3) Play a conundrum round.\n" + "\t4) Return.\n");
			try {
				int input = scanner.nextInt();
				scanner.nextLine();
				System.out.println();
				switch (input) {
				case 1:
					lineBreak();
					playSingleRound("Letters");
					break;
				case 2:
					lineBreak();
					playSingleRound("Numbers");
					break;
				case 3:
					lineBreak();
					playSingleRound("Conundrum");
					break;
				case 4:
					lineBreak();
					System.out.println("Returning.\n");
					break displayMenu;
				default:
					System.out.println("Your input does not represent an action.");
					break;
				}
			} catch (InputMismatchException e) {
				System.out.println("Please input a number between 1 and 4.");
				scanner.next();
			}
		}
	}
	
	private void playFullGame(){
		System.out.println("Full game selected.\n");
		loop: while (true) {
			lineBreak();
			System.out.println("Please select an action.\n"
					+ "\t1) Next Round.\n" + "\t2) Save game.\n"
					+ "\t3) Quit.\n");
			try {
				int input = scanner.nextInt();
				scanner.nextLine();
				System.out.println();
				switch (input) {
				case 1:
					lineBreak();
					System.out.println("Starting next round.\n");
					break;
				case 2:
					lineBreak();
					saveGame();
					break;
				case 3:
					lineBreak();
					System.out.println("Quitting game.\n");
					break loop;
				default:
					System.out.println("Your input does not represent an action.");
					continue;
				}
			} catch (InputMismatchException e) {
				System.out.println("Please input a number between 1 and 4.");
				scanner.next();
			}
		}

	}
	
	private void saveGame(){
		System.out.println("Saving\n");
	}
	
	private void resumeGame(){
		System.out.println("Resuming\n");
	}

	private void playSingleRound(String game) {
		switch(game){
		case "Letters":
			System.out.println("Letters round.\n");
			break;
		case "Numbers":
			System.out.println("Numbers round.\n");
			break;
		case "Conundrum":
			System.out.println("Conundrum round.\n");
			break;
		default:
			System.out.println("Error.\n");	
		}
		
	}

	private void viewHighScores() {
		System.out.println("View high scores.\n");
	}
	
	private void exitGame() {
		System.out.println("Thank you for playing!");
		System.exit(0);
	}
}
