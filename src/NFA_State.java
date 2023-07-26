import java.util.HashMap;

public class NFA_State {
    public String stateName;
    public HashMap<Character, Integer> reachableStates;
    public boolean start = false;
    public  boolean Final = false;
    public NFA_State[][] nStates;

    NFA_State(String stateName, int alphabets){
        this.stateName = stateName;
        reachableStates = new HashMap<>();
        nStates = new NFA_State[alphabets][];
    }
    NFA_State(){}

    public void addChain(NFA_State[] s , char alphabet){
        int index = reachableStates.get(alphabet);
        nStates[index] = s;
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
    String states(int index){
        StringBuilder stringBuilder = new StringBuilder();
        for (NFA_State nfa_state:
             nStates[index]) {
            stringBuilder.append(nfa_state.stateName);
        }
        return  stringBuilder.toString();
    }
}
