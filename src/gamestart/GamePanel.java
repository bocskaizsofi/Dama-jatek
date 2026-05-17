package gamestart;
import javax.swing.*;

import board.Board;
import game.GameState;
import leaderboard.Player;
import leaderboard.ScoreList;
import menu.BackToMenuButton;
import menu.Menu;
import menu.Pair;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * A GamePanel osztály a játék indításakor való megjelenését biztosítja.
 * <p>
 * Feladatai:
 * <ul>
 *   <li>Megjeleníti a játékosok névbeviteli mezőit.</li>
 *   <li>Beállítja a játékosokat és a kezdőállapotot.</li>
 *   <li>Elindítja a játékot a "Start" gombbal.</li>
 *   <li>Visszalépést biztosít a főmenübe.</li>
 * </ul>
 */
public class GamePanel extends JPanel {
     /** A ranglistát tartalmazó ScoreList. */
    protected ScoreList list;

    /**
     * Létrehoz egy új legördülő listát a játékos nevekhez.
     * <p>
     * A lista a ranglistából kiolvasott neveket tartalmazza, és szerkeszthető.
     * </p>
     *
     * @param id a panel sorszáma
     * @return a létrehozott JComboBox
     */
    protected JComboBox<String> newJComboBox(int id){
        String[] options = list.names().split("\n");
        JComboBox<String> comboBox = new JComboBox<>(options);
        comboBox.setEditable(true);
        this.add(playerPanel(comboBox, id, "Játékos neve:"));
        return comboBox;
    }

    /**
     * Létrehoz egy panelt a játékos névbeviteli mezőjével és címkéjével.
     *
     * @param name a névbeviteli mező
     * @param id a panel sorszáma
     * @param text a címke szövege
     * @return a létrehozott JPanel
     */
    protected JPanel playerPanel(JComponent name, int id, String text){
        JPanel player = new JPanel(new GridLayout(2, 1, 5, 5));
        JLabel label = new JLabel(text);
        player.add(label);
        player.add(name);
        player.setBounds(50, 100*id, 150, 50);
        return player;
    }

    /**
     * Beállítja a játékosokat a játék állapotában.
     * <p>
     * Véletlenszerűen eldönti, hogy melyik játékos lesz a sötét és melyik a világos.
     * </p>
     *
     * @param name1 az első játékos neve
     * @param name2 a második játékos neve
     * @param isComputer1 igaz, ha az első játékos gép
     * @param isComputer2 igaz, ha a második játékos gép
     * @param state a játék állapota
     */
    protected static void setPlayer(String name1, String name2, boolean isComputer1, boolean isComputer2, GameState state){
    	Random r= new Random();
        int x=r.nextInt(0,2);
        if(x==1){
        	String temp=name1;
            name1=name2;
            name2=temp;
            state.setDark(new Player(name1, isComputer2));
            state.setWhite(new Player(name2, isComputer1));
        }
        else{
            state.setDark(new Player(name1, isComputer1));
            state.setWhite(new Player(name2, isComputer2));
        }
    }

    /**
     * Létrehoz egy "Indít" gombot, amely elindítja a játékot.
     *
     * @return a létrehozott JButton
     */
    protected JButton startButton(){
        JButton start = new JButton("Indít");
        start.setBounds(50, 300, 150, 50);
        start.setBackground(Menu.buttonColor); 
        return start;
    }

    /**
     * Létrehozza a játék indítópanelt.
     * <p>
     * Megjeleníti a visszalépés gombot és a játék táblát.
     * </p>
     *
     * @param menuPair a menübe visszalépéshez használt CardLayout és JPanel pár
     * @param list a ranglistát tartalmazó ScoreList
     */
    public GamePanel(Pair<CardLayout, JPanel> menuPair, ScoreList list){
        this.list=list;
        this.setBackground(Menu.backGroundColor);
        this.setLayout(null);
        this.add(new BackToMenuButton(menuPair));
        this.add(new Board(true,new GameState(),new ArrayList<>(),null, menuPair,list));
    }
}
