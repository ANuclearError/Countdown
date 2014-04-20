import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Conundrum extends Round{
	
	private String original, anagram;
	private Dictionary dictionary;
	
	public Conundrum(Dictionary dict, Scanner in){
		super(in);
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

	public void playGame(){
		System.out.println("The anagram is: " + anagram + "\nYour Answer (you have 30s): ");
		String answer = CountdownTimer.getAnswer(30);
		if (CountdownTimer.input == false) 
			submitSolution("");
		else 
			submitSolution(answer);
		if(scoreSolution() > 0){
			System.out.println("You scored: 10");
		}
		else{
			revealSolution();
			System.out.println("You scored: 0");
		}
	}
		
	@Override
	public boolean checkSolution() {
		if(playerSolution.equals(original))
			return true;
		return false;
	}
	
	@Override
	public int scoreSolution() {
		if(checkSolution())
			return 10;
		return 0;
	}
	
	@Override
	public void revealSolution() {
		System.out.println("The answer was: " + original + ".");		
	}

}
