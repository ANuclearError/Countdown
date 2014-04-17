import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

public class Score implements Comparable<Score>{
	public String name;
	public int score;
	public String date;
	
	public Score(String playerName, int playerScore, String time){
		name = playerName;
		score = playerScore;
		date = time;
	}
	
	public Score(String line){
		StringTokenizer st = new StringTokenizer(line, "|");
		score = Integer.parseInt(st.nextToken());
		name = st.nextToken();
		date = st.nextToken();
	}
	
	public String toString(){
		return score + "\t" + name + "\t" + date;
	}

	@Override
	public int compareTo(Score compare) {
		return score - compare.score;
	}
	
	public void saveScore(String filename){
		try {
			FileWriter fw = new FileWriter(filename, true);
			BufferedWriter bw = new BufferedWriter(fw);
			String line = this.score + "|" + this.name + "|" + this.date + "\n";
			bw.write(line);
			bw.newLine();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
