public class Transition {
    public String source;
    public String target;
    public char alphabet;

    public Transition(String source, char alphabet, String target) {
        this.source = source;
        this.target = target;
        this.alphabet = alphabet;
    }

    @Override
    public String toString() {
        return "Transition(" + source +
                ", " + alphabet +
                ") = " + target + "\n"  ;
    }

}
