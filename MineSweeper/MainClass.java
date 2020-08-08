package MineSweeper;

// imports
import java.awt.*;
import javax.swing.JDialog;
import javax.swing.JFrame;

public class MainClass extends JDialog {
    // variables
    Dimension screenSize;
    int row, col , w, h, max, side;

    // constructor
    public MainClass(int row, int col){
        // initializing variables
        // this part measures the needed size of the dialoge
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.row = row; this.col = col;
        max = (int) (0.7*screenSize.getHeight());
        side = (int) (max/row);
        w = col*side;
        h = row*side;

        // initializing dialoge
        setTitle("MineSweeper!");
        setSize(w, h);        
        setResizable(false);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // adding other panels to the dialoge
        pnlCenter pnc = new pnlCenter(row, col); add(pnc, BorderLayout.CENTER);
        pnlNorth pnn = new pnlNorth(pnc); add(pnn, BorderLayout.NORTH);
        pnlSouth pns = new pnlSouth(pnc, pnn, this); add(pns, BorderLayout.SOUTH);
        
        // making dialoge visible
        setVisible(true);
    }

    // starter of the program
    public static void main(String[] args) {
        new MainClass(9, 9);
    }
}