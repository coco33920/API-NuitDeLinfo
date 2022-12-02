package gay.bacoin.api.vih;

public class Fact {

    private final String fact;
    private final boolean value;
    private final String explanation;

    public Fact(String fact, boolean value, String explanation) {
        this.fact = fact;
        this.value = value;
        this.explanation = explanation;
    }

    public String getExplanation() {
        return explanation;
    }

    public String getFact() {
        return fact;
    }

    public boolean getValue() {
        return value;
    }
}
