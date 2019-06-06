/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boris.modele.IA;

import com.boris.modele.Jeu;

/**
 *
 * @author boris
 */
public class IABasique extends AbstractIA {
    
    public IABasique(Jeu j) {
        super(j);
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
    
}
