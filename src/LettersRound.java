import java.io.*;
import java.util.*;

public class LettersRound extends Round{

	private String letters;
	private ArrayList<Character> vowels, consonants;
	private Dictionary dictionary;

	public LettersRound(Dictionary dict, Scanner in, int timer){
		super(in, timer);
		vowels = readPool("files/vowels.txt");
		consonants = readPool("files/consonants.txt");
		dictionary = dict;
		letters = "";
	}

	public ArrayList<Character> readPool(String filename){
		ArrayList<Character> pool = new ArrayList<Character>();
		try {
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			while(line != null){
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
		String answer1 ="";
		String answer2 ="";
		switch (numberOfPlayers) {
		case 1:
			System.out.println("\nPlayer 1: You have 10s to input your solution.");
			answer1 = CountdownTimer.getAnswer(5);
			if (CountdownTimer.input == false) 
				answer1 = "";
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
			break;
		}
		
//		if (CountdownTimer.input == false) 
			submitSolution(answer1, answer2);
//		else 
//			submitSolution(answer);
		System.out.println("Player 1 scored: " + scoreSolution(answer1));
		System.out.println("Player 2 scored: " + scoreSolution(answer2));
		revealSolution();
		
	}

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

	private String generateLetters(){
		String tempLetters = "";
		int loop = getNoOfVowels();
		for(int i = 0; i< loop; i++)
			tempLetters += dealLetter(0);
		for(int i = 0; i < (9-loop); i++)
			tempLetters += dealLetter(1);
		return tempLetters;
	}

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

	private String removeChar(String string, int index){
		StringBuilder sb = new StringBuilder(string);
		sb.deleteCharAt(index);
		return sb.toString();
	}	

	private Boolean checkIfValid(String answer){
		String temp = letters.toLowerCase();
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

	public void revealSolution() {
		ArrayList<String> list = new ArrayList<String>();
		try{
			FileReader fr = new FileReader("files/dictionary.txt");
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			int length = player1Solution.length();
			if (player2Solution.length() > length)
				length = player2Solution.length();
			while(line != null){

				if(line.length() > length && checkIfValid(line)) {
					length = line.length();
					list.clear();
					list.add(line);
				}
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

		if(list.size() == 0)
			System.out.println("There are no solutions worthwhile solutions.");
		else{
			System.out.println("These are the best solutions");
			for(int i=0; i < list.size(); i++)
				System.out.println("\t" + list.get(i));
		}
	}

}
