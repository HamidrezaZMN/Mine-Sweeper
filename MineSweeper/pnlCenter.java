package MineSweeper;

// imports
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.plaf.metal.MetalButtonUI;

public class pnlCenter extends JPanel implements ActionListener {
//--------initializing--------
    // buttons' row & col
    int row, col;
    // buttons
    JButton[][] btns;
    // total_sides
    int a, b;
    // paint
    int NUMBER_OF_RANDS, temp, rowBomb, colBomb;
    ArrayList<Point> bombs, bomb_sides, results;
    String[][] paint;
    // action performer
    ArrayList<Point> checked;
    JButton jbt;

    //theme handler
    String THEME;
    Color buttonBG, // button's background color
          buttonBorder, // button's border color
          buttonWithFlagBG, // button with flag background color
          buttonWithFlagText, // button with flag text color
          buttonEmptyBG, // disabled button background color
          buttonEmptyBorder, // disabled button foreground color
          buttonEmptyText; // disabled button text color
    Font f = new Font("TimesRoman", Font.PLAIN, 24); // font for button
    
    //difficulty handler
    String DIFFICULTY;

    // constructor
    public pnlCenter(int row, int col) {
        // initializing variables
        this.row = row; this.col = col;
        btns = new JButton[row][col];
        paint = new String[row][col];
        bombs = new ArrayList<>();
        bomb_sides = new ArrayList<>();
        checked = new ArrayList<>();
        // setting theme and difficulty
        THEME = "dracula";
        DIFFICULTY = "easy";
        setTheme(THEME);
        setDifficulty(DIFFICULTY);
        //painting and adding buttons
        paintMaker();        
        init();
    }
//------------end------------

//--------pnlNorth&South methods--------
    // makes a new game
    public void newGame(){
        // clearing variables
        bombs = new ArrayList<>();
        bomb_sides = new ArrayList<>();
        paint = new String[row][col];
        checked = new ArrayList<>();
        // setting up the whole thing
        setDifficulty(DIFFICULTY);
        setTheme(THEME);
        paintMaker();
        repairButton();
    }
    
    // repairs buttons for the new game
    public void repairButton(){
        JButton tempBtn;
        for(int i=0; i<row; i++)
            for(int j=0; j<col; j++){
                tempBtn = btns[i][j];
                if(!tempBtn.isEnabled()) {
                    tempBtn.setEnabled(true);
                    tempBtn.setBackground(buttonBG);
                    tempBtn.setBorder(BorderFactory.createLineBorder(buttonBorder));
                    tempBtn.setText("");
                }
            }
    }

    // setter for difficulty
    public void setDifficulty(String method){
        int diff = (int) (0.5*(row+col));
        switch(method){
            case "easy":
                NUMBER_OF_RANDS = diff;
                break;
            case "medium":
                NUMBER_OF_RANDS = (int) (1.5*diff);
                break;
            case "hard":
                NUMBER_OF_RANDS = (int) (1.5*diff);
                NUMBER_OF_RANDS = (int) (1.5*NUMBER_OF_RANDS);
        }
    }

    // setter for theme
    // each case is a theme
    public void setTheme(String method){
        switch(method){
            case "dracula":
                TSSC(
                    new Color(68, 71, 90),
                    new Color(93,93,93),
                    new Color(98, 114, 164),
                    new Color(255, 184, 108),
                    new Color(255, 121, 198),
                    new Color(24, 60, 68),
                    Color.white
                );
                break;
            case "dark":
                TSSC(
                    new Color(68, 68, 68),
                    new Color(255, 255, 255),
                    new Color(244, 212, 124),
                    new Color(32, 105, 224),
                    new Color(190, 190, 190),
                    new Color(135, 134, 131),
                    Color.black
                );
                break;
            case "colorful":
                TSSC(
                    new Color(255, 134, 80),
                    new Color(255, 255, 255),
                    new Color(139, 241, 139),
                    Color.black,
                    new Color(255, 233, 129),
                    new Color(255, 85, 94),
                    Color.white
                );
                break;
            case "light":
                TSSC(
                    new Color(158,158,158),
                    new Color(255, 255, 255),
                    new Color(136,0,0),
                    Color.white,
                    new Color(0,1,3),
                    new Color(222,222,222),
                    Color.black
                );
                break;
        }
    }
    
    // theme setter shortcut
    // it's to avoid copy paste
    public void TSSC(Color a, Color b, Color c, Color d, Color e, Color f, Color g){
        buttonBG = a;
        buttonBorder = b;
        buttonWithFlagBG = c;
        buttonWithFlagText = d;
        buttonEmptyBorder = e;
        buttonEmptyBG = f;
        buttonEmptyText = g;
    }

    // changes theme
    // it is used to change theme without appending new game
    public void themeChanger(){
        JButton btn;
        setTheme(THEME);
        for(int i=0; i<row; i++)
            for(int j=0; j<col; j++){
                btn = btns[i][j];
                if(btn.isEnabled()){
                    btn.setBackground(buttonBG);
                    btn.setBorder(BorderFactory.createLineBorder(buttonBorder));
                } else if(btns[i][j].getText()=="flag"){
                    btn.setBackground(buttonWithFlagBG);
                    btn.setBorder(BorderFactory.createLineBorder(buttonBorder));
                    btn.setUI(new MetalButtonUI(){
                        @Override
                        protected Color getDisabledTextColor(){
                            return buttonWithFlagText;
                        }
                    });
                } else {
                    btn.setBackground(buttonEmptyBG);
                    btn.setBorder(BorderFactory.createLineBorder(buttonEmptyBorder));
                    btn.setUI(new MetalButtonUI(){
                        @Override
                        protected Color getDisabledTextColor(){
                            return buttonEmptyText;
                        }
                    });
                }
            }
    }
//------------end------------
    
//--------initial methods--------
    // initializes the buttons
    public void init(){
        setLayout(new GridLayout(row, col));
        for(int i=0; i<row; i++)
            for(int j=0; j<col; j++){
                btns[i][j] = new JButton();
                btns[i][j].addActionListener(this);

                // puts flag when right button is pressed
                btns[i][j].addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if(e.getButton()==MouseEvent.BUTTON3) putFlag(e);
                    }
                    @Override
                    public void mousePressed(MouseEvent e) {}
                    @Override
                    public void mouseReleased(MouseEvent e) {}
                    @Override
                    public void mouseEntered(MouseEvent e) {}
                    @Override
                    public void mouseExited(MouseEvent e) {}
                });
                
                btns[i][j].setBorder(BorderFactory.createLineBorder(buttonBorder));
                btns[i][j].setName(i+"-"+j);
                btns[i][j].setFont(f);
                btns[i][j].setBackground(buttonBG);
                add(btns[i][j]);
            }
    }
//------------end------------
    
//--------instructing paint--------
    // makes the paint array which is used in paintComponent
    public void paintMaker(){
        // making bombs
        while(bombs.size()<NUMBER_OF_RANDS) {
            temp = (int) (Math.random()*(row*col));
            rowBomb = temp/col;
            colBomb = temp%col;
            if(!inArray(bombs, rowBomb, colBomb))
                bombs.add(new Point(rowBomb, colBomb));
        }
        // making bomb sides
        for(Point p: bombs)
            for(Point side: total_sides(p))
                if(inArray(bombs, side.x, side.y)==false)
                    bomb_sides.add(side);
        // making paint
        int sideN = 0;
        for(int i=0; i<row; i++)
            for(int j=0; j<col; j++){
                if(inArray(bombs, i, j))
                    paint[i][j] = "b";
                else if(inArray(bomb_sides, i, j)){
                    sideN = 0;
                    for(Point p: total_sides(new Point(i, j)))
                        if(inArray(bombs, p.x, p.y))
                            sideN++;
                    paint[i][j] = Integer.toString(sideN);
                }
                else
                    paint[i][j] = " ";
            }
    }
//------------end------------
    
//--------side methods--------
    // returns total sides of the given point
    public ArrayList<Point> total_sides(Point p){
        a = p.x; b = p.y;
        results = new ArrayList<>();
        for(int i=0; i<row; i++)
            for(int j=0; j<col; j++)
                if( (Math.abs(i-a)<=1) && (Math.abs(j-b)<=1) && (i!=a || j!=b) )
                    results.add(new Point(i, j));
        return results;
    }
    // returns if point(a, b) is in arr or not
    public boolean inArray(ArrayList<Point> arr, int a, int b){
        for(Point p: arr)
            if(p.x==a && p.y==b)
                return true;
        return false;
    }
    // counts how many visible button is left
    public int countBombsLeft(){
        int count = 0;
        for(int i=0; i<row; i++)
            for(int j=0; j<col; j++){
                if(btns[i][j].isEnabled()) count++;
                else if(btns[i][j].getText()=="flag") count++;
            }
        return count;
    }
    // checks if the game is finished
    public boolean isFinished(){
        return countBombsLeft()==NUMBER_OF_RANDS;
    }
    // Game-Over finisher
    public void GameOver(){
        for(int i=0; i<row; i++)
            for(int j=0; j<col; j++)
                disBtnSC(new Point(i, j));
        JOptionPane.showMessageDialog(this, "GAME OVER!");
    }
    // You-Won finisher
    public void YouWon(){
        for(int i=0; i<row; i++)
            for(int j=0; j<col; j++)
                if(btns[i][j].isEnabled()) disBtnSC(new Point(i, j));
        JOptionPane.showMessageDialog(this, "YOU WON!");
    }
//------------end------------
    
//--------action performer--------
    // disables the visibility of appropriate buttons
    // accroding to the given button
    public void disableButtons(Point btn){
        disBtnSC(btn);
        if("b".equals(paint[btn.x][btn.y])) GameOver();
        else if(" ".equals(paint[btn.x][btn.y]))
            for(Point p: total_sides(btn))
                if(!inArray(checked, p.x, p.y)){
                    disBtnSC(p);
                    checked.add(p);
                    if(" ".equals(paint[p.x][p.y])) disableButtons(p);
                }
    }

    //disable buttons shortcut
    public void disBtnSC(Point btn){
        btnSetPref(
            btns[btn.x][btn.y], 
            false, 
            f, 
            buttonEmptyBG, 
            buttonEmptyBorder,
            buttonEmptyText, 
            paint[btn.x][btn.y]
        );
    }

    // set prefrences for button
    public void btnSetPref(JButton btn, boolean enable, Font f, Color bgC, Color brC, Color tC, String t){
        btn.setEnabled(enable);
        btn.setFont(f);
        btn.setBackground(bgC);
        btn.setBorder(BorderFactory.createLineBorder(brC));
        btn.setUI(new MetalButtonUI(){
            @Override
            protected Color getDisabledTextColor(){
                return tC;
            }
        });
        btn.setText(t);
    }

    // right click puts flag
    public void putFlag(MouseEvent e){
        JButton btnRC = (JButton) e.getSource();
        if(btnRC.isEnabled())
            btnSetPref(
                btnRC, 
                false,
                f, 
                buttonWithFlagBG, 
                buttonBorder,
                buttonWithFlagText,
                "flag"
            );
        // if it has flag, then unflag it
        else if(btnRC.getText()=="flag")
            btnSetPref(
                btnRC, 
                true,
                f, 
                buttonBG, 
                buttonBorder,
                Color.white, 
                ""
            );
    }

    // returns coordinates of the given button in Point
    public Point coord(JButton btn){
        String[] btnName = btn.getName().split("-");
        int x = Integer.parseInt(btnName[0]);
        int y = Integer.parseInt(btnName[1]);
        return new Point(x, y);
    }

    // performs the action of button
    @Override
    public void actionPerformed(ActionEvent e) {
        // get coordinates of the clicked button
        JButton pbtn = (JButton) e.getSource();
        Point buttonPoint = coord(pbtn);
        // disables buttons properly (according to clicked button)
        disableButtons(buttonPoint);
        // finish the game if all none-bomb buttons are visible
        if(isFinished()) YouWon();
    }
//------------end------------
}