/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import GUI.Observator;
import Grid.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Paweł
 */
public abstract class CellularAutomaton implements Observable {

    protected Grid mainGrid;
    protected Grid utilGrid;
    private boolean isGridChanging = true;

    private ArrayList<Observator> observers;

    public CellularAutomaton(int hei, int wi) {
        mainGrid = new Grid(hei, wi);
        utilGrid = new Grid(hei, wi);
        clearGrid(mainGrid.getGameGrid());
        observers = new ArrayList<Observator>();
    }

    @Override
    public void addObservator(Observator o) {
        observers.add(o);
    }

    @Override
    public void removeObservator(Observator o) {
        int index = observers.indexOf(o);
        observers.remove(index);
    }

    @Override
    public void notifyObservator() {
        for (Observator o : observers) {
            o.onUpdate();
        }
    }

    public void resizeGameGrid(int hei, int wi) {
        mainGrid.resize(hei, wi);
        clearGrid(mainGrid.getGameGrid());
        utilGrid.resize(hei, wi);
        clearGrid(utilGrid.getGameGrid());
    }

    protected void changeGrid() {
        for (int i = 0; i < mainGrid.getHeight(); i++) {
            for (int j = 0; j < mainGrid.getWidth(); j++) {
                mainGrid.setGameGridCell(i, j, utilGrid.getGameGridCell(i, j));
            }
        }
    }

    protected void checkGrid() {
        if (Arrays.deepEquals(mainGrid.getGameGrid(), utilGrid.getGameGrid())) {
            this.setIsGridChanging(false);
        } else {
            this.setIsGridChanging(true);
        }
    }

    protected abstract void clearGrid(Cell[][] gridToClear);

    public abstract void generate();

    protected abstract Cell cellState(int i, int j);

    public boolean isIsGridChanging() {
        return this.isGridChanging;
    }

    public void setIsGridChanging(boolean isGridChanging) {
        this.isGridChanging = isGridChanging;
    }

    public Grid getMainGrid() {
        return mainGrid;
    }

}
