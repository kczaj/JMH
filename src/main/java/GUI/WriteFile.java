/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.io.FileWriter;
import java.io.PrintWriter;
import Core.CellularAutomaton;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author Kuba
 */
public class WriteFile {
    
    private String gameType;
    private File file;
    private FileWriter fw;
    private PrintWriter pw;

    public WriteFile(String fileName) throws IOException {
        file = new File(fileName);
        this.fw = new FileWriter(this.file, false);
        this.pw = new PrintWriter(fw);
    }

    
    private void getGameType(CellularAutomaton ca) {
        gameType = ca.getClass().getName();
    }
    
    private void writeGameType() {
        if("Core.WireWorld".equals(gameType)){
            pw.println("Automat: WireWorld");
        } else {
            pw.println("Automat: GameOfLife");
        }
    }
    
    private void writeGrid(CellularAutomaton ca) {
        pw.println("Grid:");
        for (int i = 1; i < ca.getMainGrid().getHeight() - 1; i++) {
            for (int j = 1; j < ca.getMainGrid().getWidth() - 1; j++) {
                if ("Core.WireWorld".equals(gameType)) {
                    switch (ca.getMainGrid().getGameGridCell(i, j)) {
                        case WIRE:
                            pw.print("1 ");
                            break;
                        case EHEAD:
                            pw.print("2 ");
                            break;
                        case ETAIL:
                            pw.print("3 ");
                            break;
                        case EMPTY:
                            pw.print("0 ");
                            break;
                    }
                } else {
                    switch (ca.getMainGrid().getGameGridCell(i, j)) {
                        case ALIVE:
                            pw.print("1 ");
                            break;
                        case DEAD:
                            pw.print("0 ");
                            break;
                    }
                }
            }
            pw.println();
        }
        pw.println("/grid");
        pw.close();
        pw.flush();
    }
    
    public void writeFile(CellularAutomaton ca) {
        getGameType(ca);
        writeGameType();
        writeGrid(ca);
    }
}
