import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

public class NumbersRound extends Round{

	private ArrayList<Integer> chosenNumbers, largeNumbers, smallNumbers;
	private ArrayList<String> finalSolution;
	private int target, result, calculations, answer1, answer2;
	private boolean isSolution;

	public NumbersRound(Scanner in, int timer, Player[] players){
		super(in, timer, players);
		smallNumbers = new ArrayList<Integer>();
		largeNumbers = new ArrayList<Integer>();
		finalSolution = new ArrayList<String>();

		fillArrays();
		chosenNumbers = generateNumbers();
		target = generateTarget();
		answer1 = 0;
		answer2 = 0;
		calculations = 0;
		result = 0;
		isSolution = false;
	}

	private void fillArrays() {
		for (int i = 1; i < 3; i++) {
			for (int j = 1; j < 11; j++)
				smallNumbers.add(j);
		}
		for (int i = 25; i < 125; i += 25)
			largeNumbers.add(i);		
	}

	private int getLargeNumbers(){
		int lnumber = -1;
		System.out.print("How many large numbers do you want?: (0-4) ");
		while (true) {
			try {
				lnumber = scanner.nextInt();
				if (lnumber > -1 && lnumber < 5)
					break;
				else {
					System.out.println("Entered invalid number of large numbers. Choose between 0 and 4.");
					continue;
				}
			}
			catch (InputMismatchException e) {
				System.out.println("No number provided. Try again.");
				scanner.next();
			}
		}
		return lnumber;
	}

	private ArrayList<Integer> generateNumbers() {
		int large = getLargeNumbers();
		ArrayList<Integer> temp = new ArrayList<Integer>();
		getSubList(largeNumbers, temp, large);
		getSubList(smallNumbers, temp, 6-large);
		Collections.sort(temp);
		return temp;
	}

	private ArrayList<Integer> getSubList(ArrayList<Integer> list, ArrayList<Integer> temp, int index){
		Random random = new Random();
		for (int i = 0; i < index; i ++ ) {
			int rand = random.nextInt(list.size());
			temp.add(list.get(rand));
			list.remove(rand);	
		}
		return temp;
	}

	private int generateTarget() {
		Random random = new Random();
		return random.nextInt(899) + 101;
	}

	@Override
	public void playGame(int numberOfPlayers) {
		System.out.println(target);
		System.out.println(chosenNumbers);
		if(timer){
			System.out.println("You have 30s to think.");
			CountdownTimer.setTimer(30);
			while (CountdownTimer.interval > 0) 
			{ 
				try {
					if(System.in.available() > 0)
						scanner.next();
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
		}
		submitInitialAnswer(numberOfPlayers);
		int score1 = 0;
		int score2 = 0;
		switch (numberOfPlayers) {
		case 1:
			if (answer1 >= (target - 10) && answer1 <= (target + 10)) {
				if (checkSolution(Integer.toString(answer1)))
					score1 = scoreSolution(Integer.toString(answer1));
					players[0].updateScore(score1);
			}
			System.out.println("You scored: " + score1 );
			break;
		case 2:
			if (answer1 >= (target - 10) && answer1 <= (target + 10)) {
				if (checkSolution(Integer.toString(answer1)))
					score1 = scoreSolution(Integer.toString(answer1));
			}
			if (answer2 >= (target - 10) && answer2 <= (target + 10)) {
				if (checkSolution(Integer.toString(answer2)))
					score2 = scoreSolution(Integer.toString(answer2));
			}
			declareWinner(score1, score2);
			break;
		}
		if((score1 < 10) || (score2 < 10))
			calculations = 1000;
		revealSolution();
	}
	
	private void declareWinner(int score1, int score2){
		if(score1 > score2){
			System.out.println(players[0].name + " scores " + score1 + " points.");
			players[0].updateScore(score1);
		} else if(score1 < score2){
			System.out.println(players[1].name + " scores " + score2 + " points.");
			players[1].updateScore(score2);
		} else{
			System.out.println("Both players score " + score2 + " points.");
			players[0].updateScore(score1);
			players[1].updateScore(score2);
		}
	}

	public void submitInitialAnswer(int numberOfPlayers){
		if (timer) {
			switch (numberOfPlayers) {
			case 1:
				System.out.print("\nWhat is your answer? (5s): ");
				try {
					answer1 = Integer.parseInt(CountdownTimer.getAnswer(5));
				} catch (NumberFormatException e) {
					System.out.println("No number in input.");
				}
				break;
//				if (CountdownTimer.input == false)
//					answer1 = 0;
			case 2:
				System.out.print("\nPlayer1: What is your answer? (5s): ");
				try {
					answer1 = Integer.parseInt(CountdownTimer.getAnswer(5));
				} catch (NumberFormatException e) {
					System.out.println("No number in input.");
				}
				System.out.print("\nPlayer2: What is your answer? (5s): ");
				try {
					answer2 = Integer.parseInt(CountdownTimer.getAnswer(5));
				} catch (NumberFormatException e) {
					System.out.println("No number in input.");
				}
				break;
//				if (CountdownTimer.input == false)
//					temp = 0;
			}
			
		}
		else{
			switch (numberOfPlayers) {
			case 1:
				System.out.print("\nWhat is your answer? : ");
				answer1 = scanner.nextInt();
				break;
			case 2:
				System.out.print("\nPlayer1: What is your answer? : ");
				answer1 = scanner.nextInt();
				System.out.print("\nPlayer2: What is your answer? : ");
				answer2 = scanner.nextInt();
				break;
			}
		}
		submitSolution(Integer.toString(answer1), Integer.toString(answer2));
	}

	@Override
	public boolean checkSolution(String playerSolution) {
		int playerAnswer = Integer.parseInt(playerSolution);
		int answer = 0;
		ArrayList<Integer> numPool = new ArrayList<Integer>();
		for(int num : chosenNumbers)
			numPool.add(num);
		scanner.nextLine();
		if(timer)
			CountdownTimer.setTimer(15);
		while((numPool.size() != 1) && (CountdownTimer.interval > 0)){
			Collections.sort(numPool);
			System.out.println("Numbers: " + numPool + "\nTarget: " + playerAnswer);
			System.out.print("Please input calculation of form \"int op int\":\t");
			try{
				String line = scanner.nextLine();
				if (line.toLowerCase().equals("quit") || line.toLowerCase().equals("exit")) {
					return false;
				}
				Scanner in = new Scanner(line);
				int int1 = in.nextInt();
				String op = in.next();
				int int2 = in.nextInt();
				in.close();
				if (int1 == int2 && Collections.frequency(numPool, int1) < 2) {
					System.out.println("You invalidly used the same number twice.");
				}
				else{
					if (numPool.contains(int1) && numPool.contains(int2)) {
						answer = performCalculation(int1, int2, op);
						if (answer != 0) {
							calculations++;
							System.out.println(answer);
							if (answer != playerAnswer) {
								numPool.remove(numPool.indexOf(int1));
								numPool.remove(numPool.indexOf(int2));
								numPool.add(answer);
							} else
								return true;
						} else
							System.out.println("Invalid operation");
					} else
						System.out.println("Invalid input.");
				}
			}
			catch(InputMismatchException e){
				System.out.println("Invalid input1");
			} catch(NoSuchElementException e){
				System.out.println("Invalid input2");
			}
		}
		System.out.println("Timeout!");
		return false;
	}


	@Override
	public int scoreSolution(String playerSolution) {
		int answer = Integer.parseInt(playerSolution);
		if (answer == target)
			return 10;
		if (answer >= (target - 5) && answer <= (target + 5))
			return 7;
		
		return 5;
	}

	@Override
	public void revealSolution() {
		ArrayList<String> progress_so_far = new ArrayList<String>();
		boolean check = searchSolution(chosenNumbers, progress_so_far, target);
		if (check && calculations > finalSolution.size()){
			System.out.println("Suggested solution: ");
			for (int i = 0; i < finalSolution.size(); i++) {
				System.out.println(finalSolution.get(i));
			}
		}
	}

	private boolean searchSolution(ArrayList<Integer> numbers, ArrayList<String> progress_so_far, int target_num) {
		String[] op = { "+", "-", "*", "/" };
		Collections.sort(numbers);

		for (int a = 0; a < numbers.size(); a++) {
			for (int b = a + 1; b < numbers.size(); b++) {
				int x = numbers.get(a);
				int y = numbers.get(b);

				for (int c = 0; c < op.length; c++) {
					result = performCalculation(x, y, op[c]);

					if (result > 0) {
						if (result == target_num) {

							isSolution = true;
							progress_so_far.add(x + " " + op[c] + " " + y + " = " + result);

							if(progress_so_far.size() < finalSolution.size() || finalSolution.isEmpty()){
								finalSolution.clear();
								finalSolution = progress_so_far;
							}

						} else if (numbers.size() > 2) {
							ArrayList<Integer> new_numbers = new ArrayList<Integer>(numbers);
							ArrayList<String> new_progress = new ArrayList<String>(progress_so_far);


							new_numbers.remove(numbers.get(a));
							new_numbers.remove(numbers.get(b));
							new_numbers.add(result);

							Collections.sort(new_numbers);

							new_progress.add(x + " " + op[c] + " " + y + " = " + result);
							searchSolution(new_numbers, new_progress, target_num);
						}
					}
				}
			}
		}
		return isSolution;	
	}

	private int performCalculation(int int1, int int2, String op) {
		if(op.equals("+"))
			return int1 + int2;
		if(op.equals("-"))
			return int1 - int2;
		if(op.equals("x") || op.equals("*"))
			return int1 * int2;
		if(op.equals("/") && ((int1 % int2) == 0 ) && int2 != 1)
			return int1 / int2;
		else
			return 0;
	}

}
