import java.io.File;

import java.util.ArrayList;

import java.io.FileNotFoundException;

import java.util.Scanner;

import java.util.ArrayList;

import java.util.Arrays;

import java.util.Collection;
public class AllHomophones

{

    public static void main(String[] args){

        UALDictionary<String, String> PDict = new UALDictionary<String, String>();

        //UALDictionary<String, ArrayList<String>> PDictF = new UALDictionary<String, ArrayList<String>>();



        File file = new File("C:\\Users\\manav\\IdeaProjects\\hw1\\hww6\\cmudict.0.7a.txt");
        Scanner scan = new Scanner(System.in);

        String word = scan.nextLine();
        int d = 0;
        String ActualW = word.toUpperCase();

        String phonemesW = "" ;

        ArrayList<String> WP = new ArrayList<String>();
        try {

            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {

                String line = scanner.nextLine();

                String UW = line.toUpperCase();

                if (UW.substring(0, 3).equals(";;;"))

                {

                    continue; // skip comment lines

                }
                Pronunciation p = new Pronunciation(UW);

                String k = p.getPhonemes();
                PDict.insert(k, UW);

                if(p.getWord().equals(ActualW)) {

                    phonemesW = k;
                }

            }
            WP = (ArrayList<String>) PDict.findAll(phonemesW);
            for (String str: WP){
                System.out.println(str);
            }
            scanner.close();

        } catch (FileNotFoundException e) {

            e.printStackTrace();

        }

        System.out.println(WP);
    }
}

