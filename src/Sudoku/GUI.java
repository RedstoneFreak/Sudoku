package Sudoku;

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
    public int[][] feld;
    JLabel[][] labels;
    boolean drawn = false;
    int activerow, activecol;
    public GUI(int width, int height, int[][] feld){
        this.width = width;
        this.height = height;
        this.feld = feld;
        labels = new JLabel[9][9];
        create();
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
        pan.setBounds(0,0,getWidth(),getHeight());//sets the Bounds of the JPanel
        add(pan);                                       //adds the pan to the JFrame as a Component

        for (int i = 0; i < 9; i++) {
            for (int u = 0; u < 9; u++) {
                labels[i][u] = new JLabel();
            }
        }
        drawn = true;
        NewSudoku();

        addMouseListener(new InputHandler());      //sets the MouseListener
        addKeyListener(new InputHandler());
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
            }



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

    public void NewSudoku(){
        for (int i = 0; i < feld.length; i++) {
            for (int u = 0; u < feld[i].length; u++) {
                if (feld[i][u] != 0) {
                    labels[i][u].setVerticalAlignment(SwingConstants.CENTER);
                    labels[i][u].setHorizontalAlignment(SwingConstants.CENTER);
                    labels[i][u].setText("" + feld[i][u] + "");
                }
            }
        }
    }

    private class InputHandler implements MouseListener, KeyListener{
        boolean Active;

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getButton() == 1){
                //linke maus taste

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

        }
    }

}
