/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import GUI.Observator;

/**
 *
 * @author Paweł
 */
public interface Observable {

    public void addObservator(Observator o);

    public void removeObservator(Observator o);

    public void notifyObservator();
    
}
