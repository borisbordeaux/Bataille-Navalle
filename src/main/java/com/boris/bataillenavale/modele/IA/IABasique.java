/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boris.bataillenavale.modele.IA;

import com.boris.bataillenavale.modele.Jeu;

/**
 * Une IA simple qui tire toutes les cases dans l'ordre
 *
 * @author boris
 */
public class IABasique extends AbstractIA {

    /**
     * le constructeur
     *
     * @param j le modele sur lequel l'ia pourra jouer
     */
    public IABasique(Jeu j) {
        super(j);
    }

    /**
     * permet de jouer un coup
     */
    @Override
    public void tirerSurUneCase() {
        modele.tirerSurCase(x, y, true);
        x++;
        if (x == 10) {
            x = 0;
            y++;
        }
    }

}
