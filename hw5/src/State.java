


import java.util.ArrayList;
import java.util.Arrays;

public class State
{
    State pred; // predecessor state
    int[] max = new int[3];
    int[] set = new int[3];
    State(State state)
    {
        this.set[0] = state.set[0];
        this.set[1] = state.set[1];
        this.set[2] = state.set[2];
        this.max[0] = state.max[0];
        this.max[1] = state.max[1];
        this.max[2] = state.max[2];
    }

    State(int[] arr)
    {
        this.set[0]= arr[0];
        this.set[1] = arr[1];
        this.set[2] = arr[2];
    }

    State(int M, int C, int B)
    {
        this.set[0] = M;
        this.set[1] = C;
        this.set[2] = B;
    }
    /**
     * @param y index
     * @param z index
     * @return State: //no invalid states here
     */
    State move(int y, int z)
    {
        State newState = new State(this);

        while(newState.set[y] !=0 && newState.set[z] != this.max[z])
        {
            newState.set[y]--;
            newState.set[z]++;
        }
        newState.pred = this;
        return newState;
    }

    void print()
    {
        System.out.println(Arrays.toString(set));
    }
    void setMax(int x, int y , int z)
    {
        this.max[0] = x;
        this.max[1] = y;
        this.max[2] = z;
    }
    boolean compare(ArrayList<State> arr)
    {
        //false means they are not the same;
        boolean result = false;
        for(State a: arr)
        {
            if((a.set[0] == this.set[0]) && (a.set[1] == this.set[1]) && (a.set[2] == this.set[2]))
            {
                result = true;
            }

        }
        if(this.set[0] == this.max[0]) {
            result = true;
        }
        return result;
    }
}
