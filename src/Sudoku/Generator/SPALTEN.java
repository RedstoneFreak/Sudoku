package Sudoku.Generator;

import java.util.ArrayList;

public class SPALTEN {
    ArrayList<Integer> belegt = new ArrayList<Integer>();

    public boolean Überprüfen(int z) {
        return !belegt.contains(z);
    }

    public void Hinzufügen(int z) {
        belegt.add(z);
    }
}

