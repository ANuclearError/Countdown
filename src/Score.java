import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Score implements Comparable<Score>{
	public String name;
	public int score;
	public String date;
	
	public Score(String playerName, int playerScore, String filename){
		name = playerName;
		score = playerScore;
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		date = dateFormat.format(new Date());
		saveScore(filename);
	}
	
	public String toString(){
		return score + "\t" + name + "\t" + name;
	}

	@Override
	public int compareTo(Score compare) {
		// TODO Auto-generated method stub
		return score - compare.score;
	}
	
	public void saveScore(String filename){
		try {
			FileWriter fw = new FileWriter(filename, true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(this.toString());
			bw.newLine();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
