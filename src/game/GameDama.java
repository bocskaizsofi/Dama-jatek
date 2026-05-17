package game;

import javax.swing.*;

import board.*;
import leaderboard.Player;
import leaderboard.ScoreList;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import menu.BackToMenuButton;
import menu.Menu;
import menu.Pair;
import menu.Rule;

/**
 * A GameDama osztály a dáma játék játék közben megjelenített panele.
 * <p>
 * Feladatai:
 * <ul>
 *   <li>Megjeleníti a táblát és a játékosok információit.</li>
 *   <li>Kezeli a vezérlőgombokat (szünet, döntetlen, szabályok).</li>
 *   <li>Betölti a mentett játékot vagy új játékot indít.</li>
 * </ul>
 */
public class GameDama extends JPanel{

    /** Az aktuális lépést jelző címke. */
    private JLabel move;
    /** A sötét játékos bábuszámát megjelenítő címke. */
    private JLabel darkCheckerNumber;
    /** A világos játékos bábuszámát megjelenítő címke. */
    private JLabel whiteCheckerNumber;
    /** A játék állapota. */
    private GameState state;
    /** A menübe visszalépéshez használt CardLayout és JPanel pár. */
    private Pair<CardLayout,JPanel> menuPair;
    /** A ranglistát tartalmazó ScoreList. */
    private ScoreList list;

    /**
    * Formázza a megadott komponenst (méret, betűtípus, háttérszín, keret).
    *
    * @param component a komponens
    * @param color a háttérszín (null esetén alapértelmezett)
    */
    private void format(JComponent component, Color color){
        component.setFont(new Font("Arial",Font.BOLD,14));
        Menu.setComponentSize(component, 200, 40);
        if(color==null){
            component.setBackground(Menu.buttonColor);
        }
        else{
            component.setOpaque(true);
            component.setBackground(color);
        }   
        component.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    /**
    * Frissíti az aktuális lépést jelző címkét.
    */
    public void currentMove(){
        move.setText("Lép: " + state.getCurrent().getName());
    }

    /**
     * Visszaadja a sötét játékos bábuszámát megjelenítő címkét.
     *
     * @return JLabel a sötét játékos bábuszámával
     */
    public JLabel getDarkCheckerNumberLabel() {
        return darkCheckerNumber;
    }

    /**
     * Visszaadja a világos játékos bábuszámát megjelenítő címkét.
     *
     * @return JLabel a világos játékos bábuszámával
     */
    public JLabel getWhiteCheckerNumberLabel() {
        return whiteCheckerNumber;
    }

    /**
     * Létrehoz egy "Játékszabály" gombot, amely megjeleníti vagy elrejti a szabályokat.
     *
     * @param panel a panel, amelyhez a szabályszöveg tartozik
     * @return a létrehozott gomb
     */
      private JButton ruleButton(JPanel panel){
        JButton rule= new JButton("Játékszabály");
        format(rule,null);
        JTextArea ruleText = Rule.ruleText(10, 0);
        ruleText.setBounds(50, 520, 700, 230);
        ruleText.setVisible(false);
        panel.add(ruleText);
        rule.addActionListener(e -> {
            ruleText.setVisible(!ruleText.isVisible());
            panel.revalidate();
            panel.repaint();
        });
        return rule;
    }

    /**
     * Létrehozza és inicializálja az aktuális lépést jelző címkét.
     *
     * @return JLabel az aktuális lépést jelző szöveggel
     */
    private JLabel setMove(){
        move= new JLabel("Lép: "+state.getCurrent().getName());
        move.setOpaque(true);
        format(move,Menu.buttonColor);
        move.setHorizontalAlignment(SwingConstants.CENTER);
        return move;
    }

    /**
     * Létrehoz egy "Szünet" gombot, amely kattintásra elmenti a játékot és visszalép a menübe.
     *
     * @param board a játék táblája
     * @return a létrehozott JButton
     */
    private JButton pauseButton(Board board){
        JButton pause= new JButton("Szünet");
        pause.addActionListener(e -> {
            if(!state.getCurrent().isComputer()){
                board.writeGame(state);
                menuPair.getFirst().show(menuPair.getSecond(),"menu");
            }
        });
        format(pause,null);
        return pause;
    }

    /**
    * Létrehoz egy "Döntetlen kérése" gombot.
     * <p>
     * Ha a játékos gép ellen játszik, automatikusan döntetlen lesz, ha ember ellen, akkor döntetlen kérés dialógusablakot nyit.
     * </p>
     *
     * @return a létrehozott JButton
     */
    private JButton drawButton(){
        JButton draw=new JButton("Döntetlen kérése");
        format(draw,null);
        draw.addActionListener(e -> {
            if(!state.getCurrent().isComputer()){
                if((state.getCurrent().getName().equals(state.getWhite().getName())&&state.getDark().isComputer())
                	||(state.getCurrent().getName().equals(state.getDark().getName())&&state.getWhite().isComputer())){
                    GameEnd g= new GameEnd(null,"Döntetlen",state,menuPair, list);
                    g.setVisible(true);
                }
                else
                    drawCall();
            }
        });
        return draw;
    }

    /**
     * Létrehoz egy panelt, amely megjeleníti a döntetlen kérés szövegét és a válasz gombot.
     *
     * @param button a válasz gombot tartalmazó panel
     * @return a létrehozott JPanel
     */
    private JPanel drawQuestion(JPanel button){
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        content.setBackground(Menu.textBackColor);
        content.setForeground(Menu.textForeColor);
        content.setFont(new Font("Arial", Font.BOLD, 18));
        content.setOpaque(true);

        content.add(new JLabel(state.getCurrent().getName() + " döntetlent szeretne."));
        content.add(Box.createVerticalStrut(10));
        content.add(new JLabel("Elfogadod?"));
        content.add(Box.createVerticalStrut(20));
        content.add(button);
        return content;
    }

    /**
     * Létrehoz egy panelt, amely tartalmazza az "Igen" és "Nem" gombokat a döntetlen kéréshez.
     *
     * @param dialog a dialógus, amelyet bezárnak a gombok
     * @return a létrehozott JPanel
     */
    private JPanel buttonPanel(JDialog dialog){
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Menu.textBackColor);
        buttonPanel.setForeground(Menu.textForeColor);
        buttonPanel.setFont(new Font("Arial", Font.BOLD, 18));
        buttonPanel.setOpaque(true);
        JButton yes = new JButton("Igen");
        yes.setBackground(Menu.buttonColor);
        yes.addActionListener(e -> {
            dialog.dispose();
            GameEnd g= new GameEnd(null,"Döntetlen",state,menuPair, list);
            g.setVisible(true);
        });
        JButton no = new JButton("Nem");
        no.setBackground(Menu.buttonColor);
        no.addActionListener(e->dialog.dispose());
        buttonPanel.add(yes);
        buttonPanel.add(no);
        return buttonPanel;
    }

    /**
     * Megjelenít egy ablakot, amelyben a játékos döntetlent kérhet.
     */
    private void drawCall(){
        JDialog dialog = new JDialog((Frame) null, "Döntetlen kérése", true);
        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(null);
        dialog.setLayout(new BorderLayout());      
        dialog.add(drawQuestion(buttonPanel(dialog)), BorderLayout.CENTER);
        dialog.setVisible(true);
    }

    /**
     * Hozzáadja a játékos nevét és bábuszámát megjelenítő címkéket a panelhez.
     *
     * @param panel a panel, amelyhez hozzáadja az információkat
     * @param player a játékos
     * @param playerColor a játékos színe (szövegesen)
     * @param color a háttérszín
     * @param checkerNumber a bábuszámot megjelenítő JLabel
     */
    private void addPlayerInfo(JPanel panel, Player player, String playerColor, Color color, JLabel checkerNumber){
        JLabel name= new JLabel(playerColor+" játékos: "+player.getName());
        format(name,color);
        panel.add(name);
        format(checkerNumber,color);
        panel.add(checkerNumber);
    }

    /**
     * Beolvassa a mentett játékot fájlból.
     *
     * @return egy Pair, amely tartalmazza a játék állapotát és a bábuk listáját
     */
    public static Pair<GameState, ArrayList<Checker>> readGame(){
        ArrayList<Checker> checkers= new ArrayList<>();
        GameState state = null;
        try{
        	FileInputStream f =new FileInputStream(Menu.gameFile);
            ObjectInputStream in =new ObjectInputStream(f);
            state = (GameState)in.readObject();
            checkers = (ArrayList<Checker>)in.readObject();
            in.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }catch(ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        return new Pair<>(state, checkers);
    }

    /**
     * Létrehozza a játék főpanelt.
     * <p>
     * Ha új játék indul, inicializálja a táblát kezdőállással.
     * <br>
     * Ha folytatott játék van, beolvassa a mentett állapotot.
     * </p>
     *
     * @param newGame igaz, ha új játék indul; hamis, ha folytatott játék
     * @param state a játék állapota
     * @param menuPair a menübe visszalépéshez használt CardLayout és JPanel pár
     * @param list a ranglistát tartalmazó ScoreList
     */
    public GameDama(boolean newGame, GameState state, Pair<CardLayout, JPanel> menuPair, ScoreList list){
        this.setBackground(Menu.backGroundColor);
        this.setLayout(null);
        this.menuPair=menuPair;
        this.list=list;
        
        ArrayList<Checker>checkers= new ArrayList<>();
        Board board;
        if(!newGame){
            Pair<GameState, ArrayList<Checker>> result = readGame();
            state=result.getFirst();
            checkers=result.getSecond();
            board=new Board(newGame,state, checkers,this,menuPair,list);
        }
        else{
            board=new Board(newGame,state, checkers,this, menuPair,list);
        }
        this.state=state;
        this.add(board);
        JButton back = new BackToMenuButton(menuPair);
        back.addActionListener(e -> {menuPair.getFirst().show(menuPair.getSecond(),"menu"); board.getGame().stop();});
        this.add(back);
        JPanel playerInfo = new JPanel(new GridLayout(9, 1, 0, 0));
        playerInfo.setBounds(50, 100, 200, 360);

        darkCheckerNumber= new JLabel("Bábuk száma: "+state.getDarkCheckerNumber());
        addPlayerInfo(playerInfo,state.getDark(),"Sötét",Menu.darkColor,darkCheckerNumber);
        
        setMove();
        playerInfo.add(move);
        if(state.getCurrent().isComputer()){
            board.getGame().moveComputer();
        }
        whiteCheckerNumber= new JLabel("Bábuk száma: "+state.getWhiteCheckerNumber());
        addPlayerInfo(playerInfo,state.getWhite(),"Világos",Menu.whiteColor,whiteCheckerNumber);

        JLabel space= new JLabel("");
        space.setOpaque(true);
        space.setBackground(Menu.backGroundColor);
        playerInfo.add(space);

        playerInfo.add(pauseButton(board));
        playerInfo.add(drawButton());
        playerInfo.add(ruleButton(this));
        this.add(playerInfo);
    }
}
