package leaderboard;

import java.io.Serializable;

/**
 * A Player osztály a dáma játékosainak reprezentációjára alkalmas.
 */
public class Player implements Comparable<Player>, Serializable{
    /** A játékos neve. */
    private String name;
    /** A játékos győzelmeinek száma.*/
    private int win;
    /** Jelzi, hogy a játékos gép-e. */
    private boolean isComputer;


    /**
    * Új játékost hoz létre a megadott névvel, illetve azzal az információval, hogy a játékos gép-e.
    * 
    * @param name a játékos neve
    * @param isComputer az információ, hogy a játékos gép-e (true, ha gép; különben false)
    */    
    public Player(String name, boolean isComputer){
        this.name=name;
        this.isComputer=isComputer;
    }

    /**
    * Visszaadja a játékos nevét
    * 
    * @return a játékos neve
    */  
    public String getName(){return name;}

    /**
    * Visszaadja a játékos győzelmeinek számát
    * 
    * @return a játékos győzelmeinek száma
    */  
    public int getWin(){return win;}

    /**
    * Növeli a játékos győzelmeinek számát a megadott értékkel.
    * 
    * @param win a növelés értéke
    */
    public void addWin(int win) {this.win=this.win+win;}

    /**
    * Két játékos hasonlít össze elsődlegesen győzelmeik, másodlagosan nevük alapján.
    * <p>
    * A játékosokat győzelmeik száma alapján csökkenő sorrendbe rendezi, azonos győzelmek esetén név szerint növekvő sorrendbe.
    * </p>
    * 
    * @param player a másik játékos
    * @return 1, ha az aktuális játékos több győzelmet ért el; -1, ha az aktuális játékos kevesebb győzelmet ért el; a nevük összehasonlításának (abc) eredménye ugyanannyi győzelem esetén
    */  
    public int compareTo(Player player){
        if(this.win<player.win)
        	return 1;
        else if(this.win>player.win) 
        	return -1;
        else 
        	return this.name.compareTo(player.name);
    }

    /**
    * Visszaadja, hogy a játékos gép-e.
    * 
    * @return true, ha a játékos gép; különben false
    */  
    public boolean isComputer(){return isComputer;}

    /**
    * Két játékos azonosságát vizsgálja.
    * <p>
    * A játékosok azonosak, amennyiben nevük megegyezik.
    * </p>
    * 
    * @param player a másik játékos
    * @return true, ha a két játékos azonos; különben false
    */  
    public boolean equals(Player player){
        return name.equals(player.name);
    }

}

