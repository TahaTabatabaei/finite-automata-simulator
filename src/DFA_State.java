import java.util.ArrayList;
import java.util.HashMap;

public class DFA_State {

    public boolean start = false;
    public  boolean Final = false;
    public String stateName;
    public HashMap<Character, DFA_State> reachableStates = new HashMap<>();
    //nfa hay motanazer ba dfa ma
    ArrayList<NFA_State> nfas = new ArrayList<>();
    // dfa hay minimize shode dar in dfa
    ArrayList<DFA_State> dfas = new ArrayList<>();

    boolean isMini;
    DFA_State head;


    DFA_State(){
    }

    DFA_State(String stateName, ArrayList<NFA_State> nfas){
        this.stateName = stateName;
        this.nfas = nfas;

        for (NFA_State nfa : nfas) {
            if (nfa.start){
                start = true;
                break;
            }
            if (nfa.Final){
                Final = true;
            }
        }
    }

    public void addChain(DFA_State s , char alphabet){
        reachableStates.put(alphabet, s);
    }

    @Override
    public String toString() {
        String str = "{ " +
                stateName +
                " }";
        if (Final)
            str += " ,final";
        if (start)
            str += " ,start";
        return str;
    }
}
