package leaderboard;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

/**
 * A ScoreList osztály a dáma játék ranglistáját valósítja meg.
 */
public class ScoreList {
    /** A korábbi játékban részt vevő játékosok listája rendezve győzelmeik száma szerint csökkenő és azon belül nevük szerint növekvő sorrendben.*/
    private ArrayList<Player> list;
    /**A fájl, amibe a listát menti. */
    private String file;

    /**
     * Létrehoz egy új ranglistát a paraméterben megadott nevű fájlból beolvasott játékosokkal.
     * <p>
     * A játékosok listáját rendezetten tárolja el (győzelmeik száma szerint csökkenő és azon belül nevük szerint növekvő sorrendben).
     * </p>
     * 
     * @param fileName a fájl neve ami a játékosok listáját tartalmazza 
     */ 
    public ScoreList(String fileName){
        list= new ArrayList<>();
        file=fileName;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            list = (ArrayList<Player>) in.readObject();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        catch(ClassNotFoundException e){
            System.out.println(e.getMessage());
        }        
        Collections.sort(list,(p1, p2)->p1.compareTo(p2));
    }

    /**
     * Új játékos vesz fel a ranglistára.
     * <p>
     * Ha új játékos felvételekor olyan játékost kap paraméterben, aki már benne van a ranglistában (van vele egyező nevű a listában), akkor a paraméterben átvett játékos győzelmeinek száma hozzáadódik a listabeli játékos győzelmeinek számához.
     * <br>
     * A módosult lista rendezettségét a függvény biztosítja.
     * </p>
     * 
     * @param player új játékos
     */
    public void add(Player player){
        boolean listContains=false;
        for(Player p1:list){
            if(p1.getName().equals(player.getName())){
                p1.addWin(player.getWin());
                listContains=true;
            }
        }
        if(!listContains){
            list.add(player);
        }
        Collections.sort(list,(p1, p2)->p1.compareTo(p2));
    }

    /**
     * Stringgé alakítja a ranglistában szereplő játékosok neveit.
     * <p>
     * A neveket a ranglistának megfelelően rendezetten, minden nevet külön sorban írja a stringbe.
     * </p>
     * 
     * @return a neveket tartalmazó string
     */  
    public String names(){
        String s="";
        for(Player p:list){
            s=s+p.getName()+"\n";
        }
        return s;
    }

    /**
     * Visszaadja a játékosok rendezett listáját.
     * 
     * @return a játékosok listája
     */
    public ArrayList<Player> getList(){return list;}
  

    /**
     * Elmenti a ranglistát a hozzá tartozó fájlba sorosítással.
     */ 
    public void saveScore(){
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(list);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
