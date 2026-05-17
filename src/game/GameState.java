package game;


import java.io.Serializable;
import java.util.ArrayList;

import leaderboard.Player;

/**
 * A GameState osztály a játék aktuális állapotát kezeli.
 */
public class GameState implements Serializable{
    /** A sötét játékos. */
    private Player dark;
    /** A világos játékos. */
    private Player white;
    /** A sötét játékos bábuinak száma. */
    private int darkCheckers;
    /** A világos játékos bábuinak száma. */
    private int whiteCheckers;
    /** Az utolsó akció (koronázás vagy ütés) körszáma. */
    private int lastActionRound;
    /** A játék során megtett utolsó 40 lépés listája. */
    private ArrayList<Move> moves;
    /** Jelzi, hogy folytatott játék állapotában van-e. */
    private boolean continued;
    /** Az aktuális körszám. */
    private int roundId; 
    /** Jelzi, hogy döntetlen állapotban van-e a játék. */
    private boolean draw;
    /** A döntetlen oka szöveges formában. */
    private String drawReason;

    /**
     * Új játék állapotot hoz létre. 
     * <p>
     * Az új játékban:
     * <ul>
     * <li>- a játékosok 12 bábuval rendelkeznek,</li>
     * <li>- a játék körszáma 1,</li>
     * <li>- nem történt még akció (koronázás, se ütés),</li>
     * <li>- alapértelmezetten 2 gépi játékost állít be</li>
     * </ul>
     */
    public GameState(){
        darkCheckers=12;
        whiteCheckers=12;
        roundId=1;
        lastActionRound=0;
        dark=new Player("Gép 1", true);
        white=new Player("Gép 2", true);
        moves= new ArrayList<>();
        draw = false;
        drawReason = "";
        continued=false;
    }

    /**
     * Visszaadja az aktuális játékost (,azaz aki ebben a körben lép).
     * 
     * @return az akutális játékos
     */
    public Player getCurrent(){
        if(roundId%2==1)
            return dark;
        else return white;
    }

    /**
     * Visszaadja az aktuális játékos ellenfelét (vagyis aki a következő körben fog lépni).
     * 
     * @return az akutális ellenfél
     */
    public Player getOpponent() {
        if(getCurrent().getName().equals(getDark().getName()))
            return getWhite();
        else 
            return getDark();
    }

    /**
     * Játékosváltás a körszám növelésével.
     */
    public void switchPlayer(){
        roundId++;
    }

    /**
     * Visszaadja a sötét játékost.
     * 
     * @return a sötét játékos
     */
    public Player getDark() { 
        return dark; 
    }

    /**
    * Visszaadja a világos játékost.
    * 
    * @return a világos játékos
    */
    public Player getWhite() { 
        return white; 
    }

    /**
     * Csökkenti a megadott játékos bábujainak számát eggyel.
     *
     * @param player a játékos, akinek csökkenti a bábuinak számát
     */
    public void subCheckerNumber(Player player){
        if(player.getName().equals(dark.getName())){
            darkCheckers--;
                
        }
        else{
            whiteCheckers--;
        }
    } 

    /**
     * Visszaadja, hogy a játék folytatott állapotban van-e.
     * 
     * @return true, ha a játék folytatott
     */    
    public boolean getContinued(){return continued;}

    /**
     * Beállítja, hogy folytatott játék állapotban van-e.
     * 
     * @param cont az érték, amire a folytatott állapotot állítja
     */
    public void setContinued(boolean cont){continued=cont;}

    /**
     * Visszaadja a sötét játékos bábuinak számát.
     * 
     * @return a sötét játékos bábuinak száma
     */
    public int getDarkCheckerNumber(){return darkCheckers;}

    /**
     * Visszaadja a világos játékos bábuinak számát.
     * 
     * @return a világos játékos bábuinak száma
     */
    public int getWhiteCheckerNumber(){return whiteCheckers;}

    /**
     * Beállítja a sötét játékost.
     * 
     * @param player az új sötét játékos
     */
    public void setDark(Player player) { dark = player; }

    /**
     * Beállítja a világos játékost.
     * 
     * @param player az új világos játékos
     */
    public void setWhite(Player player) { white = player; }

    /**
    * Visszaadja, hogy a játék döntetlen állapotban van-e.
    * 
    * @return true, ha a játék döntetlen állapotban van
    */
    public boolean isDraw() { return draw; }

    /**
     * Visszaadja a döntetlen okát szöveges formában.
     * 
     * @return a döntetlen oka
     */
    public String getDrawReason() { return drawReason; }

    /**
     * Hozzáad egy lépést a játékhoz.
     * <p>
     * Ellenőrzi a döntetlen feltételeket, döntetlen esetén döntetlen állapotba helyezi a játékot és beállítja a döntetlen okát.
     * </p>
     *
     * @param move a megtett lépés
     * @param crown igaz, ha a lépés koronázással járt
     * @param remove igaz, ha a lépés ütést tartalmazott
     */
    public void addMove(Move move, boolean crown, boolean remove){
        if(moves.size()==40){
            moves.remove(0);
        }
        moves.add(move);
        if(crown||remove){
            lastActionRound=roundId;
        }
        int count=0;
        for(Move m:moves){
            if(m.isEqualMove(move))
                count++;
        }
        if(count==3){
            draw=true;
            drawReason="<html><center>Döntetlen<br>(3 egymást követő lépés)</center></html>";
        }
        if(roundId-lastActionRound>40){
            draw=true;
            drawReason="<html><center>Döntetlen<br>(40 lépésben se koronázás, se ugrás)</center></html>";
        }
    }
}
