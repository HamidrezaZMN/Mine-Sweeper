package MineSweeper;

// imports
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

public class pnlNorth extends JPanel implements ActionListener{
//--------initializing--------
    // variables
    pnlCenter pnc;
    
    // defining components
    JButton newGame = new JButton("NEW GAME");
    JTextArea numberOfBombs = new JTextArea(1, 1);
    JLabel numberOfBombsLabel = new JLabel("number of bombs: ");
    
    // constructor
    public pnlNorth(pnlCenter pnc) {
        // initializing variables
        this.pnc = pnc;
        
        // adding components to the panel
        newGame.addActionListener(this);
        add(newGame);
        add(numberOfBombsLabel); add(numberOfBombs);

        // initializing text area
        numberOfBombs.setBackground(Color.yellow);
        numberOfBombs.setText(Integer.toString(pnc.NUMBER_OF_RANDS));
    }
//------------end------------

//--------action performer--------
    @Override
    public void actionPerformed(ActionEvent e) {
        pnc.newGame();
    }
//------------end------------
}