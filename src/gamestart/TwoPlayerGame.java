package gamestart;

import java.awt.CardLayout;
import javax.swing.*;

import game.GameDama;
import game.GameState;
import leaderboard.ScoreList;
import menu.Pair;

/**
 * A TwoPlayerGame osztály a játék indítópanelje két játékos közötti játékhoz.
 * <p>
 * Feladatai:
 * <ul>
 *   <li>Megjeleníti a két játékos névbeviteli mezőjét.</li>
 *   <li>Beállítja a játékosokat a játék állapotában.</li>
 *   <li>Elindítja a játékot a "Start" gombbal.</li>
 * </ul>
 */
public class TwoPlayerGame extends GamePanel{

     /**
     * Létrehozza a kétjátékos panelt.
     * <p>
     * A panel tartalmazza:
     * <ul>
     *   <li>a két játékos névbeviteli mezőjét,</li>
     *   <li>a "Start" gombot, amely elindítja a játékot két játékos között.</li>
     * </ul>
     *
     * @param menuPair a menübe visszalépéshez használt CardLayout és JPanel pár
     * 
     * @param state a játék állapota
     * @param list a ranglistát tartalmazó ScoreList
     */
    public TwoPlayerGame(Pair<CardLayout, JPanel> menuPair,GameState state, ScoreList list){
        super(menuPair, list);
        CardLayout cardLayout=menuPair.getFirst();
        JPanel cardPanel= menuPair.getSecond();
        JComboBox<String> comboBox1 = newJComboBox(1);
        JComboBox<String> comboBox2 = newJComboBox(2);
        
        JButton start=startButton();
        start.addActionListener(e-> {
            setPlayer((String)comboBox1.getSelectedItem(),(String)comboBox2.getSelectedItem(),false,false, state);
            cardPanel.add(new GameDama(true, state,menuPair,list),"gamedama");
            cardLayout.show(cardPanel,"gamedama");
        });
        this.add(start);
        
    }
}
