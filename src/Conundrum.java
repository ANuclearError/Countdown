import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Conundrum extends Round{
	
	private String original, anagram;
	private Dictionary dictionary;
	
	public Conundrum(Dictionary dict, Scanner in, int timer, Player[] players){
		super(in, timer, players);
		dictionary = dict;
		original = generateOriginal();
		anagram = generateAnagram();
	}

	private String generateOriginal() {
		String word = dictionary.getRandomWord();
		if(word.length() == 9)
			return word;
		return generateOriginal();
	}
	
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

	public void playGame(int numberOfPlayers){
		String answer1="";
		String answer2="";
		System.out.println("The anagram is: " + anagram + " " + original);
		if (timer) {
			switch (numberOfPlayers) {
			case 1:
				System.out.print("\nYour Answer (you have 30s): ");
				answer1 = CountdownTimer.getAnswer(30);
				if (CountdownTimer.input == false)
					submitSolution("", answer2);
				else
					submitSolution(answer1, answer2);
				//scores
				System.out.println("Scores 1player should be here.");
				break;
			case 2:
				System.out.println("The player who first finds the answer" + 
									" type in your number (1 - player1, 2 -player2)");
				int nofplayer = 0;
				int pause = 0; //remaining time
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
						if (answer1 == null)
							answer1 = "";
						if (!checkSolution(answer1)) {
							System.out.println("Player 2: Type in the solution.");
							answer2 = CountdownTimer.getAnswer(pause);
							if (answer2 == null)
								answer2 = "";
							checkSolution(answer2);
						}
					}				
					else {
						answer2 = CountdownTimer.getAnswer(5);
						if (answer2 == null)
							answer2 = "";
						if (!checkSolution(answer2)) {
							System.out.println("Player 1: Type in the solution.");
							answer2 = CountdownTimer.getAnswer(pause);
							if (answer2 == null)
								answer2 = "";
							checkSolution(answer2);
						}
					}
				
				}
				else
					System.out.println("Timeout.");
				
				//scores
				System.out.println("Scores 2player should be here.");
			}			
		}
				
		else{
			switch (numberOfPlayers) {
			case 1:
				System.out.print("\nYour Answer: ");
				answer1 = scanner.next();
				submitSolution(answer1, answer2);
				//scores
				System.out.println("Scores 1player should be here.");
				break;
			case 2:
				System.out.println("The player who first finds the answer - " + 
									" type in your number (1 - player1, 2 -player2)");
				int nofplayer = 0;
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
				//scores here
				System.out.println("Scores 2players should be here.");
			}			
		}
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
