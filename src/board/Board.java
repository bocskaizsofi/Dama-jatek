package board;

import javax.swing.*;
import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import game.*;
import leaderboard.ScoreList;
import menu.Menu;
import menu.Pair;

/**
 * A Board osztály a dámajáték tábláját jeleníti meg és kezeli a mezők létrehozását.
 * <p>
 * Feladatai:
 * <ul>
 *   <li>Kirajzolja a 8x8-as táblát sor- és oszlopszámozással.</li>
 *   <li>Elhelyezi a bábukat a kezdőállásnak megfelelően, vagy betölti őket mentett állapotból.</li>
 *   <li>Kezeli a mezők szomszédsági viszonyait (átlós kapcsolatok).</li>
 *   <li>Lehetővé teszi a játék állapotának mentését fájlba.</li>
 * </ul>
 */
public class Board extends JPanel {
    /** A tábla oszlopainak betűjelölései (az első elem üres, mert az oszlopjelölés az 1. oszloptól indul). */
    private static final String[] letters = {"", "A", "B", "C", "D", "E", "F", "G", "H"};
     /** A tábla sorainak száma. */
    private static final int rows=8;
    /** A tábla oszlopainak száma. */
    private static final int cols=8;
    /** A játék logikáját kezelő objektum. */
    private Game game;

    /**
     * Visszaadja a játék logikáját kezelő objektumot.
     * 
     * @return a játék logikáját kezelő objektum
     */
    public Game getGame(){return game;}

    /**
     * Létrehoz egy új JLabel komponenst a sor- vagy oszlopjelöléshez.
     *
     * @param name a létrehozott komponens szövege
     * @return a létrehozott JLabel
     */
    private JLabel newLabel(String name){
        JLabel label= new JLabel(name);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER); 
        label.setFont(new Font("Arial", Font.BOLD, 13));
        return label;
    }

    /**
     * Hozzáad egy új bábut a megadott mezőhöz a kezdőállásnak megfelelően.
     *
     * @param square a mező
     * @param row a sor indexe
     * @param state a játék állapota
     */
    private void addChecker(Square square, int row, GameState state){
        if(row < rows/2 - 1){ //felső sorokban sötét bábuk
            square.setChecker(new Checker(state.getDark(),true));
        }else if(row >= rows / 2 + 1){ //alsó sorokban világos bábuk
            square.setChecker(new Checker(state.getWhite(),false));
        }
    }

    /**
     * Hozzáad egy mezőt a táblához és szükség esetén bábut állít be.
     *
     * @param newBoard igaz, ha új játék kezdődik; hamis, ha betöltött állapotból indul
     * @param checkers a betöltött bábuk listája
     * @param i a sor indexe
     * @param j az oszlop indexe
     * @param state a játék állapota
     * @param sList a mezők listája
     */
    private void addSquare(boolean newBoard, ArrayList<Checker>checkers,int i, int j, GameState state, ArrayList<Square> sList){
        JPanel squarePanel = new JPanel();
        if((i + j) % 2 == 0){ //sötét mező esetén bábu
        	squarePanel.setBackground(Menu.darkColor);
            Square square= new Square(i);
            if(!newBoard){  //folytatott játék esetén mentett bábu
            	square.setChecker(checkers.get(sList.size()));
            }
            else{ //új játék esetén új bábu hozzáadása
                addChecker(square,i, state);
            }
            square.addActionListener(e -> game.handleClick(square));
            sList.add(square);
            squarePanel.add(square);
        }else{
        	squarePanel.setBackground(Menu.whiteColor);
        }
        this.add(squarePanel);
    }

    /**
     * Mentettnek nyilvánítja és fájlba menti a játék állapotát.
     *
     * @param state a játék állapota
     */
    public void writeGame(GameState state){
        try {
            state.setContinued(true);
            FileOutputStream f =new FileOutputStream(Menu.gameFile);
            ObjectOutputStream out =new ObjectOutputStream(f);
            out.writeObject(state);
            ArrayList<Checker>checkerList= new ArrayList<>();
            for(int i=0; i < game.getSquares().size(); i++){
                checkerList.add(game.getSquares().get(i).getChecker());
            }
            out.writeObject(checkerList);    
            out.close();
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }
    
    /**
     * Beállítja a mezők szomszédsági viszonyait.
     *
     * @param squares a mezők listája
     */
    public void addNeighbours(ArrayList<Square>squares ) {
        for (int index = 0; index < squares.size(); index++) {
            Square s = squares.get(index);

            int row = index / (cols / 2);       // minden sorban fele mező sötét
            int col = (index % (cols / 2)) * 2 + ((row + 1) % 2); 
            //párosoknál a sötét mezők számával vett maradék 2-szerese, mert csak sötét mezőket néz, páros sornál 1-gyel eltolás (a sorok számozása 0-tól indul)

            Square leftUp;
            if(row > 0 && col > 0)
                leftUp=findSquare(squares,row - 1, col - 1);
            else
                leftUp=null;

            Square rightUp;
            if(row > 0 && col < cols - 1)
                rightUp=findSquare(squares,row - 1, col + 1);
            else
                rightUp=null;
        
        
            Square leftDown;
            if(row < rows - 1 && col > 0)
                leftDown=findSquare(squares,row + 1, col - 1);
            else
                leftDown=null;

            Square rightDown;
            if(row < rows - 1 && col < cols - 1)
                rightDown=findSquare(squares,row + 1, col + 1);
            else
                rightDown=null;

            s.setNeighbours(leftUp, rightUp, leftDown, rightDown);
        }
    }

    /**
     * Visszaadja a megfelelő mezőt a mezők megadott listájából a sora és oszlopa alapján.
     *
     * @param squares a mezők listája
     * @param row a sor indexe
     * @param col az oszlop indexe
     * @return a megfelelő mező
     */
    private Square findSquare(ArrayList<Square>squares,int row, int col) {
        int index = row * (cols/2) + (col / 2);
        return squares.get(index);
    }

    /**
     * Létrehozza a dáma tábla panelt.
     *
     * @param newBoard igaz, ha új játék kezdődik; hamis, ha betöltött állapotból indul
     * @param state a játék állapota
     * @param checkers a betöltött bábuk listája
     * @param gameDama a játékpanel
     * @param menuPair a menübe visszalépéshez használt CardLayout és JPanel pár
     * @param list a ranglistát tartalmazó ScoreList
     */
    public Board(boolean newBoard, GameState state, ArrayList<Checker> checkers,GameDama gameDama,Pair<CardLayout, JPanel> menuPair, ScoreList list){
        super(new GridLayout(rows+1, cols+1));
        this.setBounds(300, 100, 400, 400);
        this.setBackground(Menu.buttonColor);
        ArrayList<Square> squares= new ArrayList<>();
        for (int i = 0; i <= rows; i++) {
            for (int j = 0; j <= cols; j++) {
                if (i == rows) {
                    this.add(newLabel(letters[j]));
                } else if (j == 0) {
                    this.add(newLabel(String.valueOf(rows - i)));
                } else {
                   addSquare(newBoard,checkers,i,j, state,squares);
                }
            }
        }
        game= new Game(squares,gameDama,menuPair,list,state);
        addNeighbours(squares);
    }


}
