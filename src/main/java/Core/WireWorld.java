/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import Grid.Cell;

/**
 *
 * @author Jakub
 */
public class WireWorld extends CellularAutomaton {

    public WireWorld(int hei, int wi) {
        super(hei, wi);
    }

    @Override
    protected Cell cellState(int i, int j) {
        int electronHeadNumber = 0;
        switch (mainGrid.getGameGridCell(i, j)) {
            case EMPTY:
                return Cell.EMPTY;
            case WIRE:
                for (int a = -1; a < 2; a++) {
                    for (int b = -1; b < 2; b++) {
                        if (mainGrid.getGameGridCell(i + a, j + b) == Cell.EHEAD) {
                            electronHeadNumber++;
                        }
                    }
                }
                if (electronHeadNumber == 1 || electronHeadNumber == 2) {
                    return Cell.EHEAD;
                } else {
                    return Cell.WIRE;
                }
            case EHEAD:
                return Cell.ETAIL;
            case ETAIL:
                return Cell.WIRE;

        }
        return Cell.EMPTY;
    }

    @Override
    public void generate() {
        for (int i = 1; i < this.mainGrid.getHeight() - 1; i++) {
            for (int j = 1; j < this.mainGrid.getWidth() - 1; j++) {
                this.utilGrid.setGameGridCell(i, j, cellState(i, j));
            }
        }
        notifyObservator();
        checkGrid();
        clearGrid(mainGrid.getGameGrid());
        changeGrid();
        clearGrid(utilGrid.getGameGrid());
    }

    @Override
    protected void clearGrid(Cell[][] gridToClear) {
        for (int i = 0; i < gridToClear.length; i++) {
            for (int j = 0; j < gridToClear[i].length; j++) {
                gridToClear[i][j] = Cell.EMPTY;
            }
        }
    }
}
