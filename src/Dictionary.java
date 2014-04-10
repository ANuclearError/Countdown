import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Dictionary {
	private String filename;
	private ArrayList<String> words;

	public Dictionary(String file) {
		filename = file;
		words = new ArrayList<String>();
		readDictionary();
	}

	private void readDictionary() {
		try {
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			String word = br.readLine();
			while (word != null) {
				words.add(word);
				word = br.readLine();
			}
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
		} catch (IOException e) {
			System.out.println("File not found.");
		}
	}

	public Boolean findWord(String target) {
		target = target.toLowerCase();
		for (String word:words) {
			if (word.equals(target))
				return true;
		}
		return false;
	}

	public String getRandomWord() {
		Random random = new Random();
		int index = random.nextInt(words.size());
		return words.get(index);
	}
}