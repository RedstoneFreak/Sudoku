package Sudoku;

import Sudoku.Generator.SUDOKU;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GUI extends JFrame {
    int width,height;
    JPanel pan;
    boolean Menu = true;
    int padding = 50;
    int FX1, FY1, FX2, FY2, Fh, Fw;
    int fontSize = 1;
    int newSize = 1;
    public int[][] feld;
    public int[][] Loesung;
    JLabel[][] labels;
    //JLabel textfeld;
    JLabel[] button;
    boolean drawn = false;
    int activerow, activecol;
    boolean Active = false;
    SUDOKU s = new SUDOKU();
    public GUI(int width, int height){
        this.width = width;
        this.height = height;
        labels = new JLabel[9][9];
        button = new JLabel[3];
        feld = new int[9][9];
        /*textfeld = new JLabel();
        textfeld.setVerticalAlignment(SwingConstants.CENTER);
        textfeld.setHorizontalAlignment(SwingConstants.CENTER);
        textfeld.setVisible(true);
        textfeld.setOpaque(false);*/
        create();
    }

    public void fieldset(int row, int col, int num){
        feld[col][row] = num;
    }
    public int[][] fieldgive(){
        return feld;
    }

    private void create(){
        //the Window
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);     //if the window gets closed the projects closes
        //setImageIcon
        setResizable(true);                              //makes the window resizable
        setVisible(true);                               //makes the window visible
        setTitle("Sudoku");                          //sets the name

        setSize(width,height);
        setLocationRelativeTo(null);                    //loc
        setLayout(null);                                //needs for the JPanel


        pan = new PrivJPanel();                  //creates the JPanel
        //pan.setBackground(Color.LIGHT_GRAY);
        pan.setBounds(0,0,getWidth(),getHeight());//sets the Bounds of the JPanel
        add(pan);                                       //adds the pan to the JFrame as a Component

        for (int i = 0; i < 9; i++) {
            for (int u = 0; u < 9; u++) {
                labels[i][u] = new JLabel();
            }
        }
        for (int i = 0; i < button.length; i++){
            button[i] = new JLabel();
            button[i].setVerticalAlignment(SwingConstants.CENTER);
            button[i].setHorizontalAlignment(SwingConstants.CENTER);
        }
        button[0].setText("Einfach");
        button[1].setText("Normal");
        button[2].setText("Schwer");
        drawn = true;

        addKeyListener(new InputHandler());
        addMouseListener(new InputHandler());      //sets the MouseListener

    }

    private void reloadVars(){
        pan.setBounds(0,0,getWidth(),getHeight());
        FX1 = padding;
        FY1 = padding;
        FX2 = getWidth()*2/3-padding;
        if (FX2 >= (getHeight()-padding)){
            FY2 = getHeight()-padding;
            FX2 = FY2;
        }else{
            FY2 = FX2;
        }
        Fh = FY2-FY1;
        Fw = FX2-FX1;

        fontSize = Math.min(Fw, Fh) / 11;
    }

    private class PrivJPanel extends JPanel{
        Graphics2D g;

        public void paintComponent(Graphics graphics){
            super.paintComponent(graphics);
            g = (Graphics2D) graphics;
            reloadVars();
            //Draw

            drawQuadrat(FX1, FY1, FX2, FY2, 3);
            drawrasta(FX1, FY1, FX2, FY2, 3);

            //Reihe1
            drawrasta(FX1, FY1, Fw/3+FX1, Fh/3+FY1, 1);
            drawrasta(Fw/3+FX1, FY1, Fw*2/3+FX1, Fh/3+FY1, 1);
            drawrasta(Fw*2/3+FX1, FY1, Fw+FX1, Fh/3+FY1, 1);
            //Reíhe2
            drawrasta(FX1, Fh/3+FY1, Fw/3+FX1, Fh*2/3+FY1, 1);
            drawrasta(Fw/3+FX1, Fh/3+FY1, Fw*2/3+FX1, Fh*2/3+FY1, 1);
            drawrasta(Fw*2/3+FX1, Fh/3+FY1, Fw+FX1, Fh*2/3+FY1, 1);
            //Reíhe3
            drawrasta(FX1, Fh*2/3+FY1, Fw/3+FX1, Fh+FY1, 1);
            drawrasta(Fw/3+FX1, Fh*2/3+FY1, Fw*2/3+FX1, Fh+FY1, 1);
            drawrasta(Fw*2/3+FX1, Fh*2/3+FY1, Fw+FX1, Fh+FY1, 1);

            if (drawn) {
                for (int i = 0; i < labels.length; i++) {
                    for (int u = 0; u < labels[i].length; u++) {
                        labels[i][u].setFont(new Font("Arial", Font.BOLD, fontSize));
                        labels[i][u].setLocation(Fw * (u) / 9 + FX1, Fh * (i) / 9 + FY1);
                        labels[i][u].setSize((FX2 - FX1) / 9, (FY2 - FY1) / 9);
                        labels[i][u].setVisible(true);
                        labels[i][u].setOpaque(false);
                        add(labels[i][u]);
                    }
                }
                button[0].setLocation(getWidth()*2/3, getHeight()/4);
                button[0].setSize(getWidth()-getWidth()*2/3-padding,getHeight()/2/3-(padding/2));
                button[1].setLocation(getWidth()*2/3,(getHeight()/4)+getHeight()/2/3);
                button[1].setSize(getWidth()-getWidth()*2/3-padding,getHeight()/2/3-(padding/2));
                button[2].setLocation(getWidth()*2/3,(getHeight()/4)+getHeight()/2*2/3);
                button[2].setSize(getWidth()-getWidth()*2/3-padding,getHeight()/2/3-(padding/2));
                for (int i = 0; i < button.length; i++){
                    newSize = (int) (fontSize/1.3);
                    button[i].setFont(new Font("Arial", Font.BOLD, newSize));
                    button[i].setVisible(true);
                    button[i].setBackground(Color.GRAY);
                    button[i].setOpaque(true);
                    add(button[i]);
                }
            }
            /*textfeld.setFont(new Font("Arial", Font.BOLD, fontSize/2));
            textfeld.setLocation(getWidth()*2/3-padding,padding/2);
            textfeld.setSize((getWidth())-(getWidth()*2/3),getHeight()/3-padding);
            add(textfeld);*/


        }
        public void drawQuadrat(int X1, int Y1, int X2, int Y2, int Stroke){
            g.setStroke(new BasicStroke(Stroke));
            g.drawRect(X1, Y1, X2-X1, Y2-Y1);
            g.setStroke(new BasicStroke(1));
        }
        public void drawrasta(int X1, int Y1, int X2, int Y2, int Stroke){
            int x2 = X2-X1;
            int y2 = Y2-Y1;
            g.setStroke(new BasicStroke(Stroke));
            g.drawLine((x2 / 3)+X1, Y1, (x2 / 3)+X1, Y2);
            g.drawLine((x2*2 / 3)+X1, Y1, (x2*2 / 3)+X1, Y2);
            g.drawLine(X1, (y2 / 3)+Y1, X2, (y2 / 3)+Y1);
            g.drawLine(X1, (y2*2 / 3)+Y1, X2, (y2*2 / 3)+Y1);
            g.setStroke(new BasicStroke(1));
        }
    }

    private void killFrame(){
        dispose();
    }

    public void NewSudoku(String difficulty){
        //feld = new int[9][9];
        Loesung = new int[9][9];
        for (int i = 0;i < feld.length; i++){
            for (int u = 0; u < feld[i].length; u++){
                feld[i][u] = 0;
            }
        }
        feld = s.SudokufeldErstellen(difficulty);
        Loesung = s.LoesungAusgeben();
        for (int i = 0; i < feld.length; i++) {
            for (int u = 0; u < feld[i].length; u++) {
                if (feld[i][u] != 0) {
                    labels[i][u].setVerticalAlignment(SwingConstants.CENTER);
                    labels[i][u].setHorizontalAlignment(SwingConstants.CENTER);
                    labels[i][u].setText("" + feld[i][u] + "");
                }else {
                    labels[i][u].setText("");
                }
            }
        }
    }

    private class InputHandler implements MouseListener, KeyListener{
        ActionHandler a = new ActionHandler();

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getButton() == 1){
                //linke maus taste
                if ((FY1+31) <= e.getY() && (FY2+31)  >= e.getY() && FX1 <= e.getX() && (FX1+Fw) >= e.getX()) {
                    activerow = (((e.getY()-32)-FX1)/(Fh/9));
                    activecol = (((e.getX()-7)-FY1)/(Fw/9));
                    Active = true;
                }
                if (e.getX() >= (getWidth()*2/3) && e.getX() <= (getWidth()-padding) && e.getY()-32 >=  getHeight()/4 && e.getY()-32 <= getHeight()/4+getHeight()/2/3-(padding/2)){
                    NewSudoku("leicht");
                }
                if (e.getX() >= (getWidth()*2/3) && e.getX() <= (getWidth()-padding ) && e.getY()-32 >=  getHeight()/4+getHeight()/2/3 && e.getY()-32 <= getHeight()/4+getHeight()/2/3+getHeight()/2/3-(padding/2)){
                    NewSudoku("mittel");
                }
                if (e.getX() >= (getWidth()*2/3) && e.getX() <= (getWidth()-padding) && e.getY()-32 >=  getHeight()/4+getHeight()/2*2/3 && e.getY()-32 <= getHeight()/4+getHeight()/2*2/3+getHeight()/2/3-(padding/2)){
                    NewSudoku("schwer");
                }
                //textfeld.setText("");
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }


        //Keys
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (Active){
                if (e.getKeyCode() == KeyEvent.VK_NUMPAD1 || e.getKeyCode() == KeyEvent.VK_1){
                    a.Eingabe(activerow, activecol, 1);
                    Active = false;
                }
                if (e.getKeyCode() == KeyEvent.VK_NUMPAD2 || e.getKeyCode() == KeyEvent.VK_2){
                    a.Eingabe(activerow, activecol, 2);
                    Active = false;
                }
                if (e.getKeyCode() == KeyEvent.VK_NUMPAD3 || e.getKeyCode() == KeyEvent.VK_3){
                    a.Eingabe(activerow, activecol, 3);
                    Active = false;
                }
                if (e.getKeyCode() == KeyEvent.VK_NUMPAD4 || e.getKeyCode() == KeyEvent.VK_4){
                    a.Eingabe(activerow, activecol, 4);
                    Active = false;
                }
                if (e.getKeyCode() == KeyEvent.VK_NUMPAD5 || e.getKeyCode() == KeyEvent.VK_5){
                    a.Eingabe(activerow, activecol, 5);
                    Active = false;
                }
                if (e.getKeyCode() == KeyEvent.VK_NUMPAD6 || e.getKeyCode() == KeyEvent.VK_6){
                    a.Eingabe(activerow, activecol, 6);
                    Active = false;
                }
                if (e.getKeyCode() == KeyEvent.VK_NUMPAD7 || e.getKeyCode() == KeyEvent.VK_7){
                    a.Eingabe(activerow, activecol, 7);
                    Active = false;
                }
                if (e.getKeyCode() == KeyEvent.VK_NUMPAD8 || e.getKeyCode() == KeyEvent.VK_8){
                    a.Eingabe(activerow, activecol, 8);
                    Active = false;
                }
                if (e.getKeyCode() == KeyEvent.VK_NUMPAD9 || e.getKeyCode() == KeyEvent.VK_9){
                    a.Eingabe(activerow, activecol, 9);
                    Active = false;
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
                killFrame();
            }
        }
    }

    public class ActionHandler{
        int row, col, num; /*Krow, Kcol*/
        //boolean moeglich;
        public ActionHandler(){}

        public void Eingabe(int row, int col, int num){
            this.row = row;
            this.col = col;
            this.num = num;

            if (feld[row][col] == 0){
                if(Loesung[row][col] == num){
                    feld[row][col] = num;
                    labels[row][col].setVerticalAlignment(SwingConstants.CENTER);
                    labels[row][col].setHorizontalAlignment(SwingConstants.CENTER);
                    labels[row][col].setText("" + feld[row][col] + "");
                }
            }
            //moeglich = true;

            /*if(num >= 1 && num <= 9) {
                for (int i = 0; i < feld.length; i++) {
                    if(feld[i][col] == num) {
                        moeglich = false;
                    }
                }
                if(moeglich) {
                    for (int u = 0; u < feld[row].length; u++) {
                        if(feld[row][u] == num) {
                            moeglich = false;
                        }
                    }

                    if(moeglich) {
                        if(row >= 0 && row <= 2) {
                            Krow = 0;
                        }else if(row >= 3 && row <= 5) {
                            Krow = 3;
                        }else if(row >= 6 && row <= 8) {
                            Krow = 6;
                        }
                        if(col >= 0 && col <= 2) {
                            Kcol = 0;
                        }else if(col >= 3 && col <= 5) {
                            Kcol = 3;
                        }else if(col >= 6 && col <= 8) {
                            Kcol = 6;
                        }

                        for (int i = Krow; i < Krow + 3; i++) {
                            for (int u = Kcol; u < Kcol + 3; u++) {
                                if(feld[i][u] == num) {
                                    moeglich = false;
                                }
                            }
                        }
                    }
                }
                if(moeglich) {
                    feld[row][col] = num;
                    labels[row][col].setVerticalAlignment(SwingConstants.CENTER);
                    labels[row][col].setHorizontalAlignment(SwingConstants.CENTER);
                    labels[row][col].setText("" + feld[row][col] + "");
                }else {
                    //textfeld.setText("Diese Zahl passt hier nicht!");
                }
            }*/
        }
    }

}
