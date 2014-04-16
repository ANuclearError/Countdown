import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Countdown {
	private String playerName, format;
	private int playerScore, round;
	private Scanner scanner;
	private ArrayList<Score> highscores;
	
	public Countdown(){
		System.out.println("Welcome to Countdown!\n");
		scanner = new Scanner(System.in);
		playerName = getPlayerName();
		playerScore = 0;
		round = 0;
		format = "LLNLLLLNNLLLLNC";
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
		System.out.println("Hello " + name + ".\n");
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
				startNewGame();
				break;
			case 2:
				lineBreak();
				resumeGame();
				break;
			case 3:
				lineBreak();
				System.out.println("Returning.");
				break;
			default:
				System.out.println("Your input does not represent an action.");
				break;
			}
		} catch (InputMismatchException e) {
			System.out.println("Please input a number between 1 and 4.");
			scanner.next();
		}
	}	
	
	private void startNewGame(){
		System.out.println("Please input the format of this full game as a string so that:\n\tL=Letters Game\n\tN=Numbers Game\n\tC=Conundrum\n");
		while(true){
			String temp = scanner.next().toUpperCase();
			if(temp.matches("[CLN]*")){
				format = temp;
				break;
			} else if(temp.equals("DEFAULT")){
				break;
			}
			else{
				System.out.println("Invalid format.");
				continue;
			}
		}
		System.out.println("Format chosen: " + format + "\n");
		playFullGame();
	}

	private void nextRound(){
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
				playerScore += playSingleRound(format.charAt(round));
				break;
			case 2:
				lineBreak();
				saveGame();
				break;
			case 3:
				lineBreak();
				System.out.println("Quitting game.");
				break;
			default:
				System.out.println("Your input does not represent an action.");
			}
		} catch (InputMismatchException e) {
			System.out.println("Please input a number between 1 and 4.");
			scanner.next();
		}
	}
	
	private void saveGame(){
		try{
			FileWriter fw = new FileWriter("files/save");
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(playerName);
			bw.newLine();
			bw.write(format);
			bw.newLine();
			bw.write(String.valueOf(round));
			bw.newLine();
			bw.write(String.valueOf(playerScore));
			bw.newLine();
			bw.close();
		} catch(IOException e){
			System.out.println("Error.");
		}
		System.out.println("Saving\n");
	}
	
	private void resumeGame(){
		try{
			FileReader fr = new FileReader("files/save");
			BufferedReader br = new BufferedReader(fr);
			playerName = br.readLine();
			format = br.readLine();
			String line = br.readLine();
			Scanner sc = new Scanner(line);
			round = sc.nextInt();
			line = br.readLine();
			sc.close();
			sc = new Scanner(line);
			playerScore = sc.nextInt();
			sc.close();
			br.close();
		} catch(IOException e){
			System.out.println("Error.");
		}
		System.out.println("Resuming\n");
		playFullGame();
		
	}
	
	private void playFullGame(){
		lineBreak();
		while(round < format.length()){
			nextRound();
			round++;
		}
		System.out.println(playerName + ": Your score was " + playerScore + ".\n");
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
					playSingleRound('L');
					break;
				case 2:
					playSingleRound('N');
					break;
				case 3:
					playSingleRound('C');
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

	private int playSingleRound(char game) {
		lineBreak();
		int result = 0;
		switch(game){
			case 'L':
				Round letters = new LettersRound(new Dictionary("files/dictionary.txt"), scanner);
				letters.playGame();
				result = letters.scoreSolution();
				break;
			case 'N':
				Round numbers = new NumbersRound(scanner);
				numbers.playGame();
				result = numbers.scoreSolution();
				break;
			case 'C':
				Round conundrum = new Conundrum(new Dictionary("files/smalldictionary.txt"), scanner);
				conundrum.playGame();
				result = conundrum.scoreSolution();
				break;
		}
		System.out.println();
		return result;
		
	}
	
	private void readHighScores(){
		FileReader fr;
		try {
			fr = new FileReader("files/highscore");
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			while(line != null){
				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void viewHighScores() {
		System.out.println("View high scores.\n");
		System.out.println("Score\tName\tDate");
		Collections.sort(highscores);
		int i = 0;
		while(i<10 && i<highscores.size()){
			System.out.println(highscores.get(i).toString());
			i++;
		}
	}
	
	private void exitGame() {
		System.out.println("Thank you for playing!");
		System.exit(0);
	}

}
