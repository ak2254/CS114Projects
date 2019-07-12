import java.util.Scanner;

public class MaxPrefixAverage {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int k = scan.nextInt();
        int total = 0;
        int maxavg = 0;
        for (int i =0; i<k; i++) {
            int j = scan.nextInt();
            total +=j;
            int average = total/(i+1);
            if (average > maxavg) {
                maxavg = average;

            }




        }
        System.out.println(maxavg);


    }






}
