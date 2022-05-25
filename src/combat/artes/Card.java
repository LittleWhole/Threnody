package combat.artes;

public class Card<A extends Arte> {
    private float level;
    private A arte;

    public A getArte() {
        return arte;
    }

    public Class<? extends Arte> getArteType() {
        return getArte().getClass();
    }
}
