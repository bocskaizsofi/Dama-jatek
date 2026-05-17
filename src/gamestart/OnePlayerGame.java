package gamestart;

import java.awt.CardLayout;

import javax.swing.*;

import game.GameDama;
import game.GameState;
import leaderboard.ScoreList;
import menu.Pair;

/**
 * A OnePlayerGame osztály a játék indítópanelje egy játékos és a gép közötti játékhoz.
 * <p>
 * Feladatai:
 * <ul>
 *   <li>Megjeleníti a játékos névbeviteli mezőjét.</li>
 *   <li>Beállítja a játékos és a gép szerepét a játék állapotában.</li>
 *   <li>Elindítja a játékot a "Start" gombbal.</li>
 * </ul>
 */
public class OnePlayerGame extends GamePanel{

     /**
     * Létrehozza az egyjátékos panelt.
     * <p>
     * A panel tartalmazza:
     * <ul>
     *   <li>a játékos névbeviteli mezőjét,</li>
     *   <li>a "Start" gombot, amely elindítja a játékot a gép ellen.</li>
     * </ul>
     *
     * @param menuPair a menübe visszalépéshez használt CardLayout és JPanel pár
     * @param state a játék állapota
     * @param list a ranglistát tartalmazó ScoreList
     */
    public OnePlayerGame(Pair<CardLayout, JPanel> menuPair,GameState state, ScoreList list){
        super(menuPair, list);
        CardLayout cardLayout=menuPair.getFirst();
        JPanel cardPanel= menuPair.getSecond();
        JComboBox<String> comboBox1 = newJComboBox(1);
        
        JButton start=startButton();
        start.addActionListener(e->{
            setPlayer((String)comboBox1.getSelectedItem(), "Gép",false,true,state);
            cardPanel.add(new GameDama(true,state,menuPair,list),"gamedama");
            cardLayout.show(cardPanel,"gamedama");
        });
        this.add(start);
        
    }
}
