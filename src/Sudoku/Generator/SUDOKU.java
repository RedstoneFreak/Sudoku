package Sudoku.Generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class SUDOKU {

    private int[][] Sudokufeld = new int[9][9];
    private int[][] Lösungsfeld = new int[9][9];
    private int[][] Loesung = new int[9][9];
    private KÄSTCHEN[][] Prüffeld = new KÄSTCHEN[9][9];
    private ZEILEN[] Zeilen = new ZEILEN[9];
    private SPALTEN[] Spalten = new SPALTEN[9];
    private QUADRATE[][] Quadrate = new QUADRATE[3][3];
    private ZEILEN[] PrüfZeilen = new ZEILEN[9];
    private SPALTEN[] PrüfSpalten = new SPALTEN[9];
    private QUADRATE[][] PrüfQuadrate = new QUADRATE[3][3];
    int Hinweise;
    Random random = new Random();
    //LocalDateTime ldt = new LocalDateTime();
    String Schwierigkeit;



    public SUDOKU(/*String SchwierigkeiNeu*/) {
        //Schwierigkeit = SchwierigkeiNeu;
        /*for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Prüffeld[j][i] = new KÄSTCHEN();

            }
            PrüfSpalten[i] = new SPALTEN();
            Spalten[i] = new SPALTEN();
            PrüfZeilen[i] = new ZEILEN();
            Zeilen[i] = new ZEILEN();
            PrüfQuadrate[i / 3][i % 3] = new QUADRATE();
            Quadrate[i / 3][i % 3] = new QUADRATE();
            switch(Schwierigkeit) {
                case "leicht" :
                    Hinweise = 55;
                    break;
                case "mittel" :
                    Hinweise = 40;
                    break;
                case "schwer" :
                    Hinweise = 25;
                    break;
                default :

            }
        }*/
    }

    public int[][] SudokufeldErstellen(String SchwierigkeiNeu) {
        Schwierigkeit = SchwierigkeiNeu;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Prüffeld[j][i] = new KÄSTCHEN();

            }


            PrüfSpalten[i] = new SPALTEN();
            Spalten[i] = new SPALTEN();
            PrüfZeilen[i] = new ZEILEN();
            Zeilen[i] = new ZEILEN();
            PrüfQuadrate[i / 3][i % 3] = new QUADRATE();
            Quadrate[i / 3][i % 3] = new QUADRATE();
            switch(Schwierigkeit) {
                case "leicht" :
                    Hinweise = 55;
                    break;
                case "mittel" :
                    Hinweise = 40;
                    break;
                case "schwer" :
                    Hinweise = 30;
                    break;
                default :

            }
        }

        SudokufeldErzeugen(0, 0);
        Übertragen();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Loesung[i][j] = Lösungsfeld[i][j];
            }
        }
        while (!LückenSchaffen(random.nextInt(0, 8), random.nextInt(0, 8), 81 - Hinweise));
        //Save();
        return Sudokufeld;
    }

    public int[][] LoesungAusgeben(){
        return Loesung;
    }

    public boolean SudokufeldErzeugen(int col, int row) {
        if(col == 9) {
            col = 0;
            row++;
        }
        ArrayList<Integer> zufall = new ArrayList<Integer>();
        for (int i = 1; i < 10; i++) {
            zufall.add(i);
        }
        Collections.shuffle(zufall);
        for (int i = 0; i < 9; i++) {
            if(REGELPRÜFER.RegelnPrüfen(PrüfZeilen[row], PrüfSpalten[col], PrüfQuadrate[row / 3][col / 3], zufall.get(i))) {
                PrüfEinsetzen(row, col, zufall.get(i));
                Prüffeld[row][col].Wert = zufall.get(i);
                if((col == 8 && row == 8) || SudokufeldErzeugen(col + 1, row)) {
                    return true;
                }
                PrüfLöschen(row, col);
                Prüffeld[row][col].Wert = 0;
            }
        }
        return false;
    }

    public boolean LückenSchaffen(int row, int col, int AnzahlZuLöschen) {
        if(AnzahlZuLöschen == 0) {
            return true;
        }
        ArrayList<Integer> zufallrow = new ArrayList<Integer>();
        ArrayList<Integer> zufallcol = new ArrayList<Integer>();
        for (int i = 0; i < 9; i++) {
            zufallrow.add(i);
        }
        for (int i = 0; i < 9; i++) {
            zufallcol.add(i);
        }
        Collections.shuffle(zufallrow);
        Collections.shuffle(zufallcol);
        if(Sudokufeld[row][col] == 0) {
            return false;
        }
        int z = Sudokufeld[row][col];
        Löschen(row, col);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if(Lösen(Sudokufeld, Zeilen, Spalten, Quadrate) && LückenSchaffen(zufallrow.get(i), zufallcol.get(j), AnzahlZuLöschen - 1)) {
                    return true;
                }
            }
        }
        Einsetzen(row, col, z);
        return false;
    }

    public void Einsetzen(int row, int col, int z) {
        Sudokufeld[row][col] = z;
        Zeilen[row].Hinzufügen(z);
        Spalten[col].Hinzufügen(z);
        Quadrate[row / 3][col / 3].Hinzufügen(z);
    }

    public void Löschen(int row, int col) {
        int z = Sudokufeld[row][col];
        Sudokufeld[row][col] = 0;
        Zeilen[row].belegt.remove(Integer.valueOf(z));
        Spalten[col].belegt.remove(Integer.valueOf(z));
        Quadrate[row / 3][col / 3].belegt.remove(Integer.valueOf(z));
    }

    public void PrüfEinsetzen(int row, int col, int z) {
        PrüfZeilen[row].belegt.add(z);
        PrüfSpalten[col].belegt.add(z);
        PrüfQuadrate[row / 3][col / 3].belegt.add(z);
    }

    public void PrüfLöschen(int row, int col) {
        int z = Prüffeld[row][col].Wert;
        PrüfZeilen[row].belegt.remove(Integer.valueOf(z));
        PrüfSpalten[col].belegt.remove(Integer.valueOf(z));
        PrüfQuadrate[row / 3][col / 3].belegt.remove(Integer.valueOf(z));
    }

    /*public boolean belegtPrüfen(int row, int col) {
        if(Sudokufeld[row][col] == 0) {
            return false;
        }
        return true;
    }*/

    /*public void SudokufeldLeeren() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Sudokufeld[j][i] = 0;
            }
        }
    }*/

    public String SudokuPrüfen() {
        for (int col = 0; col < 9; col++) {
            for (int row = 0; row < 9; row++) {
                Prüffeld[row][col].zurücksetzen();
                Prüffeld[row][col].WertSetzen(Sudokufeld[row][col]);
            }
            PrüfSpalten[col] = Spalten[col];
            PrüfQuadrate[col / 3][col % 3] = Quadrate[col / 3][col % 3];
            PrüfZeilen[col] = Zeilen[col];
        }

        int j = 0;
        int gefülltCounter = 0;
        int möglichCounter = 0;
        while (möglichCounter < 82) {
            if(j > 80) {
                j = 0;
                gefülltCounter = 0;
            }
            int i = Prüffeld[j / 9][j % 9].FeldPrüfen(PrüfZeilen[j / 9], PrüfSpalten[j % 9], PrüfQuadrate[(j / 9) / 3][(j % 9) / 3]);
            if(i == -2) {
                gefülltCounter ++;
                if(gefülltCounter == 80) {
                    return "gelöst";
                }
            }
            else if(i == -1) {
                return "ungelöst";
            }
            else if(i != 0) {
                möglichCounter = 0;
                PrüfEinsetzen(j / 9, j % 9, i);
            }
            else {
                möglichCounter++;
            }
            j++;
        }
        return "ungelöst";
    }

    /*public void Ausgeben() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(Sudokufeld[i][j] + " ");
            }
            System.out.println();
        }
    }*/

    /*public void Save() {
        Files.append("Sudokus.txt", Schwierigkeit + ";");
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Files.append("Sudokus.txt", Sudokufeld[i][j]+",");
            }
        }
    }*/


    public void Übertragen() {
        for (int col = 0; col < 9; col++) {
            for (int row = 0; row < 9; row++) {
                Sudokufeld[row][col] = Prüffeld[row][col].Wert;
                Lösungsfeld[row][col] = Sudokufeld[row][col];
            }
        }
    }

    public boolean Lösen(int[][] board, ZEILEN[] Row, SPALTEN[] Coloumn, QUADRATE[][] Squares) {
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                if(board[row][column] == 0) {
                    for (int k = 1; k < 10; k++) {
                        board[row][column] = k;
                        if(REGELPRÜFER.RegelnPrüfen(Row[row], Coloumn[column], Squares[row / 3][column / 3], k) && Lösen(board, Row, Coloumn, Squares)) {
                            board[row][column] = 0;
                            return true;
                        }
                        board[row][column] = 0;
                    }
                    return false;
                }
            }
        }
        return true;
    }


}
