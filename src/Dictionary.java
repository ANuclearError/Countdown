import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
  
public class Dictionary {
    private String filename;
      
    public Dictionary(String file){
        filename = file;
    }
      
    public Boolean findWord(String target){
        target = target.toLowerCase();
        try {
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);
            String word = br.readLine();
            while(word != null){
                if(target.equals(word)){
                    br.close();
                    return true;
                }
                word = br.readLine();
            }
            br.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
        catch (IOException e) {
            System.out.println("File not found.");
        }
        return false;
    }
      
    public ArrayList<String> getWords(int length){
        ArrayList<String> list = new ArrayList<String>();
        try {
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);
            String word = br.readLine();
            while(word != null){
                if(word.length() == length){
                    list.add(word);
                }
                word = br.readLine();
            }
            br.close();
            return list;
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
        catch (IOException e) {
            System.out.println("File not found.");
        }
        return list;
    }
      
    public String getRandomWord(ArrayList<String> list){
        Random random = new Random();
        return list.get(random.nextInt(list.size()));
    }
} 