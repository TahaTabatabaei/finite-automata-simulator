
import java.util.ArrayList;

public class MinimalDFA {

    ArrayList<DFA_State> dfaStates;
    ArrayList<DFA_State> minimizedStates = new ArrayList<>();
    ArrayList<Pair> pairList = new ArrayList<>();
    char[] alphabet;

    public MinimalDFA(ArrayList<DFA_State> states, char[] alpabet) {
        this.alphabet = alpabet;
        dfaStates = states;
    }

    public void minimize() {
        // sakhtan pair haye momken
        for (int i = 0; i < dfaStates.size(); i++) {
            for (int j = i + 1; j < dfaStates.size(); j++) {
                Pair p = new Pair(dfaStates.get(i), dfaStates.get(j));
                // agar yeki az state haye pair final va digari final nabashe
                if (dfaStates.get(i).Final != dfaStates.get(j).Final) {
                    p.mark = true;
                }
                pairList.add(p);
            }
        }
        boolean cont = true;
        while (cont) {
            cont = false;
            for (Pair aPair : pairList) {
                // be ezaye har pair, agar dar marhale aval mark nashode bashad
                if (aPair.mark == false) {
                    DFA_State p1 = aPair.s1;
                    DFA_State p2 = aPair.s2;
                    for (char c : alphabet) {
                        DFA_State p3 = p1.reachableStates.get(c);
                        DFA_State p4 = p2.reachableStates.get(c);
                        for (Pair bPair : pairList) {
                            // bayad check konim ta state haye p1&2 ba state haye "bPair" yeksan bashande.
                            // tebghe algorithm agar yeksan bashand va "bPair" mark shode bashad, bayad "apair" ra
                            // ham mark kard.
                            if ((p3.stateName.equals(bPair.s1.stateName) && p4.stateName.equals(bPair.s2.stateName)) || (
                                (p4.stateName.equals(bPair.s1.stateName) && p3.stateName.equals(bPair.s2.stateName)))) {

                                if (bPair.mark) {
                                    aPair.mark = true;
                                    cont = true;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }

        //marhale pardazesh

        for (int i = 0; i < dfaStates.size(); i++) {
            // yek dfa-state ke hanoze minimize nashode
            if (!dfaStates.get(i).isMini) {
                // sakhtan "dfa_state" jadid ke mishe statei ke gharare tosh chand ta state dige minimize beshan
                DFA_State dfa_state = new DFA_State();
                dfa_state.dfas.add(dfaStates.get(i));
                StringBuilder name = new StringBuilder(dfaStates.get(i).stateName);
                // age final bashe
                if (dfaStates.get(i).Final) {
                    dfa_state.Final = true;
                }
                // age start bashe
                if (dfaStates.get(i).start) {
                    dfa_state.start = true;
                }
                for (Pair pair : pairList) {
                    if (pair.mark == false) {
                        // migardim ta bebinim state haye dfa-state jadid toye pair haye dige hast ya na
                        if (pair.s1.stateName.equals(dfaStates.get(i).stateName) ||
                                pair.s2.stateName.equals(dfaStates.get(i).stateName)) {

                            DFA_State d1 = new DFA_State();
                            DFA_State d2 = new DFA_State();
                            // entekhab az dfaState
                            for (DFA_State dfaState : dfaStates) {
                                if (pair.s1.stateName.equals(dfaState.stateName)) {
                                    d1 = dfaState;
                                }

                                if (pair.s2.stateName.equals(dfaState.stateName)) {
                                    d2 = dfaState;
                                }
                            }

                            if (!d1.isMini && !d2.isMini) {
                                // age d1 hamon state khat 70 bode bashe
                                // mishe jaye "pair.s1", "d1" gozasht
                                if (pair.s1.stateName.equals(dfaStates.get(i).stateName)) {
                                    name.append(pair.s2.stateName);

                                    if (pair.s2.start) {
                                        dfa_state.start = true;
                                    }
                                    if (pair.s2.Final){
                                        dfa_state.Final = true;
                                    }
                                    for (DFA_State dfaState : dfaStates) {
                                        if (dfaState.stateName.equals(pair.s2.stateName)) {
                                            dfaState.isMini = true;
                                            break;
                                        }
                                    }
                                    dfa_state.dfas.add(pair.s2);
                                } else {
                                    // age d2 state khat 70 bode
                                    name.append(pair.s1.stateName);

                                    if (pair.s1.start) {
                                        dfa_state.start = true;
                                    }
                                    if (pair.s1.Final){
                                        dfa_state.Final = true;
                                    }
                                    for (DFA_State dfaState : dfaStates) {
                                        if (dfaState.stateName.equals(pair.s1.stateName)) {
                                            dfaState.isMini = true;
                                            break;
                                        }
                                    }
                                    dfa_state.dfas.add(pair.s1);
                                }
                            }
                        }
                    }
                }
                // entehaye amaliyat roye "dfaStates.get(i)"
                dfaStates.get(i).isMini = true;
                dfa_state.head = dfaStates.get(i);
                dfa_state.stateName = name.toString();
                minimizedStates.add(dfa_state);
            }
        }

        //check mikonim ke dfa-State hay minimize shode, harkodom ba yek harf be koja miran

        for (int i = 0; i < minimizedStates.size(); i++) {
            for (char c : alphabet) {
                // yek state ro entekhab mikonim
                DFA_State head1 = minimizedStates.get(i).head;
                // mibinim ba avalin harf be koja mire -> "state"
                DFA_State state = head1.reachableStates.get(c);
                // yek state minimize shode entekhab mikonim
                for (DFA_State minimizedState : minimizedStates) {
                    for (int u = 0; u < minimizedState.dfas.size(); u++) {
                        // age on state minimize shode dar harkodom az dfaState hay tashkil dahandash
                        // , ba "state" yeksan bood, pas on "minimizedState" az "head1"
                        // ghabel dastrasi hast
                        if (state.stateName.equals(minimizedState.dfas.get(u).stateName)) {
                            minimizedStates.get(i).reachableStates.put(c, minimizedState);
                            break;
                        }
                    }
                }
            }
        }
    }
}
