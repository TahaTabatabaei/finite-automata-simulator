import java.util.ArrayList;
import java.util.StringTokenizer;

public class Accepter {
    boolean finished;
    ArrayList<DFA_State> dfa_states;

    Accepter(ArrayList<DFA_State> dfas){
        dfa_states = dfas;
        finished = false;
    }


    boolean check(String str){
        finished = false;
        // shekastan string vorodi
        char[] charArray = str.toCharArray();
        DFA_State dfaState1 = new DFA_State();
        for (DFA_State dfaState : dfa_states) {
            // peyfa kardan state start
            if (dfaState.start){
                dfaState1 = dfaState;
                char c;
                for (int j=0; j< charArray.length; j++){
                    c = charArray[j];
                    // agar char "c" statei baray raftan dashte bashe, "dfaState1" ro mizarim on
                    // state maghsad
                    if (dfaState1.reachableStates.containsKey(c)){
                        dfaState1 = dfaState1.reachableStates.get(c);
                    }
                }
            }
        }

        System.out.println("terminate on state: "+dfaState1.stateName);
        if (dfaState1.Final){
            finished = true;
        }

        return finished;
    }
}
