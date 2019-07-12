import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Collections;

/* Starter code for PS8.
 */

public class RankMovies {

    public static void main(String[] args) {
        File file = new File("C:\\Users\\manav\\IdeaProjects\\hw1\\ps8\\ratings-utf8");

        ArrayList<MovieRating> rl = new ArrayList<MovieRating>();

        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                /* Read in lines, using fact that the
                 * title starts in column 32, and the
                 * other information is separated by
                 * 1 or more blanks.
                 */
                String line = scanner.nextLine();
                String pre = line.substring(0, 31);
                String post = line.substring(32);
                String[] tkns = pre.split("\\s+");

                MovieRating nr = new MovieRating(tkns[2], tkns[3], post);
                rl.add(nr);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int minVotes = 1;
        int numRecords = 1;
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.println();
            System.out.println("Enter minimum vote threshold and number of records:");
            minVotes = input.nextInt();

            if (minVotes  == 0)
                break;
            long startTime = System.currentTimeMillis();

            /* Fill in code to determine the top numRecords movies that have at least
             * minVotes votes. For each record mr, in decreaseing order of average rating,
             * execute a System.out.println(mr).
             * Do not sort the movie list!
             */
            int n = 0;
            MaxHeap ki = new MaxHeap();
            for(MovieRating k : rl)
            {
                if(minVotes<= k.getVotes())

                {
                    ki.insert(k);
                    n+=1;
                }
            }
            System.out.println(n);




            long readTime = System.currentTimeMillis();
            System.out.println("Time: "+(System.currentTimeMillis()-startTime)+" ms");
        }
    }
}
