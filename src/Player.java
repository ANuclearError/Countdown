import java.util.Scanner;

public class Player {
	public String name;
	public int score;
	private Scanner in;
	public Player(Scanner in, int player){
		this.in = in;
		name = setName(player);
		score = 0;
	}
	
	public String setName(int player){
		System.out.print("Player " + player + ", please enter your name: ");
		return in.next();
	}
	
	public int updateScore(int score){
		return this.score += score;
	}
}
