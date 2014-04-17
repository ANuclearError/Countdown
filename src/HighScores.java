import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class HighScores {
	ArrayList<Score> highscores;
	String filename;
	
	public HighScores(String file){
		highscores = new ArrayList<Score>();
		filename = file;
		readHighScores();
	}
	
	private void readHighScores(){
		try {
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			String line;
			while((line = br.readLine()) != null){
				Score temp = new Score(line);
				highscores.add(temp);
			}
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
		} catch (IOException e) {
			System.out.println("Error reading file.");
		}
	}

	public void viewHighScores(){
		Collections.sort(highscores);
		Collections.reverse(highscores);
		for(int i=0; i<highscores.size(); i++){
			String line = highscores.get(i).toString();
			System.out.println(line);
		}
			
	}
	
}
