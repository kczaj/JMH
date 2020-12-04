/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grid;

/**
 *
 * @author Paweł
 */
public class Grid {

    private int width;
    private int height;
    private Cell[][] gameGrid;

    public Grid(int hei, int wi) {
        height = hei;
        width = wi;
        gameGrid = new Cell[height][width];
    }

    public void resize(int hei, int wi) {
        height = hei;
        width = wi;
        gameGrid = new Cell[height][width];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Cell[][] getGameGrid() {
        return gameGrid;
    }

    public Cell getGameGridCell(int i, int j) {
        return gameGrid[i][j];
    }

    public void setGameGridCell(int i, int j, Cell cell) {
        this.gameGrid[i][j] = cell;
    }

}
