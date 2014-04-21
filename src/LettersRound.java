import java.io.*;
import java.util.*;

public class LettersRound extends Round{

	/**
	 * The 9 letters given to the player.
	 */
	private String letters;
	/**
	 * The pools containing the possible vowels and consonants
	 */
	private ArrayList<Character> vowels, consonants;
	/**
	 * The given dictionary.
	 */
	private Dictionary dictionary;

	/**
	 * Dictionary
	 * @param dict The dictionary to be used.
	 * @param in Input scanner.
	 * @param timer Whether or not a timer is to be used.
	 * @param players A list of participating players.
	 */
	public LettersRound(Dictionary dict, Scanner in, int timer, Player[] players){
		super(in, timer, players);
		vowels = readPool("files/vowels.txt");
		consonants = readPool("files/consonants.txt");
		dictionary = dict;
		letters = "";
	}

	/**
	 * Reads a text file which shows the possible amount of times a letter can appear.
	 * @param filename The name of the file to be read.
	 * @return The list of characters to be drawn from later.
	 */
	public ArrayList<Character> readPool(String filename){
		ArrayList<Character> pool = new ArrayList<Character>();
		try {
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			while(line != null){ //There are characters ot be added.
				Scanner sc = new Scanner(line);
				char letter = sc.next().charAt(0);
				int number = sc.nextInt();
				for(int i=0; i<number; i++){
					pool.add(letter);
				}
				line = br.readLine();
				sc.close();
			}
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
		} catch (IOException e) {
			System.out.println("Error reading file.");
		} catch (InputMismatchException e) {
			System.out.println("Line not formatted properly.");
		}
		return pool;	
	}

	@Override
	public void playGame(int numberOfPlayers) {
		letters = generateLetters();
		String answer1 ="";
		String answer2 ="";
		if (timer) {
			System.out.println("Your letters are: " + letters + "\nYou have 30s to think.");
			CountdownTimer.setTimer(30);
			while (CountdownTimer.interval > 0) { 
				try {
					if(System.in.available() > 0)
						scanner.next();
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
			switch (numberOfPlayers) {
			case 1:
				System.out.println("\nYou have 10s to input your solution.");
				answer1 = CountdownTimer.getAnswer(5);
				if (CountdownTimer.input == false) 
					answer1 = "";
				System.out.println("You scored: " + scoreSolution(answer1));
				players[0].updateScore(scoreSolution(answer1));
				break;
			case 2:
				System.out.println("\nPlayer 1: You have 10s to input your solution.");
				answer1 = CountdownTimer.getAnswer(5);
				if (CountdownTimer.input == false) 
					answer1 = "";
				System.out.println("\nPlayer 2: You have 10s to input your solution.");
				answer2 = CountdownTimer.getAnswer(5);
				if (CountdownTimer.input == false) 
					answer2 = "";
				declareWinner(answer1, answer2);
				break;
			}
		}
		else {
			System.out.println("Your letters are: " + letters);
			switch (numberOfPlayers) {
			case 1:
				System.out.print("\nInput your solution: ");
				answer1 = scanner.next();
				System.out.println("You scored: " + scoreSolution(answer1));
				players[0].updateScore(scoreSolution(answer1));
				break;
			case 2:
				System.out.println("\nPlayer 1: Input your solution: ");
				answer1 = scanner.next();
				System.out.println("\nPlayer 2: Input your solution: ");
				answer2 = scanner.next();
				declareWinner(answer1, answer2);
				break;
			}
		}
		submitSolution(answer1, answer2);
		revealSolution();	
	}

	/**
	 * Declares the winner of the round in a 2 player game.
	 * @param answer1 Player one's answer
	 * @param answer2 Player two's answer.
	 */
	private void declareWinner(String answer1, String answer2){
		if(answer1.length() > answer2.length()){
			System.out.println(players[0].name + " scores " + scoreSolution(answer1) + " points.");
			players[0].updateScore(scoreSolution(answer1));
		} else if(answer1.length() < answer2.length()){
			System.out.println(players[1].name + " scores " + scoreSolution(answer2) + " points.");
			players[1].updateScore(scoreSolution(answer2));
		} else{
			System.out.println("Both players score " + scoreSolution(answer2) + " points.");
			players[0].updateScore(scoreSolution(answer1));
			players[1].updateScore(scoreSolution(answer2));
		}
	}
	
	/**
	 * Asks a player how many vowels they would like in the game.
	 * @return The chosen number.
	 */
	private int getNoOfVowels(){
		int number;
		System.out.print("How many vowels would you like? Choose 3, 4 or 5: ");
		while(true){
			try {
				number = scanner.nextInt();
				if(number >= 3 && number < 6){
					break;
				}
				else{
					System.out.println("Entered number out of scope, please enter 3, 4 or 5.");
					continue;
				}
			} catch (InputMismatchException e) {
				System.out.println("No number in input, please enter a number.");
				scanner.next();
				continue;
			}
		}
		return number;
	}

	/**
	 * Randomly generates a string based on 9 random letters, split by number of vowels.
	 * @return The 9 letters.
	 */
	private String generateLetters(){
		String tempLetters = "";
		int loop = getNoOfVowels();
		for(int i = 0; i< loop; i++)
			tempLetters += dealLetter(0);
		for(int i = 0; i < (9-loop); i++)
			tempLetters += dealLetter(1);
		return tempLetters;
	}

	/**
	 * Choses a random character from 1 of 2 lists.
	 * @param option The list to be chosen from.
	 * @return The random character.
	 */
	private char dealLetter(int option){
		char returnVal = 'a';
		Random random = new Random();
		if(option == 0){
			int index = random.nextInt(vowels.size());
			returnVal = vowels.get(index);
			vowels.remove(index);
		}
		else{
			int index = random.nextInt(consonants.size());
			returnVal = consonants.get(index);
			consonants.remove(index);
		}
		return returnVal;
	}

	/**
	 * Removes a character from a string
	 * @param string to remove from.
	 * @param index The index of the character to be removed.
	 * @return The string without removed character.
	 */
	private String removeChar(String string, int index){
		StringBuilder sb = new StringBuilder(string);
		sb.deleteCharAt(index);
		return sb.toString();
	}	

	/**
	 * Checks whether or not the given word can be made from the letters.
	 * @param answer The answer to be checked.
	 * @return if possible or not.
	 */
	private Boolean checkIfValid(String answer){
		String temp = letters.toLowerCase();
		answer = answer.toLowerCase();
		boolean valid = true;
		for(int i=0; i<answer.length(); i++){
			char character = answer.charAt(i);
			if(temp.indexOf(character) >= 0){
				temp = removeChar(temp, temp.indexOf(character));
			}
			else
				valid = false;
		}
		return valid;
	}

	@Override
	public boolean checkSolution(String playerSolution) {
		return (dictionary.findWord(playerSolution) && checkIfValid(playerSolution));
	}

	@Override
	public int scoreSolution(String playerSolution) {
		if(checkSolution(playerSolution) && playerSolution.length() < 9)
			return playerSolution.length();
		if(checkSolution(playerSolution))
			return 18;
		return 0;
	}

	/**
	 * Reveals the best solution the computer can come up with.
	 */
	public void revealSolution() {
		ArrayList<String> list = new ArrayList<String>();
		try{
			FileReader fr = new FileReader("files/dictionary.txt");
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			int length = 0;
			if (playerSolutions[0].length() <= 9)
				length = playerSolutions[0].length();
			if (playerSolutions[1].length() > length && playerSolutions[1].length() <=9)
				length = playerSolutions[1].length();
			while(line != null){

				//A word has been found better than all currently in list.
				if(line.length() > length && checkIfValid(line)) {
					length = line.length();
					list.clear();
					list.add(line); 
				}
				//A word has been found that should be in list.
				else if(line.length() == length && checkIfValid(line)){
					list.add(line);
				}
				line = br.readLine();
			}
			br.close();
		}
		catch (FileNotFoundException e){
			System.out.println("File not found.");
		}
		catch (IOException e){
			System.out.println("Error reading file. ");
		}
		catch (NoSuchElementException e){
			System.out.println("Error reading file. ");
		}

		//Prints out the list, depending on the number of solutions.
		if(list.size() == 0)
			System.out.println("There are no worthwhile solutions.");
		else{
			System.out.println("These are the best solutions");
			for(int i=0; i < list.size(); i++)
				System.out.println("\t" + list.get(i));
		}
	}
}
