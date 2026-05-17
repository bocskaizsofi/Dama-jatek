package menu;
import javax.swing.*;

import gamestart.GameStart;
import leaderboard.LeaderBoard;
import leaderboard.ScoreList;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * A Menu osztály a dáma játék menü ablakát valósítja meg.
 *<p>
 * Definiálja a játékhoz tartozó színeket és fájlokat.<br>
 * Kezeli a menüt és a különböző nézeteket, illetve betölti és menti a ranglistát.<br>
 * A nézetek közötti váltást CardLayout valósítja meg.
 * </p>
 */
public class Menu extends JFrame{
    /** A szöveg háttérszíne */
    public static final Color textBackColor= new Color(230,208,185);
    /** A szöveg előtérszíne. */
    public static final Color textForeColor= new Color(125,114,101);
    /** Az ablak háttérszíne. */
    public static final Color backGroundColor= new Color(181,152,152);
    /** A gombok színe. */
    public static final Color buttonColor= new Color(226,234,244);
    /** A sötét mezők színe. */
    public static final Color darkColor= new Color(130,93,58);
    /** A világos mezők színe. */
    public static final Color whiteColor= new Color(224,199,172);

    /** A játékállás fájl neve. */
    public static final String gameFile="games.txt";
    /** A ranglista fájl neve. */
    public static final String scoreFile="scoreboard.txt";

    /** A ranglistát tároló objektum. */
    private ScoreList scoreList;
    

    /** A nézetek közötti váltást vezérlő CardLayout. */
    private CardLayout cardLayout;
    /** A fő panel, amely tartalmazza a nézeteket. */
    private JPanel cardPanel;

     /**
     * Létrehozza a menü ablakot.
     * <p>
     * Beállítja az ablak méretét és címét.<br>
     * A fent definiált fájlból inicializálja a ranglistát és addWindowListener-rel gondoskodik a mentéséről az ablak bezárásakor.<br>
     * Létrehozza a nézeteket és hozzáadja őket a CardLayout-hoz.
     * </p>
     */
    public Menu(){
        super("Dáma");
        setSize(800,800);
        setLocationRelativeTo(null);   
        cardLayout=new CardLayout();
        scoreList=new ScoreList(scoreFile);
        setCardPanel();
        add(cardPanel);
        setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                scoreList.saveScore();
            }
        });
    }

     /**
     * Beállítja egy komponens fix méretét.
     *
     * @param component a komponens
     * @param width a szélesség
     * @param height a magasság
     */
    public static void setComponentSize(JComponent component, int width, int height){
        Dimension componentSize = new Dimension(width, height);
        component.setPreferredSize(componentSize);
        component.setMinimumSize(componentSize);
        component.setMaximumSize(componentSize);
    }

    /**
     *
     * Létrehozza és beállítja a fő panelt, amely tartalmazza az alábbi nézeteket: 
     * 
     * <ul>
     * <li>- főmenü,</li>
     * <li>- szabályok,</li>
     * <li>- ranglista,</li>
     * <li>- új játék.</li>
     * </ul>
     * <p>
     * A nézetek közötti váltást a Pair-ben tárolt CardLayout és JPanel segítségével a gombok tudják vezérelni.
     * </p>
     */
    private void setCardPanel(){
        cardPanel= new JPanel(cardLayout);
        Pair<CardLayout,JPanel> menuPair= new Pair<>(cardLayout, cardPanel);

        JPanel menuPanel=new MenuPanel(menuPair,scoreList);
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        cardPanel.add(menuPanel,"menu");

        cardPanel.add(new Rule(menuPair),"rule");

        cardPanel.add(new LeaderBoard(menuPair, scoreList),"leaderboard");

        cardPanel.add(new GameStart(menuPair, scoreList),"newgame");
    }

    	/**
     * A program belépési pontja.
     * <p>
     * Létrehozza és megjeleníti a főmenüt.
     * </p>
     */
     public static void main(String[] args) { 
        Menu menu= new Menu();
        menu.setVisible(true);
        
    }

}
