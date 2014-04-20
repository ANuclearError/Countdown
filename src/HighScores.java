import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * The storage and display of various scores, used as a leaderboard for the game.
 * 
 * @author Aidan O'Grady
 *
 */
public class HighScores {
	/**
	 * A list of all the scores that are contained within the leaderboard.
	 */
	ArrayList<Score> highscores;
	/**
	 * The location of where the highscores are persistently stored.
	 */
	String filename;
	
	/**
	 * Constructor
	 * 
	 * @param file The file to be used.
	 */
	public HighScores(String file){
		highscores = new ArrayList<Score>();
		filename = file;
		readHighScores(); //Existing high scores are read.
	}
	
	/**
	 * Existing high scores are read from designated file.
	 */
	private void readHighScores(){
		try {
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			String line;
			while((line = br.readLine()) != null){
				Score temp = new Score(line);
				highscores.add(temp); //New Score object created.
			}
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
		} catch (IOException e) {
			System.out.println("Error reading file.");
		}
	}

	/**
	 * Prints out all the scores in descending order.
	 */
	public void viewHighScores(){
		Collections.sort(highscores); //Sorts the highscores.
		Collections.reverse(highscores); //Reversed so that highest scores are top.
		
		for(int i=0; i<highscores.size(); i++){ //Prints leaderboard.
			String line = highscores.get(i).toString();
			System.out.println(line);
		}
			
	}
	
}
