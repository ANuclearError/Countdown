import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 * An implementation of Countdown's Conundrum round, in which the players
 * are given a 9 letter anagram they must solve.
 * 
 * @author Aidan O'Grady, Kristine Semjonova
 *
 */
public class Conundrum extends Round{

	/**
	 * The word to be solved and the anagram given to the player.
	 */
	private String original, anagram;
	/**
	 * The dictionary which is used to determine the word.
	 */
	private Dictionary dictionary;

	/**
	 * Constructor
	 * @param dict The dictionary to be used.
	 * @param in Input scanner.
	 * @param timer Whether or not a timer is to be used.
	 * @param players A list of participating players.
	 */
	public Conundrum(Dictionary dict, Scanner in, int timer, Player[] players){
		super(in, timer, players);
		dictionary = dict;
		original = generateOriginal();
		anagram = generateAnagram();
	}

	/**
	 * Chooses a random word from the dictionary to be used in the game.
	 * @return The chosen word.
	 */
	private String generateOriginal() {
		String word = dictionary.getRandomWord();
		if(word.length() == 9) //This was deemed quicker than getting every 9 letter word beforehand.
			return word;
		return generateOriginal(); //If the word is not useful. 
	}

	/**
	 * Now that there is a chosen word, an anagram must be made form it.
	 * @return The anagram displayed to the player.
	 */
	private String generateAnagram() {
		Random random = new Random();
		String conundrum = "";
		ArrayList<Character> list = new ArrayList<Character>();

		for(int i = 0; i < original.length(); i++){ //For every character in target.
			list.add(original.charAt(i));
		}

		while(!list.isEmpty()){ //While there is a character to be added.
			int next = random.nextInt(list.size()); //Take random index.
			conundrum += list.get(next);
			list.remove(next); //Character is removed to prevent duplication.
		}

		return conundrum;	
	}

	@Override
	public void playGame(int numberOfPlayers){
		String answer1="";
		String answer2="";
		System.out.println("The anagram is: " + anagram + " " + original);
		if (timer) {
			switch (numberOfPlayers) {
			case 1: //One player game with timer.
				timerOnePlayer(answer1);
				break;
			case 2: //Two player game with timer.
				timerTwoPlayer(answer1, answer2);
				break;
			}			
		}

		else{
			switch (numberOfPlayers) {
			case 1: //One player game without timer.
				noTimerOnePlayer(answer1);
				break;
			case 2: //Two player game without timer.
				noTimerTwoPlayer(answer1, answer2);
				break;
			}			
		}
	}

	
	public void timerOnePlayer(String answer1){
		System.out.print("\nYour Answer (you have 30s): ");
		answer1 = CountdownTimer.getAnswer(30);
		submitSolution(answer1, "");
		System.out.println("You scored: " + scoreSolution(answer1));
		players[0].updateScore(scoreSolution(answer1));
	}
	
	/**
	 * Playing the game with 2 players, and a timer.
	 * @param answer1 Player One's answer.
	 * @param answer2 Player Two's answer.
	 */
	public void timerTwoPlayer(String answer1, String answer2){
		System.out.println("The player who first finds the answer - " + 
				" type in your number (1 - Player1, 2 - Player2)");
		int nofplayer = 0; //number of player who buzzed in first
		int pause = 0; //to store remaining time after buzzer pressed
		CountdownTimer.setTimer(30);
		while (CountdownTimer.interval > 0) {
			try {
				System.out.println("Please input 1 or 2.");
				nofplayer = scanner.nextInt();
				if (nofplayer == 1 || nofplayer == 2) {							
					pause = CountdownTimer.interval; 
					CountdownTimer.timer.cancel();
					break;
				}
			}
			catch(InputMismatchException e){
				scanner.next();}
		}

		if (pause > 0) {
			System.out.println("Player " + nofplayer + ": Type in the solution.");
			if (nofplayer == 1) {
				answer1 = CountdownTimer.getAnswer(5);
				if (!checkSolution(answer1)) {
					System.out.println("Player 2: Type in the solution.");
					answer2 = CountdownTimer.getAnswer(pause);
					checkSolution(answer2);
				}
			}				
			else {
				answer2 = CountdownTimer.getAnswer(5);
				if (!checkSolution(answer2)) {
					System.out.println("Player 1: Type in the solution.");
					answer2 = CountdownTimer.getAnswer(pause);
					checkSolution(answer2);
				}
			}

		}
		else
			System.out.println("Timeout.");

		System.out.println("Player 1 scored:" + scoreSolution(answer1));
		players[0].updateScore(scoreSolution(answer1));
		System.out.println("Player 2 scored:" + scoreSolution(answer2));
		players[1].updateScore(scoreSolution(answer2));

	}
	
	/**
	 * Playing the game with 1 player, and no timer.
	 * @param answer1 Player One's answer.
	 */
	public void noTimerOnePlayer(String answer1){
		System.out.print("\nYour Answer: ");
		answer1 = scanner.next();
		submitSolution(answer1, "");
		//scores
		System.out.println("You scored: " + scoreSolution(answer1));
		players[0].updateScore(scoreSolution(answer1));
	}

	/**
	 * Playing the game with 2 players, and no timer.
	 * @param answer1 Player One's answer.
	 * @param answer2 Player Two's answer.
	 */
	public void noTimerTwoPlayer(String answer1, String answer2){
		System.out.println("The player who first finds the answer - " + 
				" type in your number (1 - player1, 2 -player2)");
		int nofplayer = 0; //stores number of player who buzzed in first
		while (true) {
			try {
				System.out.println("Please input 1 or 2.");
				nofplayer = scanner.nextInt();
				if (nofplayer == 1 || nofplayer == 2) 							
					break;
				continue;
			}
			catch(InputMismatchException e){
				scanner.next();}
		}
		System.out.println("Player " + nofplayer + ": Type in the solution.");
		if (nofplayer == 1) {
			answer1 = scanner.next();
			if (!checkSolution(answer1)) {
				System.out.println("Player 2: Type in the solution.");
				answer2 = scanner.next();
				checkSolution(answer2);
			}
		}
		else {
			answer2 = scanner.next();
			if (!checkSolution(answer2)) {
				System.out.println("Player 1: Type in the solution.");
				answer2 = scanner.next();
				checkSolution(answer2);
			}
		}
		System.out.println("Player 1 scored: " + scoreSolution(answer1));
		players[0].updateScore(scoreSolution(answer1));
		System.out.println("Player 2 scored: " + scoreSolution(answer2));
		players[1].updateScore(scoreSolution(answer2));
	}

	@Override
	public boolean checkSolution(String playerSolution) {
		if(playerSolution.equals(original))
			return true;
		return false;
	}

	@Override
	public int scoreSolution(String playerSolution) {
		if(checkSolution(playerSolution))
			return 10;
		return 0;
	}

	@Override
	public void revealSolution() {
		System.out.println("The answer was: " + original + ".");		
	}

}
