package menu;
import java.awt.CardLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * A menübe visszatérő gombot valósít meg, amely kattintásra megjeleníti a "menu" panelt.
 * <p>
 * A gomb formázott:
 * <ul>
 * <li>- "Menü" feliretot tartalmaz,</li>
 * <li>- fix mérettel, pozícióval és háttérszínnel rendelkezik.</li>
 * </ul>
 */
public class BackToMenuButton extends JButton{

    /**
     * Létrehozza a menübe visszalépő gombot.
     *
     * @param menuPair a menübe visszalépéshez használt CardLayout és JPanel pár
     */
    public BackToMenuButton(Pair<CardLayout,JPanel> menuPair){
        super("Menü");
        setBounds(10, 10, 100, 30);
        setBackground(Menu.buttonColor);
        addActionListener(e-> menuPair.getFirst().show(menuPair.getSecond(),"menu"));
    }
    
}