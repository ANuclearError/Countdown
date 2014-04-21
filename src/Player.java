import java.util.Scanner;

/**
 * A class used to store information of someone playing Countdown.
 * @author Aidan O'Grady
 *
 */
public class Player {
	/**
	 * The player's name.
	 */
	public String name;
	/**
	 * Their score.
	 */
	public int score;
	/**
	 * Countdown's scanner, for input handling.
	 */
	private Scanner in;
	
	/**
	 * Constructor
	 * @param in The scanner to be passed.
	 * @param player Is it player one or two?
	 */
	public Player(Scanner in, int player){
		this.in = in;
		name = setName(player);
		score = 0;
	}
	
	/**
	 * Sets the player's name.
	 * @param player
	 * @return
	 */
	public String setName(int player){
		System.out.print("Player " + player + ", please enter your name: ");
		return in.next();
	}
	
	/**
	 * Increases the value of the player's score by a given amount
	 * @param score The increase in score
	 * @return The new score.
	 */
	public int updateScore(int score){
		return this.score += score;
	}
}
