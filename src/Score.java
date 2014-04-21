import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 * An object that is used to represent a final score from a game,
 * to be stored in the High Scores table.
 * @author Aidan O'Grady
 *
 */
public class Score implements Comparable<Score>{
	/**
	 * The name of the player.
	 */
	public String name;
	/**
	 * The player's score.
	 */
	public int score;
	/**
	 * The date the score was achieved.
	 */
	public String date;
	
	/**
	 * Constructor in which the parameters are seperate.
	 * 
	 * @param playerName The name of the player
	 * @param playerScore Their score
	 * @param time The date the score was achieved.
	 */
	public Score(Player player, String time){
		name = player.name;
		score = player.score;
		date = time;
	}
	
	/**
	 * Constructor in which all the information is in a single string. Used
	 * for reading scores from a file.
	 * 
	 * @param line A string containing all information.
	 */
	public Score(String line){
		StringTokenizer st = new StringTokenizer(line, "|"); //'|' seperates fields.
		score = Integer.parseInt(st.nextToken());
		name = st.nextToken();
		date = st.nextToken();
	}
	
	/**
	 * Creates a string version of the score for printing purposes.
	 */
	public String toString(){
		return score + "\t" + name + "\t" + date;
	}

	@Override
	public int compareTo(Score compare) {
		return score - compare.score;
	}
	
	/**
	 * The score is saved to appended to a given file and written.
	 * 
	 * @param filename The location of the destination file.
	 */
	public void saveScore(String filename){
		try {
			//Objects used for writing to file.
			FileWriter fw = new FileWriter(filename, true);
			BufferedWriter bw = new BufferedWriter(fw);
			
			//Creating the string version, different from the toString() method.
			String line = this.score + "|" + this.name + "|" + this.date + "\n";
			bw.write(line);
			bw.newLine();
			bw.close();
		} catch (IOException e) {
			System.out.println("An error occured.");
		}
	}
}
