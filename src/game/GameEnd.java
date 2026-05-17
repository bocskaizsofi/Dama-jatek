package game;
import menu.Menu;
import menu.MenuPanel;
import menu.Pair;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;

import leaderboard.*;

/**
 * A GameEnd osztály a játék végét megjelenítő dialógusablakot valósítja meg.
 * <p>
 * Feladatai:
 * <ul>
 * <li>- megjeleníti a játék eredményét (győztes vagy döntetlen),</li>
 * <li>- frissíti a ranglistát a játékosok adataival,</li>
 * <li>- visszaléptet a főmenübe,</li>
 * <li>- üríti a mentett játékállást, ha folytatott játék volt.</li>
 * </ul>
 */
public class GameEnd extends JDialog{

    /** A menübe visszalépéshez használt CardLayout és JPanel pár. */
    private Pair<CardLayout,JPanel> menuPair;

    /**
     * Létrehoz egy JLabel komponenst, amely az eredményt jeleníti meg.
     *
     * @param resultStr az eredmény szövege
     * @return az eredményt megjelenítő JLabel
     */
    public JLabel setResult(String resultStr){
        JLabel result=new JLabel(resultStr);
        result.setHorizontalAlignment(SwingConstants.CENTER);
        result.setVerticalAlignment(SwingConstants.CENTER);
        result.setBackground(Menu.textBackColor);
        result.setForeground(Menu.textForeColor);
        result.setFont(new Font("Arial", Font.BOLD, 27));
        result.setOpaque(true);
        return result;
    }

    /**
     * Eltávolítja a megadott nevű panelt a főpanelből.
     *
     * @param panelName az eltávolítandó panel neve
     */ 
    private void removePanel(String panelName){
        JPanel cardPanel=menuPair.getSecond();
        for (Component comp : cardPanel.getComponents()) {
            if (comp.getName() != null &&panelName.equals(comp.getName()) ) {
                cardPanel.remove(comp);
                break;
            }
        }
        cardPanel.revalidate();
        cardPanel.repaint();
    }

    /**
     * Létrehoz egy "OK" gombot, amely kattintásra visszalép a főmenübe.
     * <p>
     * A gomb eltávolítja az előző játék panelt, újra létrehozza a főmenü panelt, majd bezárja a dialógusablakot.
     * </p>
     *
     * @param list a ranglistát tartalmazó ScoreList
     * @return az "OK" gomb
     */
    public JButton okButton(ScoreList list){
        JButton ok=new JButton("OK");
        Menu.setComponentSize(ok, 40, 40);
        ok.setBackground(Menu.buttonColor); 
        ok.addActionListener(e->{
            removePanel("prevgame");
            JPanel menuPanel=new MenuPanel(menuPair,list);
            menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
            menuPair.getSecond().add(menuPanel,"menu");
            menuPair.getFirst().show(menuPair.getSecond(),"menu");
            dispose();
        });
        return ok;
    }

    /**
     * Létrehozza a játék végét megjelenítő dialógusablakot.
     * <p>
     * Megjeleníti az eredményt, frissíti a ranglistát, és kezeli a folytatott játék állapotát.
     * </p>
     *
     * @param winnerPlayer a győztes játékos (null, ha nincs győztes)
     * @param resultStr az eredmény szövege
     * @param state a játék állapota
     * @param menuPair a menübe visszalépéshez használt CardLayout és JPanel pár
     * @param list a ranglistát tartalmazó ScoreList
     */
    public GameEnd(Player winnerPlayer, String resultStr, GameState state,Pair<CardLayout, JPanel> menuPair, ScoreList list){
        setLayout(new BorderLayout(2,1));
        setModal(true);
        setSize(400, 400);
        setLocationRelativeTo(null);
        this.menuPair=menuPair;
        if(winnerPlayer!=null){
            Player winner=winnerPlayer;
            winner.addWin(1);
            resultStr="Győztes: "+winner.getName();
        }
        add(setResult(resultStr),BorderLayout.CENTER);
        add(okButton(list),BorderLayout.SOUTH);
        if(state!=null){
            list.add(state.getDark());
            list.add(state.getWhite());
            removePanel("leaderboard");
            menuPair.getSecond().add(new LeaderBoard(menuPair, list),"leaderboard");
            if(state.getContinued()){
                try {
                    FileWriter fw = new FileWriter(Menu.gameFile);
                    fw.write("");
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
    }
}
