import java.lang.*;
import java.util.*;

public class NFA_To_DFA {

    char[] alphabet;
    NFA_State[] nfa_states;
    Stack<DFA_State> dfa_stateStack = new Stack<>();
    ArrayList<DFA_State> final_DFA_States = new ArrayList<>();
    int counter = 0;

    NFA_To_DFA(char[] alphabet, NFA_State[] nfaStates){
        this.alphabet = alphabet;
        nfa_states = nfaStates;
    }

    public void initialStateFinder(){
        for(int i = 0; i < nfa_states.length ;i++ ){
            // peyda kardan state start
            if( nfa_states[i].start ){
                DFA_State startingState = new DFA_State();
                startingState.nfas.add(nfa_states[i]);

                startingState.start = true;
                startingState.Final = false;

                startingState.stateName = nfa_states[i].stateName;
                dfa_stateStack.push(startingState);
                break;
            }
        }
        // tabdil yek nfa-state be dfa-state
        while(!dfa_stateStack.empty() ){
            convertState(dfa_stateStack.pop());
        }
    }

    void convertState(DFA_State stackTopState){

        // aya in state pardazesh shode ya na
        if(isNew(stackTopState)){
            for(int i = 0; i < alphabet.length; i++ ){
                ArrayList<NFA_State> nfasArray = new ArrayList<>();
                // entekhab yeki az nfa-state haei ke zer majmoei 'stackTopState" hast
                for(int j = 0; j < stackTopState.nfas.size(); j++ ){
                    if(stackTopState.nfas.get(j).nStates[i] != null){
                        // peyda kardan nfa-state haye ghabel dastresi ba "alphabet[i]" az "stackTopState"
                        NFA_State[] DFA_nStates = stackTopState.nfas.get(j).nStates[i];
                        for (NFA_State nfa_nState2 : DFA_nStates) {
                            if (nfasArray.isEmpty()) {
                                nfasArray.add(nfa_nState2);
                            } else {
                                // age ghablan ezafe nashode bashad, ezafe mishe
                                if (isRepetitious(nfa_nState2, nfasArray)) {
                                    nfasArray.add(nfa_nState2);
                                }
                            }
                        }
                    }
                }
                if(nfasArray.size() > 0){
                    DFA_State finalState = new DFA_State();
                    StringBuilder name = new StringBuilder();
                    //boolean isFinal  = false;
                    for (NFA_State nfa_state : nfasArray) {
                        name.append(nfa_state.stateName);
                        if (nfa_state.Final) {
                            finalState.Final = true;
                        }
                    }
                    finalState.stateName = name.toString();
                    finalState.nfas = nfasArray;
                    
                    stackTopState.reachableStates.put(alphabet[i], finalState);
                    // nabayad push she?
                    dfa_stateStack.add(finalState);
                }
            }
            final_DFA_States.add(stackTopState);
        }
    }

    boolean isNew(DFA_State newState){
        // aya mishe ba esm?
        for (DFA_State dfaState : final_DFA_States) {
            int counter1 = 0;
            if (dfaState.nfas.size() == newState.nfas.size()) {
                for (int i = 0; i < newState.nfas.size(); i++) {
                    for (int j = 0; j < dfaState.nfas.size(); j++) {
                        if (dfaState.nfas.get(i) == newState.nfas.get(j)) {
                            counter1++;
                        }
                    }
                }
            }
            if (counter1 >= newState.nfas.size()) {
                return false;
            }
        }
        return true ;
    }

    boolean isRepetitious(NFA_State nfaState , ArrayList<NFA_State> states){
        for (NFA_State state : states) {
            if (nfaState.stateName.equals(state.stateName)) {
                return false;
            }
        }
        return true ;
    }

    ArrayList<DFA_State> trapList = new ArrayList<>();
    public void trap(){
        for (DFA_State dfa : final_DFA_States) {
            for (char c : alphabet) {
                if (dfa.reachableStates.get(c) == null) {
                    DFA_State trap = new DFA_State();
                    trap.stateName ="-"+ c + dfa.stateName + "-";
                    trap.start = false;
                    trap.Final = false;
                    dfa.reachableStates.put(c,trap);
                    trapList.add(trap);
                }
            }
        }

        for (char c: alphabet) {
            for (DFA_State trap1 : trapList) {
                trap1.reachableStates.put(c,trap1);
            }
        }
        final_DFA_States.addAll(trapList);
    }
}
