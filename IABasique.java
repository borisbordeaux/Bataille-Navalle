/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boris.modele;

/**
 *
 * @author boris
 */
public class IABasique extends AbstractIA {

    private int x;
    private int y;
    
    public IABasique(Jeu j) {
        super(j);
        x = 0;
        y = 0;
    }

    @Override
    public void tirerSurUneCase() {
        jeu.tirerSurCase(x, y, true);
        x++;
        if (x == 10){
            x = 0;
            y++;
        }
    }

    @Override
    public void reset() {
        x = 0;
        y = 0;
    }
    
}
