import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class for the maintenance and use of a dictionary.
 * 
 * @author Aidan O'Grady
 *
 */
public class Dictionary {
	/**
	 * The location of the dictionary to be used.
	 */
	public String filename;
	/**
	 * An ArrayList of every word in the dictionary. Probably not that efficient.
	 */
	private ArrayList<String> words;

	/**
	 * Constructor
	 * 
	 * @param file The file to be used.
	 */
	public Dictionary(String file) {
		filename = file;
		words = new ArrayList<String>();
		readDictionary();
	}

	/**
	 * Adds every word in dictionary to words ArrayList.
	 */
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

	/**
	 * Searches for a given word in words
	 * 
	 * @param target The word to be searched.
	 * @return Whether or not it is found.
	 */
	public Boolean findWord(String target) {
		target = target.toLowerCase();
		return words.contains(target);
	}

	/**
	 * Gets a random word from the dictionary.
	 * 
	 * @return The returned word.
	 */
	public String getRandomWord() {
		Random random = new Random();
		int index = random.nextInt(words.size());
		return words.get(index);
	}
}