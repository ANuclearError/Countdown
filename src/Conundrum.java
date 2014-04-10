import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class Conundrum extends Round{
	
	private String original;
	private String anagram;
	private Dictionary dictionary;
	
	
	public Conundrum(Dictionary dict){
		super();
		dictionary = dict;
		original = generateOriginal();
		anagram = generateAnagram();
		playGame();
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
		System.out.println("The anagram is: " + anagram + "\nPlease put in your answer.");
		Scanner sc = new Scanner(System.in);
		String answer = sc.next();
		submitSolution(answer);
		if(scoreSolution() > 0){
			System.out.println("Well done, you were correct");
		}
		else{
			revealSolution();
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
		System.out.println("Unlucky, the answer was: " + original);		
	}

}
