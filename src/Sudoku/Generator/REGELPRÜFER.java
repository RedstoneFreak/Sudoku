package Sudoku.Generator;

public class REGELPRÜFER {

    private REGELPRÜFER() {}

    public static boolean RegelnPrüfen(ZEILEN zeile, SPALTEN spalte, QUADRATE quadrat, int z) {
        if(zeile.Überprüfen(z) && spalte.Überprüfen(z) && quadrat.Überprüfen(z)) {
            return true;
        }
        return false;
    }
}
