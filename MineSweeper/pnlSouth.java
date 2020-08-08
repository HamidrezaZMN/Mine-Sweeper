package MineSweeper;

// imports
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

public class pnlSouth extends JPanel implements ActionListener{
//--------initializing--------
    // variables
    pnlCenter pnc;
    pnlNorth pnn;
    JDialog d;

    // combo boxes texts
    String[] themeNames = {"dracula", "dark", "light", "colorful"};
    String[] modeNames = {"9v9", "13v13", "16v16", "11v19",};
    String[] diffNames = {"easy", "medium", "hard"};
    
    // defining components
    JButton newGame = new JButton("NEW GAME");
    JComboBox themes = new JComboBox(themeNames);
    JComboBox modes = new JComboBox(modeNames);
    JComboBox difficulties = new JComboBox(diffNames);
    JLabel themeLabel = new JLabel("Theme: "),
           modeLabel = new JLabel("Mode: "),
           diffLabel = new JLabel("Difficulty: ");

    // constructor
    public pnlSouth(pnlCenter pnc, pnlNorth pnn, JDialog d){
        // initializing variables
        this.pnn = pnn;
        this.pnc = pnc;
        this.d = d;

        // adding components to the panel
        themes.addActionListener(this);
        modes.addActionListener(this);
        difficulties.addActionListener(this);
        add(themeLabel);add(themes); 
        add(diffLabel);add(difficulties);
        add(modeLabel);add(modes); 
    }
//------------end------------

//--------action performer--------
    @Override
    public void actionPerformed(ActionEvent e) {
        // performing each combo box
        JComboBox jcb = (JComboBox) e.getSource();
        if(jcb==themes) themeHandler(jcb);
        else if(jcb==modes) modeHandler(jcb);
        else diffHandler(jcb);
    }
    
    // handles theme
    public void themeHandler(JComboBox jcb){
        int selected = jcb.getSelectedIndex();
        pnc.THEME = themeNames[selected];
        pnc.themeChanger();
    }
    
    // handles game's mode
    public void modeHandler(JComboBox jcb){
        int selected = jcb.getSelectedIndex();
        String[] item_string = modeNames[selected].split("v");
        int row = Integer.parseInt(item_string[0]);
        int col = Integer.parseInt(item_string[1]);
        d.dispose(); 
        new MainClass(row, col);
    }
    
    // handles game's difficulty
    public void diffHandler(JComboBox jcb){
        int selected = jcb.getSelectedIndex();
        pnc.DIFFICULTY = diffNames[selected];
        pnc.newGame();
        pnn.numberOfBombs.setText(Integer.toString(pnc.NUMBER_OF_RANDS));
    }
//------------end------------
}