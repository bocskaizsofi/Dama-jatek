package game;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

import board.*;

import java.awt.*;

import leaderboard.Player;
import leaderboard.ScoreList;
import menu.Pair;

/**
 * A Game osztály a dáma játék logikáját kezeli.
 * <p>
 * Feladatai:
 * <ul>
 *   <li>A lépések és ütési szabályok ellenőrzése.</li>
 *   <li>A játékosváltás és a játék végének kezelése.</li>
 *   <li>A számítógép lépéseinek generálása.</li>
 *   <li>A bábuk mozgatása és koronázása.</li>
 * </ul>
 */
public class Game {

    /** A tábla mezőinek listája. */
    private ArrayList<Square> squares; 
    /** Az éppen kiválasztott mező. */
    private Square selectedSquare;
    /** A játék főpanelje. */
    private GameDama gameDama;
    /** A menübe visszalépéshez használt CardLayout és JPanel pár. */
    private Pair<CardLayout,JPanel> menuPair;
    /** A ranglistát tartalmazó ScoreList. */
    private ScoreList list;
    /** Véletlenszám generátor a számítógép lépéseihez. */
    private Random random;
    /** A játékhoz tartozó játékállás. */
    private GameState state;
    /** A géphez tartozó késleltetés időzítője. */
    Timer timer;

    /**
     * Létrehozza a játék logikát kezelő objektumot.
     *
     * @param squares a mezők listája
     * @param gameDama a játék főpanelje
     * @param menuPair a menübe visszalépéshez használt páros
     * @param list a ranglista
     * @param state a játékállás
     */
    public Game(ArrayList<Square> squares,GameDama gameDama, Pair<CardLayout, JPanel> menuPair, ScoreList list, GameState state){
        this.squares= squares ;
        selectedSquare=null;
        this.gameDama=gameDama;
        this.menuPair=menuPair;
        this.list=list;
        this.state=state;
        random = new Random();
    }
   
    /**
     * Visszaadja a mezők listáját.
     *
     * @return a mezők listája
     */
    public ArrayList<Square> getSquares(){
        return squares;
    }

    /**
     * Késleltetve meghívja a számítógép lépését.
     */
    private void delayedMove() {
        timer = new Timer(2000, e -> moveComputer()); 
        timer.setRepeats(false);
        timer.start();
    }

    /**
     * Leállítja a gép időzítőjét.
     */
    public void stop() {
        if (timer != null) {
            timer.stop();
        }
    }
    
    /**
     * Játékosváltás: növeli a körszámot, ellenőrzi a játék végét, és ha a következő játékos gép, akkor elindítja a gépi lépést.
     */
    private void switchPlayer(){
        state.switchPlayer();
        if (checkGameEnd()) 
            return;
        gameDama.currentMove();
        if(state.getCurrent().isComputer())
            delayedMove();
        
    }

    /**
     * Ellenőrzi, hogy véget ért‑e a játék.
     * <p>
     * Amennyiben a játék véget ért, megjeleníti a játék végéért felelős ablakot.
     * </p>
     * @return true, ha a játék véget ért
     */
    private boolean checkGameEnd() {
        if (state.getDarkCheckerNumber() == 0) {
            new GameEnd(state.getWhite(), "", state, menuPair,list).setVisible(true);
            return true;
        }
        if (state.getWhiteCheckerNumber() == 0) {
            new GameEnd(state.getDark(), "", state, menuPair,list).setVisible(true);
            return true;
        }
        if (!mustCapture() && !canStep()) {
            new GameEnd(state.getOpponent(), "", state, menuPair,list).setVisible(true);
            return true;
        }
        if (state.isDraw()) {
            new GameEnd(null, state.getDrawReason(), state, menuPair,list).setVisible(true);
            return true;
        }
        return false;
    }

    /**
     * Csökkenti a megadott játékos bábuszámát és frissíti a kijelzőt.
     *
     * @param player a játékos
     */
    private void subCheckerNumber(Player player){
        state.subCheckerNumber(player);
        if(state.getOpponent().equals(state.getDark())){
            gameDama.getDarkCheckerNumberLabel().setText("Bábuk száma: "+state.getDarkCheckerNumber());
        }
        else{
            gameDama.getWhiteCheckerNumberLabel().setText("Bábuk száma: "+state.getWhiteCheckerNumber());
        }
    } 

    /**
     * Megkeresi a számítógép lépését.
     * <p>
     * Ha kötelező ütés van, abból választ véletlenszerűen, különben véletlenszerű ütés nélküli lépést választ.
     * </p>
     *
     * @return a számítógép lépése
     */
    private Move findComputerMove(){
        Move captureMove=MovefindRandomCapture();
        if(captureMove!=null)
            return captureMove;
        return findRandomMove();
    }

    /**
     * Véletlenszerű ütést választ a számítógép számára.
     *
     * @return egy véletlenszerű ütés, vagy null ha nincs
     */
    private Move MovefindRandomCapture(){
        ArrayList<Move> validCaptures= new ArrayList<>();
        for (Square s : squares) {
            if (s!=null&&s.getChecker() != null && s.getChecker().isOwner(state.getCurrent())) { // a mezőn az aktuális játékos bábuja van
                for (Square sn : s.getNeighbours()){ //a játékos bábujának mezőjével szomszédos nem üres mezők
                    if(sn!=null){
                        for(Square s2:sn.getNeighbours()){ // a bábutól 2 távolságra levő mezők
                            if(s2!=null&&isValidCapture(s,s2)) //ha a 2 távolságra levő mezőre érvényes az ütés az eredetiből
                                 validCaptures.add(new Move(s, s2,s.getChecker().getId()));
                        }
                     }
                 }
            }
        }
        if(validCaptures.isEmpty())
           return null;
        int randomId= random.nextInt(0,validCaptures.size());
        return validCaptures.get(randomId);
    }

    /**
     * Véletlenszerű lépést (nem ütést) választ a számítógép számára.
     *
     * @return egy véletlenszerű lépés, vagy null ha nincs
     */
    private Move findRandomMove(){
        ArrayList<Move> validMoves= new ArrayList<>();
        for (Square s : squares) {
            if (s!=null&&s.getChecker() != null && s.getChecker().isOwner( state.getCurrent())) { // a mezőn az aktuális játékos bábuja van
                for(Square sn:s.getNeighbours()){
                    if(sn!=null&&isValidStep(s, sn)){ //a bábu szomszédjára lehet lépni
                        validMoves.add(new Move(s, sn,s.getChecker().getId()));
                    }
                        
                }
            }
        }
        if(validMoves.isEmpty())
            return null;
        int randomId= random.nextInt(0,validMoves.size());
        return validMoves.get(randomId);
    }

    /**
     * Visszaadja a köztes mezőt, ha a lépés ütés.
     *
     * @param from a kiinduló mező
     * @param to a célmező
     * @return a köztes mező, ha van, különben null
     */
    private Square getMiddleIfJump(Square from, Square to) {
        if (from.getNeighbours().contains(to)) return null; //szomszédos mezőre nem ugrás
        for (Square n : from.getNeighbours()) {
            if (n != null && n.getNeighbours().contains(to)) { //kettővel melletti szomszédra ugorhat
                return n;
            }
        }
        return null;
    }
    
    
    /**
     * Végrehajtja a megadott lépést.
     * <p>
     * Kezeli az ütést, koronázást, majd ha nincs vége a játéknak, játékost vált.
     * </p>
     *
     * @param move a lépés
     */
    private void moveChecker(Move move){
        boolean crown=false;
        boolean remove=false;
        Checker moving= move.getFrom().getChecker(); //a bábu
        Square middle = getMiddleIfJump(move.getFrom(), move.getTo()); //középső mező ha van ugrás, ha nincs ugrás null
        if (middle != null && middle.getChecker() != null) { //ha van középső mező és azon van bábu leveszi
            subCheckerNumber(middle.getChecker().getOwner());
            middle.removeChecker();
            remove=true;
        }
        //átállítja a mezőket - lép a bábu
        move.getTo().setChecker(moving);
        move.getFrom().removeChecker();
        if (!moving.isCrowned()&&((moving.isOwner(state.getWhite())&& move.getTo().isDarkLine() ) //ha világos sima lépett a sötét első sorába koronázás
        		||(moving.isOwner(state.getDark()) && move.getTo().isWhiteLine()))) { //ha sötét sima lépett a világos első sorába koronázás
            moving.crown(); 
            move.getTo().setIcon(moving.getIcon()); 
            crown=true;
        }
        state.addMove(move,crown,remove);
        if (!checkGameEnd()) 
            switchPlayer();
         
    }

    /**
     * A számítógép lépését hajtja végre.
     */
    public void moveComputer(){
        Move move = findComputerMove();
        if (move != null) {
            moveChecker(move);
        }
        
    }

    /**
     * Kezeli a játékos kattintását egy mezőre.
     * <p>
     * Ha érvényes lépés, végrehajtja.
     * </p>
     *
     * @param clicked a kattintott mező
     */
    public void handleClick(Square clicked) {
        if(!state.getCurrent().isComputer()){
            if (selectedSquare == null) {
                if (clicked.getChecker()!=null && clicked.getChecker().isOwner(state.getCurrent())) {
                    selectedSquare = clicked;
                }
                return;
            } 
            Square from = selectedSquare;
            if (isValidMove(from, clicked)) {
                moveChecker(new Move(from, clicked,from.getChecker().getId()));
            }
            selectedSquare = null; 
            
        }
    }

    /**
     * Ellenőrzi, hogy a jelenlegi játékosnak kötelező-e ütést végrehajtania.
     *
     * @return true, ha kötelező ütés van
     */
    private boolean mustCapture(){
        for (Square s : squares) {
            if (s.getChecker() != null && s.getChecker().isOwner(state.getCurrent())&&canCaptureFrom(s)) { //ha a mezőn az aktuális játékos bábuja van és az tud ütni
                return true;
                
            }
        }
        return false;
    }

    /**
     * Ellenőrzi, hogy egy adott mezőről lehetséges-e ütést végrehajtani.
     *
     * @param from a kiinduló mező
     * @return true, ha lehetséges ütés
     */
    private boolean canCaptureFrom(Square from) {
        if (from.getChecker() == null) 
            return false;
        for (Square s : from.getNeighbours()){
            if(s!=null){
                for(Square s2:s.getNeighbours()){ //a 2 távoságra levő mezőre érvényes az ugrás
                    if(s2!=null&&isValidCapture(from,s2))
                        return true;
                }
            }
        }
        return false;
    }


    /**
     * Ellenőrzi, hogy egy lépés szabályos-e (nem ütés).
     *
     * @param from a kiinduló mező
     * @param to a célmező
     * @return true, ha a lépés szabályos
     */
    private boolean isValidStep(Square from, Square to) {
        if(to.getChecker()!=null) return false; //foglalt mezőre nem lehet lépni
        if(from.getNeighbours().contains(to)){ //szomszédos mezőre próbál lépni
            if(from.getChecker().isCrowned()) //koronázott bármelyik irányba léphet
                return true;
            else if(from.getChecker().isOwner(state.getDark())){ //sima sötét csak lefelé lép
                if(from.getNeighbours().get(2)==to||from.getNeighbours().get(3)==to)
                    return true;
            }else if(from.getNeighbours().get(0)==to||from.getNeighbours().get(1)==to) //sima világos csak felfelé lép
                return true;
        }
        return false;
    }

    /**
     * Ellenőrzi, hogy egy ütés szabályos-e.
     *
     * @param from a kiinduló mező
     * @param to a célmező
     * @return true, ha az ütés szabályos
     */
    private boolean isValidCapture(Square from, Square to){
        if(from.getChecker()==null)return false;
        if(to.getChecker()!=null)return false;
        Square middle=null;
        for(Square s:from.getNeighbours()){ //középső mező megkeresésée
            if(s!=null&&s.getNeighbours().contains(to)){
                middle=s;
                break;
            }
        }
        if(middle!=null&&middle.getChecker()!=null&&!middle.getChecker().isOwner(from.getChecker().getOwner())){ //van középső mező, amin az ellenfél bábuja van
            if(from.getChecker().isCrowned() && ((from.getNeighbours().get(2)==middle&&middle.getNeighbours().get(2)==to)|| //koronázott bármelyik irányba üthet, de a kiindulási mezőtől a középső és a középsőtől a célmező ugyanabban az irányban kell legyen
            	(from.getNeighbours().get(3)==middle&&middle.getNeighbours().get(3)==to)||
                (from.getNeighbours().get(0)==middle&&middle.getNeighbours().get(0)==to)||
                (from.getNeighbours().get(1)==middle&&middle.getNeighbours().get(1)==to))){
                    return true;
            }
            if(from.getChecker().isOwner(state.getDark())){
                if((from.getNeighbours().get(2)==middle&&middle.getNeighbours().get(2)==to)|| //sima sötét csak lefelé üthet, és a kiindulási mezőtől a középső és a középsőtől a célmező ugyanabban az irányban kell legyen
                    (from.getNeighbours().get(3)==middle&&middle.getNeighbours().get(3)==to)){
                        return true;
                }
            }else if((from.getNeighbours().get(0)==middle&&middle.getNeighbours().get(0)==to)|| //sima világos csak felfelé üthet, és a kiindulási mezőtől a középső és a középsőtől a célmező ugyanabban az irányban kell legyen
        		   (from.getNeighbours().get(1)==middle&&middle.getNeighbours().get(1)==to)){
                        return true;
            		}
            }
        
        return false;
    }

    /**
     * Ellenőrzi, hogy egy lépés szabályos-e (ütés vagy sima lépés).
     *
     * @param from a kiinduló mező
     * @param to a célmező
     * @return true, ha a lépés szabályos
     */
    private boolean isValidMove(Square from, Square to) {
        if (to.getChecker()!=null) return false;
        if(from.getChecker()==null) return false; 
        if (mustCapture()) { //ha tud ütni muszáj is
            return isValidCapture(from, to);
        } else { // érvényes-e a nem ütéssel járó lépés
            return isValidStep(from, to);
        }
    }

     /**
     * Ellenőrzi, hogy a jelenlegi játékos tud-e lépni (nem ütés).
     *
     * @return true, ha tud lépni
     */
    private boolean canStep(){
        for (Square s : squares) {
            if (s!=null&&s.getChecker() != null && s.getChecker().isOwner(state.getCurrent())) { //kijelölt mezőn van bábu, ami az aktuális játékosé
                for(Square sn:s.getNeighbours()){
                    if(sn!=null&&isValidStep(s, sn)) //tud lépni a szomszédos mezőre
                        return true;
                }
            }
        }
        return false;
    }
}
