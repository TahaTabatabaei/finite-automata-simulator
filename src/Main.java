import java.awt.desktop.QuitStrategy;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        String[] Qs;
        char[] alphabet;
        String start;
        String[] Finals;
        Transition[] transitions;

        File machineFile = new File("C:\\Users\\taha\\IdeaProjects\\L&A_P2_2nd edition\\src\\Input2.txt");
        Scanner fileReader = new Scanner(machineFile);

        String str = fileReader.nextLine();
        StringTokenizer st = new StringTokenizer(str,",");
        Qs = new String[st.countTokens()];
        int count =0;
        System.out.println("states :");
        while (st.hasMoreTokens()){
            Qs[count] = st.nextToken();
            System.out.println(Qs[count++]);
        }


        str = fileReader.nextLine();
        st = new StringTokenizer(str,",");
        alphabet = new char[st.countTokens()];
        count = 0;
        System.out.println("alphabet :");
        while (st.hasMoreTokens()){
            alphabet[count] = st.nextToken().charAt(0);
            System.out.println(alphabet[count++]);
        }

        str = fileReader.nextLine();
        start = str;
        System.out.println("start : "+start);

        str = fileReader.nextLine();
        st = new StringTokenizer(str,",");
        Finals = new String[st.countTokens()];
        count =0;
        System.out.println("finals : ");
        while (st.hasMoreTokens()){
            Finals[count] = st.nextToken();
            System.out.println(Finals[count++]);
        }

        transitions = new Transition[100];
        count = 0;
        while (fileReader.hasNext()) {
            str = fileReader.nextLine();
            st = new StringTokenizer(str, ",");
            transitions[count] = new Transition(st.nextToken(),st.nextToken().charAt(0),st.nextToken());
            System.out.println(transitions[count]);
            count++;
        }
        Transition[] t = new Transition[count];

        for (int i = 0;i<t.length;i++){
            t[i] = transitions[i];
        }

        System.out.println("----------- convert -----------");
        Converter converter = new Converter();
        converter.nfa_state_maker(Qs,alphabet,t,start,Finals);

        NFA_State[] nfa_states = new NFA_State[converter.nfa_states.size()];
        for (int i=0; i<converter.nfa_states.size(); i++){
            nfa_states[i] = converter.nfa_states.get(i);
        }
        System.out.println("----------- nfa to dfa -----------");

        NFA_To_DFA nfaToDfa = new NFA_To_DFA(alphabet,nfa_states);
        nfaToDfa.initialStateFinder();
        nfaToDfa.trap();

        System.out.println();
        for (DFA_State dfa :
                nfaToDfa.final_DFA_States) {
            System.out.println(dfa + ":");
            for (char c :
                    alphabet) {
                System.out.println(c + "-->" + dfa.reachableStates.get(c));
            }
            System.out.println();
        }

        System.out.println("----------- minimize -----------");
        MinimalDFA minimalDFA = new MinimalDFA(nfaToDfa.final_DFA_States,alphabet);
        minimalDFA.minimize();

        System.out.println();
        for (DFA_State dfa :
                minimalDFA.minimizedStates) {
            System.out.println(dfa + ":");
            for (char c :
                    alphabet) {
                System.out.println(c + "-->" + dfa.reachableStates.get(c));
            }
            System.out.println();
        }

        System.out.println("----------- accept -----------");
        Accepter accepter = new Accepter(minimalDFA.minimizedStates);

        String[] test = {"aaba","ababb","bbba","ab","","b"};
        for (int k = 0; k< test.length; k++){
            boolean b = accepter.check(test[k]);
            System.out.println(test[k] + "=");
            if (b){
                System.out.println("accept");
            }else {
                System.out.println("reject");
            }
            System.out.println();
        }
    }
}
