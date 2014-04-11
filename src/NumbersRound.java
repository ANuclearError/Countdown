import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

public class NumbersRound extends Round{

	private ArrayList<Integer> chosenNumbers, largeNumbers, smallNumbers;
	private ArrayList<String> finalSolution;
	private int target, answer, result, calculations;
	private boolean isSolution;

	public NumbersRound(Scanner in){
		super(in);
		smallNumbers = new ArrayList<Integer>();
		largeNumbers = new ArrayList<Integer>();
		finalSolution = new ArrayList<String>();

		fillArrays();
		chosenNumbers = generateNumbers();
		target = generateTarget();
		answer = 0;
		calculations = 0;
		result = 0;
		isSolution = false;
		playGame();
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
		System.out.println("How many large numbers do you want? (0-4) ");
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
	public void playGame() {
		System.out.println(target);
		System.out.println(chosenNumbers);
		answer = submitInitialAnswer();
		int score = scoreSolution();
		if(answer != 0)
			System.out.println("You scored: " + score);
		else
			System.out.println("You scored: 0");
		if(score != 10)
			calculations = 1000;
		revealSolution();
	}

	public int submitInitialAnswer(){
		int temp = 0;
		System.out.println("What is your answer?");
		while(true){
			try{
				temp = scanner.nextInt();
				break;
			} catch (InputMismatchException e) {
				System.out.println("No number in input, please enter a number.");
				scanner.next();
				continue;
			}
		}
		submitSolution(Integer.toString(temp));
		return temp;
	}

	@Override
	public boolean checkSolution() {
		int answer = 0;
		ArrayList<Integer> numPool = new ArrayList<Integer>();
		for(int num : chosenNumbers)
			numPool.add(num);
		scanner.nextLine();
		while(numPool.size() != 1){
			Collections.sort(numPool);
			System.out.println("Numbers: " + numPool + "\nTarget: " + this.answer);
			System.out.print("Please input calculation of form \"int op int\":\t");
			try{
				Scanner in = new Scanner(scanner.nextLine());
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
							if (answer != this.answer) {
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
				System.out.println("Invalid input");
			} catch(NoSuchElementException e){
				System.out.println("Invalid input");
			}
		}		
		return false;
	}

	@Override
	public int scoreSolution() {
		if (answer >= (target - 10) && answer <= (target + 10)) {
			boolean correct = checkSolution();
			if (correct){
				if (answer == target)
					return 10;
				if (answer >= (target - 5) && answer <= (target + 5))
					return 7;
				return 5;
			}	
		}
		return 0;
	}

	@Override
	public void revealSolution() {
		ArrayList<String> progress_so_far = new ArrayList<String>();		
		if (searchSolution(chosenNumbers, progress_so_far, target) && calculations > finalSolution.size()){
			System.out.println("Suggested solution: ");
			for (int i = 0; i < finalSolution.size(); i++) {
				System.out.println(finalSolution.get(i));
			}
		}
		else if (!searchSolution(chosenNumbers, progress_so_far, target))
			System.out.println("No solution for this numbers combination.");
	}

	private boolean searchSolution(ArrayList<Integer> numbers, ArrayList<String> progress_so_far, int target) {
		String[] op = { "+", "-", "*", "/" };
		Collections.sort(numbers);

		for (int a = 0; a < numbers.size(); a++) {
			for (int b = a + 1; b < numbers.size(); b++) {
				int x = numbers.get(a);
				int y = numbers.get(b);

				for (int c = 0; c < op.length; c++) {
					result = performCalculation(x, y, op[c]);

					if (result > 0) {
						if (result == target) {

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
							searchSolution(new_numbers, new_progress, target);
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
