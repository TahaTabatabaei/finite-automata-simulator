import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Converter {

    ArrayList<NFA_State> nfa_states = new ArrayList<>();
    HashMap<String , Integer> stateList;
    ArrayList<NFA_State> trapList = new ArrayList<>();

    Converter(){}

    public void nfa_state_maker(String[] states, char[] alphabet, Transition[] t, String start, String[] finals){
        for (String s : states) {
            System.out.println(s);
            NFA_State n1 = new NFA_State(s,alphabet.length);
            if (s.equals(start)) {
                n1.start = true;
            }
            for (String f : finals) {
                if (f.equals(s)) {
                    n1.Final = true;
                    break;
                }
            }
            nfa_states.add(n1);
        }
        transitionTranslate(alphabet,t);
    }

    public void transitionTranslate(char[] alphabet, Transition[] transitions){
        System.out.println("+++++ transitions +++++");

        for (int i=0; i< alphabet.length; i++){

            ArrayList<NFA_State> ns = new ArrayList<>();
            for (NFA_State nfa : nfa_states) {

                for (Transition transition : transitions) {

                    if (nfa.stateName.equals(transition.source)){
                        for (NFA_State nfa_state : nfa_states) {

                            if (nfa_state.stateName.equals(transition.target)){
                                if (transition.alphabet == alphabet[i]) {
                                    ns.add(nfa_state);
                                    break;
                                }
                            }
                        }
                    }
                }
                nfa.nStates[i] = new NFA_State[ns.size()];
                if (ns.size() > 0) {
                    nfa.reachableStates.put(alphabet[i], i);

                    nfa.nStates[i] = new NFA_State[ns.size()];
                    System.out.println("nfa = " + nfa.stateName + " char:" + alphabet[i]);
                    for (int k = 0; k < ns.size(); k++) {
                        nfa.nStates[i][k] = ns.get(k);
                        System.out.println(ns.get(k));
                    }
                    System.out.println();
                    ns.clear();
                }
            }
        }
    }
}
