package menu;

/**
 * A Pair osztály egy generikus adatstruktúra, amely két értéket tárol együtt.
 *
 * @param <A> az első elem típusa
 * @param <B> a második elem típusa
 */
public class Pair<A, B> {
    /** Az első elem. */
    private A first;
    /** A második elem. */
    private B second;

     /**
     * Létrehoz egy új Pair objektumot a megadott értékekkel.
     *
     * @param first az első elem
     * @param second a második elem
     */
    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Visszaadja az első elemet.
     *
     * @return az első elem
     */
    public A getFirst() { return first; }

    /**
     * Visszaadja a második elemet.
     *
     * @return a második elem
     */
    public B getSecond() { return second; }
}
