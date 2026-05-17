package menu;

import javax.swing.*;
import java.awt.*;

/**
 * A Rule osztály megjeleníti a dáma játék szabályait.
 *<p>
 * A panel tartalmazza:
 * <ul>
 *  <li>- a játékszabályokat szöveges formában,</li>
 *  <li>- a menübe visszalépő gombot.</li>
 * </ul>
 */
public class Rule extends JPanel {

     /** A dáma játék részletes szabályait tartalmazó több sorra tagolt szöveg. */
    private static final String rule="Játékszabályok:\n\nA játékot 2 fő játszhatja egy 8x8-as sakktáblán, aminek a bal alsó sarka sötét mező."
    +"\n\nMinden játékosnak 12 bábuja van, ezek kezdetben a játékosokhoz közel eső első 3 sor sötét mezőin helyezkednek el."
    +"\n\nA fekete játékos lép először, lépni egyet átlósan előre lehet egy üres mezőre. Amelyik bábu elérte a tábla túlsó szélét, dámává válik, ennek jelzésére még egy bábut helyeznek rá, a dámák előnye, hogy visszafelé is tudnak mozogni az egyszerű bábukhoz hasonlóképpen."
    +"\n\nA bábu ugrani is tud, ha az ellenfél bábuja átlósan szomszédos vele és üres mező van mögötte. Az ugrások kötelezően végrehajtandóak, amennyiben egy bábu tud ugrani, muszáj is neki. Az átugrott bábuk lekerülnek a tábláról."
    +"\n\nA játék akkor ér véget, amikor az egyik játékos nem tud már lépni vagy nincs több bábuja vagy dámája. Ekkor ő veszít és a másik játékos a nyertes. Tehát a játékosok fő célja az ellenfél összes bábujának leütése, vagy az, hogy az ellenfél ne tudjon több lépést végrehajtani."
    +"\n\nDöntetlen is kialakulhat, ha a játékosok közösen megegyeznek, ha ugyanaz a lépés ismétlődött meg több, mint 3-szor egy játékos által vagy ha az elmúlt 40 lépésben nem történt sem ugrás, sem dámává válás.";


    /**
     * Létrehoz egy formázott JTextArea komponenst, amely a szabályokat jeleníti meg.
     *
     * @param fontSize a betűméret
     * @param marginSize a margó mérete
     * @return a szabályokat tartalmazó JTextArea
     */
    public static JTextArea ruleText(int fontSize, int marginSize){
        JTextArea rulesArea = new JTextArea(rule);       
        rulesArea.setMargin(new Insets(marginSize,marginSize,marginSize,marginSize));
        rulesArea.setLineWrap(true);              
        rulesArea.setWrapStyleWord(true); 
        rulesArea.setBackground(Menu.textBackColor);
        rulesArea.setForeground(Menu.textForeColor);
        rulesArea.setFont(new Font("Arial", Font.ITALIC, fontSize));
        return rulesArea;
    }

    /**
    * Létrehozza a Rule panelt.
    *
    * @param menuPair a menübe visszalépéshez használt CardLayout és JPanel pár
    */
    public Rule(Pair<CardLayout, JPanel> menuPair){
        this.setBackground(Menu.backGroundColor);
        this.setLayout(null);
        JTextArea rulesArea = ruleText(16,5);
        rulesArea.setBounds(50, 100,700,440);
        this.add(new BackToMenuButton(menuPair));
        this.add(rulesArea);
    }
}
