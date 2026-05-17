package leaderboard;


import javax.swing.*;

import menu.BackToMenuButton;
import menu.Menu;
import menu.Pair;

import java.awt.*;

/**
 * A LeaderBoard osztály megjeleníti az aktuális állást a ranglista alapján.
 * <p>
 * Tartalmazza:
 * <ul>
 * <li>- a fejlécet,</li>
 * <li>- a gördíthető adatokat,</li>
 * <li>- a menübe visszalépő gombot.</li>
 * </ul>
 * A ranglista adatai 3 oszlopban jelennek:
 * <ul>
 * <li>- a helyezéseket (azonos győzelemszám esetén azonos a helyezés is)</li>
 * <li>- a játékosok </li>
 * <li> a játékosok győzelmeinek számát.</li>
 * </ul>
 */
public class LeaderBoard extends JPanel{

     /**
     * Formázott JTextArea komponenst hoz létre a megadott szöveggel.
     * 
     * @param content szöveg tartalma
     * @param style a betűstílus (pl. Font.PLAIN vagy Font.BOLD)
     * @return a formázott JTextArea komponens
     */
    private JTextArea newText(String content, int style){
        JTextArea text= new JTextArea(content);
        text.setFont(new Font("Arial",style,15));
        text.setMargin(new Insets(5, 5, 5, 5));
        text.setForeground(Menu.textForeColor);
        text.setBackground(Menu.textBackColor);
        text.setEditable(false);
        return text;
    }

    /**
     * Létrehozza a fejlécet, amely 3 oszlopot tartalmaz: Helyezés, Név, Pontszám.
     *
     * @return a fejléc panel
     */
    private JPanel headerPanel(){
        JPanel header= new JPanel(new GridLayout(1,3));
        JTextArea headerPlace= newText("Helyezés",Font.BOLD);
        header.add(headerPlace);
        JTextArea headerName= newText("Név",Font.BOLD);
        header.add(headerName);
        JTextArea headerPoint= newText("Pontszám",Font.BOLD);
        header.add(headerPoint);
        header.setBounds(150,100,483,40);
        return header;
    }

    /**
     * Létrehozza a ranglista adatait megjelenítő panelt, amely 3 oszlopot tartalmaz: a helyezéseket (azonos győzelmek esetén azonos helyezés), a játékosok neveit, a győzelmek számát.
     *
     * @param list a ranglistát tartalmazó ScoreList
     * @return az adatokat tartalmazó panel
     */
    private JPanel dataPanel(ScoreList list){
        JPanel data= new JPanel(new GridLayout(1,3));
        if(!list.getList().isEmpty()){
            String places="1\n";
            String wins=Integer.toString(list.getList().get(0).getWin())+"\n";
            int place=1;
            for(int i=1; i<list.getList().size(); i++){
                if(list.getList().get(i).getWin()!=list.getList().get(i-1).getWin())
                    place++;
                places=places+place+"\n";
                wins=wins+list.getList().get(i).getWin()+"\n";
            }
            data.add(newText(places,Font.PLAIN));
            data.add(newText(list.names(),Font.PLAIN));
            data.add(newText(wins,Font.PLAIN));
        }
        else
            data.add(newText("Nincs játékos a ranglistában.",Font.PLAIN));
        data.setBounds(150,140,500,490);
        return data;
    }
    
    /**
    * Létrehozza a LeaderBoard panelt.
    *
    * @param menuPair a menübe visszalépéshez használt CardLayout és JPanel pár
    * @param list a ranglistát tartalmazó ScoreList, amelyből az adatok megjelenítésre kerülnek
    */
    public LeaderBoard(Pair<CardLayout, JPanel> menuPair, ScoreList list){
        this.setBackground(Menu.backGroundColor);
        this.setLayout(null);
        this.add(headerPanel());
        JScrollPane scrollPane = new JScrollPane(dataPanel(list));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(150,140, 500,490);
        this.add(scrollPane);
        this.add(new BackToMenuButton(menuPair));
    }
}
