package Sudoku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GUI extends JFrame {
    int width,height;
    JPanel pan;
    boolean Menu = true;
    int padding = 50;
    int FX1, FY1, FX2, FY2, Fh, Fw;
    public int[][] feld;
    public GUI(int width, int height, int[][] feld){
        this.width = width;
        this.height = height;
        this.feld = feld;
        create();
    }

    private void create(){
        //the Window
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);     //if the window gets closed the projects closes
        //setImageIcon
        setResizable(true);                             //makes the window resizable
        setVisible(true);                               //makes the window visible
        setTitle("Sudoku");                          //sets the name

        setSize(width,height);
        setLocationRelativeTo(null);                    //loc
        setLayout(null);                                //needs for the JPanel

        pan = new PrivJPanel();                  //creates the JPanel
        pan.setBounds(0,0,getWidth(),getHeight());//sets the Bounds of the JPanel
        add(pan);                                       //adds the pan to the JFrame as a Component

        addMouseListener(new PrivMouseListener());      //sets the MouseListener
    }

    private void reloadVars(){
        pan.setBounds(0,0,getWidth(),getHeight());
        FX1 = padding;
        FY1 = padding;
        FX2 = getWidth()*2/3-padding;
        if (FX2 >= (getHeight()-padding)){
            FY2 = getHeight()-padding;
        }else{
            FY2 = FX2;
            FX2 = FY2;
        }
        Fh = FY2-FY1;
        Fw = FX2-FX1;
    }

    private class PrivJPanel extends JPanel{
        Graphics2D g;
        //private JLabel[][] labels;

        public void paintComponent(Graphics graphics){
            super.paintComponent(graphics);
            g = (Graphics2D) graphics;
            reloadVars();
            //Draw

            /*int newSize = Math.min(getWidth(), getHeight()) / 10;
            int oldSize = 0;
            if (newSize != oldSize) {
                for (int i = 0; i < labels.length; i++) {
                    for (int u = 0; u < labels[i].length; u++){
                        labels[i][u].setFont(new Font("Arial", Font.PLAIN, newSize));
                    }
                }
                oldSize = newSize;
            }*/



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

    private class PrivMouseListener implements MouseListener{

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
    }

    private void killFrame(){
        dispose();
    }
}
