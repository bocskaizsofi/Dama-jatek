package menu;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import board.Checker;
import game.GameDama;
import game.GameEnd;
import game.GameState;
import leaderboard.ScoreList;

/**
 * A MenuPanel osztály a dáma játék menü paneljét valósítja meg.
 * <p>
 * A gombok segítségével a felhasználó a megfelelő nézetre válthat.
 *<br>
 * A panel tartalmazza a menü gombjait:
 * <ul>
 * <li>- Új játék indítása,</li>
 * <li>- Előző játék folytatása,</li>
 * <li>- Ranglista,<li>
 * <li>- Játékszabályok.</li>
 * </ul>
 */
public class MenuPanel extends JPanel{
    /** A menü váltásához használt CardLayout és JPanel pár. */
    private Pair<CardLayout,JPanel> menuPair;

    /** A ranglistát tartalmazó ScoreList objektum. */
    private ScoreList list;
    
    /**
     * Létrehozza a menü panelt a gombokkal, amik segítségével nézetet lehet váltani.
     *
     * @param menuPair a menübe visszalépéshez használt CardLayout és JPanel pár
     * @param list a ranglistát tartalmazó ScoreList
     */
    public MenuPanel(Pair<CardLayout,JPanel> menuPair, ScoreList list){
        this.list=list;
        this.menuPair=menuPair;
        setBackground(Menu.backGroundColor); 
         // Gombok elhelyezése függőleges irányban, középre igazítva
        add(Box.createVerticalGlue());
        add(menuButton("Új játék indítása","newgame"));
        add(Box.createVerticalStrut(10));

        JButton prev=menuButton("Előző játék folytatása","prevgame");
        prev.addActionListener(e->prevGame());
        add(prev);
        add(Box.createVerticalStrut(10));

        add(menuButton("Ranglista","leaderboard"));

        add(Box.createVerticalStrut(10));
        add(menuButton("Játékszabály","rule"));
        add(Box.createVerticalGlue());
    }

     /**
     * Előző játék folytatása.
     *<p>
     * Beolvassa a félbehagyott játék állapotát a fájlból.Ha van mentett játék, létrehoz egy új GameDama panelt és megjeleníti, ellenkező esetben egy GameEnd ablak jelenik meg. 
     * </p>
     */
    private void prevGame(){
        CardLayout cardLayout=menuPair.getFirst();
        JPanel cardPanel=menuPair.getSecond();
        Pair<GameState, ArrayList<Checker>> result = GameDama.readGame();
        GameState s= result.getFirst();
        ArrayList<Checker>checkers = result.getSecond();
        if(checkers==null||checkers.isEmpty()){
            GameEnd g= new GameEnd(null,"Nincs félbehagyott játék.",s,menuPair, list);
            g.setVisible(true);
        }
        else{
            // Ha már létezik "prevgame" panel, eltávolítjuk
            for (Component comp : cardPanel.getComponents()) {
                if ("prevgame".equals(comp.getName())) {
                    cardPanel.remove(comp);
                    break;
                }
            }
            // Új játékpanel létrehozása a mentett állapotból
            GameDama gamePanel = new GameDama(false, s,menuPair, list);
            gamePanel.setName("prevgame");
            cardPanel.add(gamePanel, "prevgame");
            cardLayout.show(cardPanel, "prevgame");
            cardPanel.revalidate();
            cardPanel.repaint();
        }
    }

    /**
     * Létrehoz egy menü gombot a megadott szöveggel és panelnévvel.
     *
     * @param text a gomb felirata
     * @param panelName a panel neve, amelyre kattintáskor vált
     * @return a létrehozott JButton
     */
    public JButton menuButton(String text, String panelName){
        JButton button = new JButton(text);
        button.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        Menu.setComponentSize(button, 250, 40);
        button.setBackground(Menu.buttonColor); 
        button.setFont(new Font("Arial",Font.BOLD,16));
        button.addActionListener(e->menuPair.getFirst().show(menuPair.getSecond(),panelName));
        return button;
    }
}
