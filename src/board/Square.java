package board;

import java.util.ArrayList;
import javax.swing.*;

import menu.Menu;

/**
 * A Square osztály a dáma játék egy mezőjét reprezentálja.
 */

public class Square extends JButton{
    /** A mező bábuját (ha nincs rajta bábu, akkor az üres állapotot null értékkel jelzi).*/
    private Checker checker;
    /** A mező sora (információt tud adni arról, hogy a mező a fekete vagy a fehér játékos kezdősorában van-e). */
    private int row;
    /** A mező szomszédait tartalmazó lista.*/
    private ArrayList<Square> neighbours;

    /**
    * Új mezőt hoz létre.
    *
    * @param row a sor, ahová a mezőt létrehozza
    */
    public Square(int row){
        neighbours= new ArrayList<>();
        checker=null;
        this.row=row;
        setBackground(Menu.darkColor);
        setAlignmentX(JComponent.CENTER_ALIGNMENT);
        Menu.setComponentSize(this,40,40);
    }

    /**
    * Visszaadja a mezőn levő bábut.
    *
    * @return a mező bábuja (ha nem lett a mezőre beállítva bábu, akkor null)
    */
    public Checker getChecker(){return checker;}

    /**
    * Beállítja a mezőn levő bábut és az annak megfelelő ikont.
    *
    * @param checker a mező bábuja
    */
    public void setChecker(Checker checker){
        this.checker=checker;
        if(checker!=null)
            setIcon(checker.getIcon());
    }

    /**
    * Eltávolítja a mezőn levő bábut.
    * <p>
    * A mezőt üres (bábu nélküli) állapotba állítja, ezt a checker null értékével, illetve az ikon eltávolításával jelzi.
    * </p>
    */
    public void removeChecker() {
        this.checker = null;
        setIcon(null);
    }

    /**
    * Visszaadja azt, hogy a mező a sötét játékos első sorában található-e.
    *
    * @return true, ha a mező a sötét játékos első sorában található; különben false
    */
    public boolean isDarkLine(){return row==0;}

    /**
    * Visszaadja azt, hogy a mező a világos játékos első sorában található-e.
    *
    * @return true, ha a mező a világos játékos első sorában található; különben false
    */
    public boolean isWhiteLine(){return row==7;}
    
    /**
    * Visszaadja a mező szomszédait.
    * <p>
    * A mezők listája 4 elemű, ha már be vannak állítva a szomszédok, különben üres.
    * </p>
    * @return a mező szomszédainak listája
    */
    public ArrayList<Square> getNeighbours(){return neighbours;}

    /**
    * Beállítja a mező szomszédait (átlós kapcsolatok).
    *
    * @param upLeft a mező bal felső szomszédja (átlósan)
    * @param upRight a mező jobb felső szomszédja (átlósan)
    * @param downLeft a mező bal alsó szomszédja (átlósan)
    * @param downRight a mező jobb alsó szomszédja (átlósan)
    */
    public void setNeighbours(Square upLeft, Square upRight, Square downLeft, Square downRight){
        if(!neighbours.isEmpty()){
            neighbours.clear();
        }
        neighbours.add(upLeft);
        neighbours.add(upRight);
        neighbours.add(downLeft);
        neighbours.add(downRight);
    }
}
