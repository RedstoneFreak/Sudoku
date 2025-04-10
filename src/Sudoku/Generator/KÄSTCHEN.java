package Sudoku.Generator;

import java.util.ArrayList;
public class KÄSTCHEN {
    ArrayList<Integer> möglichkeiten = new ArrayList<Integer>();
    int Wert;
    boolean gefüllt = false;
    public KÄSTCHEN() {
        for (int i = 1; i < 10; i++) {
            möglichkeiten.add(i);
        }
    }

    public void WertSetzen(int z) {
        if(z != 0) {
            Wert = z;
            gefüllt = true;
        }
    }

    /*public int WertGeben() {
        return Wert;
    }*/

    public void zurücksetzen() {
        gefüllt = false;
        möglichkeiten.clear();
        for (int i = 0; i < 9; i++) {
            möglichkeiten.add(i);
        }
    }


    public int FeldPrüfen(ZEILEN zeile, SPALTEN spalte, QUADRATE quadrat) {
        if(gefüllt) {
            return -2;
        }
        möglichkeitenAktualisieren(zeile, spalte, quadrat);
        if(möglichkeiten.size() > 1) {
            return 0;
        }
        else if(möglichkeiten.size() == 1) {
            WertSetzen(möglichkeiten.get(0));
            return möglichkeiten.get(0);
        }
        else {
            return -1;
        }
    }
    public void möglichkeitenAktualisieren(ZEILEN zeile, SPALTEN spalte, QUADRATE quadrat) {
        for (int i = 0; i < möglichkeiten.size(); i++) {
            if(!REGELPRÜFER.RegelnPrüfen(zeile, spalte, quadrat, möglichkeiten.get(i))) {
                möglichkeiten.remove(i);
            }
        }
    }
}

