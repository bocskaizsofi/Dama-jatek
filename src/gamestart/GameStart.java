package gamestart;

import javax.swing.*;
import java.awt.CardLayout;

import game.GameDama;
import game.GameState;
import leaderboard.ScoreList;
import menu.Menu;
import menu.Pair;

/**
 * A GameStart osztály a játék indítópanelje, amelyben a felhasználó kiválaszthatja a játékmódot.
 * <p>
 * Feladatai:
 * <ul>
 *   <li>Megjeleníti a játékmód választó legördülő listát.</li>
 *   <li>Kezeli az "OK" gombot, amely a kiválasztott játékmódnak megfelelő panelt nyitja meg.</li>
 *   <li>Elindítja a megfelelő játékot (két játékos, egy játékos gép ellen, vagy gép-gép).</li>
 * </ul>
 */
public class GameStart extends GamePanel {

    /**
     * Létrehozza a játék indítópanelt.
     * <p>
     * A panel tartalmazza:
     * <ul>
     *   <li>a játékmód választó legördülő listát,</li>
     *   <li>az "OK" gombot, amely a kiválasztott játékmódnak megfelelő panelt nyitja meg (gép-gép mód esetén már indul a játék).</li>
     * </ul>
     *
     * @param menuPair a menübe visszalépéshez használt CardLayout és JPanel pár
     * @param list a ranglistát tartalmazó ScoreList
     */
    public GameStart(Pair<CardLayout, JPanel> menuPair, ScoreList list){
        super(menuPair, list);
        CardLayout cardLayout=menuPair.getFirst();
        JPanel cardPanel= menuPair.getSecond();
        String[] options = {"Gép-Gép", "Játékos-Gép", "Játékos-Játékos"};
        JComboBox<String> comboBox = new JComboBox<>(options);
        JPanel playerModePanel = playerPanel(comboBox, 1, "Válasszon játékmódot");
        JButton ok = new JButton("OK");
        ok.setBackground(Menu.buttonColor); 
        ok.setBounds(50, 300, 150, 50);
        ok.addActionListener(e->{
                GameState state=new GameState();
                if("Játékos-Játékos".equals(comboBox.getSelectedItem())){
                    cardPanel.add(new TwoPlayerGame(menuPair,state, list),"twoplayergame");
                    cardLayout.show(cardPanel,"twoplayergame");
                }   
                else if("Játékos-Gép".equals(comboBox.getSelectedItem())){
                    cardPanel.add(new OnePlayerGame(menuPair, state, list),"oneplayergame");
                    cardLayout.show(cardPanel,"oneplayergame");
                }
                else{
                    setPlayer("Gép 1","Gép 2",true,true, state);
                    cardPanel.add(new GameDama(true,state,menuPair,list),"gamedama");
                    cardLayout.show(cardPanel,"gamedama");
                }  
        });
        this.add(playerModePanel);
        this.add(ok);
        
    }
}
