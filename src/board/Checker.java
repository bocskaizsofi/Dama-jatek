package board;

import javax.swing.*;

import leaderboard.Player;

import java.awt.*;
import java.io.Serializable;

/**
 * A Checker osztály a dáma játék egy bábuját reprezentálja.
 *<p>
 * Az osztály felelős:
 * <ul>
 * <li>- az egyedi azonosítók kiosztásáért,</li>
 * <li>- a bábu állapotának kezeléséért (koronázás, tulajdonos),</li>
 * <li>- az ikon előállításáért.</li>
 *</ul>
 * A bábu a tábla mezőin mozoghat, játék közben állapota módosulhat.
 */

public class Checker implements Serializable{
    /** A bábu tulajdonos játékosa. */
    private Player owner;
    /** A bábu koronázottsági állapota (koronázott vagy koronázatlan). */
    private boolean crowned;
    /** A bábu színe (sötét vagy világos). */
    private boolean dark;
    /** A bábu egyedi azonosítója. */
    private int id;
    /** Statikus váltózó a létrehozott bábuk számára, felhasznált egyedi azonosító előállításában.*/
    private static int count=0;

    /**
    * Új bábut hoz létre a megadott színnel a megfelelő játékoshoz.
    * 
    * @param player a bábu tulajdonosa
    * @param dark true, ha a bábu sötét színű; false ha világos
    */
    public Checker(Player player, boolean dark){
        this.id=count;
        count++;
        owner=player;
        crowned=false;
        this.dark=dark;
    }

     /**
     * A bábut királynővé koronázza.
     */
    public void crown(){
        crowned=true;
    }

    /**
     * Visszaadja a bábu egyedi azonosítóját.
     *
     * @return a bábu azonosító száma
     */
    public int getId(){return id;}

    /**
     * Megadja, hogy a bábu koronázott-e.
     *
     * @return true, ha koronázott; false, ha nem
     */
    public boolean isCrowned(){return crowned;}

    /**
     * Ellenőrzi, hogy a bábu tulajdonosa megegyezik-e a megadott játékossal.
     *
     * @param player a vizsgált játékos
     * @return true, ha a bábu tulajdonosa a megadott játékos
     */
    public boolean isOwner(Player player){
        return owner.equals(player);
    }

     /**
     * Visszaadja a bábu tulajdonosát.
     *
     * @return a bábu tulajdonosa
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * Visszaadja a bábuhoz tartozó 35x35 pixelre skálázott ikont.
     * <p>
     * Megkülönbözteti a bábukat színük, illetve koronázottságuk alapján és ennek megfelelően adja vissza a hozzá tartozó ikont.
     * </p>
     *
     * @return a bábu ikonja
     */
    public Icon getIcon() {
        ImageIcon originalIcon;
        if(dark){
            if(crowned){
                originalIcon = new ImageIcon("darkQueen.png");
            }else{
                originalIcon = new ImageIcon("dark.png");
            }
        }
        else{
            if(crowned){
                originalIcon = new ImageIcon("whiteQueen.png");
            }
            else{
                originalIcon = new ImageIcon("white.png");
            }
            
        }
        Image scaledImage = originalIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }        
}

