import java.util.ArrayList;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;
import java.util.Queue;
public class WaterJugs
{
    //#9
    static Stack stack = new Stack();
    static ArrayList<State> prev = new ArrayList<State>();
    static int goal;
    static int[] max= {50,17,12};
    static int maxJ1,maxJ2,maxJ3;
    public static void main(String[] args)
    {
        Queue<State> q = new LinkedList<State>();
        Scanner scan = new Scanner(System.in);
        String line = scan.nextLine();

        String[] m = line.split(" ");

        Stack<State> stack = new Stack<State>();
        maxJ1 = Integer.parseInt(m[0]);

        maxJ2 = Integer.parseInt(m[1]);

        maxJ3 = Integer.parseInt(m[2]);

        goal = Integer.parseInt(m[3]);
        int[] base = {maxJ1,0,0};
        int[] max = {maxJ1,maxJ2,maxJ3};
        //by now we have everything we need to send to state
        State s = new State(base);
        s.setMax(maxJ1, maxJ2, maxJ3);
        q.add(s);
        stack.push(s);
        while(q.peek() != null)
        {
            State next = q.poll();
            next.setMax(maxJ1, maxJ2, maxJ3);
            if(isAnswer(next))
            {
                for (State x = next; x != null; x = x.pred)
                { // use stack to reverse order
                    stack.push(x);
                }
                break;
            }

            for (int i = 0; i <= 2; i++)
            {
                for (int j = 0; j <= 2; j++)
                {
                    if(i == j)
                    {
                        continue;
                    }
                    State p = next.move(i,j);
                    //contains method is not working for us
                    if(p.compare(prev))
                    {
                        continue;
                    }

                    q.add(p);
                    prev.add(p);
                }
            }
        }

        prev.add(s);
        while(stack.size()> 1)
        {
            stack.pop().print();
        }


    }
    public static boolean isAnswer(State b)
    {
        return (b.set[0] == goal||b.set[1] == goal || b.set[2] == goal);
    }

}
