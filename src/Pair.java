public class Pair {
    
    public DFA_State s1;
    public DFA_State s2;
    public boolean mark  = false;
    
    public Pair(DFA_State p1 , DFA_State p2)
    {
        s1 = p1 ;
        s2 = p2 ;
    }
}
