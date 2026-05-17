package game;
import java.io.Serializable;

import board.Square;

/**
 * A Move osztály a dáma játék során a lépések kezelésére alkalmas.
 */
public class Move implements Serializable{
    /** A kiinduló mező. */
    private Square from;
    /** A célmező. */
    private Square to;
    /** A lépést végrehajtó bábu azonosítója */
    private int checkerId;

    /**
    * Új lépést hoz létre.
    * 
    * @param from a lépést kiinduló mezője
    * @param to a lépés célmezője
    * @param id a lépést végrehajtó bábu azonosítója
    */
    public Move(Square from, Square to, int id) {
        this.from = from;
        this.to = to;
        checkerId=id;
    }

    /**
     * Két lépésről vizsgálja, hogy azonosak-e.
     * <p>
     * Két lépés akkor azonos, ha ugyanazok mezők között hajtódik végre, ugyanazon bábu által.
     * </p>
     *
     * @param move a másik lépés
     * @return true, ha a lépések azonosak; különben false
     */
    public boolean isEqualMove(Move move){
        return from.equals(move.from)&&to.equals(move.to)&&checkerId==move.checkerId;
    }

    /**
     * Visszaadja a lépés kiindulási mezőjét.
     * 
     * @return a lépés kiindulási mezője
     */
    public Square getFrom(){return from;}

    /**
     * Visszaadja a lépés célmezőjét.
     * 
     * @return a lépés célmezője
     */   
    public Square getTo(){return to;}
}
