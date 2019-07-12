
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
public class MostAnagrams {

    public static void main(String[] args) {

        File file = new File("C:\\Users\\manav\\IdeaProjects\\hw1\\hw4\\words.txt");

        Scanner input = new Scanner(System.in);

        int maxWords = input.nextInt(); // the maximum number of lines to read
        int n=0; // for counting the number of words read
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext())
            {
                n++;
                String line = scanner.next();
                char[] wordArray1 = line.toCharArray();7
                java.util.Arrays.sort(wordArray1);
                String w =new  String(wordArray1);
                if(map.containsKey(w))
                {
                    int  mw = 	map.get(w);
                    map.put(w, mw+1);
                }
                else
                {
                    map.put(w, 1);
                }
                if(n >= maxWords) {
                    break;
                    // Now do something with the word

                }
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // compute max, the maximum number of analgrams for any word

        Collection<Integer> a = map.values();

        int max = 0;
        for(int i: a) {
            if(i > max) {
                max =i;
            }
        }
        System.out.print(max);
    }
}

