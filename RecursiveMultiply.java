import java.util.Scanner;

public class RecursiveMultiply {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int x = scan.nextInt();
        int y = scan.nextInt();
        long start = System.currentTimeMillis();
        long z = mult(x,y);
        long t = System.currentTimeMillis() - start;
        System.out.println(z);
        // Use the following if you want to find run time.
        //System.out.println("Elapsed time = " + t);
    }

    public static long mult(int a, int b) {
        if(a == 0 || b==0) {
            return 0;
        }

        if(b==1) {
            return a;
        }
        else if(b%2==0) {
            long k = mult(a,b/2);
            return k+k;

        }
        else if(b%2==1){

            long y = mult(a, (b-1)/2);

            return a+y+y;



        }
        else {
            return b;

        }
    }
}
