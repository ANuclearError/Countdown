import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The overall controller of Countdown, using the various menus
 * and game modes all wrapped together.
 * 
 * @author Aidan O'Grady, Kristine Semjonova
 */
public class Countdown {
	/**
	 * Player One and Two's Names, plus the format of a full game.
	 */
	private String format;
	/**
	 * Player One and Two's current scores, the current round, and the status of the timer.
	 */
	private int round, timer;
	/**
	 * User input handling, using System.in
	 */
	private Scanner scanner;
	/**
	 * Dictionary used for instances of Letters and Conundrum rounds.
	 */
	private Dictionary dictionary;
	/**
	 * Determines if it's a 1 or 2 player game.
	 */
	static int numberOfPlayers;
	
	private Player[] players;
	
	/**
	 * Constructor.
	 */
	public Countdown(){
		System.out.println("Welcome to Countdown!\n"); //Welcome message
		scanner = new Scanner(System.in);
		
		numberOfPlayers = getNumberOfPlayers();
		players = new Player[numberOfPlayers];
		for(int i = 0; i<numberOfPlayers; i++)
			players[i] = new Player(scanner, i+1);
		System.out.print("Hello " + players[0].name);
		if(numberOfPlayers == 2)
			System.out.print(" and " + players[1].name);
		System.out.println("\n");
		
		round = 0;
		timer = -1;
		format = "LLNLLLLNNLLLLNC";
		
		dictionary = new Dictionary("files/dictionary.txt");
		while(true)
			displayMenu();
	}
	
	/**
	 * Allows the user to choose number of players in the game.
	 * @return 1 or 2
	 */
	private int getNumberOfPlayers() {
		int n = -1;
		while (true) {
			try{
				System.out.println("Select number of players: \n" +
									"\t1) One player.\n" +
									"\t2) Two players.");
				n = scanner.nextInt();
				switch(n){ 
					case 1:
						return 1;
					case 2:
						return 2;
					default:
						System.out.println("Invalid input. Please input 1 or 2.\n");
				}	
			}		
			catch(InputMismatchException e){
				System.out.println("Invalid input. Please input 1 or 2.\n");
				scanner.next();
			}
		}
	}
	
	/**
	 * Used to print out a series of dashes to increase readability of output.
	 */
	private void lineBreak(){
		for(int i=0; i<80; i++)
			System.out.print("-");
		System.out.println("\n");
	}

	/**
	 * Displays the main menu of the game, and gets the user's next action.
	 */
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

	/**
	 * The sub-menu for playing a full game.
	 * Allows the user to choose if they are starting a fresh new game, resuming an old game, or returning to previous screen.
	 */
	private void fullGameMenu() {
		lineBreak();
		System.out.println("Please choose one of the following\n"
				+ "\t1) Start new game.\n"
				+ "\t2) Load game.\n"
				+ "\t3) Return.");
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
				System.out.println("Returning.\n");
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
	
	/**
	 * Starts a new game, resetting any variables used.
	 */
	private void startNewGame(){
		round = 0;
		players[0].score = 0;
		if(numberOfPlayers == 2)
			players[1].score = 0;
		setFormat();
		timer = setTimer(); //Is the timer desired?
		playFullGame();
	}
	
	/**
	 * Asks the user if they want the timer or not. Recursively calls itself if invalid answer.
	 * 
	 * @return 1 The timer is enabled.
	 * @return 0 The timer is disabled.
	 */
	private int setTimer() {
		System.out.print("Do you want a timer? Y/N: ");
		char answer = scanner.next().toLowerCase().charAt(0);
		if(answer == 'y')
			return 1;
		else if(answer == 'n')
			return 0;
		else
			System.out.println("Invalid response");
			return setTimer();
	}

	/**
	 * Allows the user to set the format of their full game, if it is to be deviated from default.
	 */
	private void setFormat(){
		System.out.println("Please input the format of this full game as a string so that:\n\tL=Letters Game\n\tN=Numbers Game\n\tC=Conundrum\n");
		while(true){
			String temp = scanner.next().toUpperCase();
			if(temp.matches("[CLN]*")){ //Ensures the user's input only contains these characters.
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
	}
	
	/**
	 * Controls the playing of the full game.
	 */
	private void playFullGame(){
		boolean loop = true;
		while(loop){
			loop = nextRound(); //Goes to next round.
		}
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String date = dateFormat.format(new Date());
		
		for(Player p:players){
			if(p.score > 0){
				Score score = new Score(p, date); //Creates score object.
				score.saveScore("files/highscore"); //Saves the score.
			}
			System.out.println(p.name + ": Your score was " + p.score + ".\n"); //Displays score.

		}
	}

	/**
	 * Starts the next round of the full game.
	 * @return If the game should continue.
	 */
	private boolean nextRound(){
		lineBreak();
		System.out.println("Please select an action.\n"
				+ "\t1) Next Round.\n" + "\t2) Save game.\n"
				+ "\t3) Quit.");
		try {
			int input = scanner.nextInt();
			scanner.nextLine();
			System.out.println();
			switch (input) {
			case 1:
				playSingleRound(format.charAt(round));
				round++;
				return (round < format.length());
			case 2:
				lineBreak();
				saveGame();
				return true;
			case 3:
				lineBreak();
				System.out.println("Quitting game.");
				return false;
			default:
				System.out.println("Your input does not represent an action.");
				return nextRound();
			}
		} catch (InputMismatchException e) {
			System.out.println("Please input a number between 1 and 4.");
			scanner.next();
			return nextRound();
		}
	}
	
	/**
	 * Saves an in progress game by writing its current status to a file.
	 */
	private void saveGame(){
		try{
			FileWriter fw = new FileWriter("files/save");
			BufferedWriter bw = new BufferedWriter(fw);
			String string = format + "|" + round;
			for(Player p:players)
				string += "|" + p.name + "|" + p.score;
			bw.write(string);
			bw.newLine();
			bw.close();
		} catch(IOException e){
			System.out.println("Error.");
		}
		System.out.println("Saving\n");
	}
	
	/**
	 * Resumes a previously saved game, reading the information from a file.
	 */
	private void resumeGame(){
		try{
			FileReader fr = new FileReader("files/save");
			BufferedReader br = new BufferedReader(fr);
			String string = br.readLine();
			br.close();
			
			StringTokenizer st = new StringTokenizer(string, "|");
			format = st.nextToken();
			round = Integer.parseInt(st.nextToken());
			players[0].name = st.nextToken();
			players[0].score = Integer.parseInt(st.nextToken());
			numberOfPlayers = 1;
			if(st.hasMoreTokens()){ //Determines whether the saved game is 2 player.
				players[1].name = st.nextToken();
				players[1].score = Integer.parseInt(st.nextToken());
				numberOfPlayers = 2;
			}
			
		} catch(IOException e){
			System.out.println("Error.");
		}
		System.out.println("Resuming\n");
		playFullGame();
		
	}

	/**
	 * Menu for single round games, to allow choice of which round is to be played.
	 */
	private void singleRoundMenu() {
		timer = -1; //Will be in game's class.
		displayMenu: while (true) {
			lineBreak();
			System.out.println("Which round would you like to play?\n"
					+ "\t1) Play a letters round.\n"
					+ "\t2) Play a numbers round.\n"
					+ "\t3) Play a conundrum round.\n" + "\t4) Return.");
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
			} catch (NoSuchElementException e){
				System.out.println("Error.");
				scanner.next();
			}
		}
	}

	/**
	 * Plays the game mode that is to be played, and returns the score.
	 * @param game Determines which game is being played.
	 * @return The score from that game.
	 */
	private void playSingleRound(char game) {
		lineBreak();
		switch(game){
			case 'L':
				Round letters = new LettersRound(dictionary, scanner, timer, players);
				letters.playGame(numberOfPlayers);
				break;
			case 'N':
				Round numbers = new NumbersRound(scanner, timer, players);
				numbers.playGame(numberOfPlayers);
				break;
			case 'C':
				Round conundrum = new Conundrum(dictionary, scanner, timer, players);
				conundrum.playGame(numberOfPlayers);
				break;
		}
		System.out.println();
		
	}

	/**
	 * Allows the user to see the high scores.
	 */
	private void viewHighScores() {
		lineBreak();
		HighScores highscores = new HighScores("files/highscore");
		highscores.viewHighScores();
		System.out.println();
	}
	
	/**
	 * Exits the game.
	 */
	private void exitGame() {
		System.out.println("Thank you for playing!");
		System.exit(0);
	}

}
